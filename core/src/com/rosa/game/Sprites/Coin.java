package com.rosa.game.Sprites;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.rosa.game.AndroidJDEV;
import com.rosa.game.screens.PlayScreen;

public class Coin extends InteractiveTileObject{

    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28;

    public Coin(PlayScreen screen, MapObject object) {
        super(screen,object);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(AndroidJDEV.COIN_BIT);
    }



    @Override
    public void onHeadHit(Player userData) {
        setCategoryFilter(AndroidJDEV.COIN_BIT);
        if(getCell().getTile().getId() == BLANK_COIN){

        }
        getCell().setTile(tileSet.getTile(BLANK_COIN));
    }
}
