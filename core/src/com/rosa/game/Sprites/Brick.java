package com.rosa.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.rosa.game.AndroidJDEV;
import com.rosa.game.Scenes.Hud;

public class Brick extends InteractiveTileObject {

    public Brick(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCatagoryFilter(AndroidJDEV.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        setCatagoryFilter(AndroidJDEV.DESTROYED_BIT);
        Gdx.app.log("End Brick","");
        getCell().setTile(null);
        //AndroidJDEV.manager.get("sounds/audio/enemyDeath.wav", Sound.class).play();

    }
}
