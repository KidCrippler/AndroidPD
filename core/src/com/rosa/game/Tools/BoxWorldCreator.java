package com.rosa.game.Tools;

import com.badlogic.gdx.graphics.g2d.Batch;
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
import com.rosa.game.Application;
import com.rosa.game.Sprites.Enemies.DumbBun;
import com.rosa.game.Sprites.Enemies.EnemyUtils.ObjectManager;
import com.rosa.game.Sprites.Enemies.AIYamYam;
import com.rosa.game.Sprites.LevelItems.Misc.HealthPotion;
import com.rosa.game.Sprites.LevelItems.WeaponStorage.WeaponBlowzier;
import com.rosa.game.screens.ScreenMainGamePlay;

public class BoxWorldCreator {

    private Array<DumbBun> buns;
    private Array<AIYamYam> yamYams;
    private Array<HealthPotion> healthPotions;
    private Array<WeaponBlowzier> weaponBlizzIcers;
    public static TiledMap map;
    private SoundPlayer soundPlayer = new SoundPlayer();

    public BoxWorldCreator(ScreenMainGamePlay screen) {
        World world = screen.getWorld();
        map = screen.getMap();
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        //Create ground:
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / Application.PPM, (rect.getY() + rect.getHeight() / 2) / Application.PPM);
            body = world.createBody(bodyDef);
            shape.setAsBox(rect.getWidth() / 2 / Application.PPM, rect.getHeight() / 2 / Application.PPM);
            fixtureDef.filter.categoryBits = Application.GROUND_BIT;
            fixtureDef.filter.maskBits = Application.PLAYER_BIT |
                    Application.ENEMY_AI_BIT |
                    Application.ENEMY_DUMB_BIT |
                    Application.POTION_BIT |
                    Application.WEAPON_BLOWZIER_BIT;
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }

        //Create walls:
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / Application.PPM, (rect.getY() + rect.getHeight() / 2) / Application.PPM);
            body = world.createBody(bodyDef);
            shape.setAsBox(rect.getWidth() / 2 / Application.PPM, rect.getHeight() / 2 / Application.PPM);
            fixtureDef.shape = shape;
            fixtureDef.filter.categoryBits = Application.WALL_BIT;
            fixtureDef.filter.maskBits = Application.PLAYER_BIT |
                    Application.ENEMY_AI_BIT |
                    Application.ENEMY_DUMB_BIT |
                    Application.BULLET_BIT |
                    Application.ENEMY_BULLET_BIT |
                    Application.RAY_C_JUMP_BIT |
                    Application.RAY_C_CLIMB_BIT |
                    Application.POTION_BIT |
                    Application.WEAPON_BLOWZIER_BIT;
            body.createFixture(fixtureDef);
        }

        //Create HealthPotion:
        healthPotions = new Array<HealthPotion>();

        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            healthPotions.add(new HealthPotion(screen, rect.getX() / Application.PPM, rect.getY() / Application.PPM));
        }

        //Create WeaponBlowzier:
        weaponBlizzIcers = new Array<WeaponBlowzier>();

        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            weaponBlizzIcers.add(new WeaponBlowzier(screen, rect.getX() / Application.PPM, rect.getY() / Application.PPM));
        }

        //Create Dumb Bun:
        buns = new Array<DumbBun>();

        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            buns.add(new DumbBun(screen, rect.getX() / Application.PPM, rect.getY() / Application.PPM));
        }

        //Create AI YamYam:
        yamYams = new Array<AIYamYam>();

        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            yamYams.add(new AIYamYam(screen, rect.getX() / Application.PPM, rect.getY() / Application.PPM));
        }


    }

    public Array<ObjectManager> getEnemies() {
        Array<ObjectManager> enemies = new Array<ObjectManager>();
        enemies.addAll(buns);
        enemies.addAll(yamYams);
        enemies.addAll(healthPotions);
        enemies.addAll(weaponBlizzIcers);
        return enemies;
    }

    public void update(float dt) {
        //Remove buns from memory:
        for (DumbBun dumbBun : buns) {
            dumbBun.update(dt);
            if (dumbBun.isDestroyed()) {
                buns.removeValue(dumbBun, true);
                soundPlayer.playSoundRandomBunDead();
            }
        }
        //Remove yamYams from memory:
        for (AIYamYam yam : yamYams) {
            yam.update(dt);
            if (yam.isDestroyed()) {
                yamYams.removeValue(yam, true);
                soundPlayer.playSoundRandomBunDead();
            }
        }
        //Remove yamYams from memory:
        for (HealthPotion hp : healthPotions) {
            hp.update(dt);
            if (hp.isDestroyed()) {
                healthPotions.removeValue(hp, true);
                soundPlayer.playSoundRandomBunDead();
                //TODO add drink sound
            }
        }

        //Remove yamYams from memory:
        for (WeaponBlowzier wb : weaponBlizzIcers) {
            wb.update(dt);
            if (wb.isDestroyed()) {
                weaponBlizzIcers.removeValue(wb, true);
                soundPlayer.playSoundRandomBunDead();
                //TODO add Weapon change
            }
        }
    }

    public void draw(Batch batch) {
        //draw yamYams
        for (AIYamYam yam : yamYams) {
            yam.draw(batch);
        }
        //TODO draw the rest
    }
}
