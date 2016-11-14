package com.stealth.game.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.stealth.game.Hud;
import com.stealth.game.MainGame;
import com.stealth.game.Sprites.Player;

import box2dLight.ConeLight;
import box2dLight.Light;
import box2dLight.RayHandler;

/**
 * Created by imont_000 on 10/29/2016.
 */

public class PlayScreen implements Screen {
    private MainGame game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer debugRenderer;

    private Player player;

    private RayHandler rayHandler;
    private Hud hud;

    public PlayScreen(MainGame game) {
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(game.vWidth / MainGame.PPM, game.vHeight / MainGame.PPM, gameCam);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MainGame.PPM);
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, 0), true);
        debugRenderer = new Box2DDebugRenderer();

        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0.01f, 0.01f, 0.01f, 0.01f);

        player = new Player(world, rayHandler);


        BodyDef def = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            def.type = BodyDef.BodyType.StaticBody;
            def.position.set((rect.getX() + rect.getWidth() / 2) / MainGame.PPM , (rect.getY() + rect.getHeight() / 2) / MainGame.PPM);

            body = world.createBody(def);

            shape.setAsBox(rect.getWidth() / 2 / MainGame.PPM, rect.getHeight() / 2 / MainGame.PPM);
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }



        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            Light light;
            light = new ConeLight(rayHandler, 32, Color.GOLD, 5, (rect.getX() + 32) / MainGame.PPM, (rect.getY() + 64) / MainGame.PPM, 270, 45);
            light.setActive(true);
            light.setStaticLight(true);
            light.setSoft(false);

        }
        player.playerLight.attachToBody(player.body);

        hud = new Hud(game.batch, player);

        switch (Gdx.app.getType()){
            case Android:
                game.playServices.signIn();
                break;
            case Desktop:
                break;
        }

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();


        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        player.draw(game.batch);
        player.cursor.draw(game.batch);
        game.batch.end();

        rayHandler.updateAndRender();
        rayHandler.setCombinedMatrix(gameCam.combined);

        debugRenderer.render(world, gameCam.combined);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.act(Gdx.graphics.getDeltaTime());
        hud.stage.draw();
    }

    public void update(float dt){
        world.step(1/60f, 6, 2);
        player.update(dt, gameCam, hud);
        gameCam.position.x = player.body.getPosition().x;
        gameCam.position.y = player.body.getPosition().y;
        gameCam.update();
        renderer.setView(gameCam);

        if(hud.lightSwitchButton.isChecked())
            player.playerLight.setActive(true);
        else
            player.playerLight.setActive(false);
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        hud.stage.getViewport().update(width, height);
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

    }
}
