package com.race2135.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Gabriel025 on 2016.11.04.
 */

public class HUD implements InputProcessor {
    float gas, brake;
    int gear; //-1 is reverse, 0 is neutral, ...

    SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;
    private int screenWidth, screenHeight;

    private Texture texGas, texBrake;
    private Sprite spriteGas, spriteBrake;

    public HUD(SpriteBatch batch)
    {
        this.batch = batch;

        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        camera.setToOrtho(true);

        texGas = new Texture("HUD/gas.png");
        texBrake = new Texture("HUD/brake.png");

        spriteGas = new Sprite(texGas);
        spriteGas.flip(false, true);

        spriteBrake = new Sprite(texBrake);
        spriteBrake.flip(false, true);
    }

    void render()
    {
        viewport.apply();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        spriteGas.draw(batch);
        spriteBrake.draw(batch);
        batch.end();
    }

    void resize(int width, int height) {
        screenWidth = width;
        screenHeight = height;

        spriteGas.setPosition(0, screenHeight - 100);
        spriteBrake.setPosition(100, screenHeight - 100);

        viewport.update(screenWidth, screenHeight);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
