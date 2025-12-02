public class Vertex<T> {
    private T value;

    public Vertex(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Vertex)) return false;
        Vertex<?> other = (Vertex<?>) obj;
        return this.value.equals(other.value);
    }
}
