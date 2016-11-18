package com.rosa.game.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.rosa.game.Application;

import javax.swing.text.View;

public class OnScreenOptionMenu implements Screen {

    OrthographicCamera orthographicCamera;
    Application game;
    BitmapFont font = new BitmapFont();

    public OnScreenOptionMenu(){
        BitmapFont font;
        create();
    }

    public void create(){
    }



    public void update(float dt) {
        handleInput();
        System.out.println("OnScreenOptionMenu");
    }


    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            GameScreen.FRAME_GAME_STATE = GameScreen.GAME_RUNNING;
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        System.out.println("render Onscreen keyboard");

        game.batch.begin();
        font.setColor(Color.RED);
        font.draw(game.batch, "hello", 12,12);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
