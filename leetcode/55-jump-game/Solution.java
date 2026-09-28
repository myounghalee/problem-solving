class Solution {
    public boolean canJump(int[] nums) {
        int len = nums.length;
        int index = 0;
        for (int i = 0; i < len; i++) {
            if (index < i) return false;
            index = Math.max(i + nums[i], index);
            if (index >= len - 1) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.canJump(new int[]{2, 3, 1, 1, 4})); // true
        System.out.println(sol.canJump(new int[]{3, 2, 1, 0, 4})); // false
    }
}
