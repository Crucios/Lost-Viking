package com.mygdx.LostViking.Player.SkillTree.IncreaseDamage;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.LostViking.Player.Player;
import com.mygdx.LostViking.Player.SkillTree.Node;

public class IncreasedDamageEnhanced extends Node{

	public IncreasedDamageEnhanced(int size, Player player, World world) {
		super(size, player, world);
		textureRegion = new TextureRegion(texture, 135, 383, 135, 188);
	}

	@Override
	public void update(float dt) {
		if(unlocked && !updated) {
			player.setDamage(player.getDamage() + 5);
			updated = true;
		}
	}
}
