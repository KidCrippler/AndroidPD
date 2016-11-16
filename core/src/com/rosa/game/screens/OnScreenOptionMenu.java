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
    BitmapFont font = new BitmapFont(); //or use alex answer to use custom font


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
        System.out.println("1");

//        game.batch.setProjectionMatrix(orthographicCamera.combined);
//
//        game.batch.begin();

/*
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
*/

//        font.draw(game.batch, "Hello World!", 640, 104);
//        font.setColor(Color.RED );
//
//
//        game.batch.draw(ScreenAssets.pauseMenu, 160 - 192 / 2, 240 - 96 / 2, 192, 96);
//
//        game.batch.end();
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
