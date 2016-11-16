package com.rosa.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by rosa on 11/17/16.
 */

public class ScreenAssets {
        public static Texture background;
        public static TextureRegion backgroundRegion;

        public static Texture items;
        public static TextureRegion mainMenu;
        public static TextureRegion pauseMenu;
        public static TextureRegion ready;
        public static TextureRegion gameOver;
        public static TextureRegion logo;
        public static TextureRegion soundOn;
        public static TextureRegion soundOff;
        public static TextureRegion arrow;
        public static TextureRegion pause;
        public static TextureRegion spring;

        public static BitmapFont font;

        public static Music music;
        public static Sound jumpSound;
        public static Sound highJumpSound;
        public static Sound hitSound;
        public static Sound coinSound;
        public static Sound clickSound;

        public static void load () {

            pauseMenu = new TextureRegion(items, 224, 128, 192, 96);

            font = new BitmapFont(Gdx.files.internal("data/font.fnt"), Gdx.files.internal("data/font.png"), false);

            music = Gdx.audio.newMusic(Gdx.files.internal("data/music.mp3"));
            music.setLooping(true);
            music.setVolume(0.5f);
//            if (Settings.soundEnabled) music.play();
            jumpSound = Gdx.audio.newSound(Gdx.files.internal("data/jump.wav"));
            highJumpSound = Gdx.audio.newSound(Gdx.files.internal("data/highjump.wav"));
            hitSound = Gdx.audio.newSound(Gdx.files.internal("data/hit.wav"));
            coinSound = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));
            clickSound = Gdx.audio.newSound(Gdx.files.internal("data/click.wav"));
        }
//
//        public static void playSound (Sound sound) {
//            if (Settings.soundEnabled) sound.play(1);
//        }
}
