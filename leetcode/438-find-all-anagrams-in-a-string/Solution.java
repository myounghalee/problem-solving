import java.util.*;

class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        int n = s.length(), m = p.length();

        int[] need = new int[26];
        for (int i = 0; i < m; i++) need[p.charAt(i) - 'a']++;

        int[] window = new int[26];
        for (int i = 0; i < m; i++) window[s.charAt(i) - 'a']++;
        if (Arrays.equals(need, window)) result.add(0);

        for (int i = m; i < n; i++) {
            window[s.charAt(i - m) - 'a']--;
            window[s.charAt(i) -  'a']++;
            if (Arrays.equals(need, window)) result.add(i - m + 1);
        }

        return result;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.findAnagrams("cbaebabacd", "abc")); // [0, 6]
        System.out.println(sol.findAnagrams("abab", "ab")); // [0, 1, 2]
    }
}
