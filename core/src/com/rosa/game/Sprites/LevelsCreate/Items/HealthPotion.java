package com.rosa.game.Sprites.LevelsCreate.Items;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.rosa.game.Application;
import com.rosa.game.Sprites.Enemies.EnemyUtils.Enemy;
import com.rosa.game.Sprites.Player.Player;
import com.rosa.game.Tools.SoundPlayer;
import com.rosa.game.screens.ScreenPlay;

public class HealthPotion extends Enemy {

    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private SoundPlayer soundPlayer = new SoundPlayer();

    public HealthPotion(ScreenPlay screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 2; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("keen"), i * 16, 0, 16, 16));

        walkAnimation = new Animation(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 16 / Application.PPM, 16 / Application.PPM);
        setToDestroy = false;
        destroyed = false;
        System.out.println("1");

    }

    public void update(float dt) {
        stateTime += dt;
        if (setToDestroy && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("keen"), 32, 0, 16, 16));

            stateTime = 0;
        } else if (!destroyed) {

            //Move:
//            b2body.setLinearVelocity(0, 0);
//            b2body.setLinearVelocity(velocity);

            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
    }

    @Override
    public void hitByEnemy(Enemy enemy) {
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
        vector2s[0] = new Vector2(-1, 33).scl(1 / Application.PPM);
        vector2s[1] = new Vector2(1, 33).scl(1 / Application.PPM);
        vector2s[2] = new Vector2(-4, 0).scl(1 / Application.PPM);
        vector2s[3] = new Vector2(4, 0).scl(1 / Application.PPM);
        head.set(vector2s);

        fixtureDef.filter.categoryBits = Application.POTION_BIT;

        fixtureDef.filter.maskBits = Application.GROUND_BIT |
                Application.ENEMY_DUMB_BIT |
                Application.WALL_BIT |
                Application.PLAYER_BIT |
                Application.GROUND_BIT |
                Application.BULLET_BIT;
        fixtureDef.shape = head;
        b2body.createFixture(fixtureDef).setUserData(this);
    }

    public void draw(Batch batch) {
        if (!destroyed || stateTime < 1)
            super.draw(batch);
    }

    public void reverseVelocity(boolean x, boolean y) {
        if (x)
            velocity.x = -velocity.x;
        if (y)
            velocity.y = -velocity.y;
    }

    public void setHealthPoints() {
        Player.PLAYER_X_POSITION = +30;
        System.out.println("YAMI!");
        soundPlayer.playSoundPlayer(0);
        soundPlayer.playSoundPlayer(1);
        setToDestroy = true;
        setToDestroy();
    }

    public void setToDestroy() {
        isDestroyed();
    }

    @Override
    public void takeShot(int bulletPowerOne) {
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
