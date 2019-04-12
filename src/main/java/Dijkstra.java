import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Dijkstra {

    public class Vertex implements Comparable<Vertex> {

        public final Integer vertex;

        public Vertex(int v) {
            this.vertex = v;
        }

        @Override
        public int compareTo(Vertex o) {
            return D[vertex] - D[o.vertex];
        }

    }

    public static class Edge {

        public final Integer source;
        public final Integer target;
        public final Integer weight;

        public Edge(int source, int target, int weight) {
            this.source = source;
            this.target = target;
            this.weight = weight;
        }

    }

    private int[] D;
    private Map<Integer, Set<Edge>> g;

    public Dijkstra(Map<Integer, Set<Edge>> we) {
        this.g = we;
        gen();
        print();
    }

    private void print() {
        for (int i = 1; i <= g.size(); i++) {
            System.out.println(D[i]);
        }
    }

    private void gen() {
        D = new int[g.size() + 1];
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
        Set<Integer> visited = new HashSet<Integer>();
        Iterator<Integer> it = g.keySet().iterator();
        Integer start = it.next();
        D[start] = 0;
        vertexQueue.add(new Vertex(start));
        visited.add(start);
        while (it.hasNext()) {
            Integer v = it.next();
            D[v] = Integer.MAX_VALUE;
            vertexQueue.add(new Vertex(v));
        }
        while (!vertexQueue.isEmpty()) {
            Vertex v = vertexQueue.remove();
            for (Iterator<Edge> iterator = g.get(v.vertex).iterator(); iterator.hasNext(); ) {
                Edge e = (Edge) iterator.next();
                if (!visited.contains(e.target)) {
                    D[e.target] = Math.min(D[v.vertex] + e.weight, D[e.target]);
                    visited.add(e.target);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(Dijkstra.class.getResourceAsStream("/input/Dijkstra.in")));
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
        new Dijkstra(we);
    }
}
