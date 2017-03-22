/* 
 * ImplementaÃ§Ã£o de uma pilha genÃ©rica com a politica de redimensionamento.
 * 
 */

public class Stack<Item> {

   private Item[] a;
   private int n;

   // construtor
   public Stack() {
      a = (Item[]) new Object[2];
      n = 0; // supÃ©rfluo
   }

   // a pilha mora em a[0..n-1]
   public boolean isEmpty() {
      return n == 0;
   }
   
   public void push(Item item) { 
      if (n == a.length) resize(2 * a.length);
      a[n++] = item;
   }

   public Item pop() {
      Item item = a[--n];
      a[n] = null; // para evitar loitering = objetos ociosos 
      if (n > 0 && n == a.length/4) resize(a.length/2);
      return item;
   }

   private void resize(int max) {
      Item[] temp = (Item[]) new Object[max];
      for (int i = 0; i < n; i++)
         temp[i] = a[i];
      a = temp;
   }
}