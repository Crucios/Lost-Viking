package com.mygdx.lifeisagame.Player.SkillTree;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.lifeisagame.Player.Player;

public class Tree extends Sprite{
	protected World world;
	protected Player player;
	protected boolean chosen;
	protected Node root;
	protected ArrayList<Integer> positionTree;
	
	public Tree(World world, Player player) {
		this.world = world;
		this.player = player;
		positionTree = new ArrayList<Integer>();
		
		//Declare Tree in here
		
	}
	
	public void add(Node newNode, ArrayList<Integer> positionBranch, int size) {
		Node news = new Node(size);
		if(root==null) {
			root = news;
		}
		else {
			Node temp = root;
			temp.add(newNode, positionBranch, 0);
		}
	}
}
