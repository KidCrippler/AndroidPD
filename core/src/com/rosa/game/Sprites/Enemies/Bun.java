package com.rosa.game.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.rosa.game.AndroidJDEV;
import com.rosa.game.Sprites.Bob.Player;
import com.rosa.game.screens.PlayScreen;


public class Bun extends Enemy {
    public static final int KICK_LEFT = -2;
    public static final int KICK_RIGHT = 2;
    public enum State {WALKING, MOVING_SHELL, STANDING_SHELL}
    public State currentState;
    public State previousState;
    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private TextureRegion shell;
    private boolean setToDestroy;
    private boolean destroyed;


    public Bun(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(screen.getAtlas().findRegion("keen"), 0, 0, 16, 24));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("keen"), 16, 0, 16, 24));
        shell = new TextureRegion(screen.getAtlas().findRegion("keen"), 64, 0, 16, 24);
        walkAnimation = new Animation(0.2f, frames);
        currentState = previousState = State.WALKING;

        setBounds(getX(), getY(), 16 / AndroidJDEV.PPM, 24 / AndroidJDEV.PPM);

    }

    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / AndroidJDEV.PPM);
        fdef.filter.categoryBits = AndroidJDEV.ENEMY_BIT;
        fdef.filter.maskBits = AndroidJDEV.GROUND_BIT |
                AndroidJDEV.COIN_BIT |
                AndroidJDEV.BRICK_BIT |
                AndroidJDEV.ENEMY_BIT |
                AndroidJDEV.OBJECT_BIT |
                AndroidJDEV.PLAYER_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);
        shape.setPosition(new Vector2(0, -14 / AndroidJDEV.PPM));
        b2body.createFixture(fdef).setUserData(this);
    }

    public TextureRegion getFrame(float dt){
        TextureRegion region;

        switch (currentState){
            case MOVING_SHELL:
            case STANDING_SHELL:
                region = shell;
                break;
            case WALKING:
            default:
                region = walkAnimation.getKeyFrame(stateTime, true);
                break;
        }

        if(velocity.x > 0 && region.isFlipX() == false){
            region.flip(true, false);
        }
        if(velocity.x < 0 && region.isFlipX() == true){
            region.flip(true, false);
        }
        stateTime = currentState == previousState ? stateTime + dt : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;
    }

    @Override
    public void update(float dt) {
        setRegion(getFrame(dt));
        if(currentState == State.STANDING_SHELL && stateTime > 5){
            currentState = State.WALKING;
            velocity.x = 1;
        }

        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - 8 /AndroidJDEV.PPM);
        b2body.setLinearVelocity(velocity);
    }


    @Override
    public void hitByEnemy(Enemy enemy) {
        reverseVelocity(true, false);
    }
}
