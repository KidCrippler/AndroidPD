package com.rosa.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;


public class Bullet extends Sprite {


    public Body b2body;


    public Bullet(){


    }


    public void update(float dt){
        //position of sprite inside shape:
        setPosition(2,2);
//        setRegion();
    }
}