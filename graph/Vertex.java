/* Vertex.java */
package graph;

import list.*;

public class Vertex {
	protected int degree;
	protected DList edgeList;
	protected Object vertex;
	protected DListNode me;

	/**
 	* Vertex constructer constructs our internal Vertex objects, containing an empty DList
 	* of edges upon construction.
 	**/
	public Vertex(Object ob) {
		edgeList = new DList();
		vertex = ob;
	}

	/**
 	* addEdgetoList() takes in an Edge object and adds to to the back of edgeList.
 	**/
	protected void addEdgetoList(Edge add) {
		edgeList.insertBack(add);
		degree++;
	}
}