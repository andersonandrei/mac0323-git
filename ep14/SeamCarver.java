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

   // width of current picture
   public int width() {
      return width;
   }

   // height of current picture
   public int height() {
      return height;
   }

   // energy of pixel at column x and row y
   public  double energy(int x, int y) {
      //we have to take the Colo with the Picture class, after, 
      //take de coordinates (r,g,b) with Color class
      //StdOut.println("oi ");
      //StdOut.println("raiz: " + Math.sqrt(deltaSquare(x,y,0) + deltaSquare(x,y,1)));
      return Math.sqrt(deltaSquare(x,y,0) + deltaSquare(x,y,1));
   }

   private Node[][] energyMatrix() {
      int i, j;
      information = new Node[pic.width()][pic.height()];
      for (i = 0; i < pic.width(); i++) {
         for (j = 0; j < pic.height(); j++) {
            //StdOut.println("Calculando info pra: " + i + "" + j);
            information[i][j] = new Node(i,j, energy(i, j));
         }
      }
      return information;
   }

   private double deltaSquare (int x, int y, int option) {
      //StdOut.println("oi 2");
      Color c1, c2;
      double red, green, blue;
      //StdOut.println("oi 3");
      if (option == 0) {
         //StdOut.println("oi 4");
         if(x-1 > 0 ) c1 = pic.get(x-1, y);
         else {
            //StdOut.println("calc1: " + (x-1 + pic.width() - 1) % pic.width());
            c1 = pic.get((x-1 + pic.width()) % pic.width(), y);
         }
         if (x+1 < pic.width()) c2 = pic.get(x+1, y);
         else c2 = pic.get((x+1 + pic.width()) % pic.width(), y);

      }
      else {
         //StdOut.println("oi 5");
         if(y-1 > 0 ) c1 = pic.get(x, y-1);
         else c1 = pic.get(x, (y-1 + pic.height()) % pic.height());
         if (y+1 < pic.height()) c2 = pic.get(x, y+1);
         else c2 = pic.get(x, (y+1 + pic.height()) % pic.height());
      }
      //StdOut.println("oi 6");
      red = c2.getRed() - c1.getRed();
      green = c2.getGreen() - c1.getGreen();
      blue = c2.getBlue() - c1.getBlue();
      //StdOut.println("oi 15");
      return (red * red) + (green * green) + (blue * blue);
   }

   private Iterable<Node> nextDown (Node root) {
      int i, j;
      Queue<Node> next = new Queue<Node>();
      i = root.x;
      j = root.y;
      //StdOut.println("dimensoes: " + pic.height() + "" + pic.width());
      if(j+1 < pic.height()) {
         //StdOut.println("Liberou pro j: " + (j+1));
         if(i-1 >= 0) {
            //StdOut.println("Liberou pro i: " + (i-1));
            next.enqueue(information[i-1][j+1]);
         }
         next.enqueue(information[i][j+1]);
         if(i+1 < pic.width()) {
            //StdOut.println("Liberou pro i: " + (i+1));
            next.enqueue(information[i+1][j+1]);
         }
      }
      return next;
   }


   // sequence of indices for vertical seam
   public int[] findVerticalSeam() {
      distTo = new double[pic.width()][pic.height()]; //the last line is to first and last node
      edgeTo = new Node[pic.width()][pic.height()];
      int k;
      Queue<Integer> queue = new Queue<Integer>();
      //int first = pic.width() * pic.height() , last = pic.width() * pic.height() + 1; 
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
/*      Node first = new Node(pic.width()-1, pic.height(), 0.0);
      first.xfather = pic.height();
      first.yfather = pic.height();
      edgeTo[pic.width()-1][pic.height()] = */

      information = energyMatrix();
      for (int i = 0; i < pic.height(); i++) {
         for (int j = 0; j < pic.width(); j++) {
            StdOut.println("---Descer a partir de " + j + "" + i);
            relax(information[j][i]);
            /*for (Node e : nextDown(information[j][i])) {
               StdOut.println("Mandando pro relax e" + e.x + "" + e.y);
               relax(e);
            }*/
         }
      }
      StdOut.println("Relaxou");
      for (int i = 0; i < pic.width(); i++) {
         StdOut.println("Ve caminho em " + i + "" + (pic.height() - 1));
         if (hasPath(i,pic.height() - 1)) {
            StdOut.println("Tem um caminho");
            k = 0;
            writePath(i, pic.height() - 1, queue);
            StdOut.println("Tamanho da pilha:"+ queue.size());
            path = new int[queue.size()];
            StdOut.println("Criou vetor");
            while (!queue.isEmpty()) {
               StdOut.println("Atribuiu");
               path[k] = queue.dequeue();
               k++;
            }
            StdOut.println("Retornou path");
            return path;
         } 
      }
      StdOut.println("nao achou caminho");
      return null;
   }

   private boolean hasPath(int m, int n) {
      if (distTo[m][n] < Double.POSITIVE_INFINITY) return true;
      return false;
   }

   private void writePath(int m, int n, Queue<Integer> q){
      StdOut.println("Escrevendo path: " + m + "" + n);
      q.enqueue(n);
      if (edgeTo[m][n].y == n) return;
      StdOut.println("Pushou e vai olhar pra : " + edgeTo[m][n].x + "" + edgeTo[m][n].y);
      writePath(edgeTo[m][n].x, edgeTo[m][n].y, q);
   }

   private void relax(Node e) {
      StdOut.println("relaxando" + e.x + "" + e.y);
      StdOut.println("pai dele: "+ e.xfather + "" + e.yfather);
      for (Node w : nextDown(information[e.x][e.y])){
         StdOut.print("na mão "+ w.x + "" + w.y);
         StdOut.println("disTO "+ distTo[w.x][w.y] + " ");
         //StdOut.println("Na mao "+distTo[w.x][w.y]);
         //StdOut.println("relax b"+distTo[0][0]);
         //StdOut.println("relax c"+e.energy);

         if(distTo[w.x][w.y] > distTo[e.xfather][e.yfather] + e.energy) {
            StdOut.println("Atualizou dist de : "+ w.x + "" + w.y);
            StdOut.println("colocou : "+ distTo[e.xfather][e.yfather] + "   " + e.energy);
            StdOut.println("fathers : "+ e.x + "   " + e.y);

            distTo[w.x][w.y] = distTo[e.xfather][e.yfather] + e.energy;
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



   // remove vertical seam from current picture
   public void removeVerticalSeam(int[] seam) {

   }
*/
   // do unit testing of this class
   public static void main(String[] args) {
      return ;
   }

}