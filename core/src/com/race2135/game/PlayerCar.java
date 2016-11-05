package com.race2135.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Adam on 2016. 11. 01.
 */
public class PlayerCar {

    Body body;
    Array<Tire> tires = new Array<Tire>();
    RevoluteJoint leftJoint, rightJoint;

    byte input = 0;

    public void __PlayerCar(World world) {
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(new Vector2(3, 3));

        body = world.createBody(bodyDef);
        body.setAngularDamping(3);

        Vector2[] vertices = new Vector2[8];

        vertices[0] = new Vector2(1.5f / Main.PPM * 4, 0);
        vertices[1] = new Vector2(3/ Main.PPM * 4, 2.5f/ Main.PPM * 4);
        vertices[2] = new Vector2(2.8f/ Main.PPM * 4, 5.5f/ Main.PPM * 4);
        vertices[3] = new Vector2(1/ Main.PPM * 4, 10/ Main.PPM * 4);
        vertices[4] = new Vector2(-1/ Main.PPM * 4, 10/ Main.PPM * 4);
        vertices[5] = new Vector2(-2.8f/ Main.PPM * 4, 5.5f/ Main.PPM * 4);
        vertices[6] = new Vector2(-3/ Main.PPM * 4, 2.5f/ Main.PPM * 4);
        vertices[7] = new Vector2(-1.5f/ Main.PPM * 4, 0/ Main.PPM * 4);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 0.1f;

        body.createFixture(fixtureDef);

        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.bodyA = body;
        jointDef.enableLimit = true;
        jointDef.lowerAngle = 0;
        jointDef.upperAngle = 0;
        jointDef.localAnchorB.setZero();

        float maxForwardSpeed = 60;
        float maxBackwardSpeed = -10;
        float backTireMaxDriveForce = 10;
        float frontTireMaxDriveForce = 0;
        float backTireMaxLateralImpulse = 0;
        float frontTireMaxLateralImpulse = 0;

        Tire tire = new Tire(world);
        tire.setValues(maxForwardSpeed, maxBackwardSpeed,
                backTireMaxDriveForce, backTireMaxLateralImpulse);
        jointDef.bodyB = tire.body;
        jointDef.localAnchorA.set(-3/ Main.PPM * 4, 0.75f/ Main.PPM * 4);
        world.createJoint(jointDef);
        tires.add(tire);

        tire = new Tire(world);
        tire.setValues(maxForwardSpeed, maxBackwardSpeed,
                backTireMaxDriveForce, backTireMaxLateralImpulse);
        jointDef.bodyB = tire.body;
        jointDef.localAnchorA.set(3/ Main.PPM * 4, 0.75f/ Main.PPM * 4);
        world.createJoint(jointDef);
        tires.add(tire);

        tire = new Tire(world);
        tire.setValues(maxForwardSpeed, maxBackwardSpeed,
                frontTireMaxDriveForce, frontTireMaxLateralImpulse);
        jointDef.bodyB = tire.body;
        jointDef.localAnchorA.set(-3/ Main.PPM * 4, 8.5f/ Main.PPM * 4);
        leftJoint = (RevoluteJoint)world.createJoint(jointDef);
        tires.add(tire);

        tire = new Tire(world);
        tire.setValues(maxForwardSpeed, maxBackwardSpeed,
                frontTireMaxDriveForce, frontTireMaxLateralImpulse);
        jointDef.bodyB = tire.body;
        jointDef.localAnchorA.set(3/ Main.PPM * 4, 8.5f/ Main.PPM * 4);
        rightJoint = (RevoluteJoint)world.createJoint(jointDef);
        tires.add(tire);
    }

    public PlayerCar(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);
        body.setAngularDamping(3);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.9f, 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 0.1f;

        body.createFixture(fixtureDef);

        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.bodyA = body;
        jointDef.enableLimit = true;
        jointDef.lowerAngle = 0;
        jointDef.upperAngle = 0;
        jointDef.localAnchorB.setZero();

        float maxForwardSpeed = 120;
        float maxBackwardSpeed = -20;
        float backTireMaxDriveForce = 100;
        float frontTireMaxDriveForce = 0;
        float backTireMaxLateralImpulse = 0;
        float frontTireMaxLateralImpulse = 0;

        Tire tire = new Tire(world);
        tire.setValues(maxForwardSpeed, maxBackwardSpeed,
                backTireMaxDriveForce, backTireMaxLateralImpulse);
        jointDef.bodyB = tire.body;
        jointDef.localAnchorA.set(-0.9f, -1.6f);
        world.createJoint(jointDef);
        tires.add(tire);

        tire = new Tire(world);
        tire.setValues(maxForwardSpeed, maxBackwardSpeed,
                backTireMaxDriveForce, backTireMaxLateralImpulse);
        jointDef.bodyB = tire.body;
        jointDef.localAnchorA.set(0.9f, -1.6f);
        world.createJoint(jointDef);
        tires.add(tire);

        tire = new Tire(world);
        tire.setValues(maxForwardSpeed, maxBackwardSpeed,
                frontTireMaxDriveForce, frontTireMaxLateralImpulse);
        jointDef.bodyB = tire.body;
        jointDef.localAnchorA.set(-0.9f, 1.6f);
        leftJoint = (RevoluteJoint)world.createJoint(jointDef);
        tires.add(tire);

        tire = new Tire(world);
        tire.setValues(maxForwardSpeed, maxBackwardSpeed,
                frontTireMaxDriveForce, frontTireMaxLateralImpulse);
        jointDef.bodyB = tire.body;
        jointDef.localAnchorA.set(0.9f, 1.6f);
        rightJoint = (RevoluteJoint)world.createJoint(jointDef);
        tires.add(tire);
    }

    public void update() {
        inputHandler();
        for (Tire tire : tires) {
            tire.updateFriction();
            tire.updateDrive();
        }

        float lockAngle = 20 * Main.DEGTORAD;
        float turnSpeed = 80 * Main.DEGTORAD;
        float turnPerStep = turnSpeed / 60.0f;
        float desiredAngle = 0;

        switch (input & (Tire.DIR_LEFT | Tire.DIR_RIGHT)) {
            case Tire.DIR_LEFT:
                desiredAngle = lockAngle;
                break;
            case Tire.DIR_RIGHT:
                desiredAngle = -lockAngle;
                break;
            default:
                break;
        }

        float angleNow = leftJoint.getJointAngle();
        float angleToTurn = desiredAngle - angleNow;
        angleToTurn = Math.max(-turnPerStep, Math.min(angleToTurn, turnPerStep));
        float newAngle = angleNow + angleToTurn;

        leftJoint.setLimits(newAngle, newAngle);
        rightJoint.setLimits(newAngle, newAngle);
    }

    private void inputHandler() {
        input = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            input |= Tire.DIR_UP;

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            input |= Tire.DIR_DOWN;

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            input |= Tire.DIR_RIGHT;

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            input |= Tire.DIR_LEFT;

        for (Tire tire : tires)
            tire.direction = input;

        //Gdx.app.debug("Input", ((input & 1) == 1 ? "U" : "") + ((input & 2) == 2 ? "D" : "")
        //        + ((input & 4) == 4 ? "L" : "") + ((input & 8) == 8 ? "R" : ""));
    }
}
