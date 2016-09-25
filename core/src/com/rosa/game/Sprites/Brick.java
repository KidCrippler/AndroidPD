package com.rosa.game.Sprites;

import com.badlogic.gdx.maps.MapObject;
import com.rosa.game.AndroidJDEV;
import com.rosa.game.screens.PlayScreen;

public class Brick extends InteractiveTileObject {

    public Brick(PlayScreen screen, MapObject object){
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(AndroidJDEV.BRICK_BIT);
    }

    @Override
    public void onHeadHit(Player player) {
        System.out.println("brick");
            setCategoryFilter(AndroidJDEV.DESTROYED_BIT);
            getCell().setTile(null);
    }
}