public class HelloNumbers {
    public static void main(String[] args) {
        for (int x = 0; x < 10; x = x + 1) {
            int sum = x;
            for (int i = 1; i < x; i = i + 1) {
	sum = sum + i;
            }
            System.out.print(sum + " ");
        }
    }
}