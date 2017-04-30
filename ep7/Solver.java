/* 
To implement the A* algorithm, you must use the MinPQ data type from algs4.jar for the priority queue.

Corner cases.  The constructor should throw a java.lang.IllegalArgumentException if the initial board 
is not solvable and a java.lang.NullPointerException if the initial board is null.
*/
//import test;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.LinkedList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.NullPointerException;
import java.lang.IllegalArgumentException;

public class Solver {
	private MinPQ<Board> open = new MinPQ<Board>();
	private MinPQ<Board> cloesed = new MinPQ<Board>();	
	private Stack<Board> solution;	
	private Iterable<Board> neighbors;
	private LinkedList<Board> known = new LinkedList<Board>();

	private Board current;
	private int currentScore;
	private int finalScore = 0;
	private int moves = 0;

	// find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
    	if (!initial.isSolvable()) throw new java.lang.IllegalArgumentException();
    	current = new Board(initial.board);
    	current.score = current.manhattan();
    	current.previous = null;
    	known.push(current);
    	while (!current.isGoal()) {
    		neighbors = current.neighbors();
    		for (Board next : neighbors) {
    			if (!known.contains(next)) {
    				open.insert(next);
    			}
    		}
    		current = open.delMin();
    		known.push(current);
    	}
    	moves = current.moves;
    }

    // min number of moves to solve initial board
    public int moves() { 
    	return moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() { 
    	solution = new Stack<Board>();
    	while (current != null) {
    		solution.push(current);
    		current = current.previous;
    	}
    	return solution;
    } 

    // unit testing 
    public static void main(String[] args) {
    	In in = new In(args[0]);
        int n = in.readInt();
        int i = 0;
        int j = 0;
        int k = 0;
        int contI = 0;
        int contJ = 0;
        int[][] b = new int[n][n];
        while (k < n * n) {
            i = 0;
            contI = 0;
            while (contI < n) {
                j = 0;
                contJ = 0;
                while (contJ < n) {
                    b[i][j] = in.readInt();
                    j++;
                    contJ++;
                    k++;
                }
                i++;
                contI++;
            }
        }
        for(i = 0; i < n; i++) {
            for(j = 0; j < n; j++) {
                StdOut.print(" " + b[i][j]);
            }
            StdOut.println("");
        }

        Board puzzle = new Board(b);
        Solver s = new Solver(puzzle);
        Iterable<Board> sol = s.solution();
        Board tmp;

    	for (Board x : sol) {
    		StdOut.println("---------");
    		x.imprimeTabuleiro();
    		StdOut.println("---------");
    	}
    	
    	return;
    }
}