import java.util.Arrays;
import java.util.Comparator;

class Solution {
    public int solution(int[][] routes) {
        int answer = 0;
        int last = Integer.MIN_VALUE;
        Arrays.sort(routes, Comparator.comparingInt(a -> a[1]));

        for (int[] r : routes) {
            if (last < r[0]) {
                last = r[1];
                answer++;
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.solution(new int[][]{{-20, -15}, {-14, -5}, {-18, -13}, {-5, -3}})); // 2
    }
}
