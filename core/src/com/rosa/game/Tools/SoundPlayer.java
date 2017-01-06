package com.rosa.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import java.util.ArrayList;
import java.util.Random;


public class SoundPlayer {

    float masterVolume = 0.30f;

    public void playSoundPlayer(int soundNumber) {
        ArrayList<String> soundString = new ArrayList();
        soundString.add(0, "sounds/audio/bob/jump.wav");
        soundString.add(1, "sounds/audio/bob/land.wav");
        soundString.add(2, "sounds/audio/bob/death1.wav");
        soundString.add(3, "sounds/audio/bob/death2.wav");

        try {
            Sound soundFXStream = Gdx.audio.newSound(Gdx.files.internal(soundString.get(soundNumber)));
            soundFXStream.play(masterVolume);
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
            soundFXStream.play(masterVolume);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSoundRandomLaserOneWall() {
        Random randomFromArray;
        randomFromArray = new Random();
        int indexSound = randomFromArray.nextInt(3);
        ArrayList<String> soundStringBunsRandom = new ArrayList();
        soundStringBunsRandom.add(0, "sounds/audio/bob/pluckWall1.wav");
        soundStringBunsRandom.add(1, "sounds/audio/bob/pluckWall2.wav");
        soundStringBunsRandom.add(2, "sounds/audio/bob/pluckWall3.wav");
        try {
            Sound soundFXStream = Gdx.audio.newSound(Gdx.files.internal(soundStringBunsRandom.get(indexSound)));
            soundFXStream.play(masterVolume);
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
            soundFXStream.play(masterVolume);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSoundRandomBunDead() {
        Random randomFromArray;
        randomFromArray = new Random();
        int indexSound = randomFromArray.nextInt(2);
        ArrayList<String> soundStringBunsRandom = new ArrayList();
        soundStringBunsRandom.add(0, "sounds/audio/buns/bun-die1.wav");
        soundStringBunsRandom.add(1, "sounds/audio/buns/bun-die2.wav");
        try {
            Sound soundFXStream = Gdx.audio.newSound(Gdx.files.internal(soundStringBunsRandom.get(indexSound)));
            soundFXStream.play(masterVolume);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSoundRandomYamYamFirePower() {
        Random randomFromArray;
        randomFromArray = new Random();
        int indexSound = randomFromArray.nextInt(1);
        ArrayList<String> soundStringBunsRandom = new ArrayList();
        soundStringBunsRandom.add(0, "sounds/audio/yamyam/yamyam_firepower.wav");
        try {
            Sound soundFXStream = Gdx.audio.newSound(Gdx.files.internal(soundStringBunsRandom.get(indexSound)));
            soundFXStream.play(masterVolume);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
