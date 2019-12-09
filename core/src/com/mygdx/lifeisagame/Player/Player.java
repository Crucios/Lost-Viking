package com.mygdx.lifeisagame.Player;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.lifeisagame.LifeIsAGame;

public class Player extends Sprite{
	private int friendship;
	private int family;
	private int health;
	private int happiness;
	private int money;
	
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
	public enum State{BABY_WALKING, BABY_JUMPING, CHILD_RUNNING, CHILD_JUMPING, TEEN_RUNNING, TEEN_JUMPING,
		YOUNG_ADULT_RUNNING ,YOUNG_ADULT_JUMPING, ADULT_RUNNING, ADULT_JUMPING, OLD_WALKING, OLD_JUMPING}
	public State currentState;
	public State previousState;
	private float stateTimer;
	
	Player(World world, Vector2 position){
		
	}

	public void defineHitBox() {
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(40 / LifeIsAGame.PPM, 70 / LifeIsAGame.PPM);
		
		fdef.shape = shape;
		fdef.friction =  4.0f;
		b2body.createFixture(fdef);
		fdef.isSensor = true;
		b2body.createFixture(fdef).setUserData("body");
	}
	
	public void definePlayer() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(position.x / LifeIsAGame.PPM,position.y / LifeIsAGame.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		
		defineHitBox();
	}
	
	public void generateAnimation() {
		
	}
	
	public TextureRegion getFrame(float dt) {
		currentState = getState();
		
		TextureRegion region;
		
		return region;
	}
	
	public State getState() {
		
	}
	
	public void update(float dt) {
		
	}
	
	public void handleInput() {
		//Space Pressed -> Jump
		
		//E Pressed -> Choice
		
	}
	
	public int getFriendship() {
		return friendship;
	}

	public void setFriendship(int friendship) {
		this.friendship = friendship;
	}

	public int getFamily() {
		return family;
	}

	public void setFamily(int family) {
		this.family = family;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getHappiness() {
		return happiness;
	}

	public void setHappiness(int happiness) {
		this.happiness = happiness;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public Vector2 getPosition() {
		return Position;
	}

	public void setPosition(Vector2 position) {
		Position = position;
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
	
	//Public Access Method
	
}
