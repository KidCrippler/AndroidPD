package com.rosa.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.rosa.game.screens.PlayScreen;


public class Bullet extends Sprite {

    public World world;
    public Body b2body;
    private TextureRegion playerStand;
    private Animation playerRun;
    private Animation playerJump;
    private float stateTimer;
    private boolean runningRight;
    private Bullet bullet;
    private PlayScreen screen;


    public Bullet(World world, PlayScreen screen) {

        super(screen.getAtlas().findRegion("keen"));
        System.out.println("Bullet new bullet");
        this.world = world;
        this.screen = screen;
    }


    public void update(float dt) {
        System.out.println("updated");
//        setPosition(b2body.getPosition().x - getWidth() / 2, (float) (b2body.getPosition().y - getHeight() / 1.5));
//        setRegion(getFrame(dt));
    }
}