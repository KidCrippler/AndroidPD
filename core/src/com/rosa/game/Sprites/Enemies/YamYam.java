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
import com.rosa.game.Tools.SoundPlayer;
import com.rosa.game.screens.PlayScreen;

public class YamYam extends Enemy {

    private enum State {JUMPING}

    public State currentState;
    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private int yamyamHP = 100;
    private SoundPlayer playSound = new SoundPlayer();
    private Array<EnemyFirePowerLas> enemyFirePowerLasArray;
    private static final long FIRE_RATE = 600000000L;
    private boolean runningRight;
    private long lastShot;
    private SoundPlayer soundPlayer = new SoundPlayer();
    public static boolean wallIntact = false;

    public YamYam(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        runningRight = true;

        for (int i = 0; i < 2; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("keen"), i * 32, 0, 32, 32));

        walkAnimation = new Animation(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 16 / Application.PPM, 16 / Application.PPM);
        setToDestroy = false;
        destroyed = false;
        enemyFirePowerLasArray = new Array<EnemyFirePowerLas>();
    }

    public void update(float dt) {
        stateTime += dt;
        if (setToDestroy && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("keen"), 11, 0, 22, 12));
            stateTime = 0;

        } else {
            if (!destroyed && b2body.isActive()) {

                if (!wallIntact || wallIntact) {

                    b2body.setLinearVelocity(0, 0);


                    if (Player.BOB_X_POSITION - 0.4 == b2body.getPosition().x || Player.BOB_X_POSITION + 0.4 == b2body.getPosition().x)
                        b2body.setLinearVelocity(0, 0);

                    if (Player.BOB_X_POSITION - 0.4 >= b2body.getPosition().x)
                        b2body.setLinearVelocity(1.5f, 0);

                    if (Player.BOB_X_POSITION + 0.4 <= b2body.getPosition().x)
                        b2body.setLinearVelocity(-1.5f, 0);

                    if (PlayScreen.moveY >= b2body.getPosition().x)
                        b2body.setLinearVelocity(0, 2);
                    //RAY:


                    //TODO: fix ai jump
//                    if (Player.BOB_Y_POSITION - 0.4 >= b2body.getPosition().y)
//                        b2body.setLinearVelocity(0, 1.5f);
/*
                    if (currentState != State.JUMPING) {
                        b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
                        soundPlayer.PlaySoundBob(0);
                        currentState = State.JUMPING;
                    }*/

                }

/*
                if (wallIntact) {
                    b2body.setLinearVelocity((float) -1.5, 0);
                }
*/


                PlayScreen.moveY = 0;

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

                //TODO: Fix steering at you and shooting
                //looking at you:
                if (b2body.getLinearVelocity().x < 0) {
                    runningRight = false;
                } else if (b2body.getLinearVelocity().x > 0) {
                    runningRight = true;
                }
            }
        }
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
        fixtureDef.filter.categoryBits = Application.ENEMY_AI;
        fixtureDef.filter.maskBits =
                Application.GROUND_BIT |
                        Application.ENEMY_AI |
                        Application.COIN_BIT |
                        Application.BRICK_BIT |
                        Application.ENEMY_BIT |
                        Application.WALL_BIT |
                        Application.BOB_BIT |
                        Application.GROUND_BIT |
                        Application.BULLET_BIT;


        b2body.createFixture(fixtureDef).setUserData(this);

        //RAY:
        FixtureDef fixtureDefRay = new FixtureDef();
        CircleShape rayShape = new CircleShape();
        rayShape.setRadius(6 / Application.PPM);
        fixtureDefRay.filter.categoryBits = Application.RAY;
        fixtureDefRay.filter.maskBits =
                Application.WALL_BIT;

        fixtureDefRay.shape = rayShape;
        fixtureDefRay.isSensor = true;
        rayShape.setPosition(new Vector2(0.5f, 0 / Application.PPM));
        b2body.createFixture(fixtureDefRay).setUserData(this);
        rayShape.setPosition(new Vector2(-0.5f, 0 / Application.PPM));
        b2body.createFixture(fixtureDefRay).setUserData(this);

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
            enemyFirePowerLasArray.add(new EnemyFirePowerLas(screen, (float) (b2body.getPosition().x - 0.1), (float) (b2body.getPosition().y + 0.2), runningRight));
            lastShot = System.nanoTime();
            soundPlayer.playSoundRandomYamYamFirePower();
        }
    }

    public void jump() {
        /*b2body.applyLinearImpulse(new Vector2(0, 50f), b2body.getWorldCenter(), true);
        soundPlayer.PlaySoundBob(0);
        currentState = Player.State.JUMPING;*/
    }

    public boolean isDestroyed() {
        return destroyed;
    }


}


//                //Follow you:
//                if (Player.BOB_X_POSITION - 0.4 >= b2body.getPosition().x) {
//                    b2body.setLinearVelocity((float) 1.6, -2);
//                } else if (Player.BOB_X_POSITION + 0.4 <= b2body.getPosition().x) {
//                    b2body.setLinearVelocity((float) -1.6, -2);
//                } else {
//                    b2body.setLinearVelocity((float) 0, -2);
//                }




                /*if (PlayScreen.moveX != 0) {
                    Vector2 vel = Player.target.getBody().getLinearVelocity();
                    target.getBody().setLinearVelocity((Player.BOB_X_POSITION * 7f), vel.y);
                }

                if (PlayScreen.moveY != 0) {
                    Vector2 vel = Player.target.getBody().getLinearVelocity();
                    target.getBody().setLinearVelocity(vel.x, (Player.BOB_Y_POSITION * 0.7f));
                }



                */

