class Solution {
    public int maxArea(int[] height) {
        int max = 0;
        int left = 0, right = height.length - 1;

        while (left < right) {
            max = Math.max(max, (right - left) * Math.min(height[left], height[right]));
            if (height[left] < height[right]) left++;
            else right--;
        }

        return max;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7})); // 49
        System.out.println(sol.maxArea(new int[]{1, 1})); // 1

        // 가장 높은 두 선이 양 끝에 있지 않은 경우
        System.out.println(sol.maxArea(new int[]{1, 2, 1})); // 2

        // 오름차순 — 가장 넓은 폭(양 끝)을 쓰는 게 항상 최선은 아님을 확인
        System.out.println(sol.maxArea(new int[]{1, 2, 3, 4, 5})); // 6 (인덱스 1과 4: min(2,5)*3=6)

        // 모두 같은 높이면 가장 넓은 폭이 곧 최대
        System.out.println(sol.maxArea(new int[]{4, 4, 4, 4, 4})); // 16
    }
}
