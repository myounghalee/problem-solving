import java.util.Arrays;

class Solution {
    private static final int MOD = 1_000_000_007;

    public int solution(int m, int n, int[][] puddles) {
        int[][] dp = new int[n][m];
        for (int[] row : dp) Arrays.fill(row, 1);

        for (int[] p : puddles) {
            dp[p[1] - 1][p[0] - 1] = 0; // p = [x, y] -> dp[y-1][x-1]
        }

        for (int y = 0; y < n; y++) {
            for (int x = 0; x < m; x++) {
                if (y == 0 && x == 0) continue;
                if (dp[y][x] == 0) continue;

                int fromUp = (y == 0) ? 0 : dp[y - 1][x];
                int fromLeft = (x == 0) ? 0 : dp[y][x - 1];
                dp[y][x] = (fromUp + fromLeft) % MOD;
            }
        }

        return dp[n - 1][m - 1];
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.solution(4, 3, new int[][]{{2, 2}})); // 4
    }
}
