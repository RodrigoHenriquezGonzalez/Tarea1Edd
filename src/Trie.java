import java.util.ArrayList;

public class Trie {

    private NodoTrie raiz;

    public Trie() {
        raiz = new NodoTrie();
    }

    // mati
    public void insertar(String w) {

        NodoTrie actual = raiz;

        for (int i = 0; i < w.length() - 1; i++) {

            int indice = w.charAt(i) - 'A';

            if (actual.P[indice] == null) {
                actual.P[indice] = new NodoTrie();
            }

            actual = actual.P[indice];
        }

        int indiceFinal =
                w.charAt(w.length() - 1) - 'A';

        actual.B =
                actual.B | (1 << indiceFinal);
    }

    // mati
    public boolean buscar(String w) {

        NodoTrie actual = raiz;

        for (int i = 0; i < w.length() - 1; i++) {

            int indice = w.charAt(i) - 'A';

            if (actual.P[indice] == null) {
                return false;
            }

            actual = actual.P[indice];
        }

        int indiceFinal =
                w.charAt(w.length() - 1) - 'A';

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

        int indiceFinal =
                w.charAt(w.length() - 1) - 'A';

        actual.B =
                actual.B & ~(1 << indiceFinal);
    }

    // mio
    public ArrayList<String> autocompletar(String s) {

        ArrayList<String> sugerencias =
                new ArrayList<>();

        NodoTrie actual = raiz;

        // Buscar el nodo correspondiente al prefijo
        for (int i = 0; i < s.length() - 1; i++) {

            int indice = s.charAt(i) - 'A';

            if (actual.P[indice] == null) {

                System.out.println(
                        "No existen palabras con el prefijo ingresado."
                );

                return sugerencias;
            }

            actual = actual.P[indice];
        }

        int indiceFinal =
                s.charAt(s.length() - 1) - 'A';

        // Comprobar que existe el último carácter
        if (actual.P[indiceFinal] == null &&
                (actual.B & (1 << indiceFinal)) == 0) {

            System.out.println(
                    "No existen palabras con el prefijo ingresado."
            );

            return sugerencias;
        }

        // El prefijo también es una palabra
        if ((actual.B & (1 << indiceFinal)) != 0) {

            sugerencias.add(s);
        }

        // Buscar palabras que continúan
        if (actual.P[indiceFinal] != null) {

            buscarSugerencias(
                    actual.P[indiceFinal],
                    s,
                    sugerencias
            );
        }

        // Mostrar sugerencias
        System.out.println(
                "Sugerencias para " + s + ":"
        );

        for (String palabra : sugerencias) {
            System.out.println(palabra);
        }

        return sugerencias;
    }

    // mio
    private void buscarSugerencias(
            NodoTrie nodo,
            String palabraParcial,
            ArrayList<String> sugerencias) {

        // Revisar palabras que terminan en este nodo
        for (int i = 0; i < 26; i++) {

            if ((nodo.B & (1 << i)) != 0) {

                char letra =
                        (char) (i + 'A');

                String palabra =
                        palabraParcial + letra;

                sugerencias.add(palabra);
            }
        }

        // Revisar los siguientes nodos
        for (int i = 0; i < 26; i++) {

            if (nodo.P[i] != null) {

                char letra =
                        (char) (i + 'A');

                String nuevaPalabra =
                        palabraParcial + letra;

                buscarSugerencias(
                        nodo.P[i],
                        nuevaPalabra,
                        sugerencias
                );
            }
        }
    }
}