package com.rosa.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import java.util.ArrayList;

public class SoundPlayer {

    public void playSound(int soundNumber) {

        ArrayList<String> soundString = new ArrayList();
        soundString.add(0, "sounds/audio/jump.wav");
        soundString.add(1, "sounds/audio/Laser_Shoot.wav");

        try {

            Sound soundFXStream = Gdx.audio.newSound(Gdx.files.internal(soundString.get(soundNumber)));
            soundFXStream.play();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
