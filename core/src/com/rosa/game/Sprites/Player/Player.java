package com.rosa.game.Sprites.Player;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.rosa.game.Application;
import com.rosa.game.Sprites.Player.PlayerUtils.PlayerBullet;
import com.rosa.game.Tools.BoxWorldCreator;
import com.rosa.game.Tools.SoundPlayer;
import com.rosa.game.screens.ScreenDead;
import com.rosa.game.screens.ScreenMainGamePlay;

public class Player extends Sprite {

    private enum State {FALLING, JUMPING, STANDING, RUNNING, DEAD}

    private State currentState;
    private State previousState;
    public World world;
    public Body b2body;
    private TextureRegion playerStand;
    private Animation playerRun;
    private Animation playerJump;
    private float stateTimer;
    private boolean runningRight;
    private ScreenMainGamePlay screen;
    private SoundPlayer soundPlayer = new SoundPlayer();
    private Array<PlayerBullet> bullets;
    public static float PLAYER_TOTAL_HEALTH;
    public static float PLAYER_X_POSITION;
    public static float PLAYER_Y_POSITION;
    private static float lastShot;

    public static float default_bullet_rate = 200000000L;
    public static float bullet_rate = default_bullet_rate;




    private Application game;

    public Player(World world, ScreenMainGamePlay screen, Application game) {
        super(screen.getAtlas().findRegion("keen"));
        this.game = game;
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

        playerStand = new TextureRegion(screen.getAtlas().findRegion("keen"), -5, 0, 23, 32);

        definePlayer();
        setBounds(0, 0, 23 / Application.PPM, 32 / Application.PPM);
        setRegion(playerStand);

        bullets = new Array<PlayerBullet>();
        PLAYER_TOTAL_HEALTH = 100;
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, (float) (b2body.getPosition().y - getHeight() / 300.0));
        setRegion(getFrame(dt));

        PLAYER_X_POSITION = b2body.getPosition().x;

        if (PLAYER_TOTAL_HEALTH <= 0 || b2body.getPosition().y <= -5) {
            dead();
        }

        for (PlayerBullet bullet : bullets) {
            bullet.update(dt);
            if (bullet.isDestroyed()) {
                bullets.removeValue(bullet, true);
            }
        }
    }

    private TextureRegion getFrame(float dt) {
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

    private State getState() {
        if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if (b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    private void definePlayer() {
        BodyDef bodyDef = new BodyDef();

        for (MapObject object : BoxWorldCreator.map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / Application.PPM, (rect.getY() + rect.getHeight() / 2) / Application.PPM);
        }

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        Vector2[] vector2s = new Vector2[4];
        vector2s[0] = new Vector2(-1, 33).scl(1 / Application.PPM);
        vector2s[1] = new Vector2(1, 33).scl(1 / Application.PPM);
        vector2s[2] = new Vector2(-4, 1).scl(1 / Application.PPM);
        vector2s[3] = new Vector2(4, 1).scl(1 / Application.PPM);
        shape.set(vector2s);
        fixtureDef.filter.categoryBits = Application.PLAYER_BIT;
        fixtureDef.filter.maskBits = Application.GROUND_BIT |
                Application.ENEMY_DUMB_BIT |
                Application.ENEMY_AI_BIT |
                Application.WALL_BIT |
                Application.ITEM_BIT |
                Application.ENEMY_BULLET_BIT |
                Application.POTION_BIT |
                Application.WEAPON_BLOWZIER_BIT;
        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef).setUserData(this);
    }

    public void jump() {
        if (currentState != State.JUMPING) {
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
            soundPlayer.playSoundPlayer(0);
            currentState = State.JUMPING;
        }
    }

//    public void goRight() {
//        b2body.applyLinearImpulse(new Vector2(0.1f, 0), b2body.getWorldCenter(), true);
//    }
//
//    public void goLeft() {
//        b2body.applyLinearImpulse(new Vector2(-0.1f, 0), b2body.getWorldCenter(), true);
//    }

    public void fire() {
        if (System.nanoTime() - lastShot >= bullet_rate) {
            bullets.add(new PlayerBullet(screen, b2body.getPosition().x, (float) (b2body.getPosition().y + 0.2), runningRight));
            lastShot = System.nanoTime();
            soundPlayer.playSoundRandomLazerLaserShootOne();
        }
    }

    public void draw(Batch batch) {
        super.draw(batch);
        for (PlayerBullet bullet : bullets)
            bullet.draw(batch);
    }

    public void setHpDown(float hpDown) {
        PLAYER_TOTAL_HEALTH -= hpDown;
        System.out.println("HP: " + PLAYER_TOTAL_HEALTH);
    }

    private void dead() {
        soundPlayer.playSoundPlayer(2);
        System.out.println("you are dead.");
        currentState = State.DEAD;
        ((Game) Gdx.app.getApplicationListener()).setScreen(new ScreenDead(game));
    }

    public void setKick() {
        b2body.applyLinearImpulse(new Vector2(b2body.getAngularVelocity(), 8), b2body.getWorldCenter(), true);
        soundPlayer.playSoundPlayer(0);
        soundPlayer.playSoundPlayer(1);
        soundPlayer.playSoundRandomBunDead();
        setHpDown(33);
    }
}
