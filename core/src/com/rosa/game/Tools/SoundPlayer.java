package com.rosa.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import java.util.ArrayList;
import java.util.Random;


public class SoundPlayer {

    public void playSound(int soundNumber) {

        ArrayList<String> soundString = new ArrayList();
        soundString.add(0, "sounds/audio/jump.wav");
        soundString.add(1, "sounds/audio/Laser_Shoot4.wav");
        soundString.add(2, "sounds/audio/pluckWall.wav");
        soundString.add(3, "sounds/audio/buns/bun-take-bullet.wav");
        soundString.add(4, "sounds/audio/buns/bun-die.wav");

        try {
            Sound soundFXStream = Gdx.audio.newSound(Gdx.files.internal(soundString.get(soundNumber)));//Volume:
            soundFXStream.play(0.15f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSoundRandom() {

        Random randomFromArray;
        randomFromArray = new Random();
        int indexSound = randomFromArray.nextInt(3);
        ArrayList<String> soundStringbunsRandom = new ArrayList();

        soundStringbunsRandom.add(0, "sounds/audio/buns/bun-ouch1.wav");
        soundStringbunsRandom.add(1, "sounds/audio/buns/bun-ouch2.wav");
        soundStringbunsRandom.add(2, "sounds/audio/buns/bun-ouch3.wav");
        soundStringbunsRandom.add(3, "sounds/audio/buns/bun-ouch4.wav");

        try {
            Sound soundFXStream = Gdx.audio.newSound(Gdx.files.internal(soundStringbunsRandom.get(indexSound)));
            soundFXStream.play(0.15f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
