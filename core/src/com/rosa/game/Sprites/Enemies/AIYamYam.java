/**
 * BREAKS IN GAME CAN CAUSE OF:
 * if (!destroyed && b2body.isActive()) {
 **/
package com.rosa.game.Sprites.Enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.ai.GdxAI;
import com.rosa.game.Application;
import com.rosa.game.Sprites.Player.Player;
import com.rosa.game.Sprites.Enemies.EnemyUtils.Enemy;
import com.rosa.game.Sprites.Enemies.EnemyUtils.EnemyBullet;
import com.rosa.game.Tools.SoundPlayer;
import com.rosa.game.screens.ScreenPlay;

public class AIYamYam extends Enemy implements RayCastCallback {

    private enum State {FALLING, JUMPING, STANDING, RUNNING}

    private State currentState;
    private State previousState;
    private boolean setToDestroy;
    private boolean destroyed;
    private int yamyamHP = 100;
    private SoundPlayer playSound = new SoundPlayer();
    private Array<EnemyBullet> enemyFirePowerLasArray;
    private long lastShot;
    private long lastNoMove;
    private boolean runningRight;
    private float stateTimer;
    private Animation yamyamRun;
    private Animation yamyamJump;
    private TextureRegion yamyamStand;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private Vector2 p1 = new Vector2();
    private Vector2 p2 = new Vector2();
    private Vector2 collision = new Vector2();
    private Vector2 point = new Vector2();
    private Vector2 normal = new Vector2();
    private Vector2 tmpD = new Vector2();
    private static final long FIRE_TIME = 440000000L;
    private static final int NOTHING = 0;
    private static final int WALL = 1;
    private static final int PLAYER = 2;
    private int rayCastStatus = NOTHING;
    private static boolean chasing = true;

    public AIYamYam(ScreenPlay screen, float x, float y) {
        super(screen, x, y);
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        setToDestroy = false;
        destroyed = false;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 1; i < 5; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("keen"), i * 23, 0, 23, 32));
        yamyamRun = new Animation(0.25f, frames);
        frames.clear();

        for (int i = 4; i < 7; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("keen"), i * 23, 0, 23, 32));
        yamyamJump = new Animation(0.1f, frames);
        frames.clear();

        yamyamStand = new TextureRegion(screen.getAtlas().findRegion("keen"), -5, 0, 23, 32);

        for (int i = 0; i < 2; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("keen"), i * 32, 0, 32, 32));

        setBounds(0, 0, 23 / Application.PPM, 32 / Application.PPM);
        setRegion(yamyamStand);

        enemyFirePowerLasArray = new Array<EnemyBullet>();
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.line(p1, p2);
        shapeRenderer.line(collision, normal);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.line(point, tmpD.set(point).add(normal));
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.end();
    }

    public void update(float dt) {

        if (setToDestroy && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("keen"), 11, 0, 22, 12));
        } else if (!destroyed && b2body.isActive()) {
            setRegion(getFrame(dt));
            setPosition(b2body.getPosition().x - getWidth() / 2, (float) (b2body.getPosition().y - getHeight() / 300.0));
            setRegion(getFrame(dt));
            AIBehavior(dt);
        }

        if (yamyamHP <= 0 || b2body.getPosition().y < -1) {
            dead();
        }
    }

    @Override
    public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {

        if (fixture.getFilterData().categoryBits == Application.WALL_BIT) {
            rayCastStatus = WALL;
        }

        if (fixture.getFilterData().categoryBits == Application.PLAYER_BIT) {
            rayCastStatus = PLAYER;
        }

        this.point.set(point);
        this.normal.set(normal);

        return fraction;
    }

    private void AIBehavior(float dt) {

        if (chasing == false) {
            b2body.setLinearVelocity(velocity);
            reverseVelocity(true, false);
        }

        if (chasing == true) {
            if (Player.BOB_X_POSITION + 0.4 <= b2body.getPosition().x) {
                b2body.applyLinearImpulse(new Vector2(-0.03f, 0), b2body.getWorldCenter(), true);
            }

            if (Player.BOB_X_POSITION - 0.4 >= b2body.getPosition().x) {
                b2body.applyLinearImpulse(new Vector2(0.03f, 0), b2body.getWorldCenter(), true);
            }
        }

        float rayCastDirection = -2;

        if (b2body.getLinearVelocity().x < 0) {
            runningRight = false;
            rayCastDirection = -2f;
        }

        if (b2body.getLinearVelocity().x > 0) {
            runningRight = true;
            rayCastDirection = 2f;
        }

        p1.set(b2body.getPosition().x, b2body.getPosition().y + 0.2f);
        p2.set(b2body.getPosition().x + rayCastDirection, b2body.getPosition().y + 0.2f);

        world.rayCast(this, p1, p2);

        if (rayCastStatus == 2) {
            chasing = true;
//            fire();
        }

        for (EnemyBullet enemyFirePowerLas : enemyFirePowerLasArray) {
            enemyFirePowerLas.update(dt);
            if (enemyFirePowerLas.isDestroyed()) {
                enemyFirePowerLasArray.removeValue(enemyFirePowerLas, true);
            }
        }


        if (b2body.getLinearVelocity().x >= 0.01f || b2body.getLinearVelocity().x >= 0.0 || b2body.getLinearVelocity().x >= -0.01f) {
            System.out.println("can't move");
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
                Application.PLAYER_BIT |
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
    }

    @Override
    public void hitByEnemy(Enemy enemy) {
        playSound.playSoundRandomBunHurt();
    }

    public void reverseVelocity(boolean x, boolean y) {
        if (x) {
            velocity.x = -velocity.x;
        }
        if (y) {
            velocity.y = -velocity.y;
        }
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
        if (currentState != State.JUMPING && chasing == true) {
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
            playSound.playSoundPlayer(0);
            currentState = State.JUMPING;
        }
    }

    public void takeShot(int bulletPower) {
        yamyamHP = yamyamHP - bulletPower;
    }

    private void dead() {
        playSound.playSoundRandomBunHurt();
        setToDestroy = true;
        setToDestroy();
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
