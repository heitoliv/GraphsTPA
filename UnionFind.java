import java.util.HashMap;
import java.util.Map;

public class UnionFind<T> {
    private Map<T, T> parent = new HashMap<>();

    public void makeSet(T x) {
        parent.put(x, x);
    }

    public T find(T x) {
        if (parent.get(x).equals(x)) return x;
        T root = find(parent.get(x));
        parent.put(x, root);
        return root;
    }

    public void union(T a, T b) {
        T rootA = find(a);
        T rootB = find(b);
        parent.put(rootA, rootB);
    }
}
