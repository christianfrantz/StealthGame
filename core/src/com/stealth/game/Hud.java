package com.stealth.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.stealth.game.Sprites.Player;

/**
 * Created by imont_000 on 10/31/2016.
 */

public class Hud implements Disposable {

    public Stage stage;
    private Viewport viewport;
    private Integer health;
    public String name;
    public Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin tSkin;
    private Drawable tBackground;
    private Drawable tStick;
    private Integer bulletInClip;
    private Integer totalBullet;

    public Button lightSwitchButton;

    Label PlayerName;
    Label PlayerHealth;
    Label BulletClip;
    Label TotalBullet;

    public Hud(SpriteBatch batch, Player player){
        Texture lightOnTex = new Texture("lightOn.png");
        Texture lightOffTex = new Texture("lightOff.png");

        name = "player";
        health = 100;
        bulletInClip = player.gunBase.clipSize;
        totalBullet = player.gunBase.maxBullets;

        viewport = new FitViewport(640, 360, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        PlayerName = new Label(name, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        PlayerHealth = new Label("Health: " + health, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        BulletClip = new Label(bulletInClip.toString(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        TotalBullet = new Label(totalBullet.toString(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        tSkin = new Skin();
        tSkin.add("touchBackground", new Texture("stickPad.png"));
        tSkin.add("touchKnob", new Texture("stick.png"));
        touchpadStyle = new Touchpad.TouchpadStyle();
        tBackground = tSkin.getDrawable("touchBackground");
        tStick = tSkin.getDrawable("touchKnob");
        touchpadStyle.background = tBackground;
        touchpadStyle.knob = tStick;
        touchpad = new Touchpad(10, touchpadStyle);
        touchpad.setBounds(0, 0, 64, 64);

        Button.ButtonStyle bs = new Button.ButtonStyle();
        bs.up = new TextureRegionDrawable(new TextureRegion(lightOffTex));
        bs.down = new TextureRegionDrawable(new TextureRegion(lightOnTex));
        bs.checked = new TextureRegionDrawable(new TextureRegion(lightOffTex));
        bs.disabled = new TextureRegionDrawable(new TextureRegion(lightOnTex));

        lightSwitchButton = new Button(bs);
        lightSwitchButton.setBounds(0, 0, 64, 64);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        table.add(PlayerName).expandX().padTop(10);
        table.add(PlayerHealth).expandX().padTop(10);
        table.row();
        table.add(BulletClip).expandX().padTop(10);
        table.add(TotalBullet).expandX().padTop(10);

        table.add(touchpad).padBottom(5f).expandX().expandY().bottom();
        table.add(lightSwitchButton).padBottom(5f).expandX().expandY().bottom();

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);

        /*lightSwitchButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(lightSwitchButton.isPressed()){
                    player.playerLight.setActive(false);
                }
            }
        });*/
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
