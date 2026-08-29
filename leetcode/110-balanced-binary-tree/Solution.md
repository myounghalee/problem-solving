# [LeetCode 110] Balanced Binary Tree — 재귀 한 번으로 "높이 계산"과 "균형 검사"를 동시에

- 문제 번호: 110
- 링크: https://leetcode.com/problems/balanced-binary-tree/
- 난이도: Easy
- 태그: Tree, Depth-First Search, Binary Tree
- 사용 자료구조/알고리즘: **DFS(post-order) + 높이 계산과 조기 종료(`-1`) 병합**

## 1. 문제 요약

이진 트리의 루트 `root`가 주어질 때, 이 트리가 **높이 균형(height-balanced)** 트리인지 판별한다. 높이 균형 트리란, **트리 안의 모든 노드**에 대해 왼쪽 서브트리와 오른쪽 서브트리의 높이 차이가 1 이하인 트리를 말한다.

```text
예시)     3
         / \
        9   20
           /  \
          15   7

높이 균형 -> true

        1
       / \
      2   2
     / \
    3   3
   / \
  4   4

노드 1 기준 왼쪽 서브트리 높이 3, 오른쪽 서브트리 높이 1 -> 차이 2, 불균형 -> false
```

## 2. 접근 아이디어

핵심은 "**모든** 노드에서 좌우 높이 차이가 1 이하인지"를 확인해야 한다는 점이다. 루트 하나만 확인해서는 안 된다 — 아래쪽 어딘가에서 불균형이 생겨도 루트 레벨에서 우연히 높이 차이가 작아 보일 수 있기 때문이다.

각 노드마다 서브트리 높이를 매번 새로 구하면(재귀 안에서 또 재귀로 높이를 구하는 방식) `O(n)`짜리 높이 계산을 노드마다 반복하게 되어 전체 `O(n²)`이 된다. 대신 **post-order DFS 한 번으로 "높이 계산"과 "균형 검사"를 동시에** 하면 `O(n)`에 끝낼 수 있다.

아이디어는 `height(node)`가 정상적인 경우엔 그 서브트리의 높이(1 이상의 정수)를 반환하고, **서브트리 어딘가에서 이미 불균형이 발견되면 `-1`이라는 특수값을 반환**해서 그 사실을 위쪽 호출로 그대로 전파하는 것이다. `-1`은 "높이"가 아니라 "포기 신호"로 취급해야 하며, 한 번 `-1`이 나오면 그 이후로는 진짜 높이인 것처럼 계산에 섞으면 안 된다(이 부분에서 실수하기 쉽다 — 아래 "겪었던 실수" 참고).

### 손으로 시뮬레이션 — `root = [3,9,20,null,null,15,7]`

post-order이므로 리프에서부터 값이 채워진다.

| 호출 | 왼쪽 높이 | 오른쪽 높이 | 차이 | 결과 |
|---|---|---|---|---|
| `height(9)` | `height(null)=0` | `height(null)=0` | 0 | `1` |
| `height(15)` | 0 | 0 | 0 | `1` |
| `height(7)` | 0 | 0 | 0 | `1` |
| `height(20)` | `height(15)=1` | `height(7)=1` | 0 | `2` |
| `isBalanced(root=3)` | `height(9)=1` | `height(20)=2` | `\|1-2\|=1` | `true` |

모든 단계에서 `-1`이 한 번도 나오지 않았고, 루트에서의 최종 차이도 1 이하이므로 `true`.

## 3. 코드 (Java)

```java
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
    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        int left = height(root.left), right = height(root.right);
        if (left == -1 || right == -1) return false; // 서브트리 내부에서 이미 불균형 발견
        return Math.abs(left - right) <= 1;
    }

    private int height(TreeNode root) {
        if (root == null) return 0;
        int left = height(root.left), right = height(root.right);
        if (left == -1 || right == -1) return -1; // 불균형 신호를 그대로 위로 전파
        if (Math.abs(left - right) > 1) return -1; // 이 노드에서 처음 불균형 발견
        return Math.max(left, right) + 1;
    }
}
```

### 코드 설명

- `height(node)`는 post-order로 동작한다 — 자기 자신의 높이를 계산하기 전에 왼쪽·오른쪽 서브트리의 높이(혹은 `-1`)를 먼저 구한다.
- `left == -1 || right == -1`을 **가장 먼저** 체크해서, 서브트리에서 이미 발견된 불균형을 그대로 위로 흘려보낸다. 이 체크를 생략하면 `-1`이 마치 정상 높이인 것처럼 `Math.abs(left - right)` 계산에 섞여 들어가 잘못된 결과가 나올 수 있다.
- 이 노드 자체에서 `Math.abs(left - right) > 1`이면 그 순간 `-1`을 반환해 상위 호출로 조기 종료 신호를 보낸다.
- `isBalanced`는 루트에 대해서만 `height(root.left)`, `height(root.right)`를 각각 부르고, 둘 중 하나라도 `-1`이면 즉시 `false`, 아니면 루트 레벨 차이만 마지막으로 한 번 더 확인한다. `height` 내부의 재귀가 이미 모든 하위 노드를 검사하므로, 이 마지막 확인까지 통과하면 트리 전체가 균형이다.

