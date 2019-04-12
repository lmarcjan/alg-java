import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.Map.Entry;

public class StableRoommates {

    private static class Matching {

        Map<Integer, List<Integer>> table;

        public Matching(Map<Integer, List<Integer>> table) {
            this.table = new HashMap<Integer, List<Integer>>();
            for (Entry<Integer, List<Integer>> e : table.entrySet()) {
                this.table.put(e.getKey(), new ArrayList<Integer>());
                this.table.get(e.getKey()).addAll(e.getValue());
            }
        }

        public void makeSemiEngaged() throws NoStableMatchingExist {
            for (; ; ) {
                if (emptyListExist())
                    throw new NoStableMatchingExist("Empty list created when making semi engaged.");
                Integer ei = getFreePerson();
                if (ei != null) {
                    Integer hi = table.get(ei).get(0);
                    removeBelow(hi, ei);
                } else break;
            }
        }

        public boolean emptyListExist() {
            for (Integer ei : table.keySet()) {
                if (table.get(ei).isEmpty())
                    return true;
            }
            return false;
        }

        public boolean isSemiEngaged(Integer ei) {
            Integer hi = table.get(ei).get(0);
            Integer bi = table.get(hi).get(table.get(hi).size() - 1);
            return bi.equals(ei);
        }

        public List<List<Integer>> getRotations() {
            Map<Integer, Integer> hr = new HashMap<Integer, Integer>();
            for (Integer ei : table.keySet()) {
                hr.put(table.get(ei).get(0), ei);
            }
            List<List<Integer>> result = new ArrayList<List<Integer>>();
            Map<Integer, Boolean> resultAdded = new HashMap<Integer, Boolean>();
            for (Integer ei : table.keySet()) {
                if (resultAdded.get(ei) == null) {
                    // search for new rotation starting at ei
                    List<Integer> r = new ArrayList<Integer>();
                    Integer current = ei, next;
                    for (; ; ) {
                        r.add(current);
                        next = getRotationNext(current, hr);
                        if (next == null || r.contains(next)) break;
                        current = next;
                    }
                    if (ei.equals(next)) {
                        result.add(r);
                        for (Integer ri : r) {
                            resultAdded.put(ri, true);
                        }
                    }
                }
            }
            System.out.println("Rotations -> ");
            for (List<Integer> r : result) {
                System.out.println(Arrays.toString(r.toArray(new Integer[r.size()])));
            }
            return result;
        }

        private Integer getRotationNext(Integer ei, Map<Integer, Integer> hr) {
            if (table.get(ei).size() < 2) return null;
            Integer si = table.get(ei).get(1);
            return hr.get(si);
        }

        public Integer getFreePerson() {
            for (Integer ei : table.keySet()) {
                if (!isSemiEngaged(ei))
                    return ei;
            }
            return null;
        }

        public boolean isEverybodyEngaged() {
            for (Integer ei : table.keySet()) {
                if (table.get(ei).size() != 1)
                    return false;
            }
            return true;
        }

        public void eliminateRotation(List<Integer> r) throws NoStableMatchingExist {
            System.out.println("Eliminating rotation -> " + Arrays.toString(r.toArray(new Integer[r.size()])));
            Map<Integer, Integer> s = new HashMap<Integer, Integer>();
            for (Integer ei : table.keySet()) {
                s.put(ei, table.get(ei).size() > 1 ? table.get(ei).get(1) : null);
            }
            for (Integer ei : r) {
                Integer si = s.get(ei);
                removeBelow(si, ei);
            }
            if (emptyListExist()) {
                throw new NoStableMatchingExist("Empty list created after rotation.");
            }
        }

        private void removeBelow(Integer i, Integer j) {
            List<Integer> kk = new ArrayList<Integer>();
            boolean add = false;
            for (Integer p : table.get(i)) {
                if (add) {
                    kk.add(p);
                }
                if (p.equals(j)) {
                    add = true;
                }
            }
            for (Integer k : kk) {
                table.get(i).remove(k);
                table.get(k).remove(i);
            }
        }

        @Override
        public String toString() {
            return "Matching [table=" + table + "]";
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((table == null) ? 0 : table.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Matching other = (Matching) obj;
            if (table == null) {
                if (other.table != null)
                    return false;
            } else if (!table.equals(other.table))
                return false;
            return true;
        }

    }

    private Map<Integer, List<Integer>> we;

    public StableRoommates(Map<Integer, List<Integer>> we) {
        this.we = we;
    }

    private static class NoStableMatchingExist extends Exception {

        private static final long serialVersionUID = 1L;

        public NoStableMatchingExist(String msg) {
            super(msg);
        }

    }

    public List<Matching> gen() throws NoStableMatchingExist {
        // phase 1
        Matching m = new Matching(we);
        m.makeSemiEngaged();
        System.out.println("Semi engaged matching -> " + m);
        // phase 2
        return gen(m);
    }

    public List<Matching> gen(Matching m) throws NoStableMatchingExist {
        List<Matching> result = new ArrayList<Matching>();
        if (m.isEverybodyEngaged()) {
            result.add(m);
        } else {
            for (List<Integer> r : m.getRotations()) {
                Matching m1 = new Matching(m.table);
                m1.eliminateRotation(r);
                System.out.println("Matching after rotation eliminated -> " + m1);
                result.addAll(gen(m1));
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(StableRoommates.class.getResourceAsStream("/input/StableRoommates.in")));
        int n = Integer.parseInt(reader.readLine());
        Map<Integer, List<Integer>> we = new HashMap<Integer, List<Integer>>();
        for (int i = 1; i <= n; i++) {
            we.put(i, new ArrayList<Integer>());
            String[] prefs = reader.readLine().split(" ");
            for (int j = 0; j < n - 1; j++) {
                Integer k = Integer.parseInt(prefs[j]);
                we.get(i).add(k);
            }
        }
        StableRoommates sr = new StableRoommates(we);
        try {
            Set<Matching> matchings = new HashSet<Matching>(sr.gen());
            System.out.println("Matchings -> ");
            for (Matching m : matchings) {
                System.out.println(m);
            }
        } catch (NoStableMatchingExist e) {
            System.out.println("No stable matching exist -> " + e.getMessage());
        }
    }

}
