import static org.junit.Assert.*;
import org.junit.Test;
import java.util.List;

/**
 * Created by Jenny Huang on 3/12/19.
 */
public class TestMyTrieSet {

    // assumes add/contains work
    @Test
    public void sanityClearTest() {
        MyTrieSet t = new MyTrieSet();
        for (int i = 0; i < 455; i++) {
            t.add("hi" + i);
            //make sure put is working via contains
            assertTrue(t.contains("hi" + i));
        }
        t.clear();
        for (int i = 0; i < 455; i++) {
            assertFalse(t.contains("hi" + i));
        }
    }

    // assumes add works
    @Test
    public void sanityContainsTest() {
        MyTrieSet t = new MyTrieSet();
        assertFalse(t.contains("waterYouDoingHere"));
        t.add("waterYouDoingHere");
        assertTrue(t.contains("waterYouDoingHere"));
    }

    // assumes add works
    @Test
    public void sanityPrefixTest() {
        String[] saStrings = new String[]{"same", "sam", "sad", "sap"};
        String[] otherStrings = new String[]{"a", "awls", "hello"};

        MyTrieSet t = new MyTrieSet();
        for (String s: saStrings) {
            t.add(s);
        }
        for (String s: otherStrings) {
            t.add(s);
        }

        List<String> keys = t.keysWithPrefix("sa");
        for (String s: saStrings) {
            assertTrue(keys.contains(s));
        }
        for (String s: otherStrings) {
            assertFalse(keys.contains(s));
        }

        String prefix = t.longestPrefixOf("sample");
        String prefix2 = t.longestPrefixOf("girl");
        assertEquals("sam", prefix);
        assertNull(prefix2);
    }

    // assumes collect and keysWithPrefix works
    @Test
    public void sanityCollectTest() {
        MyTrieSet t = new MyTrieSet();
        String[] inputs = new String[]{"hello", "hi", "help", "zebra"};
        for (String input : inputs) {
            t.add(input);
        }

        List<String> collection = t.collect();
        List<String> prefixCollection = t.keysWithPrefix("he");

        for (String input : inputs) {
            assertTrue(collection.contains(input));
        }

        assertTrue(prefixCollection.contains("help"));
        assertFalse(prefixCollection.contains("zebra"));
    }

}
