public class Trie {
    private NodoTrie raiz;

    public Trie() {
        raiz = new NodoTrie();
    }

    // mati
    public void insertar(String w) {
        NodoTrie actual = raiz;

        for (int i = 0; i < w.length(); i++) {
            int indice = w.charAt(i) - 'A';
            if (actual.P[indice] == null) {
                actual.P[indice] = new NodoTrie();
            }
            actual = actual.P[indice];
        }

        int indiceFinal = w.charAt(w.length() - 1) - 'A';
        actual.B = actual.B | (1 << indiceFinal);
    }

    // mati
    public boolean buscar(String w) {
        NodoTrie actual = raiz;

        for (int i = 0; i < w.length(); i++) {
            int indice = w.charAt(i) - 'A';
            if (actual.P[indice] == null) {
                return false;
            }
            actual = actual.P[indice];
        }

        int indiceFinal = w.charAt(w.length() - 1) - 'A';
        return (actual.B & (1 << indiceFinal)) != 0;
    }

    // mio
    public void eliminar(String w) {
        if (!buscar(w)) {
            return;
        }

        NodoTrie actual = raiz;

        for (int i = 0; i < w.length() - 1; i++) {
            int indice = w.charAt(i) - 'A';
            actual = actual.P[indice];
        }

        int indiceFinal = w.charAt(w.length() - 1) - 'A';
        actual.B = actual.B & ~(1 << indiceFinal);
    }

    // mio
    public void autocompletar(String s) {
        NodoTrie actual = raiz;
        NodoTrie padre = null;
        int ultimoIndice = -1;

        for (int i = 0; i < s.length(); i++) {
            ultimoIndice = s.charAt(i) - 'A';
            if (actual.P[ultimoIndice] == null) {
                System.out.println("No existen palabras con el prefijo ingresado.");
                return;
            }
            padre = actual;
            actual = actual.P[ultimoIndice];
        }

        System.out.println("Sugerencias para " + s + ":");

        if (padre != null && (padre.B & (1 << ultimoIndice)) != 0) {
            System.out.println(s);
        }

        buscarSugerencias(actual, s);
    }

    // mio
    private void buscarSugerencias(NodoTrie nodo, String palabraParcial) {
        for (int i = 0; i < 26; i++) {
            if (nodo.P[i] != null) {
                char letra = (char) (i + 'A');
                String nuevaPalabra = palabraParcial + letra;

                if ((nodo.B & (1 << i)) != 0) {
                    System.out.println(nuevaPalabra);
                }
                buscarSugerencias(nodo.P[i], nuevaPalabra);
            }
        }
    }
}