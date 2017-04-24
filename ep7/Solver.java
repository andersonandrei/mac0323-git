/*Corner cases.  You may assume that the constructor receives an N-by-N array containing the N2 integers between 0 and N2 − 1, 
where 0 represents the blank square. The tileAt() method should throw a java.lang.IndexOutOfBoundsException unless both i or j 
are between 0 and N − 1.

Performance requirements.  Your implementation should support all Board methods in time proportional to N² (or better) 
in the worst case, with the exception that isSolvable() may take up to N4 in the worst case. 

*/

public class Solver {
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    public int moves()                     // min number of moves to solve initial board
    public Iterable<Board> solution()      // sequence of boards in a shortest solution
    public static void main(String[] args) // unit testing 
}