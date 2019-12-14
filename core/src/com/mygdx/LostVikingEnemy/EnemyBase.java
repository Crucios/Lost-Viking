package com.mygdx.LostVikingEnemy;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.LostViking.LostViking;
import com.mygdx.LostViking.Player.Player;
import com.mygdx.LostViking.Player.Bullet.BaseBullet;
import com.mygdx.LostVikingEnemy.Projectiles.BaseProjectiles;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public abstract class EnemyBase extends Sprite {
	public World world;
	public Body b2body;
	public Player player;
	protected TextureRegion enemy;
	protected Vector2 position;
	protected Vector2 nowPosition;
	protected Vector2 bulletPosition;
	protected float speed;
	protected float bulletTimer;
	protected int damage;
	protected int hitPoint;
	protected int score;
	protected int type;
	protected boolean isHit;
	protected boolean stop;
	protected boolean destroy;
	protected boolean miss;
	protected boolean hasDamaged;
	protected boolean hasScore;
	protected Random rand;
	private Sound sound;
	protected ArrayList<BaseProjectiles> enemyBullet;
	
	public EnemyBase(World world, Player player) {
		super(new AtlasRegion(new TextureAtlas("Enemy/enemy.pack").findRegion("Enemy Spaceship")));
		this.world = world;		
		this.destroy = false;
		this.damage = 4;
		this.speed = -1.5f;
		this.player = player;
		sound=Gdx.audio.newSound(Gdx.files.internal("Enemy/enemydead.mp3"));
		hasDamaged = false;
		hasScore= true;
		score = 0;
		bulletTimer = 2;
		stop = true;
		isHit = false;
		hitPoint = 17;
		rand = new Random();
		enemyBullet = new ArrayList<BaseProjectiles>();
		float xPosition = rand.nextInt(8) + rand.nextFloat() + 1;
		this.position = new Vector2(xPosition, 17.5f);
		
	}
	public void update(float dt) {
		setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
		b2body.setLinearVelocity(0,speed);
		if(position.y < 1 && stop) {
			isHit = true;	
		}
		if(hitPoint <= 0 && hasScore) {
        	player.setScore(player.getScore() + score);
        	sound.play(0.3f);
        	isHit = true;
        	hasScore = false;
        }
		setPosition(new Vector2(b2body.getPosition().x, b2body.getPosition().y));
		nowPosition = new Vector2(b2body.getPosition().x, b2body.getPosition().y);
		bulletPosition = new Vector2(b2body.getPosition().x, b2body.getPosition().y);
		bulletTimer += dt;
		for(BaseProjectiles bul : enemyBullet) {
			if(!bul.getDestroy()) {
				bul.update(dt);
			}
		}
		if(isHit) {
			world.destroyBody(b2body);
			hasScore = true;
			destroy = true;
			isHit = false;
			stop = false;
		}
		setRegion(enemy);
	}
	public void defineHitBox(int x, int y) {
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(x / LostViking.PPM, y / LostViking.PPM);
		
		fdef.shape = shape;
		fdef.friction = 0.0f;
		fdef.isSensor = true;
		b2body.createFixture(fdef);
		b2body.createFixture(fdef).setUserData(this);
	}
	
	public void definePistolBullet() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(position.x,position.y);
		bdef.type = BodyDef.BodyType.KinematicBody;
		b2body = world.createBody(bdef);
		defineHitBox(24,48);
	}
	public boolean getDestroy() {
		return destroy;
	}
	
	public void onHit() {
		isHit = true;
	}
	
	public ArrayList<BaseProjectiles> getEnemyBullet(){
		return enemyBullet;
	}
	public void onHit(Player player) {
		isHit = true;
		if(!player.getInvicible()) {
    		player.setHitpoints(player.getHitpoints() - 1);
    		player.setDamaged(true);
    	}
	}
	public void setPosition(Vector2 positions) {
		this.position = positions;
	}
	
	public Vector2 getNowPosition() {
		return nowPosition;
	}
	public void setNowPosition(Vector2 nowPosition) {
		this.nowPosition = nowPosition;
	}
	public Vector2 getPosition() {
		return position;
	}
	public int getHP() {
	      return hitPoint;
	}
    public void setHP(int hp) {
        hitPoint = hp;
    }
    public int getType() {
    	return type;
    }
}
