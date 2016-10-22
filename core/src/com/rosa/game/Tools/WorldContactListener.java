package com.rosa.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.rosa.game.AndroidJDEV;
import com.rosa.game.Sprites.Bob.Bullet;
import com.rosa.game.Sprites.Enemies.Enemy;
import com.rosa.game.Sprites.Enemies.YamYam;
import com.rosa.game.Sprites.LevelsCreate.Item;

public class WorldContactListener implements ContactListener {

    SoundPlayer soundPlayer = new SoundPlayer();

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {

            //      *       *       *       ENEMY     *       *       *       //
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

            case AndroidJDEV.ENEMY_HEAD_BIT | AndroidJDEV.BOB_BIT:
                if (fixA.getFilterData().categoryBits == AndroidJDEV.ENEMY_HEAD_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;


            //      *       *       *       ENEMY-AI     *       *       *       //


            case AndroidJDEV.ENEMY_AI | AndroidJDEV.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == AndroidJDEV.ENEMY_AI) {
                    ((YamYam) fixA.getUserData()).jump();
                    System.out.println("jj");
                } else {
                    ((YamYam) fixB.getUserData()).jump();
                    System.out.println("jj");
                }
                break;

            case AndroidJDEV.ENEMY_AI | AndroidJDEV.ENEMY_AI:
                ((Enemy) fixA.getUserData()).hitByEnemy((Enemy) fixB.getUserData());
                ((Enemy) fixB.getUserData()).hitByEnemy((Enemy) fixA.getUserData());
                break;

            //      *       *       *       BOB     *       *       *       //
            case AndroidJDEV.BOB_BIT | AndroidJDEV.GROUND_BIT:
                if (fixA.getFilterData().categoryBits == AndroidJDEV.BOB_BIT)
                    soundPlayer.PlaySoundBob(1);
                else
                    soundPlayer.PlaySoundBob(1);
                break;
            case AndroidJDEV.BOB_BIT | AndroidJDEV.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == AndroidJDEV.BOB_BIT)
                    soundPlayer.PlaySoundBob(1);
                else
                    soundPlayer.PlaySoundBob(1);
                break;

            //      *       *       *       BULLETS     *       *       *       //
            case AndroidJDEV.BULLET_BIT | AndroidJDEV.GROUND_BIT:
                if (fixA.getFilterData().categoryBits == AndroidJDEV.BULLET_BIT) {
                    ((Bullet) fixA.getUserData()).setToDestroy();
                    soundPlayer.playSoundRandomLaserOneWall();
                } else {
                    ((Bullet) fixB.getUserData()).setToDestroy();
                    soundPlayer.playSoundRandomLaserOneWall();
                }
                break;

            case AndroidJDEV.BULLET_BIT | AndroidJDEV.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == AndroidJDEV.BULLET_BIT) {
                    ((Bullet) fixA.getUserData()).setToDestroy();
                    soundPlayer.playSoundRandomLaserOneWall();
                } else {
                    ((Bullet) fixB.getUserData()).setToDestroy();
                    soundPlayer.playSoundRandomLaserOneWall();

                }
                break;
            //Enemy:
            case AndroidJDEV.BULLET_BIT | AndroidJDEV.ENEMY_HEAD_BIT:
                //Remove the enemy:
                if (fixA.getFilterData().categoryBits == AndroidJDEV.ENEMY_HEAD_BIT)
                    ((Enemy) fixA.getUserData()).setToDestroy();
                else
                    ((Enemy) fixB.getUserData()).setToDestroy();
                //Remove the bullet:
                if (fixA.getFilterData().categoryBits == AndroidJDEV.BULLET_BIT)
                    ((Bullet) fixA.getUserData()).setToDestroy();
                else
                    ((Bullet) fixB.getUserData()).setToDestroy();
                break;
            //Enemy AI:
            case AndroidJDEV.BULLET_BIT | AndroidJDEV.ENEMY_AI:
                //Remove the enemy:
                if (fixA.getFilterData().categoryBits == AndroidJDEV.ENEMY_AI)
                    ((Enemy) fixA.getUserData()).setToDestroy();
                else
                    ((Enemy) fixB.getUserData()).setToDestroy();
                //Remove the bullet:
                if (fixA.getFilterData().categoryBits == AndroidJDEV.BULLET_BIT)
                    ((Bullet) fixA.getUserData()).setToDestroy();
                else
                    ((Bullet) fixB.getUserData()).setToDestroy();
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
