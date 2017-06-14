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
import java.lang.NullPointerException;
import java.awt.Color;


public class SeamCarver {

   Picture pic, backup;
   int width; 
   int height;


   // create a seam carver object based on the given picture
   public SeamCarver(Picture picture){
      pic = picture;
      backup = picture;
      width = pic.width;
      height = pic.height;
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
      return sqrt(deltaSquare(x,y,0) + deltaSquare(x,y,1));
   }

   private double deltaSquare (int x, int y, int option) {
      Color c1, c2;
      double red, green, blue;
      if (option == 0) {
         c1 = pic.get(x-1, y);
         c2 = pic.get(x+1, y);
      }
      else {
         c1 = pic.get(x, y-1);
         c2 = pic.get(x, y+1);
      }
      red = c2.getRed() - c1.getRed();
      green = c2.getGreen() - c1.getGreen();
      blue = c2.getBlue() - c1.getBlue();
      return (red * red) + (green * green) + (blue * blue);
   }

   // sequence of indices for horizontal seam
   public   int[] findHorizontalSeam() {}

   // sequence of indices for vertical seam
   public   int[] findVerticalSeam() {}

   // remove horizontal seam from current picture
   public    void removeHorizontalSeam(int[] seam) {}

   // remove vertical seam from current picture
   public    void removeVerticalSeam(int[] seam) {}

   // do unit testing of this class
   public static void main(String[] args) {}

}