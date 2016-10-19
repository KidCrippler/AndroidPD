package com.rosa.game.Sprites.LevelsCreate;

import com.badlogic.gdx.maps.MapObject;
import com.rosa.game.AndroidJDEV;
import com.rosa.game.Sprites.Bob.Player;
import com.rosa.game.screens.PlayScreen;

public class Brick extends InteractiveTileObject {

    public Brick(PlayScreen screen, MapObject object){
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(AndroidJDEV.BRICK_BIT);
    }


}