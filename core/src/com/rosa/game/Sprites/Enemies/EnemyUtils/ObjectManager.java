package com.rosa.game.Sprites.Enemies.EnemyUtils;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.rosa.game.screens.ScreenPlay;

public abstract class ObjectManager extends Sprite {

    protected World world;
    protected ScreenPlay screen;
    public Body b2body;
    public Vector2 velocity;

    public ObjectManager(ScreenPlay screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineSpriteObject();
        velocity = new Vector2(-1, -2);
        b2body.setActive(false);
    }

    protected abstract void defineSpriteObject();
    public abstract void update(float dt);
    public abstract void hitByObject(ObjectManager objectManager);
    public abstract void setToDestroy();
    public abstract void takeShot(int bulletPowerOne);
    public abstract void reverseVelocity (boolean x, boolean y);
}
