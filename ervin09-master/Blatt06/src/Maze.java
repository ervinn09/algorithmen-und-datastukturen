import java.util.*;

/**
 * Class that represents a maze with N*N junctions.
 * 
 * @author Vera Röhr
 */
public class Maze{
    private final int N;
    private Graph M;    //Maze
    public int startnode;
        
	public Maze(int N, int startnode) {
		
        if (N < 0) throw new IllegalArgumentException("Number of vertices in a row must be nonnegative");
        this.N = N;
        this.M= new Graph(N*N);
        this.startnode= startnode;
        buildMaze();
	}
	
    public Maze (In in) {
    	this.M = new Graph(in);
    	this.N= (int) Math.sqrt(M.V());
    	this.startnode=0;
    }

	
    /**
     * Adds the undirected edge v-w to the graph M.
     *
     * @param  v one vertex in the edge
     * @param  w the other vertex in the edge
     * @throws IllegalArgumentException unless both {@code 0 <= v < V} and {@code 0 <= w < V}
     */
    public void addEdge(int v, int w) {
		// TODO
        if (0 > v || v >= M.V () || 0 > w || w >= M.V () ){throw new IllegalArgumentException ();}
        if (!hasEdge (v,w)) M.addEdge (v,w);

    }
    
    /**
     * Returns true if there is an edge between 'v' and 'w'
     * @param v one vertex
     * @param w another vertex
     * @return true or false
     */
    public boolean hasEdge( int v, int w){
		// TODO
        if (0 > v || v >= M.V () || 0 > w || w >= M.V () ){return false;}
        if (M.adj (v).contains (w)|| M.adj (w).contains (v)||v==w){ return true; }
       return false;
    }	
    
    /**
     * Builds a grid as a graph.
     * @return Graph G -- Basic grid on which the Maze is built
     */
    public Graph mazegrid() {
		// TODO
     Graph maze = new Graph (M.V ());
        for (int i = 0;i<N;i++) {
            for (int j = 0;j<N-1;j++) {
                maze.addEdge (i*N+j,j+1+i*N);
                if (i<N-1)
                    maze.addEdge (i*N+j,N*(i+1)+j);
            }
            if (i<N-1)
                maze.addEdge (N*(i+1)-1,N*(i+2)-1);
        }
        return maze;
    }
    
    /**
     * Builds a random maze as a graph.
     * The maze is build with a randomized DFS as the Graph M.
     */
    private void buildMaze() {
		// TODO
      Graph graph = mazegrid (); // mazegrid function aufrufen
      RandomDepthFirstPaths random =new RandomDepthFirstPaths (graph,startnode);  // new Random depth object
      random.randomDFS (graph); ////dfs aufrufen

        List<Integer> list = new ArrayList<Integer> ();

      for (int a = 0;a < M.V (); a++){
           list = random.pathTo (a);
          for (int b = 0;b < list.size ()-1; b++) addEdge (list.get (b),list.get (b+1));
      }
    }

    /**
     * Find a path from node v to w
     * @param v start node
     * @param w end node
     * @return List<Integer> -- a list of nodes on the path from v to w (both included) in the right order.
     */
    public List<Integer> findWay(int v, int w){
		// TODO
        if (v>=M.V ()|| v<0)
            throw new IllegalArgumentException ("v<0 or v >= M.V()");
        if ( w>=M.V ()||w<0)
            throw new IllegalArgumentException ("w<0 or w >= M.V()");
        List<Integer> list = new ArrayList<Integer> ();
        RandomDepthFirstPaths random =new RandomDepthFirstPaths (M,v); //create a new RandomD.. object
        random.randomNonrecursiveDFS (M);       // dfs Function aufrufen
         list = random.pathTo (w);
        Collections.reverse (list);
        return list;
    }

    /**
     * @return Graph M
     */
    public Graph M() {
    	return M;
    }

    public static void main(String[] args) {
		// FOR TESTING
    }


}

