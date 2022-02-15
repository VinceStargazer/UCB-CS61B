import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>, Iterable<K> {
    private BSTMap<K, V> left, right, parent;
    private K key;
    private V value;

    public BSTMap() {
        clear();
    }

    @Override
    public void clear() {
        left = null;
        right = null;
        parent = null;
        key = null;
        value = null;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        if (isEmpty()) {
            return null;
        }
        int cmp = key.compareTo(this.key);
        if (cmp == 0) {
            return value;
        } else if (cmp < 0) {
            return left.get(key);
        } else {
            return right.get(key);
        }
    }

    @Override
    public int size() {
        if (isEmpty()) {
            return 0;
        }
        return 1 + left.size() + right.size();
    }

    @Override
    public void put(K key, V value) {
        if (isEmpty()) {
            this.key = key;
            this.value = value;
            left = new BSTMap<>();
            right = new BSTMap<>();
            left.parent = this;
            right.parent = this;
            return;
        }
        int cmp = key.compareTo(this.key);
        if (cmp == 0) {
            throw new IllegalArgumentException("Key already exists.");
        } else if (cmp < 0) {
            left.put(key, value);
        } else {
            right.put(key, value);
        }
    }

    @Override
    public Set<K> keySet() {
        return returnHelper(new HashSet<>());
    }

    private Set<K> returnHelper(Set<K> S) {
        if (!isEmpty()) {
            if (left.isEmpty() && right.isEmpty()) {
                S.add(key);
            } else {
                left.returnHelper(S);
                S.add(key);
                right.returnHelper(S);
            }
        }
        return S;
    }

    @Override
    public V remove(K key) {
        if (this.key == key) {
            return deleteKey();
        } else if (key.compareTo(this.key) > 0) {
            return right.remove(key);
        } else {
            return left.remove(key);
        }
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    private boolean isEmpty() {
        return key == null;
    }

    private void printInOrder() {
        Set<K> S = keySet();
        for (K i : S) {
            System.out.println(i + " | " + get(i));
        }
    }

    private K findSuccessor() {
        if (!isEmpty() && left.isEmpty()) {
            return key;
        }
        return left.findSuccessor();
    }

    private V deleteKey() {
        V returnVal = value;
        if (left.isEmpty() && right.isEmpty()) {
            BSTMap<K, V> node = parent;
            clear();
            parent = node;
        } else if (parent == null || !left.isEmpty() && !right.isEmpty()) {
            K successor;
            if (parent == null && right.isEmpty()) {
                successor = left.key;
            } else {
                successor = right.findSuccessor();
            }
            this.value = remove(successor);
            this.key = successor;
        } else if (left.isEmpty()) {
            if (right.key.compareTo(parent.key) > 0) {
                parent.right = right;
            } else {
                parent.left = right;
            }
        } else {
            if (left.key.compareTo(parent.key) > 0) {
                parent.right = left;
            } else {
                parent.left = left;
            }
        }
        return returnVal;
    }
}
