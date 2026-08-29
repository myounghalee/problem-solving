class Solution {
    public int longestPalindromeSubseq(String s) {
        char[] c = s.toCharArray();
        int[][] dp = new int[c.length][c.length];
        for (int i = 0 ; i < c.length; i++) dp[i][i] = 1;

        for (int i = 1; i < c.length; i++) {
            for (int j = 0; j < c.length - i; j++) {
                if (c[j] == c[j + i]) dp[j][j + i] = dp[j + 1][j + i -1] + 2;
                else dp[j][j + i] = Math.max(dp[j][j + i - 1], dp[j + 1][j + i]);
            }
        }

        return dp[0][c.length - 1];
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.longestPalindromeSubseq("bbbab")); // 4
        System.out.println(sol.longestPalindromeSubseq("cbbd")); // 2
    }
}
