import java.util.NavigableSet;
import java.util.TreeSet;

public class NavigableSets {

    public static void main(String[] args) {
        NavigableSet<Integer> tree = new TreeSet<Integer>();
        for (int i = 10; i <= 100; i += 10) {
            tree.add(i);
        }
        Integer ceiling = tree.ceiling(25);
        System.out.println("ceiling of 25 = " + ceiling);
        NavigableSet<Integer> subset = tree.subSet(new Integer(40), false, new Integer(70), true);
        for (Integer x : subset) {
            System.out.println(x + " ");
        }
    }
}
