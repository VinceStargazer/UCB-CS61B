import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertFalse(palindrome.isPalindrome("horse"));
        assertFalse(palindrome.isPalindrome("Hannah"));
    }

    @Test
    public void testIsPalindromeOffByOne() {
        CharacterComparator cc = new OffByOne();
        assertTrue(palindrome.isPalindrome("man", cc));
        assertTrue(palindrome.isPalindrome("flake", cc));
        assertFalse(palindrome.isPalindrome("horse", cc));
        assertFalse(palindrome.isPalindrome("www", cc));
    }

    @Test
    public void testIsPalindromeOffByN() {
        CharacterComparator cc = new OffByN(5);
        assertTrue(palindrome.isPalindrome("bag", cc));
        assertFalse(palindrome.isPalindrome("flake", cc));
        assertFalse(palindrome.isPalindrome("www", cc));
    }
}
