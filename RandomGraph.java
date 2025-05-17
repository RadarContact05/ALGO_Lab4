public class RandomGraph{

  public static void main(String[] args) {

    int n = Integer.parseInt(args[0]);
    int upperBound = Integer.parseInt(args[1]);

    Graph rg = new Graph();
    for (int i = 0; i < n ; i++) {
      String v = "" + (int)(Math.random()*upperBound);
      String w = "" + (int)(Math.random()*upperBound);
      if (!v.equals(w)) {
        rg.addEdge(v, w);
      }
    }
    
    System.out.println("Nr of nodes: " + rg.V());
    System.out.println("Nr of edges: " + rg.E());
    System.out.println("The graph:\n" + rg);
  }

}
