import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {

        int maxX = 0, maxY = 0;
        for (int[] rect : rectangle) {
            maxX = Math.max(maxX, rect[2]) * 2;
            maxY = Math.max(maxY, rect[3]) * 2;
        }

        boolean[][] map = new boolean[maxX + 1][maxY + 1];
        for (int[] rect : rectangle) {
            for (int x = rect[0] * 2; x <= rect[2] * 2; x++) {
                map[x][rect[1] * 2] = true;
                map[x][rect[3] * 2] = true;
            }
            for (int y = rect[1] * 2; y <= rect[3] * 2; y++) {
                map[rect[0] * 2][y] = true;
                map[rect[2] * 2][y] = true;
            }
        }

        for (int[] rect : rectangle) {
            for (int i = rect[0] * 2 + 1; i < rect[2] * 2; i++) {
                for (int j = rect[1] * 2 + 1; j < rect[3] * 2; j++) {
                    map[i][j] = false;
                }
            }
        }

        int[] dx = {-1, 1, 0, 0}, dy = {0, 0, -1, 1};
        boolean[][] visited = new boolean[maxX + 1][maxY + 1];
        Deque<int[]> q = new ArrayDeque<>();
        q.add(new int[]{characterX * 2, characterY * 2, 0});
        visited[characterX * 2][characterY * 2] = true;
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int cx =  cur[0], cy = cur[1], count = cur[2];
            if (cx == itemX * 2 && cy == itemY * 2) return count / 2;

            for (int i = 0; i < 4; i++) {
                int nx = cx + dx[i], ny = cy + dy[i];
                if (nx < 0 || ny < 0 || nx >= maxX + 1 || ny >= maxY + 1) continue;
                if (!map[nx][ny]) continue;
                if (visited[nx][ny]) continue;
                visited[nx][ny] = true;
                q.add(new int[]{nx, ny, count + 1});
            }
        }

        int answer = 0;
        return answer;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.solution(new int[][]{{1, 1, 7, 4}, {3, 2, 5, 5}, {4, 3, 6, 9}, {2, 6, 8, 8}}, 1, 3, 7, 8)); // 17
        System.out.println(sol.solution(new int[][]{{1, 1, 8, 4}, {2, 2, 4, 9}, {3, 6, 9, 8}, {6, 3, 7, 7}}, 9, 7, 6, 1)); // 11
        System.out.println(sol.solution(new int[][]{{1, 1, 5, 7}}, 1, 1, 4, 7)); // 9
        System.out.println(sol.solution(new int[][]{{2, 1, 7, 5}, {6, 4, 10, 10}}, 3, 1, 7, 10)); // 15
        System.out.println(sol.solution(new int[][]{{2, 2, 5, 5}, {1, 3, 6, 4}, {3, 1, 4, 6}}, 1, 4, 6, 3)); // 10
    }
}
