/* Client. 
 * Write a client program Subset.java that takes a command-line integer k; 
 * reads in a sequence of strings from standard input using StdIn.readString(); 
 * and prints out exactly k of them, uniformly at random. 
 * Each item from the sequence can be printed out at most once.
 * 
 * Performance requirements:  
 * The running time of Subset must be linear in the size of the input. 
 * You may use only a constant amount of memory plus either one Deque or RandomizedQueue 
 * object of maximum size at most N. (For an extra challenge, limit the maximum size to k.)
*/

/* 0 ≤ k ≤ N, where N is the number of string on standard input.*/

import edu.princeton.cs.algs4.In; 
import edu.princeton.cs.algs4.StdIn; 
import edu.princeton.cs.algs4.StdOut; 
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class Subset {
   public static void main(String[] args) {
		int n = StdIn.readInt();
		RandomizedQueue<String> q = new RandomizedQueue<String>();
		while (!StdIn.isEmpty()){
			q.enqueue(StdIn.readString());
		}
		for (int i = 0; i < n; i++)
			StdOut.println(q.sample());
	}
}
