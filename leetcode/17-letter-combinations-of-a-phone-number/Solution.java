import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<String> letterCombinations(String digits) {

        String[] alphabet = new String[]{null, null, "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

        List<String> res = new ArrayList<>();
        if (digits.isEmpty()) return res;
        res.add("");

        for (char digit : digits.toCharArray()) {
            List<String> temp = new ArrayList<>();
            for (int i = 0; i < alphabet[digit - '0'].length(); i++) {
                char ch = alphabet[digit - '0'].charAt(i);
                for (int j = 0; j < res.size(); j++) {
                    temp.add(res.get(j) + ch);
                }
            }
            res.clear();
            res.addAll(temp);
        }

        return res;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.letterCombinations("23")); // [ad, ae, af, bd, be, bf, cd, ce, cf]
        System.out.println(sol.letterCombinations("")); // []
        System.out.println(sol.letterCombinations("2")); // [a, b, c]
        System.out.println(sol.letterCombinations("79")); // 7,9는 문자 4개짜리 → 16가지
    }
}
