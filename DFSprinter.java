import java.util.HashSet;
import java.util.Scanner;

public class DFSprinter{

  private HashSet<String> marked;
  private Graph G;
  private String s;

  public DFSprinter(Graph G, String s){
    marked = new HashSet<String>();
    this.G = G;
    this.s = s;
  }

  public void print(){
    dfsPrint(G, s);
  }

  private void dfsPrint(Graph G, String v){
    marked.add(v);
    System.out.println(v);
    for(String w : G.adj(v)){
      if(!marked.contains(w)){
        dfsPrint(G, w);
      }
    }
  }

  public static void main(String[] args) {
    Graph G = new Graph(new Scanner(System.in));
    DFSprinter printer = new DFSprinter(G, args[0]);
    printer.print();
  }
}
