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
import com.rosa.game.Scenes.Hud;
import com.rosa.game.Sprites.Enemies.EnemyUtils.Enemy;
import com.rosa.game.Sprites.Bob.Player;
import com.rosa.game.Tools.BoxWorldCreator;
import com.rosa.game.Tools.Controller;
import com.rosa.game.Tools.WorldCollisionListener;

public class PlayScreen implements Screen {

    private Application game;
    private TextureAtlas atlas;
    private OrthographicCamera orthographicCamera;
    private Viewport gamePort;
    private Hud hud;
    private MenuPaused playscreenmenu;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Player player;
    private Controller controller;
    private World world;
    private Box2DDebugRenderer b2dr;
    private BoxWorldCreator creator;


    public PlayScreen(Application game) {
        GameScreen.FRAME_GAME_STATE = GameScreen.GAME_RUNNING;

        this.game = game;

        atlas = new TextureAtlas("style/ingame/figure/bob/bob.pack");
        orthographicCamera = new OrthographicCamera();
        gamePort = new FitViewport(Application.V_WIDTH / Application.PPM, Application.V_HEIGHT / Application.PPM, orthographicCamera);
        hud = new Hud(game.batch);
        playscreenmenu = new MenuPaused(game.batch);

        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load("style/ingame/level/tmap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Application.PPM);
        orthographicCamera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        world = new World(new Vector2(0, -10), true);
        creator = new BoxWorldCreator(this);
        b2dr = new Box2DDebugRenderer();
        player = new Player(world, this);
        world.setContactListener(new WorldCollisionListener());
        controller = new Controller();
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }

    public void handleInputController() {

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

    public void handleInput() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.jump();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
            player.fire();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            GameScreen.FRAME_GAME_STATE = GameScreen.GAME_PAUSED;
        }
    }

    public void update(float dt) {

        handleInput();
        handleInputController();

        world.step(1 / 60f, 6, 2);

        player.update(dt);
        hud.update(dt);
        creator.update(dt);

        //Load objects around the points of player:
        for (Enemy enemy : creator.getEnemies()) {
            enemy.update(dt);

            if (((player.getX() - (380 / Application.PPM)) <= enemy.getX()) && ((player.getX() + (380 / Application.PPM)) >= enemy.getX())) {
                enemy.b2body.setActive(true);
//                enemy.b2bodyRay.setActive(true);
            } else {
                enemy.b2body.setActive(true);
//                enemy.b2bodyRay.setActive(true);
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

        game.batch.setProjectionMatrix(orthographicCamera.combined);

        //Clear screen:
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Map render:
        renderer.render();

        //debug line:
        b2dr.render(world, orthographicCamera.combined);

        game.batch.setProjectionMatrix(orthographicCamera.combined);
        game.batch.begin();

        player.draw(game.batch);

        //End Batch

        //HUD:
        //Menu Active when pasue state:
        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        controller.draw();

        if (GameScreen.FRAME_GAME_STATE == GameScreen.GAME_PAUSED) {
//            game.batch.setProjectionMatrix(playscreenmenu.stage.getCamera().combined);
            controller.draw();
            playscreenmenu.render(dt);
            playscreenmenu.show();
            playscreenmenu.stage.draw();
        }

        //Game stop  state:
        if (GameScreen.FRAME_GAME_STATE == GameScreen.GAME_RUNNING) {
            update(dt);
        }
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

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }
}
