package com.race2135.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2D;

public class Main extends Game {
    public Screen menuScreen, playScreen, carSelectionScreen;

	@Override
	public void create () {
        Box2D.init();

        CarInfo.init();
        LevelInfo.init();

        menuScreen = new MainMenu(this);
        playScreen = new PlayScreen(this, CarInfo.models.get(0), LevelInfo.levels.get(0));
        carSelectionScreen = new CarSelectionMenu(this);

        setScreen(menuScreen);
	}
}
