class Solution {

    char[][] plays;

    public int solution(int n, int[][] results) {
        plays = new char[n][n]; // plays[i][j]: i가 j에게 'W'(승) / 'L'(패), 미정이면 0

        for (int[] result : results) {
            plays[result[0] - 1][result[1] - 1] = 'W';
            plays[result[1] - 1][result[0] - 1] = 'L';
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) continue;
                if (plays[i][j] == 'W' || plays[i][j] == 'L') {
                    dfs(i, j, n); // i-j 관계를 j를 거쳐 다른 선수에게 전파
                }
            }
        }

        int answer = 0;
        for (char[] row : plays) {
            int sum = 0;
            for (char col : row) {
                sum += col == 0 ? 0 : 1; // 관계가 확정된 상대 수
            }
            if (sum == n - 1) answer++; // 나머지 전원과의 관계가 확정되면 순위 확정
        }

        return answer;
    }

    private void dfs(int i, int j, int n) {
        char temp = plays[i][j]; // i -> j 관계 (W 또는 L)
        char temp2 = temp == 'W' ? 'L' : 'W'; // 반대편(j -> i, k -> i)에 채울 관계
        for (int k = 0; k < n; k++) {
            if (k == i) continue;
            if (plays[j][k] == temp && plays[i][k] != temp) { // 아직 i-k가 같은 관계로 확정되지 않았을 때만 전파
                plays[i][k] = temp;
                plays[k][i] = temp2;
                dfs(i, k, n);
            }
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.solution(5, new int[][]{{4, 3}, {4, 2}, {3, 2}, {1, 2}, {2, 5}})); // 2
    }
}
