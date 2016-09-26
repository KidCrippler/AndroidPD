package com.rosa.game.Sprites;

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


public class Bullet extends Sprite {

    public World world;
    public Body b2body;
    boolean fireRight;
    boolean destroyed;
    boolean setToDestroy;
    private float stateTime;
    Animation fireAnimation;
    Array<TextureRegion> bullets;

    public Bullet(PlayScreen screen, float x, float y, boolean fireRight)  {

        this.fireRight = fireRight;
        this.world = screen.getWorld();
        bullets = new Array<TextureRegion>();
        for (int i = 0; i < 4; i++) {
            bullets.add(new TextureRegion(screen.getAtlas().findRegion("keen"), i * 8, 0, 8, 8));
        }
        fireAnimation = new Animation(0.2f, bullets);
        setRegion(fireAnimation.getKeyFrame(0));
        setBounds(x, y, 6 / AndroidJDEV.PPM, 6 / AndroidJDEV.PPM);
        defineBullet();
    }

    public void defineBullet() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(fireRight ? getX() + 5 / AndroidJDEV.PPM : getX() - 12 / AndroidJDEV.PPM, getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        if (!world.isLocked())
            b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(2 / AndroidJDEV.PPM);

        fdef.shape = shape;
//        fdef.filter.maskBits = AndroidJDEV.FIREBALL_BIT;
        fdef.filter.maskBits = AndroidJDEV.FIREBALL_BIT | AndroidJDEV.BRICK_BIT | AndroidJDEV.GROUND_BIT;

        b2body.createFixture(fdef).setUserData(this);


        b2body.setLinearVelocity(new Vector2(fireRight ? 2  : -2 , 0));
        b2body.setBullet(true);
        b2body.setGravityScale(0);


    }

    public void update(float dt) {

        stateTime += dt;
        setRegion(fireAnimation.getKeyFrame(stateTime, true));
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if ((stateTime > 6 || setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }
        if (b2body.getLinearVelocity().y > 2f)
            b2body.setLinearVelocity(b2body.getLinearVelocity().x, 2f);
        if ((fireRight && b2body.getLinearVelocity().x < 0) || (!fireRight && b2body.getLinearVelocity().x > 0))
            setToDestroy();

        if(this.bullets.equals(AndroidJDEV.GROUND_BIT)){
            System.out.println("touchDown");
        }


    }

    public boolean setToDestroy() {
        return setToDestroy;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}