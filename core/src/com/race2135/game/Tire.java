package com.race2135.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Adam on 2016. 10. 31..
 */
public class Tire {

    Body body;

    /*public enum Direction{up, down, right, left, stop};
    Direction direction = Direction.stop;*/

    //For further information, search for "bit fields"
    public static final byte DIR_UP = 1, DIR_DOWN = 2, DIR_LEFT = 4, DIR_RIGHT = 8;
    public byte direction = 0;

    float maxForwardSpeed, maxBackwardSpeed, maxDriveForce, maxLateralImpulse;


   /* public void __Tire(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.02f, 0.03f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1;
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
       /* fixtureDef.filter.categoryBits = Constants.TIRE;
        fixtureDef.filter.maskBits  = Constants.GROUND;
        Fixture fixture = body.createFixture(fixtureDef);

        body.setUserData(this);
    }*/

    public Tire(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.10f, 0.325f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1;
        fixtureDef.isSensor = true;
        Fixture fixture = body.createFixture(fixtureDef);

        //body.setUserData(this);
    }

    //TODO create proper mutators and accessors
    public void setValues(float a, float b, float c, float d){
        maxForwardSpeed = a;
        maxBackwardSpeed = b;
        maxDriveForce = c;
        maxLateralImpulse = d;

    }

    Vector2 getForwardVelocity() {
        Vector2 result = body.getWorldVector(new Vector2(0, 1)); //Forw. normal
        result.scl(result.dot(body.getLinearVelocity()));

        return result;
    }

    Vector2 getLateralVelocity() {
        Vector2 result = body.getWorldVector(new Vector2(1, 0)); //Right normal
        result.scl(result.dot(body.getLinearVelocity()));

        return result;
    }

    public void updateDrive() {
        float speed = getForwardVelocity().len();

        Vector2 driveForce = body.getWorldVector(new Vector2(0, 1));

        switch(direction & (DIR_UP | DIR_DOWN)) {
            case DIR_UP:
                driveForce.scl(maxDriveForce);
                break;
            case DIR_DOWN:
                driveForce.scl(-maxDriveForce);
                break;
            default:
                return;
        }

        body.applyForceToCenter(driveForce, true);
    }

    public void updateFriction() {
        Vector2 impulse = getLateralVelocity().scl(-0.2f);

        body.applyForceToCenter(impulse, true);
        //body.applyAngularImpulse(0.1f * body.getInertia() * -body.getAngularVelocity(), true);
        //body.applyTorque(0.1f * body.getInertia() * -body.getAngularVelocity(), true);

        Vector2 dragForce = getForwardVelocity().scl(-0.05f);

        body.applyForceToCenter(dragForce, true);
    }

    /*
    public void updateTurn() {
        float desiredTorque = 0;

        switch(direction & (DIR_LEFT | DIR_RIGHT)){
            case DIR_LEFT:
                desiredTorque = 15;
                break;
            case DIR_RIGHT:
                desiredTorque = -15;
                break;
            default:
                return;
        }
        body.applyTorque(desiredTorque, true);
    }
    */
}
