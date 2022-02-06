public class ArrayDeque<T> {
    private T[] items;
    private int capacity;
    private int nextFirst = 7;
    private int nextLast = 0;
    private int size = 0;
    private int REFACTOR_SIZE = 2;
    private int CAPACITY_BASE = 8;
    private double USAGE_RATIO = 0.25;

    public ArrayDeque() {
        this(8);
    }

    ArrayDeque(int capacity) {
        items = (T[]) new Object[capacity];
        this.capacity = capacity;
    }

    ArrayDeque(ArrayDeque other) {
        this(other.capacity);
        for (int i = 0; i < other.size(); i++) {
            addLast((T) other.get(i));
        }
        nextFirst = capacity - 1;
        nextLast = size;
    }

    public void addFirst(T x) {
        items[nextFirst] = x;
        nextFirst = decrement(nextFirst);
        size++;
        if (isFull()) {
            expandArray();
        }
    }

    public void addLast(T x) {
        items[nextLast] = x;
        nextLast = increment(nextLast);
        size++;
        if (isFull()) {
            expandArray();
        }
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        nextFirst = increment(nextFirst);
        T first = items[nextFirst];
        items[nextFirst] = null;
        size--;
        if (isSparse()) {
            shrinkArray();
        }
        return first;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        nextLast = decrement(nextLast);
        T last = items[nextLast];
        items[nextLast] = null;
        size--;
        if (isSparse()) {
            shrinkArray();
        }
        return last;
    }

    public T get(int index) {
        if (size > index + nextLast) {
            return items[index + nextFirst + 1];
        } else {
            return items[index + nextLast - size];
        }
    }

    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private boolean isFull() {
        return size == capacity;
    }

    private boolean isSparse() {
        return capacity > CAPACITY_BASE && size < (capacity * USAGE_RATIO);
    }

    private void reshape(int newCapacity) {
        T[] a = (T[]) new Object[newCapacity];
        if (nextFirst < nextLast && !isFull()) {
            System.arraycopy(items, nextFirst + 1, a, 0, size);
        } else {
            System.arraycopy(items, nextFirst + 1, a, 0, capacity - nextFirst - 1);
            System.arraycopy(items, 0, a, capacity - nextFirst - 1,
                    size + nextFirst + 1 - capacity);
        }
        items = a;
        capacity = newCapacity;
        nextFirst = capacity - 1;
        nextLast = size;
    }

    private void expandArray() {
        reshape(capacity * REFACTOR_SIZE);
    }

    private void shrinkArray() {
        reshape(capacity / REFACTOR_SIZE);
    }

    private int decrement(int x) {
        if (x == 0) {
            return capacity - 1;
        } else {
            return x - 1;
        }
    }

    private int increment(int x) {
        if (x == capacity - 1) {
            return 0;
        } else {
            return x + 1;
        }
    }

    /** Test if methods work. Change "private" to "public" to work. */
    private static void main(String[] args) {
        ArrayDeque test = new ArrayDeque();
        String[] input = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l"};
        for (int i = 0; i < input.length; i++) {
            if (i % 2 == 0) {
                test.addLast(input[i]);
            } else {
                test.addFirst(input[i]);
            }
        }
        test.printDeque();

        ArrayDeque leftStack = new ArrayDeque(test);
        leftStack.removeFirst();
        leftStack.removeLast();
        leftStack.printDeque();

        ArrayDeque rightStack = new ArrayDeque();
        for (int i = 0; i < test.size; i++) {
            rightStack.addFirst(test.get(i));
        }
        rightStack.removeFirst();
        rightStack.removeLast();
        rightStack.printDeque();

        for (int i = 0; i < leftStack.size; i++) {
            rightStack.addLast(leftStack.get(i));
        }
        rightStack.printDeque();

        for (int i = 0; i < 16; i++) {
            rightStack.removeLast();
        }
        rightStack.printDeque();

        System.out.println("done");
    }
}
