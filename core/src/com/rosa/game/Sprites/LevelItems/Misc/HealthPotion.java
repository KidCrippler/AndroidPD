package com.rosa.game.Sprites.LevelItems.Misc;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.rosa.game.Application;
import com.rosa.game.Sprites.Enemies.EnemyUtils.ObjectManager;
import com.rosa.game.Sprites.Player.Player;
import com.rosa.game.Tools.SoundPlayer;
import com.rosa.game.screens.ScreenMainGamePlay;

public class HealthPotion extends ObjectManager {

    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private SoundPlayer soundPlayer = new SoundPlayer();

    public HealthPotion(ScreenMainGamePlay screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 2; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("keen"), i * 16, 0, 16, 16));

        walkAnimation = new Animation(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 16 / Application.PPM, 16 / Application.PPM);
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

            //Move:
            potionRun(false);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
    }

    private void potionRun(boolean potionRun) {
        if (potionRun) {
            b2body.setLinearVelocity(velocity);
        }
    }

    @Override
    public void hitByObject(ObjectManager Health) {
    }

    @Override
    protected void defineSpriteObject() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDefRayOfClimb = new FixtureDef();
        CircleShape circleRayOfClimb = new CircleShape();
        fixtureDefRayOfClimb.shape = circleRayOfClimb;
        circleRayOfClimb.setRadius(4 / Application.PPM);
        fixtureDefRayOfClimb.filter.categoryBits = Application.POTION_BIT;

        b2body.createFixture(fixtureDefRayOfClimb).setUserData(this);
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
        Player.PLAYER_TOTAL_HEALTH += 20;

        if (Player.PLAYER_TOTAL_HEALTH >= 100) {
            Player.PLAYER_TOTAL_HEALTH = 100;
        }

        System.out.println(Player.PLAYER_TOTAL_HEALTH);

        soundPlayer.playSoundPlayer(0);
        soundPlayer.playSoundPlayer(1);
        setToDestroy = true;
        setToDestroy();
    }

    public void setToDestroy() {
        isDestroyed();
    }

    @Override
    public void takeShot(float bulletPowerOne) {
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
