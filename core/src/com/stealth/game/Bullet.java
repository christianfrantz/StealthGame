package com.stealth.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.stealth.game.Sprites.Player;

/**
 * Created by imont_000 on 11/12/2016.
 */

public class Bullet {
    public Vector2 position;
    public Vector2 velocity;
    public boolean isActive;
    public Body body;
    public float bulletTimer;
    public float bulletActiveTime;

    public Bullet (Player player, World world){
        setupBody(player, world);
        bulletActiveTime = 1f;
    }

    public void update(float dt, Player player){
        if(isActive){
            body.applyLinearImpulse(new Vector2(player.cursor.getX(), player.cursor.getY()), body.getWorldCenter(), true);
            bulletTimer += dt;
        }
        if(bulletTimer >= bulletActiveTime) {
            isActive = false;
            Destroy(this);
        }
    }

    public void setupBody(Player player, World world){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(player.body.getPosition());
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / MainGame.PPM);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
    }

    private void Destroy(Bullet bullet){
        bullet.body.setActive(false);
    }
}
