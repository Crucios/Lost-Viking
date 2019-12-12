package com.mygdx.lifeisagame.Enemy;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.LostViking.LostViking;
import com.mygdx.lifeisagame.Enemy.Projectiles.BaseProjectiles;
import com.mygdx.lifeisagame.Player.Player;

public class side_melee extends EnemyBase{
	protected int cekRight;
	protected float randomHeight;
	protected float XSpeed;
	public side_melee(World world, Player player) {
		super(world, player);
		this.cekRight = rand.nextInt(2);
		this.randomHeight = rand.nextInt(14) + rand.nextFloat();
		this.XSpeed = rand.nextInt(5) + rand.nextFloat() + 2f;
		// TODO Auto-generated constructor stub
		if(cekRight == 1) {
			this.position = new Vector2(0, randomHeight + 2);
		}
		else {
			this.position = new Vector2(10,randomHeight + 2);
		}
		enemy = new TextureRegion(getTexture(), 200,46,70, 70);
		enemy.flip(false, true);
		setBounds(200,46,70 / LostViking.PPM * 1.3f,70 / LostViking.PPM * 1.3f);
		definePistolBullet();
	}
	@Override
	public void update(float dt) {
		setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
		if(cekRight == 1) {
			b2body.setLinearVelocity(XSpeed,speed);
		}
		else {
			b2body.setLinearVelocity(-XSpeed,speed);
		}
		if((position.y < 1 || position.x > 9.5f) && stop) {
			isHit = true;	
		}
		if(hitPoint <= 0) {
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
		setRegion(enemy);	
	}
	@Override
	public void definePistolBullet() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(position.x,position.y);
		bdef.type = BodyDef.BodyType.KinematicBody; 
		b2body = world.createBody(bdef);
		defineHitBox(35,48);
	}
}
