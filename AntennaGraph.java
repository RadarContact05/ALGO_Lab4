import java.util.*;

public class AntennaGraph extends Graph {
    private final Map<String, Antenna> antennas = new HashMap<>();

    private static class Antenna {
        double x, y, r;
        Antenna(double x, double y, double r) {
            this.x = x;
            this.y = y;
            this.r = r;
        }
    }

    public AntennaGraph(Scanner in) {
        List<String> ids = new ArrayList<>();
        while (in.hasNext()) {
            String id = in.next();
            double x = in.nextDouble();
            double y = in.nextDouble();
            double r = in.nextDouble();
            antennas.put(id, new Antenna(x, y, r));
            ids.add(id);
        }
        for (int i = 0; i < ids.size(); i++) {
            for (int j = i + 1; j < ids.size(); j++) {
                String v = ids.get(i);
                String w = ids.get(j);
                Antenna a = antennas.get(v);
                Antenna b = antennas.get(w);
                if (Math.hypot(a.x - b.x, a.y - b.y) <= a.r + b.r) {
                    addEdge(v, w);
                }
            }
        }
    }


    public boolean connected(String a, String b) {
        return shortestDistance(a, b) >= 0;
    }
    
    public int nrOfNwks() {
        return countComponents();
    }

    public double distanceToBorder(String a) {
        if(!antennas.containsKey(a)) {
            throw IllegalArgumentException("Antenna does not exist");
        }
        Set<String> component = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        component.put(a);
        queue.add(a);

        while (!queue.isEmpty()) {
            String q = queue.poll();
            for (String mark : adj(q)) {
                if (!component.containsKey(mark)) {
                    queue.add(mark);
                }
            }
        }

        Antenna index = antennas.get(a);
        double dist = 0.0;
        for (String that : component) {
            Antenna target = antennas.get(that);
            double d = Math.hypot(index.x - target.x, index.y - target.y);
            if (d > dist) {
                dist = d;
            }
        }
        return dist;
    }

    public int shortestDistance(String a, String b) {
        Queue<String> q = new Queue<String>();
        
    }

    public static void main(String[] args) {

    }
}