
/* 2d-tree implementation. 
Write a mutable data type KdTreeST.java that uses a 
2d-tree to implement the same API (but renaming PointST to KdTreeST). 
A 2d-tree is a generalization of a BST to two-dimensional keys. The idea is 
to build a BST with points in the nodes, using the x- and y-coordinates of 
the points as keys in strictly alternating sequence, starting 
with the x-coordinates.  */

public class KdTreeST<Value> {
	
	// construct an empty symbol table of points
	public KdTreeST() {}

	// is the symbol table empty?
	public boolean isEmpty() {}

	// number of points                      
	public int size() {}

	// associate the value val with point p
	public void put(Point2D p, Value val) {}

	// value associated with point p      
	public Value get(Point2D p) {}

	// does the symbol table contain point p?  
	public boolean contains(Point2D p) {}

	// all points in the symbol table
	public Iterable<Point2D> points() {}

	// all points that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {}

	// a nearest neighbor to point p; null if the symbol table is empty 
	public Point2D nearest(Point2D p) {}

	// unit testing (required) 
	public static void main(String[] args) {}
}