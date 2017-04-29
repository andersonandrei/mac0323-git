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
        StdOut.println("-----Board criardo: ");
        imprimeTabuleiro(board);
        StdOut.println("-----Goal esperado: ");
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
        return tam;
    }    

    public int sumPositionExpTile (int i, int j, int x) {
        int sumA, sumB;
        int m = x / size();
        int n = x % size() - 1;
        if(x == 0) {
            return (size() - i) + (size() - j);
        }

        if (m > i) 
            sumA = m - i;
        else
            sumA = i - m;
        if (n > j)
            sumB = n - j;
        else
            sumB = j - n;

        return sumA + sumB;
    }

    public int inversions() {
        int inv = 0;
        int[] aux = new int[size];
        int x = 0;
        for (int i = 0; i < size(); i++)
            for (int j = 0; j < size(); j++){
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
        if (size() > 0) {
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
        if (size() == 0) return false;
        if (isGoal() == true) return true;
        int inv = inversions();
        int white = findLineWhite();
        if (size() % 2 == 0) {
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
        if (size() > 0) {
            for (int i = 0; i < tam; i++)
                for(int j = 0; i < tam; j++)
                    if (board[i][j] != aux.tileAt(i,j))
                        return false;
            return true;
        }
        return false;
    }

    public void changeTile(Board b, int i, int j, int m, int n) {
        int aux = b.board[i][j];
        b.board[i][j] = b.board[m][n];
        b.board[m][n] = aux;
        return;
    }

    public void copy (int[][]orig, int[][] dest) {
        for(int i = 0; i < tam - 1; i++) {
            for(int j = 0; j < tam - 1; j++) {
                dest[i][j] = orig[i][j];
            }
        }
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        StdOut.println("No neighbors");
        imprimeTabuleiro(board);
        MinPQ<Board> queue =  new MinPQ<Board>(new priority());
        Board b;
        int i = 0, j = 0, m = 0, n = 0;
        for(i = 0; i < tam; i++) {
            for(j = 0; j < tam; j++) {
                StdOut.println("Olhando pra " + board[i][j] + "com " + i + j);
                if (board[i][j] == 0) {
                    m = i;
                    n = j;
                }
            }
        }

        if (i == 3) i-=1;
        if (j == 3) j-=1;

        i = m;
        j = n;

        StdOut.println("Depois do for com " + i + j);
        StdOut.println("Antes do if1");
        if (i - 1 >= 0 ) { // [x] sobe
            StdOut.println("if1");
            b = new Board(board);
            copy(board, b.board);
            changeTile(b, i, j, i-1, j);
            StdOut.println("No if1 criou");
            imprimeTabuleiro(b.board);
            b.previous = this;
            b.distance = distance + 1;
            b.moves = moves + 1;
            queue.insert(b);
        }
        StdOut.println("Antes do if2");
        if (i + 1 < size()) { // [x] desce
            StdOut.println("if2");
            b = new Board(board);
            copy(board, b.board);
            changeTile(b,i, j, i+1, j);
            StdOut.println("No if2 criou");
            imprimeTabuleiro(b.board);
            b.previous = this;
            b.distance = distance + 1;
            b.moves = moves + 1;
            queue.insert(b);
        }
        StdOut.println("Antes do if3");
        if (j - 1 >= 0) { // <--- [x]
            StdOut.println("if3");
            b = new Board(board);
            copy(board, b.board);
            changeTile(b,i, j, i, j-1);
            StdOut.println("No if3 criou");
            imprimeTabuleiro(b.board);
            b.distance = distance + 1;
            b.moves = moves + 1;            
            b.previous = this;
            queue.insert(b);
        }
        StdOut.println("Antes do if4");
        if (j + 1 < size()) { // [x] --->
            StdOut.println("if4");
            b = new Board(board);
            copy(board, b.board);
            changeTile(b,i, j, i, j+1);
            StdOut.println("No if4 criou");
            imprimeTabuleiro(b.board);
            b.distance = distance + 1;
            b.moves = moves + 1;
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

    public void imprimeTabuleiro(int[][] board) {
        for(int i = 0; i < tam; i++) {
            for(int j = 0; j < tam; j++) {
                StdOut.print(" " + board[i][j]);
            }
            StdOut.println("");
        }
    }

    public void imprimeTabuleiro() {
        for(int i = 0; i < tam; i++) {
            for(int j = 0; j < tam; j++) {
                StdOut.print(" " + board[i][j]);
            }
            StdOut.println("");
        }
    }

    public int compareTo(Board x) {
        //return (this.manhattan() + x.moves) - (x.manhattan() + x.moves);
        if (this.manhattan() > x.manhattan()) return 1;
        else if (this.manhattan() < x.manhattan()) return -1;
        return 0;
    }

    private class priority implements Comparator<Board> {
        public int compare(Board x, Board y) {
            if (x.manhattan() > y.manhattan()) return 1;
            else if (x.manhattan() < y.manhattan()) return -1;
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