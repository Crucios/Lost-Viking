package com.mygdx.LostViking.Player;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.mygdx.LostViking.Player.Bullet.BaseBullet;
import com.mygdx.LostViking.Player.SkillTree.Node;
import com.mygdx.LostViking.Player.SkillTree.SkillTree;

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
	private boolean piercing;
	private boolean chooseSkill;
	private boolean choosingSkill;
	private boolean justChooseSkill;
	private Node skill;
	private boolean setToDestroy;
	private Sound sound;
	private Sound skillsound;

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
	private ArrayList<Boolean> checkScore;
	private int level;
	
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
	private float elapsedDestroyed;
	
	//Animation
	//State Player
	public enum State{SWING_LEFT, BOOST_LEFT, REVERSE_LEFT, STOP, BOOST, REVERSE, SWING_RIGHT, BOOST_RIGHT, REVERSE_RIGHT, DAMAGED, DESTROYED}
	public State currentState;
	public State previousState;
	private float stateTimer;
	private boolean hasDestroyed;
	
	public Player(World world, Vector2 position){
		super(new AtlasRegion(new TextureAtlas("Player/Player.pack").findRegion("Alternative Player")));
		this.world = world;
		this.position = position;
		sound=Gdx.audio.newSound(Gdx.files.internal("Player/Explosion.mp3"));
		skillsound=Gdx.audio.newSound(Gdx.files.internal("Player/Skill Unlock.mp3"));
		//Initialization
		camGlitched = false;
		shooting = false;
		damaged = false;
		invicible = false;
		level = 0;
		checkScore = new ArrayList<>();
		checkScore.add(false);
		checkScore.add(false);
		checkScore.add(false);
		
		toRight = false;
		toLeft = false;
		boosting = false;
		reversing = false;
		
		moving = false;
		movingTimer = 0;
		limitMovementSpeed = 2;
		movementSpeed = 0.5f;
		criticalRate = 10;
		bulletSpeed = 0.5f;
		movingVelocity = new Vector2(0,0);
		
		chooseSkill = false;
		justChooseSkill = false;
		choosingSkill = false;
		
		elapsedInvicible = 0;
		elapsedDestroyed = 0;
		setToDestroy = false;
		hasDestroyed = false;
		
		//bullet
		piercing = false;
		bulletTimer = 0.5f;
		bullet = new ArrayList<BaseBullet>();
		rocketTimer = 5;
		
		//Properties
		hitpoints = 5;
		damage = 5;
		dodgeRate = 0;
		criticalRate = 0;
		score = 0;
		highscore=0;
		
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
		elapsedDestroyed += dt;
		
		//Update Last Skill
		skill = skillTree.getLastSkill(skillTree.getRoot());
		
		if(moving)
			movingVelocity = b2body.getLinearVelocity();
		
		//Set Position
		setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight() / 2);
		nowPosition = new Vector2(b2body.getPosition().x, b2body.getPosition().y);
		
		//Bullet
		if(currentState != State.DESTROYED)
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
		
		//Set Texture Region
		setRegion(getFrame(dt));
		
		//Check dead or not
		if(currentState==State.DESTROYED && !setToDestroy)
		{
			elapsedDestroyed = 0;
		    setToDestroy = true;
		    sound.play(0.3f);
		    b2body.setLinearVelocity(new Vector2(0, 0));
		}
		
		if(setToDestroy && elapsedDestroyed > 2 && !hasDestroyed) {
			 if (currentScore > highscore) {
		            preferences.putInteger("highscore", highscore);
		            preferences.flush();
		    }
			 highscore = preferences.getInteger("highscore");
		    hasDestroyed = true;
		}
		
		if(score >= 300 && !checkScore.get(0)) {
			chooseSkill = true;
			checkScore.set(0, true);
			skillsound.play(0.5f);
		}
		else if(score >= 500 && !checkScore.get(1)) {
			chooseSkill = true;
			checkScore.set(1, true);
			skillsound.play(0.5f);
		}
		else if(score >= 700 && !checkScore.get(2)) {
			chooseSkill = true;
			checkScore.set(2, true);
			skillsound.play(0.5f);
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
			
//			if(Gdx.input.isKeyJustPressed(Input.Keys.ALT_LEFT)) {
//				chooseSkill = true;
//			}
			
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
					if(skill.getNodes().get(1) != null) {
						skill.getNodes().get(1).setUnlocked(true);
						choosingSkill = false;
						justChooseSkill = true;
						chooseSkill = false;
					}
				}
				
				if(skill.getNodes().size() > 2) {
					if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
						if(skill.getNodes().get(2) != null) {
							skill.getNodes().get(2).setUnlocked(true);
							choosingSkill = false;
							justChooseSkill = true;
							chooseSkill = false;
						}		
					}
				}
				
			}
			
		}
//		System.out.println(Gdx.input.getX()/LostViking.PPM + " " + -Gdx.input.getY()/LostViking.PPM);
//		if(Gdx.input.getX()/LostViking.PPM*1.9f - 2f> 0.5f && Gdx.input.getX()/LostViking.PPM*1.9f - 2f < 9.5f && -Gdx.input.getY()/LostViking.PPM*1.9f + 18.5f > 0.5 &&-Gdx.input.getY()/LostViking.PPM*1.9f + 18.5f < 16.5) {
//			b2body.setTransform(new Vector2(Gdx.input.getX()/LostViking.PPM*1.9f - 2f,-Gdx.input.getY()/LostViking.PPM*1.9f + 18.5f),0);
//		}
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

	public boolean getInvicible() {
		return invicible;
	}
	
	public void setInvicible(boolean invicible) {
		this.invicible = invicible;
	}
	
	public float getElapsed() {
		return elapsedInvicible;
	}

	public void setElapsed(float elapsed) {
		this.elapsedInvicible = elapsed;
	}
	
	public void setDamaged(boolean damaged) {
		this.damaged = damaged;
	}
	
	public boolean getDamaged() {
		return damaged;
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
	}
	
	public void setBulletSpeed(float bulletSpeed) {
		this.bulletSpeed = bulletSpeed;
	}
	
	public float getBulletSpeed() {
		return bulletSpeed;
	}
	public boolean getPiercing() {
		return piercing;
	}
	public void setPiercing(boolean isPiercing) {
		piercing = isPiercing;
	}

	public boolean isHasDestroyed() {
		return hasDestroyed;
	}

	public void setHasDestroyed(boolean hasDestroyed) {
		this.hasDestroyed = hasDestroyed;
	}

	public boolean isSetToDestroy() {
		return setToDestroy;
	}

	public void setSetToDestroy(boolean setToDestroy) {
		this.setToDestroy = setToDestroy;
	}
	
	public boolean isChooseSkill() {
		return chooseSkill;
	}

	public void setChooseSkill(boolean chooseSkill) {
		this.chooseSkill = chooseSkill;
	}
}
