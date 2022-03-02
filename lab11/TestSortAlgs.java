import edu.princeton.cs.algs4.Queue;

import org.junit.Test;
import java.util.Random;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class TestSortAlgs {

    private Queue<String> tas() {
        Queue<String> tas = new Queue<>();
        tas.enqueue("New");
        tas.enqueue("Order");
        tas.enqueue("Bizarre");
        tas.enqueue("Love");
        tas.enqueue("Triangle");
        return tas;
    }

    private Queue<Integer> reversed() {
        int size = 10000;
        Queue<Integer> reversed = new Queue<>();
        for (int i = 0; i < size; i++) {
            reversed.enqueue(size - i);
        }
        return reversed;
    }

    private Queue<Integer> random() {
        int size = 10000;
        Queue<Integer> rnd = new Queue<>();
        Random r = new Random();
        for (int i = 0; i < size; i++) {
            rnd.enqueue(r.nextInt(1000));
        }
        return rnd;
    }

    @Test
    public void testMergeSort() {
        assertFalse(isSorted(tas()));
        Queue<String> sat = MergeSort.mergeSort(tas());
        assertTrue(isSorted(sat));

        assertFalse(isSorted(reversed()));
        Queue<Integer> sorted = MergeSort.mergeSort(reversed());
        assertTrue(isSorted(sorted));

        Queue<Integer> rnd = MergeSort.mergeSort(random());
        assertTrue(isSorted(rnd));
    }

    @Test
    public void testQuickSort() {
        Queue<String> sat = QuickSort.quickSort(tas());
        assertTrue(isSorted(sat));

        Queue<Integer> sorted = QuickSort.quickSort(reversed());
        assertTrue(isSorted(sorted));

        Queue<Integer> rnd = QuickSort.quickSort(random());
        assertTrue(isSorted(rnd));
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
