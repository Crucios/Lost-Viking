package com.mygdx.LostViking.Player.SkillTree;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.LostViking.Player.Player;

public class IncreasedDamage extends Node{
	public IncreasedDamage(int size, Player player, World world) {
		super(size, player, world);
		textureRegion = new TextureRegion(texture, 272, 383, 135, 188);
		updated = false;
	}

	@Override
	public void update(float dt) {
		if(unlocked && !updated) {
			player.setDamage(player.getDamage() + 3);
			updated = true;
		}
	}
}
