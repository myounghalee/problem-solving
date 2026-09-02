import java.util.HashSet;
import java.util.Set;

class Solution {
    public boolean isValidSudoku(char[][] board) {

        Set<Integer> row;
        Set<Integer> col;
        Set<Integer> block;
        for (int i = 0; i < 9; i++) {
            row = new HashSet<>();
            col = new HashSet<>();
            block = new HashSet<>();
            for (int j = 0; j < 9; j++) {
                if (check(board[i][j], row)) return false;
                if (check(board[j][i], col)) return false;
                if (check(board[(j / 3) + 3 * (i / 3)][(j % 3) + 3 * (i % 3)], block)) return false;
            }
        }

        return true;
    }

    private boolean check(char val, Set<Integer> set) {
        if (val == '.') return false;
        if (set.contains(val - '0')) return true;
        set.add(val - '0');
        return false;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 유효한 판
        char[][] valid = toBoard(
                "53..7....",
                "6..195...",
                ".98....6.",
                "8...6...3",
                "4..8.3..1",
                "7...2...6",
                ".6....28.",
                "...419..5",
                "....8..79");

        // 위 판의 (0,0)만 5 → 8: 좌측 상단 3x3 박스에 8이 두 번
        char[][] invalidBox = toBoard(
                "83..7....",
                "6..195...",
                ".98....6.",
                "8...6...3",
                "4..8.3..1",
                "7...2...6",
                ".6....28.",
                "...419..5",
                "....8..79");

        System.out.println(sol.isValidSudoku(valid)); // true
        System.out.println(sol.isValidSudoku(invalidBox)); // false
        System.out.println(sol.isValidSudoku(toBoard(
                ".........", ".........", ".........",
                ".........", ".........", ".........",
                ".........", ".........", "........."))); // true (전부 빈 칸)
    }

    private static char[][] toBoard(String... rows) {
        char[][] b = new char[9][];
        for (int i = 0; i < 9; i++) b[i] = rows[i].toCharArray();
        return b;
    }
}
