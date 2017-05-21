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
		for (int i = 0; i < str.length; i++) {
			carac = str[i].charAt(0);
			if (carac == 32) { 
				position = wf.get(word.toString());
				if (position == null){
					position = new LinkedList<Integer>();
				}
				if(!position.contains(i)){
					position.add(i);
				}
				wf.put(word.toString(), position);
			}

			if ((carac >= 65 && carac <= 90) || (carac >= 97 && carac <=122)){
				word.append(carac);
			}
		}
	}

	//retorna a palavra que se repete mais vezes nas strings dadas. 
	//Caso a palavra apareça mais de uma vez na mesma string, ignore.
	public String getMax() {
		Iterable<String> words = wf.keys();
		String str = null;
		int min = Integer.MIN_VALUE;
		int tmp;

		for (String s : words) {
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
    	LinkedList<Integer> position;
    	int cont = 0;
    	int v[] = null;

    	if (wf.contains(str)) {
    		position = wf.get(str);
    		v = new int[position.size()];
    		for (Integer i : position) {
    			v[cont] = i;
    			cont++;
    		}
    	}
    	return v;
    }

    public void main () {
    	return;
    }

}