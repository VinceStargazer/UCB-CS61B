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
        return get(buckets.get(findIndex(key)), key);
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
        int index = findIndex(key);
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
        V returnVal = get(key);
        int index = findIndex(key);
        Entry<K, V> entry = buckets.get(index);
        if (entry != null) {
            remove(entry, key, index);
        }
        return returnVal;
    }

    /** Remove the mapping of key in the corresponding entry bucket */
    private void remove(Entry<K, V> entry, K key, int index) {
        if (entry.key.equals(key) && entry.next == null) {
            buckets.set(index, null);
        } else if (entry.key.equals(key)) {
            copyEntry(entry, entry.next);
        } else {
            Entry<K, V> newEntry = new Entry<>(entry.key, entry.value, null);
            while (entry.next != null) {
                if (!entry.next.key.equals(key)) {
                    newEntry.next = entry.next;
                }
                entry = entry.next;
            }
            buckets.set(index, newEntry);
        }
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

    /** Find the entry bucket that includes the key */
    private int findIndex(K key) {
        return Math.floorMod(key.hashCode(), buckets.size());
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

    /** Turn entry1 into a duplicate of entry2 */
    private void copyEntry(Entry<K, V> entry1, Entry<K, V> entry2) {
        entry1.key = entry2.key;
        entry1.value = entry2.value;
        entry1.next = entry2.next;
    }
}
