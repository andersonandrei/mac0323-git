/*
 Dequeue. 
 * A double-ended queue or deque (pronounced "deck") is a generalization 
 * of a stack and a queue that supports adding and removing items from either 
 * the front or the back of the data structure. 

Corner cases.  
* Throw a 
* java.lang.NullPointerException if the client attempts to add a null item; 
* java.util.NoSuchElementException if the client attempts to remove an item from an empty deque; 
* java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator; 
* java.util.NoSuchElementException if the client calls the next() method in the iterator and there are no more items to return.

Performance requirements.  
* Your deque implementation must support each deque operation (including construction) 
* in constant worst-case time and use space linear in the number of items currently in the deque. 
* Additionally, your iterator implementation must support each operation (including construction) in constant worst-case time.

*/
import edu.princeton.cs.algs4.StdIn; 
import edu.princeton.cs.algs4.StdOut; 
import java.util.Iterator;
import java.util.NoSuchElementException; 

public class Deque<Item> implements Iterable<Item> {
	
	private Node first, last;
	private int n;
	
	private class Node {
		
		public Item item;
		public Node next;
		
		private Node(){
			item = null;
			next = null;
		}
		
		private Node(Item item) {
			this.item = item;
			next = first;
		}
	}
	
	// construct an empty deque
	public Deque() {
		first = null;
		last = null;
		n = 0;
	}
	
	// is the deque empty?
	public boolean isEmpty() {
		return n == 0;
	}
	
	// return the number of items on the deque
   public int size() {
		return n;
	}
	
	// add the item to the front
   public void addFirst(Item item) {
	   if (item == null)
            throw new java.lang.NullPointerException();
		Node oldfirst = first;
		first = new Node(item);
		first.next = oldfirst;
		n++;
	}
	
	// add the item to the end	
   public void addLast(Item item) {
		if (item == null)
            throw new java.lang.NullPointerException();
      if (first == null || last == null) {
			Node entry = new Node(item);
			first = entry;
			last = entry;
			last.next = first;
		}
		else {
			Node oldlast = last;
			last = new Node(item);
			oldlast.next = last;
			last.next = first;
		}
		n++;
		
	}
   
   // remove and return the item from the front	
   public Item removeFirst() {
		if(isEmpty())
			throw new java.util.NoSuchElementException();
		Node novo = first;
		first = first.next;
		n--;
		return novo.item;
	}
	
	// remove and return the item from the end
   public Item removeLast() {
		if (isEmpty())
			throw new java.util.NoSuchElementException();
		Node novo = new Node();
		Node aux = new Node();
		Item x;
		int i = 0;
		novo = first;
		aux = null;
		if (n > 1) {
			while (i < n-1) {
				aux = novo;
				novo = novo.next;
				i++;
			}
			aux.next = first;
			last = aux;
			x = novo.item;
			novo = null;
		}
		else {
			x = first.item;
			first = null;
			last = null;		
		}
		
		n--;
		
		return x;
	}
	
	// return an iterator over items in order from front to end
   public Iterator<Item> iterator() {
		return new ListNodes();
	}
	
	private class ListNodes implements Iterator<Item> {
		Node it;
		
		public ListNodes() {
			it = new Node();
			it.next = first;
		}
		
		public boolean hasNext() {
			return (it.next != first);
		}
		
		public Item next() {
			if (!hasNext()) 
                throw new java.util.NoSuchElementException();
			it = it.next;
			return it.item;
		}
		
		public void remove() {
			throw new java.lang.UnsupportedOperationException(); 
		}
	}
	
	// unit testing (required)
	public static void main(String[] args) {
		
		Deque<String> q = new Deque<String>();
		StdOut.println("Oi ...");
		StdOut.println("Ta vazia? : " +q.isEmpty());
		StdOut.println("Empilhando First");
		q.addFirst("Hoje");
		q.addFirst("é");
		q.addFirst("Um");
		q.addFirst("novo");
		q.addFirst("dia");
		StdOut.println("Desempilhando: First");
		StdOut.println(q.removeFirst());
		StdOut.println(q.removeFirst());
		StdOut.println(q.removeFirst());
		StdOut.println(q.removeFirst());
		StdOut.println(q.removeFirst());
		StdOut.println("Ta vazia? : " +q.isEmpty());
		StdOut.println("Isso ai.");
		
		StdOut.println("---------------");
		StdOut.println("Empilhando Last ");
		q.addLast("Hoje");
		q.addLast("é");
		q.addLast("Um");
		q.addLast("novo");
		q.addLast("dia");
		StdOut.println("Desempilhando: last");
		StdOut.println(q.removeLast());
		StdOut.println(q.removeLast());
		StdOut.println(q.removeLast());
		StdOut.println(q.removeLast());
		StdOut.println(q.removeLast());
		StdOut.println("Ta vazia? : " +q.isEmpty());

		StdOut.println("---------------");
		StdOut.println("Empilhando last");
		q.addLast("Hoje");
		q.addLast("é");
		q.addLast("Um");
		q.addLast("novo");
		q.addLast("dia");
		StdOut.println("Desempilhando: first");
		StdOut.println(q.removeFirst());
		StdOut.println(q.removeFirst());
		StdOut.println(q.removeFirst());
		StdOut.println(q.removeFirst());
		StdOut.println(q.removeFirst());
		StdOut.println("Ta vazia? : " +q.isEmpty());
		StdOut.println("Isso ai.");
		
		StdOut.println("---------------");
		StdOut.println("Empilhando First");
		q.addFirst("Hoje");
		q.addFirst("é");
		q.addFirst("Um");
		q.addFirst("novo");
		q.addFirst("dia");
		StdOut.println("Desempilhando: last");
		StdOut.println(q.removeLast());
		StdOut.println(q.removeLast());
		StdOut.println(q.removeLast());
		StdOut.println(q.removeLast());
		StdOut.println(q.removeLast());
		StdOut.println("Ta vazia? : " +q.isEmpty());
		StdOut.println("Isso ai.");
	   
		
	}
}
