import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private int size;
    private double loadFactor;
    private ArrayList<Entry<K, V>> buckets;

    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        private Entry(K k, V v, Entry<K, V> n) {
            key = k;
            value = v;
            next = n;
        }
    }

    public MyHashMap() {
        this(16, 0.75);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, 0.75);
    }

    public MyHashMap(int initialSize, double loadFactor) {
        if (initialSize < 1 || loadFactor <= 0.0) {
            throw new IllegalArgumentException();
        }
        size = 0;
        this.loadFactor = loadFactor;
        buckets = new ArrayList<>(initialSize);
        putNull(initialSize);
    }

    @Override
    public void clear() {
        size = 0;
        buckets = new ArrayList<>(16);
        putNull(16);
    }

    /** Add null to bucket list for num times */
    private void putNull(int num) {
        for (int i = 0; i < num; i++) {
            buckets.add(null);
        }
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        int index = Math.floorMod(key.hashCode(), buckets.size());
        return get(buckets.get(index), key);
    }

    /** Get the corresponding value to key from a certain bucket */
    private V get(Entry<K, V> entry, K key) {
        if (entry == null) {
            return null;
        } else if (entry.key.equals(key)) {
            return entry.value;
        }
        return get(entry.next, key);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        int index = Math.floorMod(key.hashCode(), buckets.size());
        if (!containsKey(key)) {
            buckets.set(index, new Entry<>(key, value, buckets.get(index)));
            size++;
            if (size > buckets.size() * loadFactor) {
                grow();
            }
        } else {
            resetValue(key, value, buckets.get(index));
        }
    }

    /** Reset the corresponding value to key to "value" */
    private void resetValue(K key, V value, Entry<K, V> entry) {
        if (entry.key.equals(key)) {
            entry.value = value;
            return;
        }
        resetValue(key, value, entry.next);
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> returnSet = new HashSet<>();
        for (Entry<K, V> e : buckets) {
            returnSet.addAll(keySet(new HashSet<>(), e));
        }
        return returnSet;
    }

    /** Return the HashSet for one entry bucket */
    private Set<K> keySet(HashSet<K> returnKeys, Entry<K, V> entry) {
        if (entry == null) {
            return returnKeys;
        }
        returnKeys.add(entry.key);
        return keySet(returnKeys, entry.next);
    }

    @Override
    public V remove(K key) {
        if (!containsKey(key)) {
            return null;
        }
        V returnVal = get(key);
        MyHashMap<K, V> newMap = new MyHashMap<>(buckets.size());
        for (K k : this) {
            if (!k.equals(key)) {
                newMap.put(k, get(k));
            }
        }
        copyMap(newMap);
        return returnVal;
    }

    @Override
    public V remove(K key, V value) {
        if (get(key).equals(value)) {
            return remove(key);
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    /** Double the size of buckets and put all items into new ones */
    private void grow() {
        MyHashMap<K, V> newMap = new MyHashMap<>(buckets.size() * 2);
        for (K key : this) {
            newMap.put(key, get(key));
        }
        copyMap(newMap);
    }

    /** Turn the current MyHashMap into a duplicate of "map" */
    private void copyMap(MyHashMap<K, V> map) {
        size = map.size;
        loadFactor = map.loadFactor;
        buckets = map.buckets;
    }
}
