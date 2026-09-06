class Solution {
    public String minWindow(String s, String t) {

        if (t.length() > s.length()) return "";

        int[] need = new int[128];
        int required = t.length();
        int min = Integer.MAX_VALUE;
        String result = "";

        for (char c : t.toCharArray()) need[c]++;

        int left = 0;
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (need[c] > 0) required--;
            need[c]--;

            while (required == 0) {
                if (min > right - left + 1) {
                    min = right - left + 1;
                    result = s.substring(left, right + 1);
                }
                char lc = s.charAt(left++);
                need[lc]++;
                if (need[lc] > 0) required++;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.minWindow("cabwefgewcwaefgcf", "cae"));
        System.out.println(sol.minWindow("ADOBECODEBANC", "ABC")); // BANC
        System.out.println(sol.minWindow("a", "a")); // a
        System.out.println(sol.minWindow("a", "aa")); // (빈 문자열)

        // t가 s보다 긴 경우
        System.out.println(sol.minWindow("a", "aab")); // (빈 문자열)

        // 필요한 문자가 여러 번 등장하는 경우
        System.out.println(sol.minWindow("aa", "aa")); // aa

        // t에 s에 없는 문자가 섞인 경우
        System.out.println(sol.minWindow("ab", "b")); // b
    }
}
