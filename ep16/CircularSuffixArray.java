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
import java.lang.IndexOutOfBoundsException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import java.lang.StringBuilder;
import edu.princeton.cs.algs4.MinPQ;
import java.util.*;

public class CircularSuffixArray {
	private static int size;
	private static String s;
	public static String originalS;
	public static int[] originalSufixes;
	public static int [] sortedS;
	public static Integer[] sortedSufixes;

	// circular suffix array of s
   	public CircularSuffixArray(String s) {
   		if (s == null) 
   			throw new java.lang.NullPointerException();
   		int aux;
   		this.s = s;
   		this.size = s.length();
   		sortedSufixes = new Integer[size];
   		sortedS = new int[size];
   		aux = size-1;
   		int k = 0;
   		originalS = s;
   		//StdOut.println("Original:" + originalS);
   		originalSufixes = new int[size];
   		for (int i = 0; i < size; i++) {
   			originalSufixes[i] = i;
   			sortedSufixes[i] = i;
   		}
   		Arrays.sort(sortedSufixes, new CompareIndex());
   		
   		for (int i = 0; i < size; i++) {
   			sortedS[i] = sortedSufixes[i];
   		}
   	}

   	// length of s
  	public static int length() {
  		return s.length();
  	}
   	
	private static char valorOf(int a) {
		return originalS.charAt(a);
	}

	public class CompareIndex implements Comparator<Integer> {
		public int compare (Integer a, Integer b) {
			// if (valorOf(a) > valorOf(b)) return 1;
			// else if (valorOf(a) < valorOf(b)) return -1;
			// else {
			// 	for (int i = 0; i < size; i++) {
			// 		if()
			// 	}
			// 	return compare ((a+1)%size, (b+1)%size);
			// }
			for (int i = 0; i < size; i++) {
    			if(valorOf(a) > valorOf(b))
       				return 1;
    			else if(valorOf(a) < valorOf(b))
       				return -1;
    			a = (a + 1)%size;
    			b = (b + 1)%size;
			}
			return 0;

		}
	}

   	// returns index of ith sorted suffix
   	//Search index ith String of SortedSufixes at OriginalSufixes
   	public static int index(int i) {
   		if (i < 0 || i > size) 
   			throw new java.lang.IndexOutOfBoundsException();
   		// for (int j = 0; j < size; j++){
   		//  	if(originalS.charAt(j) == i) {
   		//  		return j;
   		//  	}
   		// }
   		// return -1;
   		return sortedSufixes[i];
   	}

   	public static String generateS(int x) {
		StringBuilder str = new StringBuilder((char)x);
		int cont = 0;
		while (cont < size) {
			str.append(originalS.charAt((x+cont) % size));
			cont++;
		}
		return str.toString();
	}

	// unit testing (not graded)
	public static void main(String[] args) {
		StringBuilder str = new StringBuilder();
   		while (!BinaryStdIn.isEmpty()) {
   			str.append(BinaryStdIn.readChar());
    	}
		CircularSuffixArray csa = new CircularSuffixArray(str.toString());
		if(args.length == 1 && args[0].equals("v")) {
			for (int i = 0; i < size; i++) {
				StdOut.println(generateS(sortedSufixes[i]));
			}
		}
		return;

	}
}