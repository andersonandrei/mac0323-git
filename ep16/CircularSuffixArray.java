/*

Circular suffix array. To efficiently implement the key component in the 
Burrows-Wheeler transform, you will use a fundamental data structure known 
as the circular suffix array, which describes the abstraction of a sorted 
array of the N circular suffixes of a string of length N. As an example, 
consider the string "ABRACADABRA!" of length 12. The table below shows 
its 12 circular suffixes and the result of sorting them.

Performance requirements.   
On typical English text, your data type should use space proportional to N + R (or better); 
the constructor should take time proportional to N log N (or better) on typical English text; 
and the methods length() and index() should take constant time. 
Warning: beginning with Java 7, Update 6, the substring() method takes time and 
space proportional to the length of the substring—in other words, you cannot 
form the N circular suffixes explicitly because that would take both quadratic time and space.

*/

import java.lang.IllegalArgumentException;
import java.lang.NullPointerException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import java.lang.StringBuilder;
import edu.princeton.cs.algs4.MinPQ;
import java.util.*;

public class CircularSuffixArray {
	private int size;
	private String s;
	public String originalS;
	public int[] originalSufixes;
	public int [] sortedS;
	public Integer[] sortedSufixes;

	// circular suffix array of s
   	public CircularSuffixArray(String s) {
   		//StdOut.println("Entrou aqui");
   		int aux;
   		this.s = s;
   		this.size = s.length();
   		sortedSufixes = new Integer[size];
   		sortedS = new int[size];
   		aux = size-1;
   		int k = 0;
   		originalS = s;
   		originalSufixes = new int[size];
   		for (int i = 0; i < size; i++) {
   			originalSufixes[i] = i;
   			sortedSufixes[i] = i;
   		}
   		Arrays.sort(sortedSufixes, new CompareIndex());
   		for (int i = 0; i < size; i++) {
   			sortedS[i] = sortedSufixes[i];
   		}
   		// for (int i = 0; i < size; i++){
   		// 	StdOut.println(sortedSufixes[i]);
   		// }

   	}

   	// length of s
  	public int length() {
  		return s.length();
  	}
   	
	private char valorOf(int a) {
		return originalS.charAt(a);
	}

	public class CompareIndex implements Comparator<Integer> {
		public int compare (Integer a, Integer b) {
			if (valorOf(a) > valorOf(b)) return 1;
			else if (valorOf(a) < valorOf(b)) return -1;
			else {
				return compare ((a+1)%size, (b+1)%size);
			}
		}
	}

   	// returns index of ith sorted suffix
   	//Search index ith String of SortedSufixes at OriginalSufixes
   	public int index(int i) {
   		for (int j = 0; j < size; j++){
   		 	if(originalS.charAt(j) == i) {
   		 		return j;
   		 	}
   		}
   		return -1;
   	}

	// unit testing (not graded)
	public static void main(String[] args) {
		//String str = "ABRACADABRA!";
		StringBuilder str = new StringBuilder();
    	while (!BinaryStdIn.isEmpty()) { 
    		str.append(BinaryStdIn.readChar());
    	}
		CircularSuffixArray csa = new CircularSuffixArray(str.toString());
	}
}