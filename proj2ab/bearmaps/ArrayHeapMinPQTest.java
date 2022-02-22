package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArrayHeapMinPQTest {

    @Test
    public void sanityTest() {
        ArrayHeapMinPQ<Character> test = new ArrayHeapMinPQ<>();
        String input = "abcdefg";
        for (int i = 0; i < input.length(); i++) {
            test.add(input.charAt(i), input.length() - i);
        }
        assertEquals('g', (char) test.removeSmallest());
        assertEquals('f', (char) test.getSmallest());
        test.changePriority('a', 0);
        assertEquals('a', (char) test.getSmallest());
        assertTrue(test.contains('f'));
        assertEquals(6, test.size());
    }

    @Test
    public void edgeTest() {
        ArrayHeapMinPQ<Character> test = new ArrayHeapMinPQ<>();
        boolean[] thrown = new boolean[]{false, false, false, false};

        try {
            test.getSmallest();
        } catch (NoSuchElementException e) {
            thrown[0] = true;
        }

        try {
            test.removeSmallest();
        } catch (NoSuchElementException e) {
            thrown[1] = true;
        }

        test.add('h', 12);
        try {
            test.add('h', 13);
        } catch (IllegalArgumentException e) {
            thrown[2] = true;
        }

        try {
            test.changePriority('g', 12);
        } catch (NoSuchElementException e) {
            thrown[3] = true;
        }

        for (boolean b : thrown) {
            assertTrue(b);
        }
    }

    /** Test running time */
    public static void main(String[] args) {
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> test2 = new NaiveMinPQ<>();

        // add speed test
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < 1_000_000; i++) {
            test.add(i, 1_000_000 - i);
        }
        timerPrint("ArrayHeapMinPQ adds 1 mil items: ", sw, false);

        sw = new Stopwatch();
        for (int i = 0; i < 1_000_000; i++) {
            test2.add(i, 1_000_000 - i);
        }
        timerPrint("NaiveMinPQ adds 1 mil items:  ", sw, true);

        // getSmallest speed test
        sw = new Stopwatch();
        for (int i = 0; i < 1000; i++) {
            test.getSmallest();
        }
        timerPrint("ArrayHeapMinPQ runs getSmallest 1k times: ", sw, false);

        sw = new Stopwatch();
        for (int i = 0; i < 1000; i++) {
            test2.getSmallest();
        }
        timerPrint("NaiveMinPQ runs getSmallest 1k times: ", sw, true);

        // removeSmallest speed test
        sw = new Stopwatch();
        for (int i = 0; i < 1000; i++) {
            test.removeSmallest();
        }
        timerPrint("ArrayHeapMinPQ runs removeSmallest 1k times: ", sw, false);

        sw = new Stopwatch();
        for (int i = 0; i < 1000; i++) {
            test2.removeSmallest();
        }
        timerPrint("NaiveMinPQ runs removeSmallest 1k times: ", sw, true);

        // contains speed test
        sw = new Stopwatch();
        for (int i = 0; i < 1000; i++) {
            test.contains(i);
        }
        timerPrint("ArrayHeapMinPQ runs contains 1k times: ", sw, false);

        sw = new Stopwatch();
        for (int i = 0; i < 1000; i++) {
            test2.contains(i);
        }
        timerPrint("NaiveMinPQ runs contains 1k times: ", sw, true);

        // changePriority speed test
        sw = new Stopwatch();
        for (int i = 0; i < 1000; i++) {
            test.changePriority(i, i);
        }
        timerPrint("ArrayHeapMinPQ runs changePriority 1k times: ", sw, false);

        sw = new Stopwatch();
        for (int i = 0; i < 1000; i++) {
            test2.changePriority(i, i);
        }
        timerPrint("NaiveMinPQ runs changePriority 1k times: ", sw, true);
    }

    private static void timerPrint(String s, Stopwatch sw, boolean newLine) {
        System.out.println(s + sw.elapsedTime() +  " seconds.");
        if (newLine) {
            System.out.println();
        }
    }
}
