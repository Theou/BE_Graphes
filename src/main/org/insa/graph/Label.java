package org.insa.graph;

public class Label implements Comparable<Label> {
	
	private int idNode;
	private float cost;
	private Node father;
	private boolean mark;
	
	public Label (int id) {
		this.idNode = id;
		this.mark = false;
		this.father = null;
	}
	
	public void setIdNode(int idNode) {
		this.idNode = idNode;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public void setFather(Node father) {
		this.father = father;
	}

	public void setMark(boolean mark) {
		this.mark = mark;
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

	@Override
	public int compareTo(Label autre) {
		if (this.getCost() < autre.getCost())
			return -1;
		else if (this.getCost() == autre.getCost())
			return 0;
		else 
			return 1;
	}
}
