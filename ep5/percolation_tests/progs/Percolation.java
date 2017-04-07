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
	private WeightedQuickUnionUF map;
	private int[][] openClose;
	private int nopen;
	private int tam;
	
	// create n-by-n grid, with all sites initially blocked
	public Percolation(int n) {
		map = new WeightedQuickUnionUF(n*n);
		openClose = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				openClose[i][j] = 0;
		nopen = 0;
		tam = n;
	}
	
	// open the site (row, col) if it is not open already
    public void open(int row, int col) {
		openClose[row][col] = 1;
		nopen++;
		if(row == 0) openClose[row][col] = 2;
		achaRoot(row, col);
		//Marca na matriz que abre que ta aberto,coma no open, olha nas adjascentes se tem algum aberto, 
		//se tem manda dar o connetc .
	}
    
    // is the site (row, col) open?    
    public boolean isOpen(int row, int col)  {
		return openClose[row][col] > 0;
	}
    
    // is the site (row, col) full?
    public boolean isFull(int row, int col)  {
		return openClose[row][col] == 2;
	}
    
    // number of open sites
    public int numberOfOpenSites() {
		return nopen;
	}
    
    // does the system percolate?
    public boolean percolates() {
		int i, j;
		for (i = 0; i < tam; i ++)
			for (j = 0; j < tam; j++)
				if (map.find(convertCo(0,i)) == map.find(convertCo(tam-1,j)))
					return true;
		return false;
	}
	
	//Mini percolations
	public boolean percolates(int row, int col) {
		int i, j;
		for (i = 0; i < tam; i ++)
			if (map.find(convertCo(0,i)) == map.find(convertCo(row,col)))
				return true;
		return false;
	}
    
    private int convertCo (int row, int col) {
		return row * tam + col;
	}
    
    public void achaRoot(int row, int col) {
		if (row-1 >= 0 && col >= 0) {
			if (isOpen(row-1,col)) {
				map.union(convertCo(row,col) ,convertCo(row-1,col));
				if (percolates(row,col)) {
					pintaConec(row,col);
				}
			}
		}
		if (row >= 0 && col+1 < tam) {
			if (isOpen(row,col+1)) {
				map.union(convertCo(row,col) ,convertCo(row,col+1));
				if (percolates(row,col)) {
					pintaConec(row,col);
				}
			}
		}
		if (row+1 < tam && col >= 0) {
			if (isOpen(row+1,col)){
				map.union(convertCo(row,col) ,convertCo(row+1,col));
				if (percolates(row,col)) {
					pintaConec(row,col);
				}
			}
		}
		if (row >= 0 && col-1 >= 0) {
			if (isOpen(row,col-1)){
				map.union(convertCo(row,col) ,convertCo(row,col-1));
				if (percolates(row,col)) {
					pintaConec(row,col);
				}
			}
		}
	}
	
	public void pintaConec(int row, int col) {
		for (int i = 0; i < tam; i++)
			for (int j = 0; j < tam; j++)
				if(map.find(convertCo(i,j)) == map.find(convertCo(row,col)))
					openClose[i][j] = 2;
	}
	
	/*Falta fazer uma função preenche pra verificar bonitinho o que pintar e comentar os if(fin() == find() no acharoot.*/
    
    // unit testing (required)
    public static void main(String[] args)   {}
    
    
    
    
}
