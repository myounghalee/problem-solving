import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {

        int[][] dp = new int[triangle.size()][triangle.getLast().size()];

        for (int i = 0; i < triangle.size(); i++) {
            for (int j = 0; j < triangle.get(i).size(); j++) {
                int value = triangle.get(i).get(j);
                if (i == 0) {
                    dp[i][j] = value;
                } else if (j == 0) {
                    dp[i][j] = dp[i - 1][j] + value;
                } else if (j == triangle.get(i).size() - 1) {
                    dp[i][j] = dp[i - 1][j - 1] + value;
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1], dp[i - 1][j]) + value;
                }
            }
        }

        int min = Integer.MAX_VALUE;
        for (int i : dp[dp.length - 1]) {
            min = Math.min(min, i);
        }
        return min;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.minimumTotal(List.of(
                List.of(2),
                List.of(3, 4),
                List.of(6, 5, 7),
                List.of(4, 1, 8, 3)))); // 11

        System.out.println(sol.minimumTotal(List.of(List.of(-10)))); // -10

        // 그리디(매 줄에서 더 작은 값을 고름)가 틀리는 대표 사례
        // 그리디: 0 -> 1(2보다 작음) -> 100  => 101
        // 최적:   0 -> 2(1보다 큼)   -> -100 => -98  (당장은 손해여도 다음 줄의 큰 음수를 잡아야 함)
        System.out.println(sol.minimumTotal(List.of(
                List.of(0),
                List.of(1, 2),
                List.of(100, 100, -100)))); // -98

        // 줄이 둘뿐
        System.out.println(sol.minimumTotal(List.of(
                List.of(-1),
                List.of(2, 3)))); // 1
    }
}
