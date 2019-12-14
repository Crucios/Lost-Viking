package com.mygdx.LostViking.Player.SkillTree.IncreaseSpeed;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.LostViking.Player.Player;
import com.mygdx.LostViking.Player.SkillTree.Node;

public class IncreasedMovementSpeed extends Node{

	public IncreasedMovementSpeed(int size, Player player, World world) {
		super(size, player, world);
		textureRegion = new TextureRegion(texture, 410, 192, 135, 188);
	}

	@Override
	public void update(float dt) {
		if(unlocked && !updated) {
			player.setLimitMovementSpeed(player.getLimitMovementSpeed() + 1f);
			player.setMovementSpeed(player.getMovementSpeed() + 1f);
			updated = true;
		}
	}
}
