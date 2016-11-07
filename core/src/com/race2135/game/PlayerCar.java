package com.race2135.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;

import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

/**
 * Created by Adam on 2016. 11. 01.
 */
public class PlayerCar {

    Body body;
    Array<Tire> tires = new Array<Tire>();
    RevoluteJoint leftJoint, rightJoint;

    Box2DSprite sprite;
    Texture texture;


    private byte input = 0;

    public PlayerCar(World world) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(90,80);

        body = world.createBody(bodyDef);
        body.setAngularDamping(3);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.9f, 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 0.2f;
        fixtureDef.friction = 1;

        texture = new Texture(Gdx.files.internal("car.png"));
        sprite = new Box2DSprite(texture);

        body.setUserData(sprite);
        body.createFixture(fixtureDef);

        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.bodyA = body;
        jointDef.enableLimit = true;
        jointDef.lowerAngle = 0;
        jointDef.upperAngle = 0;
        jointDef.localAnchorB.setZero();

        float maxForwardSpeed = 120;
        float maxBackwardSpeed = -20;
<<<<<<< HEAD
        float backTireMaxDriveForce = 10;
=======
        float backTireMaxDriveForce = 30;
>>>>>>> origin/master
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

        float lockAngle = 30 * Main.DEGTORAD;
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
