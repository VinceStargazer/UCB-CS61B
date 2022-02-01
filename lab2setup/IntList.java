public class IntList {
    public int first;
    public IntList rest;

    public IntList(int f, IntList r) {
        first = f;
        rest = r;
    }

    public static IntList square(IntList L) {
        if (L == null) {
            return null;
        }
        return new IntList(L.first * L.first, square(L.rest));
    }

    public static void squareDestructive(IntList L) {
        L.first *= L.first;
        if (L.rest != null) {
            squareDestructive(L.rest);
        }
    }

    public static void main(String[] args) {
        IntList a = new IntList(5, null);
        a = new IntList(10, a);
        IntList b = square(a);
        System.out.println(b.first);
        squareDestructive(a);
        System.out.println(a.first);
    }
}
