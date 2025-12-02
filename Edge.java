public class Edge<T> {
    // Adicionado o campo 'origem' necessário para o Kruskal (e.origem)
    Vertex<T> origem; 
    Vertex<T> destino;
    float peso;

    // Construtor 1: Mantido para compatibilidade (usado na lista de adjacência)
    public Edge(Vertex<T> destino, float peso) {
        this.destino = destino;
        this.peso = peso;
    }

    // Construtor 2: NOVO (usado na linha 105 do seu Graph.java para o Kruskal)
    public Edge(Vertex<T> origem, Vertex<T> destino, float peso) {
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
    }

    @Override
    public String toString() {
        if (origem != null) {
            return origem + " -> " + destino + " (" + peso + ")";
        }
        return "-> " + destino + " (" + peso + ")";
    }
}