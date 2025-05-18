import java.util.*;

public class AntennaGraph {
    private static class Antenna {
        String id;               // id for antenna   
        double x, y;            // coordinate for antenna
        double r;               // antenna radius
        Antenna(String id, double x, double y, double r) {
            this.id = id;           
            this.x = x;           
            this.y = y;          
            this.r = r;            
        }
    }

    // List of all antennas
    private final List<Antenna> antennas = new ArrayList<>();

    // Adjecency list mappig each antenna ID to its list of neighboring ID
    private final Map<String, List<String>> adj = new HashMap<>();

    // constructor, reads from scanner and builds nodes/antennas from data given from file. Builds the list and then populate the adjacencies/overlaps
    public AntennaGraph(Scanner scanner) {

        // read all antennas
        while (scanner.hasNext()) {
            scanner.next();                     // skip node
            String id = scanner.next();         // Antenna ID
            scanner.next();                     // skip x string
            double x = scanner.nextDouble();    // x
            scanner.next();                     // skip y string
            double y = scanner.nextDouble();    // y
            scanner.next();                     // skip r string
            double r = scanner.nextDouble();    // r

            // add antennas to list of antennas
            antennas.add(new Antenna(id, x, y, r));

            // prepare adjacency list entr
            adj.put(id, new ArrayList<>());
        }

        // build graph. For each pair, add edge if coverage circles overlap
        for (int i = 0; i < antennas.size(); i++) {
            Antenna a = antennas.get(i);
            for (int j = i + 1; j < antennas.size(); j++) {
                Antenna b = antennas.get(j);

                // two circles overlap of center distance <= sum of rad
                if (Math.hypot(a.x - b.x, a.y - b.y) <= a.r + b.r) {
                    adj.get(a.id).add(b.id);
                    adj.get(b.id).add(a.id);
                }
            }
        }
    }

    // returns true if there exists any path/overlapp between rad
    public boolean connected(String a, String b) {
        return shortestDistance(a, b) >= 0;
    }

    // counts the number of connected components
    public int nrOfNwks() {
        Set<String> visited = new HashSet<>();
        int count = 0;
        for (Antenna a : antennas) {
            if (!visited.contains(a.id)) {
                dfs(a.id, visited);
                count++;
            }
        }
        return count;
    }

    // depth-first traversal, marking all reachable IDs from v
    private void dfs(String v, Set<String> visited) {
        visited.add(v);
        for (String w : adj.get(v)) {
            if (!visited.contains(w)) dfs(w, visited);
        }
    }

    // for component containing a, computes the max distance from a to any other antenna in that component
    public double distanceToBorder(String a) {
        // find all nodes in the same component via BFS
        Set<String> comp = new HashSet<>();
        Queue<String> q = new LinkedList<>();
        comp.add(a);
        q.add(a);
        while (!q.isEmpty()) {
            String v = q.poll();
            for (String w : adj.get(v)) {
                if (!comp.contains(w)) {
                    comp.add(w);
                    q.add(w);
                }
            }
        }

        // locate the origin antenna object
        Antenna origin = null;
        for (Antenna ant : antennas) {
            if (ant.id.equals(a)) {
                origin = ant;
                break;
            }
        }

        // Compute max distance from origin to any in component
        double max = 0;
        for (String id : comp) {
            for (Antenna ant : antennas) {
                if (ant.id.equals(id)) {
                    double d = Math.hypot(origin.x - ant.x, origin.y - ant.y);
                    if (d > max) max = d;
                    break;
                }
            }
        }
        return max;
    }

    // returns the fewest number of edges between antennas a and b. return -1 if unreachable
    public int shortestDistance(String a, String b) {
        Map<String, Integer> dist = new HashMap<>();
        Queue<String> q = new LinkedList<>();
        dist.put(a, 0);
        q.add(a);
        while (!q.isEmpty()) {
            String v = q.poll();
            int d = dist.get(v);
            if (v.equals(b)) return d;
            for (String w : adj.get(v)) {
                if (!dist.containsKey(w)) {
                    dist.put(w, d + 1);
                    q.add(w);
                }
            }
        }
        return -1;
    }

    public void draw() {
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, 1000);
        StdDraw.setYscale(0, 1000);
        StdDraw.clear();

        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.GRAY);
        for (Antenna a : antennas) {
            StdDraw.circle(a.x, a.y, a.r);
            StdDraw.text(a.x, a.y, a.id);
        }

        StdDraw.setPenRadius(0.002);
        StdDraw.setPenColor(StdDraw.BLUE);
        for (String v : adj.keySet()) {
            Antenna a = null;
            for (Antenna x : antennas) if (x.id.equals(v)) a = x;
            for (String w : adj.get(v)) {
                Antenna b = null;
                for (Antenna x : antennas) if (x.id.equals(w)) b = x;
                StdDraw.line(a.x, a.y, b.x, b.y);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AntennaGraph g = new AntennaGraph(scanner);
        g.draw();
        System.out.println();
        int rn1 = (int) (Math.random() * g.nrOfNwks());
        int rn2 = (int) (Math.random() * g.nrOfNwks());
        String id1 = String.valueOf(rn1);
        String id2 = String.valueOf(rn2);
        System.out.println("Testing random nodes, " + id1 + " and " + id2);
        System.out.println("Connected? " + g.connected(id1, id2));
        System.out.println("Number of networks: " + g.nrOfNwks());
        System.out.println("Max distance from " + id1 + ": " + g.distanceToBorder(id1));
        System.out.println("Shortest path " + id1 + "->" + id2 + ": " + g.shortestDistance(id1, id2));
    }   
}