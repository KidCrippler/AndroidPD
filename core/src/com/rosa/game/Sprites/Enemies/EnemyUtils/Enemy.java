package com.rosa.game.Sprites.Enemies.EnemyUtils;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.rosa.game.screens.ScreenPlay;

public abstract class Enemy extends Sprite {

    protected World world;
    protected ScreenPlay screen;
    public Body b2body;
    public Vector2 velocity;

    public Enemy(ScreenPlay screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(-1, -2);
        b2body.setActive(false);
    }

    protected abstract void defineEnemy();
    public abstract void update(float dt);
    public abstract void hitByEnemy(Enemy enemy);
    public abstract void setToDestroy();
    public abstract void takeShot(int bulletPowerOne);
    public abstract void reverseVelocity (boolean x, boolean y);
}
