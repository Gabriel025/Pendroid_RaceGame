package com.race2135.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Gabriel025 on 2016.11.04.
 */

public class HUD implements InputProcessor {
    private OrthographicCamera camera;
    private Viewport viewport;
    private int screenWidth, screenHeight;

    private Texture texGas, texBrake;
    private Sprite spriteGas, spriteBrake;


    private float throttle, brake;
    private int gear; //-1 is reverse, 0 is neutral, ...

    private class Touch
    {
        public int x, y, pointer;

        public Touch()
        {
            x = y = pointer = 0;
        }

        public Touch(int x, int y, int pointer)
        {
            this.x = x;
            this.y = y;
            this.pointer = pointer;
        }
    }
    Array<Touch> touchArray;

    public HUD()
    {
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        camera.setToOrtho(true);

        touchArray = new Array<Touch>(4);

        texGas = new Texture("HUD/gas.png");
        texBrake = new Texture("HUD/brake.png");

        spriteGas = new Sprite(texGas);
        spriteGas.flip(false, true);

        spriteBrake = new Sprite(texBrake);
        spriteBrake.flip(false, true);
    }

    public float getThrottle() {
        return throttle;
    }

    public float getBraking() {
        return brake;
    }

    public int getGear() {
        return gear;
    }

    private void update()
    {
        throttle = brake = 0;
        for(Touch touch : touchArray)
        {
            if(spriteGas.getBoundingRectangle().contains(touch.x, touch.y))
                throttle = (touch.y - spriteGas.getY()) / spriteGas.getHeight();
            else if(spriteBrake.getBoundingRectangle().contains(touch.x, touch.y))
                brake = (touch.y - spriteBrake.getY()) / spriteBrake.getHeight();

        }

        //Gdx.app.log("Pedals", "Gas: " + throttle + "; Brake: " + brake);
    }

    public void render(SpriteBatch batch)
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
        //Gdx.app.log("event", "touchDown(" + screenX + ", " + screenY + ", " + pointer + ", " + button + ");");

        touchArray.add(new Touch(screenX, screenY, pointer));

        update();
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        for(int i = 0; i < touchArray.size; i++)
            if(touchArray.get(i).pointer == pointer) {
                touchArray.set(i, new Touch(screenX, screenY, pointer));
                break;
            }

        update();
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        for(int i = 0; i < touchArray.size; i++)
            if(touchArray.get(i).pointer == pointer) {
                touchArray.removeIndex(i);
                break;
            }

        update();
        return true;
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
