import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class ClientStackGenerico {

   public static void main(String[] args) {
      Stack<String> pilha;
      pilha = new Stack<String>(20);

      while (!StdIn.isEmpty()) {
         String str = StdIn.readString();
         if (!str.equals("-"))
            pilha.push(str);
         else if (!pilha.isEmpty()) 
                 StdOut.println(pilha.pop() + " ");
      }
      StdOut.println("(" + pilha.size() + " left on stack)");
   }
}