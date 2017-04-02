/** ***********************************************************************
 *  Compilation:  javac LinkedListST.java
 *  Execution:    java LinkedListST
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/31elementary/tinyST.txt  
 *  
 *  Symbol table implementation with an ordered linked list.
 *
 *  % more tinyST.txt
 *  S E A R C H E X A M P L E
 *  
 *  % java LinkedListST < tinyST.txt
 *  A 8
 *  C 4
 *  E 12
 *  H 5
 *  L 11
 *  M 9
 *  P 10
 *  R 3
 *  S 0
 *  X 7
 *
 *************************************************************************/

// The StdIn class provides static methods for reading strings and numbers from standard input.
// https://www.ime.usp.br/~pf/sedgewick-wayne/stdlib/documentation/index.html
// http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdIn.html
import edu.princeton.cs.algs4.StdIn; 

// This class provides methods for printing strings and numbers to standard output.
// https://www.ime.usp.br/~pf/sedgewick-wayne/stdlib/documentation/index.html
// http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdOut.html
import edu.princeton.cs.algs4.StdOut; 

// https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html 
// http://codereview.stackexchange.com/questions/48109/simple-example-of-an-iterable-and-an-iterator-in-java
import java.util.Iterator; 

/** This is an implementation of a symbol table whose keys are comparable.
 * The keys are kept in increasing order in an linked list.
 * Following our usual convention for symbol tables, 
 * the keys are pairwise distinct.
 * <p>
 * For additional documentation, see 
 * <a href="http://algs4.cs.princeton.edu/31elementary/">Section 3.1</a> 
 * of "Algorithms, 4th Edition" (p.378 of paper edition), 
 * by Robert Sedgewick and Kevin Wayne.
 *
 */

public class LinkedListST<Key extends Comparable<Key>, Value> {
    // atributos de estado 
    private Key first;
    private Key last;
    private int n;
    
    private class Key {
		public String key;
		public int value;
		public Key next;
		
		private Key () {
			key = null;
			value = 0;
			next = null;
		}
		
		private Key (String str, int qnt) {
			key = str;
			value = qnt;
			next = null;
		}
	}
    
    /** Constructor.
     * Creates an empty symbol table with default initial capacity.
     */
    public LinkedListST() {
        // escreva seu método a seguir
        n = 0;
        first = new Key();
        last = first;
    }

    /** Is the key in this symbol table?
     */
    public boolean contains(Key key) {
        // escreva seu método a seguir
        if (key == null) {
            throw new java.lang.NullPointerException("ST.contains(): key is null");
        }
        else {
			for (Key aux : first){
				if ((aux.key).compareTo(aux.next.key) == 0){
					return true;
				}
			}
		}
		return false;
    }

    /** Returns the number of (key,value) pairs in this symbol table.
     */
    public int size() {
        return n;
    }

    /** Is this symbol table empty?
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /** Returns the value associated with the given key, 
     *  or null if no such key.
     *  Argument key must be nonnull.
     */
    public Value get(Key key) {
        if (key == null) 
			throw new IllegalArgumentException("argument to get() is null");
		else {
			for (Key aux : first) {
				if ((aux.key).compareTo(aux.next.key) == 0) {
					return aux.value;
				}
			}
			return -1;
		}
    } 
    
    /** Returns the number of keys in the table 
     *  that are strictly smaller than the given key.
     *  Argument key must be nonnull.
     */
    public int rank(Key key) {
		int n = 0;
        if (key == null) throw new IllegalArgumentException("argument to rank() is null");
		if (!isEmpty()) {
			for (Key aux : first) {
				if ((aux.key).compareTo(key) < 0)
					n++;
			}
		}
		return n;
    }
    
    /** Search for key in this symbol table. 
     * If key is in the table, update the corresponing value.
     * Otherwise, add the (key,val) pair to the table.
     * Argument key must be nonnull.
     * If argument val is null, the key must be deleted from the table.
     */
    public void put(Key key, Value val)  {
		Key novo;
        if (key == null) throw new IllegalArgumentException("argument to put() is null");
        if (val == null) { 
            delete(key); 
            return; 
        }
        else {
			if (contains(key) == true){
				for (Key aux : first) {
					if ((aux.key).compareTo(key) == 0)
						aux.value++;
				}
			}
			else {
				for (Key aux : first) {
					if ((aux.key).compareTo(key) < 0) {
						novo = new Key(key, val);
						Key.next = aux.next;
						aux.next = novo;
					}
				}
			}
		}
    }


    /** Remove key (and the corresponding value) from this symbol table.
     * If key is not in the table, do nothing.
     */
    public void delete(Key key)  {
        if (key == null) throw new IllegalArgumentException("argument to put() is null");
        if (!contains(key)) return ;
		for (Key aux : first) {
			if ((aux.next.key).compareTo(key) == 0)
				aux.next = aux.next.next;
				//talvez meter um null no antigo, pra ajudar o varredo ******
		}
		return;
    }

