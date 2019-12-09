package com.mygdx.lifeisagame.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.lifeisagame.LifeIsAGame;
import com.mygdx.lifeisagame.Player.HUD;
import com.mygdx.lifeisagame.Player.Player;

public class gamescreen implements Screen{
	protected LifeIsAGame game;
	
	//Camera
	protected OrthographicCamera gamecam;
	protected Viewport gamePort;
	protected double maxLeft;
	protected double maxRight;
		
	//HUD
	private HUD hud;
	
	//Tiled map variables
	protected TmxMapLoader mapLoader;
	protected TiledMap map;
	protected OrthogonalTiledMapRenderer renderer;
		
	protected Music music;
		
	Texture texture;
		
	//Box2D
	protected World world;
	protected Box2DDebugRenderer b2dr;
	
	
	public gamescreen(LifeIsAGame game, World world, Player player, String filepath_tmx){
		this.game = game;
	}
	
	public void update(float dt) {
		
	}
	
	public void handleInput(float dt) {
		
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
