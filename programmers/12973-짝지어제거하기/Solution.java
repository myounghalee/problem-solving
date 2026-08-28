class Solution {
    public int solution(String s) {
        char[] stack = new char[s.length()];
        int top = -1;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (top >= 0 && stack[top] == c) {
                top--;
            } else {
                stack[++top] = c;
            }
        }

        return top == -1 ? 1 : 0;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.solution("baabaa")); // 1
        System.out.println(sol.solution("cdcd"));    // 0
    }
}
