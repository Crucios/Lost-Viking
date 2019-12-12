package com.mygdx.lifeisagame.Player.SkillTree.IncreaseHitpoints.Quadshoot4s;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.lifeisagame.Player.Player;
import com.mygdx.lifeisagame.Player.SkillTree.Node;

public class SexShoot2s extends Node{

	public SexShoot2s(int size, Player player, World world) {
		super(size, player, world);
		textureRegion = new TextureRegion(texture, 0, 192, 135, 188);
	}

	@Override
	public void update(float dt) {
		
	}
}
