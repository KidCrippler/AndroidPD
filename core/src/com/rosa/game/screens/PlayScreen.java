package com.rosa.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rosa.game.AndroidJDEV;
import com.rosa.game.Scenes.Hud;
import com.rosa.game.Sprites.Bullet;
import com.rosa.game.Sprites.Enemy;
import com.rosa.game.Sprites.Item;
import com.rosa.game.Sprites.Player;
import com.rosa.game.Tools.B2WorldCreator;
import com.rosa.game.Tools.Controller;
import com.rosa.game.Tools.SoundPlayer;
import com.rosa.game.Tools.WorldContactListener;

public class PlayScreen implements Screen {

    public static Batch batch;
    private AndroidJDEV game;
    private TextureAtlas atlas;

    //ScreenPlay:
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    //Map load:
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;


    private Player player;
    Controller controller;
    SoundPlayer playsound;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    //Items:
    private Bullet bullet;
    private Array<Item> items;


    //private Music music;

    public PlayScreen(AndroidJDEV game) {


        atlas = new TextureAtlas("keen_one.pack");
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(AndroidJDEV.V_WIDTH / AndroidJDEV.PPM, AndroidJDEV.V_HEIGHT / AndroidJDEV.PPM, gamecam);

        hud = new Hud(game.batch);


        playsound = new SoundPlayer();

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("tmap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / AndroidJDEV.PPM);

        //GameCam:
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);

        creator = new B2WorldCreator(this);


        b2dr = new Box2DDebugRenderer();


        //Start the player:
        player = new Player(world, this);

        world.setContactListener(new WorldContactListener());

        controller = new Controller();
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }




    public void handleInputControler() {

        if (controller.isUpPressed()) {
            player.jump();
        } else if (controller.isRightPressed()) {
            player.goRight();

        } else if (controller.isLeftPressed()) {
            player.goLeft();
        } else if (controller.isSpacePressed()) {
            player.fire();
        }
    }

    public void handleInput(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.jump();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            player.fire();
    }

    public void update(float dt) {
        //Handler user input
        handleInput(dt);
        handleInputControler();

        world.step(1 / 60f, 6, 2);

        //UPDATE CLASSES:
        player.update(dt);

        /*for(Enemy enemy : creator.getEnemies()) {
            enemy.update(dt);
            if(enemy.getX() < player.getX() + 224 / AndroidJDEV.PPM) {
                enemy.b2body.setActive(true);
            }
        }*/


        hud.update(dt);

        if((player.b2body.getPosition().x > 2) && (player.b2body.getPosition().x < 36.5)) {
            gamecam.position.x = player.b2body.getPosition().x;
        }


        gamecam.update();
        renderer.setView(gamecam);
    }


    @Override
    public void render(float delta) {
        update(delta);

        //Clear screen:
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Map render:
        renderer.render();

        //Render Box2D debug line:
        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);

        //End Batch
        game.batch.end();

        //HUD:
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        controller.draw();


    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        controller.resize(width, height);
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }

    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return world;
    }
}
