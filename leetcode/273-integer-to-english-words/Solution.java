import java.util.ArrayList;
import java.util.List;

class Solution {
    public String numberToWords(int num) {
        if(num == 0) return "Zero";

        String[] one = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
        String[] ten = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
        String[] unit = {"", "Thousand", "Million", "Billion", "trillion", "quadrillion", "quintillion", "sextillion", "septillion"};
        List<String> list = new ArrayList<String>();

        int u = 0;
        while (num > 0) {
            int no = num % 1000;
            num = num / 1000;

            if (no == 0) {
                u++;
                continue;
            }

            StringBuilder sb = new StringBuilder();
            if (no >= 100) {
                sb.append(one[no / 100]).append(" Hundred");
                no = no % 100;
                if (no > 0) sb.append(" ");

            }

            if (no < 20) {
                sb.append(one[no]);
            } else  {
                sb.append(ten[no / 10]);
                if (no % 10 > 0) sb.append(" ").append(one[no % 10]);
            }

            if (!sb.isEmpty() && !unit[u].isEmpty()) sb.append(" ").append(unit[u]);
            u++;
            list.add(sb.toString());
        }

        StringBuilder sb = new StringBuilder();
        for (int i = list.size() - 1; i >= 0 ; i--) {
            sb.append(list.get(i));
            if (i != 0) sb.append(" ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.numberToWords(123)); // One Hundred Twenty Three
        System.out.println(sol.numberToWords(12345)); // Twelve Thousand Three Hundred Forty Five
        System.out.println(sol.numberToWords(1234567)); // One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven
    }
}
