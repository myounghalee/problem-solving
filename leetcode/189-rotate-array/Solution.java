import java.util.Arrays;

class Solution {
    public void rotate(int[] nums, int k) {
        for (int i = 0; i < k; i++) {
            int num = nums[0];
            for (int j = 1; j < nums.length; j++) {
                int next = nums[j];
                nums[j] = num;
                num = next;
            }
            nums[0] = num;
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        int[] nums1 = {1, 2, 3, 4, 5, 6, 7};
        sol.rotate(nums1, 3);
        System.out.println(Arrays.toString(nums1)); // [5, 6, 7, 1, 2, 3, 4]

        int[] nums2 = {-1, -100, 3, 99};
        sol.rotate(nums2, 2);
        System.out.println(Arrays.toString(nums2)); // [3, 99, -1, -100]
    }
}
