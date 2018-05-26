package org.insa.algo.shortestpath;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

import org.insa.algo.ArcInspector;
import org.insa.algo.AbstractInputData.Mode;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.GraphStatistics;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;

public class Performance {

    private static Graph graph;
    private final int NBTESTS = 100;
    private long moyenneBellman = 0;
    private long moyenneDijkstra = 0;
    private long moyenneAstar = 0;


    @BeforeClass
    public static void initAll() throws IOException {
    	
        String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/toulouse.mapgr";
        
        // Create a graph reader.
        GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

        // Read the graph.
		graph = reader.read();

    }
    
    @Test
	public void performances()  throws IOException {
    	
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
        
        for (int i = 0; i < NBTESTS; ++i) {
        	Random rand = new Random(); 
        	int nodeOrigin = rand.nextInt(graph.size());
        	int nodeDestination = rand.nextInt(graph.size());
        	ShortestPathData data = new ShortestPathData(graph, graph.get(nodeOrigin),  graph.get(nodeDestination), AI);
    		BellmanFordAlgorithm bellmanAlgo = new BellmanFordAlgorithm(data);
    		DijkstraAlgorithm dijkstraAlgo = new DijkstraAlgorithm(data);
    		AStarAlgorithm astarAlgo = new AStarAlgorithm(data);
    		
    		long tempsExecutionBellman = System.currentTimeMillis();
    		ShortestPathSolution spBellman = bellmanAlgo.doRun();
    		tempsExecutionBellman = System.currentTimeMillis() - tempsExecutionBellman;
    		moyenneBellman += tempsExecutionBellman;
    		
    		long tempsExecutionDijkstra = System.currentTimeMillis();
    		ShortestPathSolution spDijkstra = dijkstraAlgo.doRun();
    		tempsExecutionDijkstra = System.currentTimeMillis() - tempsExecutionDijkstra;
    		moyenneDijkstra += tempsExecutionDijkstra;
    		
    		long tempsExecutionAstar = System.currentTimeMillis();
    		ShortestPathSolution spAstar = astarAlgo.doRun();
    		tempsExecutionAstar = System.currentTimeMillis() - tempsExecutionAstar;
    		moyenneAstar += tempsExecutionAstar;
    		
    		System.out.println("ORIGINE : " + nodeOrigin + "   DESTINATION : " + nodeDestination);
    		System.out.println("Bellman : " + tempsExecutionBellman + "ms");
    		System.out.println("Dijkstra : " + tempsExecutionDijkstra + "ms");
    		System.out.println("Astar : " + tempsExecutionAstar + "ms");
        }
        
        moyenneBellman = (long)moyenneBellman/NBTESTS;
        moyenneDijkstra = (long)moyenneDijkstra/NBTESTS;
        moyenneAstar = (long)moyenneAstar/NBTESTS;
        
        System.out.println("\nAprès ces " + NBTESTS + " tests, voici les moyennes de temps d'exécution :");
        System.out.println("Bellman : " + moyenneBellman + "ms");
        System.out.println("Dijkstra : " + moyenneDijkstra + "ms");
        System.out.println("Astar : " + moyenneAstar + "ms");
    }
	
}
