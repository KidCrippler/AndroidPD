package com.rosa.game.Sprites;

import com.badlogic.gdx.Gdx;
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
    public void onHeadHit() {
       // setCategoryFilter(AndroidJDEV.COIN_BIT);

      /*  if(getCell().getTile().getId() == BLANK_COIN){
            AndroidJDEV.manager.get("sounds/audio/Laser_Shoot.wav");
        }else{
            AndroidJDEV.manager.get("sounds/audio/Laser_Shoot.wav");
        }*/

        getCell().setTile(tileSet.getTile(BLANK_COIN));
    }
}
