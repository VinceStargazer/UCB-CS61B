import java.security.PublicKey;
import java.util.Arrays;

public class SLList {
    private static class IntNode {
        public int item;
        public IntNode next;
        public IntNode(int item, IntNode next) {
            this.item = item;
            this.next = next;
        }
    }

    private IntNode first;

    public SLList(int x) {
        first = new IntNode(x, null);
    }

    public void addFirst(int x) {
        first = new IntNode(x, first);
    }

    public int getFirst() {
        return first.item;
    }

    public void printList() {
        int i = 0;
        IntNode p = first;
        while (p != null) {
            System.out.print("(" + p.item);
            p = p.next;
            i++;
        }
        while (i != 0) {
            System.out.print(")");
            i--;
        }
        System.out.print("\n");
    }

    public void insert(int item, int position) {
        IntNode p = first;
        while (p.next != null) {
            if (position == 0) {
                IntNode q = new IntNode(p.item, p.next);
                p.item = item;
                p.next = q;
                return;
            }
            p = p.next;
            position--;
        }
        p.next = new IntNode(item, null);
    }

    public void reverse() {
        if (first == null || first.next == null) {
            return;
        }
        IntNode p = first.next;
        first.next = null;
        while (p != null) {
            IntNode temp = p.next;
            p.next = first;
            first = p;
            p = temp;
        }
    }

    public static int[] insert2(int[] arr, int item, int position) {
        int n = 0;
        int[] lst = new int[arr.length + 1];
        for (int i = 0; i < lst.length; i++) {
            if (position == i) {
                lst[i] = item;
            }
            else {
                lst[i] = arr[n];
                n++;
            }
        }
        return lst;
    }

    public static void reverse(int[] arr) {
        int i = 0;
        int j = arr.length - 1;
        while (i < j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            i++;
            j--;
        }
    }

    public static int[] replicate(int[] arr) {
        int n = 0;
        int len = 0;
        for (int i : arr) {
            len += i;
        }
        int[] lst = new int[len];
        for (int i : arr) {
            for (int j = 0; j < i; j++) {
                lst[n] = i;
                n++;
            }
        }
        return lst;
    }

    public static void main(String[] args) {
        SLList a = new SLList(1);
        a.addFirst(2);
        a.addFirst(3);
        a.reverse();
        a.printList();

        int[] x = new int[]{5, 9, 14, 15};
        int[] y = new int[]{3, 2, 1};
        System.out.println(Arrays.toString(insert2(x, 6, 2)));
        reverse(x);
        System.out.println(Arrays.toString(x));
        System.out.println(Arrays.toString(replicate(y)));
    }
}


