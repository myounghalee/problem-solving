class Solution {
    public int maxProfit(int[] prices) {
        int min = prices[0];
        int ans = 0;
        int tmp = 0;
        for (int i = 1; i < prices.length; i++) {
            tmp = Math.max(tmp, prices[i] - min);;
            min = Math.min(min, prices[i]);

            if (i < prices.length - 1) {
                if (prices[i] > prices[i + 1]) {
                    ans += tmp;
                    min = prices[i];
                    tmp = 0;
                }
            } else  {
                ans += tmp;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.maxProfit(new int[]{7, 1, 5, 3, 6, 4})); // 7
        System.out.println(sol.maxProfit(new int[]{1, 2, 3, 4, 5}));    // 4
        System.out.println(sol.maxProfit(new int[]{7, 6, 4, 3, 1}));    // 0
    }
}
