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

public class CircularSuffixArray {
	// circular suffix array of s
   	public CircularSuffixArray(String s) {}

   	// length of s
  	public int length() {}
   	
   	// returns index of ith sorted suffix
   	public int index(int i) {}

	// unit testing (not graded)
	public static void main(String[] args) {}
}