package com.mygdx.lifeisagame.Player.SkillTree.IncreaseHitpoints;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.lifeisagame.Player.Player;
import com.mygdx.lifeisagame.Player.Bullet.BaseBullet;
import com.mygdx.lifeisagame.Player.SkillTree.Node;

public class QuadShoot4s extends Node{
	private float bulletTimer;
	private Vector2 bulletPosition;
	private Body b2bodyPlayer;
	private float bulletTimer2;
	private ArrayList<BaseBullet> bullet;
	private World world;
	public QuadShoot4s(int size, Player player, World world) {
		super(size, player, world);
		bulletTimer2 = 3;
		unlocked = true;
		b2bodyPlayer = this.player.getB2body();
		this.bullet = this.player.getBullet();
		this.world = world;
		textureRegion = new TextureRegion(texture, 410, 0, 135, 188);
	}

	@Override
	public void update(float dt) {
		//bullet	
		bulletTimer += dt;
		bulletTimer2 += dt;
		bulletPosition = new Vector2(b2bodyPlayer.getPosition().x, b2bodyPlayer.getPosition().y + 0.2f);
		//System.out.println("bullet Position: "+bulletPosition.x + " " + bulletPosition.y);
		for(BaseBullet bul : bullet) {
			if(!bul.getDestroy()) {
				bul.update(dt);
			}
		}
		if(bulletTimer2 > 2f) {
		bullet.add(new BaseBullet(world,new Vector2(bulletPosition.x - 0.1f, bulletPosition.y),0, true));
		bullet.add(new BaseBullet(world,new Vector2(bulletPosition.x + 0.1f, bulletPosition.y),0,true));
		bullet.add(new BaseBullet(world,new Vector2(bulletPosition.x - 0.3f, bulletPosition.y),0, true));
		bullet.add(new BaseBullet(world,new Vector2(bulletPosition.x + 0.3f, bulletPosition.y),0,true));
		bulletTimer2 = 0;
		}
	}
}
