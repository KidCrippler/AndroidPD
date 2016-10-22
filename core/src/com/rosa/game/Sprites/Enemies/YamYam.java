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
import com.rosa.game.AndroidJDEV;
import com.rosa.game.Sprites.Bob.Player;
import com.rosa.game.Tools.SoundPlayer;
import com.rosa.game.screens.PlayScreen;

public class YamYam extends Enemy {

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

    private enum State {JUMPING}

    private State currentState;


    public YamYam(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        runningRight = true;


        for (int i = 0; i < 2; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("keen"), i * 32, 0, 32, 32));

        walkAnimation = new Animation(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 16 / AndroidJDEV.PPM, 16 / AndroidJDEV.PPM);
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
        } else if (!destroyed) {
            if (b2body.isActive()) {
                b2body.setLinearVelocity(velocity);

                //Sets the position where the sprite will be drawn:
                //setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

                setRegion(walkAnimation.getKeyFrame(stateTime, true));

/*
                //Follow you:
                if (Player.BOB_X_POSITION - 0.4 >= b2body.getPosition().x) {
                    b2body.setLinearVelocity((float) 1.6, -2);
                } else if (Player.BOB_X_POSITION + 0.4 <= b2body.getPosition().x) {
                    b2body.setLinearVelocity((float) -1.6, -2);
                } else {
                    b2body.setLinearVelocity((float) 0, -2);
                }
*/


                //Follow you:
                if (Player.BOB_X_POSITION - 0.4 >= b2body.getPosition().x) {
                    b2body.setLinearVelocity((float) 1.6, -2);
                } else if (Player.BOB_X_POSITION + 0.4 <= b2body.getPosition().x) {
                    b2body.setLinearVelocity((float) -1.6, -2);
                } else {
                    b2body.setLinearVelocity((float) 0, -2);
                }




                //Shooting you:
                fire();

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

    @Override
    protected void defineEnemy() {
        FixtureDef fixtureDef = new FixtureDef();
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);
        PolygonShape head = new PolygonShape();
        Vector2[] vector2s = new Vector2[4];
        vector2s[0] = new Vector2(-5, 34).scl(1 / AndroidJDEV.PPM);
        vector2s[1] = new Vector2(5, 34).scl(1 / AndroidJDEV.PPM);
        vector2s[2] = new Vector2(-3, 3).scl(1 / AndroidJDEV.PPM);
        vector2s[3] = new Vector2(3, 3).scl(1 / AndroidJDEV.PPM);
        head.set(vector2s);
        fixtureDef.shape = head;
        fixtureDef.restitution = 0.5f;
        fixtureDef.filter.categoryBits = AndroidJDEV.ENEMY_AI;
        fixtureDef.filter.maskBits = AndroidJDEV.GROUND_BIT |
                        AndroidJDEV.ENEMY_AI |
                        AndroidJDEV.COIN_BIT |
                        AndroidJDEV.BRICK_BIT |
                        AndroidJDEV.ENEMY_BIT |
                        AndroidJDEV.OBJECT_BIT |
                        AndroidJDEV.BOB_BIT |
                        AndroidJDEV.GROUND_BIT |
                        AndroidJDEV.BULLET_BIT;


        b2body.createFixture(fixtureDef).setUserData(this);
    }

    public void draw(Batch batch) {
       /* if (!destroyed || stateTime < 1)
            super.draw(batch);*/
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
        b2body.applyLinearImpulse(new Vector2(0, 50f), b2body.getWorldCenter(), true);
        soundPlayer.PlaySoundBob(0);
        currentState = State.JUMPING;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
