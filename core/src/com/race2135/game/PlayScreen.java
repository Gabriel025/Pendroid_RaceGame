package com.race2135.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayScreen implements Screen {
    private OrthographicCamera gamecam;
    private Viewport viewport;
    private World world;
    private Box2DDebugRenderer b2dr;

    private Main game;
    GameInstanceInfo instanceInfo;

    PlayerCar playerCar;
    GameInput gameInput;

    SpriteBatch spriteBatch;
    Texture texture;

    public PlayScreen(Main game, GameInstanceInfo instanceInfo) {
        this.game = game;
        this.instanceInfo = instanceInfo;

        gamecam = new OrthographicCamera();
        viewport = new FillViewport(80, 60, gamecam);

        gameInput = new GameInput(7);

        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        spriteBatch = new SpriteBatch();

        playerCar = new PlayerCar(world, instanceInfo.carInfo, gameInput);
        playerCar.body.setTransform(105, 80, 0);


        texture = new Texture(Gdx.files.internal("race.png"));

        BodyLoader bodyLoader = new BodyLoader(Gdx.files.internal("map_physics1.json"));

        BodyDef bd = new BodyDef();
        bd.position.set(0, 0);
        bd.type = BodyDef.BodyType.StaticBody;

        FixtureDef fd = new FixtureDef();
        fd.density = 1;
        fd.friction = 0.5f;
        fd.restitution = 0.3f;

        Body body = world.createBody(bd);

        bodyLoader.attachFixture(body, "Name", fd, 400);
    }

    public void show() {
        Gdx.input.setInputProcessor(gameInput);
    }

    public void update(float dt) {
        playerCar.update();

        world.step(1f/60f, 6, 2);
    }

    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gamecam.position.set(playerCar.body.getWorldCenter(), gamecam.position.z);
        gamecam.up.set(new Vector2(0, 1).rotateRad(playerCar.body.getAngle()), 0);
        gamecam.update();
        viewport.apply();

        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(gamecam.combined);
        spriteBatch.draw(texture, 0, 0, 400, 400);

        playerCar.render(spriteBatch);
        spriteBatch.end();

        b2dr.render(this.world, this.gamecam.combined);

        gameInput.render(spriteBatch);
    }


    public void resize(int width, int height) {
        viewport.update(width, height);
        gameInput.resize(width, height);
    }

    public void pause() { }
    public void resume() { }
    public void hide() { }
    public void dispose() { }
}
