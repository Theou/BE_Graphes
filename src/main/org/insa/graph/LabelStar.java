package org.insa.graph;

import org.insa.algo.AbstractInputData.Mode;
import org.insa.algo.shortestpath.ShortestPathData;

public class LabelStar extends Label implements Comparable<Label> {

	private double costVol;
	private static ShortestPathData data;
	
	@Override
	public double getTotalCost() {
		if (this.data.getMode() == Mode.LENGTH)
			return costVol + super.getCost();
		else
			return (costVol + super.getCost())/(data.getGraph().getGraphInformation().getMaximumSpeed()/3.6);
	}

	public void setCostVol(double costVol) {
		this.costVol = costVol;
	}
	
	public double getCostVol() {
		return costVol;
	}

	public LabelStar(int id, ShortestPathData data) {
		super(id);
		this.costVol = 0;
		this.data = data;
	}

}
