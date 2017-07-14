/*

Move-to-front encoding and decoding. The main idea of move-to-front encoding is 
to maintain an ordered sequence of the characters in the alphabet, and 
repeatedly read in a character from the input message, print out the position 
in which that character appears, and move that character to the front of 
the sequence. As a simple example, if the initial ordering over a 6-character 
alphabet is A B C D E F, and we want to encode the input CAAABCCCACCF, 
then we would update the move-to-front sequences as follows:

Performance requirements.   
The running time of both move-to-front encoding and decoding should 
be proportional to R N (or better) in the worst case and 
proportional to N + R (or better) in practice on inputs that 
arise when compressing typical English text, where N is the number 
of characters in the input and R is the alphabet size. 
The amount of memory used by both move-to-front encoding 
and decoding should be proportional to N + R (or better) in the worst case.

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

public class MoveToFrontOld {

	private static int ascii[];
	private static int R = 255;
	private static char[] input;
	private static int[] seq;
	private static int size;

	public MoveToFrontOld(String str) {
		ascii = new int[R];
		for (int i = 0; i < R; i++) {
			ascii[i] = i;
		}
		input = new char[str.length()];
		for (int i = 0; i < str.length(); i++) {
			input[i] = str.charAt(i);
			//StdOut.println(input[i]);
		}
		// ascii[0] = 65;
		// ascii[1] = 66;
		// ascii[2] = 67;
		// ascii[3] = 68;
		// ascii[4] = 69;
	}

	public MoveToFrontOld() {
		ascii = new int[R];
		for (int i = 0; i < R; i++) {
			ascii[i] = i;
		}
		size = 1;
		// ascii[0] = 65;
		// ascii[1] = 66;
		// ascii[2] = 67;
		// ascii[3] = 68;
		// ascii[4] = 69;
	}

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
    	//String abra = "BACA";
    	int[] sorted = new int[R];
    	for (int i = 0; i < R; i++) {
    		sorted[i] = ascii[i];
    	}
    	int search;
    	int aux;
    	for (int i = 0; i < size; i++) {
    		search = input[i];
    		for (int j = 0; j < R; j++) {
    			if (sorted[j] == search && j != 0) {
    				StdOut.println(j);
	    				for (int k = j; k > 0; k--) {
	    					aux = sorted[k];
	    					sorted[k] = sorted[k-1];
	    					sorted[k-1] = aux;
	    				}
	    		}
    		}
    	}

    	// StdOut.println("Arrumando saporra ----");
    	// for (int i = 0; i < R; i++) {
    	// 	StdOut.println((char)sorted[i]);
    	// }
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
    	int search;
    	int aux;
    	search = input[0];
    	for (int j = 0; j < R; j++) {
    		if (ascii[j] == search) {
    			BinaryStdOut.write((char)ascii[j]);
    			if(j != 0) {
	    			for (int k = j; k > 0; k--) {
	    				aux = ascii[k];
	    				ascii[k] = ascii[k-1];
	    				ascii[k-1] = aux;
	    			}
    			}
    		}
    	}
    }

    public static void decode(char c) {
    	int search;
    	int aux;
    	input[0] = c;
    	search = input[0];
    	for (int j = 0; j < R; j++) {
    		StdOut.println("Procurando por: " + search);
    		if (ascii[j] == search) {
    			StdOut.println((char)ascii[j]);
    			if(j != 0) {
	    			for (int k = j; k > 0; k--) {
	    				aux = ascii[k];
	    				ascii[k] = ascii[k-1];
	    				ascii[k-1] = aux;
	    			}
    			}
    		}
    	}
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
    	if (args[0].equals("-")) {
    		StringBuilder str = new StringBuilder();
    		int size = 0;
    		while (!StdIn.isEmpty()) { 
    			str.append(StdIn.readChar());
    			size++;
    		}
    		MoveToFrontOld mvtf = new MoveToFrontOld(str.toString());
    		mvtf.size = size;
    		mvtf.encode();
    	}
    	else if (args[0].equals("+")) {
    		int c;
    		MoveToFrontOld mvtf = new MoveToFrontOld();
    		if(!StdIn.isEmpty()) {
    			c = StdIn.readInt();
    			input = new char[1];
    			mvtf.input[0] = (char)c;
    			mvtf.decode();
    		}
    		while (!StdIn.isEmpty()) { 
    			c = StdIn.readInt();
    			StdOut.println("Leu " + c);
    			mvtf.decode((char)c);
    		}
    	}
    	//BinaryStdOut.close();
    }
}