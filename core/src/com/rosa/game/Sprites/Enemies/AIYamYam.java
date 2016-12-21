package com.rosa.game.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.utils.Array;
import com.rosa.game.Application;
import com.rosa.game.Sprites.Bob.Player;
import com.rosa.game.Sprites.Enemies.EnemyUtils.Enemy;
import com.rosa.game.Sprites.Enemies.EnemyUtils.EnemyBullet;
import com.rosa.game.Tools.SoundPlayer;
import com.rosa.game.screens.ScreenPlay;

public class AIYamYam extends Enemy {

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
    private static final long FIRE_TIME = 220000000L;
    private long lastShot;
    private boolean runningRight;
    private float stateTimer;
    private Animation yamyamRun;
    private Animation yamyamJump;
    private TextureRegion yamyamStand;
    private boolean rayTwoNextToWall;
    private boolean chasing;
    public static boolean playerAtRangeOfFire;
    private Vector2 startPoint = b2body.getPosition();
    private Vector2 endPoint = b2body.getPosition();
    private float fraction;
    EdgeShape edgeShapeRealRayCast;

    public AIYamYam(ScreenPlay screen, float x, float y) {
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


        walkAnimation = new Animation(0.1f, frames);

        setBounds(0, 0, 23 / Application.PPM, 32 / Application.PPM);
        setRegion(yamyamStand);

        enemyFirePowerLasArray = new Array<EnemyBullet>();
        chasing = true;

    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
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
                setPosition(b2body.getPosition().x - getWidth() / 2, (float) (b2body.getPosition().y - getHeight() / 300.0));
                setRegion(getFrame(dt));
                AIBehavior(dt);
            }
        }
        if (yamyamHP <= 0 || b2body.getPosition().y < -1)
            dead();
    }

    private void AIBehavior(float dt) {
        //RAY_JUMP (AI movement):
        if (Player.BOB_X_POSITION + 0.4 <= b2body.getPosition().x)
            b2body.applyLinearImpulse(new Vector2(-0.02f, 0), b2body.getWorldCenter(), true);

        if (Player.BOB_X_POSITION - 0.4 >= b2body.getPosition().x)
            b2body.applyLinearImpulse(new Vector2(0.02f, 0), b2body.getWorldCenter(), true);
        //Fire bullets:
        if (!rayTwoNextToWall && playerAtRangeOfFire) {
//            fire();
        }

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



        RayCastCallback callback = new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {

                if (fixture.getFilterData().categoryBits == Application.BOB_BIT) {
                    System.out.println("CAN SEE!");
                    return 0;
                }
                if (fixture.getFilterData().categoryBits == Application.WALL_BIT) {
                    System.out.println("CAN SEE WALL!");
                    return 0;
                }
                return -1;
            }
        };

        world.rayCast(callback, b2body.getPosition(), new Vector2(b2body.getPosition().x - 500, b2body.getPosition().y));
        edgeShapeRealRayCast.set(new Vector2(b2body.getPosition().x, b2body.getPosition().x), new Vector2(b2body.getPosition().x, b2body.getPosition().x ));

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
        FixtureDef fixtureDefOfEnemyBody = new FixtureDef();
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);
        PolygonShape heads = new PolygonShape();
        Vector2[] vector2s = new Vector2[4];
        vector2s[0] = new Vector2(-1, 33).scl(1 / Application.PPM);
        vector2s[1] = new Vector2(1, 33).scl(1 / Application.PPM);
        vector2s[2] = new Vector2(-4, 1).scl(1 / Application.PPM);
        vector2s[3] = new Vector2(4, 1).scl(1 / Application.PPM);
        heads.set(vector2s);

        fixtureDefOfEnemyBody.shape = heads;
        fixtureDefOfEnemyBody.filter.categoryBits = Application.ENEMY_AI_BIT;
        fixtureDefOfEnemyBody.filter.maskBits = Application.ENEMY_AI_BIT |
                Application.ENEMY_DUMB_BIT |
                Application.WALL_BIT |
                Application.BOB_BIT |
                Application.GROUND_BIT |
                Application.BULLET_BIT;

        b2body.createFixture(fixtureDefOfEnemyBody).setUserData(this);

        //RAYOne - (Outer - Jump):
        FixtureDef fixtureDefRayOfJump = new FixtureDef();
        CircleShape circleRayOfJump = new CircleShape();
        circleRayOfJump.setRadius(6 / Application.PPM);
        fixtureDefRayOfJump.filter.categoryBits = Application.RAY_JUMP;

        fixtureDefRayOfJump.shape = circleRayOfJump;
        fixtureDefRayOfJump.isSensor = true;
        circleRayOfJump.setPosition(new Vector2(0.5f, 0 / Application.PPM));
        b2body.createFixture(fixtureDefRayOfJump).setUserData(this);
        circleRayOfJump.setPosition(new Vector2(-0.5f, 0 / Application.PPM));
        b2body.createFixture(fixtureDefRayOfJump).setUserData(this);

