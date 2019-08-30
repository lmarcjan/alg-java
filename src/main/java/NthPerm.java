public class NthPerm {

    private static void ithPermutation(int n, int i) {

        // fix
        n = n + 1;

        int[] fact = new int[n];
        int[] perm = new int[n];
        int j, k = 0;

        // compute factorial numbers
        fact[k] = 1;
        while (++k < n)
            fact[k] = fact[k - 1] * k;

        // fix
        i = i % fact[n - 1];

        // compute factorial code
        for (k = 0; k < n; ++k) {
            perm[k] = i / fact[n - 1 - k];
            i = i % fact[n - 1 - k];
        }

        // readjust values to obtain the permutation
        // start from the end and check if preceding values are lower
        for (k = n - 1; k > 0; --k)
            for (j = k - 1; j >= 0; --j)
                if (perm[j] <= perm[k])
                    perm[k]++;

        // print permutation
        // fix
        for (k = 1; k < n; ++k)
            System.out.printf("%s ", perm[k]);
        System.out.print("\n");
    }

    public static void main(String[] args) {
        ithPermutation(7, 17012013);
    }
}
