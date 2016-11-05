package com.race2135.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2D;

public class Main extends Game {
    public static final float PPM = 100;
	public static final float DEGTORAD = 0.0174532925199432957f;

	@Override
	public void create () {
        Box2D.init();
        setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
