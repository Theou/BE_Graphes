package org.insa.graph;


public class Label implements Comparable<Label> {
	
	private int idNode;
	private double cost;
	private Node father;
	private boolean mark;
	private Arc arcLeast;
	
	public Label (int id) {
		this.idNode = id;
		this.mark = false;
		this.father = null;
		this.arcLeast = null;
	}
	
	public Arc getArcLeast() {
		return arcLeast;
	}

	public void setArcLeast(Arc arcLeast) {
		this.arcLeast = arcLeast;
	}

	public void setIdNode(int idNode) {
		this.idNode = idNode;
	}

	public void setCost(double cost) {
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

	public double getCost() {
		return cost;
	}
	
	public double getTotalCost() {
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
		if (this.getTotalCost() < autre.getTotalCost())
			return -1;
		else if (this.getTotalCost() == autre.getTotalCost())
			return 0;
		else 
			return 1;
	}
}
