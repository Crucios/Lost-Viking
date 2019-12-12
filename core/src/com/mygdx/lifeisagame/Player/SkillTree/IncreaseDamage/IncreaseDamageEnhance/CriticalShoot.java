package com.mygdx.lifeisagame.Player.SkillTree.IncreaseDamage.IncreaseDamageEnhance;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.lifeisagame.Player.Player;
import com.mygdx.lifeisagame.Player.SkillTree.Node;

public class CriticalShoot extends Node{

	public CriticalShoot(int size, Player player, World world) {
		super(size, player, world);
		textureRegion = new TextureRegion(texture, 272, 192, 135, 188);
	}

	@Override
	public void update(float dt) {
		
	}
}
