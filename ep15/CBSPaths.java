import java.lang.IllegalArgumentException;
import java.lang.NullPointerException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.DijkstraSP;
import edu.princeton.cs.algs4.MinPQ;

public class CBSPaths {
	private int n, m, k;
	private String[] names; //name of the cities
	private int qntNames; //Quantity of cities
	private DirectedEdge[] cities;
	private EdgeWeightedDigraph map; //map with the DirectEdges cities
	private DijkstraSP[] dijks;
	private DijkstraSP search; //Min dists vector to MF
	private int[] begin; //Cities that the executives begin
	private int beginMF; //city that MF begin
	private boolean[] secure;
	private Double[] dists;
	private Double[] distsMF;

	private CBSPaths (String str) {
		In in = new In(str);
		this.n = in.readInt();
		this.m = in.readInt();
		this.k = in.readInt();
		this.cities = new DirectedEdge[m];
		this.begin = new int[k];
		names = new String[cities()];
		qntNames = 0;
		for (int i = 0; i < cities(); i++){
			names[i] = " ";
		}
		for (int i = 0; i < connections(); i++){ //Construct connections
			String city1, city2;
			city1 = in.readString();
			city2 = in.readString();
			insertCity(city1);
			insertCity(city2);
			cities[i] = new DirectedEdge(findIndice(city1), findIndice(city2), in.readDouble());
		}
		for (int i = 0; i < executives(); i++){ //Where the executives begin
			begin[i] = findIndice(in.readString());
		}
		beginMF = findIndice(in.readString());
		map = new EdgeWeightedDigraph(cities(), 0);
		for (int i = 0; i < connections(); i++){ //Construct map of EdgeWeightedDigraph
			map.addEdge(cities[i]);
		}
		dijks = new DijkstraSP[executives()];
		for (int i = 0; i < executives(); i++) { //Construct DijkstraSP[] to take the min(s) dist(s)
			dijks[i] = new DijkstraSP(map, begin[i]);
		}
		search = new DijkstraSP(map, beginMF);
		dists = new Double[cities()];
		distsMF = new Double[cities()];
		for (int i = 0; i < cities(); i++){
			dists[i] = 0.0;
			distsMF[i] = 0.0;
		}
		airMap(dists, distsMF);
		secure = new boolean[cities()];
		secure = securePlaces(dists, distsMF);
	}

	//Insert a city into the dictionary
	private void insertCity(String city) {
		if (!containCity(city)){
			names[qntNames] = city;
			qntNames++;
		}
	}

	//Verify if the city alredy exist
	private boolean containCity(String city) {
		for(int i = 0; i < cities(); i++) {
			if (names[i].equals(city)){
				return true;
			}
		};
		return false;
	}

	//Calculate the dists with DijkstraSP
	private void airMap(Double[] dists, Double[] distsMF) {
		for(int i = 0; i < cities(); i++){
			for (int j = 0; j < executives(); j++){
				if (dijks[j].distTo(i) > dists[i]){
					dists[i] = dijks[j].distTo(i);
				}
			}
			distsMF[i] = search.distTo(i);
		}
	}

	//Mark the secure places
	private boolean[] securePlaces(Double[] dists, Double[] distsMF) {
		for (int i = 0; i < cities(); i++) {
			if (dists[i] < distsMF[i]) {
				secure[i] = true;
			}
		}
		return secure;
	}

	//Verify if exist secure place
	private boolean hasSecurePlace() {
		for(int i = 0; i < cities(); i++)
			if(secure[i]) return true;
		return false;
	}

	//Print the secure place
	private void printSecurePlace() {
		for (int i = 0; i < cities(); i++){
			if(secure[i])
				StdOut.println(findName(i));
		}
		return;
	}

	private int cities() {
		return n;
	}

	private int connections() {
		return m;
	}

	private int executives() {
		return k;
	}

	private int findIndice (String s) {
		for (int i = 0; i < n; i++) {
			if (names[i].equals(s)) return i;
		}
		return 0;
	}

	private String findName (int s) {
		return names[s];
	}

	public static void main(String[] args) {
		CBSPaths cbs = new CBSPaths(args[0]);
		//LinearProbingHashST<String, Integer> st = new LinearProbingHashST<String, Integer>();
		MinPQ<String> st = new MinPQ<String>();
		if (cbs.hasSecurePlace()) {
			for (int i = 0; i < cbs.cities(); i++){
				if(cbs.secure[i])
					st.insert(cbs.findName(i));
					//StdOut.println(cbs.findName(i));
			}
			while (!st.isEmpty()) {
				StdOut.println(st.delMin());
			}
			//cbs.printSecurePlace();
		}
		else StdOut.println("VENHA COMIGO PARA CURITIBA");
		return;
	}
}