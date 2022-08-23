import java.util.Stack;

public class ShortestPathsTopological {
    private int[] parent;
    private int s;
    private double[] dist;
    private int N = Integer.MAX_VALUE;


    public ShortestPathsTopological(WeightedDigraph G, int s) {
        // TODO
        dist = new double[G.V ()];
        parent = new int[G.V ()];
        this.s=s;
        Stack<Integer> stack = new Stack<> ();
        TopologicalWD wd = new TopologicalWD (G);

        for (int v = 0; v < G.V(); v++) {
            dist[v] = N;
        }
        dist[s]=0;
         wd.dfs (s);

         stack = wd.order ();

        while (!stack.isEmpty ()){
            for (DirectedEdge edge: G.incident (stack.pop ())) {
                relax (edge);
            }
        }
    }

    public void relax(DirectedEdge e) {
        // TODO
        int v = e.from ();
        int w = e.to ();
        if (dist[w] > dist[v] +  e.weight ()){
            parent[w] = v;
            dist[w] = dist[v] +  e.weight ();
        }
    }

    public boolean hasPathTo(int v) {
        return parent[v] >= 0;
    }

    public Stack<Integer> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<Integer> path = new Stack<>();
        for (int w = v; w != s; w = parent[w]) {
            path.push(w);
        }
        path.push(s);
        return path;
    }
}

