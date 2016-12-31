package com.rosa.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rosa.game.Application;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rosa.game.Sprites.Player.Player;

public class ScreenHud extends Sprite  {
    private Stage stage;
    private Viewport viewport;
    private Skin skin;
    private ProgressBarStyle barStyle;
    private TextureRegionDrawable textureBar;
    private ProgressBar bar;

    public ScreenHud(SpriteBatch sb) {
        viewport = new FitViewport(Application.V_WIDTH, Application.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        skin = new Skin(Gdx.files.internal("style/ingame/hud/mainmenu.json"), new TextureAtlas("style/ingame/hud/mainmenu.pack"));
        barStyle = new ProgressBarStyle(skin.newDrawable("red_bar", Color.RED),textureBar);
        barStyle.knobBefore = barStyle.knob;
        bar = new ProgressBar(0, Player.bob_health, 0.5f, false, barStyle);
    }

    public void show() {
        bar.setPosition(6,197);
        bar.setSize(Player.bob_health, bar.getPrefHeight());
        bar.setAnimateDuration(2);
        stage.addActor(bar);
    }

    public void render(float dt) {
        stage.act();
        stage.draw();
    }

    public void draw(Batch batch) {
        super.draw(batch);
    }
}