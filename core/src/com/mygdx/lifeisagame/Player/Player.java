package com.mygdx.lifeisagame.Player;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
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
import com.mygdx.lifeisagame.Player.Bullet.BaseBullet;
import com.mygdx.lifeisagame.Player.SkillTree.Node;
import com.mygdx.lifeisagame.Player.SkillTree.SkillTree;

public class Player extends Sprite{
	//Properties
	private int damage;
	private int hitpoints; 
	private int score;
	private boolean shooting;
	private boolean damaged;
	private boolean invicible;
	private SkillTree skillTree;
	private int dodgeRate;
	private int criticalRate;
	private boolean chooseSkill;
	private boolean choosingSkill;
	private boolean justChooseSkill;
	private Node skill;

	private int currentScore;
	private int highscore;

	
	//Movement properties
	private double limitMovementSpeed;
	private float movementSpeed;
	private boolean moving;
	private float movingTimer;
	private Vector2 movingVelocity;
	
	//Texture
	TextureRegion swing_left;
	TextureRegion boost_left;
	TextureRegion reverse_left;
	TextureRegion stop;
	TextureRegion boost;
	TextureRegion reverse;
	TextureRegion swing_right;
	TextureRegion boost_right;
	TextureRegion reverse_right;
	private boolean toRight;
	private boolean toLeft;
	private boolean boosting;
	private boolean reversing;
	
	//bullet
	private ArrayList<BaseBullet> bullet;
	private float bulletTimer;
	private Vector2 bulletPosition;
	private float bulletSpeed;
	//Rocket
	private float rocketTimer;
	
	Animation shipDestroyed;
	Animation shipDamaged;
	Preferences preferences = Gdx.app.getPreferences("My preferences");
	
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
	private float elapsedInvicible;
	
	//Animation
	//State Player
	public enum State{SWING_LEFT, BOOST_LEFT, REVERSE_LEFT, STOP, BOOST, REVERSE, SWING_RIGHT, BOOST_RIGHT, REVERSE_RIGHT, DAMAGED, DESTROYED}
	public State currentState;
	public State previousState;
	private float stateTimer;
	
	public Player(World world, Vector2 position){
		super(new AtlasRegion(new TextureAtlas("Player/Player.pack").findRegion("Alternative Player")));
		this.world = world;
		this.position = position;
		
		//Initialization
		camGlitched = false;
		shooting = false;
		damaged = false;
		invicible = false;
		
		toRight = false;
		toLeft = false;
		boosting = false;
		reversing = false;
		
		moving = false;
		movingTimer = 0;
		limitMovementSpeed = 2;
		movementSpeed = 0.5f;
		criticalRate = 10;
		bulletSpeed = 6f;
		movingVelocity = new Vector2(0,0);
		
		chooseSkill = false;
		justChooseSkill = false;
		choosingSkill = false;
		
		elapsedInvicible = 0;
		
		//bullet
		bulletTimer = 2;
		bullet = new ArrayList<BaseBullet>();
		rocketTimer = 5;
		
		//Properties
		hitpoints = 5;
		damage = 5;
		dodgeRate = 0;
		criticalRate = 0;
		score = 0;
		
		generateAnimation();
		
		definePlayer();
		
		skillTree = new SkillTree(world, this);
		skill = skillTree.getLastSkill(skillTree.getRoot());
	}

