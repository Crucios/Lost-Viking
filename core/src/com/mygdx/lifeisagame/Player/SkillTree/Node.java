package com.mygdx.lifeisagame.Player.SkillTree;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.lifeisagame.Player.Player;

public class Node extends Sprite{
	protected ArrayList<Node> nodes;
	protected boolean unlocked;
	protected Player player;
	protected boolean updated;
	protected Texture texture;
	protected TextureRegion textureRegion;
	
	public Node(int size, Player player, World world) {
		this.player = player;
		unlocked = false;
		updated = false;
		texture = new Texture(Gdx.files.internal("Player/HUD/semua.png"));
		nodes = new ArrayList<Node>();
		for(int i=0;i<size;i++)
			nodes.add(null);
	}
	
	public void add(Node newNode, int[] nNode, int index) {
		if(nodes.get(nNode[index]) == null)
			nodes.set(nNode[index], newNode);
		else
			nodes.get(nNode[index]).add(newNode, nNode, index++);
	}

	public void update(float dt) {
		
	}
	
	//Public Access Method
	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}

	public boolean isUnlocked() {
		return unlocked;
	}

	public void setUnlocked(boolean unlocked) {
		this.unlocked = unlocked;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}
}
