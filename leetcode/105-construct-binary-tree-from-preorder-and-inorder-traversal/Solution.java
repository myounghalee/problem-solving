import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
    int idx;

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        idx = 0;
        return build(preorder, inorder, 0, inorder.length - 1);
    }

    private TreeNode build(int[] preorder, int[] inorder, int start, int end) {

        if (start > end) return null;

        TreeNode node = new TreeNode(preorder[idx]);

        for (int i = start; i <= end; i++) {
            if (inorder[i] == node.val) {
                idx++;
                node.left = build(preorder, inorder, start, i - 1);
                node.right = build(preorder, inorder, i + 1, end);
                break;
            }
        }

        return node;
    }


    public static void main(String[] args) {
        Solution solution = new Solution();

        // 예 1: preorder=[3,9,20,15,7], inorder=[9,3,15,20,7]
        //      3
        //     / \
        //    9  20
        //       / \
        //      15  7
        TreeNode expected1 = new TreeNode(3,
                new TreeNode(9),
                new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        check(solution.buildTree(new int[]{3, 9, 20, 15, 7}, new int[]{9, 3, 15, 20, 7}), expected1);

        // 예 2: 노드 하나
        check(solution.buildTree(new int[]{-1}, new int[]{-1}), new TreeNode(-1));

        // 완전히 왼쪽으로만 뻗은 트리: 1 -> 2 -> 3 (전부 왼쪽 자식)
        TreeNode expectedLeftSkewed = new TreeNode(1,
                new TreeNode(2, new TreeNode(3), null),
                null);
        check(solution.buildTree(new int[]{1, 2, 3}, new int[]{3, 2, 1}), expectedLeftSkewed);

        // 완전히 오른쪽으로만 뻗은 트리: 1 -> 2 -> 3 (전부 오른쪽 자식)
        TreeNode expectedRightSkewed = new TreeNode(1,
                null,
                new TreeNode(2, null, new TreeNode(3)));
        check(solution.buildTree(new int[]{1, 2, 3}, new int[]{1, 2, 3}), expectedRightSkewed);

        // 음수 값이 섞인 경우
        TreeNode expectedNeg = new TreeNode(0, new TreeNode(-2), new TreeNode(1));
        check(solution.buildTree(new int[]{0, -2, 1}, new int[]{-2, 0, 1}), expectedNeg);
    }

    // 편의용 헬퍼: 두 트리가 구조와 값 모두 같은지 확인 (100번 Same Tree와 동일한 로직)
    private static boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    private static void check(TreeNode actual, TreeNode expected) {
        boolean same = isSameTree(actual, expected);
        System.out.println((same ? "OK" : "FAIL") + " actual=" + toList(actual) + " expected=" + toList(expected));
    }

    // 디버깅 출력용: 트리를 전위 순회 문자열로 (null 포함, 구조를 알아볼 수 있게)
    private static String toList(TreeNode node) {
        StringBuilder sb = new StringBuilder();
        toListHelper(node, sb);
        return sb.toString();
    }

    private static void toListHelper(TreeNode node, StringBuilder sb) {
        if (node == null) { sb.append("."); return; }
        sb.append(node.val).append("(");
        toListHelper(node.left, sb);
        sb.append(",");
        toListHelper(node.right, sb);
        sb.append(")");
    }
}
