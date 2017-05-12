
/* 2d-tree implementation. 
Write a mutable data type KdTreeST.java that uses a 
2d-tree to implement the same API (but renaming PointST to KdTreeST). 

A 2d-tree is a generalization of a BST to two-dimensional keys. The idea is 
to build a BST with points in the nodes, using the x- and y-coordinates of 
the points as keys in strictly alternating sequence, starting 
with the x-coordinates.  

Use the immutable data type Point2D (part of algs4.jar) for points in the plane. 
Here is the subset of its API that you may use:

    public class Point2D implements Comparable<Point2D> {
       public Point2D(double x, double y)              // construct the point (x, y)
       public  double x()                              // x-coordinate 
       public  double y()                              // y-coordinate 
       public  double distanceSquaredTo(Point2D that)  // square of Euclidean distance between two points 
       public     int compareTo(Point2D that)          // for use in an ordered symbol table 
       public boolean equals(Object that)              // does this point equal that object? 
       public  String toString()                       // string representation 
    }

Use the immutable data type RectHV (part of algs4.jar) for axis-aligned rectanges. 
Here is the subset of its API that you may use:

    public class RectHV {
       public    RectHV(double xmin, double ymin,      // construct the rectangle [xmin, xmax] x [ymin, ymax] 
                        double xmax, double ymax)      
       public  double xmin()                           // minimum x-coordinate of rectangle 
       public  double ymin()                           // minimum y-coordinate of rectangle 
       public  double xmax()                           // maximum x-coordinate of rectangle 
       public  double ymax()                           // maximum y-coordinate of rectangle 
       public boolean contains(Point2D p)              // does this rectangle contain the point p (either inside or on boundary)? 
       public boolean intersects(RectHV that)          // does this rectangle intersect that rectangle (at one or more points)? 
       public  double distanceSquaredTo(Point2D p)     // square of Euclidean distance from point p to closest point in rectangle 
       public boolean equals(Object that)              // does this rectangle equal that object? 
       public  String toString()                       // string representation 
    }
*/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Iterator;
import java.lang.NullPointerException;

public class KdTreeST<Value> {
	private Node root;
	private int size;

	private class Node {
	   private Point2D p;     // the point
	   private Value value;   // the symbol table maps the point to this value
	   private RectHV rect;   // the axis-aligned rectangle corresponding to this node
	   private Node left;       // the left/bottom subtree
	   private Node right;       // the right/top subtree
	   private int orientation; // 0 for vertical, 1 for horizontal
	
	   private Node () {
			this.p = null;
			value = null;
			rect = null;
			left = null;
			right = null;
			orientation = 0;
	   }

	   private Node (Point2D p, Value val) {
	   		this.p = p;
			value = val;
			rect = null;
			left = null;
			right = null;
			orientation = 0;
	   }
	}

	// construct an empty symbol table of points
	public KdTreeST() {
		root = null;
		size = 0;
	}

	// is the symbol table empty?
	public boolean isEmpty() {
		return size == 0;
	}

	// number of points                      
	public int size() {
		return size;
	}

	// associate the value val with point p
	public void put(Point2D p, Value val) {
		if (p == null) return ;
		Node x = new Node (p,val);
		if (root == null) {
			x.orientation = 0;
			x.rect = new RectHV(0, 0, 1, 1);
			root = x;
			return;
		}
		if (root.p.equals(x.p)) {
			root.value = x.value;
			return;
		}
		if (root.orientation == 0) {
			x.orientation = 1;
			if (root.p.x() > x.p.x()) {
				put(root.left, x, root, 0);
			}
			else {
				put(root.right, x, root, 1);	
			}
		}
		else {
			x.orientation = 0;
			if (root.p.y() > x.p.x()) {
				put(root.left, x, root, 0);
			}
			else {
				put(root.right, x, root, 1);	
			}
		}
		return;
	}



	public void put(Node current, Node x, Node parent, int side) { //side : 0 left, 1 right
		if (current == null) {
			x.rect = rect(x, parent, side, x.orientation, parent.rect);
			if (side == 0) {
				//maybe the rect should to be here.
				parent.left = x;
				return;
			}
			else {
				//maybe the rect should to be here.
				parent.right = x;
				return;
			}
		}
		if (current.orientation == 0) {
			x.orientation = 1;
			if (current.p.x() > x.p.x()) {
				put(current.left, x, current, 0);
			}
			else {
				put(current.right, x, current, 1);	
			}
		}
		else {
			x.orientation = 0;
			if (current.p.y() > x.p.y()) {
				put(current.left, x, current, 0);
			}
			else {
				put(current.right, x, current, 1);	
			}
		}
		return;
	}

	//Calculate the rect for the point of Node x
	private RectHV rect (Node x, Node parent, int side, int orientation, RectHV parentRect) {
		if(x == null) return null;
		if(x == root) return new RectHV(0, 0, 1, 1);
		if (side == 0) {
			if (orientation == 0) {
				return new RectHV(parentRect.xmin(), parentRect.ymin(), parent.p.x(), parentRect.ymax());
			}
			else {
				return new RectHV(parentRect.xmin(), parentRect.ymin(), parentRect.xmax(), parent.p.y());
			}
		}
		else {
			if (orientation == 0) {
				return new RectHV(parent.p.x(), parentRect.ymin(), parentRect.xmax(), parentRect.ymax());
			}
			else {
				return new RectHV(parentRect.xmin(), parent.p.y(), parentRect.xmax(), parentRect.ymax());
			}
		}
	}

	// value associated with point p
	public Value get(Point2D p) {
		return this.get(root, p);
	}

	public Value get(Node x, Point2D p) {
		if (p == null || x == null) return null;
		if (x.p.equals(p)) return x.value;
		if (x.orientation == 0) { //use x-coordenate
			if (x.p.x() > p.x()) { //go to <-
				return get(x.left, p);
			}
			else { //go to ->
				return get(x.right, p);
			}
		}
		else { //use y-coordenate
			if (x.p.y() > p.y()) { //go to <-
				return get(x.left, p);
			}
			else { //go to ->
				return get(x.right, p);
			}
		}
	}

	// does the symbol table contain point p?  
	public boolean contains(Point2D p) {
		if (get(p) != null) return true;
		return false;
	}

	// all points in the symbol table
	public Iterable<Point2D> points() { // read: left, middle, rigth
		Queue<Point2D> points = new Queue<Point2D>();
		inOrdem(root, points);
		return points;
	}

	private void inOrdem (Node x, Queue<Point2D> q) {
		if (x != null) {
			inOrdem(x.left, q);
			q.enqueue(x.p);
			inOrdem(x.right, q);
		}
	}

	// all points that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {
		return null;
	}

	private void range(Node current, RectHV rect, Queue<Point2D> queue) {
		if (current == null || !rect.contains(current.p)) return;
		if (current.rect.intersect(rect)) {
			if (rect.contains(current.p)) {
				queue.enqueue(current.p);
			}
		}
		range(current.left, rect, queue);
		range(current.right, rect, queue);
	}

	// a nearest neighbor to point p; null if the symbol table is empty 
	public Point2D nearest(Point2D p) {
		return p;
	}

	// unit testing (required) 
	public static void main(String[] args) {}
}