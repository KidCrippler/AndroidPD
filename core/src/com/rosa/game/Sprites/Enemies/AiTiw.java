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
import com.rosa.game.Sprites.Enemies.EnemyUtils.EnemyBullet;
import com.rosa.game.Tools.SoundPlayer;
import com.rosa.game.screens.ScreenPlay;

public class AiTiw extends Enemy {

    private enum State {FALLING, JUMPING, STANDING, RUNNING}

    private State currentState;
    private State previousState;
    private float stateTime;
    private Animation walkAnimation;
    private boolean setToDestroy;
    private boolean destroyed;
    private int yamyamHP = 100;
    private SoundPlayer playSound = new SoundPlayer();
    private Array<EnemyBullet> enemyFirePowerLasArray;
    private static final long FIRE_TIME = 2220000000L;
    private long lastShot;
    private boolean runningRight;
    private float stateTimer;
    private Animation yamyamRun;
    private Animation yamyamJump;
    private TextureRegion yamyamStand;
    private boolean rayTwoNextToWall;
    private boolean chasing;

    public AiTiw(ScreenPlay screen, float x, float y) {
        super(screen, x, y);
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        stateTime = 0;
        setToDestroy = false;
        destroyed = false;
        chasing = true;

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

        enemyFirePowerLasArray = new Array<EnemyBullet>();
        chasing = true;
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

            if (!destroyed && b2body.isActive()) {

                //Draw:
                setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
                setRegion(walkAnimation.getKeyFrame(stateTime, true));

                //TODO: fix chase or not
                //If you are not chased:
                if (!chasing) {
                    chasing = true;
                }

                //If you are chased behaver:
                if (chasing) {

                    //RAY_JUMP (AI movement):
                    if (Player.BOB_X_POSITION + 0.4 <= b2body.getPosition().x)
                        b2body.applyLinearImpulse(new Vector2(-0.02f, 0), b2body.getWorldCenter(), true);

                    if (Player.BOB_X_POSITION - 0.4 >= b2body.getPosition().x)
                        b2body.applyLinearImpulse(new Vector2(0.02f, 0), b2body.getWorldCenter(), true);
                    //Fire bullets:
                    fire();
                    for (EnemyBullet enemyFirePowerLas : enemyFirePowerLasArray) {
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
        if (yamyamHP <= 0 || b2body.getPosition().y < -1) {
            dead();
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
        vector2s[0] = new Vector2(-1, 33).scl(1 / Application.PPM);
        vector2s[1] = new Vector2(1, 33).scl(1 / Application.PPM);
        vector2s[2] = new Vector2(-4, 1).scl(1 / Application.PPM);
        vector2s[3] = new Vector2(4, 1).scl(1 / Application.PPM);
        head.set(vector2s);

        fixtureDef.shape = head;
        fixtureDef.filter.categoryBits = Application.ENEMY_AI_BIT;
        fixtureDef.filter.maskBits = Application.ENEMY_AI_BIT |
                Application.ENEMY_DUMB_BIT |
                Application.WALL_BIT |
                Application.BOB_BIT |
                Application.GROUND_BIT |
                Application.BULLET_BIT;

        b2body.createFixture(fixtureDef).setUserData(this);

        //RAYOne - (Outer):
        FixtureDef fixtureDefRayOne = new FixtureDef();
        CircleShape rayShapeOne = new CircleShape();
        rayShapeOne.setRadius(6 / Application.PPM);
        fixtureDefRayOne.filter.categoryBits = Application.RAY_JUMP;

        fixtureDefRayOne.shape = rayShapeOne;
        fixtureDefRayOne.isSensor = true;
        rayShapeOne.setPosition(new Vector2(0.5f, 0 / Application.PPM));
        b2body.createFixture(fixtureDefRayOne).setUserData(this);
        rayShapeOne.setPosition(new Vector2(-0.5f, 0 / Application.PPM));
        b2body.createFixture(fixtureDefRayOne).setUserData(this);
    }

    public void draw(Batch batch) {
        if (!destroyed || stateTime < 1)
            super.draw(batch);
    }

    @Override
    public void hitByEnemy(Enemy enemy) {
        playSound.playSoundRandomBunHurt();

    }

    public void reverseVelocity(boolean x, boolean y) {
        if (x)
            velocity.x = -velocity.x;
        if (y)
            velocity.y = -velocity.y;
    }

    public void setToDestroy() {
        isDestroyed();
    }

    private void fire() {
        if (!rayTwoNextToWall) {
            if (System.nanoTime() - lastShot >= FIRE_TIME) {
                enemyFirePowerLasArray.add(new EnemyBullet(screen, b2body.getPosition().x, (float) (b2body.getPosition().y + 0.2), runningRight));
                lastShot = System.nanoTime();
                playSound.playSoundRandomYamYamFirePower();
            }
        }
    }

    public void jump() {
        if (!destroyed && b2body.isActive() && chasing) {
            if (currentState != State.JUMPING) {
                b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
                playSound.playSoundBob(0);
                currentState = State.JUMPING;
            }
        }
    }

    public void takeShot(int bulletPower) {
        yamyamHP = yamyamHP - bulletPower;
        System.out.println("YamYam HP= " + yamyamHP);
    }

    private void dead() {
        playSound.playSoundRandomBunHurt();
        System.out.println("dead!!!");
        setToDestroy();
        setToDestroy = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setRayTwoNextToWall(boolean rayTwoNextToWall) {
        this.rayTwoNextToWall = rayTwoNextToWall;
    }
}
