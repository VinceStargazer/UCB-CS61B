package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int[][] grid;
    private int openSites = 0;
    private final int length, topSite, bottomSite;
    private final WeightedQuickUnionUF unionSites, unionWithoutBottom;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Length must be positive");
        }
        grid = new int[N][N];
        length = N;
        topSite = N * N;
        bottomSite = topSite + 1;
        unionSites = new WeightedQuickUnionUF(bottomSite + 1);
        unionWithoutBottom = new WeightedQuickUnionUF(bottomSite + 1);
    } // create N-by-N grid, with all sites initially blocked

    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }
        grid[row][col] = 1;
        openSites++;
        unionAround(row, col);
    } // open the site (row, col) if it is not open already

    private final int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    private void unionAround(int row, int col) {
        for (int[] dir : directions) {
            int x = row + dir[0];
            int y = col + dir[1];
            if (0 <= x && x < length && 0 <= y && y < length && isOpen(x, y)) {
                unionSites.union(index(row, col), index(x, y));
                unionWithoutBottom.union(index(row, col), index(x, y));
            }
            if (row == 0) {
                unionSites.union(index(row, col), topSite);
                unionWithoutBottom.union(index(row, col), topSite);
            }
            if (row == length - 1) {
                unionSites.union(index(row, col), bottomSite);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row][col] == 1;
    } // is the site (row, col) open?

    public boolean isFull(int row, int col) {
        return isOpen(row, col) && unionWithoutBottom.connected(index(row, col), topSite);
    } // is the site (row, col) full?

    public int numberOfOpenSites() {
        return openSites;
    } // number of open sites

    public boolean percolates() {
        return unionSites.connected(topSite, bottomSite);
    } // does the system percolate?

    private void validate(int row, int col) {
        if (row < 0 || col < 0 || row >= length || col >= length) {
            throw new IndexOutOfBoundsException("Site out of the grid");
        }
    }

    private int index(int row, int col) {
        return row * length + col;
    }

    public static void main(String[] args) {

    } // use for unit testing (not required, but keep this here for the autograder)
}
