package com.rosa.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Array;
import com.rosa.game.AndroidJDEV;
import com.rosa.game.screens.PlayScreen;

public class Bullet extends Sprite{

    Array<TextureRegion> frames;
    PlayScreen screen;
    public float velX;

    public Bullet(float velX, PlayScreen screen){
        this.velX = velX;
        this.screen = screen;

        frames = new Array<TextureRegion>();


        Rectangle rectangle = new Rectangle();

        rectangle.set(3,3,3,3);

        frames.add(new TextureRegion(screen.getAtlas().findRegion("keen"), 1 * 8, 0, 8, 8));

        System.out.println(velX);
        BodyDef bdef = new BodyDef();

        bdef.position.set(new Vector2(getX() / AndroidJDEV.PPM,getY() / AndroidJDEV.PPM));

    }

}
