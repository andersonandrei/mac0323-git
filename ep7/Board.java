/*
Corner cases.  You may assume that the constructor receives an N-by-N array containing the N2 integers between 0 and N2 − 1, 
where 0 represents the blank square. 
The tileAt() method should throw a java.lang.IndexOutOfBoundsException unless both i or j 
are between 0 and N − 1.

Performance requirements.  Your implementation should support all Board methods in time proportional to N² (or better) 
in the worst case, with the exception that isSolvable() may take up to N4 in the worst case. 
*/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

import java.util.Iterator;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.lang.NullPointerException;

public class Board implements Comparable<Board> {
    public int[][] board;
    public int[][] goal;
    private int size;
    public int tam;
    public int moves; //number of moves to came here.
    public int distance; //distance for root.
    public int score; //manhattam.
    private MinPQ<Board> queue;
    public Board previous;


    //construct a board from an N-by-N array of tiles (where tiles[i][j] = tile at row i, column j)
    public Board(int[][] tiles) {
        int i, j;
        tam = tiles.length;
        size = tam * tam;
        board = new int[tam][tam];
        goal = new int[tam][tam];
        moves = 0;
        distance = 0;
        for (i = 0; i < tam; i++){
            for (j = 0; j < tam; j++){
                board[i][j] = tiles[i][j];
                goal[i][j] = tileExp(i,j);
            }
        }
    }

    // return tile at row i, column j (or 0 if blank)
    public int tileAt(int i, int j){
        if (i < 0 || j < 0 || i > tam || j > tam) 
            throw new java.lang.IndexOutOfBoundsException();
        return board[i][j];
    }

    //Tile expect
    public int tileExp(int i, int j){
        if (i < 0 || j < 0 || i > tam || j > tam) 
            throw new java.lang.IndexOutOfBoundsException();
        int t = i * size() + j + 1;
        if (t == size)
            return 0;
        return t;
    }

    // board size N
    public int size() {
        return tam;
    }    

    public int sumPositionExpTile (int i, int j, int x) {
        int m = (x - 1) / tam;
        int n = (x - 1) % tam;

        if (x == 0) {
            return 0;
        }
        return Math.abs(m-i) + Math.abs(n-j);
    }

    // calculate the number 
    public int inversions() {
        int inv = 0;
        int[] aux = new int[size];
        int x = 0;
        for (int i = 0; i < tam; i++)
            for (int j = 0; j < tam; j++){
                aux[x] = board[i][j];
                x++;
            }
        
        for (int i = 0; i < size - 1; i++){
            for (int j = i+1; j < size; j++)
                if (aux[i] > aux[j] && (aux[i] != 0 && aux[j] != 0))
                    inv++;
        }
        return inv;
    }
    
    // find the line of 0
    public int findLineWhite() {
        for (int i = 0; i < tam; i++) {
            for (int j = 0; j < tam; j++) {
                if (board[i][j] == 0)
                    return i;
            }
        }
        return 0;
    }

    // number of tiles out of place
    public int hamming(){
        int cont = 0;
        if (size > 0) {
            for(int i = 0; i < tam; i++) {
                for(int j = 0; j < tam; j++) {
                    if((tileAt(i,j) != tileExp(i,j)) && (tileAt(i,j) != 0))
                        cont++; 
                }
            }
        }
        return cont;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        int sum = 0;
        if (size > 0) {
            for(int i = 0; i < tam; i++) {
                for(int j = 0; j < tam; j++) {
                    sum += sumPositionExpTile(i , j, tileAt(i,j));
                }
            }
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal(){
        if (tam > 0) {
            for (int i = 0; i < tam; i++)
                for(int j = 0; j < tam; j++){
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
        if (tam == 0) return false;
        if (isGoal() == true) return true;
        int inv = inversions();
        int white = findLineWhite();
        if (size % 2 == 0) {
            if ((inv + white) % 2 == 0) return false;
            return true;
        }
        else {
            if (inv % 2 == 0) return true;
            return false;
        }
    }

    // does this board equal y?
    public boolean equals(Object y){
        Board aux = (Board)y;
        if (tam > 0) {
            for (int i = 0; i < tam; i++)
                for(int j = 0; j < tam; j++)
                    if (board[i][j] != aux.tileAt(i,j))
                        return false;
            return true;
        }
        return false;
    }

    // change a tile
    public void changeTile(Board b, int i, int j, int m, int n) {
        int aux = b.board[i][j];
        b.board[i][j] = b.board[m][n];
        b.board[m][n] = aux;
        return;
    }

    // copy a table
    public void copy (int[][] orig, int[][] dest) {
        for(int i = 0; i < tam - 1; i++) {
            for(int j = 0; j < tam - 1; j++) {
                dest[i][j] = orig[i][j];
            }
        }
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        MinPQ<Board> queue =  new MinPQ<Board>(new priority());
        Board b;
        int i = 0, j = 0, m = 0, n = 0;
        //Taking the coordenates of 0 
        for(i = 0; i < tam; i++) {
            for(j = 0; j < tam; j++) {
                if (board[i][j] == 0) {
                    m = i;
                    n = j;
                }
            }
        }
        i = m;
        j = n;
        if (i - 1 >= 0 ) { // [x] get up
            b = new Board(board);
            copy(board, b.board);
            changeTile(b, i, j, i-1, j);
            b.score = manhattan();
            b.previous = this;
            b.distance = distance + 1;
            b.moves = this.moves + 1;
            queue.insert(b);
        }
        if (i + 1 < size()) { // [x] get down
            b = new Board(board);
            copy(board, b.board);
            changeTile(b,i, j, i+1, j);
            b.score = manhattan();
            b.previous = this;
            b.distance = distance + 1;
            b.moves = this.moves + 1;
            queue.insert(b);
        }
        if (j - 1 >= 0) { // <--- to left
            b = new Board(board);
            copy(board, b.board);
            changeTile(b,i, j, i, j-1);
            b.score = manhattan();
            b.distance = distance + 1;
            b.moves = this.moves + 1;            
            b.previous = this;
            queue.insert(b);
        }
        if (j + 1 < size()) { // to right
            b = new Board(board);
            copy(board, b.board);
            changeTile(b,i, j, i, j+1);
            b.score = manhattan();
            b.distance = distance + 1;
            b.moves = this.moves + 1;
            b.previous = this;
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

    // print the table
    public void imprimeTabuleiro(int[][] board) {
        for(int i = 0; i < tam; i++) {
            for(int j = 0; j < tam; j++) {
                StdOut.print(" " + board[i][j]);
            }
            StdOut.println("");
        }
    }
    
    // print the table
    public void imprimeTabuleiro() {
        for(int i = 0; i < tam; i++) {
            for(int j = 0; j < tam; j++) {
                StdOut.print(" " + board[i][j]);
            }
            StdOut.println("");
        }
    }

    // print the table
    public void imprimeTabuleiroVetor() {
        for(int i = 0; i < tam; i++) {
            for(int j = 0; j < tam; j++) {
                StdOut.print(" " + board[i][j]);
            }
        }
    }

    // method to Comparable implements
    public int compareTo(Board x) {
        return (this.score + this.moves) - (x.score + x.moves);
    }

    // method to apply Comparable implements into MinPQ
    private class priority implements Comparator<Board> {
        public int compare(Board x, Board y) {
            if (x.score + x.moves > y.score + y.moves) return 1;
            else if (x.score + x.moves < y.score + y.moves) return -1;
            return 0;
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