package com.stealth.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.stealth.game.MainGame;
import com.stealth.game.Sprites.Player;


import javax.print.DocFlavor;

/**
 * Created by imont_000 on 11/14/2016.
 */

public class PickClassScreen implements Screen{
    public MainGame mainGame;

    private Stage stage;
    private Viewport viewport;
    private Button basicButton;
    private Button camperButton;
    private Button stealthButton;
    private Button startButton;

    private String noneSelected;
    private String basicSelected;
    private String camperSelected;
    private String stealthSelected;

    private String serverConnected = "Connected to server";
    private String serverNotConnected = "Disconnected from server";

    private ButtonGroup buttonGroup;

    Label classSelectedLabel;
    Label serverStatusLabel;

    public PickClassScreen(MainGame game){
        this.mainGame = game;

        viewport = new FitViewport(640, 360, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);

        noneSelected = "Choose a class";
        basicSelected = "Basic selected";
        camperSelected = "Camper selected";
        stealthSelected = "Stealth selected";

        classSelectedLabel = new Label(noneSelected, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        serverStatusLabel = new Label(serverNotConnected, new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        final Texture button = new Texture("startButton.png");
        Button.ButtonStyle bs = new Button.ButtonStyle();
        bs.up = new TextureRegionDrawable(new TextureRegion(button));
        startButton = new Button(bs);

        Texture button1 = new Texture("basicButton.png");
        Button.ButtonStyle bs1 = new Button.ButtonStyle();
        bs1.up = new TextureRegionDrawable(new TextureRegion(button1));
        basicButton = new Button(bs1);

        Texture button2 = new Texture("camperButton.png");
        Button.ButtonStyle bs2 = new Button.ButtonStyle();
        bs2.up = new TextureRegionDrawable(new TextureRegion(button2));
        camperButton = new Button(bs2);

        Texture button3 = new Texture("stealthButton.png");
        Button.ButtonStyle bs3 = new Button.ButtonStyle();
        bs3.up = new TextureRegionDrawable(new TextureRegion(button3));
        stealthButton = new Button(bs3);

        buttonGroup = new ButtonGroup(basicButton, camperButton, stealthButton);
        buttonGroup.setMaxCheckCount(1);
        buttonGroup.setMinCheckCount(0);
        buttonGroup.setUncheckLast(true);
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        table.add(basicButton).expandX().padTop(10);
        table.add(stealthButton).expandX().padTop(10);
        table.add(camperButton).expandX().padTop(10);
        table.row();
        table.add(classSelectedLabel).expandX().padTop(10);
        table.add(serverStatusLabel).expandX().padTop(10);

        table.add(startButton).padBottom(10).expandX().expandY().bottom();

        startButton.addListener(new ClickListener(){
           @Override
            public void clicked(InputEvent event, float x, float y){
               if(buttonGroup.getChecked() == null)
                   return;
               else if(buttonGroup.getChecked() == camperButton){
                   mainGame.SetPlayScreen(Player.PlayerClass.CAMPER);
               }
               else if(buttonGroup.getChecked() == stealthButton){
                   mainGame.SetPlayScreen(Player.PlayerClass.STEALTH);
               }
               else if(buttonGroup.getChecked() == basicButton){
                   mainGame.SetPlayScreen(Player.PlayerClass.BASIC);
               }
           }
        });

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mainGame.batch.setProjectionMatrix(stage.getCamera().combined);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        switch (buttonGroup.getCheckedIndex()) {
            case 0:
                classSelectedLabel.setText(basicSelected);
                break;
            case 1:
                classSelectedLabel.setText(camperSelected);
                break;
            case 2:
                classSelectedLabel.setText(stealthSelected);
                break;
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
