package com.rosa.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rosa.game.screens.PlayScreen;

public class AndroidJDEV extends Game {

    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
    public static final float PPM = 100;

    public static final short DEFAULT_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short BRICK_BIT = 4;
    public static final short COIN_BIT = 8;
    public static final short DESTROYED_BIT = 16;


    public SpriteBatch batch;

    public static AssetManager manager;

    @Override
    public void create() {

        batch = new SpriteBatch();
/*
        manager = new AssetManager();
        manager.load("sounds/music/jungle.mp3", Music.class);
        manager.load("sounds/audio/Laser_Shoot.wav", Sound.class);
        manager.load("sounds/audio/jump.wav", Sound.class);
        manager.load("sounds/audio/enemyDeath.wav", Sound.class);
        manager.finishLoading();*/


        setScreen(new PlayScreen(this));
    }

    @Override
    public void render() {
        super.render();
//        manager.update();
    }

    @Override
    public void dispose() {
        super.dispose();
      //  manager.dispose();
        batch.dispose();
    }
}
