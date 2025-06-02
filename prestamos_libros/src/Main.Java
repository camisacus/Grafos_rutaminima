import java.util.*;

class Libro {
    String titulo;
    int copiasDisponibles;

    Libro(String titulo) {
        this.titulo = titulo;
        this.copiasDisponibles = 3;
    }
}

class Estudiante {
    String nombre;
    List<Libro> librosSolicitados = new ArrayList<>();

    Estudiante(String nombre) {
        this.nombre = nombre;
    }

    boolean solicitarLibro(Libro libro) {
        if (librosSolicitados.size() < 10 && libro.copiasDisponibles > 0) {
            librosSolicitados.add(libro);
            libro.copiasDisponibles--;
            return true;
        }
        return false;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Libro> libros = new ArrayList<>();
        List<Estudiante> estudiantes = new ArrayList<>();

        // Cargar libros
        libros.add(new Libro("Matemáticas"));
        libros.add(new Libro("Física"));
        libros.add(new Libro("Química"));
        libros.add(new Libro("Historia"));
        libros.add(new Libro("Literatura"));

        // Cargar estudiantes
        estudiantes.add(new Estudiante("Juan"));
        estudiantes.add(new Estudiante("María"));
        estudiantes.add(new Estudiante("Pedro"));
        estudiantes.add(new Estudiante("Ana"));
        estudiantes.add(new Estudiante("Luis"));

        System.out.println("=== SISTEMA DE PRÉSTAMO DE LIBROS ===");

        for (Estudiante estudiante : estudiantes) {
            System.out.println("\nEstudiante: " + estudiante.nombre);
            int librosPedidos = 0;

            while (librosPedidos < 10) {
                System.out.println("Libros disponibles:");
                for (int i = 0; i < libros.size(); i++) {
                    Libro l = libros.get(i);
                    System.out.println(i + ". " + l.titulo + " (" + l.copiasDisponibles + " copias)");
                }

                System.out.print("Ingrese el número del libro que desea (o -1 para terminar): ");
                int opcion = sc.nextInt();

                if (opcion == -1) break;
                if (opcion < 0 || opcion >= libros.size()) {
                    System.out.println("Opción inválida.");
                    continue;
                }

                Libro libroSeleccionado = libros.get(opcion);
                if (estudiante.solicitarLibro(libroSeleccionado)) {
                    System.out.println("Libro asignado.");
                    librosPedidos++;
                } else {
                    System.out.println("No se pudo asignar el libro (sin copias o límite alcanzado).");
                }
            }
        }

        // Mostrar resultados
        System.out.println("\n=== RESULTADOS DE LOS PRÉSTAMOS ===");
        for (Estudiante estudiante : estudiantes) {
            System.out.println("Estudiante: " + estudiante.nombre);
            if (estudiante.librosSolicitados.isEmpty()) {
                System.out.println("No solicitó libros.");
            } else {
                for (Libro libro : estudiante.librosSolicitados) {
                    System.out.println("- " + libro.titulo);
                }
            }
            System.out.println();
        }

        System.out.println("Copias restantes de cada libro:");
        for (Libro libro : libros) {
            System.out.println(libro.titulo + ": " + libro.copiasDisponibles + " copias");
        }
    }
}
