package com.mygdx.LostVikingEnemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.LostViking.LostViking;
import com.mygdx.LostViking.Player.Player;
import com.mygdx.LostVikingEnemy.Projectiles.BaseProjectiles;

public class BOSS extends EnemyBase{

	private Sound sound;
	private float bulletTimer2;
	private float lasserTimer;
	private boolean prev;
	private boolean laserActive;
	private boolean isShooting;
	public BOSS(World world, Player player) {
		super(world, player);
		// TODO Auto-generated constructor stub
		this.type = 5;
		score = 100;
		sound=Gdx.audio.newSound(Gdx.files.internal("Enemy/enemydead.mp3"));
		enemy = new TextureRegion(getTexture(), 254,625,200, 130);
		enemy.flip(false, true);
		laserActive = false;
		prev = false;
		isShooting = false;
		hitPoint = 700;
		bulletTimer2 = 9f;
		lasserTimer = 3f;
		speed = -2.5f;
		this.position = new Vector2(5, 19f);
		setBounds(254,625,200 / LostViking.PPM * 2.5f,130 / LostViking.PPM * 2f);		
		definePistolBullet();
	}
	@Override
	public void update(float dt) {
		setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
		System.out.println(hitPoint);
		if(position.y > 15.5f) {
			b2body.setLinearVelocity(0,-1f);
		}
		else {
			isShooting = true;
			if(position.x > 8f && stop) {
				b2body.setLinearVelocity(-3f,0);
				prev = false;
			}
			else if(position.x < 2f && stop) {
				b2body.setLinearVelocity(3f,0);
				prev = true;
			}
			else {
				if(prev) {
					b2body.setLinearVelocity(3f,0f);
				}
				else {
					b2body.setLinearVelocity(-3f,0f);
				}
			}
		}
		if(position.y < -0.5f && stop) {
			isHit = true;	
		}
		if(hitPoint <= 0 && hasScore) {
        	player.setScore(player.getScore() + score);
        	isHit = true;
        	hasScore = false;
        	sound.play(0.3f);
        }
		setPosition(new Vector2(b2body.getPosition().x, b2body.getPosition().y));
		nowPosition = new Vector2(b2body.getPosition().x, b2body.getPosition().y);
		bulletPosition = new Vector2(b2body.getPosition().x, b2body.getPosition().y);
		bulletTimer += dt;
		bulletTimer2 += dt;
		lasserTimer += dt;
		for(BaseProjectiles bul : enemyBullet) {
			if(!bul.getDestroy()) {
				bul.update(dt);
			}
		}
		if(!laserActive && bulletTimer2 > 9f) {
			laserActive = true;
			bulletTimer2 = 0;
		}
		else {
			if(laserActive && bulletTimer2 > 1f) {
				laserActive = false;
			}
		}
		if(laserActive && lasserTimer > 0.2f && isShooting) {
			enemyBullet.add(new BaseProjectiles(world,new Vector2(bulletPosition.x, bulletPosition.y- 0.6f),0));
			enemyBullet.add(new BaseProjectiles(world,new Vector2(bulletPosition.x + 2.4f, bulletPosition.y- 0.6f),0));
			enemyBullet.add(new BaseProjectiles(world,new Vector2(bulletPosition.x - 2.4f, bulletPosition.y- 0.6f),0));
			enemyBullet.add(new BaseProjectiles(world,new Vector2(bulletPosition.x + 4.8f, bulletPosition.y- 0.6f),0));
			enemyBullet.add(new BaseProjectiles(world,new Vector2(bulletPosition.x - 4.8f, bulletPosition.y- 0.6f),0));
			lasserTimer = 0;
		}
		if(bulletTimer > 2f && hitPoint > 2 && isShooting) {
			enemyBullet.add(new BaseProjectiles(world,new Vector2(bulletPosition.x + 0.2f, bulletPosition.y- 0.6f),0));
			enemyBullet.add(new BaseProjectiles(world,new Vector2(bulletPosition.x - 0.2f, bulletPosition.y- 0.6f),0));
			enemyBullet.add(new BaseProjectiles(world,new Vector2(bulletPosition.x, bulletPosition.y- 0.6f),-30));
			enemyBullet.add(new BaseProjectiles(world,new Vector2(bulletPosition.x, bulletPosition.y- 0.6f),30));
			enemyBullet.add(new BaseProjectiles(world,new Vector2(bulletPosition.x, bulletPosition.y- 0.6f),-15));
			enemyBullet.add(new BaseProjectiles(world,new Vector2(bulletPosition.x, bulletPosition.y- 0.6f),15));
			enemyBullet.add(new BaseProjectiles(world,new Vector2(bulletPosition.x, bulletPosition.y- 0.6f),-7.5f));
			enemyBullet.add(new BaseProjectiles(world,new Vector2(bulletPosition.x, bulletPosition.y- 0.6f),7.5f));
			bulletTimer = 0;
		}
		if(isHit) {
			world.destroyBody(b2body);
			destroy = true;
			isHit = false;
			stop = false;
		}
		setRegion(enemy);	
	}
	@Override
	public void definePistolBullet() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(position.x,position.y);
		bdef.type = BodyDef.BodyType.KinematicBody;
		b2body = world.createBody(bdef);
		defineHitBox(200,120);
	}

}
