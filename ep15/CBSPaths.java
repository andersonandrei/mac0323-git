import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.lang.NullPointerException;
import edu.princeton.cs.algs4.In;
import java.lang.IllegalArgumentException;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.DijkstraSP;

public class CBSPaths {
	private int n, m, k;
	private String[] names = new String[6];
	private DirectedEdge[] cities;
	private EdgeWeightedDigraph map;
	private int[] begin;
	private int beginMF;

	private CBSPaths (int n, int m, int k) {
		this.n = n;
		this.m = m;
		this.k = k;
		names[0] = "GRU";
		names[1] = "POA";
		names[2] = "CNF";
		names[3] = "BSB";
		names[4] = "GIG";
		names[5] = "CWB";
		//names = ["GRU","POA","CNF","BSB","GIG","CWB"];
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

	private void airMesh () {

	}

	public static void main(String[] args) {
		int n, m, k;
		String filename = args[0];
        String a;
        In in = new In(filename);
		n = in.readInt();
		m = in.readInt();
		k = in.readInt();
		StdOut.println("Olha ai: " + n + " " + m + " " + k);
		CBSPaths cbs = new CBSPaths(n, m, k);
		cbs.cities = new DirectedEdge[m];
		cbs.begin = new int[k];
		for (int i = 0; i < m; i++){
			a = in.readString();
			StdOut.println("Cidade: " + cbs.findIndice(a));
			cbs.cities[i] = new DirectedEdge(cbs.findIndice(a), cbs.findIndice(in.readString()), in.readDouble());
		}
		for (int i = 0; i < k; i++){
			cbs.begin[i] = cbs.findIndice(in.readString());
		}
		cbs.beginMF = cbs.findIndice(in.readString());

		StdOut.println("Cabou de ler");

		cbs.map = new EdgeWeightedDigraph(cbs.n, 0);
		for (int i = 0; i < m; i++){
			StdOut.println("From:" + cbs.cities[i].to());
			cbs.map.addEdge(cbs.cities[i]);
		}

		StdOut.println("Mostrando");
		StdOut.println("From:" + cbs.map.E());
		for (DirectedEdge e : cbs.map.edges()) {
			StdOut.println(e.toString());
		}

		DijkstraSP search = new DijkstraSP(cbs.map, cbs.beginMF);



		return ;
	}

}