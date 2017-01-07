package com.rosa.game.Sprites.LevelItems.WeaponStorage;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.rosa.game.Application;
import com.rosa.game.Sprites.Enemies.EnemyUtils.ObjectManager;
import com.rosa.game.Sprites.Player.Player;
import com.rosa.game.Sprites.Player.PlayerUtils.PlayerBullet;
import com.rosa.game.Tools.SoundPlayer;
import com.rosa.game.screens.ScreenMainGamePlay;

public class WeaponBlowzier extends ObjectManager {

    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private SoundPlayer soundPlayer = new SoundPlayer();

    public WeaponBlowzier(ScreenMainGamePlay screen, float x, float y) {
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
        fixtureDefRayOfClimb.filter.categoryBits = Application.WEAPON_BLOWZIER_BIT;

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

    public void setWeaponBlowzier() {

        System.out.println("Weapon in use: WeaponBlowzier");
        soundPlayer.playSoundPlayer(0);
        soundPlayer.playSoundPlayer(1);
        Player.bullet_rate = 1000000000L;
        PlayerBullet.bullet_hp_down = 100;
        PlayerBullet.bullet_speed = 1;
        PlayerBullet.bullet_size = 10;
        setToDestroy = true;
        setToDestroy();

        //Weapon change back:
        float delay = 10;

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                System.out.println("Weapon in use: Default");
                soundPlayer.playSoundPlayer(1);
                Player.bullet_rate = Player.default_bullet_rate;
                PlayerBullet.bullet_hp_down = PlayerBullet.default_bullet_hp_down;
                PlayerBullet.bullet_speed = PlayerBullet.default_bullet_speed;
                PlayerBullet.bullet_size = PlayerBullet.default_bullet_size;

            }
        }, delay);

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
