import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Solution {
    public String intToRoman(int num) {

        char[] unit = {'I', 'V', 'X', 'L', 'C', 'D', 'M'};
        List<String> list = new ArrayList<>();

        int u = 0;
        int no = 0;
        while (num > 0) {
            no = num % 10;
            num = num / 10;

            if (no != 0) {
                StringBuilder sb = new StringBuilder();

                int a = no / 5, b = no % 5;
                if (b < 4) {
                    if (a == 1) sb.append(unit[u + 1]);
                    for (int i = 0; i < b; i++) sb.append(unit[u]);
                } else {
                    sb.append(unit[u]).append(unit[u + a + 1]);
                }
                list.add(sb.toString());
            }
            u += 2;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = list.size() - 1; i >= 0; i--) {
            sb.append(list.get(i));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.intToRoman(3749)); // MMMDCCXLIX
        System.out.println(sol.intToRoman(58)); // LVIII
        System.out.println(sol.intToRoman(1994)); // MCMXCIV
    }
}
