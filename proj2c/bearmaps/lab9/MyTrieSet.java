package bearmaps.lab9;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MyTrieSet implements TrieSet61B {
    private Node root = new Node();
    private int size;

    public static class Node {
        private boolean isKey;
        private final Hashtable<Character, Node> map;

        Node() {
            this.isKey = false;
            map = new Hashtable<>();
        }
    }

    @Override
    public void clear() {
        root = new Node();
        size = 0;
    }

    @Override
    public boolean contains(String key) {
        Node end = findNode(key);
        if (end == null) {
            return false;
        }
        return end.isKey;
    }

    @Override
    public void add(String key) {
        validate(key);
        Node curr = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node());
            }
            curr = curr.map.get(c);
        }
        curr.isKey = true;
        size++;
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        Node start = findNode(prefix);
        if (start == null) {
            return new ArrayList<>();
        }
        return colHelp(prefix, new ArrayList<>(), start);
    }

    @Override
    public String longestPrefixOf(String key) {
        validate(key);
        Node curr = root;
        StringBuilder prefix = new StringBuilder();
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c) && curr.isKey) {
                return prefix.toString();
            } else if (!curr.map.containsKey(c)) {
                break;
            }
            prefix.append(c);
            curr = curr.map.get(c);
        }
        return null;
    }

    /** Collect all existing strings from trie set */
    public List<String> collect() {
        return colHelp("", new ArrayList<>(), root);
    }

    /** Validate the string input is legal */
    private void validate(String key) {
        if (key == null || key.length() < 1) {
            throw new IllegalArgumentException();
        }
    }

    /** Help collect the string set where certain rule applies */
    private List<String> colHelp(String s, ArrayList<String> x, Node curr) {
        if (curr.isKey) {
            x.add(s);
        }
        for (char c : curr.map.keySet()) {
            colHelp(s + c, x, curr.map.get(c));
        }
        return x;
    }

    /** Find the node where prefix ends */
    private Node findNode(String prefix) {
        validate(prefix);
        Node curr = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (!curr.map.containsKey(c)) {
                return null;
            }
            curr = curr.map.get(c);
        }
        return curr;
    }

    /** Return the size of trie set */
    private int size() {
        return size;
    }
}
