package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private final double[] results;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Inputs must be positive");
        }
        results = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation test = pf.make(N);
            int count = 0;
            while (!test.percolates()) {
                int x = StdRandom.uniform(N);
                int y = StdRandom.uniform(N);
                if (!test.isOpen(x, y)) {
                    test.open(x, y);
                    count++;
                }
            }
            results[i] = (double) count / (N * N);
        }
    }  // perform T independent experiments on an N-by-N grid

    public double mean() {
        return StdStats.mean(results);
    } // sample mean of percolation threshold

    public double stddev() {
        return StdStats.stddev(results);
    } // sample standard deviation of percolation threshold

    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(results.length);
    } // low endpoint of 95% confidence interval

    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(results.length);
    } // high endpoint of 95% confidence interval
}
