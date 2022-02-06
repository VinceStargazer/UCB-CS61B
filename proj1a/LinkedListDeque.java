public class LinkedListDeque<T> {
    private class DequeNode {
        private T item;
        private DequeNode prev;
        private DequeNode next;

        public DequeNode() {
            this(null, null, null);
        }

        DequeNode(DequeNode p, T i, DequeNode n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    private DequeNode sentinel;
    private int size;

    LinkedListDeque() {
        sentinel = new DequeNode();
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    LinkedListDeque(T x) {
        sentinel = new DequeNode();
        sentinel.next = new DequeNode(sentinel, x, sentinel);
        sentinel.prev = sentinel.next;
        size = 1;
    }

    LinkedListDeque(LinkedListDeque other) {
        this();
        for (int i = 0; i < other.size(); i++) {
            addLast((T) other.get(i));
        }
    }

    public void addFirst(T x) {
        sentinel.next = new DequeNode(sentinel, x, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    public void addLast(T x) {
        sentinel.prev = new DequeNode(sentinel.prev, x, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T first = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return first;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T last = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return last;
    }

    public T get(int x) {
        DequeNode node = sentinel.next;
        while (node != sentinel) {
            if (x == 0) {
                return node.item;
            }
            node = node.next;
            x -= 1;
        }
        return null;
    }

    public T getRecursive(int index) {
        if (index == 0) {
            return sentinel.next.item;
        } else if (isEmpty()) {
            return null;
        }
        LinkedListDeque other = new LinkedListDeque(this);
        other.removeFirst();
        return (T) other.getRecursive(index - 1);
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

    /** Test if methods work. Change "private" to "public" to work. */
    private static void main(String[] args) {
        LinkedListDeque<Integer> deque = new LinkedListDeque<>(6);
        deque.addFirst(3);
        deque.addLast(9);
        System.out.println(deque.getRecursive(2));
        deque.printDeque();
    }
}
