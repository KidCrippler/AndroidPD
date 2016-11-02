package com.rosa.game.Sprites.Enemies.EnemyAITool;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.rosa.game.Sprites.Enemies.YamYam;

public class SteeringAgent implements Steerable<Vector2> {

    private static final SteeringAcceleration<Vector2> steeringOutput =
            new SteeringAcceleration<Vector2>(new Vector2());

    Vector2 position;
    float orientation;
    Vector2 linearVelocity;
    float angularVelocity;
    float maxSpeed;
    boolean independentFacing;
    SteeringBehavior<Vector2> steeringBehavior;

    /* Here you should implement missing methods inherited from Steerable */

    @Override
    public Vector2 getPosition() {
        return null;
    }

    @Override
    public float getOrientation() {
        return 0;
    }

    @Override
    public void setOrientation(float orientation) {

    }

    // Actual implementation depends on your coordinate system.
    // Here we assume the y-axis is pointing upwards.
    @Override
    public float vectorToAngle (Vector2 vector) {
        return (float)Math.atan2(-vector.x, vector.y);
    }

    // Actual implementation depends on your coordinate system.
    // Here we assume the y-axis is pointing upwards.
    @Override
    public Vector2 angleToVector (Vector2 outVector, float angle) {
        outVector.x = -(float)Math.sin(angle);
        outVector.y = (float)Math.cos(angle);
        return outVector;
    }

    @Override
    public Location<Vector2> newLocation() {
        return null;
    }

    public void update (float delta) {
        if (steeringBehavior != null) {
            // Calculate steering acceleration
            steeringBehavior.calculateSteering(steeringOutput);

            /*
             * Here you might want to add a motor control layer filtering steering accelerations.
             *
             * For instance, a car in a driving game has physical constraints on its movement:
             * - it cannot turn while stationary
             * - the faster it moves, the slower it can turn (without going into a skid)
             * - it can brake much more quickly than it can accelerate
             * - it only moves in the direction it is facing (ignoring power slides)
             */

            // Apply steering acceleration to move this agent
//            applySteering(steeringOutput, delta);
        }
    }

    /*private void applySteering (SteeringAcceleration<Vector2> steering, float time) {
        // Update position and linear velocity. Velocity is trimmed to maximum speed
        this.position.mulAdd(linearVelocity, time);
        this.linearVelocity.mulAdd(steering.linear, time).limit(this.getMaxLinearSpeed());

        // Update orientation and angular velocity
        if (independentFacing) {
            this.orientation += angularVelocity * time;
            this.angularVelocity += steering.angular * time;
        } else {
            // For non-independent facing we have to align orientation to linear velocity
            float newOrientation = YamYam.calculateOrientationFromLinearVelocity(this);
            if (newOrientation != this.orientation) {
                this.angularVelocity = (newOrientation - this.orientation) * time;
                this.orientation = newOrientation;
            }
        }
    }*/

   /* public static float calculateOrientationFromLinearVelocity (Steerable<T> character) {
        // If we haven't got any velocity, then we can do nothing.
        if (character.getLinearVelocity().isZero(character.getZeroLinearSpeedThreshold()))
            return character.getOrientation();

        return character.vectorToAngle(character.getLinearVelocity());
    }*/


    @Override
    public Vector2 getLinearVelocity() {
        return null;
    }

    @Override
    public float getAngularVelocity() {
        return 0;
    }

    @Override
    public float getBoundingRadius() {
        return 0;
    }

    @Override
    public boolean isTagged() {
        return false;
    }

    @Override
    public void setTagged(boolean tagged) {

    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {

    }

    @Override
    public float getMaxLinearSpeed() {
        return 0;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {

    }

    @Override
    public float getMaxLinearAcceleration() {
        return 0;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {

    }

    @Override
    public float getMaxAngularSpeed() {
        return 0;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {

    }

    @Override
    public float getMaxAngularAcceleration() {
        return 0;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {

    }
}