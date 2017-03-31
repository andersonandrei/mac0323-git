// This class provides methods for reading strings and numbers from standard
// input, file input, URLs, and sockets.
// https://www.ime.usp.br/~pf/sedgewick-wayne/stdlib/documentation/index.html
import edu.princeton.cs.algs4.In;

// This class provides methods for printing strings and numbers to standard output.
// https://www.ime.usp.br/~pf/sedgewick-wayne/stdlib/documentation/index.html
// http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdOut.html
import edu.princeton.cs.algs4.StdOut; 


public class LinkedList {
    
    private Node first;
    private Node last;
    private int n;
    
    private class Node {
		public String value;
		public Node next;
		
		private Node () {
			value = "";
			next = null;
		}
		
		private Node (String str) {
			value = str;
			next = null;
		}
	}
    
    private void add (String s) {
		Node oldlast = last;
		last = new Node();
		last.value = s;
		last.next = oldlast;
		oldlast.next = last;
		n++;
	}
	
	private int size() {
		return n;
	}
	
	private boolean isStackEmpty(){
		return n == 0;
	}
	
	private void removeFirst () {
		first.next = (first.next).next;
		n--;
	}
	
	public LinkedList() {
		n = 0;
		first = new Node();
		last = first;
	}
}
