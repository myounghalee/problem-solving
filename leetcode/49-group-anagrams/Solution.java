import java.util.*;

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {

        Map<String, List<String>> map = new HashMap<>();

        for (String str : strs) {
            char[] c = str.toCharArray();
            Arrays.sort(c);
            map.computeIfAbsent(Arrays.toString(c), k -> new ArrayList<>()).add(str);
        }

        List<List<String>> result = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            result.add(entry.getValue());
        }
        return result;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"})); // [[bat], [nat, tan], [ate, eat, tea]]
        System.out.println(sol.groupAnagrams(new String[]{""})); // [[]]
        System.out.println(sol.groupAnagrams(new String[]{"a"})); // [[a]]
    }
}
