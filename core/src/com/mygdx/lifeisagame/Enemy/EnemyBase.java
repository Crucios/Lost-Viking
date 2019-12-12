package com.mygdx.lifeisagame.Enemy;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.LostViking.LostViking;
import com.mygdx.lifeisagame.Enemy.Projectiles.BaseProjectiles;
import com.mygdx.lifeisagame.Player.Bullet.BaseBullet;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class EnemyBase extends Sprite {
	public World world;
	public Body b2body;
	private TextureRegion bullet;
	private Vector2 position;
	private Vector2 nowPosition;
	private Vector2 bulletPosition;
	private float speed;
	private float bulletTimer;
	private int damage;
	private int hitPoint;
	private int type;
	private boolean isHit;
	private boolean stop;
	private boolean destroy;
	private boolean miss;
	private boolean hasDamaged;
	private Random rand;
	private ArrayList<BaseProjectiles> enemyBullet;
	
	public EnemyBase(World world, int type) {
		super(new AtlasRegion(new TextureAtlas("Item/bullet.pack").findRegion("bullet")));
		this.world = world;		
		this.destroy = false;
		this.damage = 4;
		this.type = type;
		hasDamaged = false;
		bulletTimer = 2;
		stop = true;
		isHit = false;
		hitPoint = 10;
		rand = new Random();
		enemyBullet = new ArrayList<BaseProjectiles>();
		float xPosition = rand.nextInt(8) + rand.nextFloat() + 1;
		this.position = new Vector2(xPosition, 17f);	
		bullet = new TextureRegion(getTexture(), 0,0,12,4);
		setBounds(0,0,12 / LostViking.PPM,4 / LostViking.PPM);
		definePistolBullet();
	}
	public void update(float dt) {
		setPosition(position.x - getWidth()/8,position.y - getHeight()/2);
		b2body.setLinearVelocity(0,-1.5f);
		if(position.y < 1 && stop) {
			isHit = true;	
		}
		if(hitPoint <= 0) {
        	isHit = true;
        }
		setPosition(new Vector2(b2body.getPosition().x, b2body.getPosition().y));
		nowPosition = new Vector2(b2body.getPosition().x, b2body.getPosition().y);
		if(type != 0) {
			bulletPosition = new Vector2(b2body.getPosition().x, b2body.getPosition().y);
			bulletTimer += dt;
			for(BaseProjectiles bul : enemyBullet) {
				if(!bul.getDestroy()) {
					bul.update(dt);
				}
			}
			if(type == 1) {
				if(bulletTimer > 3f) {
					enemyBullet.add(new BaseProjectiles(world,new Vector2(bulletPosition.x, bulletPosition.y),0));
					bulletTimer = 0;
				}
			}
			else if(type == 2) {
				if(bulletTimer > 4f) {
					enemyBullet.add(new BaseProjectiles(world,new Vector2(bulletPosition.x + 0.8f, bulletPosition.y),0));
					enemyBullet.add(new BaseProjectiles(world,new Vector2(bulletPosition.x - 0.8f, bulletPosition.y),0));
					bulletTimer = 0;
				}
			}
			else if(type == 3) {
				if(bulletTimer > 0.4f) {
					enemyBullet.add(new BaseProjectiles(world,new Vector2(bulletPosition.x, bulletPosition.y),-60));
					enemyBullet.add(new BaseProjectiles(world,new Vector2(bulletPosition.x, bulletPosition.y),60));
					bulletTimer = 0;
				}
			}
			
		}
		if(isHit) {
			world.destroyBody(b2body);
			
			destroy = true;
			isHit = false;
			stop = false;
		}
	}
	public void defineHitBox(int x, int y) {
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(x / LostViking.PPM, y / LostViking.PPM);
		
		fdef.shape = shape;
		fdef.friction = 0.0f;
		fdef.isSensor = true;
		b2body.createFixture(fdef);
		b2body.createFixture(fdef).setUserData("enemy");
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
		
	}
	
	public ArrayList<BaseProjectiles> getEnemyBullet(){
		return enemyBullet;
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
	public int getHP() {
	      return hitPoint;
	}
    public void setHP(int hp) {
        hitPoint = hp;
    }
}
