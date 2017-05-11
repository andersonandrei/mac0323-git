
/* Brute-force implementation. Write a mutable data type PointST.java 
that is symbol table with Point2D. 
Implement the following API by 
using a red-black BST (using either RedBlackBST from algs4.jar 
or java.util.TreeMap); 
do not implement your own red-black BST.

Corner cases.
	-Throw a java.lang.NullPointerException if any argument is null. 

Performance requirements.  

	-Your implementation should support size() in constant time; 
	-it should support put(), get() and contains() in time proportional 
		to the logarithm of the number of points in the set in the worst case; 
	-and it should support points(), nearest(), and range() in time 
		proportional to the number of points in the symbol table.

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
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;

import java.util.Iterator;
import java.lang.NullPointerException;
    


public class PointST<Value> {

	// construct an empty symbol table of points
	public PointST() {
		RedBlackBST rbt = new rbt();
	}

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

