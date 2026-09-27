import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    private static void cargarDiccionario(Trie trie, String nombreArchivo) {
        try {
            Scanner lector = new Scanner(new File(nombreArchivo));
            while (lector.hasNextLine()) {
                String linea = lector.nextLine().trim();
                if (linea.matches("^[A-Z]+$")) {
                    trie.insertar(linea);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo " + nombreArchivo);
        }
    }

    public static void main(String[] args) {
        Trie miTrie = new Trie();
        cargarDiccionario(miTrie, "diccionario.txt");

        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Buscar una palabra");
            System.out.println("2. Insertar una nueva palabra");
            System.out.println("3. Eliminar una palabra existente");
            System.out.println("4. Ingresar un prefijo y obtener sugerencias");
            System.out.println("5. Seleccionar una de las sugerencias");
            System.out.println("6. Salir de la aplicación");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
                continue;
            }

            if (opcion >= 1 && opcion <= 4) {
                System.out.print("Ingrese texto (solo mayúsculas): ");
                String entrada = scanner.nextLine();

                if (!entrada.matches("^[A-Z]+$")) {
                    System.out.println("Entrada inválida. Use exclusivamente letras mayúsculas (A-Z).");
                    continue;
                }

                switch (opcion) {
                    case 1:
                        if (miTrie.buscar(entrada)) System.out.println("La palabra está en el Trie.");
                        else System.out.println("La palabra no está almacenada.");
                        break;
                    case 2:
                        miTrie.insertar(entrada);
                        System.out.println("Palabra insertada exitosamente.");
                        break;
                    case 3:
                        miTrie.eliminar(entrada);
                        System.out.println("Proceso de eliminación finalizado.");
                        break;
                    case 4:
                        miTrie.autocompletar(entrada);
                        break;
                }
            } else if (opcion == 5) {
                System.out.println("Debe ejecutar la opción 4 primero para generar sugerencias.");
            } else if (opcion != 6) {
                System.out.println("Opción inválida.");
            }

        } while (opcion != 6);
    }
}
