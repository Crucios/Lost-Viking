package com.mygdx.LostViking;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.LostViking.Player.Player;
import com.mygdx.LostVikingScreens.GameOverScreen;
import com.mygdx.LostVikingScreens.GameScreen;
import com.mygdx.LostVikingScreens.MainMenuScreen;

public class LostViking extends Game {
	public static final int WIDTH = 720;
	public static final int HEIGHT = 1280;
	public static final int V_WIDTH = 1760;
	public static final int V_HEIGHT = 1080;
	public static final int MOVEMENT_CAMERA = 500;
	public static final float PPM = 100;	//PixelPerMeter
	public static final int MAX_MAP_SIZE = 10000;
	public SpriteBatch batch;
	public static AssetManager manager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("Combat Music.mp3",Music.class);
		manager.load("MainMenu/backsound.mp3",Music.class);
		manager.finishLoading();
		
		World tempWorld = new World(new Vector2(0, 0), true);
		this.setScreen(new MainMenuScreen(this));
		//setScreen(new GameScreen(this, tempWorld, new Player(tempWorld, new Vector2(500,100)),"Maps/Map.tmx"));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.render();
	}
}
