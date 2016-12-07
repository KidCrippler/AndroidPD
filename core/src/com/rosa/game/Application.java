package com.rosa.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rosa.game.Tools.Debbugerxy;
import com.rosa.game.screens.ScreenPlay;
import com.rosa.game.screens.ScreenSplash;

public class Application extends Game {

    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
    public static final float PPM = 100;

    //Collision:
    public static final short NOTHING_BIT = 0;
    public static final short BIT = 1;
    public static final short ITEM_BIT = 2;
    public static final short BOB_BIT = 4;
    public static final short ENEMY_AI_BIT = 8;
    public static final short ENEMY_DUMB_BIT = 16;
    public static final short BULLET_BIT = 64;
    public static final short ENEMY_BULLET_BIT = 128;
    public static final short RAY_ONE_OUTER = 256;
    public static final short RAY_TWO_INNER = 512;
    public static final short WALL_BIT = 1024;
    public static final short GROUND_BIT = 2048;

    //Debug
    public static int debbugerx;
    public static int debbugery;

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
