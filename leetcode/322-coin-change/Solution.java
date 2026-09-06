class Solution {

    int[] dp;

    public int coinChange(int[] coins, int amount) {
        dp = new int[amount + 1];
        return dfs(coins, amount);
    }

    private int dfs(int[] coins, int amount) {
        if (amount == 0) return 0;
        if (amount < 0) return -1;
        if (dp[amount] != 0) return dp[amount]; // 0이면 아직 계산 안 한 것 (amount==0은 위에서 이미 처리)

        int min = Integer.MAX_VALUE;
        for (int coin : coins) {
            if (amount >= coin) { // == 포함: 정확히 이 동전 하나로 끝나는 경우도 재귀가 처리
                int tmp = dfs(coins, amount - coin);
                if (tmp >= 0 && min > tmp) min = tmp; // tmp == -1(불가능)은 후보에서 제외
            }
        }
        dp[amount] = min == Integer.MAX_VALUE ? -1 : min + 1;
        return dp[amount];
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.coinChange(new int[]{1, 2, 5}, 11)); // 3
        System.out.println(sol.coinChange(new int[]{2}, 3)); // -1
        System.out.println(sol.coinChange(new int[]{1}, 0)); // 0

        // 동전 하나로 정확히 나누어떨어지는 경우
        System.out.println(sol.coinChange(new int[]{1}, 2)); // 2

        // 그리디로 풀면 틀리는 대표 사례: 큰 동전부터 채우면 최적이 안 나옴
        System.out.println(sol.coinChange(new int[]{1, 3, 4}, 6)); // 2 (3+3, 그리디면 4+1+1=3개로 틀림)

        // 큰 목표 금액
        System.out.println(sol.coinChange(new int[]{186, 419, 83, 408}, 6249)); // 20

        // 동전 값이 중복되는 경우
        System.out.println(sol.coinChange(new int[]{6, 6}, 6)); // 1
    }
}
