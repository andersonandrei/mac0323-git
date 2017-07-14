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
//import java.util.BinaryStdIn;
//import java.util.BinaryStdOut;
import java.lang.StringBuilder;
import edu.princeton.cs.algs4.MinPQ;

public class CircularSuffixArray {
	private int size;
	private String s;
	private String[] originalSufixes;
	public String[] sortedSufixes;

	// circular suffix array of s
   	public CircularSuffixArray(String s) {
   		int aux;
   		this.s = s;
   		this.size = s.length();
   		originalSufixes = new String[size];
   		sortedSufixes = new String[size];
   		aux = size-1;
   		StringBuilder str = new StringBuilder();
   		String newString;
   		int k = 0;
   		originalSufixes[0] = s;
   		for(int i = 0; i < size; i++) {
   			str = new StringBuilder();
   			k = 0;
   			for (int j = i+1; j < size; j++) {
   				str.append(s.charAt(j));
   			}
   			while(k <= i) {
   				str.append(s.charAt(k));
   				k++;	
   			}
   			if(i < size-1)
   				originalSufixes[i+1] = str.toString();
   		}

   		MinPQ<String> st = new MinPQ<String>();
   		for (int i = 0; i < size; i++){
			st.insert(originalSufixes[i]);
		}
		for (int i = 0; !st.isEmpty(); i++) {
			sortedSufixes[i] = st.delMin();
		}

   	}

   	// length of s
  	public int length() {
  		return s.length();
  	}
   	
   	// returns index of ith sorted suffix
   	//Search index ith String of SortedSufixes at OriginalSufixes
   	public int index(int i) {
   		for (int j = 0; j < size; j++){
   			if(originalSufixes[j].equals(sortedSufixes[i]))
   				return j;
   		}
   		return -1;
   	}

	// unit testing (not graded)
	public static void main(String[] args) {
		String str = "ABRACADABRA!";
		CircularSuffixArray csa = new CircularSuffixArray(str);
	}
}