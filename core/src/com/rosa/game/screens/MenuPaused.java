package com.rosa.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Color;
import com.rosa.game.Application;
import com.rosa.game.Sprites.Bob.Player;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class MenuPaused extends Sprite implements Screen  {
    public Stage stage;
    public Viewport viewport;
    private Button resumeButton;
    private Skin skin;
    private Label levelLabel;
    Application game;

    public MenuPaused(SpriteBatch sb) {
        viewport = new FitViewport(Application.V_WIDTH, Application.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        skin = new Skin(Gdx.files.internal("style/menu/mainmenu.json"), new TextureAtlas("style/menu/mainmenu.pack"));

        Table table = new Table();
        stage.addActor(table);
        table.top();
        table.right();
        table.setFillParent(true);

        levelLabel = new Label("s2c", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(levelLabel);
        levelLabel.setSize(0.5f, 0.5f);

    }


    @Override
    public void show() {
        resumeButton = new Button(skin, "startbutton");
        resumeButton.setPosition(Player.BOB_X_POSITION,Player.BOB_Y_POSITION);

        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.FRAME_GAME_STATE = GameScreen.GAME_RUNNING;

            }
        });
        stage.addActor(resumeButton);
        Gdx.input.setInputProcessor(stage);

        game.batch.draw(ScreenAssets.pauseMenu, 160 - 192 / 2, 240 - 96 / 2, 192, 96);
        ScreenAssets.font.draw(game.batch, "1", 16, 480 - 20);


        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            GameScreen.FRAME_GAME_STATE = GameScreen.GAME_RUNNING;
        }

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