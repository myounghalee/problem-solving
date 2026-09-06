class Solution {
    public int minSubArrayLen(int target, int[] nums) {

        int left = 0;
        int minLength = Integer.MAX_VALUE;
        int sum = 0;
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            while (sum >= target) {
                minLength = Math.min(minLength, right - left + 1);
                sum -= nums[left++];
            }
        }

        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3})); // 2
        System.out.println(sol.minSubArrayLen(4, new int[]{1, 4, 4})); // 1
        System.out.println(sol.minSubArrayLen(11, new int[]{1, 1, 1, 1, 1, 1, 1, 1})); // 0

        // 원소 하나가 정확히 target과 같은 경우
        System.out.println(sol.minSubArrayLen(5, new int[]{5})); // 1

        // 배열 전체를 다 더해야만 되는 경우
        System.out.println(sol.minSubArrayLen(15, new int[]{1, 2, 3, 4, 5})); // 5

        // 값이 큰 원소 하나가 뒤쪽에 있어, 앞부분을 지나쳐야 짧은 답이 나오는 경우
        System.out.println(sol.minSubArrayLen(100, new int[]{1, 1, 1, 1, 100, 1, 1})); // 1
    }
}
