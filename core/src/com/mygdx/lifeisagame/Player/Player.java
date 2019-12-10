package com.mygdx.lifeisagame.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.LostViking.LostViking;

public class Player extends Sprite{
	//Properties
	private int damage;
	private int hitpoints; 
	private int level;
	private boolean shooting;
	private boolean damaged;
	
	//Movement properties
	private int limitMovementSpeed;
	private float movementSpeed;
	private float flyTimer;
	
	//Texture
	TextureRegion ship_level1;
	TextureRegion ship_level2;
	TextureRegion ship_level3;
	TextureRegion ship_level4;
	TextureRegion ship_level5;
	Animation shipDestroyed;
	
	//For gameCam glitch in screen
	private boolean camGlitched;
	
	//Position Player (Origin)
	private Vector2 position;
	
	//Position Player Dynamically
	private Vector2 nowPosition;
	
	//Box2d Player
	public World world;
	public Body b2body; 
	
	//Elapsed Time Player
	private float elapsed;
	
	//Animation
	//State Player
	public enum State{PLAYER_LEVEL1, PLAYER_LEVEL2, PLAYER_LEVEL3, PLAYER_LEVEL4, PLAYER_LEVEL5, DESTROYED}
	public State currentState;
	public State previousState;
	private float stateTimer;
	
	public Player(World world, Vector2 position){
		super(new AtlasRegion(new TextureAtlas("Player/Player.pack").findRegion("Player")));
		this.world = world;
		this.position = position;
		
		//Initializatuin
		level = 1;
		
		camGlitched = false;
		shooting = false;
		damaged = false;
		
		flyTimer = 0;
		limitMovementSpeed = 2;
		movementSpeed = 0.5f;
		
		//Properties
		hitpoints = 100;
		
		generateAnimation();
		
		definePlayer();
	}

	public void defineHitBox() {
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(40 / LostViking.PPM, 70 / LostViking.PPM);
		
		fdef.shape = shape;
		fdef.friction =  4.0f;
		b2body.createFixture(fdef);
		fdef.isSensor = true;
		b2body.createFixture(fdef).setUserData("player");
	}
	
	public void definePlayer() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(position.x / LostViking.PPM,position.y / LostViking.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		
		defineHitBox();
	}
	
	public void generateAnimation() {
		ship_level1 = new TextureRegion(getTexture(), 71, 351, 102, 96);
		ship_level2 = new TextureRegion(getTexture(), 215, 350, 123, 97);
		ship_level3 = new TextureRegion(getTexture(), 369, 343, 126, 108);
		ship_level4 = new TextureRegion(getTexture(), 532, 335, 110, 128);
		ship_level5 = new TextureRegion(getTexture(), 672, 327, 140, 143);
		//65, 29
		Array<TextureRegion> frames = new Array<TextureRegion>();
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				frames.add(new TextureRegion(getTexture(), i*64, j*64, 64, 64));
			}
		}
		shipDestroyed = new Animation(0.1f, frames);
		frames.clear();
	}
	
	public TextureRegion getFrame(float dt) {
		currentState = getState();
		
		TextureRegion region;
		switch(currentState) {
		case DESTROYED:
			region = (TextureRegion) shipDestroyed.getKeyFrame(stateTimer, true);
			break;
		case PLAYER_LEVEL1:
			region = ship_level1;
			break;
		case PLAYER_LEVEL2:
			region = ship_level2;
			break;
		case PLAYER_LEVEL3:
			region = ship_level3;
			break;
		case PLAYER_LEVEL4:
			region = ship_level4;
			break;
		case PLAYER_LEVEL5:
			region = ship_level5;
			break;
			default:
				region = ship_level1;
				break;
		}
		
		setSize((float) 0.9,(float) 0.9);
		stateTimer = currentState == previousState ? stateTimer + dt : 0;
		previousState = currentState;
		return region;
	}
	
	public State getState() {
		if(hitpoints <= 0)
			return State.DESTROYED;
		
		if(level == 1)
			return State.PLAYER_LEVEL1;
		else if(level == 2)
			return State.PLAYER_LEVEL2;
		else if(level == 3)
			return State.PLAYER_LEVEL3;
		else if(level == 4)
			return State.PLAYER_LEVEL4;
		else
			return State.PLAYER_LEVEL5;
	}
	
	public void update(float dt) {
		//Update Timer
		flyTimer += dt;
		
		if(level > 5)
			level = 5;
		
		//Set Position
		setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight() / 2);
		nowPosition = new Vector2(b2body.getPosition().x, b2body.getPosition().y);
		
		//Set Texture Region
		setRegion(getFrame(dt));
	}
	
	public void handleInput() {
		if(currentState != State.DESTROYED) {
			if(Gdx.input.isKeyPressed(Input.Keys.W) && b2body.getLinearVelocity().y <= limitMovementSpeed && !Gdx.input.isKeyPressed(Input.Keys.S)) {
				b2body.applyLinearImpulse(new Vector2(0,movementSpeed), b2body.getWorldCenter(), true);
			}
			
			if(Gdx.input.isKeyPressed(Input.Keys.A) && b2body.getLinearVelocity().x >= -limitMovementSpeed && !Gdx.input.isKeyPressed(Input.Keys.D)) {
				b2body.applyLinearImpulse(new Vector2(-movementSpeed,0), b2body.getWorldCenter(), true);
			}
			
			if(Gdx.input.isKeyPressed(Input.Keys.S) && b2body.getLinearVelocity().y >= -limitMovementSpeed && !Gdx.input.isKeyPressed(Input.Keys.W)) {
				b2body.applyLinearImpulse(new Vector2(0,-movementSpeed), b2body.getWorldCenter(), true);
			}
			
			if(Gdx.input.isKeyPressed(Input.Keys.D) && b2body.getLinearVelocity().x <= limitMovementSpeed && !Gdx.input.isKeyPressed(Input.Keys.A)) {
				b2body.applyLinearImpulse(new Vector2(movementSpeed,0), b2body.getWorldCenter(), true);
			}
		}
		
		Gdx.app.log("Player Position", position.x + " " + position.y);
	}
	
	//Public Access Method
	public boolean isCamGlitched() {
		return camGlitched;
	}

	public void setCamGlitched(boolean camGlitched) {
		this.camGlitched = camGlitched;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getNowPosition() {
		return nowPosition;
	}

	public void setNowPosition(Vector2 nowPosition) {
		this.nowPosition = nowPosition;
	}

	public float getElapsed() {
		return elapsed;
	}

	public void setElapsed(float elapsed) {
		this.elapsed = elapsed;
	}
}
