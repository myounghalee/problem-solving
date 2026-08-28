import java.util.Arrays;

class Solution {
    public int solution(int[] A, int[] B) {
        int answer = 0;

        Arrays.sort(A);
        Arrays.sort(B);

        int i = 0;
        for (int a : A) {
            while (i < B.length) {
                int b = B[i];
                i++;
                if (a < b) {
                    answer++;
                    break;
                }
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.solution(new int[]{5, 1, 3, 7}, new int[]{2, 2, 6, 8})); // 3
        System.out.println(sol.solution(new int[]{2, 2, 2, 2}, new int[]{1, 1, 1, 1})); // 0
    }
}
