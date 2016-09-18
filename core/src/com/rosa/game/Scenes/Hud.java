package com.rosa.game.Scenes;

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
import com.rosa.game.AndroidJDEV;

public class Hud implements Disposable {

    public Stage stage;
    public Viewport viewport;

    private Label levelLabel;

    public Hud(SpriteBatch sb) {
        viewport = new FitViewport(AndroidJDEV.V_WIDTH, AndroidJDEV.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.right();
        table.setFillParent(true);

        levelLabel = new Label("Health", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(levelLabel);
        levelLabel.setSize(0.5f,0.5f);

        stage.addActor(table);
    }

    public void update(float dt) {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
