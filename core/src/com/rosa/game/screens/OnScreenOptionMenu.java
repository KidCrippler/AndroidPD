package com.rosa.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class OnScreenOptionMenu {


    public void update(float dt) {

        System.out.println("OnScreenOptionMenu");

        handleInput();
    }


    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            GameScreen.FRAME_GAME_STATE = GameScreen.GAME_RUNNING;
        }
    }
}
