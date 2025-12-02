public class Main {
    public static void main(String[] args) {
        Graph<String> g = new Graph<>();

        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addVertex("D");

        g.addEdge("A", "B", 1.2f);
        g.addEdge("A", "C", 2.5f);
        g.addEdge("B", "D", 1.0f);
        g.addEdge("C", "D", 0.7f);

        System.out.println("\n--- REPRESENTAÇÃO DO GRAFO ---");
        g.imprimir();

        System.out.println("\n--- BFS A PARTIR DE A ---");
        g.bfs("A");
    }
}
