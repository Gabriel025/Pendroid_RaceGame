package com.race2135.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class PlayScreen implements Screen {
    private Main game;
    private OrthographicCamera gamecam;
    private Viewport viewport;
    private World world;
    private Box2DDebugRenderer b2dr;

    PlayerCar playerCar;
    HUD hud;

    SpriteBatch spriteBatch;
    Texture texture;

    public PlayScreen(Main game) {
        this.game = game;

        gamecam = new OrthographicCamera();
        viewport = new FillViewport(80, 60, gamecam);

        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        spriteBatch = new SpriteBatch();

        playerCar = new PlayerCar(world);

        hud = new HUD(spriteBatch);

        texture = new Texture(Gdx.files.internal("badlogic.jpg"));
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
        gamecam.update();
        viewport.apply();

        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(gamecam.combined);
        spriteBatch.draw(texture, 0, 0, 1000, 1000);
        spriteBatch.end();

        b2dr.render(this.world, this.gamecam.combined);

        hud.render();
    }


    public void resize(int width, int height) {
        viewport.update(width, height);
        hud.resize(width, height);
    }

    public void show() {
    }
    public void pause() {
    }

    public void resume() {
    }

    public void hide() {
    }

    public void dispose() {
    }
}
