public class ArrayDeque<T> {
    private T[] items;
    private int capacity = 8;
    private int nextFirst = 7;
    private int nextLast = 0;
    private int size = 0;
    private int REFACTOR_SIZE = 2;
    private int CAPACITY_BASE = 16;
    private double USAGE_RATIO = 0.25;

    public ArrayDeque() {
        items = (T[]) new Object[capacity];
    }

    public ArrayDeque(ArrayDeque other) {
        this();
        for (int i = 0; i < other.size(); i++) {
            addLast((T) other.get(i));
        }
    }

    public void reshape(int index, int capacity) {
        T[] a = (T[]) new Object[capacity];
        System.arraycopy(items, index + 1, a, 0, size - index);
        System.arraycopy(items, 0, a, size - index, index);
        items = a;
        nextFirst = capacity - 1;
        nextLast = size;
    }

    public void expandArray(int index) {
        T[] a = (T[]) new Object[capacity * REFACTOR_SIZE];
        System.arraycopy(items, 0, a, 0, index);
        System.arraycopy(items, index + 1, a,
                size + index + 2, size - index);
        items = a;
        nextFirst = size + index + 1;
    }

    public void shrinkArray(int index) {
        T[] a = (T[]) new Object[capacity / REFACTOR_SIZE];
        System.arraycopy(items, 0, a, 0, index);
        System.arraycopy(items, index + 1, a,
                size + index + 2, size - index);
        items = a;
        nextFirst = size + index + 1;
    }

    public void addFirst(T x) {
        items[nextFirst] = x;
        nextFirst = decrement(nextFirst);
        size++;
        if (almostFull()) {
            expandArray(nextFirst);
        }
    }

    public void addLast(T x) {
        items[nextLast] = x;
        nextLast = increment(nextLast);
        size++;
        if (almostFull()) {
            expandArray(nextLast);
        }
    }

    public void removeFirst() {
        if (isEmpty()) {
            return;
        }
        nextFirst = increment(nextFirst);
        items[nextFirst] = null;
        size--;
        if (isSparse()) {
            shrinkArray(nextFirst);
        }
    }

    public void removeLast() {
        if (isEmpty()) {
            return;
        }
        nextLast = decrement(nextLast);
        items[nextLast] = null;
        size--;
        if (isSparse()) {
            shrinkArray(nextLast);
        }
    }

    public T get(int index) {
        if (size > index + nextLast) {
            return items[index + nextFirst + 1];
        }
        else {
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

    public boolean almostFull() {
        return size == capacity - 1;
    }

    public boolean isSparse() {
        return size > CAPACITY_BASE && size < (capacity * USAGE_RATIO);
    }

    public int decrement(int x) {
        if (x == 0) {
            return capacity - 1;
        } else {
            return x - 1;
        }
    }

    public int increment(int x) {
        if (x == capacity - 1) {
            return 0;
        } else {
            return x + 1;
        }
    }

    public static void main(String[] args) {
        ArrayDeque test = new ArrayDeque();
        String[] input = new String[]{"a", "b", "c", "d", "e", "f",
                "g", "h", "i", "j", "k", "l", "m", "n"};
        for (int i = 0; i < input.length; i++) {
            if (i % 2 == 0) {
                test.addLast(input[i]);
            } else {
                test.addFirst(input[i]);
            }
        }
        test.removeFirst();
        test.removeLast();
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

        System.out.println("done");
    }
}
