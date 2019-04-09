import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Grotolazi {

	int n;
	private boolean[][] T;
	private boolean[] A, B;
	private int[] SA, SB;
	private boolean[] OB;
	private Map<Integer, Set<Integer>> g;
	private int wynik;

	public Grotolazi(Map<Integer, Set<Integer>> we) {
		this.g = we;
		this.n = g.size();
		gen();
		print();
	}

	private void print() {
		System.out.println(wynik);
	}

	private void gen() {
		T = new boolean[n + 1][];
		for (int i = 1; i <= n; i++) {
			T[i] = new boolean[n + 1];
			for (int j : g.get(i)) {
				T[i][j] = true;
			}
		}

		if (T[1][n]) {
			wynik++;
		}

		A = new boolean[n + 1];
		B = new boolean[n + 1];
		for (int i = 1; i <= n; i++) {
			A[i] = T[1][i];
			B[i] = T[i][n];
		}

		for (int k = 1; k <= n; k++) {
			for (int i = 1; i <= n; i++) {
				if (T[i][k]) {
					for (int j = 1; j <= n; j++) {
						T[i][j] = T[i][j] || T[k][j];
					}
				}
			}
		}
		for (int i = 1; i <= n; i++) {
			T[i][i] = true;
		}

		OB = new boolean[n + 1];
		SA = new int[n + 1];
		SB = new int[n + 1];
		boolean koniec = false;
		while (!koniec) {
			koniec = true;
			for (int i = 1; i <= n; i++) {
				OB[i] = false;
			}
			for (int i = 1; i <= n; i++) {
				if (A[i] && SA[i] == 0) {
					if (Powieksz(i)) {
						koniec = false;
						wynik++;
					}
				}
			}
		}
	}

	private boolean Powieksz(int x) {
		if (!OB[x]) {
			OB[x] = true;
			for (int y = 1; y <= n; y++) {
				if (B[y] && T[x][y]) {
					if (SB[y] == 0 || Powieksz(SB[y])) {
						SA[x] = y;
						SB[y] = x;
						return true;
					}
				}
			}
		}
		return false;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(Grotolazi.class.getResourceAsStream("/input/oi/Grotolazi.in")));
		int n = Integer.parseInt(reader.readLine());
		Map<Integer, Set<Integer>> we = new HashMap<Integer, Set<Integer>>();
		for (int v = 1; v <= n; v++) {
			we.put(v, new HashSet<Integer>());
		}
		for (int v1 = 1; v1 < n; v1++) {
			String[] e = reader.readLine().split(" ");
			int m = Integer.parseInt(e[0]);
			for (int j = 1; j <= m; j++) {
				Integer v2 = Integer.parseInt(e[j]);
				we.get(v1).add(v2);
			}
		}
		new Grotolazi(we);
	}
}
