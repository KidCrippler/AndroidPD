package com.rosa.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MenuScreen implements Screen {

    private Skin skin;
    private Stage stage;
    private MapScreen mapScreen = new MapScreen();
    private OptionsScreen optionsScreen = new OptionsScreen();
    private CreditsScreen creditsScreen = new CreditsScreen();
    private Image bg;
    private Button startButton;
    private Button optionsButton;
    private Button creditsButton;
    private Button facebookButton;

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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
