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
    private int[][] goal;
    private int size;
    private MinPQ<int> queue;


    //construct a board from an N-by-N array of tiles (where tiles[i][j] = tile at row i, column j)
    public Board(int[][] tiles) {
        int i, j;
        for (int i = 0; i < n; i++){
            for (j = 0; j < n; j++){
                board[i][j] = tiles[i][j];
                goal[i][j] = tileExp(i,j);
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

    public int sumPositionExpTile (int i, int j, int x) {
        int m = x / size();
        int n = x % size();
        return (m - i) + (n - j);
    }

    public int inversions() {
        int inv = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                int item = board[i][j];

                for (int m = i; m < size(); m++) {
                    for (int n = 0; n < size(); n++) {
                        if (item > board[m][n]  && (m > i || n > j))
                            inv++;
                    }
                }
            }
        }
    }

    public int findLineWhite() {
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (board[i][j] == 0)
                    return i;
            }
        }
        return 0;
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
    public int manhattan(){
        int sum = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(tileAt(i,j) != tileExp(i,j)) {
                    sum += sumPositionExpTile(i , j, tile(i,j));
                }
            }
        }
        return sum;
    }

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
    public boolean isSolvable() {
        //Vou ver se é par ou impar e cada uma das confdições dentro dessas duas primeiras
        if (size() == 0) return false;
        int inv = inversions();
        int white = findLineWhite();
        if (size() % 2 == 0) {
            if ((inv + white) % 2 == 0) return true;
            return false;
        }
        else {
            if (inv % 2 == 0) return true;
            return false;
        }
    }

    // does this board equal y?
    public boolean equals(Object y){
        Board aux = (Board)y;
        if (size() > 0) {
            for (int i = 0; i < n; i++)
                for(int j = 0; i < n; j++)
                    if (board[i][j] != aux[i][j])
                        return false;
            return true;
        }
        return false;
    }

    public void changeTile(int i, int j, int m, int n) {
        int aux = board[i][j];
        board[i][j] = board[m][n];
        board[m][n] = aux;
        return;
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        MinPQ<int> queue =  new MinPQ<int>();
        Board b;
        int i, j, m, n;
        for(i = 0; i < n; i++) {
            for(j = 0; j < n; j++) {
                if(tileAt(i,j) != tileExp(i,j)) {
                    if (board[i][j] == 0) break;
                }
            }
        }
        //só pra guardar
        wi = i;
        wj = j;

        if (i - 1 >= 0) { // [x] sobe
            changeTile(i, j, i-1, j);
            b = board;
            changeTile(i-1, j, i, j);
            queue.insert(b);
        }

        if (i + 1 < size()) { // [x] desce
            changeTile(i, j, i+1, j);
            b = board;
            changeTile(i-1, j, i, j);
            queue.insert(b);
        }

        if (j - 1 >= 0) { // <--- [x]
            changeTile(i, j, i, j-1);
            b = board;
            changeTile(i, j-1, i, j);
            queue.insert(b);
        }

        if (j + 1 < size()) { // [x] --->
            changeTile(i, j, i, j+1);
            b = board;
            changeTile(i, j+1, i, j);
            queue.insert(b);
        }

        return queue;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                StdOut.print(" " + tileAt(i, j));
            }
            StdOut.println();
        }
    }

    public static void main(String[] args) // unit testing (required)
}