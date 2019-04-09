import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.Stack;

public class Samochodziki {

	public class Samochodzik implements Comparable<Samochodzik> {

		public int nr;
		public Stack<Integer> czas;

		public Samochodzik(int nr) {
			this.nr = nr;
			czas = new Stack<Integer>();
			czas.push(p + 1);
		}

		@Override
		public int compareTo(Samochodzik o) {
			return -(czas.peek() - o.czas.peek());
		}
	}

	private int wynik = 0;
	private int p;
	private PriorityQueue<Samochodzik> podloga = new PriorityQueue<Samochodzik>();

	public Samochodziki(int n, int k, int p, int[] we) {
		Samochodzik[] S = new Samochodzik[n + 1];
		for (int i = 1; i <= n; i++) {
			S[i] = new Samochodzik(i);
		}
		this.p = p;
		for (int t = p; t >= 1; t--) {
			S[we[t]].czas.push(t);
		}
		for (int t = 1; t <= p; t++) {
			S[we[t]].czas.pop();
			if (!podloga.contains(S[we[t]])) {
				if (podloga.size() == k) {
					podloga.remove();
				}
				podloga.add(S[we[t]]);
				wynik++;
			} else {
				podloga.remove(S[we[t]]);
				podloga.add(S[we[t]]);
			}
		}
		print();
	}

	private void print() {
		System.out.println(wynik);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(Samochodziki.class.getResourceAsStream("/input/oi/Samochodziki.in")));
		String[] dane = reader.readLine().split(" ");
		int n = Integer.parseInt(dane[0]);
		int k = Integer.parseInt(dane[1]);
		int p = Integer.parseInt(dane[2]);
		int[] we = new int[p + 1];
		for (int i = 1; i <= p; i++) {
			we[i] = Integer.parseInt(reader.readLine());
		}
		new Samochodziki(n, k, p, we);
	}
}
