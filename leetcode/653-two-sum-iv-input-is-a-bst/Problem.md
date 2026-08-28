# Two Sum IV - Input is a BST

- 문제 번호: 653
- 링크: https://leetcode.com/problems/two-sum-iv-input-is-a-bst/
- 난이도: Easy
- 태그: Hash Table, Two Pointers, Tree, DFS, BFS, Binary Search Tree, Binary Tree

## 문제 설명 (요약)

이진 탐색 트리(BST)의 루트 `root`와 정수 `k`가 주어질 때, 트리 안에 있는 두 원소의 합이 `k`가 되는 경우가 존재하면 `true`, 아니면 `false`를 반환한다.

[1번(Two Sum)](../1-two-sum/Problem.md), [167번(Two Sum II)](../167-two-sum-ii-input-array-is-sorted/Problem.md)과 같은 "합이 k인 두 원소 찾기" 계열이지만, 이번엔 입력이 배열이 아니라 **BST**라는 점이 다르다. BST는 중위 순회(in-order traversal)하면 정렬된 순서로 값을 뽑아낼 수 있다는 성질이 있다.

## 제약사항

- 트리의 노드 개수: `1 ~ 10^4`
- `-10^4 <= Node.val <= 10^4`
- `root`는 항상 유효한 BST임이 보장된다.
- `-10^5 <= k <= 10^5`

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
    public boolean findTarget(TreeNode root, int k) {
        
    }
}
```

## 입출력 예

**입출력 예 #1**

```
      5
     / \
    3   6
   / \    \
  2   4    7
```

`root = [5,3,6,2,4,null,7]`, `k = 9` → `true` (3 + 6 = 9, 혹은 2 + 7 = 9)

**입출력 예 #2**

같은 트리, `k = 28` → `false` (트리 안의 어떤 두 값도 합이 28이 되지 않는다)
