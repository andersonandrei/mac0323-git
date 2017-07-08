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

public class MoveToFront {
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {}

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {}

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {}
}