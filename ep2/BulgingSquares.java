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
    
   
	public static void mudaPincel (int x, int y, int tipo) {
		/* Recebe inteiros x e y representando posições em uma matriz 15x15
		 * e um inteiro tipo, que identifica qual quadrado será desenhado,
		 * se for 0 é o quadrado de tamanho normal (RADIUS_MAX), caso contrário
		 * o pequeno (RADIUS_MIN), e então muda a cor do pincel dependendo da 
		 * posição na matriz e do tipo de quadrado a ser desenhado. */
		
		if (tipo == 0) {
			if (x % 2 == 0) {
				if (y % 2 == 0)
					StdDraw.setPenColor(Color.black);
				else 
					StdDraw.setPenColor(Color.white);
			}
			else {
				if (y % 2 != 0)
					StdDraw.setPenColor(Color.black);
				else 
					StdDraw.setPenColor(Color.white);
			}
		}
		else {
			if (x % 2 == 0) {
				if (y % 2 == 0)
					StdDraw.setPenColor(Color.white);
				else 
					StdDraw.setPenColor(Color.black);
			}
			else {
				if (y % 2 != 0)
					StdDraw.setPenColor(Color.white);
				else 
					StdDraw.setPenColor(Color.black);
			}
		}
	}
   
	public static void pintaQuadrado (double m, double n, int x, int y) {
		/* Recebe doubles m e n representando um ponto e inteiros x e y
		 * representando uma posição em uma matriz 15x15, e pinta um quadrado de 
		 * tamanho normal (RADIUS_MAX) de centro m,n. */
		mudaPincel(x, y, 0);
		StdDraw.filledSquare(m, n, RADIUS_MAX);
	}
	
	/* Foi definido que a posição dos quadradinhos será +- 2.75 do centro, 
	 * escolhido a partir de sucessivos testes. */
	 
	public static void pintaQuadradinhoA(double m, double n, int x, int y) {
		/* Recebe doubles m e n representando um ponto e inteiros x e y
		 * representando uma posição em uma matriz 15x15, e pinta um quadrado de 
		 * tamanho pequeno (RADIUS_MIN) de centro m,n 
		 * posicionado nos 1º e 3º quadrante */
		mudaPincel(x, y, 1);
		StdDraw.filledSquare(m + 2.75, n + 2.75, RADIUS_MIN);
		StdDraw.filledSquare(m - 2.75, n - 2.75, RADIUS_MIN);
	}
	
	public static void pintaQuadradinhoB (double m, double n, int x, int y) {
		/* Recebe doubles m e n representando um ponto e inteiros x e y
		 * representando uma posição em uma matriz 15x15, e pinta um quadrado de 
		 * tamanho pequeno (RADIUS_MIN) de centro m,n 
		 * posicionado nos 2º e 4º quadrante */
		mudaPincel(x, y, 1);
		StdDraw.filledSquare(m - 2.75, n + 2.75, RADIUS_MIN);
		StdDraw.filledSquare(m + 2.75, n - 2.75, RADIUS_MIN);
	}
	
	public static void pintaQuadradinhoC (double m, double n, int x, int y) {
		/* Recebe doubles m e n representando um ponto e inteiros x e y
		 * representando uma posição em uma matriz 15x15, e pinta um quadrado de 
		 * tamanho pequeno (RADIUS_MIN) de centro m,n 
		 * posicionado no eixo x à esquerda do centro */
		mudaPincel(x, y, 1);
		StdDraw.filledSquare(m + 2.75, n + 2.75, RADIUS_MIN);
		StdDraw.filledSquare(m + 2.75, n - 2.75, RADIUS_MIN);
	}
	
	public static void pintaQuadradinhoD (double m, double n, int x, int y) {
		/* Recebe doubles m e n representando um ponto e inteiros x e y
		 * representando uma posição em uma matriz 15x15, e pinta um quadrado de 
		 * tamanho pequeno (RADIUS_MIN) de centro m,n 
		 * posicionado no eixo x à direta do centro */
		mudaPincel(x, y, 1);
		StdDraw.filledSquare(m - 2.75, n + 2.75, RADIUS_MIN);
		StdDraw.filledSquare(m - 2.75, n - 2.75, RADIUS_MIN);
	}
	
	public static void pintaQuadradinhoE (double m, double n, int x, int y) {
		/* Recebe doubles m e n representando um ponto e inteiros x e y
		 * representando uma posição em uma matriz 15x15, e pinta um quadrado de 
		 * tamanho pequeno (RADIUS_MIN) de centro m,n 
		 * posicionado no eixo y à cima do centro */
		mudaPincel(x, y, 1);
		StdDraw.filledSquare(m - 2.75, n - 2.75, RADIUS_MIN);
		StdDraw.filledSquare(m + 2.75, n-2.75, RADIUS_MIN);
	}
	
	public static void pintaQuadradinhoF (double m, double n, int x, int y) {
		/* Recebe doubles m e n representando um ponto e inteiros x e y
		 * representando uma posição em uma matriz 15x15, e pinta um quadrado de 
		 * tamanho pequeno (RADIUS_MIN) de centro m,n 
		 * posicionado no eixo y à baixo do centro */
		mudaPincel(x, y, 1);
		StdDraw.filledSquare(m + 2.75, n + 2.75, RADIUS_MIN);
		StdDraw.filledSquare(m - 2.75, n + 2.75, RADIUS_MIN);
	}
	
    public static void main(String[] args) {
		int i, j;
		double m, n;
        
      // set the scale of the coordinate system
      StdDraw.setXscale(XMIN, XMAX);
      StdDraw.setYscale(YMIN, YMAX);
      StdDraw.enableDoubleBuffering();
       
      // clear the background
      StdDraw.clear(StdDraw.WHITE);
		
		/*Vamos definir um raio de 62 para o circulo da figura, 
		* escolhido a partir de sucessivos testes. */
			
		m = XMIN+5;
		for (i = 0; i < 15; i++) {
			n = YMAX -5 ;
			for (j = 0; j < 15; j++) {
				if (m*m + n*n <= 3844) {
					if ((m < 0 && n > 0) || (m > 0 && n < 0)) {
						pintaQuadrado(m, n, i, j);
						pintaQuadradinhoA (m, n, i, j);
					}
					else if ((m < 0 && n < 0) || (m > 0 && n > 0)) {
						pintaQuadrado(m, n, i, j);
						pintaQuadradinhoB (m, n, i, j);
					}
					
					else if (n == 0 && m < 0) {
						pintaQuadrado(m, n, i, j);
						pintaQuadradinhoC (m, n, i, j);
					}
					
					else if (n == 0 && m > 0) {
						pintaQuadrado(m, n, i, j);
						pintaQuadradinhoD (m, n, i, j);
					}
					
					else if (n > 0 && m == 0) {
						pintaQuadrado(m, n, i, j);
						pintaQuadradinhoE (m, n, i, j);
					}
					
					else if(n < 0 && m == 0) {
						pintaQuadrado(m, n, i, j);
						pintaQuadradinhoF (m, n, i, j);
					}
					else
						pintaQuadrado(m, n, i, j);
				}
				else
					pintaQuadrado(m, n, i, j);
				n -= 10;
			}
			m += 10;
		}

	   // copy offscreen buffer to onscreen
	   StdDraw.show();
   }

} 
