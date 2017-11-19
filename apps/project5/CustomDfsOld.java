import edu.princeton.cs.algs4.*;

public class CustomDfsOld {
    private boolean[] marked;  // marked[v] = true if v is reachable
                               // from source (or sources)
    private int count;         // number of vertices reachable from s
    private SCC scc;

    public CustomDfsOld(Digraph G, Digraph newG, SCC sc) {
        marked = new boolean[G.V()];
        scc = sc;
        dfs(G, newG, 0);
    }

    /**
     * Computes the vertices in digraph {@code G} that are
     * connected to any of the source vertices {@code sources}.
     * @param G the graph
     * @param sources the source vertices
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     *         for each vertex {@code s} in {@code sources}
     */
    // public CustomDfsOld(Digraph G, Digraph newG, Iterable<Integer> sources) {
    //     marked = new boolean[G.V()];
    //     validateVertices(sources);
    //     for (int v : sources) {
    //         if (!marked[v]) dfs(G, v);
    //     }
    // }

    private void dfs(Digraph G, Digraph newG, int v) { 
        count++;
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (scc.id(w) != scc.id(v)) {
                newG.addEdge(scc.id(w), scc.id(v));
            }
            if (!marked[w]) dfs(G, newG, w);
        }
    }

    /**
     * Is there a directed path from the source vertex (or any
     * of the source vertices) and vertex {@code v}?
     * @param  v the vertex
     * @return {@code true} if there is a directed path, {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean marked(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * Returns the number of vertices reachable from the source vertex
     * (or source vertices).
     * @return the number of vertices reachable from the source vertex
     *   (or source vertices)
     */
    public int count() {
        return count;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        }
        int V = marked.length;
        for (int v : vertices) {
            if (v < 0 || v >= V) {
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
            }
        }
    }

    /**
     * Unit tests the {@code CustomDfsOld} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        // read in digraph from command-line argument
        // In in = new In(args[0]);
        // Digraph G = new Digraph(in);

        // // read in sources from command-line arguments
        // Bag<Integer> sources = new Bag<Integer>();
        // for (int i = 1; i < args.length; i++) {
        //     int s = Integer.parseInt(args[i]);
        //     sources.add(s);
        // }

        // // multiple-source reachability
        // CustomDfsOld dfs = new CustomDfsOld(G, sources);

        // // print out vertices reachable from sources
        // for (int v = 0; v < G.V(); v++) {
        //     if (dfs.marked(v)) StdOut.print(v + " ");
        // }
        // StdOut.println();
    }

}
