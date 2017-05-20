/*

Neste exercício programa vocês farão mais um contador de palavras. Dessa vez, trata-se de um utilitário que receberá várias strings com várias palavras, e deverá dar informações variadas.

Mais precisamente, vocês deverão criar uma classe WordFinder. Essa classe deverá receber, em seu construtor, um vetor de Strings. Essas strings são compostas de palavras separadas por espaços. Não haverá pontuação ou caracteres especiais. Os seguintes métodos deverão ser implementados em WordFinder:

    String getMax(): retorna a palavra que se repete mais vezes nas strings dadas. Caso a palavra apareça mais de uma vez na mesma string, ignore.
    String containedIn(int a, int b): retorna uma palavra que apareça tanto na string de índice a do vetor quanto na string de índice b. Se não tiver nenhuma, retorne null. Se tiver mais de uma, retorne a que quiser.
    int[] appearsIn(String s): recebe uma palavra e retorna um vetor com os índices das strings do vetor inicial em que ela apareça. Se não aparecer em nenhum, retorne um vetor vazio.

Aconselhamos a utilização de uma HashTable do Sedgewick (como a SeparateChainingHashST.java) para estruturar este problema.

*/


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SeparateChainingHashST;

import java.lang.NullPointerException;

public class WordFinder<String, Value> {
	private int size;
	private SeparateChainingHashST<String, Value> wf;
 	
	//Constructor
	public WordFinder (String[] s) {
		wf = new SeparateChainingHashST<String, Value> ();
	}

	//retorna a palavra que se repete mais vezes nas strings dadas. 
	//Caso a palavra apareça mais de uma vez na mesma string, ignore.
	public String getMax() {
		Iterable<String> words = wf.keys();
		int size ;
		Integer max = -1;
		String wordSelected;
		Integer cont = 0;
		for (String word : words) {
			size = wf.size();
			while (size > 0) {
				if (wf[size - 1].contains(word)) {
					cont++;
				}
				size--;
			}
			if (cont > max) {
				max = cont;
				wordSelected = word;
			}
			cont = 0;
		}
		return wordSelected;
	} 

	//retorna uma palavra que apareça tanto na string de índice 
	//a do vetor quanto na string de índice b. Se não tiver nenhuma, 
	//retorne null. Se tiver mais de uma, retorne a que quiser.
    public String containedIn(int a, int b) {
    	Iterable<String> wordsA = wf[a].keys();
    	for (String word : wordsA) {
    		if (wf[b].contains(word)) {
    			return word;
    		}
    	}
    	return null;
    }

	//recebe uma palavra e retorna um vetor com os índices das strings 
	//do vetor inicial em que ela apareça. Se não aparecer em nenhum, 
	//retorne um vetor vazio.
    public int[] appearsIn(String s) {
    	int[] ind = new int[wf.size()];
    	Integer size = wf.size();
    	Integer tam = 0;
    	Iterable<String> words;
    	Integer k;
    	while (tam < size) {
    		if (wf[tam].contains(s)) {
	    		words = wf[tam-1].keys();
    			k = 0;
	    		for (String word : words) {
	    			if (word.equals(s)){
	    				break;
	    			}
	    			else {
	    				k++;
	    			}
	    		}
	    		ind[tam] = k;
    		}
    		tam++;
    	}
    	return ind;
    }

    public void main () {
    	return;
    }

}