import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class Autobus {

	public class Punkt {

		public int nr;
		public int x;
		public int y;
		public int p;

		public Punkt(int x, int y, int p) {
			this.x = x;
			this.y = y;
			this.p = p;
		}
	}

	private int wynik = 0;
	private Punkt[] N;
	private int[] W;

	public Autobus(int n, int m, int k, int[] we_x, int we_y[], int[] we_p) {
		N = new Punkt[k];
		W = new int[k];
		for (int i = 0; i < k; i++) {
			N[i] = new Punkt(we_x[i], we_y[i], we_p[i]);
			W[i] = 0;
		}
		Arrays.sort(N, new Comparator<Punkt>() {
			@Override
			public int compare(Punkt o1, Punkt o2) {
				if (o1.y != o2.y) {
					return o1.y - o2.y;
				}
				return o1.x - o2.x;
			}
		});
		for (int i = 0; i < k; i++) {
			N[i].nr = i;
		}
		Arrays.sort(N, new Comparator<Punkt>() {
			@Override
			public int compare(Punkt o1, Punkt o2) {
				if (o1.x != o2.x) {
					return o1.x - o2.x;
				}
				return o1.y - o2.y;
			}
		});
		for (int i = 0; i < k; i++) {
			W[N[i].nr] = N[i].p;
			int max = 0;
			for (int t = 0; t < N[i].nr; t++) {
				max = Math.max(max, W[t]);
			}
			W[N[i].nr] += max;
			wynik = Math.max(wynik, W[N[i].nr]);
		}
		print();
	}

	private void print() {
		System.out.println(wynik);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(Autobus.class.getResourceAsStream("/input/oi/Autobus.in")));
		String[] dane = reader.readLine().split(" ");
		int n = Integer.parseInt(dane[0]);
		int m = Integer.parseInt(dane[1]);
		int k = Integer.parseInt(dane[2]);
		int[] we_x = new int[k];
		int[] we_y = new int[k];
		int[] we_p = new int[k];
		for (int i = 0; i < k; i++) {
			dane = reader.readLine().split(" ");
			we_x[i] = Integer.parseInt(dane[0]);
			we_y[i] = Integer.parseInt(dane[1]);
			we_p[i] = Integer.parseInt(dane[2]);
		}
		new Autobus(n, m, k, we_x, we_y, we_p);
	}
}
