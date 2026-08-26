import java.util.Arrays;

class Solution {
    public int solution(int[] people, int limit) {
        Arrays.sort(people);

        int left = 0;
        int right = people.length - 1;
        int boats = 0;

        while (left <= right) {
            if (left != right && people[left] + people[right] <= limit) {
                left++;   // 가장 가벼운 사람도 함께 태움
            }
            right--;      // 가장 무거운 사람은 항상 처리 완료 (같이 탔든 혼자 탔든)
            boats++;
        }

        return boats;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.solution(new int[]{70, 50, 80, 50}, 100)); // 3
        System.out.println(sol.solution(new int[]{70, 80, 50}, 100));     // 3
    }
}
