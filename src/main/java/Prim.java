import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Prim {

	public static class Edge implements Comparable<Edge> {

		public final Integer source;
		public final Integer target;
		public final Integer weight;

		public Edge(int source, int target, int weight) {
			this.source = source;
			this.target = target;
			this.weight = weight;
		}

		@Override
		public int compareTo(Edge o) {
			return weight - o.weight;
		}

		@Override
		public String toString() {
			return "(" + source + ", " + target + ")";
		}
	}

	private Set<Edge> mst;
	private Map<Integer, Set<Edge>> g;

	public Prim(Map<Integer, Set<Edge>> we) {
		this.g = we;
		gen();
		print();
	}

	private void print() {
		if (mst.size() < g.size() - 1) {
			throw new IllegalArgumentException("The graph is not connected");
		}
		for (Edge e : mst) {
			System.out.println(e.toString());
		}
	}

	private void gen() {
		mst = new HashSet<Edge>();
		Set<Integer> visited = new HashSet<Integer>();
		Integer start = g.keySet().iterator().next();
		visited.add(start);
		PriorityQueue<Edge> edgeQueue = new PriorityQueue<Edge>();
		edgeQueue.addAll(g.get(start));
		while (!edgeQueue.isEmpty()) {
			Edge e = edgeQueue.remove();
			if (!visited.contains(e.target)) {
				mst.add(e);
				visited.add(e.target);
				edgeQueue.addAll(g.get(e.target));
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(Prim.class.getResourceAsStream("/input/Prim.in")));
		int n = Integer.parseInt(reader.readLine());
		Map<Integer, Set<Edge>> we = new HashMap<Integer, Set<Edge>>();
		for (int v = 1; v <= n; v++) {
			we.put(v, new HashSet<Edge>());
		}
		int m = Integer.parseInt(reader.readLine());
		for (int i = 0; i < m; i++) {
			String[] e = reader.readLine().split(" ");
			Integer v1 = Integer.parseInt(e[0]);
			Integer v2 = Integer.parseInt(e[1]);
			Integer w = Integer.parseInt(e[2]);
			we.get(v1).add(new Edge(v1, v2, w));
			we.get(v2).add(new Edge(v2, v1, w));
		}
		new Prim(we);
	}
}
