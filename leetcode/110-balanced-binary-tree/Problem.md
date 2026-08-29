# Balanced Binary Tree

- 문제 번호: 110
- 링크: https://leetcode.com/problems/balanced-binary-tree/
- 난이도: Easy
- 태그: Tree, Depth-First Search, Binary Tree

## 문제 설명 (요약)

이진 트리의 루트 `root`가 주어질 때, 이 트리가 **높이 균형(height-balanced)** 트리인지 판별한다. 높이 균형 트리란, 트리의 모든 노드에 대해 왼쪽 서브트리와 오른쪽 서브트리의 높이 차이가 1 이하인 트리를 말한다.

## 제약사항

- 트리의 노드 개수: `0 ~ 5000`
- `-10^4 <= Node.val <= 10^4`

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
    public boolean isBalanced(TreeNode root) {
        
    }
}
```

## 입출력 예

**입출력 예 #1**

```
      3
     / \
    9   20
       /  \
      15   7
```

`root = [3,9,20,null,null,15,7]` → `true`

**입출력 예 #2**

```
        1
       / \
      2   2
     / \
    3   3
   / \
  4   4
```

`root = [1,2,2,3,3,null,null,4,4]` → `false` (노드 1의 왼쪽 서브트리 높이는 3, 오른쪽 서브트리 높이는 1이라 차이가 2)

**입출력 예 #3**

`root = []` → `true`
