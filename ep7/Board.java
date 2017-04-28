/* 
To implement the A* algorithm, you must use the MinPQ data type from algs4.jar for the priority queue.

Corner cases.  The constructor should throw a java.lang.IllegalArgumentException if the initial board 
is not solvable and a java.lang.NullPointerException if the initial board is null.
*/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.NullPointerException;
    


public class Board {
    private int[][] board;
    private int[][] goal;
    private int size;
    private int tam;
    private MinPQ<Board> queue;


    //construct a board from an N-by-N array of tiles (where tiles[i][j] = tile at row i, column j)
    public Board(int[][] tiles) {
        int i, j;
        int tam = tiles.length;
        StdOut.println("Tam criar ----" + tam);
        int size = tam * tam;
        StdOut.println("size criar ----" + size);
        board = new int[tam][tam];
        goal = new int[tam][tam];
        for (i = 0; i < tam; i++){
            for (j = 0; j < tam; j++){
                board[i][j] = tiles[i][j];
                goal[i][j] = tileExp(i,j);
            }
        }

        StdOut.println("-----Board criardo: ");
        StdOut.println("Tam criar ----" + tam);
        imprimeTabuleiro(board);
        imprimeTabuleiro(goal);
        
    }

    // return tile at row i, column j (or 0 if blank)
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
        StdOut.println("size--------"+tam);
        return tam;
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
        return inv;
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
            for(int i = 0; i < tam; i++) {
                for(int j = 0; j < tam; j++) {
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
        for(int i = 0; i < tam; i++) {
            for(int j = 0; j < tam; j++) {
                if(tileAt(i,j) != tileExp(i,j)) {
                    //Se é ta diferente do que eu esperava, vou ver a distancia da posição certa do intruso.
                    sum += sumPositionExpTile(i , j, tileAt(i,j));
                }
            }
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal(){
        StdOut.println("Aquiiiiiiiii");
        StdOut.println("Size" + size());
        if (size() > 0) {
            for (int i = 0; i < tam; i++)
                for(int j = 0; i < tam; j++){
                    StdOut.println("Esperava em " + i + " " + j + " " + tileExp(i, j));
                    if (board[i][j] != tileExp(i, j))
                        return false;
                }
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
            for (int i = 0; i < tam; i++)
                for(int j = 0; i < tam; j++)
                    if (board[i][j] != aux.tileAt(i,j))
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
        MinPQ<Board> queue =  new MinPQ<Board>();
        StdOut.println("Tam antes de iterable ----" + tam);
        Board b = new Board(board);
        StdOut.println("Tam depois de iterable ----" + tam);
        int i = 0, j = 0, m = 0, n = 0;
        for(i = 0; i < tam; i++) {
            for(j = 0; j < tam; j++) {
                if(tileAt(i,j) != tileExp(i,j)) {
                    if (board[i][j] == 0) break;
                }
            }
        }

        if (i - 1 >= 0) { // [x] sobe
            changeTile(i, j, i-1, j);
            b.board = board;
            changeTile(i-1, j, i, j);
            queue.insert(b);
        }

        if (i + 1 < size()) { // [x] desce
            changeTile(i, j, i+1, j);
            b.board = board;
            changeTile(i-1, j, i, j);
            queue.insert(b);
        }

        if (j - 1 >= 0) { // <--- [x]
            changeTile(i, j, i, j-1);
            b.board = board;
            changeTile(i, j-1, i, j);
            queue.insert(b);
        }

        if (j + 1 < size()) { // [x] --->
            changeTile(i, j, i, j+1);
            b.board = board;
            changeTile(i, j+1, i, j);
            queue.insert(b);
        }

        return queue;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        String text = "";
        for(int i = 0; i < tam; i++) {
            for(int j = 0; j < tam; j++) {
                text += (" " + tileAt(i, j));
            }
            text += "\n";
        }
        return text;
    }

    public void imprimeTabuleiro(int[][] board) {
        StdOut.println("Imprimindo tabuleiro");
        for(int i = 0; i < tam; i++) {
            for(int j = 0; j < tam; j++) {
                StdOut.print(" " + board[i][j]);
            }
            StdOut.println("");
        }
    }

    // unit testing (required)
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

        //inversions, findLineWhite, hamming, manhattan,isGoal,isSolvable
        StdOut.println("Testes");

        StdOut.println("Inversions: " + puzzle.inversions());
        StdOut.println("White line: " + puzzle.findLineWhite());
        StdOut.println("Hamming: " + puzzle.hamming());
        StdOut.println("Manhattan: " + puzzle.manhattan());
        StdOut.println("IsGoal: " + puzzle.isGoal());
        StdOut.println("IsSolvable: " + puzzle.isSolvable());

    }
}