package com.rosa.game.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.rosa.game.Application;
import com.rosa.game.Sprites.Bob.Player;
import com.rosa.game.Sprites.Enemies.EnemyUtils.Enemy;
import com.rosa.game.Sprites.Enemies.EnemyUtils.EnemyFirePowerLas;
import com.rosa.game.Tools.SoundPlayer;
import com.rosa.game.screens.PlayScreen;

public class YamYam extends Enemy {

    private enum State {FALLING, JUMPING, STANDING, RUNNING}

    private State currentState;
    private State previousState;
    private float stateTime;
    private Animation walkAnimation;
    private boolean setToDestroy;
    private boolean destroyed;
    private int yamyamHP = 100;
    private SoundPlayer playSound = new SoundPlayer();
    private Array<com.rosa.game.Sprites.Enemies.EnemyUtils.EnemyFirePowerLas> enemyFirePowerLasArray;
    private static final long FIRE_RATE = 1200000000L;
    private long lastShot;
    private boolean runningRight;
    private SoundPlayer soundPlayer = new SoundPlayer();
    private float stateTimer;
    private Animation yamyamRun;
    private Animation yamyamJump;
    private TextureRegion yamyamStand;
    private static boolean chasing = true;


    public YamYam(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        stateTime = 0;
        setToDestroy = false;
        destroyed = false;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 1; i < 5; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("keen"), i * 23, 0, 23, 32));
        yamyamRun = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 4; i < 7; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("keen"), i * 23, 0, 23, 32));
        yamyamJump = new Animation(0.1f, frames);
        frames.clear();

        yamyamStand = new TextureRegion(screen.getAtlas().findRegion("keen"), -5, 0, 23, 32);

        for (int i = 0; i < 2; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("keen"), i * 32, 0, 32, 32));


        walkAnimation = new Animation(0.4f, frames);

        setBounds(0, 0, 23 / Application.PPM, 32 / Application.PPM);
        setRegion(yamyamStand);

        enemyFirePowerLasArray = new Array<com.rosa.game.Sprites.Enemies.EnemyUtils.EnemyFirePowerLas>();
    }

    public void update(float dt) {
        stateTime += dt;
        if (setToDestroy && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("keen"), 11, 0, 22, 12));
            stateTime = 0;

        } else {
                setRegion(getFrame(dt));

            if (!destroyed && b2body.isActive() && chasing) {

                //RAY_ONE (AI movement):
                if (Player.BOB_X_POSITION + 0.4 <= b2body.getPosition().x)
                    b2body.applyLinearImpulse(new Vector2(-0.02f, 0), b2body.getWorldCenter(), true);

                if (Player.BOB_X_POSITION - 0.4 >= b2body.getPosition().x)
                    b2body.applyLinearImpulse(new Vector2(0.02f, 0), b2body.getWorldCenter(), true);

                //Draw:
                setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
                setRegion(walkAnimation.getKeyFrame(stateTime, true));

//                fire();
                for (EnemyFirePowerLas enemyFirePowerLas : enemyFirePowerLasArray) {
                    enemyFirePowerLas.update(dt);
                    if (enemyFirePowerLas.isDestroyed()) {
                        enemyFirePowerLasArray.removeValue(enemyFirePowerLas, true);
                    }
                }
                //looking at you:
                if (b2body.getLinearVelocity().x < 0) {
                    runningRight = false;
                } else if (b2body.getLinearVelocity().x > 0) {
                    runningRight = true;
                }
            }
        }
    }

    private TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case JUMPING:
                region = yamyamJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = yamyamRun.getKeyFrame(stateTimer, true);

                break;
            case FALLING:
            case STANDING:
            default:
                region = yamyamStand;
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

    @Override
    protected void defineEnemy() {
        FixtureDef fixtureDef = new FixtureDef();
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);
        PolygonShape head = new PolygonShape();
        Vector2[] vector2s = new Vector2[4];
        vector2s[0] = new Vector2(-5, 34).scl(1 / Application.PPM);
        vector2s[1] = new Vector2(5, 34).scl(1 / Application.PPM);
        vector2s[2] = new Vector2(-3, 3).scl(1 / Application.PPM);
        vector2s[3] = new Vector2(3, 3).scl(1 / Application.PPM);
        head.set(vector2s);

        fixtureDef.shape = head;
        fixtureDef.filter.categoryBits = Application.ENEMY_AI_BIT;
        fixtureDef.filter.maskBits = Application.GROUND_BIT |
                        Application.ENEMY_AI_BIT |
                        Application.ENEMY_DUMB_BIT |
                        Application.WALL_BIT |
                        Application.BOB_BIT |
                        Application.GROUND_BIT |
                        Application.BUN_BULLET_BIT;

        b2body.createFixture(fixtureDef).setUserData(this);

        //RAYOne:
        FixtureDef fixtureDefRayOne = new FixtureDef();
        CircleShape rayShapeOne = new CircleShape();
        rayShapeOne.setRadius(6 / Application.PPM);
        fixtureDefRayOne.filter.categoryBits = Application.RAY_ONE;
        fixtureDefRayOne.filter.maskBits =
                Application.WALL_BIT;

        fixtureDefRayOne.shape = rayShapeOne;
        fixtureDefRayOne.isSensor = true;
        rayShapeOne.setPosition(new Vector2(0.5f, 0 / Application.PPM));
        b2body.createFixture(fixtureDefRayOne).setUserData(this);
        rayShapeOne.setPosition(new Vector2(-0.5f, 0 / Application.PPM));
        b2body.createFixture(fixtureDefRayOne).setUserData(this);

        //RAYOne:
        FixtureDef fixtureDefRayTwo = new FixtureDef();
        CircleShape rayShapeTwo = new CircleShape();
        rayShapeTwo.setRadius(6 / Application.PPM);
        fixtureDefRayTwo.filter.categoryBits = Application.RAY_TWO;
        fixtureDefRayTwo.filter.maskBits =
                Application.WALL_BIT;

        fixtureDefRayTwo.shape = rayShapeTwo;
        fixtureDefRayTwo.isSensor = true;
        rayShapeTwo.setPosition(new Vector2(0.2f, 0 / Application.PPM));
        b2body.createFixture(fixtureDefRayTwo).setUserData(this);
        rayShapeTwo.setPosition(new Vector2(-0.2f, 0 / Application.PPM));
        b2body.createFixture(fixtureDefRayTwo).setUserData(this);
    }

    public void draw(Batch batch) {
        if (!destroyed || stateTime < 1)
            super.draw(batch);
    }

    @Override
    public void hitByEnemy(com.rosa.game.Sprites.Enemies.EnemyUtils.Enemy enemy) {
        playSound.playSoundRandomBunHurt();
    }

    public void reverseVelocity(boolean x, boolean y) {
        if (x)
            velocity.x = -velocity.x;
        if (y)
            velocity.y = -velocity.y;

        chasing = !chasing;

        System.out.println("hit");
    }

    public void setToDestroy() {
        isDestroyed();
        playSound.playSoundRandomBunHurt();
        int bulletPowerOne = 10;
        yamyamHP = yamyamHP - bulletPowerOne;
        System.out.println(yamyamHP);
        if (yamyamHP <= 0) {
            playSound.playSoundRandomBunDead();
            setToDestroy = true;
        }
    }

    private void fire() {
        if (System.nanoTime() - lastShot >= FIRE_RATE) {
            enemyFirePowerLasArray.add(new com.rosa.game.Sprites.Enemies.EnemyUtils.EnemyFirePowerLas(screen, (float) (b2body.getPosition().x - 0.1), (float) (b2body.getPosition().y + 0.2), runningRight));
            lastShot = System.nanoTime();
            soundPlayer.playSoundRandomYamYamFirePower();
        }
    }

    public void jump() {
        if (currentState != State.JUMPING) {
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
            soundPlayer.PlaySoundBob(0);
            currentState = State.JUMPING;
        }
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}