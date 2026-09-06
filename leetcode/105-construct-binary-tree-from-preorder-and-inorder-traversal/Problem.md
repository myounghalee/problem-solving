# Construct Binary Tree from Preorder and Inorder Traversal

- 문제 번호: 105
- 링크: https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
- 난이도: Medium
- 태그: Array, Hash Table, Divide and Conquer, Tree, Binary Tree

## 문제 설명 (요약)

어떤 이진 트리를 **전위 순회(preorder)**한 결과 `preorder`와 **중위 순회(inorder)**한 결과 `inorder`가 주어질 때, 그 이진 트리를 복원해서 루트 노드를 반환한다.

- 전위 순회: `루트 → 왼쪽 서브트리 → 오른쪽 서브트리` 순서로 방문.
- 중위 순회: `왼쪽 서브트리 → 루트 → 오른쪽 서브트리` 순서로 방문.

두 순회 결과만으로 원래 트리 하나가 유일하게 정해진다(단, 모든 노드의 값이 서로 다르다는 전제하에).

## 제약사항

- `1 <= preorder.length <= 3000`
- `inorder.length == preorder.length`
- `-3000 <= preorder[i], inorder[i] <= 3000`
- `preorder`와 `inorder`의 값은 각각 **모두 서로 다르다**(중복 없음).
- `inorder`의 모든 값은 `preorder`에도 나타난다.
- `preorder`는 실제로 어떤 이진 트리의 전위 순회 결과이고, `inorder`는 같은 트리의 중위 순회 결과임이 보장된다.

## 함수 시그니처

```java
class Solution {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        
    }
}
```

## 입출력 예

| preorder | inorder | 결과 |
|---|---|---|
| [3,9,20,15,7] | [9,3,15,20,7] | [3,9,20,null,null,15,7] |
| [-1] | [-1] | [-1] |

### 입출력 예 설명

**입출력 예 #1**:

```text
     3
    / \
   9  20
      / \
     15  7
```

전위 순회하면 `3,9,20,15,7`, 중위 순회하면 `9,3,15,20,7`이 나와 주어진 입력과 일치한다.

**입출력 예 #2**: 노드가 하나뿐이면 두 순회 결과가 같다.
