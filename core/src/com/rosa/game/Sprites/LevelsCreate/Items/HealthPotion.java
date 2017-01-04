package com.rosa.game.Sprites.LevelsCreate.Items;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.rosa.game.Application;
import com.rosa.game.Sprites.LevelsCreate.InteractiveTileObject;
import com.rosa.game.Sprites.Player.Player;
import com.rosa.game.screens.ScreenPlay;

public class HealthPotion extends InteractiveTileObject {
    public HealthPotion(ScreenPlay screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(Application.POTION_BIT);
    }

    public void onHeadHit(Player player) {
        System.out.println("Yami");
        setCategoryFilter(Application.DESTROYED_BIT);
        getCell().setTile(null);
        Player.PLAYER_TOTAL_HEALTH = +30;

    }
}