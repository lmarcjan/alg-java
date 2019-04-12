package oi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Addon {

    private int wynik = 0;
    private PriorityQueue<Integer> kand = new PriorityQueue<Integer>();
    private List<Integer> P = new ArrayList<Integer>();

    public Addon(int n, int[] we) {
        int nast = 2 * we[1];
        P.add(we[1]);
        for (int k = 2; k <= n; k++) {
            if (we[k] > nast) {
                break;
            } else {
                if (we[k] < nast) {
                    P.add(we[k]);
                }
                for (int p : P) {
                    kand.add(we[k] + p);
                }
                if (we[k] == nast) {
                    nast = kand.remove();
                }
            }
        }
        wynik = nast - 1;
        print();
    }

    private void print() {
        System.out.println(wynik);
        for (int p : P) {
            System.out.println(p);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(Addon.class.getResourceAsStream("/input/oi/Addon.in")));
        int n = Integer.parseInt(reader.readLine());
        int[] we = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            we[i] = Integer.parseInt(reader.readLine());
        }
        new Addon(n, we);
    }
}
