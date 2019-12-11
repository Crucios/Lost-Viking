package com.mygdx.lifeisagame.Screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.LostViking.LostViking;
import com.mygdx.lifeisagame.Player.HUD;
import com.mygdx.lifeisagame.Player.Player;
import com.mygdx.lifeisagame.Tools.WorldCreator;

public class GameScreen implements Screen{
	protected LostViking game;
	protected Player player;
	
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
	protected ArrayList<Map> mapAnimation;
		
	protected Music music;
		
	Texture texture;
		
	//Box2D
	protected World world;
	protected Box2DDebugRenderer b2dr;
	
	
	public GameScreen(LostViking game, World world, Player player, String filepath_tmx){
		this.game = game;
		this.player = player;
		this.world = world;
		
		//Camera movement
		gamecam = new OrthographicCamera();
			
		//Maintain virtual aspect ratio despite resizing
		gamePort = new FitViewport(LostViking.V_HEIGHT / LostViking.PPM, LostViking.V_WIDTH / LostViking.PPM, gamecam);
		
		//Map loading
		mapLoader = new TmxMapLoader();
		map = mapLoader.load(filepath_tmx);
		renderer = new OrthogonalTiledMapRenderer(map, 1 / LostViking.PPM);
		
		mapAnimation = new ArrayList<Map>();
		mapAnimation.add(new Map(new Vector2(0, 0), false));
		mapAnimation.add(new Map(new Vector2(0, 1712), true));
		
		//First camera position
		gamecam.position.set(4.8f,8.5f,0);
		renderer.setView(gamecam);
		
		//Box2D Debug Renderer
		b2dr = new Box2DDebugRenderer();
		
		//Generate World
		maxLeft = gamePort.getWorldWidth() / 2;
		maxRight = 126.26;
		
		//Generate Wall and Ground
		new WorldCreator(world, map);
		
		//world.setContactListener(new WorldContactListener());
	}
	
	public void update(float dt) {
		handleInput(dt);
		world.step(1/60f, 6, 2);
		player.update(dt);
		gamecam.update();
		renderer.setView(gamecam);
		
		for(int i=0; i<mapAnimation.size(); i++) {
			if(mapAnimation.get(i).isDisposed()) {
				boolean rotate = false;
				if(!mapAnimation.get(i).isRotate())
					rotate = true;
				
				mapAnimation.remove(i);
				mapAnimation.add(new Map(new Vector2(0, 1712), rotate));
			}
			else
				mapAnimation.get(i).update(dt);
		}
		
		//Update
		//hud.update(dt);
		
	}
	
	public void handleInput(float dt) {
		player.handleInput(dt);
//		if(Gdx.input.isKeyPressed(Input.Keys.D))
//			gamecam.position.x += 100 *dt;
//		if(Gdx.input.isKeyPressed(Input.Keys.A))
//			gamecam.position.x -= 100*dt;
//		if(Gdx.input.isKeyPressed(Input.Keys.W))
//			gamecam.position.y += 100*dt;
//		if(Gdx.input.isKeyPressed(Input.Keys.S))
//			gamecam.position.y -= 100*dt;
		
//		Gdx.app.log("Camera Position", gamecam.position.x + " " + gamecam.position.y);
	}
	
	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		renderer.render();
		
		//game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		//hud.stage.draw();
		game.batch.setProjectionMatrix(gamecam.combined);		
		game.batch.begin();
		
		for(int i=0;i<mapAnimation.size();i++) {
			mapAnimation.get(i).draw(game.batch);
		}
			
		player.draw(game.batch);
		game.batch.end();
		
		b2dr.render(world, gamecam.combined);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		gamePort.update(width, height);
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
