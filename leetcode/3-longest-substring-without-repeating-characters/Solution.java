import java.util.HashSet;
import java.util.Set;

class Solution {
    public int lengthOfLongestSubstring(String s) {

        Set<Character> set = new HashSet<>();

        int left = 0;   // 윈도우 왼쪽 끝
        int len = 0;    // 현재 윈도우 길이
        int maxLen = 0; // 지금까지 본 최대 윈도우 길이
        for (int right = 0; right < s.length(); right++) {
            char ch = s.charAt(right);
            len++;

            while (set.contains(ch)) {
                set.remove(s.charAt(left++));
                len--;
            }
            set.add(ch);

            maxLen = Math.max(maxLen, len);
        }

        return maxLen;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.lengthOfLongestSubstring("abcabcbb")); // 3
        System.out.println(sol.lengthOfLongestSubstring("bbbbb")); // 1
        System.out.println(sol.lengthOfLongestSubstring("pwwkew")); // 3
        System.out.println(sol.lengthOfLongestSubstring("dvdf")); // 3
        System.out.println(sol.lengthOfLongestSubstring(" ")); // 1
        System.out.println(sol.lengthOfLongestSubstring("")); // 0
        System.out.println(sol.lengthOfLongestSubstring("a1!a1!b")); // 4
    }
}
