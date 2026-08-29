import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    List<Integer> list;

    public int kthLargestPerfectSubtree(TreeNode root, int k) {
        list = new ArrayList<>();
        dfs(root);
        list.sort(Comparator.reverseOrder());
        if (list.size() < k) return -1;
        return list.get(k - 1);
    }

    private int dfs(TreeNode root) {
        if (root == null) return -1;

        if (root.left == null && root.right == null) {
            list.add(1);
            return 1;
        }

        int left = dfs(root.left), right = dfs(root.right);
        if (left == -1 || right == -1) return -1;
        if (left == right) {
            int val = left + right + 1;
            list.add(val);
            return val;
        }

        return -1;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        // 예 1: root = [5,3,6,5,2,5,7,1,8,null,null,6,8], k = 2 -> 3
        TreeNode root1 = new TreeNode(5,
                new TreeNode(3,
                        new TreeNode(5, new TreeNode(1), new TreeNode(8)),
                        new TreeNode(2)),
                new TreeNode(6,
                        new TreeNode(5, new TreeNode(6), new TreeNode(8)),
                        new TreeNode(7)));
        check(solution.kthLargestPerfectSubtree(root1, 2), 3);

        // 예 2: root = [1,2,3,4,5,6,7], k = 1 -> 7
        TreeNode root2 = new TreeNode(1,
                new TreeNode(2, new TreeNode(4), new TreeNode(5)),
                new TreeNode(3, new TreeNode(6), new TreeNode(7)));
        check(solution.kthLargestPerfectSubtree(root2, 1), 7);

        // 예 3: root = [1,2,3,null,4], k = 3 -> -1
        TreeNode root3 = new TreeNode(1,
                new TreeNode(2, null, new TreeNode(4)),
                new TreeNode(3));
        check(solution.kthLargestPerfectSubtree(root3, 3), -1);
    }

    private static void check(int actual, int expected) {
        System.out.println((actual == expected ? "OK" : "FAIL") + " actual=" + actual + " expected=" + expected);
    }
}
