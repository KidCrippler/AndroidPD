package com.rosa.game.Tools;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.rosa.game.AndroidJDEV;
import com.rosa.game.Sprites.Enemies.Enemy;
import com.rosa.game.Sprites.Enemies.YamYam;
import com.rosa.game.Sprites.LevelsCreate.Brick;
import com.rosa.game.Sprites.LevelsCreate.Coin;
import com.rosa.game.Sprites.Enemies.Bun;
import com.rosa.game.screens.PlayScreen;


public class B2WorldCreator {

    private Array<Bun> buns;
    private Array<YamYam> yamYams;


    public B2WorldCreator(PlayScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //create ground bodies/fixtures
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / AndroidJDEV.PPM, (rect.getY() + rect.getHeight() / 2) / AndroidJDEV.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / AndroidJDEV.PPM, rect.getHeight() / 2 / AndroidJDEV.PPM);
            fdef.shape = shape;
//            fdef.filter.categoryBits = AndroidJDEV.GROUND_BIT;
//            fdef.filter.maskBits =
//                    AndroidJDEV.BOB_BIT | AndroidJDEV.ENEMY_BIT;

            body.createFixture(fdef);
        }

        //create pipe bodies/fixtures
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / AndroidJDEV.PPM, (rect.getY() + rect.getHeight() / 2) / AndroidJDEV.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / AndroidJDEV.PPM, rect.getHeight() / 2 / AndroidJDEV.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = AndroidJDEV.OBJECT_BIT;
            body.createFixture(fdef);
        }

        //create brick bodies/fixtures
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            new Brick(screen, object);
        }

        //create coin bodies/fixtures
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            new Coin(screen, object);
        }

        //create buns bodies/fixtures

        buns = new Array<Bun>();

        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            buns.add(new Bun(screen, rect.getX() / AndroidJDEV.PPM, rect.getY() / AndroidJDEV.PPM));
        }

        //create yumYums bodies/fixtures

        yamYams = new Array<YamYam>();

        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            yamYams.add(new YamYam(screen, rect.getX() / AndroidJDEV.PPM, rect.getY() / AndroidJDEV.PPM));
        }
    }


    public Array<Enemy> getEnemies() {
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(buns);
        enemies.addAll(yamYams);
        return enemies;
    }

    public void update(float dt) {
        //Remove buns from memory:
        for (Bun bun : buns) {
            bun.update(dt);
            if (bun.isDestroyed()) {
                buns.removeValue(bun, true);
                System.out.println("Bun out of list.");
            }
        }
        //Remove yamYams from memory:
        for (YamYam yam : yamYams) {
            yam.update(dt);
            if (yam.isDestroyed()) {
                yamYams.removeValue(yam, true);
                System.out.println("yum out of list.");
            }
        }
    }
}
