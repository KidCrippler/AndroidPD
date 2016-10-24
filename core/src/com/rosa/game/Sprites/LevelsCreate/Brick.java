package com.rosa.game.Sprites.LevelsCreate;

import com.badlogic.gdx.maps.MapObject;
import com.rosa.game.Application;
import com.rosa.game.screens.PlayScreen;

public class Brick extends InteractiveTileObject {

    public Brick(PlayScreen screen, MapObject object){
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(Application.BRICK_BIT);
    }


}