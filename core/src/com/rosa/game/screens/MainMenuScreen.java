package com.rosa.game.screens;

import com.badlogic.gdx.Game;
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
import com.rosa.game.Application;

public class MainMenuScreen implements Screen {


    private Skin skin;
    private Stage stage;
    private PlayScreen playScreen;
    private MapScreen mapScreen = new MapScreen(game);
    private OptionsScreen optionsScreen = new OptionsScreen(game,playScreen);
    private Image bg;
    private Button startButton;
    private Button optionsButton;
    private Button creditsButton;
    private static Application game;
    private MainMenuScreen mainMenuScreen;
    protected GameScreen gamescreen;

    public MainMenuScreen(Application game) {
        this.game = game;
        this.playScreen = playScreen;
        this.mainMenuScreen = mainMenuScreen;
    }

    @Override
    public void show() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("style/menu/mainmenu.json"), new TextureAtlas("style/menu/mainmenu.pack"));
        gamescreen = new GameScreen(game);

        bg = new Image(skin, "bg");
        startButton = new Button(skin, "startbutton");
        optionsButton = new Button(skin, "optionsbutton");
        creditsButton = new Button(skin, "creditsbutton");

        bg.setPosition(1, 1);
        startButton.setPosition(780, 500);
        optionsButton.setPosition(780, 430);
        creditsButton.setPosition(780, 360);

        //Maps:
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(mapScreen);
            }
        });

        //Credits: / back / resume
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(optionsScreen);
            }
        });
        creditsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("http://www.facebook.com");
            }
        });

        stage.addActor(bg);
        stage.addActor(startButton);
        stage.addActor(optionsButton);
        stage.addActor(creditsButton);

        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        update(dt);

    }

    public void update(float dt) {
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
