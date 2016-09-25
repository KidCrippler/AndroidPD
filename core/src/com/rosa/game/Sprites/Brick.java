package com.rosa.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.rosa.game.AndroidJDEV;

public class Brick extends InteractiveTileObject {

    public Brick(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(AndroidJDEV.BRICK_BIT );
    }

    @Override
    public void onHeadHit(Player userData) {
        System.out.println("brick");
            setCategoryFilter(AndroidJDEV.DESTROYED_BIT);
            getCell().setTile(null);
    }
}