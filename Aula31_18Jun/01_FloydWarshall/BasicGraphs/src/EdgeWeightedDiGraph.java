import java.util.HashSet;
import java.util.Set;

public class EdgeWeightedDiGraph {
	private static final String NEWLINE = System.getProperty("line.separator");

	private final int V;
	private int E;
	private Bag<DiEdge>[] adj;

	/**
	 * Initializes an empty edge-weighted graph with {@code V} vertices and 0
	 * edges.
	 *
	 * @param V
	 *            the number of vertices
	 * @throws IllegalArgumentException
	 *             if {@code V < 0}
	 */
	public EdgeWeightedDiGraph(int V) {
		if (V < 0)
			throw new IllegalArgumentException("Number of vertices must be nonnegative");
		this.V = V;
		this.E = 0;
		adj = (Bag<DiEdge>[]) new Bag[V];
		for (int v = 0; v < V; v++) {
			adj[v] = new Bag<DiEdge>();
		}
	}

	public EdgeWeightedDiGraph(int V, int E) {
		this(V);
		if (E < 0)
			throw new IllegalArgumentException("Number of edges must be nonnegative");
		for (int i = 0; i < E; i++) {
			int v = StdRandom.uniform(V);
			int w = StdRandom.uniform(V);
			double weight = Math.round(100 * StdRandom.uniform()) / 100.0;
			DiEdge e = new DiEdge(v, w, weight);
			addEdge(e);
		}
	}

	public EdgeWeightedDiGraph(In in) {
		this(in.readInt());
		int E = in.readInt();
		if (E < 0)
			throw new IllegalArgumentException("Number of edges must be nonnegative");
		for (int i = 0; i < E; i++) {
			int v = in.readInt();
			int w = in.readInt();
			validateVertex(v);
			validateVertex(w);
			double weight = in.readDouble();
			DiEdge e = new DiEdge(v, w, weight);
			addEdge(e);
		}
	}

	/**
	 * Initializes a new edge-weighted graph that is a deep copy of {@code G}.
	 *
	 * @param G
	 *            the edge-weighted graph to copy
	 */
	public EdgeWeightedDiGraph(EdgeWeightedDiGraph G) {
		this(G.V());
		this.E = G.E();
		for (int v = 0; v < G.V(); v++) {
			// reverse so that adjacency list is in same order as original
			Stack<DiEdge> reverse = new Stack<DiEdge>();
			for (DiEdge e : G.adj[v]) {
				reverse.push(e);
			}
			for (DiEdge e : reverse) {
				adj[v].add(e);
			}
		}
	}

	public int V() {
		return V;
	}

	public int E() {
		return E;
	}

	private void validateVertex(int v) {
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
	}

	public void addEdge(DiEdge e) {
		int v = e.source();
		int w = e.target();
		validateVertex(v);
		validateVertex(w);
		adj[v].add(e);
		E++;
	}

	public Iterable<DiEdge> adj(int v) {
		validateVertex(v);
		return adj[v];
	}

	public int degree(int v) {
		validateVertex(v);
		return adj[v].size();
	}

	public Iterable<DiEdge> edges() {
		Bag<DiEdge> list = new Bag<DiEdge>();
		for (int v = 0; v < V; v++) {
			int selfLoops = 0;
			for (DiEdge e : adj(v))
				list.add(e);
		}
		return list;
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(V + " " + E + NEWLINE);
		for (int v = 0; v < V; v++) {
			s.append(v + ": ");
			for (DiEdge e : adj[v]) {
				s.append(e + "  ");
			}
			s.append(NEWLINE);
		}
		return s.toString();
	}

	public String toDot() {
		StringBuilder s = new StringBuilder();
		s.append("digraph {" + NEWLINE);
		s.append("rankdir = LR;" + NEWLINE);
		s.append("node [shape = circle];" + NEWLINE);
		for (DiEdge e : edges()) {
			String attrib = "label=" + e.weight();
			if (e.getColor() != null)
				attrib += ", color=" + e.getColor();
			s.append(e.source() + " -> " + e.target() + " [" + attrib + "]" + NEWLINE);
		}
		s.append("}");
		return s.toString();
	}

	public static void main(String[] args) {
		In in = new In(args[0]);
		EdgeWeightedDiGraph G = new EdgeWeightedDiGraph(in);
		StdOut.println(G.toDot());
	}

}
