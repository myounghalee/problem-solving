import java.util.*;

class Solution {
    public int solution(int[][] game_board, int[][] table) {
        List<int[][]> blanks = new ArrayList<>();
        List<int[][]> puzzles = new ArrayList<>();
        Deque<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[game_board.length][game_board[0].length];

        // game_board 빈칸 찾기
        find(game_board, blanks, q, visited, 1);
        visited = new boolean[table.length][table[0].length];

        // game_board 빈칸 찾기
        find(table, puzzles, q, visited, 0);

        int answer = 0;
        boolean[] used = new boolean[puzzles.size()];
        for (int[][] blank : blanks) {
            for (int i = 0; i < puzzles.size(); i++) {
                int[][] puzzle = puzzles.get(i);
                if (blank.length == puzzle.length && !used[i] && isMatch(blank, puzzle)) {
                    answer += puzzle.length;
                    used[i] = true;
                    break;
                }
            }
        }

        return answer;
    }

    private boolean isMatch(int[][] blank, int[][] puzzle) {
        int[][] p = puzzle.clone();
        for (int i = 0; i < 4; i++) {
            lotate(p);
            if (match(blank, p)) return true;
        }

        return false;
    }

    private void lotate(int[][] blank) {
        for (int i = 0; i < blank.length; i++) {
            blank[i] = new int[] {blank[i][1], -blank[i][0]};
        }
    }

    private boolean match(int[][] blank, int[][] puzzle) {
        Arrays.sort(blank, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        Arrays.sort(puzzle, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);

        int b0x = Integer.MAX_VALUE, b0y = Integer.MAX_VALUE;
        for (int[] b : blank) {
            b0x = Math.min(b0x, b[0]);
            b0y = Math.min(b0y, b[1]);
        }

        int p0x = Integer.MAX_VALUE, p0y = Integer.MAX_VALUE;
        for (int[] p : puzzle) {
            p0x = Math.min(p0x, p[0]);
            p0y = Math.min(p0y, p[1]);
        }

        for (int i = 0; i < blank.length; i++) {
            int bx = blank[i][0] - b0x, by = blank[i][1] - b0y;
            int px = puzzle[i][0] - p0x, py = puzzle[i][1] - p0y;
            if (bx != px || by != py) return false;
        }

        return true;
    }

    private void find(int[][] t, List<int[][]> puzzle, Deque<int[]> q, boolean[][] visited, int no) {
        int[] dr = {1, -1, 0, 0}, dc = {0, 0, 1, -1};

        for (int r = 0; r < t.length; r++) {
            for (int c = 0; c < t[0].length; c++) {
                if (t[r][c] == no) continue;
                if (visited[r][c]) continue;

                List<int[]> p = new ArrayList<>();
                int[] dot = new int[]{r, c};
                p.add(dot);
                q.add(dot);
                visited[r][c] = true;

                while (!q.isEmpty()) {
                    int[] cur = q.poll();
                    int cr = cur[0], cc = cur[1];
                    for (int i = 0; i < 4; i++) {
                        int nr = cr + dr[i], nc = cc + dc[i];
                        if (nr < 0 || nr >= t.length || nc < 0 || nc >= t[0].length) continue;
                        if (t[nr][nc] == no) continue;
                        if (visited[nr][nc]) continue;

                        int[] nd = new int[]{nr, nc};
                        p.add(nd);
                        q.add(nd);
                        visited[nr][nc] = true;
                    }
                }
                puzzle.add(p.toArray(new int[p.size()][]));
            }
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.solution(
                new int[][]{{1, 1, 0, 0, 1, 0}, {0, 0, 1, 0, 1, 0}, {0, 1, 1, 0, 0, 1}, {1, 1, 0, 1, 1, 1}, {1, 0, 0, 0, 1, 0}, {0, 1, 1, 1, 0, 0}},
                new int[][]{{1, 0, 0, 1, 1, 0}, {1, 0, 1, 0, 1, 0}, {0, 1, 1, 0, 1, 1}, {0, 0, 1, 0, 0, 0}, {1, 1, 0, 1, 1, 0}, {0, 1, 0, 0, 0, 0}}
        )); // 14
        System.out.println(sol.solution(
                new int[][]{{0, 0, 0}, {1, 1, 0}, {1, 1, 1}},
                new int[][]{{1, 1, 1}, {1, 0, 0}, {0, 0, 0}}
        )); // 0
    }
}
