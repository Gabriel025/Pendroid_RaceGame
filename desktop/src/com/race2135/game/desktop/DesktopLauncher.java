package com.race2135.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import com.race2135.game.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        //config.width = 800;
        //config.height = 480;
		config.width = 1280;
		config.height = 720;
		new LwjglApplication(new Main(), config);
	}
}
