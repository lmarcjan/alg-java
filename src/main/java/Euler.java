import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Euler {

	private Map<Integer, List<Integer>> Sasiedzi;
	private Map<Integer, List<Integer>> SasiedziR;	
	private Map<Integer, Stack<Integer>> Stos;
	private Map<Integer, Boolean> Odwiedzony;

	public Euler(Map<Integer, List<Integer>> we) {
		this.Sasiedzi = we;
		this.SasiedziR = new HashMap<Integer, List<Integer>>();		
		this.Stos = new HashMap<Integer, Stack<Integer>>();
		this.Odwiedzony = new HashMap<Integer, Boolean>();
		for (int v : Sasiedzi.keySet()) {
			SasiedziR.put(v, new ArrayList<Integer>());
			Stos.put(v, new Stack<Integer>());
			Odwiedzony.put(v, false);
		}
		for (int v : Sasiedzi.keySet()) {		
			for (int w : Sasiedzi.get(v)) {
				SasiedziR.get(w).add(v);
			}
		}	
		int r = we.keySet().iterator().next();
		gen(r);
	}

	private void W_glab(int v) {
		Odwiedzony.put(v, true);
		for (int w : SasiedziR.get(v)) {
			Stos.get(w).push(v);
			if (Odwiedzony.get(w)==false) {
				W_glab(w);
			}
		}
	}

	private void gen(Integer v) {
		W_glab(v);
		while (!Stos.get(v).isEmpty()) {
			System.out.println(v + " --> " + Stos.get(v).peek());
			v = Stos.get(v).pop();
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(Euler.class.getResourceAsStream("/input/Euler.in")));
		int n = Integer.parseInt(reader.readLine());
		Map<Integer, List<Integer>> we = new HashMap<Integer, List<Integer>>();
		for (int v = 1; v <= n; v++) {
			we.put(v, new ArrayList<Integer>());
		}
		int m = Integer.parseInt(reader.readLine());
		for (int i = 0; i < m; i++) {
			String[] e = reader.readLine().split(" ");
			Integer v1 = Integer.parseInt(e[0]);
			Integer v2 = Integer.parseInt(e[1]);
			we.get(v1).add(v2);
		}
		new Euler(we);
	}
}
