package com.rosa.game.Sprites.Player;

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
import com.rosa.game.Application;
import com.rosa.game.screens.ScreenPlay;

public class PlayerBullet extends Sprite {

    public World world;
    public Body b2body;
    boolean fireRight;
    boolean destroyed;
    boolean setToDestroy;
    private float stateTime;
    private Animation fireAnimation;
    private Array<TextureRegion> frames;
    private ScreenPlay screen;

    public PlayerBullet(ScreenPlay screen, float x, float y, boolean fireRight) {

        this.fireRight = fireRight;
        this.world = screen.getWorld();
        this.screen = screen;

        frames = new Array<TextureRegion>();
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("keen"), i * 8, 0, 1, 20));
        }
        fireAnimation = new Animation(0.02f, frames);
        setRegion(fireAnimation.getKeyFrame(0));
        setBounds(x, y, 6 / Application.PPM, 6 / Application.PPM);
        definePlayerBullet();
    }

    public void definePlayerBullet() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(fireRight ? getX() + 0.20f / Application.PPM : getX() - 0.20f / Application.PPM, getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        if (!world.isLocked())
            b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(2 / Application.PPM);

        fixtureDef.filter.categoryBits = Application.BULLET_BIT;
        fixtureDef.filter.maskBits =
                        Application.ENEMY_DUMB_BIT |
                        Application.ENEMY_AI_BIT |
                        Application.WALL_BIT;
        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef).setUserData(this);

        b2body.setLinearVelocity(new Vector2(fireRight ? 6 : -6, 0));
        b2body.setBullet(true);
        b2body.setGravityScale(0);
    }

    public void update(float dt) {
        stateTime += dt;

        //PlayerBullet anim:
        setRegion(fireAnimation.getKeyFrame(stateTime, true));
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

        //Remove the bullet after 8 seconds:
        if ((stateTime > 8 || setToDestroy) && !destroyed) {
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