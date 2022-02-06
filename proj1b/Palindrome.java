public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> deque = wordToDeque(word);
        return word.equals(reversed(deque));
    }

    private String reversed(Deque d) {
        if (d.isEmpty()) {
            return "";
        }
        return d.removeLast() + reversed(d);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        for (int i = 0; i < word.length() / 2; i++) {
            if (!(cc.equalChars(word.charAt(i), word.charAt(word.length() - 1 - i)))) {
                return false;
            }
        }
        return true;
    }
}
