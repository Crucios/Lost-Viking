package com.mygdx.lifeisagame.Player.SkillTree;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.lifeisagame.Player.Player;

public class IncreasedDamage extends Node{
	private int increasedamage;
	private boolean updated;
	
	public IncreasedDamage(int size, Player player, World world) {
		super(size, player, world);
		updated = false;
		increasedamage = 3;
	}

	@Override
	public void update(float dt) {
		if(unlocked && !updated) {
			player.setDamage(player.getDamage() + increasedamage);
			updated = true;
		}
	}
}
