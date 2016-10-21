package com.rosa.game.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.rosa.game.AndroidJDEV;
import com.rosa.game.screens.PlayScreen;


public class EnemyFirePowerLas extends Sprite {

    public World world;
    public Body b2body;
    boolean fireRight;
    boolean destroyed;
    boolean setToDestroy;
    private float stateTime;
    Animation fireAnimation;
    Array<TextureRegion> frames;
    PlayScreen screen;

    public EnemyFirePowerLas(PlayScreen screen, float x, float y, boolean fireRight) {

        this.fireRight = fireRight;
        this.world = screen.getWorld();
        this.screen = screen;


        frames = new Array<TextureRegion>();
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("keen"), i * 8, 0, 8, 8));
        }
        fireAnimation = new Animation(0.2f, frames);
        setRegion(fireAnimation.getKeyFrame(0));
        setBounds(x, y, 6 / AndroidJDEV.PPM, 6 / AndroidJDEV.PPM);
        defineBullet();
    }

    public void defineBullet() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(fireRight ? getX() + 5 / AndroidJDEV.PPM : getX() - 12 / AndroidJDEV.PPM, getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        if (!world.isLocked())
            b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(2 / AndroidJDEV.PPM);

        fixtureDef.filter.categoryBits = AndroidJDEV.ENEMY_FIREPOWER;
        fixtureDef.filter.maskBits =
                AndroidJDEV.BRICK_BIT |
                        AndroidJDEV.COIN_BIT |
                        AndroidJDEV.ENEMY_BIT |
                        AndroidJDEV.GROUND_BIT |
                        AndroidJDEV.OBJECT_BIT |
                        AndroidJDEV.ENEMY_HEAD_BIT;

        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef).setUserData(this);

        b2body.setLinearVelocity(new Vector2(fireRight ? 6 : -6, 0));
        b2body.setBullet(true);
        b2body.setGravityScale(0);
    }

    public void update(float dt) {
        stateTime += dt;

        //Bullet anim:
        setRegion(fireAnimation.getKeyFrame(stateTime, true));
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

        //Remove the bullet after 3 seconds:
        if ((stateTime > 3 || setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }
        if (b2body.getLinearVelocity().y > 2f)
            b2body.setLinearVelocity(b2body.getLinearVelocity().x, 2f);
    }

    public void setToDestroy() {
        setToDestroy = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}