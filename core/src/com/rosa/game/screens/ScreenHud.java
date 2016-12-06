package com.rosa.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.rosa.game.Sprites.Bob.Player;

public class ScreenHud extends Sprite {

    private ScreenPlay screen;
    private NinePatchDrawable loadingBarBackground;
    private NinePatchDrawable loadingBar;
    private TextureAtlas skinAtlas;

    public ScreenHud(ScreenPlay screen) {
        this.screen = screen;


        skinAtlas = new TextureAtlas(Gdx.files.internal("style/ingame/hud/health_bar.atlas"));


        NinePatch loadingBarBackgroundPatch = new NinePatch(skinAtlas.findRegion("grey_bar"), 1, 1, 93, 4);
//        NinePatch loadingBarPatch = new NinePatch(skinAtlas.findRegion("red_bar"), 1, 1, 93, 4);



        loadingBarBackground = new NinePatchDrawable(loadingBarBackgroundPatch);
//        loadingBar = new NinePatchDrawable(loadingBarPatch);

    }
    public void draw(Batch batch) {
        loadingBarBackground.draw(batch, Player.BOB_X_POSITION, Player.BOB_Y_POSITION, 0.93f, 0.4f);
//        loadingBar.draw(batch, Player.BOB_X_POSITION, Player.BOB_Y_POSITION, 93, 4);
        setSize(0.7f,0.7f);
        scale(-52222);
    }

    public void update(float dt) {
    }


}