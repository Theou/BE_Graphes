package org.insa.algo.shortestpath;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.insa.algo.ArcInspector;
import org.insa.algo.AbstractInputData.Mode;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.GraphStatistics;
import org.insa.graph.Node;
import org.insa.graph.Path;
import org.insa.graph.RoadInformation;
import org.insa.graph.RoadInformation.RoadType;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DijkstraAlgorithmTestOnGraph {
	
	 // Small graph use for tests
    private static Graph graph;
    
    private static float[][] costsBellman = new float[6][6];
    private static float[][] costsDijkstra = new float[6][6];
    
    private static ArcInspector AI;

    // List of nodes
    private static Node[] nodes;

    // List of arcs
    @SuppressWarnings("unused")
    private static Arc x1x2, x1x3, x2x4, x2x5, x2x6, x3x1, x3x2, x3x6, x5x3, x5x4, x5x6, x6x5; 

    @BeforeClass
    public static void initAll() throws IOException {

        RoadInformation speed = new RoadInformation(RoadType.MOTORWAY, null, true, 36, "");

        
        AI = new ArcInspector() {
			
            @Override
            public boolean isAllowed(Arc arc) {
                return true;
            }

            @Override
            public double getCost(Arc arc) {
                return arc.getLength();
            }

            @Override
            public int getMaximumSpeed() {
                return GraphStatistics.NO_MAXIMUM_SPEED;
            }

            @Override
            public Mode getMode() {
                return Mode.LENGTH;
            }

            @Override
            public String toString() {
                return "Shortest path, all roads allowed";
            }   
		};
		
        // Create nodes
        nodes = new Node[6];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, null);
        }

        // Add arcs...
        x1x2 = Node.linkNodes(nodes[0], nodes[1], 7, speed, null);
        x1x3 = Node.linkNodes(nodes[0], nodes[2], 8, speed, null);
        x2x4 = Node.linkNodes(nodes[1], nodes[3], 4, speed, null);
        x2x5 = Node.linkNodes(nodes[1], nodes[4], 1, speed, null);
        x2x6 = Node.linkNodes(nodes[1], nodes[5], 5, speed, null);
        x3x1 = Node.linkNodes(nodes[2], nodes[0], 7, speed, null);
        x3x2 = Node.linkNodes(nodes[2], nodes[1], 2, speed, null);
        x3x6 = Node.linkNodes(nodes[2], nodes[5], 2, speed, null);
        x5x3 = Node.linkNodes(nodes[4], nodes[2], 2, speed, null);
        x5x4 = Node.linkNodes(nodes[4], nodes[3], 2, speed, null);
        x5x6 = Node.linkNodes(nodes[4], nodes[5], 3, speed, null);
        x6x5 = Node.linkNodes(nodes[5], nodes[4], 3, speed, null);
        
        graph = new Graph("ID", "", Arrays.asList(nodes), null);
        
        for (int i = 0; i < nodes.length; ++i) { 
        	for (int j = 0; j < nodes.length; ++j) {
        		ShortestPathData data = new ShortestPathData(graph, nodes[i], nodes[j], AI);
        		BellmanFordAlgorithm bellmanAlgo = new BellmanFordAlgorithm(data);
        		DijkstraAlgorithm dijkstraAlgo = new DijkstraAlgorithm(data);
        		ShortestPathSolution spBellman = bellmanAlgo.doRun(), spDijkstra = dijkstraAlgo.doRun();
        		try {
        			Path pathBellman = spBellman.getPath(); 
        			costsBellman[i][j] = pathBellman.getLength();
        		} catch (Exception e) {
        			costsBellman[i][j] = -1;
        		}
        		try {
	        		Path pathDijkstra = spDijkstra.getPath();	
	        		costsDijkstra[i][j] = pathDijkstra.getLength();
        		} catch (Exception e) {
        			costsDijkstra[i][j] = -1;
        		}
        	}
        		
        	
        }
        
    }
    
    @SuppressWarnings("deprecation")
	@Test
    public void verifyLengths() {
    	for (int i = 0; i < 6; ++i) {
    		for (int j = 0; j < 6; ++j) {
    			Assert.assertEquals(costsBellman[i][j], costsDijkstra[i][j], 0);
    		}
    	}
    	
    }

}