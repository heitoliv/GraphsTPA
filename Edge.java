public class Edge<T> {
    Vertex<T> destino;
    float peso;

    public Edge(Vertex<T> destino, float peso) {
        this.destino = destino;
        this.peso = peso;
    }

    @Override
    public String toString() {
        return "-> " + destino + " (" + peso + ")";
    }
}
