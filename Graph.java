import java.util.Scanner;
import java.util.HashMap;

public class Graph {
    private int V;
    private int E;
    private HashMap<String, Bag<String>> adj;

    public Graph(Scanner in) {
      adj = new HashMap<String, Bag<String>>();
      while(in.hasNext()){
        String v = in.next();
        String w = in.next();
        addEdge(v, w);
      }
    }

   public Graph(Scanner in, String delimiter) {
     adj = new HashMap<String, Bag<String>>();
     while(in.hasNext()){
       String[] line = in.nextLine().split(delimiter);
       String v = line[0];
       for (int i = 1; i < line.length; i++) {
         addEdge(v, line[i]);
       }
     }
   }

   public Graph(){
     adj = new HashMap<String, Bag<String>>();
     V = 0;
     E = 0;
   }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(String v, String w) {
        E++;
        if(!adj.containsKey(v)) {
            V++;
            adj.put(v, new Bag<String>());
        }
        if(!adj.containsKey(w)) {
          V++;
          adj.put(w, new Bag<String>());
        }
        adj.get(v).add(w);
        adj.get(w).add(v);
      }


    public Iterable<String> vertices(){
      return adj.keySet();
    }

    public Iterable<String> adj(String v) {
        return adj.get(v);
    }

    public int degree(String v) {
        return adj.get(v).size();
    }


    public String toString() {
        StringBuilder s = new StringBuilder();
        for (String v : vertices()) {
            s.append(v + ": ");
            for (String w : adj.get(v)) {
                s.append(w + " ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        String delimiter = args[0];
        Scanner in = new Scanner(System.in);
        Graph G = new Graph(in, delimiter);

        for(String v : G.vertices()){
          System.out.println("degree of " + v + ": " + G.degree(v));
        }

        for(String v : G.vertices()){
          for(String w : G.adj(v)){
            System.out.print("[" + v + " -> " + w + "] ");
          }
          System.out.println();
        }

        System.out.println(G);
    }
}
