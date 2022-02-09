import org.junit.Test;
import static org.junit.Assert.*;

public class TestUnionFind {
    @Test
    public void someTest() {
        UnionFind a = new UnionFind(10);
        a.union(0, 1);
        a.union(2, 3);
        a.union(4, 5);
        a.union(6, 7);
        a.union(8, 9);
        a.union(9, 2);
        a.union(4, 8);
        a.union(0, 6);
        assertTrue(a.connected(5, 8));
        assertFalse(a.connected(5, 6));
        assertEquals(6, a.sizeOf(5));
        a.union(7, 9);
        assertTrue(a.connected(5, 6));
        assertEquals(10, a.sizeOf(5));
        a.find(0);
        assertEquals(3, a.find(5));
    }
}
