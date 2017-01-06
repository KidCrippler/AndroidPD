package com.rosa.game.screens;

import com.badlogic.gdx.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.Game;
import com.rosa.game.Application;

public class ScreenOptions implements Screen {

    ScreenMainGamePlay playScreen;
    private Skin skin;
    private Stage stage;
    private Image optionsScreen;
    private Button backButton;
    private Application game;

    public ScreenOptions(Application game, ScreenMainGamePlay playScreen) {
        this.game = game;
        this.playScreen = playScreen;
    }


    @Override
    public void show() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("mainmenu.json"), new TextureAtlas("mainmenu.pack"));
        final ScreenMainMenu mainMenuScreen = new ScreenMainMenu(game);

        optionsScreen = new Image(skin, "optionsscreen");
        backButton = new Button(skin, "backbutton");

        optionsScreen.setPosition(0.0f, 0.0f);
        backButton.setPosition(Gdx.graphics.getWidth() / 2 - 66 / 2f, Gdx.graphics.getHeight() / 2 - 340f);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(mainMenuScreen);
            }
        });

        stage.addActor(optionsScreen);
        stage.addActor(backButton);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
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
        skin.dispose();
        stage.dispose();
    }
}
