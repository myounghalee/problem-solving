# K-th Largest Perfect Subtree Size in Binary Tree

- 문제 번호: 3319
- 링크: https://leetcode.com/problems/k-th-largest-perfect-subtree-size-in-binary-tree/
- 난이도: Medium
- 태그: Tree, Depth-First Search, Sorting, Binary Tree

## 문제 설명 (요약)

이진 트리의 루트 `root`와 정수 `k`가 주어질 때, **완전 이진 트리(perfect binary tree)** 형태인 서브트리들 중 `k`번째로 큰 크기를 반환한다. 그런 서브트리가 `k`개 미만이면 `-1`을 반환한다.

여기서 완전 이진 트리란, 모든 리프가 같은 레벨에 있고 모든 부모 노드가 자식을 두 개씩 가지는 트리를 말한다.

## 제약사항

- 트리의 노드 개수: `1 ~ 2000`
- `1 <= Node.val <= 2000`
- `1 <= k <= 1024`

## 함수 시그니처

```java
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
class Solution {
    public int kthLargestPerfectSubtree(TreeNode root, int k) {
        
    }
}
```

## 입출력 예

**입출력 예 #1**

```
          5
        /   \
       3     6
      / \   / \
     5   2 5   7
    / \     / \
   1   8   6   8
```

`root = [5,3,6,5,2,5,7,1,8,null,null,6,8]`, `k = 2` → `3`

완전 이진 서브트리들의 크기를 내림차순으로 나열하면 `[3, 3, 1, 1, 1, 1, 1, 1]`이고, 2번째로 큰 값은 `3`.

**입출력 예 #2**

`root = [1,2,3,4,5,6,7]`, `k = 1` → `7`

완전 이진 서브트리 크기를 내림차순으로 나열하면 `[7, 3, 3, 1, 1, 1, 1]`이고, 가장 큰 값은 `7` (트리 전체가 완전 이진 트리).

**입출력 예 #3**

`root = [1,2,3,null,4]`, `k = 3` → `-1`

완전 이진 서브트리 크기는 `[1, 1]`뿐이라 3번째로 큰 값이 존재하지 않는다.
