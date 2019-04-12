import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Multibroadcast {

    private Map<Integer, List<Integer>> g;
    private int k;
    private int t;
    private int v0;
    private Map<Integer, Boolean> L = new HashMap<Integer, Boolean>();
    private Map<Integer, int[]> T = new HashMap<Integer, int[]>();
    private Map<Integer, int[]> M = new HashMap<Integer, int[]>();

    public Multibroadcast(Map<Integer, List<Integer>> we, int n, int k) {
        this.g = we;
        this.k = k;
        this.t = n * k;
        this.v0 = t;
        int r = we.keySet().iterator().next();
        gen(r);
        print(r, 0);
    }

    private void print(Integer v, int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("\t");
        }
        System.out.print("(" + v + ")");
        if (!L.get(v)) {
            for (Integer w : g.get(v)) {
                int[] m = new int[M.get(w).length];
                for (int i = 0; i < m.length; i++) {
                    m[i] = M.get(w)[i] - v0 + 1;
                }
                System.out.print(" " + w + "->" + Arrays.toString(m));
            }
            System.out.println();
            for (Integer w : g.get(v)) {
                print(w, level + 1);
            }
        } else {
            System.out.println();
        }
    }

    private void gen(Integer v) {
        int l = 0;
        int[][] label = new int[g.get(v).size()][];
        for (Integer w : g.get(v)) {
            gen(w);
            int[] t_w = new int[k];
            for (int i = 0; i < k; i++) {
                t_w[i] = L.get(w) ? t - k + i : T.get(w)[i] - 1;
            }
            label[l] = t_w;
            l++;
        }
        L.put(v, l == 0);
        RightmostTokenLocationResult tokenLocation = rightmostTokenLocation(label);
        l = 0;
        for (Integer w : g.get(v)) {
            M.put(w, tokenLocation.pos[l]);
            l++;
        }
        T.put(v, tokenLocation.V);
    }

    private static class RightmostTokenLocationResult {
        int[][] pos;
        int[] V;
    }

    private RightmostTokenLocationResult rightmostTokenLocation(int[][] label) {
        int l = label.length;
        int[][] pos = new int[l][];
        for (int i = 0; i < l; i++) {
            pos[i] = new int[k];
        }
        int[] V = new int[k];
        boolean[] token = new boolean[t];
        for (int j = k - 1; j >= 0; j--) {
            V[j] = t;
            for (int i = 0; i < l; i++) {
                // find the free box with largest number max not exceeding label[i][j]
                int max = label[i][j];
                while (token[max]) {
                    max--;
                }
                token[max] = true;
                pos[i][j] = max;
                V[j] = Math.min(V[j], max);
            }
            v0 = Math.min(v0, V[j]);
        }
        RightmostTokenLocationResult result = new RightmostTokenLocationResult();
        result.pos = pos;
        result.V = V;
        return result;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(Multibroadcast.class.getResourceAsStream("/input/Multibroadcast.in")));
        int n = Integer.parseInt(reader.readLine());
        Map<Integer, List<Integer>> we = new HashMap<Integer, List<Integer>>();
        for (int v = 1; v <= n; v++) {
            we.put(v, new ArrayList<Integer>());
        }
        for (int i = 1; i < n; i++) {
            String[] e = reader.readLine().split(" ");
            Integer v1 = Integer.parseInt(e[0]);
            Integer v2 = Integer.parseInt(e[1]);
            we.get(v1).add(v2);
        }
        int k = Integer.parseInt(reader.readLine());
        new Multibroadcast(we, n, k);
    }
}
