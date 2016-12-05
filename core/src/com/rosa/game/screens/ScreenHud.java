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
    private TextureAtlas skinAtlas;

    public ScreenHud() {

        skinAtlas = new TextureAtlas(Gdx.files.internal("style/ingame/hud/health_bar.atlas"));

        NinePatch loadingBarBackgroundPatch = new NinePatch(skinAtlas.findRegion("grey_bar"), 122, 122, 122, 122);
        NinePatch loadingBarPatch = new NinePatch(skinAtlas.findRegion("red_bar"), 122, 122, 122, 122);

        loadingBar = new NinePatchDrawable(loadingBarPatch);
        loadingBarBackground = new NinePatchDrawable(loadingBarBackgroundPatch);

    }

    public void update(float dt) {
    }

    public void draw(Batch batch) {
        loadingBarBackground.draw(batch, Player.BOB_X_POSITION, Player.BOB_Y_POSITION, 1222, 1222);
        loadingBar.draw(batch, Player.BOB_X_POSITION, Player.BOB_Y_POSITION, 1222, 1222);
    }

}