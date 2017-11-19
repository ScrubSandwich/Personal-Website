import edu.princeton.cs.algs4.*;
import java.util.ArrayList;

public class StronglyConnected {

    private static void print(MyGraph g) {
        StdOut.println("Undirected Graph: ");
        for (int v = 0; v < g.V(); v++) {
            for (int w : g.adj(v))
            StdOut.println(v + " " + w);
        }
    }

    private static void print(Graph g) {
        StdOut.println("Undirected Graph: ");
        for (int v = 0; v < g.V(); v++) {
            for (int w : g.adj(v))
            StdOut.println(v + " " + w);
        }
    }

    private static void print(Digraph g) {
        StdOut.println("Directed Graph");
        for (int v = 0; v < g.V(); v++) {
            for (int w : g.adj(v))
            StdOut.println(v + " " + w);
        }
    }

    private static void print(ArrayList<Pair> a) {
        for (int i = 0; i < a.size(); i++) {
            StdOut.println(a.get(i).getV() + " " + a.get(i).getW());
        }
    }

    private static void printArrayListInteger(ArrayList<Integer> a) {
        for (int i = 0; i < a.size(); i++) {
            StdOut.println(a.get(i));
        }
    }

    // can optomize this ? because MyGraph has duplicate points 
    private static ArrayList<Pair> getLeftOverList(MyGraph G, ArrayList<Pair> directedPairs) {
        ArrayList<Pair> leftOver = new ArrayList<Pair>();
        
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                boolean found = false;

                for (int i = 0; i < directedPairs.size(); i++) {
                    Pair pair = directedPairs.get(i);
                    int vv = pair.getV();
                    int ww = pair.getW();

                    if (vv == v && ww == w) {
                        found = true;
                        break;
                    }

                    if (vv == w && ww == v) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    // Make sure its not the other way around before adding it:
                    boolean f = false;
                    for (int i = 0; i < leftOver.size(); i++) {
                        Pair pair = leftOver.get(i);
                        int vvv = pair.getV();
                        int www = pair.getW();

                        //StdOut.println("Current pair:  " + vvv + " " + www);
                        //StdOut.println("Checking with: " + v + " " + w);
                        
                        if (vvv == v && www == w) {
                            f = true;
                            //StdOut.println("Match");
                            break;
                        }

                        if (vvv == w && www == v) {
                            f = true;
                            //StdOut.println("Match when reversed");
                            break;
                        }

                    }
                    
                    if (!f) {
                        leftOver.add(new Pair(v, w));
                    }
                }
            }
        }

        return leftOver;
    }

    public static void main(String[] args) {
        int numberOfVertices = Integer.parseInt(StdIn.readLine());
        int numberOfEdges = Integer.parseInt(StdIn.readLine());
        int maxNode = -999999; // Holds the greatest node to determine what we dfs on first
        ArrayList<Pair> edges = new ArrayList<Pair>();

        // Build Undirected MyGraph
        MyGraph graph = new MyGraph(numberOfVertices);
        Digraph digraph = new Digraph(numberOfVertices);

        while (!StdIn.isEmpty()) {
            String line = StdIn.readLine();
            String[] numbers = line.split("\\s+");

            int u = Integer.parseInt(numbers[0]);
            int v = Integer.parseInt(numbers[1]);

            if (u > maxNode) { maxNode = u; }
            if (v > maxNode) { maxNode = v; }

            graph.addEdge(u, v);
            Pair p = new Pair(u, v);
            graph.addEdgePair(p);
            edges.add(p);
        }

        // DFS on the maxNode
        Dfs dfs = new Dfs(graph, digraph, maxNode, edges);        
        //print(digraph);   // correct so far
        String output = dfs.getOutput(); // the dirgaph lines, but in order of DFS for the output
///////////////////////////////////////////print this now

        // We must update the digraph to have the new edges

        // Strongly connected digraph 
        SCC scc = new SCC(digraph);

        // New digraph of all the SCC from the OG digraph
        Digraph newDigraph = new Digraph(scc.count());

        // arraylist that holds all the edges of the digraph to assist in the DFS
        ArrayList<Pair> ed = new ArrayList<Pair>();
        // populate ed with the edges
        for (int v = 0; v < digraph.V(); v++) {
            for (int w : digraph.adj(v))
            //StdOut.println(v + " " + w);
            ed.add(new Pair(v, w));
        }

        //print(ed);    Correct!

        // DFS on old digraph, and add edges to newDigraph when the componets of the DFS are not same SCC
        CustomDfs customDfs = new CustomDfs(digraph, newDigraph, scc, ed, digraph.V() - 1);

        // print out the newDigraph as a test
        //print(newDigraph); 

        int max = 0;
        for (int v = 0; v < newDigraph.V(); v++) {
            for (int w : newDigraph.adj(v)) {
                // if (newDigraph.outdegree(w) > max) {
                //     max = newDigraph.outdegree(w);
                    
                // }

                // if (newDigraph.indegree(w) > max) {
                //     max = newDigraph.indegree(w);
                // }
                // ////
                // if (newDigraph.outdegree(v) > max) {
                //     max = newDigraph.outdegree(v);
                    
                // }

                // if (newDigraph.indegree(v) > max) {
                //     max = newDigraph.indegree(v);
                // }

                if (newDigraph.outdegree(w) == 0) {
                    max++;
                    
                }

                if (newDigraph.indegree(w) == 0) {
                    max++;
                }
            }
        }


        // The answer
        StdOut.println(max);
        StdOut.print(output); 

        // dfs on the digraph,
        // Compare each node to see if it is in DIFFERENT ID from SCC.id(node) != SCC.id(node) { }
        // if different, make edge between them, (make edges on the NEW digraph, which treats its nodes as all the SCC)
        // check in and out on that digraph  for answer!!!!!!!!!

        // number of connected components
        // int m = scc.count();
        // StdOut.println(m + " strong components");

        // // compute list of vertices in each strong component
        // Queue<Integer>[] components = (Queue<Integer>[]) new Queue[m];
        // for (int i = 0; i < m; i++) {
        //     components[i] = new Queue<Integer>();
        // }
        // for (int v = 0; v < digraph.V(); v++) {
        //     components[scc.id(v)].enqueue(v);
        // }

        // int max = 0;

        // // print results
        // for (int i = 0; i < m; i++) {
        //     for (int v : components[i]) {
        //         StdOut.print(v + " ");
        //     }
        //     StdOut.println();
        // }

        // Unused //

        //ArrayList<Pair> directedPairs = dfs.getPairs();
        // Get the a List of the pairs that are not contained in Directed Pairs
        // These leftovers will be the other ones printed at the end
        // ArrayList<Pair> nonTreeEdges = getLeftOverList(graph, directedPairs);
        //StdOut.println(nonTreeEdges.size());        
        //StdOut.println();
        //print(nonTreeEdges);
        // print(digraph);
        // printArrayListInteger(graph.getAdjacentList(2));
        //print(directedPairs);
        //print(graph);
    }
}