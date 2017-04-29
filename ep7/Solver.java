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
import edu.princeton.cs.algs4.Stack;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.lang.NullPointerException;

public class Solver {
	private MinPQ<Board> open = new MinPQ<Board>();
	private MinPQ<Board> cloesed = new MinPQ<Board>();	
	private Stack<Board> solution;	
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
    	current = new Board(initial.board);
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
    		known.insert(current);
    		neighbors = current.neighbors();
    		for (Board next : neighbors) {
    			StdOut.println("dentro do for --- tab ---");
    			next.imprimeTabuleiro();
    			StdOut.println("Manhatan ---" + next.manhattan());
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
    		StdOut.println("Pra ele o menor é --------menooooooooor");
    		current.imprimeTabuleiro();	

    		
    	}
    	moves = current.moves;

    }

    public boolean contains(Board b) {
    	StdOut.println("No contains -renho tudo isso ai-------");
    	for (Board x : known) {
    		x.imprimeTabuleiro();
    	}
    	boolean achou = false;
    	StdOut.println("Caso a caso-------");
    	for (Board x : known) {
    		StdOut.println("x --------");
    		x.imprimeTabuleiro();
    		boolean ok = true;
    		for (int i = 0; i < b.tam && ok; i++) {
    			for (int j = 0; j < b.tam && ok; j++){
    				StdOut.println("Comparando :  " + b.board[i][j] + "------ " + x.board[i][j]);
    				if(b.board[i][j] != x.board[i][j]) {
    					ok = false;
    					StdOut.println("x não é igual------");
    				}
    			}
    		}
    		if (ok == true) return true;
    	}
    	StdOut.println("olha o q retorna ----" + achou);
    	return achou;
    } 

    // min number of moves to solve initial board
    public int moves() { 
    	return 0;
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
        StdOut.println("antes de chamar");
        Solver s = new Solver(puzzle);
        StdOut.println("chamou");
        Iterable<Board> sol = s.solution();
        Board tmp;
        /*while (!s.solution.isEmpty()) {
        	StdOut.println("dempilhando solução");
        	tmp = s.solution.pop();
        	tmp.imprimeTabuleiro();
        }
        
    	for (Board x : s.solution()) {
    		x.imprimeTabuleiro();
    	}
    	*/

    	for (Board x : sol) {
    		x.imprimeTabuleiro();
    	}
    	
    	return;
    }
}