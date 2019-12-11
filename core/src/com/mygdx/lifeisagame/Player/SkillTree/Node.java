package com.mygdx.lifeisagame.Player.SkillTree;

import java.util.ArrayList;

public class Node {
	protected ArrayList<Node> nodes;
	
	public Node(int size) {
		nodes = new ArrayList<Node>();
		for(int i=0;i<size;i++)
			nodes.add(null);
	}
	
	public void add(Node newNode, ArrayList<Integer> nNode, int index) {
		if(nodes.get(nNode.get(index)) == null)
			nodes.set(nNode.get(index), newNode);
		else
			nodes.get(nNode.get(index)).add(newNode, nNode, index++);
	}

	//Public Access Method
	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}
}
