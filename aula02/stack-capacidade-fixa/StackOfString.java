/*
 * Implementação de uma pilha de Strings de capacidade fixa.
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class StackOfString {
    private String[] a = null; // inicialização é supérflua
    private int n = 0; // inicialização é supérflua

    public StackOfString(int cap) {
        a = new String[cap];
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void push(String item) {
        a[n++] = item;
    }

    public String pop() {
        return a[--n];
    }

    // unit test
    public static void main(String[] args) {
      StackOfString pilha;
      pilha = new StackOfString(20);

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