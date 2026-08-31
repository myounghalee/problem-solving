import java.util.ArrayList;
import java.util.List;

class Solution {
    public int nthUglyNumber(int n) {
        int x = 1, x2 = 0, x3 = 0, x5 = 0;
        int i = 1, i2 = 0, i3 = 0, i5 = 0;
        List<Integer> list = new ArrayList<>();
        list.add(1);

        while (i <= n) {
            if (i == n) return x;

            x2 = 2 * list.get(i2);
            x3 = 3 * list.get(i3);
            x5 = 5 * list.get(i5);

            x = Math.min(x2, Math.min(x3, x5));
            list.add(x);

            if (x2 == x) i2++;
            if (x3 == x) i3++;
            if (x5 == x) i5++;
            i++;
        }

        return 1;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        check(solution.nthUglyNumber(11), 15);

        // 예 1: n = 10 -> 12
        check(solution.nthUglyNumber(10), 12);

        // 예 2: n = 1 -> 1
        check(solution.nthUglyNumber(1), 1);
    }

    private static void check(int actual, int expected) {
        System.out.println((actual == expected ? "OK" : "FAIL") + " actual=" + actual + " expected=" + expected);
    }
}
