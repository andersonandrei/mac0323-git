/*
--------------

Corner cases. Your code should throw an exception when a constructor or method 
is called with an invalid argument, as documented below:

By convention, the indices x and y are integers between 0 and W − 1 and 
between 0 and H − 1, respectively, where W is the width and H is the height 
of the current image. Throw a java.lang.IndexOutOfBoundsException if energy() 
is called with either an x-coordinate or y-coordinate outside its prescribed range.

Throw a java.lang.NullPointerException if the constructor, removeVerticalSeam(), 
or removeHorizontalSeam() is called with a null argument.

Throw a java.lang.IllegalArgumentException if either removeVerticalSeam() 
or removeHorizontalSeam() is called with an array of the wrong length or if the 
array is not a valid seam (either an entry is outside the height/width bounds or 
two adjacent entries differ by more than 1).

Throw a java.lang.IllegalArgumentException if either removeVerticalSeam() 
or removeHorizontalSeam() is called when the width or height of the current 
picture is 1, respectively. 

--------------

Constructor. The data type may not mutate the Picture argument to the constructor. 

--------------

Performance requirements. The width(), height(), and energy() methods should 
take constant time in the worst case. All other methods should run in time 
proportional to W H (or better) in the worst case. 

--------------
*/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Queue;
import java.lang.Math;
import java.lang.NullPointerException;

import java.awt.Color;


public class SeamCarver {

   Picture pic, backup;
   int width; 
   int height;
   Node[][] information;
   double[][] distTo;
   Node[][] edgeTo;

   public class Node {
      double energy;
      int x;
      int y;
      int xfather;
      int yfather;

      private Node(int x, int y, double e) {
         this.x = x;
         this.y = y;
         this.xfather = 0;
         this.yfather = 0;
         this.energy = e;
      }
   }

   // create a seam carver object based on the given picture
   public SeamCarver(Picture picture){
      pic = picture;
      backup = picture;
      width = pic.width();
      height = pic.height();
   }

   // current picture
   public Picture picture() {
      return pic;
   }

   /*// width of current picture
   public int width() {
      return width;
   }

   // height of current picture
   public int height() {
      return height;
   }
*/
   public int width() {
      return pic.width();
   }

   public int height() {
      return pic.height();
   }

   // energy of pixel at column x and row y
   public  double energy(int x, int y) {
      return Math.sqrt(deltaSquare(x,y,0) + deltaSquare(x,y,1));
   }

   private Node[][] energyMatrix() {
      int i, j;
      information = new Node[pic.width()][pic.height()];
      for (i = 0; i < pic.width(); i++) {
         for (j = 0; j < pic.height(); j++) {
            information[i][j] = new Node(i,j, energy(i, j));
         }
      }
      return information;
   }

   private double deltaSquare (int x, int y, int option) {
      Color c1, c2;
      double red, green, blue;
      if (option == 0) {
         if(x-1 > 0 ) c1 = pic.get(x-1, y);
         else {
            c1 = pic.get((x-1 + pic.width()) % pic.width(), y);
         }
         if (x+1 < pic.width()) c2 = pic.get(x+1, y);
         else c2 = pic.get((x+1 + pic.width()) % pic.width(), y);

      }
      else {
         if(y-1 > 0 ) c1 = pic.get(x, y-1);
         else c1 = pic.get(x, (y-1 + pic.height()) % pic.height());
         if (y+1 < pic.height()) c2 = pic.get(x, y+1);
         else c2 = pic.get(x, (y+1 + pic.height()) % pic.height());
      }
      red = c2.getRed() - c1.getRed();
      green = c2.getGreen() - c1.getGreen();
      blue = c2.getBlue() - c1.getBlue();
      return (red * red) + (green * green) + (blue * blue);
   }

   private Iterable<Node> nextDown (Node root) {
      int i, j;
      Queue<Node> next = new Queue<Node>();
      i = root.x;
      j = root.y;
      if(j+1 < pic.height()) {
         if(i-1 >= 0) {
            next.enqueue(information[i-1][j+1]);
         }
         next.enqueue(information[i][j+1]);
         if(i+1 < pic.width()) {
            next.enqueue(information[i+1][j+1]);
         }
      }
      return next;
   }

