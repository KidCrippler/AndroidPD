package com.rosa.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.rosa.game.Application;
import com.rosa.game.Sprites.Bob.Bullet;
import com.rosa.game.Sprites.Enemies.EnemyUtils.Enemy;
import com.rosa.game.Sprites.Enemies.YamYam;

public class WorldCollisionListener implements ContactListener {

    SoundPlayer soundPlayer = new SoundPlayer();

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {

            //      *       *       *       ENEMY     *       *       *       //
            case Application.ENEMY_DUMB_BIT | Application.WALL_BIT:
                if (fixA.getFilterData().categoryBits == Application.ENEMY_DUMB_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;

            case Application.ENEMY_DUMB_BIT | Application.ENEMY_DUMB_BIT:
                ((Enemy) fixA.getUserData()).hitByEnemy((Enemy) fixB.getUserData());
                ((Enemy) fixB.getUserData()).hitByEnemy((Enemy) fixA.getUserData());
                break;

            //      *       *       *       ENEMY-AI     *       *       *       //


            case Application.ENEMY_AI_BIT | Application.ENEMY_AI_BIT:
                ((Enemy) fixA.getUserData()).hitByEnemy((Enemy) fixB.getUserData());
                ((Enemy) fixB.getUserData()).hitByEnemy((Enemy) fixA.getUserData());
                break;

            //      *       *       *       BOB     *       *       *       //
            case Application.BOB_BIT | Application.GROUND_BIT:
                if (fixA.getFilterData().categoryBits == Application.BOB_BIT)
                    soundPlayer.PlaySoundBob(1);
                else
                    soundPlayer.PlaySoundBob(1);
                break;
            case Application.BOB_BIT | Application.WALL_BIT:
                if (fixA.getFilterData().categoryBits == Application.BOB_BIT)
                    soundPlayer.PlaySoundBob(1);
                else
                    soundPlayer.PlaySoundBob(1);
                break;

            //      *       *       *       BULLETS     *       *       *       //
            case Application.BUN_BULLET_BIT | Application.GROUND_BIT:
                if (fixA.getFilterData().categoryBits == Application.BUN_BULLET_BIT) {
                    ((Bullet) fixA.getUserData()).setToDestroy();
                    soundPlayer.playSoundRandomLaserOneWall();
                } else {
                    ((Bullet) fixB.getUserData()).setToDestroy();
                    soundPlayer.playSoundRandomLaserOneWall();
                }
                break;

            case Application.BUN_BULLET_BIT | Application.WALL_BIT:
                if (fixA.getFilterData().categoryBits == Application.BUN_BULLET_BIT) {
                    ((Bullet) fixA.getUserData()).setToDestroy();
                    soundPlayer.playSoundRandomLaserOneWall();
                } else {
                    ((Bullet) fixB.getUserData()).setToDestroy();
                    soundPlayer.playSoundRandomLaserOneWall();

                }
                break;
            //Enemy:
            case Application.BUN_BULLET_BIT | Application.ENEMY_DUMB_BIT:
                //Remove the enemy:
                if (fixA.getFilterData().categoryBits == Application.ENEMY_DUMB_BIT)
                    ((Enemy) fixA.getUserData()).setToDestroy();
                else
                    ((Enemy) fixB.getUserData()).setToDestroy();
                //Remove the bullet:
                if (fixA.getFilterData().categoryBits == Application.BUN_BULLET_BIT)
                    ((Bullet) fixA.getUserData()).setToDestroy();
                else
                    ((Bullet) fixB.getUserData()).setToDestroy();
                break;
            //Enemy AI:
            case Application.BUN_BULLET_BIT | Application.ENEMY_AI_BIT:
                //Remove the enemy:
                if (fixA.getFilterData().categoryBits == Application.ENEMY_AI_BIT)
                    ((Enemy) fixA.getUserData()).setToDestroy();
                else
                    ((Enemy) fixB.getUserData()).setToDestroy();
                //Remove the bullet:
                if (fixA.getFilterData().categoryBits == Application.BUN_BULLET_BIT)
                    ((Bullet) fixA.getUserData()).setToDestroy();
                else
                    ((Bullet) fixB.getUserData()).setToDestroy();
                break;

            //      *       *       *       RAY_ONE - ONE    *       *       *       //
            case Application.RAY_ONE | Application.WALL_BIT:
                if (fixA.getFilterData().categoryBits == Application.RAY_ONE)
                    ((YamYam) fixA.getUserData()).jump();
                else
                    ((YamYam) fixB.getUserData()).jump();
                break;

            //      *       *       *       RAY_ONE - TWO    *       *       *       //
            case Application.RAY_TWO | Application.WALL_BIT:
                if (fixA.getFilterData().categoryBits == Application.RAY_TWO)
                    ((YamYam) fixA.getUserData()).reverseVelocity(true,false);
                else
                    ((YamYam) fixB.getUserData()).reverseVelocity(true,false);
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
