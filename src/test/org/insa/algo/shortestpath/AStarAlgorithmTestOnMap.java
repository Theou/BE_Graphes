package org.insa.algo.shortestpath;

import static org.junit.Assert.assertEquals;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.EnumSet;

import org.insa.algo.*;
import org.insa.algo.AbstractInputData.Mode;
import org.insa.graph.*;
import org.insa.graph.AccessRestrictions.AccessMode;
import org.insa.graph.AccessRestrictions.AccessRestriction;
import org.insa.graph.io.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class AStarAlgorithmTestOnMap {

    private static Graph graph;


    @BeforeClass
    public static void initAll() throws IOException {
    	
        String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/toulouse.mapgr";
        
        // Create a graph reader.
        GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

        // Read the graph.
		graph = reader.read();

    }
    
    // Vérifie que la longueur des chemins trouvés par Astar et Dijkstra sont égales en ShortestPath avec toutes les routes acceptées
    @SuppressWarnings("deprecation")
	@Test
	public void verifyShortestPathAllRoadsAllowed()  throws IOException {

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
        
        float costDijkstra, costAstar;
        ShortestPathData data = new ShortestPathData(graph, graph.get(619),  graph.get(6549), AI);
		DijkstraAlgorithm dijkstraAlgo = new DijkstraAlgorithm(data);
		AStarAlgorithm astarAlgo = new AStarAlgorithm(data);
		ShortestPathSolution spDijkstra = dijkstraAlgo.doRun(), spAstar = astarAlgo.doRun();
		try {
    		Path pathDijkstra = spDijkstra.getPath();	
    		costDijkstra = pathDijkstra.getLength();
		} catch (Exception e) {
			costDijkstra = -1;
		}
		try {
    		Path pathAstar = spAstar.getPath();	
    		costAstar = pathAstar.getLength();
		} catch (Exception e) {
			costAstar = -1;
		}
    	
		assertEquals(costAstar, costDijkstra, 0);
	}
    
    
    // Vérifie qu'aucun chemin n'est trouvé lorsque l'origine est égale à la destination
	@SuppressWarnings("deprecation")
	@Test
	public void verifyNoPathWhenOriginEqualsDestination()  throws IOException {
		
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

        float costDijkstra, costAstar;
        ShortestPathData data = new ShortestPathData(graph, graph.get(619),  graph.get(6549), AI);
		DijkstraAlgorithm dijkstraAlgo = new DijkstraAlgorithm(data);
		AStarAlgorithm astarAlgo = new AStarAlgorithm(data);
		ShortestPathSolution spDijkstra = dijkstraAlgo.doRun(), spAstar = astarAlgo.doRun();
		try {
    		Path pathDijkstra = spDijkstra.getPath();	
    		costDijkstra = pathDijkstra.getLength();
		} catch (Exception e) {
			costDijkstra = -1;
		}
		try {
    		Path pathAstar = spAstar.getPath();	
    		costAstar = pathAstar.getLength();
		} catch (Exception e) {
			costAstar = -1;
		}
    	
		assertEquals(costAstar, costDijkstra, 0);
	}
	
	
	
	// Vérifie qu'aucun chemin n'est trouvé quand il n'en existe effectivement aucun
	@SuppressWarnings("deprecation")
	@Test
	public void verifyWhenNoPathExisting()  throws IOException {
		
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

        float costDijkstra, costAstar;
        ShortestPathData data = new ShortestPathData(graph, graph.get(619),  graph.get(6549), AI);
		DijkstraAlgorithm dijkstraAlgo = new DijkstraAlgorithm(data);
		AStarAlgorithm astarAlgo = new AStarAlgorithm(data);
		ShortestPathSolution spDijkstra = dijkstraAlgo.doRun(), spAstar = astarAlgo.doRun();
		try {
    		Path pathDijkstra = spDijkstra.getPath();	
    		costDijkstra = pathDijkstra.getLength();
		} catch (Exception e) {
			costDijkstra = -1;
		}
		try {
    		Path pathAstar = spAstar.getPath();	
    		costAstar = pathAstar.getLength();
		} catch (Exception e) {
			costAstar = -1;
		}
    	
		assertEquals(costAstar, costDijkstra, 0);
	}
	

	// Vérifie que la longueur des chemins trouvés par Astar et Dijkstra sont égales en ShortestPath avec seulement les routes de voitures acceptées
	@SuppressWarnings("deprecation")
	@Test
	public void verifyShortestPathWhenOnlyRoadCars()  throws IOException {
		
		ArcInspector AI = new ArcInspector() {

			@Override
            public boolean isAllowed(Arc arc) {
                return arc.getRoadInformation().getAccessRestrictions()
                        .isAllowedForAny(AccessMode.MOTORCAR, EnumSet.complementOf(EnumSet
                                .of(AccessRestriction.FORBIDDEN, AccessRestriction.PRIVATE)));
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
            	return "Shortest path, only roads open for cars";
            }
        };

        float costDijkstra, costAstar;
        ShortestPathData data = new ShortestPathData(graph, graph.get(619),  graph.get(6549), AI);
		DijkstraAlgorithm dijkstraAlgo = new DijkstraAlgorithm(data);
		AStarAlgorithm astarAlgo = new AStarAlgorithm(data);
		ShortestPathSolution spDijkstra = dijkstraAlgo.doRun(), spAstar = astarAlgo.doRun();
		try {
    		Path pathDijkstra = spDijkstra.getPath();	
    		costDijkstra = pathDijkstra.getLength();
		} catch (Exception e) {
			costDijkstra = -1;
		}
		try {
    		Path pathAstar = spAstar.getPath();	
    		costAstar = pathAstar.getLength();
		} catch (Exception e) {
			costAstar = -1;
		}
    	
		assertEquals(costAstar, costDijkstra, 0);
	}
}