   // sequence of indices for vertical seam
   public int[] findVerticalSeam() {
      distTo = new double[pic.width()][pic.height()]; //the last line is to first and last node
      edgeTo = new Node[pic.width()][pic.height()];
      Queue<Integer> queue = new Queue<Integer>(); 
      int[] path;
      for(int v = 0; v < pic.width(); v++){
         for(int w = 0; w < pic.height(); w++){
            distTo[v][w] = Double.POSITIVE_INFINITY;
            edgeTo[v][w] = new Node(v, w, 0.0);
            edgeTo[v][w].xfather = v;
            edgeTo[v][w].yfather = w;
         }
      }
      for(int v = 0; v < pic.width(); v++) {
         distTo[v][0] = 0.0;
      }
      information = energyMatrix();
      for (int i = 0; i < pic.height(); i++) {
         for (int j = 0; j < pic.width(); j++) {
            relax(information[j][i]);
         }
      }
      return minPath();
   }

   private int[] minPath() {
      int k;
      int indPath = 0;
      int minSum = 1000000000;
      int auxSum = 0;
      int verification;
      Queue<Double> queueEnergy = new Queue<Double>(); 
      Queue<Integer> queue = new Queue<Integer>(); 
      int[] path;
      int i;
      for (i = 0; i < pic.width(); i++) {
         verification = pic.width()-1;
         if (hasPath(i,pic.height() - 1)) {
            k = 0;
            writePathEnergy(i, pic.height() - 1, queueEnergy);
            auxSum = 0;
            while (!queueEnergy.isEmpty()) {
               auxSum += queueEnergy.dequeue();
            }
            if (auxSum < minSum) {   
               minSum = auxSum;
               indPath = i;

            }
         }
      }
      writePath(indPath, pic.height() -1, queue);
      path = new int[queue.size()];
      k = queue.size()-1;
      while (!queue.isEmpty()) {
         path[k] = queue.dequeue();
         k--;
      }
      return path;
   }

   private boolean hasPath(int m, int n) {
      if (distTo[m][n] < Double.POSITIVE_INFINITY) return true;
      return false;
   }

   private void writePath(int m, int n, Queue<Integer> q){
      q.enqueue(m);
      if (edgeTo[m][n].y == n) return;
      writePath(information[m][n].xfather, information[m][n].yfather, q);
   }

   private void writePathEnergy(int m, int n, Queue<Double> q){
      q.enqueue(information[m][n].energy);
      if (edgeTo[m][n].y == n) return;
      writePathEnergy(information[m][n].xfather, information[m][n].yfather, q);
   }

   private void relax(Node e) {
      for (Node w : nextDown(information[e.x][e.y])){
         if(distTo[w.x][w.y] > distTo[e.x][e.y] + e.energy) {
            distTo[w.x][w.y] = distTo[e.x][e.y] + e.energy;
            w.xfather = e.x;
            w.yfather = e.y;
            edgeTo[w.x][w.y] = e;
         }
      }
   }

/*   // sequence of indices for horizontal seam
   public int[] findHorizontalSeam() {
      
   }


   // remove horizontal seam from current picture
   public    void removeHorizontalSeam(int[] seam) {}


*/
   // remove vertical seam from current picture
   public void removeVerticalSeam(int[] seam) {
      int i, j, k;
      boolean found = false;
      Picture newPic = new Picture(pic.width()-1, pic.height());
      for(j = 0; j < pic.height(); j++) {
         found = false;
         for(i = 0; i < pic.width()-1; i++) {
            if(found == true || i == seam[j]) {
               found = true;
               newPic.set(i, j, pic.get(i+1, j));
            }
            else {
               newPic.set(i, j, pic.get(i, j));
            }
         }
      }
      pic = newPic;
      width -= 1;
   }

   // do unit testing of this class
   public static void main(String[] args) {
      return ;
   }

}