//        RAYTwo - (Inner):
//        FixtureDef fixtureDefRayTwo = new FixtureDef();
//        CircleShape rayShapeTwo = new CircleShape();
//        rayShapeTwo.setRadius(6 / Application.PPM);
//        fixtureDefRayTwo.filter.categoryBits = Application.RAY_TWO_INNER;
//
//        fixtureDefRayTwo.shape = rayShapeTwo;
//        fixtureDefRayTwo.isSensor = true;
//        rayShapeTwo.setPosition(new Vector2(0.2f, 0 / Application.PPM));
//        b2body.createFixture(fixtureDefRayTwo).setUserData(this);
//        rayShapeTwo.setPosition(new Vector2(-0.2f, 0 / Application.PPM));
//        b2body.createFixture(fixtureDefRayTwo).setUserData(this);

        //Ray Fire:
        FixtureDef fixtureDefRayOfFire = new FixtureDef();
        EdgeShape edgeShapeOfFire = new EdgeShape();

        fixtureDefRayOfFire.filter.categoryBits = Application.RAY_BULLET;

        fixtureDefRayOfFire.shape = edgeShapeOfFire;
        fixtureDefRayOfFire.isSensor = true;

//        edgeShapeOfFire.set(new Vector2(0 / Application.PPM, 24 / Application.PPM), new Vector2(240 / Application.PPM, 24 / Application.PPM));
//        b2body.createFixture(fixtureDefRayOfFire).setUserData(this);
//
//        edgeShapeOfFire.set(new Vector2(0 / Application.PPM, 24 / Application.PPM), new Vector2(-240 / Application.PPM, 24 / Application.PPM));
//        b2body.createFixture(fixtureDefRayOfFire).setUserData(this);
//



        //Ray Real:
        FixtureDef FixtureRealRayCast = new FixtureDef();
        edgeShapeRealRayCast = new EdgeShape();

        FixtureRealRayCast.filter.categoryBits = Application.RAY_BULLET;

        FixtureRealRayCast.shape = edgeShapeRealRayCast;
        FixtureRealRayCast.isSensor = true;

//        edgeShapeRealRayCast.set(new Vector2(b2body.getPosition().x, b2body.getPosition().x), new Vector2(b2body.getPosition().x, b2body.getPosition().x ));
        b2body.createFixture(FixtureRealRayCast).setUserData(this);

//        edgeShapeRealRayCast.set(new Vector2(0 / Application.PPM, 48 / Application.PPM), new Vector2(-100 / Application.PPM, 48 / Application.PPM));
//        b2body.createFixture(FixtureRealRayCast).setUserData(this);
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
        if (System.nanoTime() - lastShot >= FIRE_TIME) {
            enemyFirePowerLasArray.add(new EnemyBullet(screen, b2body.getPosition().x, (float) (b2body.getPosition().y + 0.2), runningRight));
            lastShot = System.nanoTime();
            playSound.playSoundRandomYamYamFirePower();
        }
    }

    public void jump() {
        if (!destroyed && b2body.isActive()) {
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
        System.out.println("YamYam IS DEAD");
        setToDestroy = true;
        setToDestroy();
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setRayTwoNextToWall(boolean rayTwoNextToWall) {
        this.rayTwoNextToWall = rayTwoNextToWall;
    }

    public void isPlayerAtRangeOfFire(boolean playerAtRangeOfFire) {
        this.playerAtRangeOfFire = playerAtRangeOfFire;
    }
}