	public void defineHitBox() {
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(40 / LostViking.PPM, 70 / LostViking.PPM);
		
		fdef.shape = shape;
		//fdef.friction =  4.0f;
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
		swing_left = new TextureRegion(getTexture(), 323, 1, 38, 41);
		boost_left = new TextureRegion(getTexture(), 324, 43, 37, 46);
		reverse_left = new TextureRegion(getTexture(), 326, 89, 35, 43);
		stop = new TextureRegion(getTexture(), 362, 0, 46, 41);
		boost = new TextureRegion(getTexture(), 363, 42, 44, 47);
		reverse = new TextureRegion(getTexture(), 362, 89, 46, 43);
		swing_right = new TextureRegion(getTexture(), 409, 0, 38, 41);
		boost_right = new TextureRegion(getTexture(), 410, 42, 36, 47);
		reverse_right = new TextureRegion(getTexture(), 409, 88, 37, 44);
		
		Array<TextureRegion> frames = new Array<TextureRegion>();
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				frames.add(new TextureRegion(getTexture(), i*64, j*64 + 40, 64, 64));
			}
		}
		shipDestroyed = new Animation(0.1f, frames);
		frames.clear();
		
		for(int i=0; i<2; i++) {
			frames.add(new TextureRegion(getTexture(), 362 + i*200, 0, 46, 41));
		}
		shipDamaged = new Animation(0.25f, frames);
	}
	
	public TextureRegion getFrame(float dt) {
		currentState = getState();
		
		TextureRegion region;
		switch(currentState) {
		case DESTROYED:
			region = (TextureRegion) shipDestroyed.getKeyFrame(stateTimer, true);
			break;
		case DAMAGED:
			region = (TextureRegion) shipDamaged.getKeyFrame(stateTimer, true);
			break;
		case SWING_LEFT:
			region = swing_left;
			break;
		case BOOST_LEFT:
			region = boost_left;
			break;
		case REVERSE_LEFT:
			region = reverse_left;
			break;
		case STOP:
			region = stop;
			break;
		case BOOST:
			region = boost;
			break;
		case REVERSE:
			region = reverse;
			break;
		case SWING_RIGHT:
			region = swing_right;
			break;
		case BOOST_RIGHT:
			region = boost_right;
			break;
		case REVERSE_RIGHT:
			region = reverse_right;
			break;
			default:
				region = stop;
				break;
		}
		
		setSize((float) 1.8,(float) 1.8);
		stateTimer = currentState == previousState ? stateTimer + dt : 0;
		previousState = currentState;
		return region;
	}
	
	public State getState() {
		if(hitpoints <= 0)
			return State.DESTROYED;
		if(damaged)
			return State.DAMAGED;
		
		if(boosting) {
			if(toLeft)
				return State.BOOST_LEFT;
			else if(toRight)
				return State.BOOST_RIGHT;
			else
				return State.BOOST;
		}
		else if(reversing) {
			if(toLeft)
				return State.REVERSE_LEFT;
			else if (toRight)
				return State.REVERSE_RIGHT;
			else
				return State.REVERSE;
		}
		else {
			if(toLeft)
				return State.SWING_LEFT;
			else if(toRight)
				return State.SWING_RIGHT;
			else
				return State.STOP;
		}
	}
	
	public void update(float dt) {
		//Update Timer
		movingTimer += dt;
		elapsedInvicible += dt;
		
		//Update Last Skill
		skill = skillTree.getLastSkill(skillTree.getRoot());
		
		if(moving)
			movingVelocity = b2body.getLinearVelocity();
		
		//Set Position
		setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight() / 2);
		nowPosition = new Vector2(b2body.getPosition().x, b2body.getPosition().y);
		
		//Bullet
		skillTree.update(skillTree.getRoot(), dt);
		
		//Grant Invicible when hit
		if(damaged && !invicible) {
			invicible = true;
			elapsedInvicible = 0;
		}
		
		if(invicible && elapsedInvicible > 2.0f) {
			invicible = false;
			damaged = false;
		}
		
		/*if(rocketTimer > 1f) {
			bullet.add(new BaseBullet(world,new Vector2(bulletPosition.x, bulletPosition.y),0,false));
			rocketTimer = 0;
		}*/
		//Set Texture Region
		setRegion(getFrame(dt));
		
		//Check dead or not
		if(currentState==State.DESTROYED)
		{
		    int highscore = preferences.getInteger("High score",0);
		    if(highscore<=currentScore)			
		    {
		    	// display yourCurrentScore
		        preferences.putInteger("High score", currentScore);
		        preferences.flush();
		        this.highscore=preferences.getInteger("highscore",0);
		    }
		    else;	
		}
	}
	
	public void handleInput(float dt) {
		if(currentState != State.DESTROYED) {
			if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.D)) {
				if(Gdx.input.isKeyPressed(Input.Keys.W) && b2body.getLinearVelocity().y <= limitMovementSpeed && !Gdx.input.isKeyPressed(Input.Keys.S)) {
					b2body.applyLinearImpulse(new Vector2(0,movementSpeed), b2body.getWorldCenter(), true);
					boosting = true;
					reversing = false;
				}
				
				if(Gdx.input.isKeyPressed(Input.Keys.A) && b2body.getLinearVelocity().x >= -limitMovementSpeed && !Gdx.input.isKeyPressed(Input.Keys.D)) {
					b2body.applyLinearImpulse(new Vector2(-movementSpeed,0), b2body.getWorldCenter(), true);
					toRight = false;
					toLeft = true;
				}
				
				if(Gdx.input.isKeyPressed(Input.Keys.S) && b2body.getLinearVelocity().y >= -limitMovementSpeed && !Gdx.input.isKeyPressed(Input.Keys.W)) {
					b2body.applyLinearImpulse(new Vector2(0,-movementSpeed), b2body.getWorldCenter(), true);
					reversing = true;
					boosting = false;
				}
				
				if(Gdx.input.isKeyPressed(Input.Keys.D) && b2body.getLinearVelocity().x <= limitMovementSpeed && !Gdx.input.isKeyPressed(Input.Keys.A)) {
					b2body.applyLinearImpulse(new Vector2(movementSpeed,0), b2body.getWorldCenter(), true);
					toRight = true;
					toLeft = false;
				}
				moving = true;
				movingTimer = 0;
			}
			else {
				moving = false;
				reversing = false;
				boosting = false;
				
				float velocityX = b2body.getLinearVelocity().x;
				float velocityY = b2body.getLinearVelocity().y;
				float applyImpulse = 0.05f;
				
				boolean toRight = false;
				boolean toUp = false;
				if(movingVelocity.x > 0) {
					toRight = true;
					velocityX -= dt + applyImpulse;
				}
				else if(movingVelocity.x < 0) {
					velocityX += dt + applyImpulse;
				}
				
				if(movingVelocity.y > 0) {
					toUp = true;
					velocityY -= dt + applyImpulse;
				}
				else if(movingVelocity.y < 0) {
					velocityY += dt + applyImpulse;
				}
				
				b2body.setLinearVelocity(new Vector2(velocityX, velocityY));
				
				if(toRight && velocityX < 0) {
					b2body.setLinearVelocity(new Vector2(0,velocityY));
					this.toRight = false;
					this.toLeft = false;
				}
				else if(!toRight && velocityX > 0) {
					b2body.setLinearVelocity(new Vector2(0,velocityY));
					this.toLeft = false;
					this.toRight = false;
				}
									
				if(toUp && velocityY < 0) {
					b2body.setLinearVelocity(new Vector2(velocityX,0));
					this.boosting = false;
					this.reversing = false;
				}
				else if(!toUp && velocityY > 0) {
					b2body.setLinearVelocity(new Vector2(velocityX,0));
					this.boosting = false;
					this.reversing = false;
				}
			}
			
			if(chooseSkill) {
				if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
					//Choose Skill, Pause
					choosingSkill = true;
				}
			}
			
			if(Gdx.input.isKeyJustPressed(Input.Keys.ALT_LEFT)) {
				chooseSkill = true;
			}
			
			if(choosingSkill) {
				if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
					if(skill.getNodes().get(0) != null) {
						skill.getNodes().get(0).setUnlocked(true);
						choosingSkill = false;
						justChooseSkill = true;
						chooseSkill = false;
					}
				}
				
				if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
					if(skill.getNodes().get(0) != null) {
						skill.getNodes().get(1).setUnlocked(true);
						choosingSkill = false;
						justChooseSkill = true;
						chooseSkill = false;
					}
				}
				
				if(skill.getNodes().size() > 2) {
					if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
						if(skill.getNodes().get(0) != null) {
							skill.getNodes().get(2).setUnlocked(true);
							choosingSkill = false;
							justChooseSkill = true;
							chooseSkill = false;
						}		
					}
				}
				
			}
			
		}

