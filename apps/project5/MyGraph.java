// MyGraph.java

import edu.princeton.cs.algs4.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

public class MyGraph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;
    private int E;
    private ArrayList<Integer>[] adj;
    private ArrayList<Pair> edges;

    public MyGraph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        edges = new ArrayList<Pair>();
        adj = (ArrayList<Integer>[]) new ArrayList[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new ArrayList<Integer>();
        }
    }

    public MyGraph(MyGraph G) {
        this(G.V());
        this.E = G.E();
        edges = new ArrayList<Pair>();
        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<Integer> reverse = new Stack<Integer>();
            for (int w : G.adj[v]) {
                reverse.push(w);
            }
            for (int w : reverse) {
                adj[v].add(w);
            }
        }
    }

    // Number of vertices
    public int V() {
        return V;
    }

    // Number of Edges
    public int E() {
        return E;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    // Adds the undirected edge v-w to this graph.
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        E++;
        adj[v].add(w);
        adj[w].add(v);

        Collections.sort(adj[v]);
        Collections.sort(adj[w]);
    }

    public void addEdgePair(Pair p) {
        edges.add(p);
    }

    public void removeEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        E--;

        edges.remove(new Pair(v, w));
        edges.remove(new Pair(v, w));
    }

    public void sortEdges() {
        for (int i = 0; i < adj.length; i++) {
            Collections.sort(adj[i]);
        }
    }

    // Returns the vertices adjacent to vertex {@code v}.
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    public ArrayList<Integer> getAdjacentList(int i) {
        return adj[i];
    }

    // Returns the degree of vertex {@code v}.
    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w : adj[v]) {
                s.append(w + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    

    public static void main(String[] args) {
        // In in = new In(args[0]);
        // MyGraph G = new MyGraph(in);
        // StdOut.println(G);
    }

}