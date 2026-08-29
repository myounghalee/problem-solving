import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Math.min;

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

    List<String> leafNodes;

    public int countPairs(TreeNode root, int distance) {
        leafNodes = new ArrayList<>();
        dfs(root, "");
        int result = 0;

        for (int i = 0; i < leafNodes.size(); i++) {
            for (int j = i + 1; j < leafNodes.size(); j++) {
                String leaf1 = leafNodes.get(i);
                String leaf2 = leafNodes.get(j);

                int min = Math.min(leaf1.length(), leaf2.length());
                for (int k = 0; k < min; k++) {
                    if (leaf1.charAt(k) != leaf2.charAt(k)) {
                        if (leaf1.length() + leaf2.length() - 2 * k <= distance) result++;
                        break;
                    }
                }
            }
        }

        return result;
    }

    private void dfs(TreeNode node, String s) {
        if (node.left == null && node.right == null) {
            leafNodes.add(s);
            return;
        }

        if (node.left != null) dfs(node.left, s + "L");
        if (node.right != null) dfs(node.right, s + "R");
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        // 예 1: root = [1,2,3,null,4], distance = 3 -> 1
        TreeNode root1 = new TreeNode(1,
                new TreeNode(2, null, new TreeNode(4)),
                new TreeNode(3));
        check(solution.countPairs(root1, 3), 1);

        // 예 2: root = [1,2,3,4,5,6,7], distance = 3 -> 2
        TreeNode root2 = new TreeNode(1,
                new TreeNode(2, new TreeNode(4), new TreeNode(5)),
                new TreeNode(3, new TreeNode(6), new TreeNode(7)));
        check(solution.countPairs(root2, 3), 2);

        // 예 3: root = [7,1,4,6,null,5,3,null,null,null,null,null,2], distance = 3 -> 1
        TreeNode root3 = new TreeNode(7,
                new TreeNode(1, new TreeNode(6), null),
                new TreeNode(4, new TreeNode(5), new TreeNode(3, null, new TreeNode(2))));
        check(solution.countPairs(root3, 3), 1);
    }

    private static void check(int actual, int expected) {
        System.out.println((actual == expected ? "OK" : "FAIL") + " actual=" + actual + " expected=" + expected);
    }
}
