package com.rosa.game.Sprites.Enemies.EnemyUtils;

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


public class EnemyBullet extends Sprite {

    private World world;
    private Body b2body;
    private boolean fireRight;
    private boolean destroyed;
    private boolean setToDestroy;
    private float stateTime;
    private Animation fireAnimation;
    private Array<TextureRegion> frames;
    private ScreenPlay screen;

    public EnemyBullet(ScreenPlay screen, float x, float y, boolean fireRight) {

        this.fireRight = fireRight;
        this.world = screen.getWorld();
        this.screen = screen;

        frames = new Array<TextureRegion>();
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("keen"), i * 8, 0, 8, 8));
        }
        fireAnimation = new Animation(0.2f, frames);
        setRegion(fireAnimation.getKeyFrame(0));
        setBounds(x, y, 6 / Application.PPM, 6 / Application.PPM);
        defineBullet();
    }

    private void defineBullet() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(fireRight ? getX() + 0.20f / Application.PPM : getX() - 0.20f / Application.PPM, getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        if (!world.isLocked())
            b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(2 / Application.PPM);

        fixtureDef.filter.categoryBits = Application.ENEMY_BULLET_BIT;
        fixtureDef.filter.maskBits =
                        Application.WALL_BIT |
                        Application.PLAYER_BIT;
        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef).setUserData(this);

        b2body.setLinearVelocity(new Vector2(fireRight ? 3 : -3, 0));
        b2body.setBullet(true);
        b2body.setGravityScale(0);
    }

    public void update(float dt) {
        stateTime += dt;

        //PlayerBullet anim:
        setRegion(fireAnimation.getKeyFrame(stateTime, true));
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

        //Remove the bullet after 3 seconds:
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