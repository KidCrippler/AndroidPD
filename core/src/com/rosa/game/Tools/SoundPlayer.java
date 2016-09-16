package com.rosa.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SoundPlayer {

    private Sound soundFXStream;


    public void soundPlayer(int soundNumber) {


        ArrayList<String> soundString = new ArrayList();


        soundString.add(1,"sounds/audio/Laser_Shoot.wav");

        soundFXStream = Gdx.audio.newSound(Gdx.files.internal(soundString.get(1)));



        soundFXStream.play();
    }
}
