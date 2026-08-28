import java.util.*;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
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

    public boolean findTarget(TreeNode root, int k) {
        Set<Integer> set = new HashSet<>();

        Deque<TreeNode> q = new ArrayDeque<>();
        q.add(root);

        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            if (set.contains(k - node.val)) return true;
            set.add(node.val);

            if (node.left != null)  q.add(node.left);
            if (node.right != null)  q.add(node.right);
        }
        return false;
    }

    public static void main(String[] args) {
        // root = [5,3,6,2,4,null,7]
        TreeNode root = new TreeNode(5,
                new TreeNode(3, new TreeNode(2), new TreeNode(4)),
                new TreeNode(6, null, new TreeNode(7)));

        Solution sol = new Solution();
        System.out.println(sol.findTarget(root, 9)); // true
        System.out.println(sol.findTarget(root, 28)); // false
    }
}