    /** Delete the minimum key and its associated value
     * from this symbol table.
     * The symbol table must be nonempty.
     */
    public void deleteMin() {
        if (isEmpty()) throw new java.util.NoSuchElementException("deleteMin(): Symbol table underflow error");
		first.next = first.next.next;
		return;
    }

    /** Delete the maximum key and its associated value
     * from this symbol table.
     */
    public void deleteMax() {
        if (isEmpty()) throw new java.util.NoSuchElementException("deleteMax(): Symbol table underflow error");
		for (Key aux : first) {
			if ((aux.next.key).compareTo(last.key) == 0) {
				last =  aux;
				//talvez meter um null no antigo, pra ajudar o varredo ******
			}
		}
    }


   /***************************************************************************
    *  Ordered symbol table methods
    **************************************************************************/

    /** Returns the smallest key in this table.
     * Returns null if the table is empty.
     */
    public Key min() {
		if (!isEmpty())
			return first;
		return null;
    }

   
    /** Returns the greatest key in this table.
     * Returns null if the table is empty.
     */
    public Key max() {
        if (!isEmpty())
			return	last;
		return null;
    }

    /** Returns a key that is strictly greater than 
     * (exactly) k other keys in the table. 
     * Returns null if k < 0.
     * Returns null if k is greater that or equal to 
     * the total number of keys in the table.
     */
    public Key select(int k) {
        int qnt;
        Key aux;
        if (k > size() || k < 0) return null;
		qnt = size() - k;
		aux = first;
		while (qnt > 0) {
			aux = aux.next;
		}
		return aux;
    }

    /** Returns the greatest key that is 
     * smaller than or equal to the given key.
     * Argument key must be nonnull.
     * If there is no such key in the table
     * (i.e., if the given key is smaller than any key in the table), 
     * returns null.
     */
    public Key floor(Key key) {
        Key cmp = null;
        if (key == null) throw new IllegalArgumentException("argument to floor() is null");
        if (!isEmpty() && contains(key)) {
			for (Key aux : first)
				if ((aux.key).compareTo(key) < 0)
					if (cmp == null || aux.key.compareTo(cmp.key) > 0) cmp = aux;
		}
		return cmp;
    }

    /** Returns the smallest key that is 
     * greater than or equal to the given key.
     * Argument key must be nonnull.
     * If there is no such key in the table
     * (i.e., if the given key is greater than any key in the table), 
     * returns null.
     */
    public Key ceiling(Key key) {
        Key cmp = null;
        if (key == null) throw new IllegalArgumentException("argument to ceiling() is null");
        if (!isEmpty() && contains(key)) {
			for (Key aux : first)
				if ((aux.key).compareTo(key) > 0)
					if (cmp == null || aux.key.compareTo(cmp) < 0) cmp = aux;
		}
		return cmp;
    }

    /** Returns all keys in the symbol table as an Iterable.
     * To iterate over all of the keys in the symbol table named st, use the
     * foreach notation: for (Key key : st.keys()).
     */
    public Iterable<Key> keys() {
        return new ListKeys();
    }

    /**
     * implements Iterable<Key> significa que essa classe deve 
     * ter um método iterator(), acho...
     */
    private class ListKeys implements Iterable<Key> {
        /** 
         * Devolve um iterador que itera sobre os itens da ST 
         * da menor até a maior chave.<br>
         */
        public Iterator<Key> iterator() {
            return new KeysIterator();
        }
        
        private class KeysIterator implements Iterator<Key> {
            // variáveis do iterador
            private Key it = first;
            
            public boolean hasNext() {
                return it.next != null;
            }

            public Key next() {
				it = it.next;
				return it;
            }
                    
            public void remove() { 
                throw new UnsupportedOperationException(); 
            }
        }
    }


   /***************************************************************************
    *   Check internal invariants: pode ser útil durante o desenvolvimento 
    **************************************************************************/
    
    // are the items in the linked list in ascending order?
    private boolean isSorted() {
        if (!isEmpty()) {
			for (Key aux : first) {
				if ((aux.key).compareTo(aux.next.key) > 0)
					return 0;
			}
			return 1;
		}
		return 1;
    }

   /** Test client.
    * Reads a sequence of strings from the standard input.
    * Builds a symbol table whose keys are the strings read.
    * The value of each string is its position in the input stream
    * (0 for the first string, 1 for the second, and so on).
    * Then prints all the (key,value) pairs.
    */
    public static void main(String[] args) { 
        LinkedListST<String, Integer> st;
        st = new LinkedListST<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
    }
}

