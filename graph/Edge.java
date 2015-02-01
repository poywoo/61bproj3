package graph;

import list.*;

public class Edge {
	protected VertexPair ends;
	protected int weight;
	protected boolean selfedge;
	protected Edge partner;
	protected DListNode me;
	
	/**
 	* This Edge constructer constructs non-self-edge Edge objects.
 	**/

	public Edge(Object o, Object d, int w, Edge p) {
		ends = new VertexPair(o,d);
		weight = w;
		partner = p;
	}

	/**
 	* This Edge constructer constructs self-edge Edge objects.
 	* Unlike the constructer above, this constructer sets the partner to null.
 	**/
	public Edge(Object o, Object d, int w) {
		ends = new VertexPair(o,d);
		weight = w;
		selfedge = true;
		partner = null;
	}

	/**
 	* origin() returns the vertex object that represents the origin of the Edge.
 	**/

	public Object origin() {
		return ends.object1;
	}

	/**
 	* destination() returns the vertex object that represents the destination of the Edge.
 	**/

	public Object destination() {
		return ends.object2;
	}

	/**
 	* weight() returns the int that is the weight of this Edge.
 	**/

	public int weight() {
		return weight;
	}

	/**
 	* isSelfEdge() returns a boolean stating whether this Edge is a self-edge.
 	* returns true if this Edge is a self-edge, false otherwise.
 	**/
	public boolean isSelfEdge() {
		return selfedge;
	}

}