import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Wielokat {

	public static class Przekatna implements Comparable<Przekatna> {

		public final Integer a;
		public final Integer b;

		public Przekatna(int a, int b) {
			this.a = a;
			this.b = b;
		}

		@Override
		public int compareTo(Przekatna o) {
			if (a != o.a) {
				return a - o.a;
			}
			return o.b - b;
		}
		
		@Override
		public String toString() {
			return "(" + a + ", " + b + ")";
		}		
	}

	private int wynik = 0;
	private int poz = 0;
	private Przekatna[] przekatne;

	public Wielokat(int n, Przekatna[] we) {
		Arrays.sort(we);
		this.przekatne = we;
		ObejrzyjWie();
		print();
	}

	private void print() {
		System.out.println(wynik);		
	}	
	
	public void ObejrzyjWie() {
		int lb = przekatne[poz].b - przekatne[poz].a + 1;
		int prawyKoniec = przekatne[poz].b;
		poz++;
		while (przekatne[poz].a < prawyKoniec) {
			lb -= przekatne[poz].b - przekatne[poz].a + 1;
			ObejrzyjWie();
		}
		wynik = Math.max(wynik, lb);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(Wielokat.class.getResourceAsStream("/input/oi/Wielokat.in")));
		int n = Integer.parseInt(reader.readLine());
		int k = Integer.parseInt(reader.readLine());
		Przekatna[] we = new Przekatna[k + 2];
		we[0] = new Przekatna(1, n);
		for (int i = 1; i <= k; i++) {
			String[] przekatna = reader.readLine().split(" ");
			int a = Integer.parseInt(przekatna[0]);
			int b = Integer.parseInt(przekatna[1]);
			we[i] = new Przekatna(Math.min(a,b),Math.max(a,b));
		}
		we[k + 1] = new Przekatna(n + 1, 0);
		new Wielokat(n, we);
	}
}
