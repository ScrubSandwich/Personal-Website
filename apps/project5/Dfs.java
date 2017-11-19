// This is modified code provided by the authors for my purposes
import edu.princeton.cs.algs4.*;
import java.util.ArrayList;

public class Dfs {
    private boolean[] marked;    // marked[v] = is there an s-v path?
    private int count;           // number of vertices connected to s
    private ArrayList<Pair> directedPairs;
    private ArrayList<Pair> edges;

    private String output = "";

    public Dfs(MyGraph G, Digraph D, int s, ArrayList<Pair> e) {
        marked = new boolean[G.V()];
        directedPairs = new ArrayList<Pair>();
        edges = e;
        validateVertex(s);
        dfs(G, D, s, e);
    }

    public ArrayList<Pair> getPairs() {
        return directedPairs;
    }

    public String getOutput() { 
        return output;
    }

    // See if the edges list has that list in it
    private boolean has(ArrayList<Pair> edges, int w, int v) {
        for (int i = 0; i < edges.size(); i++) {
            Pair pair = edges.get(i);
            int x = pair.getV();
            int y = pair.getW();

            if ((w == x && v == y)) {
                edges.remove(i);
                // StdOut.println("Removing this edge: ");
                // pair.print();
                return true;
            }
            if (w == y && v == x) {
                edges.remove(i);
                // StdOut.println("Removing this edge: ");
                // pair.print();
                return true;
            }
        }
        return false;
    }

    // depth first search from v
    private void dfs(MyGraph G, Digraph D, int v,  ArrayList<Pair> edges) {
        count++;
        marked[v] = true;

        int vw = 0;
        for (int w : G.adj(v)) {
            //StdOut.println("checking node " + w);
            boolean has = has(edges, w, v);
            
            if (!marked[w] || has) {

                if (!has) {
                    D.addEdge(v, w);
                    directedPairs.add(new Pair(v, w));
                }
                
                output +=  v + " " + w + "\n";
                D.addEdge(v, w);
                //if (v == w) { vw++; continue;}
                if (!marked[w]) { dfs(G, D, w, edges); }
            }
        }
    }

     // Is there a path between the source vertex {@code s} and vertex {@code v}?
     private boolean marked(int v) {
        validateVertex(v);
        return marked[v];
    }

    // Returns the number of vertices connected to the source vertex {@code s}.
    public int count() {
        return count;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    public static void main(String[] args) {

        // int s = Integer.parseInt(args[1]);
        // Dfs search = new Dfs(G, s);
        // for (int v = 0; v < G.V(); v++) {
        //     if (search.marked(v))
        //         StdOut.print(v + " ");
        // }

        // StdOut.println();
        // if (search.count() != G.V()) StdOut.println("NOT connected");
        // else                         StdOut.println("connected");
    }

}