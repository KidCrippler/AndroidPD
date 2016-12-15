package com.rosa.game.Sprites.Enemies.EnemyUtils;


import com.badlogic.gdx.ai.utils.Collision;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.ai.utils.RaycastCollisionDetector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.ClosestNotMeRayResultCallback;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;


public class BulletRaycastCollisionDetector implements RaycastCollisionDetector<Vector3> {

    btCollisionWorld world;

    ClosestRayResultCallback callback;

    public BulletRaycastCollisionDetector (btCollisionWorld world, btCollisionObject me) {
        this.world = world;
        this.callback = new ClosestNotMeRayResultCallback(me);
    }

    @Override
    public boolean collides (Ray<Vector3> ray) {
        return findCollision(null, ray);
    }

    @Override
    public boolean findCollision (Collision<Vector3> outputCollision, Ray<Vector3> inputRay) {
        // reset because we reuse the callback
        callback.setCollisionObject(null);

        world.rayTest(inputRay.start, inputRay.end, callback);

        if (outputCollision != null) {
            callback.getHitPointWorld(outputCollision.point);
            callback.getHitNormalWorld(outputCollision.normal);
        }


        return callback.hasHit();
    }

}