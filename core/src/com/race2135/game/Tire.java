package com.race2135.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
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
    private World world;
    CarInfo carInfo;
    Body body;

    public static final byte DIR_UP = 1, DIR_DOWN = 2, DIR_LEFT = 4, DIR_RIGHT = 8;

    public Tire(World world, CarInfo info) {
        this.world = world;
        carInfo = info;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(carInfo.tireThickness / 2, carInfo.tireDiameter / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1;
        fixtureDef.isSensor = true;
        Fixture fixture = body.createFixture(fixtureDef);
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

    public void updateDrive(float driveTorque) {
        /*
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
        */

        Vector2 driveForce = body.getWorldVector(new Vector2(0, 1));
        driveForce.scl(carInfo.tireDiameter / 2 * driveTorque);

        body.applyLinearImpulse(driveForce, body.getWorldCenter(), true);
        //body.applyForceToCenterToCenter(driveForce, true);
    }

    public void updateFriction(float brake) {
        Vector2 impulse = getLateralVelocity().scl(-0.2f * carInfo.mass);

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
