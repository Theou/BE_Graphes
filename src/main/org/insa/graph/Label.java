package org.insa.graph;

public class Label {
	
	private int idNode;
	private float cost;
	private Node father;
	private boolean mark;
	
	public Label (int cost, Node father) {
		this.cost=cost;
		this.father=father;
		this.mark = false;
	}
	
	public int getIdNode() {
		return idNode;
	}



	public float getCost() {
		return cost;
	}



	public Node getFather() {
		return father;
	}



	public boolean isMark() {
		return mark;
	}



	public void marquer() {
		this.mark = true;
	}
}
