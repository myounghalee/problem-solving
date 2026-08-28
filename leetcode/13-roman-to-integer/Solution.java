import java.util.HashMap;
import java.util.Map;

class Solution {
    public int romanToInt(String s) {
        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);

        char[] arr = s.toCharArray();
        int res = 0;
        int temp = 0;
        for (int i = arr.length - 1; i >= 0; i--) {
            int no = map.get(arr[i]);
            if (temp > no) no *= -1;
            temp = map.get(arr[i]);
            res += no;
        }
        return res;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
//        System.out.println(sol.romanToInt("III")); // 3
//        System.out.println(sol.romanToInt("LVIII")); // 58
        System.out.println(sol.romanToInt("MCMXCIV")); // 1994
    }
}
