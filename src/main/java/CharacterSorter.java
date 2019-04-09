import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class CharacterSorter {

    public static void main(String [] args) {
	char [] chars = args[0].toCharArray();
	List<Character> list = new ArrayList<Character>();
	for (char c : chars) {
	    list.add(c);
	}
	Collections.sort(list);
	for (Character c : list) {
	    System.out.print(c);
	}
	System.out.println();
    }
}
