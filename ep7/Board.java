/* 
To implement the A* algorithm, you must use the MinPQ data type from algs4.jar for the priority queue.

Corner cases.  The constructor should throw a java.lang.IllegalArgumentException if the initial board 
is not solvable and a java.lang.NullPointerException if the initial board is null.
*/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ:

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.NullPointerException;
    


public class Board {
    private int[][] board;
    private int size;
    private MinPQ<int> queue;


    //construct a board from an N-by-N array of tiles (where tiles[i][j] = tile at row i, column j)
    public Board(int[][] tiles) {
        int i, j;
        for (int i = 0; i < n; i++){
            for (j = 0; j < n; j++){
                board[i][j] = tiles[i][j];
            }
        }
    }

    //// return tile at row i, column j (or 0 if blank)
    public int tileAt(int i, int j){
        if (board[i][j] == -1) return 0;
        return board[i][j];
    }

    //Tile expect
    public int tileExp(int i, int j){
        int t = i*size() + j + 1;
        if (t == size() * size())
            return 0;
        return t;
    }

    // board size N
    public int size() {
        return size;
    }               

    // number of tiles out of place
    public int hamming(){
        if (size() > 0) {
            int cont = 0;
            for(int i = 0; i < n; i++) {
                for(j = 0; j < n; j++) {
                    if(tileAt(i,j) != tileExp(i,j))
                        cont++; 
                }
            }
            return cont;
        }
        return 0;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){}

    // is this board the goal board?
    public boolean isGoal(){
        if (size() > 0) {
            for (int i = 0; i < n; i++)
                for(int j = 0; i < n; j++)
                    if (board[i][j] != tileExp(i, j))
                        return false;
            return true;
        }
        return false;
    }

    // is this board solvable?
    public boolean isSolvable()            
    public boolean equals(Object y)        // does this board equal y?
    public Iterable<Board> neighbors()     // all neighboring boards
    public String toString()               // string representation of this board (in the output format specified below)

    public static void main(String[] args) // unit testing (required)
}