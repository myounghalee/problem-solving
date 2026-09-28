import java.util.HashMap;
import java.util.Map;

class Solution {
    public boolean wordPattern(String pattern, String s) {
        String[] words = s.split(" ");
        if (pattern.length() != words.length) return false;

        Map<Character, String> map_ps = new HashMap<>();
        Map<String, Character> map_sp = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            if (map_ps.containsKey(pattern.charAt(i)) && !map_ps.get(pattern.charAt(i)).equals(words[i])) {
                return false;
            }
            if (map_sp.containsKey(words[i]) && !map_sp.get(words[i]).equals(pattern.charAt(i))) {
                return false;
            }
            map_ps.put(pattern.charAt(i), words[i]);
            map_sp.put(words[i], pattern.charAt(i));
        }

        return true;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.wordPattern("abba", "dog cat cat dog"));  // true
        System.out.println(sol.wordPattern("abba", "dog cat cat fish")); // false
        System.out.println(sol.wordPattern("aaaa", "dog cat cat dog"));  // false
    }
}
