import java.util.Arrays;

class Solution {
    public int[] twoSum(int[] numbers, int target) {

        int l = 0, r = numbers.length - 1;
        while (l < r) {
            if (numbers[l] + numbers[r] == target) return new int[]{l + 1, r + 1};
            if (numbers[l] + numbers[r] < target) l++;
            else r--;
        }

        return new int[]{};
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(Arrays.toString(sol.twoSum(new int[]{2, 7, 11, 15}, 9))); // [1, 2]
        System.out.println(Arrays.toString(sol.twoSum(new int[]{2, 3, 4}, 6))); // [1, 3]
        System.out.println(Arrays.toString(sol.twoSum(new int[]{-1, 0}, -1))); // [1, 2]
    }
}
