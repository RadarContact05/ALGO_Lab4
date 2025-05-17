import java.util.HashMap;
import java.util.Scanner;
public class DFSpaths{

  private HashMap<String,String> previous;
  private String start;

  public DFSpaths(Graph G, String s){
    previous = new HashMap<String,String>();
    start = s;
    previous.put(s,s);
    dfs(G, s);
  }

  private void dfs(Graph G, String v){
    for(String w : G.adj(v)){
      if(!previous.containsKey(w)){
        previous.put(w,v);
        dfs(G, w);
      }
    }
  }

  public boolean hasPathTo(String v){
    return previous.containsKey(v);
  }

  public Iterable<String> pathTo(String v){
    if (!hasPathTo(v)) return null;
    Bag<String> path = new Bag<String>();
    String x = v;
    while(!previous.get(x).equals(x)){
      path.add(x);
      x = previous.get(x);
    }
    path.add(start);
    return path;
  }

  public static void main(String[] args) {
    Graph G = new Graph(new Scanner(System.in));
    DFSpaths paths = new DFSpaths(G, args[0]);

    if(paths.hasPathTo(args[1])){
      for (String s : paths.pathTo(args[1])) {
        System.out.println(s);
      }
    }
    else System.out.println("No path from " + args[0] + " to " + args[1]);
  }

}
