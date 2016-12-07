package com.race2135.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2D;

public class Main extends Game {
    public static Texture black;

	@Override
	public void create() {
        Box2D.init();

        CarInfo.init();
        LevelInfo.init();
        black = new Texture("black.png");

        setScreen(new MainMenu(this));
	}
}
