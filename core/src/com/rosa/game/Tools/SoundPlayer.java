package com.rosa.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import java.util.ArrayList;
import java.util.Random;


public class SoundPlayer {

    public void playSound(int soundNumber) {

        ArrayList<String> soundString = new ArrayList();
        //Bob:
        soundString.add(0, "sounds/audio/bob/jump.wav");
        try {
            Sound soundFXStream = Gdx.audio.newSound(Gdx.files.internal(soundString.get(soundNumber)));//Volume:
            soundFXStream.play(0.15f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSoundRandomLazerLaserShootOne() {
        Random randomFromArray;
        randomFromArray = new Random();
        int indexSound = randomFromArray.nextInt(3);
        ArrayList<String> soundStringBunsRandom = new ArrayList();
        soundStringBunsRandom.add(0, "sounds/audio/bob/Laser_Shoot1.wav");
        soundStringBunsRandom.add(1, "sounds/audio/bob/Laser_Shoot2.wav");
        soundStringBunsRandom.add(2, "sounds/audio/bob/Laser_Shoot3.wav");
        try {
            Sound soundFXStream = Gdx.audio.newSound(Gdx.files.internal(soundStringBunsRandom.get(indexSound)));
            soundFXStream.play(0.15f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void playSoundRandomLazerOneWall() {
        Random randomFromArray;
        randomFromArray = new Random();
        int indexSound = randomFromArray.nextInt(3);
        ArrayList<String> soundStringBunsRandom = new ArrayList();
        soundStringBunsRandom.add(0, "sounds/audio/bob/pluckWall1.wav");
        soundStringBunsRandom.add(1, "sounds/audio/bob/pluckWall2.wav");
        soundStringBunsRandom.add(2, "sounds/audio/bob/pluckWall3.wav");
        try {
            Sound soundFXStream = Gdx.audio.newSound(Gdx.files.internal(soundStringBunsRandom.get(indexSound)));
            soundFXStream.play(0.15f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSoundRandomBunHurt() {
        Random randomFromArray;
        randomFromArray = new Random();
        int indexSound = randomFromArray.nextInt(3);
        ArrayList<String> soundStringBunsRandom = new ArrayList();
        soundStringBunsRandom.add(0, "sounds/audio/buns/bun-ouch1.wav");
        soundStringBunsRandom.add(1, "sounds/audio/buns/bun-ouch2.wav");
        soundStringBunsRandom.add(2, "sounds/audio/buns/bun-ouch3.wav");
        soundStringBunsRandom.add(3, "sounds/audio/buns/bun-ouch4.wav");
        try {
            Sound soundFXStream = Gdx.audio.newSound(Gdx.files.internal(soundStringBunsRandom.get(indexSound)));
            soundFXStream.play(0.25f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSoundRandomBunDead() {
        Random randomFromArray;
        randomFromArray = new Random();
        int indexSound = randomFromArray.nextInt(3);
        ArrayList<String> soundStringBunsRandom = new ArrayList();
        soundStringBunsRandom.add(0, "sounds/audio/buns/bun-die1.wav");
        soundStringBunsRandom.add(1, "sounds/audio/buns/bun-die2.wav");
        try {
            Sound soundFXStream = Gdx.audio.newSound(Gdx.files.internal(soundStringBunsRandom.get(indexSound)));
            soundFXStream.play(0.15f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
