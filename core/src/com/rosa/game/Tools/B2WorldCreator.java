package com.rosa.game.Tools;

import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.rosa.game.Application;
import com.rosa.game.Sprites.Bob.Player;
import com.rosa.game.Sprites.Enemies.Enemy;
import com.rosa.game.Sprites.Enemies.EnemyAITool.B2dSteeringEntity;
import com.rosa.game.Sprites.Enemies.YamYam;
import com.rosa.game.Sprites.LevelsCreate.Brick;
import com.rosa.game.Sprites.LevelsCreate.Coin;
import com.rosa.game.Sprites.Enemies.Bun;
import com.rosa.game.screens.PlayScreen;


public class B2WorldCreator {

    private Array<Bun> buns;
    private Array<YamYam> yamYams;
    private B2dSteeringEntity entity;


    public B2WorldCreator(PlayScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        Player player = screen.getPlayer();
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;


        //create ground bodies/fixtures
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / Application.PPM, (rect.getY() + rect.getHeight() / 2) / Application.PPM);

            body = world.createBody(bodyDef);

            shape.setAsBox(rect.getWidth() / 2 / Application.PPM, rect.getHeight() / 2 / Application.PPM);
            fixtureDef.shape = shape;
//            fdef.filter.categoryBits = Application.GROUND_BIT;
//            fdef.filter.maskBits =
//                    Application.BOB_BIT | Application.ENEMY_BIT;

            body.createFixture(fixtureDef);
        }

        //create pipe bodies/fixtures
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / Application.PPM, (rect.getY() + rect.getHeight() / 2) / Application.PPM);

            body = world.createBody(bodyDef);

            shape.setAsBox(rect.getWidth() / 2 / Application.PPM, rect.getHeight() / 2 / Application.PPM);
            fixtureDef.shape = shape;
            fixtureDef.filter.categoryBits = Application.OBJECT_BIT;
            body.createFixture(fixtureDef);
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
            buns.add(new Bun(screen, rect.getX() / Application.PPM, rect.getY() / Application.PPM));
        }

        //create yumYums bodies/fixtures

        yamYams = new Array<YamYam>();

        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            yamYams.add(new YamYam(screen, rect.getX() / Application.PPM, rect.getY() / Application.PPM));
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
            }
        }
        //Remove yamYams from memory:
        for (YamYam yam : yamYams) {
            yam.update(dt);
            if (yam.isDestroyed()) {
                yamYams.removeValue(yam, true);
            }
        }
    }
}
