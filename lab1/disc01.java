public class disc01 {
    public static void main(String[] args){
        System.out.println(fib(8));
    }

    public static int fib(int n) {
        if (n <= 1) {
            return n;
        }
        return fib(n-2) + fib(n-1);
    }

    public static int fib2(int n, int k, int f0, int f1) {
        if (n == k) {
            return f0;
        }
        return fib2(n, k + 1, f1, f0 + f1);
    }
}
