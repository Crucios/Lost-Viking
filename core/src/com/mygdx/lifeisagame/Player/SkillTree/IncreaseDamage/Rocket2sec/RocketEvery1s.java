package com.mygdx.lifeisagame.Player.SkillTree.IncreaseDamage.Rocket2sec;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.lifeisagame.Player.Player;
import com.mygdx.lifeisagame.Player.SkillTree.Node;

public class RocketEvery1s extends Node{

	public RocketEvery1s(int size, Player player, World world) {
		super(size, player, world);
		textureRegion = new TextureRegion(texture, 134, 782, 135, 188);
	}

	@Override
	public void update(float dt) {
		
	}
}
