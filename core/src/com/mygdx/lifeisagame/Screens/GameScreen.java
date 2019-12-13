package com.mygdx.lifeisagame.Screens;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.mygdx.lifeisagame.Enemy.EnemyBase;
import com.mygdx.lifeisagame.Enemy.double_shoot;
import com.mygdx.lifeisagame.Enemy.side_melee;
import com.mygdx.lifeisagame.Enemy.side_shoot;
import com.mygdx.lifeisagame.Enemy.straight_melee;
import com.mygdx.lifeisagame.Enemy.straight_shoot;
import com.mygdx.lifeisagame.Player.HUD;
import com.mygdx.lifeisagame.Player.Player;
import com.mygdx.lifeisagame.Player.Bullet.BaseBullet;
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
	
	//enemy
	protected ArrayList<EnemyBase> enemy;
	protected float enemyTimer;
	protected float enemyBoxY;
	protected float enemyBoxX;
	protected Random rand;
	protected ArrayList<BaseBullet> bullet;
	
	
	public GameScreen(LostViking game, World world, Player player, String filepath_tmx){
		this.game = game;
		this.player = player;
		this.world = world;
		
		hud = new HUD(game.batch, this.player);
		
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
		
		enemy = new ArrayList<EnemyBase>();
		enemyTimer = 0;
		rand = new Random();
		bullet = player.getBullet();
		
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
		bullet = player.getBullet();
		enemyTimer += dt;
		for(EnemyBase ene : enemy) {
			if(!ene.getDestroy()) {
				ene.update(dt);
			}
		}
		if(enemyTimer > 2f) {
			int enemyRandom = rand.nextInt(6);
			if(enemyRandom == 1) {
				enemy.add(new straight_melee(world,player));
				enemyTimer = 0;
			}
			else if(enemyRandom == 2) {
				enemy.add(new straight_shoot(world,player));
				enemyTimer = 0;
			}
			else if(enemyRandom == 3) {
				enemy.add(new side_shoot(world,player));
				enemyTimer = 0;
			}
			else if(enemyRandom == 4) {
				enemy.add(new double_shoot(world,player));
				enemyTimer = 0;
			}
			else if(enemyRandom == 5) {
				enemy.add(new side_melee(world,player));
				enemyTimer = 0;
			}
		}
		for(int i = 0;i<enemy.size();i++) {
			if(!enemy.get(i).getDestroy()) {
				enemy.get(i).update(dt);
				//Collision Detection
				for(int j=0;j<bullet.size();j++) {
					switch(enemy.get(i).getType()) {
					case 0:
						enemyBoxX = 0.46f;
						enemyBoxY = 0.52f;
						break;
					case 1:
						enemyBoxX = 0.36f;
						enemyBoxY = 0.52f;
						break;
					case 2:
						enemyBoxX = 0.70f;
						enemyBoxY = 0.52f;
						break;
					case 3:
						enemyBoxX = 0.36f;
						enemyBoxY = 0.48f;
						break;
					case 4:
						enemyBoxX = 0.36f;
						enemyBoxY = 0.52f;
						break;
					}
					if(!bullet.get(j).getDestroy()) {
						if(bullet.get(j).getPosition().y < enemy.get(i).getPosition().y + enemyBoxY
								&& bullet.get(j).getPosition().y > enemy.get(i).getPosition().y - enemyBoxY
								&& bullet.get(j).getPosition().x < enemy.get(i).getPosition().x + enemyBoxX
								&& bullet.get(j).getPosition().x > enemy.get(i).getPosition().x - enemyBoxX) {
							bullet.get(j).onHit(enemy.get(i));
							System.out.println("hiting");
							System.out.println("Enemy Position : " + enemy.get(i).getPosition());
							System.out.println("Bullet Position: " + bullet.get(j).getNowPosition());
						}
						System.out.println("Enemy Position : " + enemy.get(i).getPosition());
						System.out.println("Bullet Position: " + bullet.get(j).getNowPosition());
					}
				}
			}
		}
		//Update
		hud.update(dt);
	}
	
	public void handleInput(float dt) {
		player.handleInput(dt);
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
	
		game.batch.setProjectionMatrix(gamecam.combined);		
		game.batch.begin();
		
		for(int i=0;i<mapAnimation.size();i++) {
			mapAnimation.get(i).draw(game.batch);
		}
		for(EnemyBase ene : enemy) {
			if(!ene.getDestroy()) {
				ene.draw(game.batch);
			}
		}	
		player.draw(game.batch);
		game.batch.end();
		
		game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();
		
		b2dr.render(world, gamecam.combined);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		gamePort.update(width, height);
	}

	
	protected Viewport getGamePort() {
		return gamePort;
	}

	protected void setGamePort(Viewport gamePort) {
		this.gamePort = gamePort;
	}

	
	protected OrthographicCamera getGamecam() {
		return gamecam;
	}

	protected void setGamecam(OrthographicCamera gamecam) {
		this.gamecam = gamecam;
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
		hud.dispose();
	}

}
