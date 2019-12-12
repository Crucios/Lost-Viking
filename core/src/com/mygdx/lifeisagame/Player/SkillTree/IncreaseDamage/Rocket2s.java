package com.mygdx.lifeisagame.Player.SkillTree.IncreaseDamage;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.lifeisagame.Player.Player;
import com.mygdx.lifeisagame.Player.SkillTree.Node;

public class Rocket2s extends Node{

	public Rocket2s(int size, Player player, World world) {
		super(size, player, world);
		textureRegion = new TextureRegion(texture, 271, 782, 135, 188);
	}

	@Override
	public void update(float dt) {
		
	}
}
