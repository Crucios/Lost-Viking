package com.mygdx.LostVikingEnemy;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.LostViking.LostViking;
import com.mygdx.LostViking.Player.Player;

public class straight_melee extends EnemyBase {

	public straight_melee(World world,Player player) {
		super(world,player);
		// TODO Auto-generated constructor stub	
		this.type = 0;
		score = 5;
		speed = -3.5f;
		hitPoint = 15;
		enemy = new TextureRegion(getTexture(), 32,36,89, 89);
		enemy.flip(false, true);
		setBounds(32,36,89 / LostViking.PPM *1.5f,89 / LostViking.PPM * 1.5f);
		definePistolBullet();
	}
	@Override
	public void update(float dt) {
		setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
		b2body.setLinearVelocity(0,speed);
		if((position.y < -0.5f || position.x > 10) && stop) {
			isHit = true;	
		}
		if(hitPoint <= 0 && hasScore) {
        	player.setScore(player.getScore() + score);
        	isHit = true;
        	hasScore = false;
        }
		setPosition(new Vector2(b2body.getPosition().x, b2body.getPosition().y));
		nowPosition = new Vector2(b2body.getPosition().x, b2body.getPosition().y);
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
		defineHitBox(46,52);
	}
}
