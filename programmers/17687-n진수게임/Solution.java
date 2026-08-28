class Solution {
    public String solution(int n, int t, int m, int p) {
        StringBuilder answer = new StringBuilder();
        int idx = 0, i =0;
        while (answer.length() < t) {
            String num = Integer.toString(i, n);
            for (char c : num.toCharArray()) {
                idx++;
                if (idx % m == p % m) {
                    answer.append(c);
                    if (answer.length() == t) break;
                }
            }
            i++;
        }
        return answer.toString().toUpperCase();
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.solution(2, 4, 2, 1));   // 0111
        System.out.println(sol.solution(16, 16, 2, 1));  // 02468ACE11111111
        System.out.println(sol.solution(16, 16, 2, 2));  // 13579BDF01234567
    }
}
