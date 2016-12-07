package com.rosa.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rosa.game.Application;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rosa.game.Sprites.Bob.Player;

public class ScreenHud extends Sprite  {
    public Stage stage;
    public Viewport viewport;
    private Button resumeButton;
    private Skin skin;
    ProgressBarStyle barStyle;
    TextureRegionDrawable textureBar;
    ProgressBar bar;

    public ScreenHud(SpriteBatch sb) {
        viewport = new FitViewport(Application.V_WIDTH, Application.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        skin = new Skin(Gdx.files.internal("style/menu/design/mainmenu.json"), new TextureAtlas("style/menu/design/mainmenu.pack"));
//        textureBar = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("style/ingame/hud/health_bar.png"))));
        barStyle = new ProgressBarStyle(skin.newDrawable("startbutton", Color.DARK_GRAY), textureBar);
        barStyle.knobBefore = barStyle.knob;
        bar = new ProgressBar(0, Player.bob_health, 0.5f, false, barStyle);

    }

    public void show() {



        bar.setPosition(10, 10);
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