package com.rosa.game.screens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class ScreenHud extends Sprite {

    ShapeRenderer sr;

    public ScreenHud(ScreenPlay screen) {
        sr = new ShapeRenderer();
    }

    public void draw(Batch batch) {
        sr.end();
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setProjectionMatrix(batch.getProjectionMatrix());
        sr.setColor(23,23,23,23);
        sr.rect(40,650,300,40);

    }

    public void update(float dt) {
    }


}