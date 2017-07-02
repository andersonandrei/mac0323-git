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
	private String[] names;
	private int qntNames;
	private DirectedEdge[] cities;
	private EdgeWeightedDigraph map;
	private DijkstraSP[] dijks;
	private DijkstraSP search;
	private int[] begin;
	private int beginMF;
	private boolean[] secure;
	private Double[] dists;
	private Double[] distsMF;

	private CBSPaths (String str) {
		In in = new In(str);
		qntNames = 0;
/*		names[0] = "GRU";
		names[1] = "POA";
		names[2] = "CNF";
		names[3] = "BSB";
		names[4] = "GIG";
		names[5] = "CWB";*/

		this.n = in.readInt();
		this.m = in.readInt();
		this.k = in.readInt();
		this.cities = new DirectedEdge[m];
		this.begin = new int[k];
		names = new String[cities()];
		for (int i = 0; i < cities(); i++){
			names[i] = " ";
		}
		for (int i = 0; i < connections(); i++){
			String city1, city2;
			city1 = in.readString();
			city2 = in.readString();
			insertCity(city1);
			insertCity(city2);
			cities[i] = new DirectedEdge(findIndice(city1), findIndice(city2), in.readDouble());
		}
		StdOut.println("Dicionario: ");
		for (int i = 0; i < cities(); i++){
			StdOut.println(names[i]);
		}

		for (int i = 0; i < executives(); i++){
			begin[i] = findIndice(in.readString());
		}
		beginMF = findIndice(in.readString());
		StdOut.println("Cabou de ler");

		map = new EdgeWeightedDigraph(cities(), 0);
		for (int i = 0; i < connections(); i++){
			StdOut.println("From:" + cities[i].to());
			map.addEdge(cities[i]);
		}

		StdOut.println("Mostrando");
		StdOut.println("From:" + map.E());
		for (DirectedEdge e : map.edges()) {
			StdOut.println(e.toString());
		}

		//========================================Prints_Iniciais=

		dijks = new DijkstraSP[executives()];
		for (int i = 0; i < executives(); i++) {
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

	private void insertCity(String city) {
		StdOut.println("Inserindo: " + city);
		if (!containCity(city)){
			StdOut.println("Vai inserir: " + city);
			names[qntNames] = city;
			qntNames++;
		}
	}

	private boolean containCity(String city) {
		for(int i = 0; i < cities(); i++) {
			if (names[i].equals(city)){
				return true;
			}
		};
		return false;
	}

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

	private boolean[] securePlaces(Double[] dists, Double[] distsMF) {
		for (int i = 0; i < cities(); i++) {
			if (dists[i] < distsMF[i]) {
				secure[i] = true;
				StdOut.println("aqui: " + findName(i) + " -- dist: " + dists[i]);
			}
		}
		return secure;
	}

	private boolean hasSecurePlace() {
		for(int i = 0; i < cities(); i++)
			if(secure[i]) return true;
		return false;
	}

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
		//StdOut.println("Olha ai: " + cbs.n + " " + cbs.m + " " + cbs.k);
		if (cbs.hasSecurePlace()) cbs.printSecurePlace();
		else StdOut.println("VENHA COMIGO PARA CURITIBA");
		return;
	}
}