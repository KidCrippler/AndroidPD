package com.rosa.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rosa.game.Application;
import com.rosa.game.Sprites.Enemies.EnemyUtils.ObjectManager;
import com.rosa.game.Sprites.Player.Player;
import com.rosa.game.Tools.BoxWorldCreator;
import com.rosa.game.Tools.KeysController;
import com.rosa.game.Tools.WorldCollisionListener;

public class ScreenMainGamePlay implements Screen {

    private Application game;
    private TextureAtlas atlas;
    private OrthographicCamera orthographicCamera;
    private Viewport gamePort;
    private ScreenHud hud;
    private ScreenMenuPaused menuPaused;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Player player;
    private KeysController controller;
    private World world;
    private Box2DDebugRenderer b2dr;
    private BoxWorldCreator creator;

    public ScreenMainGamePlay(Application game) {
        ScreenGame.FRAME_GAME_STATE = ScreenGame.GAME_RUNNING;
        this.game = game;
        atlas = new TextureAtlas("style/ingame/figure/bob/bob.pack");
        orthographicCamera = new OrthographicCamera();
        gamePort = new FitViewport(Application.V_WIDTH / Application.PPM, Application.V_HEIGHT / Application.PPM, orthographicCamera);
        menuPaused = new ScreenMenuPaused(game.batch);
        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load("style/ingame/level/tmap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Application.PPM);
        orthographicCamera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        world = new World(new Vector2(0, -10), true);
        creator = new BoxWorldCreator(this);
        b2dr = new Box2DDebugRenderer();
        hud = new ScreenHud(game.batch);
        player = new Player(world, this, game);
        world.setContactListener(new WorldCollisionListener());
        controller = new KeysController();
    }

    public void handleInputController() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.jump();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            player.fire();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            ScreenGame.FRAME_GAME_STATE = ScreenGame.GAME_PAUSED;
        }


//
//        if (controller.isUpPressed()) {
//            player.jump();
//        } else if (controller.isRightPressed()) {
//            player.goRight();
//        } else if (controller.isLeftPressed()) {
//            player.goLeft();
//        } else if (controller.isSpacePressed()) {
//                player.fire();
//        }
//
//        }
    }

//    public void handleInput() {
//        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
//            player.jump();
//
//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
//            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
//
//        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
//            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
//
//        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
//            player.fire();
//
//        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
//            ScreenGame.FRAME_GAME_STATE = ScreenGame.GAME_PAUSED;
//        }
//    }

    public void update(float dt) {
//
//        handleInput();
        handleInputController();
        world.step(1 / 60f, 6, 2);
        player.update(dt);
        creator.update(dt);
        //Load around player:
        for (ObjectManager enemy : creator.getEnemies()) {
            enemy.update(dt);

            if (((player.getX() - (380 / Application.PPM)) <= enemy.getX()) && ((player.getX() + (380 / Application.PPM)) >= enemy.getX())) {
                enemy.b2body.setActive(true);
            } else {
                enemy.b2body.setActive(false);
            }
        }

        if ((player.b2body.getPosition().x > 2) && (player.b2body.getPosition().x < 36.5)) {
            orthographicCamera.position.x = player.b2body.getPosition().x;
        }

        orthographicCamera.update();
        renderer.setView(orthographicCamera);
    }


    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        //debug line:
        b2dr.render(world, orthographicCamera.combined);
        hud.render(dt);
        game.batch.setProjectionMatrix(orthographicCamera.combined);

        game.batch.begin();
        creator.draw(game.batch);
        player.draw(game.batch);
        hud.show();
        game.batch.end();
        controller.draw();

        if (ScreenGame.FRAME_GAME_STATE == ScreenGame.GAME_RUNNING) {
            update(dt);
        }

        if (ScreenGame.FRAME_GAME_STATE == ScreenGame.GAME_PAUSED) {
            menuPaused.render(dt);
            menuPaused.stage.draw();
            game.batch.begin();
            menuPaused.show();
            game.batch.end();
        }
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        controller.resize(width, height);
    }

    @Override
    public void show() {

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
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public OrthographicCamera getOrthographicCamera(){
        return orthographicCamera;
    }
}
