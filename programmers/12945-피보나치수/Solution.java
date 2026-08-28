class Solution {
    private static final int MOD = 1234567;

    public int solution(int n) {
        int prev2 = 0; // F(0)
        int prev1 = 1; // F(1)

        for (int i = 2; i <= n; i++) {
            int current = (prev1 + prev2) % MOD;
            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.solution(3)); // 2
        System.out.println(sol.solution(5)); // 5
    }
}
