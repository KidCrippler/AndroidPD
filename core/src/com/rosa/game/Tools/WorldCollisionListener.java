package com.rosa.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.rosa.game.Application;
import com.rosa.game.Sprites.Enemies.DumbBun;
import com.rosa.game.Sprites.Enemies.EnemyUtils.ObjectManager;
import com.rosa.game.Sprites.LevelItems.Misc.HealthPotion;
import com.rosa.game.Sprites.LevelItems.WeaponStorage.WeaponBlowzier;
import com.rosa.game.Sprites.Player.PlayerUtils.PlayerBullet;
import com.rosa.game.Sprites.Player.Player;
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

            //ObjectManager Dumb
            case Application.ENEMY_DUMB_BIT | Application.WALL_BIT:
                if (fixA.getFilterData().categoryBits == Application.ENEMY_DUMB_BIT)
                    ((ObjectManager) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((ObjectManager) fixB.getUserData()).reverseVelocity(true, false);
                break;

            case Application.ENEMY_DUMB_BIT | Application.ENEMY_DUMB_BIT:
                ((ObjectManager) fixA.getUserData()).hitByObject((ObjectManager) fixB.getUserData());
                ((ObjectManager) fixB.getUserData()).hitByObject((ObjectManager) fixA.getUserData());
                break;

            //Power Kick:
            case Application.ENEMY_DUMB_BIT | Application.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == Application.PLAYER_BIT)
                    ((Player) fixA.getUserData()).setKick();
                else
                    ((Player) fixB.getUserData()).setKick();

                if (fixA.getFilterData().categoryBits == Application.ENEMY_DUMB_BIT)
                    ((DumbBun) fixA.getUserData()).setKick();
                else
                    ((DumbBun) fixB.getUserData()).setKick();
                break;
//

//            case Application.ENEMY_AI_BIT | Application.WALL_BIT:
//                if (fixA.getFilterData().categoryBits == Application.ENEMY_AI_BIT)
//                    ((ObjectManager) fixA.getUserData()).reverseVelocity(true, false);
//                else
//                    ((ObjectManager) fixB.getUserData()).reverseVelocity(true, false);
//                break;
//
//
//             ObjectManager Ai
//            case Application.ENEMY_AI_BIT | Application.ENEMY_AI_BIT:
//                ((ObjectManager) fixA.getUserData()).hitByObject((ObjectManager) fixB.getUserData());
//                ((ObjectManager) fixB.getUserData()).hitByObject((ObjectManager) fixA.getUserData());
//                break;


            //Item collect:
            case Application.POTION_BIT | Application.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == Application.POTION_BIT)
                    ((HealthPotion) fixA.getUserData()).setHealthPoints();
                else
                    ((HealthPotion) fixB.getUserData()).setHealthPoints();
                break;

            case Application.WEAPON_BLOWZIER_BIT | Application.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == Application.WEAPON_BLOWZIER_BIT)
                    ((WeaponBlowzier) fixA.getUserData()).setWeaponBlowzier();
                else
                    ((WeaponBlowzier) fixB.getUserData()).setWeaponBlowzier();
                break;

            //Player
            case Application.PLAYER_BIT | Application.GROUND_BIT:
                if (fixA.getFilterData().categoryBits == Application.PLAYER_BIT)
                    soundPlayer.playSoundPlayer(1);
                else
                    soundPlayer.playSoundPlayer(1);
                break;

            case Application.PLAYER_BIT | Application.WALL_BIT:
                if (fixA.getFilterData().categoryBits == Application.PLAYER_BIT)
                    soundPlayer.playSoundPlayer(1);
                else
                    soundPlayer.playSoundPlayer(1);
                break;

            //Bullets
            case Application.BULLET_BIT | Application.WALL_BIT:
                if (fixA.getFilterData().categoryBits == Application.BULLET_BIT) {
                    ((PlayerBullet) fixA.getUserData()).setToDestroy();
                    soundPlayer.playSoundRandomLaserOneWall();
                } else {
                    ((PlayerBullet) fixB.getUserData()).setToDestroy();
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

            //PlayerBullet fire at Player:
            case Application.ENEMY_BULLET_BIT | Application.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == Application.PLAYER_BIT)
                    ((Player) fixA.getUserData()).setHpDown(10); //TODO: this float should be change with the power of the enemey gun.
                else
                    ((Player) fixB.getUserData()).setHpDown(10); //TODO: this float should be change with the power of the enemey gun.
                //Remove the bullet:
                if (fixA.getFilterData().categoryBits == Application.ENEMY_BULLET_BIT)
                    ((EnemyBullet) fixA.getUserData()).setToDestroy();
                else
                    ((EnemyBullet) fixB.getUserData()).setToDestroy();
                break;

            //PlayerBullet fire at ObjectManager (ENEMY abstract):
            case Application.BULLET_BIT | Application.ENEMY_DUMB_BIT:
                //Remove the enemy:
                if (fixA.getFilterData().categoryBits == Application.ENEMY_DUMB_BIT)
                    ((ObjectManager) fixA.getUserData()).takeShot(Player.bullet_hp_down);
                else
                    ((ObjectManager) fixB.getUserData()).takeShot(Player.bullet_hp_down);
                //Remove the bullet:
                if (fixA.getFilterData().categoryBits == Application.BULLET_BIT)
                    ((PlayerBullet) fixA.getUserData()).setToDestroy();
                else
                    ((PlayerBullet) fixB.getUserData()).setToDestroy();
                break;

            //ObjectManager AI:
            case Application.BULLET_BIT | Application.ENEMY_AI_BIT:
                //Remove the enemy:
                if (fixA.getFilterData().categoryBits == Application.ENEMY_AI_BIT)
                    ((ObjectManager) fixA.getUserData()).takeShot(Player.bullet_hp_down);
                else
                    ((ObjectManager) fixB.getUserData()).takeShot(Player.bullet_hp_down);
                //Remove the bullet:
                if (fixA.getFilterData().categoryBits == Application.BULLET_BIT)
                    ((PlayerBullet) fixA.getUserData()).setToDestroy();
                else
                    ((PlayerBullet) fixB.getUserData()).setToDestroy();
                break;

            //RAY_C_JUMP_BIT:
            case Application.RAY_C_JUMP_BIT | Application.WALL_BIT:
                if (fixA.getFilterData().categoryBits == Application.RAY_C_JUMP_BIT)
                    ((AIYamYam) fixA.getUserData()).jump();
                else
                    ((AIYamYam) fixB.getUserData()).jump();
                break;

            //RAY_C_JUMP_BIT:
            case Application.RAY_C_CLIMB_BIT | Application.WALL_BIT:
                if (fixA.getFilterData().categoryBits == Application.RAY_C_CLIMB_BIT)
                    ((AIYamYam) fixA.getUserData()).isClimbing(true);
                else
                    ((AIYamYam) fixB.getUserData()).isClimbing(true);
                break;
        }

    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            //RAY_C_JUMP_BIT:
            case Application.RAY_C_CLIMB_BIT | Application.WALL_BIT:
                if (fixA.getFilterData().categoryBits == Application.RAY_C_CLIMB_BIT)
                    ((AIYamYam) fixA.getUserData()).isClimbing(false);
                else
                    ((AIYamYam) fixB.getUserData()).isClimbing(false);
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