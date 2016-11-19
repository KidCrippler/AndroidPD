package com.rosa.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Color;
import com.rosa.game.Application;

public class PlayScreenMenu implements Disposable {
    public Stage stage;
    public Viewport viewport;

    private Label levelLabel;

    public PlayScreenMenu(SpriteBatch sb) {
        viewport = new FitViewport(Application.V_WIDTH, Application.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);


        Table table = new Table();
        table.top();
        table.right();
        table.setFillParent(true);

        levelLabel = new Label("s2c", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(levelLabel);
        levelLabel.setSize(0.5f, 0.5f);

        stage.addActor(table);
    }

    public void update(float dt) {

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            GameScreen.FRAME_GAME_STATE = GameScreen.GAME_RUNNING;
        }

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
