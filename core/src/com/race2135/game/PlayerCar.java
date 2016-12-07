package com.race2135.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
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

import static com.badlogic.gdx.Gdx.input;

/**
 * Created by Adam on 2016. 11. 01.
 */
public class PlayerCar {
    World world;
    CarInfo carInfo;
    GameInput gameInput;

    //Box2D related fields
    Body body;
    Array<Tire> tires = new Array<Tire>();
    RevoluteJoint leftJoint, rightJoint;

    //Texture and sprite
    Texture texture;
    Box2DSprite sprite;

    //Drive related fields
    float engineRPM = 0;
    Sound engineSoundLow, engineSoundHigh;
    long engineSoundLowID = -1, engineSoundHighID = -1;

    public PlayerCar(World world, CarInfo info, Vector2 position, float angle, GameInput gameInput) {
        this.world = world;
        carInfo = info;
        this.gameInput = gameInput;

        engineSoundLow = Gdx.audio.newSound(Gdx.files.internal(carInfo.engineSoundLowPath));
        engineSoundHigh = Gdx.audio.newSound(Gdx.files.internal(carInfo.engineSoundHighPath));

        //Box2D stuff
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);
        bodyDef.angle = angle;

        body = world.createBody(bodyDef);
        body.setAngularDamping(3);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(carInfo.width / 2, carInfo.length / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = carInfo.mass / (carInfo.width * carInfo.length);
        fixtureDef.friction = 1;

        texture = new Texture(Gdx.files.internal("car.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite = new Box2DSprite(texture);

        body.setUserData(sprite);
        body.createFixture(fixtureDef);

        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.bodyA = body;
        jointDef.enableLimit = true;
        jointDef.lowerAngle = 0;
        jointDef.upperAngle = 0;
        jointDef.localAnchorB.setZero();

        Tire tire = new Tire(world, carInfo);
        jointDef.bodyB = tire.body;
        jointDef.localAnchorA.set(-0.85f, -1.35f);
        world.createJoint(jointDef);
        tires.add(tire);

        tire = new Tire(world, carInfo);
        jointDef.bodyB = tire.body;
        jointDef.localAnchorA.set(0.85f, -1.35f);
        world.createJoint(jointDef);
        tires.add(tire);

        tire = new Tire(world, carInfo);
        jointDef.bodyB = tire.body;
        jointDef.localAnchorA.set(-0.85f, 1.35f);
        leftJoint = (RevoluteJoint)world.createJoint(jointDef);
        tires.add(tire);

        tire = new Tire(world, carInfo);
        jointDef.bodyB = tire.body;
        jointDef.localAnchorA.set(0.85f, 1.35f);
        rightJoint = (RevoluteJoint)world.createJoint(jointDef);
        tires.add(tire);
    }

    public void update() {
        float maxRPM = 4500, idleRPM = 900;
        if(carInfo.gearRatios[gameInput.getGear() + 1] != 0)
            maxRPM /= carInfo.gearRatios[gameInput.getGear() + 1];
        if(maxRPM < 0) maxRPM = -maxRPM;

        engineRPM = MathUtils.lerp(engineRPM,
                (maxRPM - idleRPM) * gameInput.getThrottle() + idleRPM, 0.02f);

        if(engineSoundLowID == -1) engineSoundLowID = engineSoundLow.loop();
        if(engineSoundHighID == -1) engineSoundHighID = engineSoundHigh.loop();
        //Pich has to be in the range of [0.5f; 2f], so two sounds are needed
        if(engineRPM < carInfo.engineSoundLowRPM * 2)
        {
            engineSoundLow.setVolume(engineSoundLowID, (gameInput.getThrottle() + 1) / 2);
            engineSoundLow.setPitch(engineSoundLowID, engineRPM / carInfo.engineSoundLowRPM);

            engineSoundHigh.setVolume(engineSoundHighID, 0);
        } else
        {
            engineSoundHigh.setVolume(engineSoundHighID, (gameInput.getThrottle() + 1) / 2);
            engineSoundHigh.setPitch(engineSoundHighID, engineRPM / carInfo.engineSoundHighRPM);

            engineSoundLow.setVolume(engineSoundLowID, 0);
        }

        float engineTorque = simpleTorqueCurve(engineRPM);

        //Gdx.app.log("RPM", "" + engineRPM);

        for (Tire tire : tires) {
            tire.update(engineTorque * carInfo.gearRatios[gameInput.getGear() + 1],
                    gameInput.getBraking());
        }

        /*
        float lockAngle = 20 * MathUtils.degreesToRadians;
        float turnSpeed = 80 * MathUtils.degreesToRadians;
        float turnPerStep = turnSpeed / 60.0f;
        float desiredAngle = 0;

        float angleNow = leftJoint.getJointAngle();
        float angleToTurn = desiredAngle - angleNow;
        angleToTurn = Math.max(-turnPerStep, Math.min(angleToTurn, turnPerStep));
        float newAngle = angleNow + angleToTurn;
        */

        Vector2 acc = new Vector2(Gdx.input.getAccelerometerX(), Gdx.input.getAccelerometerY());

        float angle = -acc.angleRad() / 2;

        if(angle <= -180) angle += 360;
        angle = MathUtils.clamp(angle, -45, 45);

        leftJoint.setLimits(angle, angle);
        rightJoint.setLimits(angle, angle);
    }

    //Rendering
    public void render(SpriteBatch batch)
    {
        for(Tire tire : tires)
            tire.render(batch);

        sprite.draw(batch, world);
    }

    float simpleTorqueCurve(float RPM)
    {
        if(RPM < 900f) return MathUtils.lerp(0f, 220f, RPM / 900f);
        if(RPM < 4500f) return MathUtils.lerp(220f, 310f, (RPM - 900f) / 4500f);
        return MathUtils.lerp(310f, 200f, (RPM - 4500f) / 7500f);
    }
}
