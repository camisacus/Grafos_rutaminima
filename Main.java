import java.util.*;

class Nodo {
    String nombre;
    double valor = Double.MAX_VALUE;
    Nodo padre = null;
    boolean activo = false;
    List<Nodo> vecinos = new ArrayList<>();
    List<Double> pesos = new ArrayList<>();

    Nodo(String nombre) {
        this.nombre = nombre;
    }

    void agregarVecino(Nodo vecino, double peso) {
        vecinos.add(vecino);
        pesos.add(peso);
    }

    int grado() {
        return vecinos.size();
    }
}

class Grafo {
    List<Nodo> nodos = new ArrayList<>();

    Nodo agregarNodo(String nombre) {
        Nodo n = buscarNodo(nombre);
        if (n == null) {
            n = new Nodo(nombre);
            nodos.add(n);
        }
        return n;
    }

    Nodo buscarNodo(String nombre) {
        for (Nodo n : nodos) {
            if (n.nombre.equals(nombre)) return n;
        }
        return null;
    }

    void agregarArista(String nombre1, String nombre2, double peso) {
        Nodo n1 = agregarNodo(nombre1);
        Nodo n2 = agregarNodo(nombre2);
        n1.agregarVecino(n2, peso);
        n2.agregarVecino(n1, peso);
    }

    void mostrarGrados() {
        System.out.println("\nGrado de cada vértice:");
        for (Nodo n : nodos) {
            System.out.println(n.nombre + ": " + n.grado());
        }
    }

    boolean esConexo() {
        Set<Nodo> visitados = new HashSet<>();
        dfs(nodos.get(0), visitados);
        return visitados.size() == nodos.size();
    }

    void dfs(Nodo actual, Set<Nodo> visitados) {
        visitados.add(actual);
        for (Nodo vecino : actual.vecinos) {
            if (!visitados.contains(vecino)) {
                dfs(vecino, visitados);
            }
        }
    }
}

class ColaPrioridad {
    PriorityQueue<Nodo> cola = new PriorityQueue<>(Comparator.comparingDouble(n -> n.valor));

    void encolar(Nodo n) {
        cola.add(n);
    }

    Nodo desencolar() {
        return cola.poll();
    }

    boolean vacia() {
        return cola.isEmpty();
    }

    void actualizar(Nodo n) {
        cola.remove(n);
        cola.add(n);
    }

    boolean contiene(Nodo n) {
        return cola.contains(n);
    }
}

class AlgoritmosGrafos {
    static List<Nodo> dijkstra(Grafo g, String origen, String destino) {
        for (Nodo n : g.nodos) {
            n.valor = Double.MAX_VALUE;
            n.padre = null;
        }

        Nodo inicio = g.buscarNodo(origen);
        Nodo fin = g.buscarNodo(destino);
        inicio.valor = 0;

        ColaPrioridad cola = new ColaPrioridad();
        cola.encolar(inicio);

        while (!cola.vacia()) {
            Nodo actual = cola.desencolar();
            for (int i = 0; i < actual.vecinos.size(); i++) {
                Nodo vecino = actual.vecinos.get(i);
                double peso = actual.pesos.get(i);
                double nuevoValor = actual.valor + peso;
                if (nuevoValor < vecino.valor) {
                    vecino.valor = nuevoValor;
                    vecino.padre = actual;
                    if (cola.contiene(vecino)) {
                        cola.actualizar(vecino);
                    } else {
                        cola.encolar(vecino);
                    }
                }
            }
        }

        List<Nodo> ruta = new ArrayList<>();
        for (Nodo n = fin; n != null; n = n.padre) {
            ruta.add(0, n);
        }
        return ruta;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Grafo g = new Grafo();

        // Cargar datos del grafo
        g.agregarArista("Medellín", "Puerto Berrio", 186);
        g.agregarArista("Medellín", "Puerto Triunfo", 190);
        g.agregarArista("Medellín", "Manizales", 200);
        g.agregarArista("Puerto Berrio", "Puerto Triunfo", 129);
        g.agregarArista("Puerto Triunfo", "Honda", 102);
        g.agregarArista("Manizales", "Honda", 141);
        g.agregarArista("Manizales", "Ibagué", 174);
        g.agregarArista("Honda", "Bogotá", 169);
        g.agregarArista("Honda", "Girardot", 138);
        g.agregarArista("Honda", "Ibagué", 141);
        g.agregarArista("Girardot", "Bogotá", 133);
        g.agregarArista("Girardot", "Ibagué", 66);

        while (true) {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Ver ciudades");
            System.out.println("2. Calcular ruta mínima");
            System.out.println("3. Ver grados de los vértices");
            System.out.println("4. Verificar si el grafo es conexo");
            System.out.println("5. Salir");
            System.out.print("Opción: ");
            int op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1:
                    System.out.println("Ciudades:");
                    for (Nodo n : g.nodos) System.out.println("- " + n.nombre);
                    break;
                case 2:
                    System.out.print("Ciudad origen: ");
                    String origen = sc.nextLine();
                    System.out.print("Ciudad destino: ");
                    String destino = sc.nextLine();
                    List<Nodo> ruta = AlgoritmosGrafos.dijkstra(g, origen, destino);
                    if (ruta.size() == 1 && !ruta.get(0).nombre.equals(destino)) {
                        System.out.println("No hay ruta.");
                    } else {
                        System.out.println("Ruta mínima:");
                        double total = 0;
                        for (Nodo n : ruta) {
                            System.out.print(n.nombre + " -> ");
                        }
                        System.out.println("FIN");
                        System.out.println("Distancia total: " + ruta.get(ruta.size() - 1).valor + " km");
                    }
                    break;
                case 3:
                    g.mostrarGrados();
                    break;
                case 4:
                    System.out.println("¿Es conexo? " + (g.esConexo() ? "Sí" : "No"));
                    break;
                case 5:
                    System.out.println("¡Hasta luego!");
                    return;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
}
