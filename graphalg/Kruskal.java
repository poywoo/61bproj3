/* Kruskal.java */

package graphalg;

import graph.*;
import set.*;
import list.*;
import sort.*;
import dict.*;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

  /**
   * minSpanTree() returns a WUGraph that represents the minimum spanning tree
   * of the WUGraph g.  The original WUGraph g is NOT changed.
   *
   * @param g The weighted, undirected graph whose MST we want to compute.
   * @return A newly constructed WUGraph representing the MST of g.
   */
  public static WUGraph minSpanTree(WUGraph g) {
  	WUGraph T = new WUGraph();
  	// create a new graph with same vertices as G
  	Object[] vertices = g.getVertices();
  	for (int start = 0; start < vertices.length ; start ++) {
  		T.addVertex(vertices[start]);
  	} 
  	LinkedQueue e = linkEdges(g);
  	ListSorts.quickSort(e);
  	makeTree(e, T);
  	return T;
  }

  /**
  * linkEdge() is a helper function that gets all the vertices and Neighbors of WUGraph g. 
  * From the vertices and Neighbors, it creates a KEdge object and addes it to the LinkedQueue.
  * @param g is the WUGraph that has the original information.
  * @return LinkedQueue is a LinkedQueue containing all the edges from the orginal WUGraph.
  **/
  private static LinkedQueue linkEdges(WUGraph g) {
    Object[] vertices = g.getVertices();
    LinkedQueue edgeList = new LinkedQueue();
    for (int start = 0; start < vertices.length ; start ++) {
      Neighbors nList = g.getNeighbors(vertices[start]);
      if (nList != null) {
        for (int x = 0; x < nList.neighborList.length; x++) {
          edgeList.enqueue(new KEdge(vertices[start], nList.neighborList[x], nList.weightList[x]));
        }
      }
    }
    return edgeList;
  }


  /**
  * makeTree() is a helper funtion that makes a minimum spanning from a sorted LinkQueue containing all
  * the edges it must add in to WUGraph graph. 
  * @param edges is a sorted LinkQueue of KEdges
  * @param graph is the graph we will be adding the edges to. 
  **/
  private static void makeTree(LinkedQueue edges, WUGraph graph) {
  	Object[] vertices = graph.getVertices();
  	DisjointSets s = new DisjointSets(vertices.length);
  	
  	HashTableChained mapVertex = new HashTableChained();

  	for (int i = 0; i < vertices.length; i++) {
		  mapVertex.insert(vertices[i], i);	
	   }

  	while (!edges.isEmpty()) {
  		try {
  			KEdge tracker = (KEdge) edges.dequeue();
  			int orig = (Integer)(mapVertex.find(tracker.origin()).value());
  			int dest = (Integer)(mapVertex.find(tracker.destination()).value());
  			int w = tracker.weight();
  			int root1 = s.find(orig);
  			int root2 = s.find(dest);
  			if (root1 != root2) {
  				graph.addEdge(tracker.origin(), tracker.destination(), w);
  				s.union(root1,root2);
  			}
  		} catch (QueueEmptyException e) {
  			System.out.println("Error: Queue empty in makeTree()");
  		}
  	}
  }

}
