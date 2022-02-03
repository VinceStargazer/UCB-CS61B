public class HorribleSteve {
    public static boolean mess() {
        int i = 0;
        for (int j = 0; i < 500; ++i, ++j) {
            if (!Flik.isSameNumber(i, j)) {
                return false;
            }
        }
        return true;
    }
}
