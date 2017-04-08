/* Implementar essa budega aqui:

   public class Percolation {
   public Percolation(int n)                // create n-by-n grid, with all sites initially blocked
   public void open(int row, int col)       // open the site (row, col) if it is not open already
   public boolean isOpen(int row, int col)  // is the site (row, col) open?
   public boolean isFull(int row, int col)  // is the site (row, col) full?
   public int numberOfOpenSites()           // number of open sites
   public boolean percolates()              // does the system percolate?
   public static void main(String[] args)   // unit testing (required)
   }
*/

/*
 * Teremos uma matriz auxiliar que aponta a raiz de cada posição, onde todos da linha 1 serão 1 e da 2 serão dois e assim por diante.
 * Quando abre uma nova posição, ve se tem vizinhos aberto, se tiver sai caçando raiz a partir deles. 
 * 		Se tiver mais de um vizinho, faz com todos e fica com o menor valor de raiz, ou seja, a raiz mais ampla.
 * 		Se não tiver nenhum fica com o valor da sua própria posição.
 * 
 * 
 * */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut; 

public class Percolation {
	private WeightedQuickUnionUF map, mapTopo;
	private int[][] openClose;
	private int nopen;
	private int tam;
	private int inicio, fim;
	private boolean perc;
	
	// create n-by-n grid, with all sites initially blocked
	public Percolation(int n) {
		map = new WeightedQuickUnionUF(n*n+2);
		mapTopo = new WeightedQuickUnionUF(n*n+1);
		openClose = new int[n][n];
		tam = n;
		inicio = n*n;
		fim = n*n+1;
	}
	
	// open the site (row, col) if it is not open already
    public void open(int row, int col) {
		if (!isOpen(row,col)) {
			openClose[row][col] = 1;
			nopen++;
			if(row == 0) {
				map.union(convertCo(row,col) , inicio);
				mapTopo.union(convertCo(row,col) , inicio);
				//openClose[row][col] = 2;
			}
			else if (row == tam-1) {
				map.union(convertCo(row,col) , fim);
				//mapTopo.union(convertCo(row,col) , fim);
			}
			achaVizinho(row, col);
		}
	}
    
    // is the site (row, col) open?    
    public boolean isOpen(int row, int col)  {
		return openClose[row][col] > 0;
	}
    
    // is the site (row, col) full?
    public boolean isFull(int row, int col)  {
		return mapTopo.find(convertCo(row,col)) == map.find(inicio);
	}
    
    // number of open sites
    public int numberOfOpenSites() {
		return nopen;
	}
    
    // does the system percolate?
    public boolean percolates() {
		if (map.find(inicio) == map.find(fim))
			return true;
		return false;
	}
    
    private int convertCo (int row, int col) {
		return row * tam + col;
	}
    
    public void achaVizinho(int row, int col) {
		if (row-1 >= 0 && col >= 0) {
			if (isOpen(row-1,col)) {
				map.union(convertCo(row,col) ,convertCo(row-1,col));
				mapTopo.union(convertCo(row,col) ,convertCo(row-1,col));
			}
		}
		if (row >= 0 && col+1 < tam) {
			if (isOpen(row,col+1)) {
				map.union(convertCo(row,col) ,convertCo(row,col+1));
				mapTopo.union(convertCo(row,col) ,convertCo(row,col+1));
			}
		}
		if (row+1 < tam && col >= 0) {
			if (isOpen(row+1,col)){
				map.union(convertCo(row,col) ,convertCo(row+1,col));
				mapTopo.union(convertCo(row,col) ,convertCo(row+1,col));
			}
		}
		if (row >= 0 && col-1 >= 0) {
			if (isOpen(row,col-1)){
				map.union(convertCo(row,col) ,convertCo(row,col-1));
				mapTopo.union(convertCo(row,col) ,convertCo(row,col-1));
			}
		}
	}
	
	
	/*Falta fazer uma função preenche pra verificar bonitinho o que pintar e comentar os if(fin() == find() no acharoot.*/
    
    // unit testing (required)
    public static void main(String[] args)   {
		PercolationStats percStat = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		
		StdOut.println("Passou :" + args[0] + args[1]);
		StdOut.println("Média:" + percStat.mean());
		StdOut.println("Stddev:" + percStat.stddev());
		StdOut.println("Min:" + percStat.confidenceLow());
		StdOut.println("Max:" + percStat.confidenceHigh());     	
	
	
	}
    
    
    
    
}
