package graphalg;

import list.*;

public class KEdge {
	protected Object origin;
	protected Object destination;
	protected int weight;
		
	/**
 	* KEdge constructer constructs a KEdge objects that stores a vertex origin, 
 	* vertex destination, and weight.
 	**/
	public KEdge(Object o, Object d, int w) {
		origin = o;
		destination = d;
		weight = w;
	}

	/**
 	* origin() returns the vertex object that represents the origin of the KEdge.
 	**/

	public Object origin() {
		return origin;
	}

	/**
 	* destination() returns the vertex object that represents the destination of the KEdge.
 	**/

	public Object destination() {
		return destination;
	}

	/**
 	* weight() returns the int that is the weight of this KEdge.
 	**/

	public int weight() {
		return weight;
	}

}