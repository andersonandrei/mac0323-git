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

public class MoveToFront {
	private static int ascii[];
	private static int R = 256;

	private static void moveToFront(int k) {
		int aux = ascii[k];
		for (int i = k - 1; i > -1; i--) {
			ascii[i+1] = ascii[i];
    	}
    	ascii[0] = aux;
	}


    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
    	char ch;
    	while (!BinaryStdIn.isEmpty()) { 
       		ch = BinaryStdIn.readChar();
       		for (int i = 0; i < R; i++) {
       			if(ch == ascii[i]) {
       				BinaryStdOut.write((char)i);
       				moveToFront(i);
       				break;
       			}
       		}
   		}
    	BinaryStdOut.flush();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
    	int ind;
    	while (!BinaryStdIn.isEmpty()) {
       		ind = (int) BinaryStdIn.readChar();
       		BinaryStdOut.write((char)ascii[ind]);
       		moveToFront(ind);
   		}
    	BinaryStdOut.flush();
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
    	ascii = new int[R];
    	for(int i = 0; i < R; i++){
    		ascii[i] = i;
    	}
    	if (args[0].equals("-")) {
    		encode();
    	}
    	else if (args[0].equals("+")) {
    		decode();
    	}
    	else {
            StdOut.println("Action '" + args[0] + "' is not valid.");
            return;
    	}
    	BinaryStdOut.close();
    }
}