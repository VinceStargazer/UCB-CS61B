package es.datastructur.synthesizer;
import org.junit.Test;

import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        for (int i = 0; i < 10; i++) {
            arb.enqueue(i);
        }
        int first = arb.peek();
        assertEquals(first, 0);
        int removed = arb.dequeue();
        assertEquals(removed, 0);
        removed = arb.dequeue();
        assertEquals(removed, 1);
        arb.enqueue(10);
        arb.enqueue(11);
    }

    @Test
    public void testLoop() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        for (int i = 0; i < 10; i++) {
            arb.enqueue(i);
        }
        arb.dequeue();
        arb.dequeue();
        for (int i : arb) {
            System.out.print(i + " ");
        }
    }

    @Test
    public void testEquals() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        for (int i = 0; i < 10; i++) {
            arb.enqueue(i);
        }
        ArrayRingBuffer<Integer> ant = new ArrayRingBuffer<>(10);
        for (int i = 0; i < 10; i++) {
            ant.enqueue(i);
        }
        assertTrue(arb.equals(ant));
        ant.dequeue();
        assertFalse(arb.equals(ant));
    }
}
