package com.rosa.game.Sprites.Enemies;

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


public class DumbBun extends Enemy {

    //TODO: Bun can kick you far away!

    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private int bunHP = 100;
    private SoundPlayer playSound = new SoundPlayer();

    public DumbBun(ScreenPlay screen, float x, float y) {
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
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }

        if(bunHP <= 0 || b2body.getPosition().y < -1){
            dead();
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
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-1, 33).scl(1 / Application.PPM);
        vertice[1] = new Vector2(1, 33).scl(1 / Application.PPM);
        vertice[2] = new Vector2(-4, 0).scl(1 / Application.PPM);
        vertice[3] = new Vector2(4, 0).scl(1 / Application.PPM);
        head.set(vertice);

        fixtureDef.filter.categoryBits = Application.ENEMY_DUMB_BIT;

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

    @Override
    public void hitByEnemy(Enemy enemy) {
        reverseVelocity(true, false);
        playSound.playSoundRandomBunHurt();
    }

    public void reverseVelocity(boolean x, boolean y) {
        if (x)
            velocity.x = -velocity.x;
        if (y)
            velocity.y = -velocity.y;
    }

    public void takeShot(int bulletPower) {
        bunHP = bunHP - bulletPower;
        System.out.println(bunHP);
    }

    private void dead(){
        playSound.playSoundRandomBunHurt();
        System.out.println("dead!!!");
        setToDestroy = true;
        setToDestroy();
    }

    public void setToDestroy() {
        isDestroyed();
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
