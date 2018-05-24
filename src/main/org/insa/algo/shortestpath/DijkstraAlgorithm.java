package org.insa.algo.shortestpath;

import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;
import java.util.Collections;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Label;
import org.insa.graph.Node;
import org.insa.graph.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

	@Override
    protected ShortestPathSolution doRun() {
		
        ShortestPathData data = getInputData();
        Graph graph = data.getGraph();
        ShortestPathSolution solution = null;
        Path chemin;
        ArrayList<Arc> arcs = new ArrayList<Arc>();
        
        if (data.getOrigin().equals(data.getDestination())) {
        	solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        	return solution;
        }

        final int nbNodes = graph.size();
        
		Label[] labels = new Label[nbNodes];
		BinaryHeap<Label> tas = new BinaryHeap<Label>();
		
		// Initialisation des labels des noeuds
		int i;
		for (Node n : graph) {
			i = n.getId();
			if (n.equals(data.getOrigin())) {
				labels[i] = new Label(data.getOrigin().getId());
				labels[i].setCost(0);
				labels[i].setFather(null);
				labels[i].setMark(true);
				tas.insert(labels[i]);
			}
			else {
				labels[i] = new Label(n.getId());
				labels[i].setCost(Float.POSITIVE_INFINITY);
			}
			
		}
		
		notifyOriginProcessed(data.getOrigin());
		
		while (!tas.isEmpty() && !labels[data.getDestination().getId()].isMark()) {
			Label l = tas.deleteMin();
			labels[l.getIdNode()].setMark(true);
			Node n = graph.get(l.getIdNode());
			notifyNodeMarked(n);
			for (Arc a : n) {
				if (!data.isAllowed(a)) {
					continue;
				}
				Node successor = a.getDestination();
				notifyNodeReached(successor);
				if (labels[successor.getId()].isMark() == false) {
					// double cout = Math.min(labels[successor.getId()].getCost(), labels[n.getId()].getCost() + data.getCost(a));
					if (labels[successor.getId()].getCost() > labels[n.getId()].getCost() + data.getCost(a)) {
						if (labels[successor.getId()].getCost() != Float.POSITIVE_INFINITY)
							tas.remove(labels[successor.getId()]);
						labels[successor.getId()].setCost(labels[n.getId()].getCost() + data.getCost(a));
						tas.insert(labels[successor.getId()]);
						labels[successor.getId()].setFather(n);
						labels[successor.getId()].setArcLeast(a);
					}	
				}
			}
		}
		
		if (labels[data.getDestination().getId()].isMark()) {
			notifyDestinationReached(data.getDestination());
			Node sommet = data.getDestination();
			while (!sommet.equals(data.getOrigin())) {
				
				arcs.add(labels[sommet.getId()].getArcLeast());
				Node parent = labels[sommet.getId()].getFather();
				sommet = parent;
				
			}
			Collections.reverse(arcs);
			chemin = new Path(graph, arcs);
			solution = new ShortestPathSolution(data, Status.OPTIMAL, chemin);
		}
		else {
			solution = new ShortestPathSolution(data, Status.INFEASIBLE);
		}
		
        return solution;
    }

}
