class Solution {
    public int rob(int[] nums) {

        // dp[i - 2] + nums[i] vs dp[i - 1]
        // 2, 7, 11, 11, 12
        // 100, 100, 101, 200

        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        if (nums.length == 1) return nums[0];

        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
        }

        return dp[nums.length - 1];
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.rob(new int[]{1, 2, 3, 1})); // 4
        System.out.println(sol.rob(new int[]{2, 7, 9, 3, 1})); // 12

        // 집이 하나뿐
        System.out.println(sol.rob(new int[]{5})); // 5

        // 집이 둘뿐 — 둘을 동시에 털 수 없으므로 더 큰 쪽만
        System.out.println(sol.rob(new int[]{2, 1})); // 2

        // 앞쪽 집들이 훨씬 커서 뒤쪽을 포기하는 게 나은 경우
        System.out.println(sol.rob(new int[]{100, 1, 1, 100})); // 200

        // 모두 0
        System.out.println(sol.rob(new int[]{0, 0, 0})); // 0
    }
}
