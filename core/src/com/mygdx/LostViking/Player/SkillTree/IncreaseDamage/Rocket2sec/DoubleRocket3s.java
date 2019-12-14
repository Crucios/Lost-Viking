package com.mygdx.LostViking.Player.SkillTree.IncreaseDamage.Rocket2sec;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.LostViking.Player.Player;
import com.mygdx.LostViking.Player.Bullet.BaseBullet;
import com.mygdx.LostViking.Player.SkillTree.Node;

public class DoubleRocket3s extends Node{
	private float bulletTimer;
	private Vector2 bulletPosition;
	private Body b2bodyPlayer;
	private ArrayList<BaseBullet> bullet;
	private World world;
	private Sound sound;
	public DoubleRocket3s(int size, Player player, World world) {
		super(size, player, world);
		b2bodyPlayer = this.player.getB2body();
		this.bullet = this.player.getBullet();
		this.world = world;
		sound=Gdx.audio.newSound(Gdx.files.internal("Player/Rocket.mp3"));
		textureRegion = new TextureRegion(texture, 0, 383, 135, 188);
	}

	@Override
	public void update(float dt) {
		if(unlocked) {
			//bullet	
			bulletTimer += dt;
			bulletPosition = new Vector2(b2bodyPlayer.getPosition().x, b2bodyPlayer.getPosition().y + 0.2f);
			//System.out.println("bullet Position: "+bulletPosition.x + " " + bulletPosition.y);
			for(BaseBullet bul : bullet) {
				if(!bul.getDestroy()) {
					bul.update(dt);
				}
			}
			if(bulletTimer > 3f) {
				sound.play(0.3f);
				bullet.add(new BaseBullet(world,new Vector2(bulletPosition.x, bulletPosition.y + 0.2f),0,false,player));
				sound.play(0.3f);
				bullet.add(new BaseBullet(world,new Vector2(bulletPosition.x, bulletPosition.y + 0.7f),0,false,player));
				bulletTimer = 0;
			}
		}
	}
}
