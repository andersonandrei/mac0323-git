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

/*
 * 
 * 	uniform(double a, double b)
		Returns a random real number uniformly in [a, b).
		* 
		* 
	setSeed(long s)
		Sets the seed of the pseudorandom number generator.
		* 
		* 
	        In in = new In(filename);
        int n = in.readInt();
        * 
 * 
 * 
 * 
 * */

public class PercolationStats {
	private double[] exp;
	private Percolation perc;
	
	public PercolationStats(){}
	
	// perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
		int x, soma, merda, aux = 0;
		double mean, stddev, confidenceLow, confidenceHigh;
		exp = new double[trials];
		perc = new Percolation(n);
		while (aux < trials) {
			while(!perc.percolates()){
				perc.open(2,3);
			}
			exp[aux] = (perc.numberOfOpenSites())/n;
			aux++;
			perc = new Percolation(n);
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
		return 0.0;
	}
    
    
    // high endpoint of 95% confidence interval     
    public double confidenceHigh()  {
		return 1.0;
	}
	
	public static void main(String[] args) {}
	
    
    
}
