package com.rosa.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.rosa.game.AndroidJDEV;
import com.rosa.game.Sprites.Bob.Bullet;
import com.rosa.game.Sprites.Enemies.Enemy;
import com.rosa.game.Sprites.LevelsCreate.InteractiveTileObject;
import com.rosa.game.Sprites.Bob.Player;
import com.rosa.game.Sprites.LevelsCreate.Item;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();


        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {

            case AndroidJDEV.PLAYER_HEAD_BIT | AndroidJDEV.BRICK_BIT:

            case AndroidJDEV.PLAYER_HEAD_BIT | AndroidJDEV.COIN_BIT:
                if (fixA.getFilterData().categoryBits == AndroidJDEV.PLAYER_HEAD_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit((Player) fixA.getUserData());
                else
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit((Player) fixB.getUserData());
                break;

            case AndroidJDEV.BULLET_BIT | AndroidJDEV.GROUND_BIT:
                if (fixA.getFilterData().categoryBits == AndroidJDEV.BULLET_BIT)
                    ((Bullet) fixA.getUserData()).setToDestroy();
                else
                    ((Bullet) fixB.getUserData()).setToDestroy();
                break;

            case AndroidJDEV.ENEMY_BIT | AndroidJDEV.ENEMY_BIT:
                ((Enemy) fixA.getUserData()).hitByEnemy((Enemy) fixB.getUserData());
                ((Enemy) fixB.getUserData()).hitByEnemy((Enemy) fixA.getUserData());
                break;

            case AndroidJDEV.ENEMY_BIT | AndroidJDEV.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == AndroidJDEV.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;

            case AndroidJDEV.ENEMY_BIT | AndroidJDEV.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == AndroidJDEV.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;

            case AndroidJDEV.BULLET_BIT | AndroidJDEV.BULLET_BIT:

            case AndroidJDEV.BULLET_BIT | AndroidJDEV.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == AndroidJDEV.BULLET_BIT)
                    System.out.println("1");
//                    ((Bullet) fixA.getUserData()).setToDestroy();
                else
                    System.out.println("1");
//                    ((Bullet) fixB.getUserData()).setToDestroy();
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
