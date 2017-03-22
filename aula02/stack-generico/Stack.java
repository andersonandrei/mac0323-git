/* 
 * Stack genÃ©rico de dimensÃ£o fixa.
 * Cada item Ã© do tipo genÃ©rico Item.
 */

public class Stack<Item> {

    private Item[] a = null;
    private int n = 0;

    public Stack(int cap) {
        a = (Item[]) new Object[cap];
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void push(Item item) {
        a[n++] = item;
    }

    public Item pop() {
        return a[--n];
    }
}