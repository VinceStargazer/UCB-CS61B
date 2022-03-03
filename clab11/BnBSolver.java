import java.util.ArrayList;
import java.util.List;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {
    private final List<Bear> sortedBears;
    private final List<Bed> sortedBeds;

    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        Pair<List<Bear>, List<Bed>> solution = quickSort(bears, beds);
        sortedBears = solution.first();
        sortedBeds = solution.second();
    }

    /**
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears() {
        return sortedBears;
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        return sortedBeds;
    }

    private static <P> List<P> concatenate(List<P> l1, List<P> l2, List<P> l3) {
        List<P> concatenated = new ArrayList<>(l1);
        concatenated.addAll(l2);
        concatenated.addAll(l3);
        return concatenated;
    }

    private static <P extends Comparable<Q>, Q extends Comparable<P>> void partition(
            List<P> unsorted, Q pivot, List<P> less,
            List<P> equal, List<P> greater) {
        for (P item : unsorted) {
            if (item.compareTo(pivot) < 0) {
                less.add(item);
            } else if (item.compareTo(pivot) == 0) {
                equal.add(item);
            } else {
                greater.add(item);
            }
        }
    }

    private static Pair<List<Bear>, List<Bed>> quickSort(List<Bear> bears, List<Bed> beds) {
        if (bears == null || beds == null || bears.size() != beds.size()) {
            throw new IllegalArgumentException();
        }

        if (bears.size() < 2) {
            return new Pair<>(bears, beds);
        }

        List<Bear> smallerBears = new ArrayList<>();
        List<Bear> equalBears = new ArrayList<>();
        List<Bear> largerBears = new ArrayList<>();
        partition(bears, beds.get(0), smallerBears, equalBears, largerBears);

        List<Bed> smallerBeds = new ArrayList<>();
        List<Bed> equalBeds = new ArrayList<>();
        List<Bed> largerBeds = new ArrayList<>();
        partition(beds, equalBears.get(0), smallerBeds, equalBeds, largerBeds);

        Pair<List<Bear>, List<Bed>> smaller = quickSort(smallerBears, smallerBeds);
        Pair<List<Bear>, List<Bed>> larger = quickSort(largerBears, largerBeds);

        return new Pair<>(concatenate(smaller.first(), equalBears, larger.first()),
                concatenate(smaller.second(), equalBeds, larger.second()));
    }
}
