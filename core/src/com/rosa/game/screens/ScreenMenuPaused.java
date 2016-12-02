package com.rosa.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rosa.game.Application;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class ScreenMenuPaused extends Sprite implements Screen {
    public Stage stage;
    public Viewport viewport;
    private Button resumeButton;
    private Button quitButton;
    private Skin skin;
    private ScreenPlay playScreen;
    private Application game;

    public ScreenMenuPaused(SpriteBatch sb) {
        viewport = new FitViewport(Application.V_WIDTH, Application.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        skin = new Skin(Gdx.files.internal("style/menu/design/mainmenu.json"), new TextureAtlas("style/menu/design/mainmenu.pack"));
    }

    @Override
    public void show() {
        resumeButton = new Button(skin, "startbutton");
        resumeButton.setPosition(140, 100);
        resumeButton.setSize(30, 30);

        quitButton = new Button(skin, "quitbutton");
        quitButton.setPosition(100, 100);
        quitButton.setSize(30, 30);

        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenGame.FRAME_GAME_STATE = ScreenGame.GAME_RUNNING;
            }
        });

        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ScreenMainMenu(game));
            }
        });


        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            ScreenGame.FRAME_GAME_STATE = ScreenGame.GAME_RUNNING;
        }

        stage.addActor(resumeButton);
        stage.addActor(quitButton);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float dt) {
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
        stage.dispose();
    }

    public void draw(Batch batch) {
        super.draw(batch);
    }
}