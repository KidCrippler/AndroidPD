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
    private Player player;

    public YamYam(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 2; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("keen"), i * 32, 0, 32, 32));
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
            setRegion(new TextureRegion(screen.getAtlas().findRegion("keen"), 11, 0, 22, 12));
            stateTime = 0;
        } else if (!destroyed) {
            b2body.setLinearVelocity(velocity);

            //Sets the position where the sprite will be drawn:
            //setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

            b2body.setLinearVelocity(0, 0);

            // TODO: 20/10/2016
            System.out.println("where? " + Player.BOB_X_POSITION );
//            System.out.println(getX()+" "+ getY());

            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
    }


    @Override
    protected void defineEnemy() {
        FixtureDef fixtureDef = new FixtureDef();
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / AndroidJDEV.PPM);
        fixtureDef.filter.categoryBits = AndroidJDEV.ENEMY_BIT;

        fixtureDef.filter.maskBits =
                AndroidJDEV.GROUND_BIT |
                        AndroidJDEV.COIN_BIT |
                        AndroidJDEV.BRICK_BIT |
                        AndroidJDEV.ENEMY_BIT |
                        AndroidJDEV.OBJECT_BIT |
                        AndroidJDEV.BOB_BIT |
                        AndroidJDEV.GROUND_BIT |
                        AndroidJDEV.BULLET_BIT;
        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef).setUserData(this);

        //Create the Head here:
        PolygonShape head = new PolygonShape();
        Vector2[] vector2s = new Vector2[4];
        vector2s[0] = new Vector2(-5, 34).scl(1 / AndroidJDEV.PPM);
        vector2s[1] = new Vector2(5, 34).scl(1 / AndroidJDEV.PPM);
        vector2s[2] = new Vector2(-3, 3).scl(1 / AndroidJDEV.PPM);
        vector2s[3] = new Vector2(3, 3).scl(1 / AndroidJDEV.PPM);
        head.set(vector2s);
        fixtureDef.shape = head;
        fixtureDef.restitution = 0.5f;
        fixtureDef.filter.categoryBits = AndroidJDEV.ENEMY_HEAD_BIT;
        b2body.createFixture(fixtureDef).setUserData(this);
    }

    public void draw(Batch batch) {
       /* if (!destroyed || stateTime < 1)
            super.draw(batch);*/
    }

    @Override
    public void hitByEnemy(Enemy enemy) {
//        reverseVelocity(true, false);
        playSound.playSoundRandomBunHurt();
    }

    public void reverseVelocity(boolean x, boolean y) {
/*        if (x)
            velocity.x = -velocity.x;
        if (y)
            velocity.y = -velocity.y;*/
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

    public boolean isDestroyed() {
        return destroyed;
    }
}
