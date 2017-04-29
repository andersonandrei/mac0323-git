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
import edu.princeton.cs.algs4.SET;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.NullPointerException;

public class Solver {
	private MinPQ<Board> open = new MinPQ<Board>();
	private MinPQ<Board> cloesed = new MinPQ<Board>();	
	private MinPQ<Board> solution;	
	private Iterable<Board> neighbors;
	private MinPQ<Board> known = new MinPQ<Board>();


	private Board current;
	private int currentScore;
	private int finalScore = 0;
	private int moves = 0;
	// find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
    	StdOut.println("Entrou no solver --------");
    	initial.imprimeTabuleiro();
    	current = initial;
    	current.previous = null;
    	StdOut.println("Antes do while");
    	while (!current.isGoal()) {
    		StdOut.println("Olhando enquanto current nao goal --------");
    		current.imprimeTabuleiro();
    		//open.insert(initial);
    	//while (!open.isEmpty()) {
    		//current = open.delMin();
    		//if (current.board.equals(initial.goal)) return ;
    		StdOut.println("Dentro  do while");
    		//known.add(current);
    		StdOut.println("Antes do neighbors");
    		neighbors = current.neighbors();
    		StdOut.println("Antes do for");
    		for (Board next : neighbors) {
    			StdOut.println("dentro do for");
    			//next = neighbors.next();
    			if (!contains(next)) { 
    				StdOut.println("Empilhou --------");
    				next.imprimeTabuleiro();	
    				open.insert(next);
    			}
    			//currentScore = next.score + next.distance;
    			//open.insert(next);
    			//if (currentScore >= next.score) {} //ignora
    			//known.add(current);
    			//next.score = currentScore;
    			//finalScore[next] = gScore[vizinho] + hestimate(vizinho,goal);
    		}
    		current = open.delMin();
    		known.insert(current);
    	}
    	moves = current.moves;

    }

    public boolean contains(Board b) {
    	boolean achou = false;
    	for (Board x : known) {
    		boolean ok = true;
    		for (int i = 0; i < b.tam && ok; i++) {
    			for (int j = 0; j < b.tam; j++){
    				if(b.board[i][j] != x.board[i][j])
    					ok = false;
    					break;
    			}
    		}
    		if (ok == true) achou = true;
    	}
    	return achou;
    } 

    // min number of moves to solve initial board
    public int moves() { 
    	return 0;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() { 
    	solution = new MinPQ<Board>();
    	while (current != null) {
    		solution.insert(current);
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
        StdOut.println(n);
        while (k < n * n) {
            i = 0;
            contI = 0;
            while (contI < n) {
                j = 0;
                contJ = 0;
                while (contJ < n) {
                    b[i][j] = in.readInt();
                    StdOut.println(b[i][j]);
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


    	for (Board x : s.solution()) {
    		x.imprimeTabuleiro();
    	}
    	return;
    }
}