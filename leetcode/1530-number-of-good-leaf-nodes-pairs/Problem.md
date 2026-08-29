# Number of Good Leaf Nodes Pairs

- 문제 번호: 1530
- 링크: https://leetcode.com/problems/number-of-good-leaf-nodes-pairs/
- 난이도: Medium
- 태그: Tree, Depth-First Search, Binary Tree, DP on Trees

## 문제 설명 (요약)

이진 트리의 루트 `root`와 정수 `distance`가 주어질 때, 서로 다른 두 리프 노드 사이의 **최단 경로 길이**(간선 수)가 `distance` 이하이면 그 쌍을 "좋은 쌍(good pair)"이라 한다. 트리 안의 좋은 리프 노드 쌍의 개수를 반환한다.

## 제약사항

- 트리의 노드 개수: `1 ~ 2^10`
- `1 <= Node.val <= 100`
- `1 <= distance <= 10`

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
    public int countPairs(TreeNode root, int distance) {
        
    }
}
```

## 입출력 예

**입출력 예 #1**

```
    1
   / \
  2   3
   \
    4
```

`root = [1,2,3,null,4]`, `distance = 3` → `1`

리프 노드는 `3`과 `4`이고, 둘 사이의 최단 경로 길이는 `3`이라 유일한 좋은 쌍이다.

**입출력 예 #2**

```
      1
     / \
    2   3
   / \ / \
  4  5 6  7
```

`root = [1,2,3,4,5,6,7]`, `distance = 3` → `2`

좋은 쌍은 `[4,5]`와 `[6,7]` (최단 경로 `2`). `[4,6]`은 최단 경로가 `4`라 좋은 쌍이 아니다.

**입출력 예 #3**

```
        7
       / \
      1   4
     /   / \
    6   5   3
             \
              2
```

`root = [7,1,4,6,null,5,3,null,null,null,null,null,2]`, `distance = 3` → `1`

유일한 좋은 쌍은 `[2,5]`.
