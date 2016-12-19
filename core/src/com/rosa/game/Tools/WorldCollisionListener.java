package com.rosa.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.rosa.game.Application;
import com.rosa.game.Sprites.Bob.Bullet;
import com.rosa.game.Sprites.Bob.Player;
import com.rosa.game.Sprites.Enemies.EnemyUtils.Enemy;
import com.rosa.game.Sprites.Enemies.AIYamYam;
import com.rosa.game.Sprites.Enemies.EnemyUtils.EnemyBullet;

public class WorldCollisionListener implements ContactListener {

    SoundPlayer soundPlayer = new SoundPlayer();

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {

            //Enemy Dumb
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

            case Application.ENEMY_DUMB_BIT | Application.BOB_BIT:
                if (fixA.getFilterData().categoryBits == Application.ENEMY_DUMB_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;



            //Enemy Ai
            case Application.ENEMY_AI_BIT | Application.ENEMY_AI_BIT:
                ((Enemy) fixA.getUserData()).hitByEnemy((Enemy) fixB.getUserData());
                ((Enemy) fixB.getUserData()).hitByEnemy((Enemy) fixA.getUserData());
                break;

            //Bob
            case Application.BOB_BIT | Application.GROUND_BIT:
                if (fixA.getFilterData().categoryBits == Application.BOB_BIT)
                    soundPlayer.playSoundBob(1);
                else
                    soundPlayer.playSoundBob(1);
                break;

            case Application.BOB_BIT | Application.WALL_BIT:
                if (fixA.getFilterData().categoryBits == Application.BOB_BIT)
                    soundPlayer.playSoundBob(1);
                else
                    soundPlayer.playSoundBob(1);
                break;

            //Bullets
            case Application.BULLET_BIT | Application.WALL_BIT:
                if (fixA.getFilterData().categoryBits == Application.BULLET_BIT) {
                    ((Bullet) fixA.getUserData()).setToDestroy();
                    soundPlayer.playSoundRandomLaserOneWall();
                } else {
                    ((Bullet) fixB.getUserData()).setToDestroy();
                    soundPlayer.playSoundRandomLaserOneWall();

                }
                break;

            case Application.ENEMY_BULLET_BIT | Application.WALL_BIT:
                if (fixA.getFilterData().categoryBits == Application.ENEMY_BULLET_BIT) {
                    ((EnemyBullet) fixA.getUserData()).setToDestroy();
                    soundPlayer.playSoundRandomLaserOneWall();
                } else {
                    ((EnemyBullet) fixB.getUserData()).setToDestroy();
                    soundPlayer.playSoundRandomLaserOneWall();
                }
                break;

            //Bullet fire at BOB:
            case Application.ENEMY_BULLET_BIT | Application.BOB_BIT:
                if (fixA.getFilterData().categoryBits == Application.BOB_BIT)
                    ((Player) fixA.getUserData()).setHpDown(10);
                else
                    ((Player) fixB.getUserData()).setHpDown(10);
                //Remove the bullet:
                if (fixA.getFilterData().categoryBits == Application.ENEMY_BULLET_BIT)
                    ((EnemyBullet) fixA.getUserData()).setToDestroy();
                else
                    ((EnemyBullet) fixB.getUserData()).setToDestroy();
                break;

            //Bullet fire at Enemy:
            case Application.BULLET_BIT | Application.ENEMY_DUMB_BIT:
                //Remove the enemy:
                if (fixA.getFilterData().categoryBits == Application.ENEMY_DUMB_BIT)
                    ((Enemy) fixA.getUserData()).takeShot(10);
                else
                    ((Enemy) fixB.getUserData()).takeShot(10);
                //Remove the bullet:
                if (fixA.getFilterData().categoryBits == Application.BULLET_BIT)
                    ((Bullet) fixA.getUserData()).setToDestroy();
                else
                    ((Bullet) fixB.getUserData()).setToDestroy();
                break;

            //Enemy AI:
            case Application.BULLET_BIT | Application.ENEMY_AI_BIT:
                //Remove the enemy:
                if (fixA.getFilterData().categoryBits == Application.ENEMY_AI_BIT)
                    ((Enemy) fixA.getUserData()).takeShot(10);
                else
                    ((Enemy) fixB.getUserData()).takeShot(10);
                //Remove the bullet:
                if (fixA.getFilterData().categoryBits == Application.BULLET_BIT)
                    ((Bullet) fixA.getUserData()).setToDestroy();
                else
                    ((Bullet) fixB.getUserData()).setToDestroy();
                break;

            //RAY_JUMP:
            case Application.RAY_JUMP | Application.WALL_BIT:
                if (fixA.getFilterData().categoryBits == Application.RAY_JUMP)
                    ((AIYamYam) fixA.getUserData()).jump();
                else
                    ((AIYamYam) fixB.getUserData()).jump();
                break;

            //RAY_BULLET_FIRE:
            case Application.RAY_BULLET | Application.BOB_BIT:
                if (fixA.getFilterData().categoryBits == Application.RAY_BULLET)
                    ((AIYamYam) fixA.getUserData()).isPlayerAtRangeOfFire(true);
                else
                    ((AIYamYam) fixA.getUserData()).isPlayerAtRangeOfFire(false);
                break;

            //RAY_TWO_INNER:
            case Application.RAY_TWO_INNER | Application.WALL_BIT:
                if (fixA.getFilterData().categoryBits == Application.RAY_TWO_INNER)
                    ((AIYamYam) fixA.getUserData()).setRayTwoNextToWall(false);
                else
                    ((AIYamYam) fixB.getUserData()).setRayTwoNextToWall(true);
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            //RAY_TWO_INNER:
            case Application.RAY_TWO_INNER | Application.WALL_BIT:
                if (fixA.getFilterData().categoryBits == Application.RAY_TWO_INNER)
                    ((AIYamYam) fixA.getUserData()).setRayTwoNextToWall(true);
                else
                    ((AIYamYam) fixB.getUserData()).setRayTwoNextToWall(false);
                break;

            //RAY_BULLET_FIRE:
            case Application.RAY_BULLET | Application.BOB_BIT:
                if (fixA.getFilterData().categoryBits == Application.RAY_BULLET)
                    ((AIYamYam) fixA.getUserData()).isPlayerAtRangeOfFire(false);
                else
                    ((AIYamYam) fixA.getUserData()).isPlayerAtRangeOfFire(true);
                break;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}