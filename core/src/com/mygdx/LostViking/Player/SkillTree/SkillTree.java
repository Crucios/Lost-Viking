package com.mygdx.LostViking.Player.SkillTree;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.LostViking.Player.Player;
import com.mygdx.LostViking.Player.SkillTree.IncreaseDamage.IncreasedDamageEnhanced;
import com.mygdx.LostViking.Player.SkillTree.IncreaseDamage.Rocket2s;
import com.mygdx.LostViking.Player.SkillTree.IncreaseDamage.IncreaseDamageEnhance.CriticalShoot;
import com.mygdx.LostViking.Player.SkillTree.IncreaseDamage.IncreaseDamageEnhance.MultiShoot;
import com.mygdx.LostViking.Player.SkillTree.IncreaseDamage.Rocket2sec.DoubleRocket3s;
import com.mygdx.LostViking.Player.SkillTree.IncreaseDamage.Rocket2sec.RocketEvery1s;
import com.mygdx.LostViking.Player.SkillTree.IncreaseHitpoints.DoubleBullet;
import com.mygdx.LostViking.Player.SkillTree.IncreaseHitpoints.QuadShoot2s;
import com.mygdx.LostViking.Player.SkillTree.IncreaseHitpoints.Doublebullet.TripleBullet;
import com.mygdx.LostViking.Player.SkillTree.IncreaseHitpoints.Doublebullet.TripleBulletExpanded;
import com.mygdx.LostViking.Player.SkillTree.IncreaseHitpoints.Quadshoot4s.OctoShoot3s;
import com.mygdx.LostViking.Player.SkillTree.IncreaseHitpoints.Quadshoot4s.SexShoot2s;
import com.mygdx.LostViking.Player.SkillTree.IncreaseSpeed.IncreasedMovementSpeed;
import com.mygdx.LostViking.Player.SkillTree.IncreaseSpeed.PiercingShoot;
import com.mygdx.LostViking.Player.SkillTree.IncreaseSpeed.IncreaseMovementSpeed.Dodge;
import com.mygdx.LostViking.Player.SkillTree.IncreaseSpeed.IncreaseMovementSpeed.IncreasedEnhancedSpeed2;
import com.mygdx.LostViking.Player.SkillTree.IncreaseSpeed.PiercingShot.IncreaseSpeedEnhanced;

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
		newNode = new IncreasedDamage(2,this.player,this.world);
		positionTree = new int[] {1};
		add(newNode,positionTree);
		newNode = new IncreasedDamageEnhanced(2,this.player,this.world);
		positionTree = new int[] {1,0};
		add(newNode,positionTree);
		newNode = new Rocket2s(2,this.player, this.world);
		positionTree = new int[] {1,1};
		add(newNode, positionTree);
		newNode = new MultiShoot(1,this.player, this.world);
		positionTree = new int[] {1,0,0};
		add(newNode, positionTree);
		newNode = new CriticalShoot(1,this.player, this.world);
		positionTree = new int[] {1,0,1};
		add(newNode, positionTree);
		newNode = new RocketEvery1s(1,this.player, this.world);
		positionTree = new int[] {1,1,1};
		add(newNode, positionTree);
		newNode = new DoubleRocket3s(1,this.player, this.world);
		positionTree = new int[] {1,1,0};
		add(newNode, positionTree);
		newNode = new IncreasedSpeed(2,this.player,this.world);
		positionTree = new int[] {2};
		add(newNode,positionTree);
		newNode = new PiercingShoot(2,this.player,this.world);
		positionTree = new int[] {2,0};
		add(newNode,positionTree);
		newNode = new IncreasedMovementSpeed(2,this.player,this.world);
		positionTree = new int[] {2,1};
		add(newNode,positionTree);
		newNode = new IncreaseSpeedEnhanced(1,this.player,this.world);
		positionTree = new int[] {2,0,0};
		add(newNode, positionTree);
		newNode=new IncreasedHitpoint(1,this.player,this.world);
		positionTree=new int[] {2,0,1};
		add(newNode,positionTree);
		newNode=new IncreasedEnhancedSpeed2(1,this.player,this.world);
		positionTree=new int[] {2,1,0};
		add(newNode,positionTree);
		newNode=new Dodge(1,this.player,this.world);
		positionTree=new int[] {2,1,1};
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

	public Node getLastSkill(Node node) {
		boolean isUnlock = false;
		Node temp = node;
		for(int i=0; i<node.getNodes().size(); i++) {
			if(node.getNodes().get(i) != null) {
				if(node.getNodes().get(i).unlocked) {
					isUnlock = true;
					temp = getLastSkill(node.getNodes().get(i)); 
				}
			}
		}
		
		if(!isUnlock)
			return node;
		else
			return temp;
	}
	
	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}
}
