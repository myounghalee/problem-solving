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
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        // 예 1: p = [1,2,3], q = [1,2,3] -> true
        check(solution.isSameTree(
                new TreeNode(1, new TreeNode(2), new TreeNode(3)),
                new TreeNode(1, new TreeNode(2), new TreeNode(3))), true);

        // 예 2: p = [1,2], q = [1,null,2] -> false (2의 위치가 다름)
        check(solution.isSameTree(
                new TreeNode(1, new TreeNode(2), null),
                new TreeNode(1, null, new TreeNode(2))), false);

        // 예 3: p = [1,2,1], q = [1,1,2] -> false (값 배치가 다름)
        check(solution.isSameTree(
                new TreeNode(1, new TreeNode(2), new TreeNode(1)),
                new TreeNode(1, new TreeNode(1), new TreeNode(2))), false);

        // 둘 다 빈 트리
        check(solution.isSameTree(null, null), true);

        // 한쪽만 비어있음
        check(solution.isSameTree(new TreeNode(1), null), false);

        // 리프 직렬화 생략으로 예전엔 true가 되던 반례 — 실제로는 다른 트리
        //      1                1
        //     / \              / \
        //    2   3            2   3
        //       /            /
        //      4            4
        check(solution.isSameTree(
                new TreeNode(1, new TreeNode(2), new TreeNode(3, new TreeNode(4), null)),
                new TreeNode(1, new TreeNode(2, new TreeNode(4), null), new TreeNode(3))), false);
    }

    private static void check(boolean actual, boolean expected) {
        System.out.println((actual == expected ? "OK" : "FAIL") + " actual=" + actual + " expected=" + expected);
    }
}
