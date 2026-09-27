import java.util.Arrays;

class Solution {
    public int removeDuplicates(int[] nums) {
        int count = 0;
        int left = 0;
        int num = Integer.MIN_VALUE;
        int numCount = 1;
        for (int right = 0, numsLength = nums.length; right < numsLength; right++) {
            if (num == nums[right]) {
                if (numCount >= 2) continue;
                numCount++;
            } else {
                numCount = 1;
                num = nums[right];
            }

            count++;
            nums[left++] = nums[right];
        }
        return count;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        int[] nums1 = {1, 1, 1, 2, 2, 3};
        int k1 = sol.removeDuplicates(nums1);
        System.out.println(k1 + " " + Arrays.toString(Arrays.copyOf(nums1, k1))); // 5 [1, 1, 2, 2, 3]

        int[] nums2 = {0, 0, 1, 1, 1, 1, 2, 3, 3};
        int k2 = sol.removeDuplicates(nums2);
        System.out.println(k2 + " " + Arrays.toString(Arrays.copyOf(nums2, k2))); // 7 [0, 0, 1, 1, 2, 3, 3]
    }
}
