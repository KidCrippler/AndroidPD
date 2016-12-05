package com.rosa.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.rosa.game.Sprites.Bob.Player;

public class ScreenHud extends Sprite {

    private NinePatchDrawable loadingBarBackground;
    private NinePatchDrawable loadingBar;

    public ScreenHud() {
        TextureAtlas skinAtlas = new TextureAtlas(Gdx.files.internal("gdx_demo/data/uiskin.atlas"));
        NinePatch loadingBarBackgroundPatch = new NinePatch(skinAtlas.findRegion("default-round"), 1, 1, 1, 1);
        NinePatch loadingBarPatch = new NinePatch(skinAtlas.findRegion("default-round-down"), 1, 1, 1, 1);



        loadingBar = new NinePatchDrawable(loadingBarPatch);
        loadingBarBackground = new NinePatchDrawable(loadingBarBackgroundPatch);
    }

    public void update(float dt) {}

    public void draw(Batch batch) {
        loadingBarBackground.draw(batch, 0.4f, 0.4f, 0.4f,0.4f);
        loadingBar.draw(batch, 0.004f, 0.004f, Player.bob_health,0.004f);
    }

}