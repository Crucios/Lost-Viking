package com.mygdx.lifeisagame.Player.SkillTree;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.lifeisagame.Player.Player;
import com.mygdx.lifeisagame.Player.SkillTree.IncreaseHitpoints.DoubleBullet;
import com.mygdx.lifeisagame.Player.SkillTree.IncreaseHitpoints.QuadShoot2s;
import com.mygdx.lifeisagame.Player.SkillTree.IncreaseHitpoints.Doublebullet.TripleBullet;
import com.mygdx.lifeisagame.Player.SkillTree.IncreaseHitpoints.Doublebullet.TripleBulletExpanded;
import com.mygdx.lifeisagame.Player.SkillTree.IncreaseHitpoints.Quadshoot4s.OctoShoot3s;
import com.mygdx.lifeisagame.Player.SkillTree.IncreaseHitpoints.Quadshoot4s.SexShoot2s;

public class SkillTree{
	protected World world;
	protected Player player;
	protected boolean chosen;
	protected Node root;
	protected int[] positionTree;
	
	public SkillTree(World world, Player player) {
		this.world = world;
		this.player = player;
		
		//Declare root
		Node newNode = new Basic(3, this.player, this.world);
		positionTree = new int[] {0};
		add(newNode, positionTree);
		newNode=new IncreasedHitpoint(2,this.player,this.world);
		positionTree=new int[] {0};
		add(newNode, positionTree);
		newNode = new QuadShoot2s(2,this.player, this.world);
		positionTree=new int[] {0,0};
		add(newNode, positionTree);
		newNode= new DoubleBullet(2,this.player,this.world);
		positionTree = new int[] {0,1};
		add(newNode,positionTree);
		newNode= new OctoShoot3s(1,this.player,this.world);
		positionTree = new int[] {0,0,0};
		add(newNode,positionTree);
		newNode= new SexShoot2s(1,this.player,this.world);
		positionTree = new int[] {0,0,1};
		add(newNode,positionTree);
		newNode= new TripleBullet(1,this.player,this.world);
		positionTree = new int[] {0,1,0};
		add(newNode,positionTree);
		newNode= new TripleBulletExpanded(1,this.player,this.world);
		positionTree = new int[] {0,1,1};
		add(newNode,positionTree);
		//Declare Tree in here
		
	}
	
	public void add(Node newNode, int[] positionBranch) {;
		if(root==null) {
			root = newNode;
		}
		else {
			Node temp = root;
			temp.add(newNode, positionBranch, 0);
		}
	}
	
	public void update(Node node, float dt) {
		for(int i=0; i<node.getNodes().size(); i++) {
			if(node.getNodes().get(i) != null) {
				if(node.getNodes().get(i).unlocked) {
					update(node.getNodes().get(i), dt);
				}
			}
		}
		node.update(dt);
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}
}
