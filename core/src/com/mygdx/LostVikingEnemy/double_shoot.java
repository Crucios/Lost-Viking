package com.mygdx.LostVikingEnemy;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.LostViking.LostViking;
import com.mygdx.LostViking.Player.Player;
import com.mygdx.LostVikingEnemy.Projectiles.BaseProjectiles;

public class double_shoot extends EnemyBase {

	public double_shoot(World world, Player player) {
		super(world,player);
		// TODO Auto-generated constructor stub
		score = 20;
		this.type = 2;
		enemy = new TextureRegion(getTexture(), 175,500,165, 130);
		enemy.flip(false, true);
		hitPoint = 16;
		speed = -1.3f;
		setBounds(175,500,165 / LostViking.PPM*1.2f,130 / LostViking.PPM*1.2f);
		definePistolBullet();
	}
	@Override
	public void update(float dt) {
		setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
		b2body.setLinearVelocity(0,speed);
		if(position.y < -0.5f && stop) {
			isHit = true;	
		}
		if(hitPoint <= 0 && hasScore) {
        	player.setScore(player.getScore() + score);
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
		if(bulletTimer > 4f) {
			enemyBullet.add(new BaseProjectiles(world,new Vector2(bulletPosition.x + 0.8f, bulletPosition.y),0));
			enemyBullet.add(new BaseProjectiles(world,new Vector2(bulletPosition.x - 0.8f, bulletPosition.y),0));
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
		defineHitBox(70,52);
	}

}
