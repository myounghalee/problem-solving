import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution {
    public boolean isAnagram(String s, String t) {

        // 방법1
        /*
        char[] str = s.toCharArray();
        Arrays.sort(str);

        char[] target = t.toCharArray();
        Arrays.sort(target);
        return Arrays.equals(str, target);
        */

        // 방법2
        /*
        int[] str = new int[26];
        for (char c : s.toCharArray()) str[c - 'a']++;

        int[] target = new int[26];
        for (char c : t.toCharArray()) target[c - 'a']++;
        return Arrays.equals(str, target);
        */

        // 방법3
        Map<Character, Integer> str = new HashMap<>();
        for (char c : s.toCharArray()) str.put(c, str.getOrDefault(c, 0) + 1);

        Map<Character, Integer> target = new HashMap<>();
        for (char c : t.toCharArray()) target.put(c, target.getOrDefault(c, 0) + 1);
        return str.equals(target);
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.isAnagram("anagram", "nagaram")); // true
        System.out.println(sol.isAnagram("rat", "car")); // false
    }
}
