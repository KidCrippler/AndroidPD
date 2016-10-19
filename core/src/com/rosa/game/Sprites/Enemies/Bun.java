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
import com.rosa.game.Sprites.Bob.Bullet;
import com.rosa.game.Tools.SoundPlayer;
import com.rosa.game.screens.PlayScreen;


public class Bun extends Enemy {

    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private int bunHP = 100;
    private int bulletPowerOne = 10;
    private SoundPlayer playSound = new SoundPlayer();

    public Bun(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 2; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("keen"), i * 16, 0, 16, 16));
        walkAnimation = new Animation(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 16 / AndroidJDEV.PPM, 16 / AndroidJDEV.PPM);
        setToDestroy = false;
        destroyed = false;
    }

    public void update(float dt) {
        stateTime += dt;
        if (setToDestroy && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("keen"), 32, 0, 16, 16));
            stateTime = 0;
        } else if (!destroyed) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
    }


    @Override
    protected void defineEnemy() {
        FixtureDef fdef = new FixtureDef();
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / AndroidJDEV.PPM);
        fdef.filter.categoryBits = AndroidJDEV.ENEMY_BIT;

        fdef.filter.maskBits =
                AndroidJDEV.GROUND_BIT |
                        AndroidJDEV.COIN_BIT |
                        AndroidJDEV.BRICK_BIT |
                        AndroidJDEV.ENEMY_BIT |
                        AndroidJDEV.OBJECT_BIT |
                        AndroidJDEV.PLAYER_BIT |
                        AndroidJDEV.BULLET_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        //Create the Head here:
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5, 24).scl(1 / AndroidJDEV.PPM);
        vertice[1] = new Vector2(5, 24).scl(1 / AndroidJDEV.PPM);
        vertice[2] = new Vector2(-3, 3).scl(1 / AndroidJDEV.PPM);
        vertice[3] = new Vector2(3, 3).scl(1 / AndroidJDEV.PPM);
        head.set(vertice);
        fdef.shape = head;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = AndroidJDEV.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch) {
//        if (!destroyed || stateTime < 1)
//            super.draw(batch);
    }

    @Override
    public void bulletShotOnHead(Bullet bullet) {
        playSound.playSoundRandomBunHurt();
        bunHP = bunHP - bulletPowerOne;
        System.out.println(bunHP);
        if (bunHP <= 0) {
            playSound.playSoundRandomBunDead();
            setToDestroy = true;

        }
    }

    @Override
    public void hitByEnemy(Enemy enemy) {
        reverseVelocity(true, false);
        playSound.playSoundRandomBunHurt();
    }
}
