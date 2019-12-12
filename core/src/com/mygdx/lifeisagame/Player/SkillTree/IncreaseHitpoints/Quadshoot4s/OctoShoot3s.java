package com.mygdx.lifeisagame.Player.SkillTree.IncreaseHitpoints.Quadshoot4s;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.lifeisagame.Player.Player;
import com.mygdx.lifeisagame.Player.SkillTree.Node;

public class OctoShoot3s extends Node{

	public OctoShoot3s(int size, Player player, World world) {
		super(size, player, world);
		textureRegion = new TextureRegion(texture, 134, 192, 135, 188);
	}
	
	@Override
	public void update(float dt) {
		
	}

}
