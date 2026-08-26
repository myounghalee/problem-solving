class Solution {
    public String solution(String p) {
        if (p.isEmpty()) return "";

        int splitIndex = split(p);
        String u = p.substring(0, splitIndex);
        String v = p.substring(splitIndex);

        if (isCorrect(u)) {
            return u + solution(v);
        }

        StringBuilder result = new StringBuilder();
        result.append('(');
        result.append(solution(v));
        result.append(')');

        for (int i = 1; i < u.length() - 1; i++) {
            result.append(u.charAt(i) == '(' ? ')' : '(');
        }

        return result.toString();
    }

    private int split(String p) {
        int balance = 0;
        for (int i = 0; i < p.length(); i++) {
            balance += p.charAt(i) == '(' ? 1 : -1;
            if (balance == 0) return i + 1;
        }
        return p.length();
    }

    private boolean isCorrect(String u) {
        int balance = 0;
        for (int i = 0; i < u.length(); i++) {
            balance += u.charAt(i) == '(' ? 1 : -1;
            if (balance < 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.solution("(()())()")); // (()())()
        System.out.println(sol.solution(")("));        // ()
        System.out.println(sol.solution("()))((()"));   // ()(())()
    }
}
