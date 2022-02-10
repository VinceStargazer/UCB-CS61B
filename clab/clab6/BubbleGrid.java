public class BubbleGrid {
    private final int[][] grid;
    private final int height;
    private final int width;

    /* Create new BubbleGrid with bubble/space locations specified by grid.
     * Grid is composed of only 1's and 0's, where 1's denote a bubble, and
     * 0's denote a space. */
    public BubbleGrid(int[][] grid) {
        this.grid = grid;
        height = grid.length;
        width = grid[0].length;
    }

    /* Returns an array whose i-th element is the number of bubbles that
     * fall after the i-th dart is thrown. Assume all elements of darts
     * are unique, valid locations in the grid. Must be non-destructive
     * and have no side-effects to grid. */
    public int[] popBubbles(int[][] darts) {
        int index = 0;
        int sum = 0;
        int[] fallBubbles = new int[darts.length];
        for (int[] dart : darts) {
            if (dart[0] >= height || dart[1] >= width) {
                throw new IndexOutOfBoundsException("Dart out of the grid.");
            } else if (grid[dart[0]][dart[1]] == 0) {
                fallBubbles[index] = 0;
                index++;
            } else {
                grid[dart[0]][dart[1]] = 0;
                UnionFind stuck = stuck();
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        if (grid[i][j] == 1 && !stuck.connected(index(i, j), 0)) {
                            grid[i][j] = 0;
                            sum++;
                        }
                    }
                }
                fallBubbles[index] = sum;
                index++;
            }
        }
        return fallBubbles;
    }

    private int index(int i, int j) {
        return i * width + j;
    }

    private UnionFind stuck() {
        UnionFind stuck = new UnionFind(height * width);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] == 1) {
                    if ((i == 0)
                            || (stuck.connected(index(i - 1, j), 0))
                            || (j > 0 && stuck.connected(index(i, j - 1), 0))) {
                        stuck.union(index(i, j), 0);
                    }
                }
            }
        }
        return stuck;
    }
}
