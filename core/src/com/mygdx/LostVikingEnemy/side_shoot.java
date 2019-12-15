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

public class side_shoot extends EnemyBase {
	private Sound sound;
	public side_shoot(World world, Player player) {
		super(world, player);
		// TODO Auto-generated constructor stub
		this.type = 4;
		score = 20;
		sound=Gdx.audio.newSound(Gdx.files.internal("Enemy/enemydead.mp3"));
		enemy = new TextureRegion(getTexture(), 36,211,89, 89);
		enemy.flip(false, true);
		hitPoint = 14;
		speed = -2.5f;
		setBounds(36,211,89 / LostViking.PPM * 1.3f,89 / LostViking.PPM * 1.3f);
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
        	sound.play(0.3f);
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
		if(bulletTimer > 1.5f && hitPoint > 2) {
			enemyBullet.add(new BaseProjectiles(world,new Vector2(bulletPosition.x, bulletPosition.y- 0.3f),-15));
			enemyBullet.add(new BaseProjectiles(world,new Vector2(bulletPosition.x, bulletPosition.y- 0.3f),15));
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
		defineHitBox(36,52);
	}

}
