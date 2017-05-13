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
		if (n <= 0) 
            throw new java.lang.IllegalArgumentException();
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
			if(tam == 1){
				map.union(convertCo(row,col) , inicio);
				mapTopo.union(convertCo(row,col) , inicio);
				map.union(convertCo(row,col) , fim);				
			}
			else {
				if(row == 0) {
					map.union(convertCo(row,col) , inicio);
					mapTopo.union(convertCo(row,col) , inicio);
				}
				else if (row == tam-1) {
					map.union(convertCo(row,col) , fim);
				}
				achaVizinho(row, col);
			}
		}
	}
    
    // is the site (row, col) open?    
    public boolean isOpen(int row, int col)  {
		return openClose[row][col] > 0;
	}
    
    // is the site (row, col) full?
    public boolean isFull(int row, int col)  {
		return mapTopo.find(convertCo(row,col)) == mapTopo.find(inicio);
	}
    
    // number of open sites
    public int numberOfOpenSites() {
		return nopen;
	}
    
    // does the system percolate?
    public boolean percolates() {
		if (tam <= 0) return false;
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
	
    // unit testing (required)
    public static void main(String[] args)   {
		PercolationStats percStat = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		StdOut.println("Example values after creating PercolationStats (" 
		+ Integer.parseInt(args[0]) + ", " + Integer.parseInt(args[1]) + ")");		
		StdOut.println("mean(): " + percStat.mean());
		StdOut.println("stddev: " + percStat.stddev());
		StdOut.println("confidenceLow(): " + percStat.confidenceLow());
		StdOut.println("confidenceHigh(): " + percStat.confidenceHigh());     	

	
	}
    
    
    
    
}
