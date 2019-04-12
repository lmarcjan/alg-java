import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Necklaces {

    private int n;
    private int k;
    private int[] a;
    private int[] b;

    public Necklaces(int n, int k) {
        this.n = n;
        this.k = k;
        this.a = new int[n + 1];
        this.b = new int[n + 1];
        for (int j = n; j >= 1; j--) {
            a[1] = j;
            for (int i = 1; i < k; i++) {
                b[1] = i;
                gen(1, 1);
            }
        }
    }
    
    private void gen(int t, int p) {
        if (a[t] >= n)
            print(t, p);
        else {
            int max = a[t + 1 - p] + a[p];
            if (max <= n) {
                a[t + 1] = max;
                b[t + 1] = b[t + 1 - p];
                gen(t + 1, p);
            } else {
                max = n;
                a[t + 1] = max;
                b[t + 1] = 1;
                gen(t + 1, t + 1);
            }
            for (int i = b[t + 1] + 1; i < k; i++) {
                b[t + 1] = i;
                gen(t + 1, t + 1);
            }
            for (int j = max - 1; j > a[t]; j--) {
                a[t + 1] = j;
                for (int i = 1; i < k; i++) {
                    b[t + 1] = i;
                    gen(t + 1, t + 1);
                }
            }
        }
    }

    private void print(int t, int p) {
        if (n % a[p] == 0) {
            int j = 1;
            for (int i = 1; i <= n; i++) {
                if (j <= t && a[j] == i) {
                    System.out.print(" " + b[j]);
                    j++;
                } else {
                    System.out.print(" " + 0);
                }
            }
            if (n == a[p]) {
                System.out.print(" (l)");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(Necklaces.class.getResourceAsStream("/input/Necklaces.in")));
        int n = Integer.parseInt(reader.readLine());
        int k = Integer.parseInt(reader.readLine());
        new Necklaces(n, k);
    }
}
