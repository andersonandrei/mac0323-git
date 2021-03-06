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
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.LSD;
import java.lang.StringBuilder;
import java.util.Arrays;

//import CircularSuffixArray.java;

public class BurrowsWheeler {
	private static char[] t;
	private static char[] firstColumn;
	private static int first;
	private static int[] next;
	private static String input;
	private static CircularSuffixArray csa;

	private BurrowsWheeler(String s) {
		csa = new CircularSuffixArray(s);
		t = new char[csa.length()];
		boolean equal = false;
		input = s;
		int index;
		for (int i = 0; i < csa.length(); i++) {
			index = csa.index(i);
			if (index == 0) {
				t[i] = input.charAt(csa.length()-1);
			}
			else {
				t[i] = input.charAt(index-1);
			}
		}
		for (int i = 0; i < csa.length(); i++) {
			if(t[i] == s.charAt(csa.length() - 1))
			{
				first = i;
				break;
			}
		}
	}

    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform() {
    	BinaryStdOut.write(first);
    	for(int i = 0; i < csa.length(); i++) {
    		BinaryStdOut.write(t[i]);
    		//StdOut.println(t[i]);
    	}
    	BinaryStdOut.flush();
    	return;
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {
    	for (int i = 0; i < csa.length(); i++) {
    		t[i] = input.charAt(i);
    	}
    	firstColumn = new char[csa.length()];
		for (int i = 0; i < csa.length(); i++) {
			firstColumn[i] = t[i];
		}
		Arrays.sort(firstColumn);
    	boolean[] used = new boolean[csa.length()];
    	char search;
		for(int i = 0; i < csa.length(); i++) {
			used[i] = false;
		}
		next = new int[csa.length()];
		for (int i = 0; i < csa.length(); i++) {
			next[i] = -1;
		}
		for (int i = 0; i < csa.length(); i++) {
			search = firstColumn[i];
			for (int j = 0; j < csa.length(); j++) {
				if (search == t[j] && used[j] == false) {
					next[i] = j;
					used[j] = true;
					break;
				}
			}
		}
		int cont = 0;
		int indNext = next[0];
		char c = firstColumn[indNext];
		while (cont < csa.length()) {
			BinaryStdOut.write(c);
			indNext = next[indNext];
			c = firstColumn[indNext];
			cont++;
		}
		BinaryStdOut.flush();
    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
    	//String s = "ABRACADABRA!";
    	if (args[0].equals("-")) {
    		StringBuilder str = new StringBuilder();
    		while (!BinaryStdIn.isEmpty()) { 
    			str.append(BinaryStdIn.readChar());
    		}
    		BurrowsWheeler bw = new BurrowsWheeler(str.toString());
    		bw.transform();
    	}
    	else if (args[0].equals("+")) {
    		int first;
    		StringBuilder str = new StringBuilder();
    		first = BinaryStdIn.readInt();
    		while (!BinaryStdIn.isEmpty()) {
    			str.append(BinaryStdIn.readChar());
    		}
    		BurrowsWheeler bw = new BurrowsWheeler(str.toString());
    		bw.first = first; 
    		bw.inverseTransform();
    	}
    	else {
            StdOut.println("Action '" + args[0] + "' is not valid.");
            return;
    	}
    	BinaryStdOut.close();
    }
}