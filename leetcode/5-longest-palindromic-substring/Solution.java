import java.text.MessageFormat;
import java.util.Arrays;

class Solution {
    public String longestPalindrome(String s) {

        int[] res = new int[2];
        char[] c = s.toCharArray();
        for (int i = 0; i < c.length; i++) {
            int l = i - 1, r = i + 1;
            res = solve(res, c, l, r);

            l = i;
            r = i + 1;
            res = solve(res, c, l, r);
        }

        StringBuilder result = new StringBuilder();
        for (int i = res[0]; i <= res[1]; i++) result.append(c[i]);
        return result.toString();
    }

    private int[] solve(int[] res, char[] c, int l, int r) {
        while (l >= 0 && r < c.length) {
            if (c[l] == c[r]) {
                if (res[1] - res[0] < r - l) res = new int[]{l, r};
            }
            else break;
            l--;
            r++;
        }
        return res;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.longestPalindrome("babad")); // bab (혹은 aba)
        System.out.println(sol.longestPalindrome("cbbd")); // bb
        System.out.println(sol.longestPalindrome("racecarxybby")); // racecar
    }
}
