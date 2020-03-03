import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Filename: GraphImpl.java Project: p4 Course: cs400 Authors: Due Date:
 * 
 * T is the label of a vertex, and List<T> is a list of adjacent vertices for
 * that vertex.
 *
 * Additional credits:
 *
 * Bugs or other notes:
 *
 * @param <T> type of a vertex
 */
public class GraphImpl<T> implements GraphADT<T> {

	
	/**
	 * Store the vertices and the vertice's adjacent vertices
	 */
	private Map<T, List<T>> verticesMap;

	private Set<T> s;
	
	
	
	/**
	 * Construct and initialize and empty Graph
	 */
	public GraphImpl() {
		verticesMap = new HashMap<T, List<T>>();
		s = new HashSet<T>();
	}

	public void addVertex(T vertex) {
		ArrayList<T> a;
		verticesMap.put(vertex, new ArrayList<T>());
		
		
	}

	public void removeVertex(T vertex) {
		
	}

	public void addEdge(T vertex1, T vertex2) {
		// TODO: implement this method
	}

	public void removeEdge(T vertex1, T vertex2) {
		// TODO: implement this method
	}

	public Set<T> getAllVertices() {
		// TODO: implement this method
		return null;
	}

	public List<T> getAdjacentVerticesOf(T vertex) {
		// TODO: implement this method
		return null;
	}

	public boolean hasVertex(T vertex) {
		// TODO: implement this method
		return false;
	}

	public int order() {
		// TODO: implement this method
		return 0;
	}

	public int size() {
		return verticesMap.size();
	}

	/**
	 * Prints the graph for the reference DO NOT EDIT THIS FUNCTION DO ENSURE THAT
	 * YOUR verticesMap is being used to represent the vertices and edges of this
	 * graph.
	 */
	public void printGraph() {

		for (T vertex : verticesMap.keySet()) {
			if (verticesMap.get(vertex).size() != 0) {
				for (T edges : verticesMap.get(vertex)) {
					System.out.println(vertex + " -> " + edges + " ");
				}
			} else {
				System.out.println(vertex + " -> " + " ");
			}
		}
	}
}
