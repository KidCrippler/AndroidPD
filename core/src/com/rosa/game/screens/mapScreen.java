package com.rosa.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.Game;

public class MapScreen implements Screen {
    private Skin skin;
    private Stage stage;
    private Image levelScreen;
    private Button backButton;
    private Button map1;

    @Override
    public void show() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("style/menu/mainmenu.json"), new TextureAtlas("style/menu/mainmenu.pack"));
        final MenuScreen menuScreen = new MenuScreen();
        final PlayScreen playScreen = new PlayScreen();

        levelScreen = new Image(skin, "levelscreen");
        backButton = new Button(skin, "backbutton");
        map1 = new Button(skin, "map1");


        levelScreen.setPosition(0.0f, 0.0f);
        backButton.setPosition(1, 1);
        map1.setPosition(510, 410);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(menuScreen);
            }
        });

        map1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(playScreen);
            }
        });


        stage.addActor(levelScreen);
        stage.addActor(backButton);
        stage.addActor(map1);

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
