/*
Burrows-Wheeler transform. The goal of the Burrows-Wheeler transform is not to 
compress a message, but rather to transform it into a form that is more amenable 
to compression. The transform rearranges the characters in the input so that 
there are lots of clusters with repeated characters, but in such a way that 
it is still possible to recover the original input. It relies on the following 
intuition: if you see the letters hen in English text, then most of the time 
the letter preceding it is t or w. If you could somehow group all such preceding 
letters together (mostly t's and some w's), then you would have an easy opportunity 
for data compression.

Performance requirements.   
The running time of your Burrows-Wheeler transform should be proportional to 
	N + R (or better) in the worst case, 
	excluding the time to construct the circular suffix array. 
The running time of your Burrows-Wheeler inverse transform should be proportional to 
	N + R (or better) in the worst case. 
The amount of memory used by both the Burrows-Wheeler transform and inverse transform 
	should be proportional to N + R (or better) in the worst case.
*/
import java.lang.IllegalArgumentException;
import java.lang.NullPointerException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
//import edu.princiton.cs.algs4.BinaryStdIn;
//import edu.princiton.cs.algs4.BinaryStdOut;
//import CircularSuffixArray.java;


public class BurrowsWheeler {
	private char[] t;
	private int first;
	private int[] next;
	private CircularSuffixArray csa;

	private BurrowsWheeler(String s) {
		csa = new CircularSuffixArray(s);
		t = new char[csa.length()];
		next = new int[csa.length()];
		
		for (int i = 0; i < csa.length(); i++) {
			if(csa.sortedSufixes[i].equals(s)) {
				first = i;
				break;
			}
		}
		StdOut.println("Index : " + first);

		for (int i = 0; i < csa.length(); i++) {
			t[i] = csa.sortedSufixes[i].charAt(csa.length()-1);
		}
		
		next[0] = first;
		for (int i = 1; i < csa.length(); i++) {
			next[i] = -1;
		}
		int cont = 0;
		char search;
		boolean[] used = new boolean[csa.length()];
		int ind;
		for(int i = 1; i < csa.length(); i++) {
			used[i] = false;
		}
		/*StdOut.println("Used[0]: " + used[0]);
		StdOut.println("Next[0]: " + next[0]);
		for(int i = 0; i < csa.length(); i++) {
			search = csa.sortedSufixes[i].charAt(csa.length()-1);
			for (int j = 0; j< csa.length(); j++) {
				ind = csa.index(j);
				StdOut.println("Search: " + search);
				StdOut.println("Used: " + ind);
				if ((csa.sortedSufixes[j].charAt(0) == search) && (used[ind] == false)) {
					next[i] = j;
					used[ind] = true;
					break;
				}
			}
		}

		*/
		int k = first;
		search = csa.sortedSufixes[k].charAt(csa.length()-1);
		while (!isFull(next)) {
			StdOut.println("Search: " + search);
			for (int j = 0; j < csa.length(); j++) {
				StdOut.println("Comparando : " + csa.sortedSufixes[j].charAt(0));
				if (csa.sortedSufixes[j].charAt(0) == search && used[j] == false) {
					StdOut.println("Achou em : " + j);
					next[j] = k;
					used[j] = true;
					k = j;
					search = csa.sortedSufixes[k].charAt(csa.length()-1);
					StdOut.println("Search: dentro : " + search);
					break;
				}
				if(used[j] == true) {
					search = csa.sortedSufixes[k].charAt(csa.length()-1);
				}
			}
		}

		StdOut.println("Next");
		for (int i = 0; i < csa.length(); i++){
			StdOut.println(next[i]);
		}
	}
    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform() {}

    private boolean isFull(int[] next) {
    	for(int i = 0; i < csa.length(); i++) {
    		if (next[i] == -1)
    			return false;
    	}
    	return true;
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {}

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
    	String s = "ABRACADABRA!";
    	BurrowsWheeler bw = new BurrowsWheeler(s);
    }
}