class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {
    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        int left = height(root.left), right = height(root.right);
        if (left == -1 || right == -1) return false;
        return Math.abs(left - right) <= 1;
    }

    private int height(TreeNode root) {
        if (root == null) return 0;
        int left = height(root.left), right = height(root.right);
        if (left == -1 || right == -1) return -1;
        if (Math.abs(left - right) > 1) return -1;
        return Math.max(left, right) + 1;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        // 예 1: root = [3,9,20,null,null,15,7] -> true
        TreeNode root1 = new TreeNode(3,
                new TreeNode(9),
                new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        check(solution.isBalanced(root1), true);

        // 예 2: root = [1,2,2,3,3,null,null,4,4] -> false
        TreeNode root2 = new TreeNode(1,
                new TreeNode(2,
                        new TreeNode(3, new TreeNode(4), new TreeNode(4)),
                        new TreeNode(3)),
                new TreeNode(2));
        check(solution.isBalanced(root2), false);

        // 예 3: root = [] -> true
        check(solution.isBalanced(null), true);
    }

    private static void check(boolean actual, boolean expected) {
        System.out.println((actual == expected ? "OK" : "FAIL") + " actual=" + actual + " expected=" + expected);
    }
}
