import java.util.*;

public class Graph<T> {

    private Map<Vertex<T>, List<Edge<T>>> adj;

    public Graph() {
        adj = new HashMap<>();
    }

    // (i) Adiciona um vértice
    public void addVertex(T value) {
        Vertex<T> v = new Vertex<>(value);
        if (!adj.containsKey(v)) {
            adj.put(v, new ArrayList<>());
        }
    }

    // Obtém o objeto vértice correspondente ao valor
    private Vertex<T> getVertex(T value) {
        for (Vertex<T> v : adj.keySet()) {
            if (v.getValue().equals(value)) return v;
        }
        return null;
    }

    // (ii) Adiciona aresta com peso
    public void addEdge(T origem, T destino, float peso) {
        Vertex<T> vOrigem = getVertex(origem);
        Vertex<T> vDestino = getVertex(destino);

        if (vOrigem == null || vDestino == null) {
            System.out.println("Erro: vértice inexistente.");
            return;
        }

        adj.get(vOrigem).add(new Edge<>(vDestino, peso));
    }

    // (iii) Caminhamento em largura (BFS)
    public void bfs(T inicio) {
        Vertex<T> start = getVertex(inicio);
        if (start == null) return;

        Set<Vertex<T>> visitado = new HashSet<>();
        Queue<Vertex<T>> fila = new LinkedList<>();

        fila.add(start);
        visitado.add(start);

        while (!fila.isEmpty()) {
            Vertex<T> atual = fila.poll();
            System.out.println(atual);

            for (Edge<T> e : adj.get(atual)) {
                if (!visitado.contains(e.destino)) {
                    visitado.add(e.destino);
                    fila.add(e.destino);
                }
            }
        }
    }

    // Apenas para visualizar o grafo
    public void imprimir() {
        for (Vertex<T> v : adj.keySet()) {
            System.out.print(v + ": ");
            for (Edge<T> e : adj.get(v)) {
                System.out.print(e + "  ");
            }
            System.out.println();
        }
    }
}
