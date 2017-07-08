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

public class BurrowsWheeler {
    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform()

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform()

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args)
}