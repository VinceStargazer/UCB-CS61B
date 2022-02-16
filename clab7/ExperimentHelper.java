import java.util.Random;

/**
 * Created by hug.
 */
public class ExperimentHelper {

    /** Returns the internal path length for an optimum binary search tree of
     *  size N. Examples:
     *  N = 1, OIPL: 0
     *  N = 2, OIPL: 1
     *  N = 3, OIPL: 2
     *  N = 4, OIPL: 4
     *  N = 5, OIPL: 6
     *  N = 6, OIPL: 8
     *  N = 7, OIPL: 10
     *  N = 8, OIPL: 13
     */
    public static int optimalIPL(int N) {
        int sum = 0;
        for (int i = 1; i <= N; i++) {
            sum += (int) (Math.log(i) / Math.log(2));
        }
        return sum;
    }

    /** Returns the average depth for nodes in an optimal BST of
     *  size N.
     *  Examples:
     *  N = 1, OAD: 0
     *  N = 5, OAD: 1.2
     *  N = 8, OAD: 1.625
     * @return
     */
    public static double optimalAverageDepth(int N) {
        return (double) optimalIPL(N) / N;
    }

    public static void knottOperation(BST<Integer> tree) {
        int N = tree.size();
        int key = tree.getRandomKey();
        Random r = new Random();
        tree.deleteTakingSuccessor(key);
        while (tree.size() < N) {
            tree.add(r.nextInt());
        }
    }

    public static void epplingerOperation(BST<Integer> tree)  {
        int N = tree.size();
        int key = tree.getRandomKey();
        Random r = new Random();
        tree.deleteTakingRandom(key);
        while (tree.size() < N) {
            tree.add(r.nextInt());
        }
    }
}
