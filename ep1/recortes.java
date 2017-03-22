// Leitura
    public static void main(String[] args) {
      StackOfString pilha;
      pilha = new StackOfString(20);

      while (!StdIn.isEmpty()) {
         String str = StdIn.readString();
         if (!str.equals("-"))
            pilha.push(str);
         else if (!pilha.isEmpty()) 
                 StdOut.println(pilha.pop() + " ");
      }
      StdOut.println("(" + pilha.size() + " left on stack)");
   }