/*

Neste exercício programa vocês farão mais um contador de palavras. Dessa vez, trata-se de um utilitário que receberá várias strings com várias palavras, e deverá dar informações variadas.

Mais precisamente, vocês deverão criar uma classe WordFinder. Essa classe deverá receber, em seu construtor, um vetor de Strings. Essas strings são compostas de palavras separadas por espaços. Não haverá pontuação ou caracteres especiais. Os seguintes métodos deverão ser implementados em WordFinder:

    String getMax(): retorna a palavra que se repete mais vezes nas strings dadas. Caso a palavra apareça mais de uma vez na mesma string, ignore.
    String containedIn(int a, int b): retorna uma palavra que apareça tanto na string de índice a do vetor quanto na string de índice b. Se não tiver nenhuma, retorne null. Se tiver mais de uma, retorne a que quiser.
    int[] appearsIn(String s): recebe uma palavra e retorna um vetor com os índices das strings do vetor inicial em que ela apareça. Se não aparecer em nenhum, retorne um vetor vazio.

Aconselhamos a utilização de uma HashTable do Sedgewick (como a SeparateChainingHashST.java) para estruturar este problema.

*/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import java.lang.StringBuilder;
import java.util.LinkedList;

import java.lang.NullPointerException;

public class WordFinder {

	SeparateChainingHashST<String, LinkedList<Integer>> wf;
 	
	//Constructor
	public WordFinder (String[] str) {
		wf = new SeparateChainingHashST<>();
		StringBuilder word = new StringBuilder();
		LinkedList<Integer> position = null;
		char carac = 0;
		int num;
		for (int i = 0; i < str.length; i++) {
			num = 0;
			for (int j = 0; j < str[i].length(); j++) {
				StdOut.println("Olhando pra:    " + str[i]);
				carac = str[i].charAt(j);
				StdOut.println("Pegou:   " + carac);
				if ((carac >= 65 && carac <= 90) || (carac >= 97 && carac <=122)){
					word.append(carac);
				}
				else if (carac == 32) {
					StdOut.println("Formou:    " + word);
					StdOut.println("Igual a espaço? " + word.equals(" "));
					position = wf.get(word.toString());
					if (position == null) {
						position = new LinkedList<Integer>();
					}
					if(!position.contains(i)) {
						position.add(i);
					}
					wf.put(word.toString(), position);
					word = new StringBuilder();
					num++;
				}
			}
		}
	}

	//retorna a palavra que se repete mais vezes nas strings dadas. 
	//Caso a palavra apareça mais de uma vez na mesma string, ignore.
	public String getMax() {
    	if (wf == null) throw new NullPointerException();
		Iterable<String> words = wf.keys();
		String str = null;
		int min = Integer.MIN_VALUE;
		int tmp;
		for (String s : words) {
			StdOut.println("No for        :" + s + "->" + wf.get(s).size() );
			tmp = wf.get(s).size();
			if (tmp > min) {
				min = tmp;
				str = s;
			}
		}
		return str;
	} 

	//retorna uma palavra que apareça tanto na string de índice 
	//a do vetor quanto na string de índice b. Se não tiver nenhuma, 
	//retorne null. Se tiver mais de uma, retorne a que quiser.
    public String containedIn(int a, int b) {
    	if (wf == null) throw new NullPointerException();
    	Iterable<String> words = wf.keys();
    	LinkedList<Integer> position;
    	for (String word : words) {
    		position = wf.get(word);
    		if (position.contains(a) && position.contains(b)) {
    			return word;
    		}
    	}
    	return null;
    }

	//recebe uma palavra e retorna um vetor com os índices das strings 
	//do vetor inicial em que ela apareça. Se não aparecer em nenhum, 
	//retorne um vetor vazio.
    public int[] appearsIn(String str) {
    	if (wf == null) throw new NullPointerException();
    	LinkedList<Integer> position;
    	int cont = 0;
    	int v[] = new int[0];
    	if (wf.contains(str)) {
    		StdOut.println("Achei a str: " + str);
    		position = wf.get(str);
    		v = new int[position.size()];
    		for (Integer i : position) {
    			StdOut.println("Position : " + i);
    			v[cont] = i;
    			StdOut.println("Position cont: " + v[cont]);
    			cont++;
    		}
    	}
    	return v;
    }

    public static void main (String[] args) {
    	String[] str = new String[3];
    	str[0] = "hoje eu acordei seila ";
    	str[1] = " oi tudo bem eu seila seila ";
    	str[2] = " acordei para sorrir e mostrar os dentes hoje ";
    	WordFinder wf = new WordFinder(str);
    	StdOut.println("getMax: " + wf.getMax());
    	StdOut.println("getMax: " + wf.getMax());
    	StdOut.println("apeearsIn: " + wf.appearsIn("hoje"));
    	StdOut.println("apeearsIn: " + wf.appearsIn("não"));
    	StdOut.println("getMax: " + wf.getMax());
    	StdOut.println("containedIN: " + wf.containedIn(0,1));
    	StdOut.println("containedIN: " + wf.containedIn(0,2));
    }

}