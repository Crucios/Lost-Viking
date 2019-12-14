package com.mygdx.LostVikingEnemy.Projectiles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.LostViking.LostViking;
import com.mygdx.LostViking.Player.Player;
import com.mygdx.LostVikingEnemy.EnemyBase;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class BaseProjectiles extends Sprite {
	public World world;
	public Body b2body;
	private TextureRegion bullet;
	private Vector2 position;
	private Vector2 nowPosition;
	private float speed;
	private float angle;
	private int damage;
	private boolean isBullet;
	private boolean isHit;
	private boolean stop;
	private boolean destroy;
	private boolean miss;
	private boolean hasDamaged;
	
	public BaseProjectiles(World world, Vector2 position,float angle) {
		super(new AtlasRegion(new TextureAtlas("Player/Player.pack").findRegion("Alternative Player")));
		this.world = world;		
		this.destroy = false;
		this.damage = 2;
		this.isBullet = isBullet;
		hasDamaged = false;
		stop = true;
		isHit = false;
		this.angle = angle;
		this.position = position;
		bullet = new TextureRegion(getTexture(), 533,284,12,12);
		setBounds(533,284,12 / LostViking.PPM*3,12 / LostViking.PPM*3);
		definePistolBullet();
	}
	public void update(float dt) {
		setPosition(position.x - getWidth()/8,position.y - getHeight()/2);
		if(angle > 0)
			b2body.setLinearVelocity(angle / -7.5f, -3f);
		else if(angle < 0)
			b2body.setLinearVelocity(angle / -7.5f, -3f);
		else{
			b2body.setLinearVelocity(0, -6f);
		}
		if(hasDamaged) {
			hasDamaged = false;
		}
		if(position.y < -0.5f && stop) {
			isHit = true;	
		}
		setPosition(new Vector2(b2body.getPosition().x - 0.08f, b2body.getPosition().y - 0.04f));
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
		bdef.position.set(position.x,position.y);
		if(angle != 0) {
			bdef.angle = (float) -(angle*Math.PI/180);
		}
		bdef.type = BodyDef.BodyType.KinematicBody;
		b2body = world.createBody(bdef);
		defineHitBox(5,5);
	}
	public boolean getDestroy() {
		return destroy;
	}
	
	public void onHit(Player player) {
		isHit = true;
		if(!player.getInvicible()) {
    		player.setHitpoints(player.getHitpoints() - 1);
    		player.setDamaged(true);
    	}
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


