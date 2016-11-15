package com.rosa.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rosa.game.Application;
import com.rosa.game.Scenes.Hud;
import com.rosa.game.Sprites.Bob.Player;
import com.rosa.game.Tools.BoxWorldCreator;
import com.rosa.game.Tools.Controller;
import com.rosa.game.Tools.WorldCollisionListener;


public class GameScreen extends ScreenAdapter implements Screen {

    public static final int GAME_READY = 0;
    public static final int GAME_RUNNING = 1;
    public static final int GAME_PAUSED = 2;
    public static final int GAME_LEVEL_END = 3;
    public static final int GAME_OVER = 4;
    public static int FRAME_GAME_STATE;

    private TextureAtlas atlas;
    private OrthographicCamera orthographicCamera;
    private Viewport gamePort;
    private Hud hud;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Player player;
    private Controller controller;
    private Box2DDebugRenderer b2dr;
    private BoxWorldCreator creator;




    public enum GameState { READY, RUNNING, PAUSED, GAMEOVER }
    public GameState state = GameState.READY;

    Application game;
    OrthographicCamera guiCam;
    Vector3 touchPoint;
    World world;
    Rectangle pauseBounds;
    Rectangle resumeBounds;
    Rectangle quitBounds;
    int lastScore;
    String scoreString;
    MenuScreen menuScreen;
    PlayScreen playScreen;


    public GameScreen(Application game,MenuScreen menuScreen )  {
        this.game = game;
        this.menuScreen = menuScreen;
        this.playScreen = playScreen;


        FRAME_GAME_STATE = GAME_READY;
    }

    @Override
    public void render(float delta) {
        System.out.println("15");


    }


    public void update(float dt) {
        if (dt > 0.1f)
            dt = 0.1f;

        System.out.println("FRAME_GAME_STATE: " + FRAME_GAME_STATE);


        switch (FRAME_GAME_STATE) {
            case GAME_READY:
                updateReady();
                break;
            case GAME_RUNNING:
                updateRunning(dt);
                break;
            case GAME_PAUSED:
                updatePaused();
                break;
            case GAME_LEVEL_END:
                updateLevelEnd();
                break;
            case GAME_OVER:
                updateGameOver();
                break;
        }
    }

    private void updateGameOver() {
    }

    private void updateLevelEnd() {
    }

    private void updatePaused () {
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (resumeBounds.contains(touchPoint.x, touchPoint.y)) {
                FRAME_GAME_STATE = GAME_RUNNING;
                return;
            }

            if (quitBounds.contains(touchPoint.x, touchPoint.y)) {
                game.setScreen(new MenuScreen(game,playScreen));
                return;
            }
        }
    }

    private void updateReady () {
    }

    private void updateRunning(float deltaTime) {
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            
            //PAUSE:
            if (pauseBounds.contains(touchPoint.x, touchPoint.y)) {
                FRAME_GAME_STATE = GAME_PAUSED;
                System.out.println("1");
                return;
            }
        }

    }
}
