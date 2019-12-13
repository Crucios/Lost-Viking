package com.mygdx.lifeisagame.Player.SkillTree;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.lifeisagame.Player.Player;

public class IncreasedSpeed extends Node{

	public IncreasedSpeed(int size, Player player, World world) {
		super(size, player, world);
		unlocked = false;
		textureRegion = new TextureRegion(texture, 272, 580, 135,188);
	}

	@Override
	public void update(float dt) {
		if(unlocked && !updated) {
			player.setBulletTimer(player.getBulletTimer() - 0.5f);
			updated = true;
		}
	}
}
