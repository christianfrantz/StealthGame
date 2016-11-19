package com.stealth.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.stealth.game.GunBase;
import com.stealth.game.Hud;
import com.stealth.game.MainGame;
import com.stealth.game.ServerStuff.Packet;
import com.stealth.game.ServerStuff.PlayerClient;

import box2dLight.ConeLight;
import box2dLight.Light;
import box2dLight.RayHandler;

/**
 * Created by imont_000 on 10/29/2016.
 */

public class Player extends Sprite {
    public World world;
    public Body body;
    public Sprite cursor;
    public Light playerLight;
    public GunBase gunBase;
    public PlayerClient playerClient;

    public enum PlayerClass{
        BASIC,
        CAMPER,
        STEALTH
    }

    public PlayerClass playerClass;

    public Player(World world, RayHandler rayHandler, PlayerClass playerClass){
        this.world = world;
        setupBody();
        setTexture(new Texture("player.png"));
        setBounds(0, 0, 64 / MainGame.PPM, 64 / MainGame.PPM);
        setOrigin(32 / MainGame.PPM, 32 / MainGame.PPM);

        cursor = new Sprite(new Texture("cursor.png"));
        cursor.setBounds(0, 0, 16 / MainGame.PPM, 16 / MainGame.PPM);

        setPosition(50, 50);

        playerLight = new ConeLight(rayHandler, 32, Color.GOLD, 5, (getOriginX() / MainGame.PPM), (getOriginY() / MainGame.PPM), 270, 45);
        playerLight.attachToBody(body);

        switch (playerClass){
            case CAMPER:
                this.playerClass = playerClass;
                gunBase = new GunBase(3, 3, true);
                break;
            case BASIC:
                this.playerClass = playerClass;
                gunBase = new GunBase(10, 10, true);
                break;
            case STEALTH:
                this.playerClass = playerClass;
                gunBase = new GunBase(6, 6, true);
                break;
        }

        MainGame.playerClient.setPlayer(this);
    }

    public void update(float dt, Camera gameCam, Hud hud){
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);

        if(!hud.touchpad.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            gameCam.unproject(touchPos);
            cursor.setPosition(touchPos.x, touchPos.y);

            Vector2 mouseRelativePlayer = new Vector2(cursor.getX() - getX(), cursor.getY() - getY());
            float anglePlayer = mouseRelativePlayer.angle();

            setRotation(anglePlayer);
            body.setTransform(body.getPosition().x, body.getPosition().y, anglePlayer / MainGame.PPM * 2);
        }
        Vector2 v = new Vector2(hud.touchpad.getKnobPercentX(), hud.touchpad.getKnobPercentY());
        v.x = v.x / 10;
        v.y = v.y / 10;
        body.applyLinearImpulse(v , body.getWorldCenter(), true);
        body.setLinearDamping(2f);

        gunBase.updateGun(dt, this, world);

        Packet.Packet6MovePlayer msg = new Packet.Packet6MovePlayer();
        msg.x = (int)body.getPosition().x;
        msg.y = (int)body.getPosition().y;

        MainGame.playerClient.client.sendTCP(msg);
    }

    private void setupBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(128 / MainGame.PPM, 128 / MainGame.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(25 / MainGame.PPM);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
    }
}
