import java.util.*;
import java.awt.Color;

/**
 * This class solves a clustering problem with the Prim algorithm.
 */
public class Clustering {
	EdgeWeightedGraph G;
	List <List<Integer>>clusters; 
	List <List<Integer>>labeled;

	
	/**
	 * Constructor for the Clustering class, for a given EdgeWeightedGraph and no labels.
	 * @param G a given graph representing a clustering problem
	 */
	public Clustering(EdgeWeightedGraph G) {
            this.G=G;
	    clusters= new LinkedList <List<Integer>>();
	}
	
    /**
	 * Constructor for the Clustering class, for a given data set with labels
	 * @param in input file for a clustering data set with labels
	 */
	public Clustering(In in) {
            int V = in.readInt();
            int dim= in.readInt();
            G= new EdgeWeightedGraph(V);
            labeled=new LinkedList <List<Integer>>();
            LinkedList labels= new LinkedList();
            double[][] coord = new double [V][dim];
            for (int v = 0;v<V; v++ ) {
                for(int j=0; j<dim; j++) {
                	coord[v][j]=in.readDouble();
                }
                String label= in.readString();
                    if(labels.contains(label)) {
                    	labeled.get(labels.indexOf(label)).add(v);
                    }
                    else {
                    	labels.add(label);
                    	List <Integer> l= new LinkedList <Integer>();
                    	labeled.add(l);
                    	labeled.get(labels.indexOf(label)).add(v);
                    	System.out.println(label);
                    }                
            }
             
            G.setCoordinates(coord);
            for (int w = 0; w < V; w++) {
                for (int v = 0;v<V; v++ ) {
                	if(v!=w) {
                	double weight=0;
                    for(int j=0; j<dim; j++) {
                    	weight= weight+Math.pow(G.getCoordinates()[v][j]-G.getCoordinates()[w][j],2);
                    }
                    weight=Math.sqrt(weight);
                    Edge e = new Edge(v, w, weight);
                    G.addEdge(e);
                	}
                }
            }
	    clusters= new LinkedList <List<Integer>>();
	}
	
