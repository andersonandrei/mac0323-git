/******************************************************************************
 *  Compilation:  javac BulgingSquares.java
 *  Execution:    java BulgingSquares
 *  Dependencies: StdDraw.java, java.awt.Color
 *
 *  Program draws an optical illusion from Akiyoshi Kitaoka. The center appears 
 *  to bulge outwards even though all squares are the same size. 
 *
 *  meu_prompt > java BulgingSquares
 *
 *  Exercise 14 http://introcs.cs.princeton.edu/java/15inout/
 * 
 ******************************************************************************/

// Standard draw. This class provides a basic capability for creating
// drawings with your programs. It uses a simple graphics model that
// allows you to create drawings consisting of points, lines, and
// curves in a window on your computer and to save the drawings to a
// file.
// https://www.ime.usp.br/~pf/sedgewick-wayne/stdlib/documentation/index.html
// http://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html
import edu.princeton.cs.algs4.StdDraw; // StdDraw.setXscale, StdDraw.setYscale, ...

import edu.princeton.cs.algs4.StdOut; //meeeeu

import java.awt.Color; // StdDraw.WHITE, StdDraw.BLACK

public class BulgingSquares {
    // constantes... vixe! use se desejar
    private static final double XMIN   = -75;
    private static final double XMAX   =  75;
    private static final double YMIN   = -75;
    private static final double YMAX   =  75;
    private static final double MARGIN =   2;
    private static final double RADIUS_MAX =   5;
    private static final double DIAM_MAX   = 2*RADIUS_MAX;
    private static final double RADIUS_MIN = 1.5;
    private static final double DIAM_MIN   = 2*RADIUS_MIN;
    
	public static void pintaQuadradinhoA(double m, double n) {
		if (m % 2 == 0) //pretos nos y pares => quadradinhos brancos
			StdDraw.setPenColor(Color.white);
		else 
			StdDraw.setPenColor(Color.black);
		StdDraw.filledSquare(m+7.25,n+7.25,RADIUS_MIN);
		StdDraw.filledSquare(m-7.25,n-7.25,RADIUS_MIN);
	}
	
	public static void pintaQuadradinhoB (double m, double n) {
		if (m % 2 == 0) //pretos nos y pares => quadradinhos brancos
			StdDraw.setPenColor(Color.white);
		else 
			StdDraw.setPenColor(Color.black);
		StdDraw.filledSquare(m-7.25,n+7.25,RADIUS_MIN);
		StdDraw.filledSquare(m+7.25,n-7.25,RADIUS_MIN);
	}
	
	public static void pintaQuadradinhoC (double m, double n) {
		if (m % 2 == 0) //pretos nos y pares => quadradinhos brancos
			StdDraw.setPenColor(Color.white);
		else 
			StdDraw.setPenColor(Color.black);
		StdDraw.filledSquare(m+7.25,n+7.25,RADIUS_MIN);
		StdDraw.filledSquare(m-7.25,n-7.25,RADIUS_MIN);
	}
	
	public static void pintaQuadradinhoD (double m, double n) {
		if (m % 2 == 0) //pretos nos y pares => quadradinhos brancos
			StdDraw.setPenColor(Color.white);
		else 
			StdDraw.setPenColor(Color.black);
		StdDraw.filledSquare(m-7.25,n+7.25,RADIUS_MIN);
		StdDraw.filledSquare(m-7.25,n-7.25,RADIUS_MIN);
	}
	
	public static void pintaQuadradinhoE (double m, double n) {
		if (m % 2 == 0) //pretos nos y pares => quadradinhos brancos
			StdDraw.setPenColor(Color.white);
		else 
			StdDraw.setPenColor(Color.black);
		StdDraw.filledSquare(m-7.25,n-7.25,RADIUS_MIN);
		StdDraw.filledSquare(m+7.25,n-7.25,RADIUS_MIN);
	}
	
