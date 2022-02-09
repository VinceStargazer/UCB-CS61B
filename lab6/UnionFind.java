import java.util.ArrayList;
import java.util.List;

public class UnionFind {
    private final int[] parent;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = -1;
        }
    }

    /* Throws an exception if vertex is not a valid index. */
    private void validate(int vertex) {
        if (vertex >= parent.length) {
            throw new IndexOutOfBoundsException(vertex + " is not a valid index.");
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        validate(v1);
        return -(parent[find(v1)]);
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        validate(v1);
        return parent[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        validate(v1);
        validate(v2);
        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        validate(v1);
        validate(v2);
        if (v1 == v2) {
            return;
        }
        if (sizeOf(v1) > sizeOf(v2)) {
            int temp = parent[find(v2)];
            parent[find(v2)] = find(v1);
            parent[find(v1)] += temp;
        } else {
            int temp = parent[find(v1)];
            parent[find(v1)] = find(v2);
            parent[find(v2)] += temp;
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        validate(vertex);
        List<Integer> record = new ArrayList<>();
        while (parent[vertex] >= 0) {
            record.add(vertex);
            vertex = parent[vertex];
        }
        for (int i : record) {
            parent[i] = vertex;
        }
        return vertex;
    }
}
