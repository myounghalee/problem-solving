# Same Tree

- 문제 번호: 100
- 링크: https://leetcode.com/problems/same-tree/
- 난이도: Easy
- 태그: Tree, DFS, BFS, Binary Tree

## 문제 설명 (요약)

두 이진 트리의 루트 `p`, `q`가 주어질 때, 두 트리가 **구조적으로 동일하고 노드 값도 전부 같은지** 판정한다.
[Solution.java](Solution.java)
## 제약사항

- 두 트리의 노드 개수는 각각 `0`~`100`.
- `-10^4 <= Node.val <= 10^4`

## 함수 시그니처

```java
class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        
    }
}
```

## 입출력 예

| p | q | 결과 |
|---|---|---|
| [1,2,3] | [1,2,3] | true |
| [1,2] | [1,null,2] | false |
| [1,2,1] | [1,1,2] | false |

### 입출력 예 설명

**입출력 예 #1**: 구조와 값이 완전히 같다.

**입출력 예 #2**: 노드 개수는 같지만(둘 다 `1`, `2`), `2`가 붙은 위치가 다르다 — `p`는 왼쪽 자식, `q`는 오른쪽 자식.

**입출력 예 #3**: 값의 배치가 다르다 — 구조는 같아도 각 위치의 값이 다르면 다른 트리다.
