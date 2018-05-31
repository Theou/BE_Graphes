package org.insa.algo.shortestpath;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.insa.algo.ArcInspector;
import org.insa.algo.AbstractInputData.Mode;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.GraphStatistics;
import org.insa.graph.Path;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;

public class LivraisonColis {

	private static Graph graph;


    @BeforeClass
    public static void initAll() throws IOException {
    	
        String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/bordeaux.mapgr";
        
        // Create a graph reader.
        GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

        // Read the graph.
		graph = reader.read();
	
    }
    
    @Test
    public void trouverPointRDV() {
    	
    	float[] objectifs = new float[graph.size()];
    	
    	ArcInspector AI = new ArcInspector() {

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
        int O1 = 1123, O2 = 1206, D1 = 619, D2 = 1270;
        
        objectifs[O1] = -1;
        objectifs[D1] = -1;
        objectifs[O2] = -1;
        objectifs[D2] = -1;
        
        for (int i = 0; i < graph.size(); ++i) {
        	if (i != O1 && i != O2 && i != D1 && i != D2) {
        		boolean noPath = false;
        		ShortestPathData O1toRDV = new ShortestPathData(graph, graph.get(O1), graph.get(i), AI);
                ShortestPathData RDVtoD1 = new ShortestPathData(graph, graph.get(i), graph.get(D1), AI);
                ShortestPathData O2toRDV = new ShortestPathData(graph, graph.get(O2), graph.get(i), AI);
                ShortestPathData RDVtoD2 = new ShortestPathData(graph, graph.get(i), graph.get(D2), AI);
                
                DijkstraAlgorithm dijkstraO1toRDV = new DijkstraAlgorithm(O1toRDV);
                DijkstraAlgorithm dijkstraRDVtoD1 = new DijkstraAlgorithm(RDVtoD1);
                DijkstraAlgorithm dijkstraO2toRDV = new DijkstraAlgorithm(O2toRDV);
                DijkstraAlgorithm dijkstraRDVtoD2 = new DijkstraAlgorithm(RDVtoD2);
                
                float costO1toRDV, costRDVtoD1, costO2toRDV, costRDVtoD2;
                
                ShortestPathSolution spO1toRDV = dijkstraO1toRDV.doRun();
                ShortestPathSolution spRDVtoD1 = dijkstraRDVtoD1.doRun();
                ShortestPathSolution spO2toRDV = dijkstraO2toRDV.doRun();
                ShortestPathSolution spRDVtoD2 = dijkstraRDVtoD2.doRun();
        	
	    		try {
	        		Path pathO1toRDV = spO1toRDV.getPath();	
	        		costO1toRDV = pathO1toRDV.getLength();
	    		} catch (Exception e) {
	    			costO1toRDV = -1;
	    			noPath = true;
	    		}
	    		
	    		try {
	        		Path pathRDVtoD1 = spRDVtoD1.getPath();	
	        		costRDVtoD1 = pathRDVtoD1.getLength();
	    		} catch (Exception e) {
	    			costRDVtoD1 = -1;
	    			noPath = true;
	    		}
	    		
	    		try {
	        		Path pathO2toRDV = spO2toRDV.getPath();	
	        		costO2toRDV = pathO2toRDV.getLength();
	    		} catch (Exception e) {
	    			costO2toRDV = -1;
	    			noPath = true;
	    		}
	    		
	    		try {
	        		Path pathRDVtoD2 = spRDVtoD2.getPath();	
	        		costRDVtoD2 = pathRDVtoD2.getLength();
	    		} catch (Exception e) {
	    			costRDVtoD2 = -1;
	    			noPath = true;
	    		}
	    		if (noPath == true) {
	    			objectifs[i] = -1;
	    		}
	    		else {
	    			objectifs[i] = costO1toRDV + costRDVtoD1 + costO2toRDV + costRDVtoD2;
	    		}
        	}
        }
        float minimum = Integer.MAX_VALUE;
        int indexMin = 0;
        for (int i = 0; i < objectifs.length; ++i) {
        	if (objectifs[i] < minimum && objectifs[i] != -1) {
        		minimum = objectifs[i];
        		indexMin = i;
        	}
        }
        System.out.println("Le point de rendez-vous optimal est le noeud " + indexMin);
    }
}