//		Gdx.app.log("Player Position", b2body.getPosition().x + " " + b2body.getPosition().y);
//		Gdx.app.log("Moving Velocity", movingVelocity.x + " " + movingVelocity.y);
	}
	
	public int getHighScore()
	{
		return highscore;
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
		return elapsedInvicible;
	}

	public void setElapsed(float elapsed) {
		this.elapsedInvicible = elapsed;
	}
	public ArrayList<BaseBullet> getBullet(){
		return bullet;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public Body getB2body() {
		return b2body;
	}

	public void setB2body(Body b2body) {
		this.b2body = b2body;
	}

	public float getBulletTimer() {
		return bulletTimer;
	}

	public void setBulletTimer(float bulletTimer) {
		this.bulletTimer = bulletTimer;
	}

	public Vector2 getBulletPosition() {
		return bulletPosition;
	}

	public void setBulletPosition(Vector2 bulletPosition) {
		this.bulletPosition = bulletPosition;
	}

	public void setBullet(ArrayList<BaseBullet> bullet) {
		this.bullet = bullet;
	}

	public int getHitpoints() {
		return hitpoints;
	}

	public void setHitpoints(int hitpoints) {
		this.hitpoints = hitpoints;
	}

	public double getLimitMovementSpeed() {
		return limitMovementSpeed;
	}

	public void setLimitMovementSpeed(double limitMovementSpeed) {
		this.limitMovementSpeed = limitMovementSpeed;
	}

	public float getMovementSpeed() {
		return movementSpeed;
	}

	public void setMovementSpeed(float movementSpeed) {
		this.movementSpeed = movementSpeed;
	}

	public float getRocketTimer() {
		return rocketTimer;
	}

	public void setRocketTimer(float rocketTimer) {
		this.rocketTimer = rocketTimer;
	}

	public int getDodgeRate() {
		return dodgeRate;
	}

	public void setDodgeRate(int dodgeRate) {
		this.dodgeRate = dodgeRate;
	}

	public int getCriticalRate() {
		return criticalRate;
	}

	public void setCriticalRate(int criticalRate) {
		this.criticalRate = criticalRate;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Node getSkill() {
		return skill;
	}

	public void setSkill(Node skill) {
		this.skill = skill;
	}

	public boolean isChoosingSkill() {
		return choosingSkill;
	}

	public void setChoosingSkill(boolean choosingSkill) {
		this.choosingSkill = choosingSkill;
	}

	public boolean isJustChooseSkill() {
		return justChooseSkill;
	}

	public void setJustChooseSkill(boolean justChooseSkill) {
		this.justChooseSkill = justChooseSkill;

	public void setBulletSpeed(float bulletSpeed) {
		this.bulletSpeed = bulletSpeed;
	}
	
	public float getBulletSpeed() {
		return bulletSpeed;

	}
}
