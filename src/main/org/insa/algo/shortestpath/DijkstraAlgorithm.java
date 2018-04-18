package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        ArrayList<Node> nodes = new ArrayList<Node>();

        final int nbNodes = graph.size();
        
		Label[] labels = new Label[nbNodes];
		BinaryHeap<Label> tas = new BinaryHeap<Label>();
		
		// Initialisation des labels des noeuds
		int i;
		for (Node n : graph) {
			i = n.getId();
			if (n.equals( data.getOrigin())) {
				labels[i] = new Label(data.getOrigin().getId());
				labels[i].setCost(0);
				labels[i].setFather(null);
				labels[i].setMark(true);
			}
			else {
				labels[i] = new Label(n.getId());
				labels[i].setCost(Float.POSITIVE_INFINITY);
			}
		}
		
		tas.insert(labels[data.getOrigin().getId()]);
		
		while (!tas.isEmpty() && !labels[data.getDestination().getId()].isMark()) {
			Label l = tas.deleteMin();
			labels[l.getIdNode()].setMark(true);
			Node n = graph.get(l.getIdNode());
			for (Arc a : n) {
				Node successor = a.getDestination();
				if (labels[successor.getId()].isMark() == false) {
					labels[successor.getId()].setCost(Math.min(labels[successor.getId()].getCost(), labels[n.getId()].getCost() + a.getLength()));
					if (labels[successor.getId()].getCost() == labels[n.getId()].getCost() + a.getLength()) {
						tas.insert(labels[successor.getId()]);
						labels[successor.getId()].setFather(n);
					}	
				}
			}
		}
		nodes.add(data.getDestination());
		Node sommet = data.getDestination();
		while (!sommet.equals(data.getOrigin())) {
			nodes.add(labels[sommet.getId()].getFather());
			sommet = labels[sommet.getId()].getFather();
		}
		// Ajouter liste d'arcs pour utiliser la fonction new Path(graph, arcs)
		nodes.
		chemin = Path.createShortestPathFromNodes(graph, nodes);
		solution = new ShortestPathSolution(data, Status.OPTIMAL, chemin);
        return solution;
    }

}