## 4. 복잡도 분석

- **시간복잡도**: `O(n)` — 각 노드를 정확히 한 번씩만 방문한다 (`n`은 노드 개수, 최대 5,000). 재귀 안에서 같은 서브트리를 다시 계산하지 않는다.
- **공간복잡도**: `O(h)` — 재귀 호출 스택 깊이만큼 사용한다 (`h`는 트리의 높이, 최악의 경우 편향 트리라면 `O(n)`).

## 5. 엣지 케이스

- **빈 트리(`root = null`)**: `isBalanced`가 바로 `true`를 반환한다. `height(null) = 0`으로 정의돼 있어 리프의 자식을 조회할 때도 자연스럽게 처리된다.
- **노드가 하나뿐인 트리**: 좌우 서브트리 모두 높이 0이라 차이 0, `true`.
- **한쪽으로만 치우친 편향 트리(예: 왼쪽 자식만 계속 이어지는 체인)**: 첫 번째 노드에서부터 좌우 높이 차이가 바로 2 이상이 되어 `-1`이 발생하고 즉시 `false`로 종료된다 — 끝까지 순회하지 않아도 됨.
- **불균형이 트리 깊숙한 곳에만 있고 루트 근처는 멀쩡해 보이는 경우**: `height` 내부에서 `-1`이 발생하는 즉시 위로 전파되므로, 루트 레벨 검사만으로는 놓칠 수 있는 케이스도 정확히 잡아낸다 (아래 "겪었던 실수" 참고).

## 6. 겪었던 실수 (디버깅 기록)

1. **루트에서만 높이 차이를 검사**: 처음 버전은 `Math.abs(height(root.left) - height(root.right)) < 2`만 보고 끝냈다. 이러면 **루트보다 아래쪽 노드**에서 불균형이 생겨도, 루트 레벨의 좌우 높이 차이가 우연히 1 이하면 놓친다. 반례: 왼쪽 서브트리 안의 어떤 노드가 자식 하나만 3단 체인으로 갖고 있어 그 노드 자체는 불균형인데, 트리 전체의 좌우 높이는 우연히 같아지는 경우 — 실제로는 `false`여야 하는데 `true`가 나왔다. → 모든 노드에서 균형을 검사하도록 `height` 함수 안에서 재귀적으로 불균형을 감지하고 전파하게 고쳤다.
2. **`-1`(불균형 신호)을 일반 숫자처럼 빼기**: 위 문제를 고치면서 `height`가 불균형일 때 `-1`을 반환하도록 바꿨지만, `isBalanced`가 여전히 `Math.abs(height(root.left) - height(root.right)) <= 1`처럼 두 값을 그냥 빼기만 했다. 이러면 **양쪽 서브트리가 각각 내부적으로 불균형(`-1`, `-1`)인데 그 차이가 0이라 통과**해버리는 경우가 생긴다 — `-1`이 "불균형" 신호가 아니라 진짜 높이인 것처럼 계산에 섞여버린 것. → `isBalanced`에서 `left == -1 || right == -1`을 **먼저** 체크해서 `-1`을 숫자 연산 대상이 아니라 즉시 `false`로 판단하는 신호로 분리했다.

두 실수 모두 "부분(서브트리)에서 참인 조건이 전체(트리)에서도 참이라고 성급히 가정"한 데서 비롯됐다 — 재귀에서 하위 결과의 특수값(`-1`)을 상위 계산에 안전하게 전파하는 습관이 중요하다.

## 7. 관련 문제

같은 이진 트리(`TreeNode`)를 다루는 문제.

- [Two Sum IV - Input is a BST (LeetCode 653)](../653-two-sum-iv-input-is-a-bst/Solution.md) — 같은 `TreeNode` 구조를 다루지만, 이쪽은 "합이 k인 두 값 찾기"가 목적이라 풀이 기법(BFS+HashSet)은 다르다.
- [K-th Largest Perfect Subtree Size in Binary Tree (LeetCode 3319)](../3319-k-th-largest-perfect-subtree-size-in-binary-tree/Solution.md) — "루트만 보지 말고 모든 노드에서 조건을 검사해야 한다", "실패 신호(`-1`)를 일반 숫자처럼 계산에 섞으면 안 된다"는 똑같은 함정을 겪은 자매 문제.

> 이 섹션은 새 문제를 풀 때마다 갱신됩니다. 트리 높이/DFS 조기 종료 계열 문제를 풀면 이 목록에 서로 링크를 추가해주세요.

---

*이 문서는 [Problem.md](./Problem.md)의 문제 설명을 기반으로 상세 풀이를 정리한 것입니다.*
