package com.rosa.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SoundPlayer {

    public void soundPlayer(int soundNumber) {
        try {


            ArrayList<String> soundString = new ArrayList();


            soundString.add(0, "sounds/audio/jump.wav");
            soundString.add(1, "sounds/audio/Laser_Shoot.wav");


            Sound soundFXStream = Gdx.audio.newSound(Gdx.files.internal(soundString.get(soundNumber)));

            soundFXStream.play();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
