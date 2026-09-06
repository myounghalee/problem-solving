class Solution {
    public boolean isPalindrome(String s) {

        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c >= 'a' && c <= 'z') sb.append(c);
            else if (c >= 'A' && c <= 'Z') sb.append((char) (c + ('a' - 'A'))); // 로케일과 무관하게 직접 소문자로
            else if (c >= '0' && c <= '9') sb.append(c);
        }

        int left = 0;
        int right = sb.length() - 1;
        while (left < right) {
            if (sb.charAt(left++) != sb.charAt(right--)) return false;
        }

        return true;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.isPalindrome("0"));
        System.out.println(sol.isPalindrome("A man, a plan, a canal: Panama")); // true
        System.out.println(sol.isPalindrome("race a car")); // false
        System.out.println(sol.isPalindrome(" ")); // true

        // 숫자가 섞인 경우
        System.out.println(sol.isPalindrome("0P")); // false ('0'과 'p'는 다름)

        // 알파벳/숫자만 있고 대소문자가 섞인 짧은 팰린드롬
        System.out.println(sol.isPalindrome("Aa")); // true

        // 문장부호로만 이루어진 경우 (전부 걸러지면 빈 문자열 -> 팰린드롬)
        System.out.println(sol.isPalindrome(".,")); // true

        // 로케일에 의존하던 시절 터키어 로케일에서 깨졌던 케이스
        System.out.println(sol.isPalindrome("Ix")); // false
    }
}
