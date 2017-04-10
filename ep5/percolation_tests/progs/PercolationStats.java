/* Implementar essas budegas

public class PercolationStats {
   public PercolationStats(int n, int trials)   // perform trials independent experiments on an n-by-n grid
   public double mean()                         // sample mean of percolation threshold
   public double stddev()                       // sample standard deviation of percolation threshold
   public double confidenceLow()                // low  endpoint of 95% confidence interval
   public double confidenceHigh()               // high endpoint of 95% confidence interval
}
*/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
	private double[] exp;
	private int tam;
	private Percolation perc;
	
	public PercolationStats(){}
	
	// perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0)
            throw new java.lang.IllegalArgumentException();
		int a, b, x, soma, merda, aux = 0;
		double mean, stddev, confidenceLow, confidenceHigh;
		tam = n;
		exp = new double[trials];
		perc = new Percolation(n);
		if (n == 1)
			perc = new Percolation(n);
		else {
			while (aux < trials) {
				while(!perc.percolates()){
					perc.open(StdRandom.uniform(n),StdRandom.uniform(n));
				}
				exp[aux] = ((double)perc.numberOfOpenSites())/(n*n);
				aux++;
				perc = new Percolation(n);
			}
		}
		mean = mean();
		stddev = stddev();
		confidenceLow = confidenceLow();
		confidenceHigh = confidenceHigh();
	}
    
    
    // sample mean of percolation threshold
    public double mean() {
		return StdStats.mean(exp);
	}
    
    
    // sample standard deviation of percolation threshold
    public double stddev() {
		return StdStats.stddev(exp);
	}
    
    
    // low  endpoint of 95% confidence interval                   
    public double confidenceLow() {
		return (mean() + (1.96 * stddev() / Math.sqrt(tam)));
	}
    
    
    // high endpoint of 95% confidence interval     
    public double confidenceHigh()  {
		return (mean() - (1.96 * stddev() / Math.sqrt(tam)));
	}
	
	public static void main(String[] args) {}
    
}
