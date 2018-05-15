package org.insa.graph;

public class LabelStar extends Label {

	private double costVol;
	
	@Override
	public double getCost() {
		return super.getCost() + costVol;
	}

	public void setCostVol(double costVol) {
		this.costVol = costVol;
	}
	
	public double getCostVol() {
		return costVol;
	}

	public LabelStar(int id) {
		super(id);
		this.costVol = 0;
	}

}
