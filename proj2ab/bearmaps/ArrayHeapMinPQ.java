package bearmaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private final ArrayList<PriorityNode> items;
    private int size;
    private final HashMap<T, Integer> dict;

    private class PriorityNode implements Comparable<PriorityNode> {
        private final T item;
        private final double priority;

        PriorityNode() {
            this(null, Integer.MIN_VALUE);
        }

        PriorityNode(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        @Override
        public int compareTo(PriorityNode other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }
    }

    public ArrayHeapMinPQ() {
        this(1);
    }

    public ArrayHeapMinPQ(int initCapacity) {
        items = new ArrayList<>(initCapacity);
        dict = new HashMap<>();
        items.add(new PriorityNode());
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Item already exists");
        }
        items.add(new PriorityNode(item, priority));
        dict.put(item, ++size);
        swim(size);
    }

    @Override
    public boolean contains(T item) {
        return dict.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue underflow");
        }
        return items.get(1).getItem();
    }

    @Override
    public T removeSmallest() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue underflow");
        }
        T min = items.get(1).getItem();
        swap(1, size--);
        sink(1);
        items.set(size + 1, null);
        dict.remove(min);
        return min;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("No such item exists");
        }
        int k = dict.get(item);
        items.set(k, new PriorityNode(item, priority));
        swim(k);
        sink(k);
    }

    private boolean isEmpty() {
        return size == 0;
    }

    private int leftChild(int k) {
        return k * 2;
    }

    private int rightChild(int k) {
        return k * 2 + 1;
    }

    private int parent(int k) {
        return k / 2;
    }

    /** To compare between priorities of two items */
    private boolean greater(int i, int j) {
        return items.get(i).compareTo(items.get(j)) > 0;
    }

    /** To send eligible items upward */
    private void swim(int k) {
        if (parent(k) > 0 && greater(parent(k), k)) {
            swap(k, parent(k));
            swim(parent(k));
        }
    }

    /** To send eligible items downward (if there's a tie, send left-down) */
    private void sink(int k) {
        while (leftChild(k) <= size) {
            int smallerChild = leftChild(k);
            if (rightChild(k) <= size && greater(leftChild(k), rightChild(k))) {
                smallerChild = rightChild(k);
            }
            if (!greater(k, smallerChild)) {
                break;
            }
            swap(k, smallerChild);
            k = smallerChild;
        }
    }

    /** To swap two items */
    private void swap(int i, int j) {
        T item = items.get(i).getItem();
        T item2 = items.get(j).getItem();
        dict.replace(item, j);
        dict.replace(item2, i);
        PriorityNode swap = items.get(i);
        items.set(i, items.get(j));
        items.set(j, swap);
    }

    /** Return the array format of priority queue */
    private T[] toArray() {
        T[] array = (T[]) new Object[size + 1];
        array[0] = null;
        for (int i = 1; i <= size; i++) {
            array[i] = items.get(i).getItem();
        }
        return array;
    }

    /** Visualize some test output */
    public static void main(String[] args) {
        ArrayHeapMinPQ<Character> test = new ArrayHeapMinPQ<>();
        String input = "abcdefg";
        for (int i = 0; i < input.length(); i++) {
            test.add(input.charAt(i), input.length() - i);
        }
        PrintHeapDemo.printFancyHeapDrawing(test.toArray());
        test.removeSmallest();
        PrintHeapDemo.printFancyHeapDrawing(test.toArray());
    }
}
