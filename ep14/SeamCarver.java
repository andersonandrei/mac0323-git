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
import java.lang.IllegalArgumentException;
import java.awt.Color;

public class SeamCarver {
   Picture pic;
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
      if (picture == null) 
         throw new java.lang.NullPointerException();
      pic = picture;
      width = pic.width();
      height = pic.height();
   }

   // current picture
   public Picture picture() {
      return pic;
   }

   public int width() {
      return pic.width();
   }

   public int height() {
      return pic.height();
   }

   //**************************************
   //**********General*********************
   //**************************************

   // energy of pixel at column x and row y
   public  double energy(int x, int y) {
      if (x < 0 || x >= width() || y < 0 || y >= height()) 
         throw new java.lang.IndexOutOfBoundsException(); 
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
         else c1 = pic.get((x-1 + pic.width()) % pic.width(), y);
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

   private boolean hasPath(int m, int n) {
      if (distTo[m][n] < Double.POSITIVE_INFINITY) return true;
      return false;
   }

   private void writePathVertical(int m, int n, Queue<Integer> q){
      q.enqueue(m);
      if (edgeTo[m][n].y == n) return;
      writePathVertical(information[m][n].xfather, information[m][n].yfather, q);
   }

   private void writePathHorizontal(int m, int n, Queue<Integer> q){
      q.enqueue(n);
      if(edgeTo[m][n].x == m) return;
      writePathHorizontal(information[m][n].xfather, information[m][n].yfather, q);
   }

   private void writePathEnergyVertical(int m, int n, Queue<Double> q){
      q.enqueue(information[m][n].energy);
      if (edgeTo[m][n].y == n) return;
      writePathEnergyVertical(information[m][n].xfather, information[m][n].yfather, q);
   }

   private void writePathEnergyHorizontal(int m, int n, Queue<Double> q){
      q.enqueue(information[m][n].energy);
      if (edgeTo[m][n].x == m) return;
      writePathEnergyHorizontal(information[m][n].xfather, information[m][n].yfather, q);
   }

   //**************************************
   //**********   Vertical  ***************
   //**************************************

   // sequence of indices for vertical seam
   public int[] findVerticalSeam() {
      distTo = new double[pic.width()][pic.height()];
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
            relaxVertical(information[j][i]);
         }
      }
      return minPathVertical();
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

   private int[] minPathVertical() {
      int k;
      int indPath = 0;
      double minSum = Double.POSITIVE_INFINITY;
      double auxSum = 0.0;
      int verification;
      Queue<Double> queueEnergy = new Queue<Double>(); 
      Queue<Integer> queue = new Queue<Integer>(); 
      int[] path;
      int i;
      for (i = 0; i < pic.width(); i++) {
         verification = pic.width()-1;
         if (hasPath(i,pic.height() - 1)) {
            k = 0;
            writePathEnergyVertical(i, pic.height() - 1, queueEnergy);
            auxSum = 0.0;
            while (!queueEnergy.isEmpty()) {
               auxSum += queueEnergy.dequeue();
            }
            if (auxSum < minSum) {   
               minSum = auxSum;
               indPath = i;
            }
         }
      }
      writePathVertical(indPath, pic.height() -1, queue);
      path = new int[queue.size()];
      k = queue.size()-1;
      while (!queue.isEmpty()) {
         path[k] = queue.dequeue();
         k--;
      }
      return path;
   }

   // remove vertical seam from current picture
   public void removeVerticalSeam(int[] seam) {
      if (seam == null) 
         throw new java.lang.NullPointerException();
      if (height() == 1)
         throw new java.lang.IllegalArgumentException();
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

   private void relaxVertical(Node e) {
      for (Node w : nextDown(information[e.x][e.y])){
         if(distTo[w.x][w.y] > distTo[e.x][e.y] + e.energy) {
            distTo[w.x][w.y] = distTo[e.x][e.y] + e.energy;
            w.xfather = e.x;
            w.yfather = e.y;
            edgeTo[w.x][w.y] = e;
         }
      }
   }

   //**************************************
   //**********   Horizontal  *************
   //**************************************

   // sequence of indices for vertical seam
   public int[] findHorizontalSeam() {
      distTo = new double[pic.width()][pic.height()];
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
      for(int v = 0; v < pic.height(); v++) {
         distTo[0][v] = 0.0;
      }
      information = energyMatrix();
      for (int i = 0; i < pic.width(); i++) {
         for (int j = 0; j < pic.height(); j++) {
            relaxHorizontal(information[i][j]);
         }
      }
      return minPathHorizontal();
   }

   // sequence of indices for horizontal seam
   private void relaxHorizontal(Node e) {
      double sum;
      for (Node w : nextRight(information[e.x][e.y])){
        if(distTo[w.x][w.y] > distTo[e.x][e.y] + e.energy) {
            sum = distTo[e.x][e.y] + e.energy;
            distTo[w.x][w.y] = sum;
            w.xfather = e.x;
            w.yfather = e.y;
            edgeTo[w.x][w.y] = e;
         }
      }
   }

   private Iterable<Node> nextRight (Node root) {
      int i, j;
      Queue<Node> next = new Queue<Node>();
      i = root.x;
      j = root.y;
      if(i+1 < pic.width()) {
         if(j-1 >= 0) {
            next.enqueue(information[i+1][j-1]);
         }
         next.enqueue(information[i+1][j]);
         if(j+1 < pic.height()) {
            next.enqueue(information[i+1][j+1]);
         }
      }
      return next;
   }

   private int[] minPathHorizontal() {
      int k;
      int indPath = 0;
      Double minSum = Double.POSITIVE_INFINITY;
      Double auxSum = 0.0;
      Queue<Double> queueEnergy = new Queue<Double>(); 
      Queue<Integer> queue = new Queue<Integer>(); 
      int[] path;
      int i;
      for (i = 0; i < pic.height(); i++) {
         if (hasPath(pic.width() - 1, i)) {
            k = 0;
            writePathEnergyHorizontal(pic.width() - 1, i, queueEnergy);
            auxSum = 0.0;
            while (!queueEnergy.isEmpty()) {
               auxSum += queueEnergy.dequeue();
            }
            if (auxSum < minSum) {   
               minSum = auxSum;
               indPath = i;
            }
         }
      }
      writePathHorizontal(pic.width() -1, indPath, queue);
      path = new int[queue.size()];
      k = queue.size()-1;
      while (!queue.isEmpty()) {
         path[k] = queue.dequeue();
         k--;
      }
      return path;
   }
   
   // remove horizontal seam from current picture
   public void removeHorizontalSeam(int[] seam) {
      if (seam == null) 
         throw new java.lang.NullPointerException();
      if (width() == 1)
         throw new java.lang.IllegalArgumentException();
      int i, j, k;
      boolean found = false;
      Picture newPic = new Picture(pic.width(), pic.height()-1);
      for(j = 0; j < pic.width(); j++) {
         found = false;
         for(i = 0; i < pic.height()-1; i++) {
            if(found == true || i == seam[j]) {
               found = true;
               newPic.set(j, i, pic.get(j, i+1));
            }
            else {
               newPic.set(j, i, pic.get(j, i));
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