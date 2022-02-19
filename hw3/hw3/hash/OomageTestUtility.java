package hw3.hash;

import java.util.ArrayList;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int N = oomages.size();
        ArrayList<Oomage>[] buckets = new ArrayList[M];

        for (int i = 0; i < M; i++) {
            buckets[i] = new ArrayList<>();
        } // initialize bucket list

        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            buckets[bucketNum].add(o);
        } // place every oomage in buckets

        for (ArrayList bucket : buckets) {
            if (bucket.size() <= N / 50 || bucket.size() >= N / 2.5) {
                return false;
            }
        } // examine the spread pattern
        return true;
    }
}
