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

public class Deque<Item> implements Iterable<Item> {
   public Deque()                           // construct an empty deque
   public boolean isEmpty()                 // is the deque empty?
   public int size()                        // return the number of items on the deque
   public void addFirst(Item item)          // add the item to the front
   public void addLast(Item item)           // add the item to the end
   public Item removeFirst()                // remove and return the item from the front
   public Item removeLast()                 // remove and return the item from the end
   public Iterator<Item> iterator()         // return an iterator over items in order from front to end
   public static void main(String[] args)   // unit testing (required)
}
