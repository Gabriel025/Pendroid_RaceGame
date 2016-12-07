package com.race2135.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

/**
 * Created by Adam on 2016. 10. 31..
 */
public class Tire {
    private World world;
    CarInfo carInfo;
    Body body;

    Box2DSprite sprite;

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

        sprite = new Box2DSprite(Main.black);
        body.setUserData(sprite);
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

    public void update(float driveTorque, float brake) {
        Vector2 force = getLateralVelocity().scl(-0.5f * carInfo.mass); //Lateral friction
        body.applyForceToCenter(force, true);

        force = getForwardVelocity().scl(-0.5f * brake * carInfo.mass); //Braking
        body.applyForceToCenter(force, true);

        force = body.getWorldVector(new Vector2(0, 1)); //Engine drive
        force.scl(carInfo.tireDiameter / 2 * driveTorque);

        body.applyLinearImpulse(force, body.getWorldCenter(), true);
        //body.applyForceToCenterToCenter(driveForce, true);

        //body.applyAngularImpulse(0.1f * body.getInertia() * -body.getAngularVelocity(), true);
        //body.applyTorque(0.1f * body.getInertia() * -body.getAngularVelocity(), true);
    }

    public void render(SpriteBatch batch)
    {
        sprite.draw(batch, world);
    }
}
