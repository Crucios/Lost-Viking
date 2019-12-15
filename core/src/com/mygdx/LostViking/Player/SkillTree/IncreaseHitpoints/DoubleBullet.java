package com.mygdx.LostViking.Player.SkillTree.IncreaseHitpoints;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.LostViking.Player.Player;
import com.mygdx.LostViking.Player.Bullet.BaseBullet;
import com.mygdx.LostViking.Player.SkillTree.Node;

public class DoubleBullet extends Node{
	private float bulletTimer;
	private Vector2 bulletPosition;
	private Body b2bodyPlayer;
	private ArrayList<BaseBullet> bullet;
	private World world;
	
	public DoubleBullet(int size, Player player, World world) {
		super(size, player, world);
		b2bodyPlayer = this.player.getB2body();
		this.bullet = this.player.getBullet();
		this.world = world;
		textureRegion = new TextureRegion(texture, 0, 0, 135, 188);
	}
	
	@Override
	public void update(float dt) {
		bulletPosition = new Vector2(b2bodyPlayer.getPosition().x, b2bodyPlayer.getPosition().y + 0.1f);
		bulletTimer += dt;
		for(BaseBullet bul : bullet) {
			if(!bul.getDestroy()) {
				bul.update(dt);
			}
		}
		if(bulletTimer > 0.5f) {
		
		bullet.add(new BaseBullet(world,new Vector2(bulletPosition.x - 0.1f, bulletPosition.y),0, true,player));
		bullet.add(new BaseBullet(world,new Vector2(bulletPosition.x + 0.1f, bulletPosition.y),0, true,player));
		
		bulletTimer = 0;
		}
	}

}
