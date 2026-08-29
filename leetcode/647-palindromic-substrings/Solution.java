class Solution {
    public int countSubstrings(String s) {
        char[] c = s.toCharArray();
        int n = s.length();
        int[][] dp = new int[n][n]; // dp[l][r]: s[l..r]가 팰린드롬이면 1

        for (int i = 0; i < n; i++) { // i: 구간 길이 - 1 (짧은 구간부터)
            for (int j = 0; j < n - i; j++) { // j: 시작 인덱스, j+i: 끝 인덱스
                if (c[j] == c[j + i]) {
                    // 길이 1은 항상 팰린드롬, 길이 2는 안쪽을 볼 필요 없음(i==1), 길이 3 이상은 안쪽 구간도 팰린드롬이어야 함
                    if (j == j + i || i == 1 || dp[j + 1][j + i - 1] == 1) dp[j][j + i]++;
                }
            }
        }

        int res = 0;
        for (int[] row : dp) {
            for (int v : row) res += v; // dp가 1인 칸의 총 개수 = 팰린드롬 부분 문자열 개수
        }
        return res;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.countSubstrings("abc")); // 3
        System.out.println(sol.countSubstrings("aaa")); // 6
    }
}
