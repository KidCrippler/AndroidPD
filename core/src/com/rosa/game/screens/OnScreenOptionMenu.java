package com.rosa.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rosa.game.Application;
import com.rosa.game.Sprites.Bob.Player;

public class OnScreenOptionMenu extends Sprite implements Screen {

    Application game;
    BitmapFont font = new BitmapFont();
    public Stage stage;
    public Viewport viewport;
    private Label levelLabel;
    Batch batch;

    public OnScreenOptionMenu(PlayScreen screen,Batch batch){
        this.batch = batch;

        BitmapFont font;
        create();
        viewport = new FitViewport(Application.V_WIDTH, Application.V_HEIGHT, new OrthographicCamera());



        Table table = new Table();
        table.top();
        table.right();
        table.setFillParent(true);

        levelLabel = new Label("!!!3213213213123123123123131233123123!!", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(levelLabel);
        levelLabel.setSize(0.5f,0.5f);

        System.out.println("OnScreenOptionMenu");

    }

    public void create(){
    }



    public void update(float dt) {
        handleInput();
        render(dt);
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

//        game.batch.draw(ScreenAssets.pauseMenu, 160 - 192 / 2, 240 - 96 / 2, 192, 96);
//        ScreenAssets.font.draw(game.batch, "test", 16, 480 - 20);

        font.draw(game.batch, "hello", Player.BOB_X_POSITION, Player.BOB_Y_POSITION);
        font.setColor(Color.RED);

//        Gdx.gl.glClearColor(121, 134, 133, 5);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


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
        stage.dispose();
    }

    public void draw(Batch batch) {
//        super.draw(batch);
    }
}
