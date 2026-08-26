import java.util.Arrays;

class Solution {
    public int solution(int[] citations) {
        int answer = 0;

        Arrays.sort(citations);
        int[] arr = new int[citations.length];
        for (int i = 0; i < citations.length; i++) {
            arr[i] = citations[citations.length - 1 - i];
        }

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= i + 1) {
                answer++;
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.solution(new int[]{3, 0, 6, 1, 5})); // 3
    }
}
