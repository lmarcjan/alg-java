package oi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Jaskinia {

	private Map<Integer, Set<Integer>> g;
	private boolean[] odwiedzony;
	private int[] f;
	private Set<Integer>[] S;
	private int wynik = 0;

	@SuppressWarnings("unchecked")
	public Jaskinia(int n, Map<Integer, Set<Integer>> we) {
		this.g = we;
		this.odwiedzony = new boolean[n + 1];
		this.f = new int[n + 1];
		this.S = new Set[n + 1];
		oblicz(1);
		print();
	}

	private void oblicz(int w) {
		odwiedzony[w] = true;
		Set<Integer> A = new TreeSet<Integer>();
		Set<Integer> B = new TreeSet<Integer>();
		for (Iterator<Integer> it = g.get(w).iterator(); it.hasNext();) {
			int u = it.next();
			if (!odwiedzony[u]) {
				oblicz(u);
				Set<Integer> b = new TreeSet<Integer>(S[u]);
				b.retainAll(A);
				B.addAll(b);
				A.addAll(S[u]);
			}
		}
		int m = B.isEmpty() ? -1 : B.iterator().next();
		f[w] = m + 1;
		while (A.contains(f[w])) {
			f[w]++;
		}
		S[w] = A;
		S[w].add(f[w]);
		for (int i = 0; i < f[w]; i++) {
			S[w].remove(i);
		}
		wynik = Math.max(wynik, f[w]);
	}

	private void print() {
		System.out.println(wynik);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(Jaskinia.class.getResourceAsStream("/input/oi/Jaskinia.in")));
		int n = Integer.parseInt(reader.readLine());
		Map<Integer, Set<Integer>> we = new HashMap<Integer, Set<Integer>>();
		for (int v = 1; v <= n; v++) {
			we.put(v, new HashSet<Integer>());
		}
		for (int i = 0; i < n - 1; i++) {
			String[] e = reader.readLine().split(" ");
			Integer v1 = Integer.parseInt(e[0]);
			Integer v2 = Integer.parseInt(e[1]);
			we.get(v1).add(new Integer(v2));
			we.get(v2).add(new Integer(v1));
		}
		new Jaskinia(n, we);
	}
}
