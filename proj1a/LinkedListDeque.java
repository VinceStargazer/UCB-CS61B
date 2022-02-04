public class LinkedListDeque<T> {
    private class DequeNode {
        public T item;
        public DequeNode prev;
        public DequeNode next;

        public DequeNode() {
            this(null, null, null);
        }

        public DequeNode(DequeNode p, T i, DequeNode n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    public DequeNode sentinel;
    public int size;

    public LinkedListDeque() {
        sentinel = new DequeNode();
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    public LinkedListDeque(T x) {
        sentinel = new DequeNode();
        sentinel.next = new DequeNode(sentinel, x, sentinel);
        sentinel.prev = sentinel.next;
        size = 1;
    }

    public LinkedListDeque(LinkedListDeque other) {
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

    public void removeFirst() {
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
    }

    public void removeLast() {
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
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
        System.out.print("\n");
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public static void main(String[] args) {
        LinkedListDeque<Integer> deque = new LinkedListDeque<>(6);
        deque.addFirst(3);
        deque.addLast(9);
        System.out.println(deque.getRecursive(2));
        deque.printDeque();
    }

}
