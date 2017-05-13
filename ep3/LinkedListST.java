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
    private Node first;
    private Node last;
    private int n;
    
    private class Node {
		public Key key;
		public Value value;
		public Node next;
		
		private Node () {
			key = null;
			value = null;
			next = null;
		}
		
		private Node (Key str, Value qnt) {
			key = str;
			value = qnt;
			next = null;
		}
	}
    
    /** Constructor.
     * Creates an empty symbol table with default initial capacity.
     */
    public LinkedListST() {
        n = 0;
        first = new Node();
        last = first;
    }   

    /** Is the key in this symbol table?
     */
    public boolean contains(Key key) {
        Node aux;
        if (key == null) {
            throw new java.lang.NullPointerException("ST.contains(): key is null");
        }
        else {
			if (first == null || first.key == null)
				return false;
			aux = first;
			while(aux != null) {
				if(aux.key == null) return false;
				if ((aux.key).compareTo(key) == 0){
					return true;
				}
				aux = aux.next;
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
        Node aux;
        if (key == null) 
			throw new IllegalArgumentException("argument to get() is null");
		if (contains(key)) {
			aux = first;
			while(aux != null) {
				if ((aux.key).compareTo(key) == 0) 
					return aux.value;
				aux = aux.next;
			}
		}
		return null;
    } 
    
    /** Returns the number of keys in the table 
     *  that are strictly smaller than the given key.
     *  Argument key must be nonnull.
     */
    public int rank(Key key) {
		Node aux;
		int t = 0;
        if (key == null) throw new IllegalArgumentException("argument to rank() is null");
		if (!isEmpty()) {
			if (first == null || first.key == null)
				return t;
			aux = first;
			while(aux != null) {
				if ((aux.key).compareTo(key) < 0)
					t++;
				aux = aux.next;
			}
		}
		return t;
    }
    
    /** Search for key in this symbol table. 
     * If key is in the table, update the corresponing value.
     * Otherwise, add the (key,val) pair to the table.
     * Argument key must be nonnull.
     * If argument val is null, the key must be deleted from the table.
     */
    public void put(Key key, Value val)  {
		Node novo, aux, oldaux;
        if (key == null) throw new IllegalArgumentException("argument to put() is null");
        if (val == null) { 
            delete(key); 
            return; 
        }
        else {
			if (contains(key) == true){
				aux = first;
				while(aux != null) {
					if ((aux.key).compareTo(key) == 0)
						aux.value = val;
					aux = aux.next;
				}
			}
			else {
				novo = new Node(key, val);
				if (first == null || first.key == null)
					first = novo;
				else {
					aux = first;
					oldaux = null;
					while(aux != null && (aux.key).compareTo(key) < 0) {
						oldaux = aux;
						aux = aux.next;
					}
					if (aux == null) { // inserir no final da lista
						oldaux.next = novo;
					}
					else if (oldaux == null) { // inserir no início da lista
						novo.next = aux;
						first = novo;
					}
					else { // inserir no meio qualquer da lista
						oldaux.next = novo;
						novo.next = aux;
					}
				}
				n++;
			}
		}
    }

    /** Remove key (and the corresponding value) from this symbol table.
     * If key is not in the table, do nothing.
     */
    public void delete(Key key)  {
        Node aux, oldaux;
        if (key == null) 
			throw new IllegalArgumentException("argument to put() is null");
        if (!contains(key))
			return ;
		aux = first;
		oldaux = null;
		while(aux != null && (aux.key).compareTo(key) != 0) {
			oldaux = aux;
			aux = aux.next;
		}
		oldaux.next = aux.next;
		aux = null;
		n--;
		return;
    }

    /** Delete the minimum key and its associated value
     * from this symbol table.
     * The symbol table must be nonempty.
     */
    public void deleteMin() {
		Node newfirst;
        if (isEmpty()) 
			throw new java.util.NoSuchElementException("deleteMin(): Symbol table underflow error");
		newfirst = first.next;
		first = newfirst;
		return;
    }

    /** Delete the maximum key and its associated value
     * from this symbol table.
     */
    public void deleteMax() {
        Node aux, oldaux;
        if (isEmpty()) 
			throw new java.util.NoSuchElementException("deleteMax(): Symbol table underflow error");
		oldaux = null;
		aux = first;
		while (aux.next != null) {
			oldaux = aux;
			aux = aux.next;
		}
		oldaux.next = null;
		aux = null;
    }

   /***************************************************************************
    *  Ordered symbol table methods
    **************************************************************************/

    /** Returns the smallest key in this table.
     * Returns null if the table is empty.
     */
    public Key min() {
		if (!isEmpty())
			return first.key;
		return null;
    }

   
    /** Returns the greatest key in this table.
     * Returns null if the table is empty.
     */
    public Key max() {
		Node aux;
        if (!isEmpty()) {
			if (first == null || first.key == null) return null;
			aux = first;
			while (aux.next != null)
				aux = aux.next;
			return aux.key;
		}
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
        Node aux;
        if (k > size() || k < 0) 
			return null;
		qnt = size() - k;
		aux = first;
		while (qnt > 0)
			aux = aux.next;
		return aux.key;
    }

    /** Returns the greatest key that is 
     * smaller than or equal to the given key.
     * Argument key must be nonnull.
     * If there is no such key in the table
     * (i.e., if the given key is smaller than any key in the table), 
     * returns null.
     */
    public Key floor(Key key) {
        Node aux, oldaux;
        if (key == null) throw new IllegalArgumentException("argument to floor() is null");
        if (first.key == null || first.key.compareTo(key) > 0) return null;
		aux = first;
		oldaux = new Node();
		while (aux != null && aux.key.compareTo(key) < 0) {
			oldaux = aux;
			aux = aux.next;
		}
		if(aux == null) return null;
		if(aux.key.compareTo(key) == 0) return aux.key;
		return oldaux.key;
    }

    /** Returns the smallest key that is 
     * greater than or equal to the given key.
     * Argument key must be nonnull.
     * If there is no such key in the table
     * (i.e., if the given key is greater than any key in the table), 
     * returns null.
     */
    public Key ceiling(Key key) {
        Node aux, cmp = new Node();
        if (key == null) 
			throw new IllegalArgumentException("argument to ceiling() is null");
        if (!isEmpty() && contains(key)) {
			if (first == null || first.key == null) 
				return null;
			aux = first;
			while (aux != null) {
				if ((aux.key).compareTo(key) > 0)
					if (cmp.key == null || aux.key.compareTo(cmp.key) <= 0)
						cmp = aux;
				aux = aux.next;
			}
		}
		return cmp.key;
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
            Node it;
            
            public KeysIterator() {
				it = new Node();
				it.next = first;
				}
            
            public boolean hasNext() {
                return it.next != null;
            }

            public Key next() {
				it = it.next;
				return it.key;
            }
                    
            public void remove() { 
                throw new UnsupportedOperationException(); 
            }
        }
    }


	/*Implementado apenas para testes, e aplicado apenas em algumas funções*/
	public Iterable<Node> nodes() {
        return new ListNodes();
    }

	private class ListNodes implements Iterable<Node> {
        /** 
         * Devolve um iterador que itera sobre os itens da ST 
         * da menor até a maior chave.<br>
         */
        public Iterator<Node> iterator() {
            return new NodeIterator();
        }
        
        private class NodeIterator implements Iterator<Node> {
            // variáveis do iterador
            Node it;
            
            public NodeIterator() {
				it = new Node();
				it.next = first;
			}
            
            public boolean hasNext() {
                return it.next != null;
            }

            public Node next() {
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
			for (Node aux : nodes()) {
				if ((aux.key).compareTo(aux.next.key) > 0)
					return false;
			}
			return true;
		}
		return true;
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
		StdOut.println(st.get("while"));
		StdOut.println(st.get("fezes"));
		StdOut.println(st.isEmpty());
		StdOut.println(st.size());
		st.put("fezes", 1);
		StdOut.println(st.max());
		st.deleteMax();
		StdOut.println(st.max());
		StdOut.println(st.min());
		st.deleteMin();
		StdOut.println(st.min());
		StdOut.println(st.get("fezes"));
        StdOut.println(st.rank("H"));
        StdOut.println(st.ceiling("R"));
        st.put("R", 15);
        StdOut.println(st.ceiling("R"));
        StdOut.println(st.floor("C"));
        StdOut.println(st.floor("P"));
        st.delete("M");
        StdOut.println(st.floor("P"));
        

    }
}
