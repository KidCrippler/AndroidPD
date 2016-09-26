package com.rosa.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.rosa.game.AndroidJDEV;
import com.rosa.game.Sprites.Brick;
import com.rosa.game.Sprites.Bullet;
import com.rosa.game.Sprites.Enemy;
import com.rosa.game.Sprites.InteractiveTileObject;
import com.rosa.game.Sprites.Item;
import com.rosa.game.Sprites.Player;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();


        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {

//            case AndroidJDEV.PLAYER_BIT | AndroidJDEV.COIN_BIT:
//                System.out.println("touch");

            case AndroidJDEV.PLAYER_HEAD_BIT | AndroidJDEV.BRICK_BIT:
            case AndroidJDEV.PLAYER_HEAD_BIT | AndroidJDEV.COIN_BIT:
                if (fixA.getFilterData().categoryBits == AndroidJDEV.PLAYER_HEAD_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit((Player) fixA.getUserData());
                else
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit((Player) fixB.getUserData());
                break;
            case AndroidJDEV.ENEMY_HEAD_BIT | AndroidJDEV.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == AndroidJDEV.ENEMY_HEAD_BIT)
                    ((Enemy) fixA.getUserData()).hitOnHead((Player) fixB.getUserData());
                else
                    ((Enemy) fixB.getUserData()).hitOnHead((Player) fixA.getUserData());
                break;
            case AndroidJDEV.ENEMY_BIT | AndroidJDEV.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == AndroidJDEV.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case AndroidJDEV.PLAYER_BIT | AndroidJDEV.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == AndroidJDEV.PLAYER_BIT)
                    ((Player) fixA.getUserData()).hit((Enemy) fixB.getUserData());
                else
                    ((Player) fixB.getUserData()).hit((Enemy) fixA.getUserData());
                break;
            case AndroidJDEV.ENEMY_BIT | AndroidJDEV.ENEMY_BIT:
                ((Enemy) fixA.getUserData()).hitByEnemy((Enemy) fixB.getUserData());
                ((Enemy) fixB.getUserData()).hitByEnemy((Enemy) fixA.getUserData());
                break;
            case AndroidJDEV.ITEM_BIT | AndroidJDEV.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == AndroidJDEV.ITEM_BIT)
                    ((Item) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Item) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case AndroidJDEV.ITEM_BIT | AndroidJDEV.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == AndroidJDEV.ITEM_BIT)
                    ((Item) fixA.getUserData()).use((Player) fixB.getUserData());
                else
                    ((Item) fixB.getUserData()).use((Player) fixA.getUserData());
                break;
            case AndroidJDEV.FIREBALL_BIT | AndroidJDEV.GROUND_BIT:
                if (fixA.getFilterData().categoryBits == AndroidJDEV.FIREBALL_BIT)
                    ((Bullet) fixA.getUserData()).setToDestroy();
                else
                    ((Bullet) fixB.getUserData()).setToDestroy();
                break;

            case AndroidJDEV.FIREBALL_BIT | AndroidJDEV.OBJECT_BIT | AndroidJDEV.PLAYER_BIT:
                if(fixA.getFilterData().categoryBits == AndroidJDEV.FIREBALL_BIT)
                    ((Bullet)fixA.getUserData()).setToDestroy();
                else
                    ((Bullet)fixB.getUserData()).setToDestroy();
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
