/* 
 * ImplementaĆ§Ć£o de uma pilha de inteiros de capacidade fixa
 */

public class StackOfInt {
    private int[] a = null;
    private int n = 0;

    public StackOfInt(int cap) {
        a = new int[cap];
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void push(int item) {
        a[n++] = item;
    }

    public int pop() {
        return a[--n];
    }
}