package com.mygdx.lifeisagame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.LostViking.LostViking;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = LostViking.HEIGHT;
		config.width = LostViking.WIDTH;
		new LwjglApplication(new LostViking(), config);
	}
}
