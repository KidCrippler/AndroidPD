package com.rosa.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.rosa.game.AndroidJDEV;
import com.rosa.game.Sprites.Bob.Bullet;
import com.rosa.game.Sprites.Enemies.Enemy;
import com.rosa.game.Sprites.LevelsCreate.Item;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();


        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {

            case AndroidJDEV.ENEMY_BIT | AndroidJDEV.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == AndroidJDEV.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
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

            case AndroidJDEV.ENEMY_HEAD_BIT | AndroidJDEV.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == AndroidJDEV.ENEMY_HEAD_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;

            //BULLETS:

            case AndroidJDEV.BULLET_BIT | AndroidJDEV.GROUND_BIT:
                if (fixA.getFilterData().categoryBits == AndroidJDEV.BULLET_BIT)
                    ((Bullet) fixA.getUserData()).setToDestroy();
                else
                    ((Bullet) fixB.getUserData()).setToDestroy();
                break;

            case AndroidJDEV.BULLET_BIT | AndroidJDEV.ENEMY_HEAD_BIT:
                if (fixA.getFilterData().categoryBits == AndroidJDEV.BULLET_BIT) {
                    ((Bullet) fixA.getUserData()).setToDestroy();
                } else {
                    ((Bullet) fixB.getUserData()).setToDestroy();
                }
                break;

/*
TODO - Need to adjest the new collision settings,
TODO - when Enemy get a bullet, the bullet is gone and so is the Enemy.
TODO - After that is happed, Need to seet the Enemy HP var to 100 and then every shot he get he will lose 10 HP till he dies.
*/

            /*   case AndroidJDEV.BULLET_BIT | AndroidJDEV.ENEMY_HEAD_BIT:
                if (fixA.getFilterData().categoryBits == AndroidJDEV.BULLET_BIT)
                    ((Bullet) fixA.getUserData()).setToDestroy();
                else
                    ((Bullet) fixB.getUserData()).setToDestroy();
                break;
*/
/*
            case AndroidJDEV.ENEMY_HEAD_BIT | AndroidJDEV.BULLET_BIT:
                if (fixA.getFilterData().categoryBits == AndroidJDEV.ENEMY_HEAD_BIT) {
                    ((Enemy) fixA.getUserData()).hitOnHead((Bullet) fixB.getUserData());
                } else {
                    ((Enemy) fixB.getUserData()).hitOnHead((Bullet) fixA.getUserData());
                }
                break;
        }
*/
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
