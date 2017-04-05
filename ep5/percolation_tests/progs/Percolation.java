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

public class Percolation {
	private int map [][];
	private int root [][];
	
	// create n-by-n grid, with all sites initially blocked
	public Percolation(int n) {
		int i, j;
		map = new int[n][n];
		root = new int[n][n];
		for (i = 0; i < n; i++) {
			for (j = 0 ; j < n; j++) {
				map[i][j] = -1;
				root[i][j] = i;
			}
		}
	}
	
	// open the site (row, col) if it is not open already
    public void open(int row, int col) {
		map[row][col] = 0;
		achaRoot(row,col);
	}
    
    // is the site (row, col) open?    
    public boolean isOpen(int row, int col)  {
		return map[row][col] == 0;
	}
    
    // is the site (row, col) full?
    public boolean isFull(int row, int col)  {
		return map[row][col] == 1;
	}
    
    // number of open sites
    public int numberOfOpenSites() {
		int i, j, cont = 0;
		map = new int[n][n];
		for (i = 0; i < n; i++)
			for (j = 0 ; j < n; j++)
				if (map[i][j] = 0)
					cont++;
		return cont;
	}
    
    // does the system percolate?
    public boolean percolates() {
		for (int j = 0; j < n; j++)
			if (root[n-1][j] == 0) 
				return true;
		return false;
	}
    
    public class achaRoot(int row, int cow) {
		if (row-1 >= 0 && col-1 >= 0) {
			if (root[row-1][col-1] < root[row][col]) 
				root[row][col] = root[row-1][col-1];
		}
		else if (row-1 >= 0 && col >= 0) {
			if (root[row-1][col] < root[row][col]) 
				root[row][col] = root[row-1][col];
		}
		else if (row-1 >= 0 && col+1 < n) {
			if (root[row-1][col+1] < root[row][col]) 
				root[row][col] = root[row-1][col+1];
		}
		else if (row >= 0 && col-1 >= 0) {
			if (root[row][col-1] < root[row][col]) 
				root[row][col] = root[row][col-1];
		}
		else if (row >= 0 && col+1 < 0) {
			if (root[row][col+1] < root[row][col]) 
				root[row][col] = root[row][col+1];
		}
		else if (row+1 < n && col-1 >= 0) {
			if (root[row+1][col-1] < root[row][col]) 
				root[row][col] = root[row+1][col-1];
		}
		else if (row+1 < n && col >= 0) {
			if (root[row+1][col] < root[row][col]) 
				root[row][col] = root[row+1][col];
		}
		else if (row+1 < n && col+1 < n) {
			if (root[row+1][col+1] < root[row][col]) 
				root[row][col] = root[row+1][col+1];
		}
	}
    
    // unit testing (required)
    public static void main(String[] args)   {}
    
    
    
    
}
