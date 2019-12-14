package com.mygdx.LostViking.Player.SkillTree.IncreaseSpeed.PiercingShot;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.LostViking.Player.Player;
import com.mygdx.LostViking.Player.SkillTree.Node;

public class IncreasedHitpointEnhanced extends Node{

	public IncreasedHitpointEnhanced(int size, Player player, World world) {
		super(size, player, world);
		textureRegion = new TextureRegion(texture, 0, 580, 135, 188);
	}

	@Override
	public void update(float dt) {
		if(unlocked && !updated) {
			player.setHitpoints(player.getHitpoints() + 4);
			updated = true;
		}
	}
}
