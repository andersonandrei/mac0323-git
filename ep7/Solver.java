/*Corner cases.  You may assume that the constructor receives an N-by-N array containing the N2 integers between 0 and N2 − 1, 
where 0 represents the blank square. The tileAt() method should throw a java.lang.IndexOutOfBoundsException unless both i or j 
are between 0 and N − 1.

Performance requirements.  Your implementation should support all Board methods in time proportional to N² (or better) 
in the worst case, with the exception that isSolvable() may take up to N4 in the worst case. 

*/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.NullPointerException;

public class Solver {
	// find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
    	MinPQ<Board> open = new MinPQ<Board>();
    	MinPQ<Board> cloesed = new MinPQ<Board>();
    	MinPQ<Board> known = new MinPQ<Board>();

    	Board current, next;
    	int currentScore;
    	int finalScore = 0;

    	open.insert(initial);
    	while (!open.isEmpty()) {
    		current = open.delMin();
    		if (current.equals(initial.goal))return ;
    		known.insert(current);
    		while (initial.neighbors.hasNext()) {
    			next = initial.neighbors.next();
    			if (isKnown(known, next)) { return ;}//implementar
    			currentScore = next.score + next.distance;
    			if (isOpen(open, next))
    				open.insert(next);
    			else if (currentScore >= next.score) {} //ignora
    			known.insert(current);
    			next.score = currentScore;
    			//finalScore[next] = gScore[vizinho] + hestimate(vizinho,goal);
    		}	
    	}

    }

    // min number of moves to solve initial board
    public int moves() {}

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {}

    // unit testing 
    public static void main(String[] args) {}
}