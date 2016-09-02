package com.rosa.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.rosa.game.AndroidJDEV;
import com.badlogic.gdx.maps.MapObject;
import com.rosa.game.Scenes.Hud;
import com.rosa.game.screens.PlayScreen;


public class Brick extends InteractiveTileObject {



    public Brick(World world, TiledMap map, Rectangle bounds){

        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(AndroidJDEV.BRICK_BIT );

    }

    @Override
    public void onHeadHit() {
            setCategoryFilter(AndroidJDEV.DESTROYED_BIT);
            getCell().setTile(null);

    }

}