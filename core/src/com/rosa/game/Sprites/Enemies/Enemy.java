package com.rosa.game.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.rosa.game.Sprites.Enemies.EnemyAITool.B2dSteeringEntity;
import com.rosa.game.screens.PlayScreen;

public abstract class Enemy extends Sprite {

    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    public Body b2bodyRay;
    public Vector2 velocity;

    public Enemy(PlayScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(-1, -2);
        b2body.setActive(false);
//        b2bodyRay.setActive(true);
    }

    protected abstract void defineEnemy();

    public abstract void update(float dt);

    public abstract void hitByEnemy(Enemy enemy);

    public abstract void setToDestroy();

    public abstract void reverseVelocity (boolean x, boolean y);

}
