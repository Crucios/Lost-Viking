package com.mygdx.lifeisagame.Player.SkillTree;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.lifeisagame.Player.Player;

public class IncreasedHitpoint extends Node{

	private Body b2bodyPlayer;
	private World world;
	
	public IncreasedHitpoint(int size, Player player, World world) {
		super(size, player, world);
		unlocked = false;
		b2bodyPlayer = this.player.getB2body();
		this.world = world;
		textureRegion = new TextureRegion(texture, 0, 580, 135, 188);
	}

	@Override
	public void update(float dt) {
		if(unlocked && !updated) {
			player.setHitpoints(player.getHitpoints() + 5);
			updated = true;
		}
	}
}
