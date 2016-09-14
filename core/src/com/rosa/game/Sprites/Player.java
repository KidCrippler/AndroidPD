package com.rosa.game.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.rosa.game.AndroidJDEV;
import com.rosa.game.Tools.SoundPlayer;
import com.rosa.game.screens.PlayScreen;

public class Player extends Sprite {

    public enum State {FALLING, JUMPING, STANDING, RUNNING, DEAD}

    private SoundPlayer playSound;
    private AndroidJDEV game;
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion playerStand;
    private Animation playerRun;
    private Animation playerJump;
    private float stateTimer;
    private boolean runningRight;
    private PlayScreen screen;

    private Array<Bullet> bullets;

    SoundPlayer soundPlayer = new SoundPlayer();

    public Player(World world, PlayScreen screen) {
        super(screen.getAtlas().findRegion("keen"));
        this.world = world;
        this.screen = screen;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;


        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 1; i < 5; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("keen"), i * 23, 0, 23, 32));
        playerRun = new Animation(0.1f, frames);
        frames.clear();


        for (int i = 4; i < 7; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("keen"), i * 23, 0, 23, 32));
        playerJump = new Animation(0.1f, frames);
        frames.clear();


        playerStand = new TextureRegion(screen.getAtlas().findRegion("keen"), 0, 0, 23, 32);


        definePlayer();
        setBounds(0, 0, 23 / AndroidJDEV.PPM, 32 / AndroidJDEV.PPM);
        setRegion(playerStand);

        bullets = new Array<Bullet>();
    }

    public void update(float dt) {
        //position of sprite inside shape:
        setPosition(b2body.getPosition().x - getWidth() / 2, (float) (b2body.getPosition().y - getHeight() / 1.5));
        setRegion(getFrame(dt));

        for (Bullet bullet : bullets) {
            bullet.update(dt);
            if (bullet.isDestroyed())
                bullets.removeValue(bullet, true);
        }
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case JUMPING:
                region = playerJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = playerRun.getKeyFrame(stateTimer, true);

                break;
            case FALLING:
            case STANDING:
            default:
                region = playerStand;
                break;
        }
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;

        previousState = currentState;
        return region;
    }

    public State getState() {
        if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if (b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public void definePlayer() {

        BodyDef bdef = new BodyDef();
        bdef.position.set(64 / AndroidJDEV.PPM, 64 / AndroidJDEV.PPM);


        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7 / AndroidJDEV.PPM);
        fdef.filter.categoryBits = AndroidJDEV.PLAYER_BIT;
        fdef.filter.maskBits = AndroidJDEV.DEFAULT_BIT | AndroidJDEV.COIN_BIT | AndroidJDEV.BRICK_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);
        shape.setPosition(new Vector2(0, -14 / AndroidJDEV.PPM));
        b2body.createFixture(fdef).setUserData(this);


        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / AndroidJDEV.PPM, 7 / AndroidJDEV.PPM), new Vector2(2 / AndroidJDEV.PPM, 7 / AndroidJDEV.PPM));
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData("head");
    }


    public void jump() {
        if (currentState != State.JUMPING) {
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
            soundPlayer.soundPlayer(1);
            currentState = State.JUMPING;
        }
    }

    public void goRight() {
        b2body.applyLinearImpulse(new Vector2(0.1f, 0), b2body.getWorldCenter(), true);
    }

    public void goLeft() {
        b2body.applyLinearImpulse(new Vector2(-0.1f, 0), b2body.getWorldCenter(), true);
    }

    public void fire() {
        bullets.add(new Bullet(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true : false));
    }

}