	public static void pintaQuadradinhoF (double m, double n) {
		if (m % 2 == 0) //pretos nos y pares => quadradinhos brancos
			StdDraw.setPenColor(Color.white);
		else 
			StdDraw.setPenColor(Color.black);
		StdDraw.filledSquare(m+7.25,n-7.25,RADIUS_MIN);
		StdDraw.filledSquare(m+7.25,n+7.25,RADIUS_MIN);
	}
	
	
	
    public static void main(String[] args) {
        int i, j, k ;
        double m, n;
        
        // set the scale of the coordinate system
        StdDraw.setXscale(XMIN-MARGIN, XMAX+MARGIN);
        StdDraw.setYscale(YMIN-MARGIN, YMAX+MARGIN);
        StdDraw.enableDoubleBuffering();
        
        // clear the background
        StdDraw.clear(StdDraw.WHITE);

        // Escreva sua solução a seguir
			//StdDraw.circle(0,0,13*RADIUS_MAX); //circulo principal
			 
			//StdDraw.filledSquare(XMIN+5,YMIN+5,RADIUS_MAX);
			//StdDraw.filledSquare(XMAX-5,YMAX-5,RADIUS_MAX);
			
			//StdDraw.square(XMIN+17.5,-2.5,RADIUS_MIN);
			
			//Fazendo o tabuleiro
			m = XMIN+5;
			for (i = 0; i < 15; i++) {
				if (i%2 == 0) {
					n = YMAX-5;
					for (j = 0; j < 7; j++) {
						//StdOut.println("Desenhando comc preto em" + m + n);
						StdDraw.filledSquare(m,n,RADIUS_MAX);
						n-=10;
						StdDraw.square(m,n,RADIUS_MAX);
						n-=10;
					}
					StdDraw.filledSquare(m,n,RADIUS_MAX);
				}
				else {
					n = YMAX-5;
					for (j = 0; j < 7; j++) {
						//StdOut.println("Desenhando comc branco em" + m + n);
						StdDraw.square(m,n,RADIUS_MAX);
						n-=10;
						StdDraw.filledSquare(m,n,RADIUS_MAX);						
						n-=10;
					}
					StdDraw.square(m,n,7*RADIUS_MAX);	
				}
				m += 10;
			}
			
			//Vamos definir um raio de 62 para o circulo da figura
			//Parametro definido a partir de sucessivos testes
			
			for (m = XMIN+5; m < 75; m += 10) {
				for (n = YMAX-5; n > -75; n-=10) {
					StdOut.println("Olhando pra "+ m + n);
					if (m*m + n*n <= 3844) {
						//casos
						//1º e 3º quadrante
						StdOut.println("entrou "+ m + n);
						if ((m < 0 && n > 0) || (m > 0 && n < 0)) {
							StdOut.println("A "+ m + n);
							pintaQuadradinhoA (m, n);
						}
						else if ((m < 0 && n < 0) || (m > 0 && n > 0)) {
							StdOut.println("B "+ m + n);
							pintaQuadradinhoB (m, n);
						}
						
						else if (m == 0 && n < 0) {
							StdOut.println("C "+ m + n);
							pintaQuadradinhoC (m, n);
						}
						
						else if (m == 0 && n > 0) {
							StdOut.println("D "+ m + n);
							pintaQuadradinhoD (m, n);
						}
						
						else if (m < 0 && n == 0) {
							StdOut.println("E "+ m + n);
							pintaQuadradinhoE (m, n);
						}
						
						else { // (x > 0 && y == 0)
							StdOut.println("F "+ m + n);
							pintaQuadradinhoF (m, n);
						}
					}
				}
			}

			//Mudando pincel de cor pra pintar no preto
			
			//StdDraw.setPenColor(Color.white);

			//Se o centro do quadrado tiver dentro do circulo, então
			//temos que desenhar os quadradinhos.
			
			
        
        // copy offscreen buffer to onscreen
        StdDraw.show();
        
        
    }

} 