    /**
	 * This method finds a specified number of clusters based on a MST.
	 *
	 * It is based in the idea that removing edges from a MST will create a
	 * partition into several connected components, which are the clusters.
	 * @param numberOfClusters number of expected clusters
	 */
	public void findClusters(int numberOfClusters){
		// TODO
		PrimMST mst = new PrimMST(G);  // PrimMSt erstellen
		List<Edge> list = (List<Edge>) mst.edges();  // übertragen von edges
		UF unionfind = new UF(G.V());
		List<Integer> list2 = new LinkedList<Integer>();
		int count = list.size () -1;

        Collections.sort (list);

		for(int w = 0; w < numberOfClusters-1; w++) {
			list.remove (count);
			count --;
		}

		for(Edge e: list) {
			int a = e.either();
			int b = e.other(a);
			if(!unionfind.connected(a,b)) {
				unionfind.union(a,b);
			}
		}
		for(int w = 0;w < G.V(); w++) {
			if(!list2.contains(unionfind.find(w))) {
				list2.add(unionfind.find(w));
			}
		}

		for(int temp: list2) {
			List<Integer> retvalue = new LinkedList<Integer>();
			for(int v = 0; v < G.V(); v++) {
				if(unionfind.find(v) == temp) {
					retvalue.add(v);
				}
			}
			clusters.add(retvalue);
		}

	}



	
	/**
	 * This method finds clusters based on a MST and a threshold for the coefficient of variation.
	 *
	 * It is based in the idea that removing edges from a MST will create a
	 * partition into several connected components, which are the clusters.
	 * The edges are removed based on the threshold given. For further explanation see the exercise sheet.
	 *
	 * @param threshold for the coefficient of variation
	 */
	public void findClusters(double threshold){
		// TODO
		//obj erzeugen
		PrimMST mst = new PrimMST(G);
		ArrayList<Edge> arrlist = new ArrayList<Edge>();
		UF unionf = new UF(G.V());
		Stack<Integer> stack = new Stack<Integer>();
		ArrayList<Edge> k = new ArrayList<Edge>();
        ////////////////////////////////////////////

		for(Edge e : mst.edges()) {
			arrlist.add(e);
		}
		Collections.sort(arrlist);

		for(int i = 0; i <arrlist.size();i++) {
			k.add(arrlist.get(i));
			double var = coefficientOfVariation(k);
			if(threshold < var) { //
				k.remove(arrlist.get(i));
			}
		}

		for(Edge e : k) {
			if(!unionf.connected(e.other(e.either()),e.either())) {
				unionf.union( e.other(e.either()),e.either() );
			}
		}
		for(int c = 0; c < G.V(); c++) {
			if(!stack.contains(unionf.find(c))) {
				stack.add(unionf.find(c));
			}
		}
		while (!stack.empty()) {
			ArrayList<Integer> neu_cluster = new ArrayList<Integer>();
			int temp = stack.pop();
			for(int i = 0; i < G.V() ; i++) {
				if(unionf.find(i) == temp) {
					neu_cluster.add(i);
				}
			}
			this.clusters.add(neu_cluster);
		}
		Collections.reverse(this.clusters);
	}

	
	/**
	 * Evaluates the clustering based on a fixed number of clusters.
	 * @return array of the number of the correctly classified data points per cluster
	 */
	public int[] validation() {
		// TODO
		int n = this.labeled.size ();
		//size of labeled
        int[] array = new int[n]; //init a new array
		int c = 0;//a new variable
        for(List<Integer> temp : this.labeled) {
            for(int a: temp) {
                for(int i : this.clusters.get(c)) {
                    if(a == i) {
                        array[c]++;
                    }
                }
            }
            c++;
        }
        return array;
	}
	
	/**
	 * Calculates the coefficient of variation.
	 * For the formula see exercise sheet.
	 * @param part list of edges
	 * @return coefficient of variation
	 */
	public double coefficientOfVariation(List <Edge> part) {
		// TODO

			double erst = 0;
			double zweit = 0;
			double sum = 0;

			//teilen mit 0 nicht erlaubt
			if (part.size () == 0)
				return 0;

            //erstmal berechne ich summe von erste Funktion
			for(Edge e:part) {
				erst += Math.pow(e.weight(),2);
			}
			// dann von zweite
			for (Edge e :part) {
			zweit += e.weight();
		    }
			double temp1 = erst/part.size ();
			double temp2 = zweit/part.size ();

			// dann substrahiere ich die und am Ende square root
			sum = Math.sqrt((temp1) - Math.pow (temp2,2));

			// gebe den Wert zurück
			 return sum/temp2;

		}

	
	/**
	 * Plots clusters in a two-dimensional space.
	 */
	public void plotClusters() {
		int canvas=800;
	    StdDraw.setCanvasSize(canvas, canvas);
	    StdDraw.setXscale(0, 15);
	    StdDraw.setYscale(0, 15);
	    StdDraw.clear(new Color(0,0,0));
		Color[] colors= {new Color(255, 255, 255), new Color(128, 0, 0), new Color(128, 128, 128), 
				new Color(0, 108, 173), new Color(8, 61, 9), new Color(226, 126, 38), new Color(132, 67, 172)};
	    int color=0;
		for(List <Integer> cluster: clusters) {
			if(color>colors.length-1) color=0;
		    StdDraw.setPenColor(colors[color]);
		    StdDraw.setPenRadius(0.02);
		    for(int i: cluster) {
		    	StdDraw.point(G.getCoordinates()[i][0], G.getCoordinates()[i][1]);
		    }
		    color++;
	    }
	    StdDraw.show();
	}
	

    public static void main(String[] args) {
		// FOR TESTING
    }
}

