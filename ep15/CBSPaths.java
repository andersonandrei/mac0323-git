/*
	Observações interessantes.
	Casos de busca da MF à Execs.
	Caso 1: MF tem origem atrás de uma pos de Exec.
		=> Todos os destinos possiveis dos exec são sucesso!
			PS: O exemplo do paca é esse caso .
	Caso 2: Origens nada a ver um com o outro, ai precisamos dos 
		caminhos mínimos.
*/

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
	private DijkstraSP[] dijks;
	private DijkstraSP search;
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

		//========================================Leituras=

		cbs.dijks = new DijkstraSP[k];
		for (int i = 0; i < k; i++) {
			cbs.dijks[i] = new DijkstraSP(cbs.map, cbs.begin[i]);
		}
		cbs.search = new DijkstraSP(cbs.map, cbs.beginMF);
		Double[] dists = new Double[n];
		Double[] distsMF = new Double[n];
		for (int i = 0; i < n; i++){
			dists[i] = 0.0;
			distsMF[i] = 0.0;
		}

		for(int i = 0; i < n; i++){
			for (int j = 0; j < k; j++){
				if (cbs.dijks[j].distTo(i) > dists[i]){
					dists[i] = cbs.dijks[j].distTo(i);
				}
			}
			distsMF[i] = cbs.search.distTo(i);
		}

		//comparisons
		for (int i = 0; i < n; i++) {
			if (dists[i] < distsMF[i]) {
				StdOut.println("aqui: " + cbs.findName(i) + " -- dist: " + dists[i]);
			}
		}

		return ;
	}

	/*for (int i = 1; i < n; i++) {
					//Se 
					if (e.distTo(cbs.begin[i]) == Double.POSITIVE_INIFINITY){
						secure = false;
					}
					else {

					}
				}
				if (!search.hasPathTo(e.from()))
					securePlace.enqueue(e);*/

/*

StdOut.println("Partindo inicialmente de: " + cbs.begin[0]);
		//We have to take the incidents of execs. 
		for (DirectedEdge e : cbs.map.adj(cbs.begin[0])) {
		//for (DirectedEdge e : cbs.dijks[0].pathTo(0)) {
			StdOut.println("indo de e para : " + e.to());
			for (DirectedEdge fromE = e; fromE.to() != 0 || fromE != e; fromE = cbs.cities[e.to()]){//: cbs.map.adj(e.to())) {
				StdOut.println("prox de prox : " + fromE.to());
			}
			//Take the other incidents of the others execs.
			for (int i = 1; i < k; i++){
				StdOut.println("Olhando pro k-esimo exec: " + i + " begin: " + cbs.begin[i]);
				for (DirectedEdge f : cbs.map.adj(cbs.begin[i])) {
				StdOut.println("indo de f para : " + f.to());
				//for (DirectedEdge f : cbs.dijks[i].pathTo(i)) {
					//Verify if we can go e -> f.
					if (cbs.dijks[0].distTo(f.from()) == Double.POSITIVE_INFINITY){
						dists[f.from()] = Double.POSITIVE_INFINITY;
					}
					else {
						//Check the distances point to point and change in the vector.
						if (cbs.dijks[i].distTo(f.from()) > cbs.dijks[0].distTo(f.from())){
							if (cbs.dijks[i].distTo(f.from()) > dists[f.from()]){
								dists[f.from()] = cbs.dijks[i].distTo(f.from());
							}
						}
						else {
							if (cbs.dijks[0].distTo(f.from()) > dists[f.from()]){
								dists[f.from()] = cbs.dijks[0].distTo(f.from());
							}
						}
					}
				}
				i++;
			}
		}


		StdOut.println("Bostão");
		for (int i = 0; i < n; i++){
			StdOut.println(dists[i]);
		}

*/

}