import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Manacher {

	private int[][] R;
	private String text;
	private int n;

	public Manacher(String we) {
		this.text = we;
		this.n = we.length();
		R = new int[2][n + 1];
		gen();
		print();
	}

	private void gen() {
		char[] s = ("#" + text + "$").toCharArray();
		for (int j = 0; j <= 1; j++) {
			R[j][0] = 0;
			int i = 1;
			int r = 0;
			while (i <= n) {
				while (s[i - r - 1] == s[i + j + r])
					r++;
				R[j][i] = r;
				int k = 1;
				while (R[j][i - k] != r - k && k < r) {
					R[j][i + k] = Math.min(R[j][i - k], r - k);
					k++;
				}
				r = Math.max(r - k, 0);
				i += k;
			}
		}
	}

	private void print() {
		System.out.println(text);
		for (int i = 1; i <= n; i++) {
			for (int j = 0; j <= 1; j++) {
				for (int r = R[j][i]; r > 0; r--) {
					for (int k = 1; k < i - r; k++)
						System.out.print(" ");
					System.out.println(text.substring(i - r - 1, i + r + j - 1));
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(Manacher.class.getResourceAsStream("/input/Manacher.in")));
		String we = reader.readLine();
		new Manacher(we);
	}
}
