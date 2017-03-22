/* Recortes */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class STFirst<Item> {
	
	private Item[] keys;
	private int n;
	
	public STFirst() {
		keys = (Item[]) new Object[2];
		n = 0;
	}
	
	public boolean isEmpty() {
		return n == 0;
	}
	
	public void push(Item key) {
		if (n == keys.length) resize(2 * keys.length);
		keys[n++] = key;
	}
	
	public Item pop() {
		Item key = keys[--n];
		keys[n] = null;
		if (n > 0 && n == keys.length/4) resize(keys.length/2);
		return key;
	}
	
	public void show() {
		for (int i = 0; i < n; i++)
			StdOut.println("keys["+i+"] : " + keys[i]);
	
	}
	
	private void resize(int max) {
      Item[] temp = (Item[]) new Object[max];
      for (int i = 0; i < n; i++)
         temp[i] = keys[i];
      keys = temp;
	}
	
	/*
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
    
    */
    
    // unit test
    public static void main(String[] args) {
      STFirst<String> keys;
      keys = new STFirst<String>();
      int i = 0;
		StdOut.println("Para encerrar, ctrl+d");
		while (!StdIn.isEmpty()) {
			String str = StdIn.readString();
			if (!str.equals("-")) {
				keys.push(str);
			}
		}
		StdOut.println("Encerrou, veja a porra toda");
		keys.show();
	}
   
   
   
   
   
   
   
}
