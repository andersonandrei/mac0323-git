import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StopwatchCPU;

public class Driver {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        Stack<Integer> pilha = new Stack<Integer>();
        StopwatchCPU t = new StopwatchCPU();
        for (int i = 0; i < n; i++) {
            pilha.push(i);
        }
        StdOut.println(n + ": " + t.elapsedTime() + "s");
    }
}