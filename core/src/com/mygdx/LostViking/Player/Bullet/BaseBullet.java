package com.mygdx.LostViking.Player.Bullet;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.LostViking.LostViking;
import com.mygdx.LostViking.Player.Player;
import com.mygdx.LostVikingEnemy.EnemyBase;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class BaseBullet extends Sprite {
	public World world;
	public Body b2body;
	public Player player;
	private TextureRegion bullet;
	private TextureRegion explode;
	private Vector2 position;
	private Vector2 nowPosition;
	private float speed;
	private float angle;
	private int damage;
	private int criticalRate;
	private boolean isBullet;
	private boolean isHit;
	private boolean stop;
	private boolean destroy;
	private boolean miss;
	private boolean hasDamaged;
	private EnemyBase enemy;
	private Random rand;
	
	public BaseBullet(World world, Vector2 position,float angle,boolean isBullet,Player player) {
		super(new AtlasRegion(new TextureAtlas("Player/Player.pack").findRegion("Alternative Player")));
		this.world = world;		
		this.destroy = false;
		this.player = player;
		this.damage = player.getDamage();
		this.criticalRate = player.getCriticalRate();
		if(isBullet) {
			this.speed = 9f;
		}
		else {
			this.speed = 6f;
		}
		this.isBullet = isBullet;
		hasDamaged = false;
		stop = true;
		isHit = false;
		this.angle = angle;
		this.position = position;
		rand = new Random();
		explode = new TextureRegion(getTexture(), 468,282,6,15);
		if(isBullet) {
			bullet = new TextureRegion(getTexture(), 468,282,6,15);
			setBounds(468,282,6 / LostViking.PPM * 2,15 / LostViking.PPM*2);
		}
		else {
			bullet = new TextureRegion(getTexture(),389,133,6,15);
			setBounds(389,133,6 / LostViking.PPM * 4,15 / LostViking.PPM*3);
		}
		
		definePistolBullet();
	}
	public void update(float dt) {
		this.damage = player.getDamage();
		this.criticalRate = player.getCriticalRate();
		if(isBullet) {
			setPosition(position.x - getWidth()/2,position.y - getHeight()/2);
		}
		else {
			setPosition(position.x - getWidth()/2,position.y - getHeight()/2);
		}
		if(angle > 0)
			b2body.setLinearVelocity(angle / -15f, speed);
		else if(angle < 0)
			b2body.setLinearVelocity(angle / -15f, speed);
		else{
			b2body.setLinearVelocity(0, speed);
		}
		if(hasDamaged) {			
			if(rand.nextInt(100)< criticalRate) {
				enemy.setHP(enemy.getHP() - damage * 2);
			}
			else {
				enemy.setHP(enemy.getHP() - damage);
			}
			hasDamaged = false;
		}
		if(position.y > 17f && stop) {
			isHit = true;	
		}
		setPosition(new Vector2(b2body.getPosition().x, b2body.getPosition().y));
		nowPosition = new Vector2(b2body.getPosition().x, b2body.getPosition().y);
		if(isHit) {
			world.destroyBody(b2body);
			destroy = true;
			isHit = false;
			stop = false;
		}
		setRegion(bullet);
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
		bdef.position.set(position.x,position.y + 0.6f);
		if(angle != 0) {
			bdef.angle = (float) (angle*Math.PI/180);
		}
		bdef.type = BodyDef.BodyType.KinematicBody;
		b2body = world.createBody(bdef);
		if(isBullet)
			defineHitBox(3,9);
		else {
			defineHitBox(6,18);
		}
	}
	public boolean getDestroy() {
		return destroy;
	}
	
	public void onHit(EnemyBase enemy) {
		if(!player.getPiercing()) {
			isHit = true;
		}
		this.enemy = enemy;
		hasDamaged = true;
	}
	
	public void setHit(boolean hit) {
		this.isHit = hit;
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
}

