/* WUGraph.java */

package graph;
import list.*;
import dict.*;

/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {
  private HashTableChained vertices;
  private HashTableChained edgesTable;
  private DList vertList;
  private int numEdge;
  private int numVert;

  /**
   * WUGraph() constructs a graph having no vertices or edges.
   *
   * Running time:  O(1).
   */
  public WUGraph() {
    vertices = new HashTableChained();
    edgesTable = new HashTableChained();
    vertList = new DList();
  }
  /**
   * vertexCount() returns the number of vertices in the graph.
   *
   * Running time:  O(1).
   */
  public int vertexCount() {
    return numVert;
  }

  /**
   * edgeCount() returns the total number of edges in the graph.
   *
   * Running time:  O(1).
   */
  public int edgeCount() {
    return numEdge;
  }

  /**
   * getVertices() returns an array containing all the objects that serve
   * as vertices of the graph.  The array's length is exactly equal to the
   * number of vertices.  If the graph has no vertices, the array has length
   * zero.
   *
   * (NOTE:  Do not return any internal data structure you use to represent
   * vertices!  Return only the same objects that were provided by the
   * calling application in calls to addVertex().)
   *
   * Running time:  O(|V|).
   */
  public Object[] getVertices() {
    Object[] vertArr = new Object[numVert];
    int x = 0;
    DListNode tracker = (DListNode) vertList.front();
    try {
      while (tracker.isValidNode()) {
        vertArr[x] = ( (Vertex) tracker.item()).vertex;
        tracker = (DListNode) tracker.next();
        x++;
      }
    } catch (InvalidNodeException e) {
      System.out.println("InvalidNodeException in getVertices() from edgeList");
    }
    return vertArr;
  }

  /**
   * addVertex() adds a vertex (with no incident edges) to the graph.
   * The vertex's "name" is the object provided as the parameter "vertex".
   * If this object is already a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(1).
   */
  public void addVertex(Object vertex) {
    if(isVertex(vertex)) {
      return;
    } else {
      Vertex v = new Vertex(vertex);
      vertList.insertBack(v);
      v.me = (DListNode) vertList.back();
      vertices.insert(vertex, v);
      numVert++;
    }
  }

  /**
   * removeVertex() removes a vertex from the graph.  All edges incident on the
   * deleted vertex are removed as well.  If the parameter "vertex" does not
   * represent a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public void removeVertex(Object vertex) {
    if (isVertex(vertex) == false) {
      return;
    } else {
      Vertex r = VertFromTable(vertex);
      try {
        DListNode tracker = (DListNode) r.edgeList.front();
        while (tracker.isValidNode()) {
          Edge ed = (Edge) tracker.item();
          Object v = ed.origin();
          Object d = ed.destination();
          tracker = (DListNode) tracker.next();
          removeEdge(v,d);
        }
        r.me.remove();
      } catch (InvalidNodeException e) {
      System.err.println("InvalidNodeException in removeVertex() from edgeList"); 
      }
      numVert--;
      vertices.remove(vertex);
    }
  }

  /**
   * isVertex() returns true if the parameter "vertex" represents a vertex of
   * the graph.
   *
   * Running time:  O(1).
   */
  public boolean isVertex(Object vertex) {
    if (vertices.find(vertex) == null) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * degree() returns the degree of a vertex.  Self-edges add only one to the
   * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
   * of the graph, zero is returned.
   *
   * Running time:  O(1).
   */
  public int degree(Object vertex) {
    int deg = 0;
    Vertex r = VertFromTable(vertex);
    if (r != null) {
      deg = r.degree;
    }
    return deg;
  }

  /**
   * getNeighbors() returns a new Neighbors object referencing two arrays.  The
   * Neighbors.neighborList array contains each object that is connected to the
   * input object by an edge.  The Neighbors.weightList array contains the
   * weights of the corresponding edges.  The length of both arrays is equal to
   * the number of edges incident on the input vertex.  If the vertex has
   * degree zero, or if the parameter "vertex" does not represent a vertex of
   * the graph, null is returned (instead of a Neighbors object).
   *
   * The returned Neighbors object, and the two arrays, are both newly created.
   * No previously existing Neighbors object or array is changed.
   *
   * (NOTE:  In the neighborList array, do not return any internal data
   * structure you use to represent vertices!  Return only the same objects
   * that were provided by the calling application in calls to addVertex().)
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public Neighbors getNeighbors(Object vertex) {
    Vertex vx = VertFromTable(vertex);
    if (isVertex(vertex) == false || vx.degree == 0) {
      return null;
    }
    Neighbors toReturn = new Neighbors();
    toReturn.neighborList = new Object[vx.degree];
    toReturn.weightList = new int[vx.degree];
    int x = 0;
    Edge insert;
    try {
      DListNode tracker = (DListNode) vx.edgeList.front();
      while (tracker.isValidNode()) {
        insert = (Edge) tracker.item();
        toReturn.neighborList[x] = insert.destination();
        toReturn.weightList[x] = insert.weight();
        tracker = (DListNode) tracker.next();
        x++;
      }
    } catch (InvalidNodeException e) {
      System.out.println("InvalidNodeException in getNeighbors()");
    }
    return toReturn;
  }

  /**
   * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
   * u and v does not represent a vertex of the graph, the graph is unchanged.
   * The edge is assigned a weight of "weight".  If the graph already contains
   * edge (u, v), the weight is updated to reflect the new value.  Self-edges
   * (where u == v) are allowed.
   *
   * Running time:  O(1).
   */
  public void addEdge(Object u, Object v, int weight) {
    if (isVertex(u) && isVertex(v)) {
      VertexPair search = new VertexPair(u,v);
      Edge f = EdgeFromTable(search);
      Vertex or;
      Vertex des;
      Edge edge1;
      Edge edge2;
      if (f != null) {
        f.weight = weight;
      } else if (u.equals(v)) {
        or = VertFromTable(u);
        edge1 = new Edge(u,v, weight);
        or.addEdgetoList(edge1);
        edge1.me = (DListNode) or.edgeList.back();
        edgesTable.insert(search, edge1);
        numEdge++;
      } else {
        or = VertFromTable(u);
        des = VertFromTable(v);
        edge1 = new Edge(u,v, weight, null);
        edge2 = new Edge(v,u, weight, edge1);
        edge1.partner = edge2;
        or.addEdgetoList(edge1);
        des.addEdgetoList(edge2);
        edge1.me = (DListNode) or.edgeList.back();
        edge2.me = (DListNode) des.edgeList.back();
        edgesTable.insert(search, edge1);
        numEdge++;
      }
    }
  }

  /**
   * removeEdge() removes an edge (u, v) from the graph.  If either of the
   * parameters u and v does not represent a vertex of the graph, the graph
   * is unchanged.  If (u, v) is not an edge of the graph, the graph is
   * unchanged.
   *
   * Running time:  O(1).
   */
  public void removeEdge(Object u, Object v) {
    if (isVertex(u) && isVertex(v)) {
      Vertex or = VertFromTable(u);
      Vertex des = VertFromTable(v);
      Edge search = EdgeFromTable(new VertexPair(u,v));
      if (search != null) {
        try {
          if (search.isSelfEdge()) {
            or.degree--;
            search.me.remove();
          } else {
            or.degree--;
            des.degree--;
            search.partner.me.remove();
            search.me.remove();
          }
          numEdge--;
          edgesTable.remove(new VertexPair(u,v));
        } catch (InvalidNodeException e) {
          System.out.println("Remove node not in edgeList");
        }
      }
    }
  }

  /**
   * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
   * if (u, v) is not an edge (including the case where either of the
   * parameters u and v does not represent a vertex of the graph).
   *
   * Running time:  O(1).
   */
  public boolean isEdge(Object u, Object v) {
    if (!isVertex(u) || !isVertex(v)) {
      return false;
    } else if (edgesTable.size() == 0) {
      return false;
    } else {
      Edge ed = EdgeFromTable(new VertexPair(u,v));
      if (ed != null) {
        return true;
      } else {
        return false;
      }
    }
  }

  /**
   * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
   * an edge (including the case where either of the parameters u and v does
   * not represent a vertex of the graph).
   *
   * (NOTE:  A well-behaved application should try to avoid calling this
   * method for an edge that is not in the graph, and should certainly not
   * treat the result as if it actually represents an edge with weight zero.
   * However, some sort of default response is necessary for missing edges,
   * so we return zero.  An exception would be more appropriate, but also more
   * annoying.)
   *
   * Running time:  O(1).
   */
  public int weight(Object u, Object v) {
    int w = 0; 
    Edge e = EdgeFromTable(new VertexPair(u,v));
    if (e != null) {
      w = e.weight();
    }
    return w;
  }


  /**
   * VertFromTable() is a helper function to get the internal Vertex object from the hashtable. 
   * This Vertex object is the value we mapped to the vertex. 
   **/
  private Vertex VertFromTable(Object v) {
    Entry e = vertices.find(v);
    if (e == null) {
      return null;
    } else {
      Vertex v1 = (Vertex) e.value();
      return v1;
    }
  }

  /**
   * EdgeFromTable() is a helper function to get the internal Edge object from the hashtable.
   * This Edge object is the value we mapped to the Vertexpair.
   **/
  private Edge EdgeFromTable(VertexPair v) {
    if (!isVertex(v.object1) || !isVertex(v.object2)) {
      return null;
    }
    Entry e = edgesTable.find(v);
    if (e == null) {
      return null;
    } else {
      Edge n = (Edge) e.value();
      return n;
    }
  }

}
