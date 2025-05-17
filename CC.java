import java.util.HashMap;
import java.util.Scanner;

public class CC{

  private HashMap<String, Integer> components;
  private int count;

  public CC(Graph G){
    components = new HashMap<String,Integer>();
    count = 0;
    for(String v : G.vertices()) {
      if(!components.containsKey(v)){
        dfs(G, v);
        count++;
      }
    }
  }

  public int count(){return count;}

  public boolean connected(String v, String w){
    return components.get(v) == components.get(w);
  }

  public int component(String v){
    return components.get(v);
  }

  private void dfs(Graph G, String v){
    components.put(v,count);
    for(String w : G.adj(v)){
      if(!components.containsKey(w)){
        dfs(G, w);
      }
    }
  }

  public static void main(String[] args) {
    Graph G = new Graph(new Scanner(System.in));
    CC cc = new CC(G);
    System.out.println("Nr of nodes: " + G.V());
    System.out.println("Nr of edges: " + G.E());
    System.out.println("Nr of components: " + cc.count());
    System.out.println("C of " + args[0] + ": " + cc.component(args[0]));
    System.out.println("C of " + args[1] + ": " + cc.component(args[1]));
    System.out.println(cc.connected(args[0], args[1]));

  }


}
