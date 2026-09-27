import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

    private static void cargarDiccionario(
            Trie trie,
            String nombreArchivo) {

        try {

            Scanner lector =
                    new Scanner(new File(nombreArchivo));

            while (lector.hasNextLine()) {

                String linea =
                        lector.nextLine().trim();

                if (linea.matches("^[A-Z]+$")) {

                    trie.insertar(linea);
                }
            }

            lector.close();

        } catch (FileNotFoundException e) {

            System.out.println(
                    "No se encontró el archivo "
                            + nombreArchivo
            );
        }
    }

    public static void main(String[] args) {

        Trie miTrie = new Trie();

        cargarDiccionario(
                miTrie,
                "diccionario.txt"
        );

        Scanner scanner =
                new Scanner(System.in);

        int opcion = 0;

        // Lista donde se guardarán las sugerencias
        ArrayList<String> sugerencias =
                new ArrayList<>();

        do {

            System.out.println("\n--- MENÚ ---");

            System.out.println(
                    "1. Buscar una palabra"
            );

            System.out.println(
                    "2. Insertar una nueva palabra"
            );

            System.out.println(
                    "3. Eliminar una palabra existente"
            );

            System.out.println(
                    "4. Ingresar un prefijo y obtener sugerencias"
            );

            System.out.println(
                    "5. Seleccionar una de las sugerencias"
            );

            System.out.println(
                    "6. Salir de la aplicación"
            );

            System.out.print(
                    "Seleccione una opción: "
            );

            try {

                opcion =
                        Integer.parseInt(
                                scanner.nextLine()
                        );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Error: Ingrese un número válido."
                );

                continue;
            }

            // OPCIONES 1, 2, 3 Y 4
            if (opcion >= 1 && opcion <= 4) {

                System.out.print(
                        "Ingrese texto (solo mayúsculas): "
                );

                String entrada =
                        scanner.nextLine();

                if (!entrada.matches("^[A-Z]+$")) {

                    System.out.println(
                            "Entrada inválida. Use exclusivamente letras mayúsculas (A-Z)."
                    );

                    continue;
                }

                switch (opcion) {

                    // BUSCAR
                    case 1:

                        if (miTrie.buscar(entrada)) {

                            System.out.println(
                                    "La palabra está en el Trie."
                            );

                        } else {

                            System.out.println(
                                    "La palabra no está almacenada."
                            );
                        }

                        break;

                    // INSERTAR
                    case 2:

                        miTrie.insertar(entrada);

                        System.out.println(
                                "Palabra insertada exitosamente."
                        );

                        break;

                    // ELIMINAR
                    case 3:

                        miTrie.eliminar(entrada);

                        System.out.println(
                                "Proceso de eliminación finalizado."
                        );

                        break;

                    // AUTOCOMPLETAR
                    case 4:

                        sugerencias =
                                miTrie.autocompletar(
                                        entrada
                                );

                        break;
                }

                // OPCIÓN 5
            } else if (opcion == 5) {

                if (sugerencias.isEmpty()) {

                    System.out.println(
                            "Debe ejecutar la opción 4 primero para generar sugerencias."
                    );

                } else {

                    System.out.println(
                            "\n--- SUGERENCIAS ---"
                    );

                    // Mostrar las sugerencias numeradas
                    for (int i = 0;
                         i < sugerencias.size();
                         i++) {

                        System.out.println(
                                (i + 1)
                                        + ". "
                                        + sugerencias.get(i)
                        );
                    }

                    System.out.print(
                            "Seleccione una sugerencia: "
                    );

                    try {

                        int seleccion =
                                Integer.parseInt(
                                        scanner.nextLine()
                                );

                        if (seleccion >= 1 &&
                                seleccion <= sugerencias.size()) {

                            String palabraSeleccionada =
                                    sugerencias.get(
                                            seleccion - 1
                                    );

                            System.out.println(
                                    "Seleccionaste: "
                                            + palabraSeleccionada
                            );

                        } else {

                            System.out.println(
                                    "Número de sugerencia inválido."
                            );
                        }

                    } catch (NumberFormatException e) {

                        System.out.println(
                                "Ingrese un número válido."
                        );
                    }
                }

                // SALIR
            } else if (opcion != 6) {

                System.out.println(
                        "Opción inválida."
                );
            }

        } while (opcion != 6);

        scanner.close();

        System.out.println(
                "Aplicación finalizada."
        );
    }
}