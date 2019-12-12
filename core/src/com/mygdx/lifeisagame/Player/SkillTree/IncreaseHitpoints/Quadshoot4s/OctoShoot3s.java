package com.mygdx.lifeisagame.Player.SkillTree.IncreaseHitpoints.Quadshoot4s;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.lifeisagame.Player.Player;
import com.mygdx.lifeisagame.Player.Bullet.BaseBullet;
import com.mygdx.lifeisagame.Player.SkillTree.Node;

public class OctoShoot3s extends Node{
	private float bulletTimer;
	private Vector2 bulletPosition;
	private Body b2bodyPlayer;
	private ArrayList<BaseBullet> bullet;
	private World world;
	
	public OctoShoot3s(int size, Player player, World world) {
		super(size, player, world);
		unlocked = false;
		b2bodyPlayer = this.player.getB2body();
		this.bullet = this.player.getBullet();
		this.world = world;
		textureRegion = new TextureRegion(texture, 134, 192, 135, 188);
	}
	
	@Override
	public void update(float dt) {
		if(unlocked) {
			bulletTimer += dt;
			bulletPosition = new Vector2(b2bodyPlayer.getPosition().x, b2bodyPlayer.getPosition().y + 0.2f);
			for(BaseBullet bul : bullet) {
				if(!bul.getDestroy()) {
					bul.update(dt);
				}
			}
			if(bulletTimer > 3f) {
			bullet.add(new BaseBullet(world,new Vector2(bulletPosition.x - 0.1f, bulletPosition.y),0, true));
			bullet.add(new BaseBullet(world,new Vector2(bulletPosition.x + 0.1f, bulletPosition.y),0,true));
			bullet.add(new BaseBullet(world,new Vector2(bulletPosition.x - 0.3f, bulletPosition.y),0, true));
			bullet.add(new BaseBullet(world,new Vector2(bulletPosition.x + 0.3f, bulletPosition.y),0,true));
			bullet.add(new BaseBullet(world,new Vector2(bulletPosition.x - 0.5f, bulletPosition.y),0, true));
			bullet.add(new BaseBullet(world,new Vector2(bulletPosition.x + 0.5f, bulletPosition.y),0,true));
			bullet.add(new BaseBullet(world,new Vector2(bulletPosition.x - 0.7f, bulletPosition.y),0, true));
			bullet.add(new BaseBullet(world,new Vector2(bulletPosition.x + 0.7f, bulletPosition.y),0,true));
			bulletTimer = 0;
			}
		}	
	}

}
