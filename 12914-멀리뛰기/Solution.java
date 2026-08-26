class Solution {
    private static final int MOD = 1234567;

    public long solution(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;

        long prev2 = 1;
        long prev1 = 2;
        for (int i = 3; i <= n; i++) {
            long cur = (prev1 + prev2) % MOD;
            prev2 = prev1;
            prev1 = cur;
        }
        return prev1;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.solution(4)); // 5
        System.out.println(sol.solution(3)); // 3
    }
}
