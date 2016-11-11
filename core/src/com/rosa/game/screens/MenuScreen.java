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
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("mainmenu.json"),new TextureAtlas("mainmenu.pack"));

        bg = new Image(skin, "bg");
        startButton = new Button(skin, "startbutton");
        optionsButton = new Button(skin, "optionsbutton");
        creditsButton = new Button(skin, "creditsbutton");
        facebookButton = new Button(skin, "facebookbutton");

        bg.setPosition(10.5f, 78f);
        startButton.setPosition(Gdx.graphics.getWidth() /2 - 210/2f, Gdx.graphics.getHeight()/2 + 70f);
        optionsButton.setPosition(Gdx.graphics.getWidth() /2 - 210/2f, Gdx.graphics.getHeight()/2-10f );
        creditsButton.setPosition(Gdx.graphics.getWidth() /2 - 210/2f, Gdx.graphics.getHeight()/2-90f);
        facebookButton.setPosition(Gdx.graphics.getWidth() /2 - 337/2f, Gdx.graphics.getHeight()/2 - 170f);

        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ((Game)Gdx.app.getApplicationListener()).setScreen(mapScreen);
            }
        });
        optionsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ((Game)Gdx.app.getApplicationListener()).setScreen(optionsScreen);
            }
        });
        creditsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ((Game)Gdx.app.getApplicationListener()).setScreen(creditsScreen);
            }
        });
        facebookButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.net.openURI("http://www.facebook.com");
            }
        });

        stage.addActor(bg);
        stage.addActor(startButton);
        stage.addActor(optionsButton);
        stage.addActor(creditsButton);
        stage.addActor(facebookButton);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0x64/255.0f,0x95/255.0f,0xed/255.0f,0xff/255.0f);
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
