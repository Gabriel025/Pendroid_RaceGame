package com.race2135.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * GameInput class (renamed from HUD)
 *
 * Processes user input(touchscreen) while ingame(GameScreen), and renders
 * control sprites.
 *
 * Created by Gabriel025 on 2016.11.04.
 */

public class GameInput implements InputProcessor {
    //Viewport and camera for screen coordinates
    private OrthographicCamera camera;
    private Viewport viewport;
    private int screenWidth, screenHeight;

    //Touch input handling
    private class Touch
    {
        int x, y, pointer;

        Touch()
        {
            x = y = pointer = 0;
        }

        Touch(int x, int y, int pointer)
        {
            this.x = x;
            this.y = y;
            this.pointer = pointer;
        }
    }
    private Array<Touch> touchArray;

    //Textures, sprites and a font
    private Texture texBkg, texGasPedal, texBrakePedal, texKnob;
    private Sprite spriteGasBkg, spriteGasPedal,
            spriteBrakeBkg, spriteBrakePedal,
            spriteGearBkg, spriteGearKnob;
    private BitmapFont font;

    //Number of gears (including R and N)
    int numGears;

    //Processed user input
    private float throttle, brake;
    private int gear = 0; //-1 is reverse, 0 is neutral, ...

    //Constructor
    public GameInput(int numGears)
    {
        this.numGears = numGears;

        //Viewport and camera for screen coordinates
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        camera.setToOrtho(true);

        //Touch input handling
        touchArray = new Array<Touch>(4);

        //Textures and default font
        texBkg = new Texture("HUD/background.png");
        texBkg.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        texGasPedal = new Texture("HUD/gas.png");
        texGasPedal.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        texBrakePedal = new Texture("HUD/brake.png");
        texBrakePedal.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        texKnob = new Texture("HUD/knob.png");
        texKnob.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        font = new BitmapFont(true);

        //Gas pedal sprites
        spriteGasBkg = new Sprite(texBkg);
        spriteGasBkg.flip(false, true);

        spriteGasPedal = new Sprite(texGasPedal);
        spriteGasPedal.flip(false, true);

        //Brake pedal sprites
        spriteBrakeBkg = new Sprite(texBkg);
        spriteBrakeBkg.flip(false, true);

        spriteBrakePedal = new Sprite(texBrakePedal);
        spriteBrakePedal.flip(false, true);

        //Gearbox sprites
        spriteGearBkg = new Sprite(texBkg);
        spriteGearBkg.flip(false, true);

        spriteGearKnob = new Sprite(texKnob);
        spriteGearKnob.flip(false, true);
    }

    //User input accessors
    public float getThrottle() {
        return throttle;
    }
    public float getBraking() {
        return brake;
    }
    public int getGear() {
        return gear;
    }

    //Update method (called by input event methods)
    private void update()
    {
        //Processing touch data
        throttle = brake = 0;
        for(Touch touch : touchArray)
        {
            if(spriteGasBkg.getBoundingRectangle().contains(touch.x, touch.y))
                throttle = (touch.y - spriteGasBkg.getY()) / spriteGasBkg.getHeight();
            else if(spriteBrakeBkg.getBoundingRectangle().contains(touch.x, touch.y))
                brake = (touch.y - spriteBrakeBkg.getY()) / spriteBrakeBkg.getHeight();

        }

        //Updating sprite positions
        spriteGasPedal.setY(spriteGasBkg.getY()
                + (spriteGasBkg.getHeight() - spriteGasPedal.getHeight()) * throttle);
        spriteBrakePedal.setY(spriteBrakeBkg.getY()
                + (spriteBrakeBkg.getHeight() - spriteBrakePedal.getHeight()) * brake);

        spriteGearKnob.setY((1f - (float)(gear + 1) / (numGears - 1))
                * (screenHeight - spriteGearKnob.getHeight()));
        //Gdx.app.log("Pedals", "Gas: " + throttle + "; Brake: " + brake);
    }

    //Render method
    public void render(SpriteBatch batch)
    {
        viewport.apply();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);

        //Drawing backgrounds
        spriteGasBkg.draw(batch);
        spriteBrakeBkg.draw(batch);

        //Drawing pedals (only when there is input)
        if(throttle > 0) spriteGasPedal.draw(batch);
        if(brake > 0) spriteBrakePedal.draw(batch);

        //Drawing gearbox
        spriteGearBkg.draw(batch);
        spriteGearKnob.draw(batch);

        batch.end();
    }

    //Resize method, also called initially to specify display dimensions.
    void resize(int width, int height) {
        screenWidth = width;
        screenHeight = height;

        //Updating sprite positions
        spriteGasBkg.setPosition(100, screenHeight - 100);
        spriteGasPedal.setX(spriteGasBkg.getX());

        spriteBrakeBkg.setPosition(0, screenHeight - 100);
        spriteBrakePedal.setX(spriteBrakeBkg.getX());

        spriteGearBkg.setPosition(screenWidth * 29 / 30, 0);
        spriteGearBkg.setSize(screenWidth / 30, screenHeight);
        spriteGearKnob.setX(spriteGearBkg.getX());
        spriteGearKnob.setSize(screenWidth / 30, screenWidth / 30);

        update();

        //Updating viewport and camera
        viewport.update(screenWidth, screenHeight);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    //Touch input handling
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

    //Unused interface methods
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