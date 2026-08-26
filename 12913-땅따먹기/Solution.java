class Solution {
    public int solution(int[][] land) {
        int answer = 0;
        int[][] dp = new int[land.length][land[0].length];

        for (int i = 0; i < land.length; i++) {
            for (int j = 0; j < land[0].length; j++) {
                int max = 0;
                if (i != 0) {
                    max = getMax(dp[i-1], j);
                }
                dp[i][j] = land[i][j] + max;
            }
        }

        for (int i : dp[dp.length - 1]) {
            answer = Math.max(answer, i);
        }

        return answer;
    }

    private int getMax(int[] dp, int j) {
        int max = 0;
        for (int i = 0; i < dp.length; i++) {
            if (i == j) continue;
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.solution(new int[][]{{1, 2, 3, 5}, {5, 6, 7, 8}, {4, 3, 2, 1}})); // 16
    }
}
