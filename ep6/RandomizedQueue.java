/* Randomized queue. 
 * A randomized queue is similar to a stack or queue, except that the item 
 * removed is chosen uniformly at random from items in the data structure.

Corner cases.  
* 
* The order of two or more iterators to the same randomized queue must be mutually independent; 
* each iterator must maintain its own random order. 
* Throw a 
* java.lang.NullPointerException if the client attempts to add a null item; 
* java.util.NoSuchElementException if the client attempts to sample or dequeue an item from an empty randomized queue; 
* java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator;
* java.util.NoSuchElementException if the client calls the next() method in the iterator and there are no more items to return.

Performance requirements.  
* Your randomized queue implementation must support each randomized queue operation (besides creating an iterator) 
* in constant amortized time and use space linear in the number of items currently in the queue. 
* That is, any sequence of M randomized queue operations (starting from an empty queue) must take at most cM steps in the worst case, 
* for some constant c. 
* Additionally, your iterator implementation must support next() and hasNext() in constant worst-case time and construction in linear time; 
* you may use a linear amount of extra memory per iterator.

*/
import edu.princeton.cs.algs4.StdIn; 
import edu.princeton.cs.algs4.StdOut; 
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
   	
	private Node first;
	private int size;
   
   private class Node {
		public Item item;
		public Node next;
	
		private Node(){
			item = null;
			next = first;
		}
	
		private Node(Item item) {
			item = item;
			next = first;
		}
	}
   
   // construct an empty randomized queue
   public RandomizedQueue() { 
		first = new Node();
		first.next = null;
		size = 0;
		
	}
		    
	// is the queue empty?	             
   public boolean isEmpty() {
		return size == 0;
	}
	
	// return the number of items on the queue
   public int size() {
		return size;
	}
	
	// add the item
   public void enqueue(Item item) {
		Node novo = new Node(item);
		novo.next = first;
		first = novo;
	}
	
	// remove and return a random item
   public Item dequeue() {
		int position, aux = 0;
		Node temp = new Node();
		Node current = new Node(); 
		position = StdRandom.uniform(size());
		current = first;
		while (aux < position-1) {
			current = current.next;
		}
		temp = current.next;
		current.next = temp.next;
		return temp.item;
	}
	// return a random item (but do not remove it)   
   public Item sample() {
		int position, aux = 0;
		Node current = new Node(); 
		position = StdRandom.uniform(size());
		current = first;
		while (aux < position) {
			current = current.next;
		}
		return current.item;
	}
	
	// return an independent iterator over items in random order
   public Iterator<Item> iterator() {
		return new ListItems();
	}
	
	private class ListItems implements Iterator<Item> {
		Node it;
		
		public ListItems() {
			it =  new Node();
			it = first;
		}
		
		public boolean hasNext() {
			return it.next != null;
		}
		
		public Item next() {
			it = it.next;
			return it.item;
		}
		
		public void remove() {
			throw new java.lang.UnsupportedOperationException(); 
		}
	
	}
	
   public static void main(String[] args) {}  // unit testing (required)
}
