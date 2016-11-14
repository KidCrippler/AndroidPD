package com.rosa.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.rosa.game.Application;


public class GameScreen extends ScreenAdapter {

    public static final int GAME_READY = 0;
    public static final int GAME_RUNNING = 1;
    public static final int GAME_PAUSED = 2;
    public static final int GAME_LEVEL_END = 3;
    public static final int GAME_OVER = 4;
    public static int FRAME_GAME_STATE;

    public enum GameState { READY, RUNNING, PAUSED, GAMEOVER }
    public GameState state = GameState.READY;

    Application game;

    public GameScreen(Application game) {
        this.game = game;
        FRAME_GAME_STATE = GAME_READY;
    }


    public void update(float dt) {
        if (dt > 0.1f)
            dt = 0.1f;

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
    }

    private void updateReady () {
    }

    private void updateRunning(float dt) {
    }
}
