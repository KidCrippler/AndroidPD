package com.rosa.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.rosa.game.AndroidJDEV;

public class Coin extends InteractiveTileObject{

    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28;

    public Coin(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(AndroidJDEV.COIN_BIT);
    }



    @Override
    public void onHeadHit(Player userData) {

        System.out.println("touchCoin");
       // setCategoryFilter(AndroidJDEV.COIN_BIT);

        if(getCell().getTile().getId() == BLANK_COIN){

        }

        getCell().setTile(tileSet.getTile(BLANK_COIN));
    }
}
