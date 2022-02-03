public class disc04 {
    public static int[] flatten(int[][] x) {
        int totalLength = 0;

        for (int[] lst : x) {
            totalLength += lst.length;
        }

        int[] a = new int[totalLength];
        int aIndex = 0;

        for (int[] lst : x) {
            for (int j : lst) {
                a[aIndex] = j;
                aIndex++;
            }
        }

        return a;
    }

    public static void main(String[] args) {
        int[][] input = {{1, 2, 3}, {}, {7, 8}};
        int[] expected = {1, 2, 3, 7, 8};
        int[] actual = flatten(input);
        if (java.util.Arrays.equals(expected, actual)) {
            System.out.println("Passed");
        } else {
            System.out.println("Failed");
        }
    }
}
