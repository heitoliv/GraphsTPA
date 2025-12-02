import java.util.*;

public class Graph<T> {

    private Map<Vertex<T>, List<Edge<T>>> adj;

    public Graph() {
        adj = new HashMap<>();
    }

    // (i) Adiciona um vértice ao grafo
    public void addVertex(T value) {
        Vertex<T> v = new Vertex<>(value);
        if (!adj.containsKey(v)) {
            adj.put(v, new ArrayList<>());
        }
    }

    // Obtém o vértice correspondente ao valor
    private Vertex<T> getVertex(T value) {
        for (Vertex<T> v : adj.keySet()) {
            if (v.getValue().equals(value)) return v;
        }
        return null;
    }

    // (ii) Adicionar aresta com peso
    public void addEdge(T origem, T destino, float peso) {
        Vertex<T> vOrigem = getVertex(origem);
        Vertex<T> vDestino = getVertex(destino);

        if (vOrigem == null || vDestino == null) {
            System.out.println("Erro: vértice inexistente.");
            return;
        }

        adj.get(vOrigem).add(new Edge<>(vDestino, peso));
    }

    // (iii) BFS - caminhamento em largura
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

    // -----------------------------
    // ALGORITMO 1: DIJKSTRA
    // -----------------------------
    public Map<Vertex<T>, Float> dijkstra(T inicio) {
        Vertex<T> start = getVertex(inicio);

        Map<Vertex<T>, Float> dist = new HashMap<>();
        for (Vertex<T> v : adj.keySet()) dist.put(v, Float.MAX_VALUE);
        dist.put(start, 0f);

        PriorityQueue<Vertex<T>> pq =
                new PriorityQueue<>(Comparator.comparing(dist::get));

        pq.add(start);

        while (!pq.isEmpty()) {
            Vertex<T> u = pq.poll();

            for (Edge<T> e : adj.get(u)) {
                Vertex<T> v = e.destino;
                float peso = e.peso;

                if (dist.get(u) + peso < dist.get(v)) {
                    dist.put(v, dist.get(u) + peso);
                    pq.add(v);
                }
            }
        }

        return dist;
    }

    // --------------------------------
    // ALGORITMO 2: KRUSKAL (MST)
    // --------------------------------
    public List<String> kruskal() {

    // Lista de arestas contendo (origem, destino, peso)
    List<Edge<T>> edges = new ArrayList<>();
    for (Vertex<T> u : adj.keySet()) {
        for (Edge<T> e : adj.get(u)) {
            edges.add(new Edge<>(u, e.destino, e.peso)); // garante origem
        }
    }

    // Ordena por peso
    edges.sort(Comparator.comparing(e -> e.peso));

    // Cria estrutura de conjuntos disjuntos
    UnionFind<Vertex<T>> uf = new UnionFind<>();

    // IMPORTANTÍSSIMO: inicializar todos os vértices no UnionFind
    for (Vertex<T> v : adj.keySet()) {
        uf.makeSet(v);
    }

    List<String> mst = new ArrayList<>();

    for (Edge<T> e : edges) {

        Vertex<T> origem = e.origem;
        Vertex<T> destino = e.destino;

        // Encontrar os conjuntos
        Vertex<T> raizOrigem = uf.find(origem);
        Vertex<T> raizDestino = uf.find(destino);

        // Se pertencem a conjuntos diferentes, une
        if (!raizOrigem.equals(raizDestino)) {
            uf.union(raizOrigem, raizDestino);

            mst.add(origem + " -- " + destino + " (" + e.peso + ")");
        }
    }

    return mst;
}


    // Método auxiliar para visualizar o grafo
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
