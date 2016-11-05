package com.rosa.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rosa.game.screens.PlayScreen;

public class Application extends Game {

    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
    public static final float PPM = 100;
    public static final short NOTHING_BIT = 0;
    public static final short GROUND_BIT = 1;
    public static final short BOB_BIT = 2;
    public static final short BRICK_BIT = 4;
    public static final short COIN_BIT = 8;
    public static final short ENEMY_AI = 16;
    public static final short WALL_BIT = 32;
    public static final short ENEMY_BIT = 64;
    public static final short ENEMY_HEAD_BIT = 128;
    public static final short ITEM_BIT = 256;
    public static final short BULLET_BIT = 512;
    public static final short ENEMY_FIREPOWER = 1024;
    public static final short RAY = 2048;

    public static SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new PlayScreen(this));
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
