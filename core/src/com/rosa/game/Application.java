package com.rosa.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rosa.game.screens.ScreenPlay;
import com.rosa.game.screens.ScreenSplash;

public class Application extends Game {

    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
    public static final float PPM = 100;

    public static final short ITEM_BIT = 2;
    public static final short PLAYER_BIT = 4;
    public static final short ENEMY_AI_BIT = 8;
    public static final short ENEMY_DUMB_BIT = 16;
    public static final short BULLET_BIT = 64;
    public static final short ENEMY_BULLET_BIT = 128;
    public static final short RAY_C_JUMP_BIT = 256;
    public static final short WALL_BIT = 1024;
    public static final short GROUND_BIT = 2048;
    public static final short RAY_C_CLIMB_BIT = 4096;
    public static final short POTION_BIT = 8192;

    public static SpriteBatch batch;
    private ScreenSplash splashScreen;
    private ScreenPlay playScreen;


    @Override
    public void create() {
        batch = new SpriteBatch();
        playScreen = new ScreenPlay(this);
        setScreen(playScreen);
//        splashScreen = new ScreenSplash(this);
//        setScreen(splashScreen);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }
}
