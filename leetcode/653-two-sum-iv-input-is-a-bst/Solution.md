# [LeetCode 653] Two Sum IV - Input is a BST — BFS + HashSet으로 "지금까지 본 값" 추적하기

- 문제 번호: 653
- 링크: https://leetcode.com/problems/two-sum-iv-input-is-a-bst/
- 난이도: Easy
- 태그: Hash Table, Two Pointers, Tree, DFS, BFS, Binary Search Tree, Binary Tree
- 사용 자료구조/알고리즘: **BFS(레벨 순회) + HashSet**

## 1. 문제 요약

이진 탐색 트리(BST)의 루트 `root`와 정수 `k`가 주어질 때, 트리 안의 서로 다른 두 노드 값의 합이 `k`가 되는 경우가 있으면 `true`, 없으면 `false`를 반환한다.

```text
예시)       5
           / \
          3   6
         / \    \
        2   4    7

k=9  → true  (3+6=9, 또는 2+7=9)
k=28 → false (어떤 두 값도 합이 28이 안 됨)
```

[Two Sum(1번)](../1-two-sum/Solution.md), [Two Sum II(167번)](../167-two-sum-ii-input-array-is-sorted/Solution.md)과 같은 "합이 k인 두 원소 찾기" 계열이지만, 입력이 배열이 아니라 **트리**라는 점이 다르다.

## 2. 접근 아이디어

핵심 관찰: **이 접근에서는 트리를 어떤 순서로 순회하든 상관없다.** 배열 버전(1번)의 해시맵 풀이를 그대로 트리에 옮기면 된다 — "지금까지 방문한 값들의 집합"을 유지하면서, 새 노드를 볼 때마다 "그 노드와 짝지어 `k`를 만들 값(`k - node.val`)이 이미 나온 적 있는지"만 확인하면 된다.

- 트리의 모든 노드를 **한 번씩** 방문해야 하므로 순회가 필요하다. DFS(재귀)든 BFS(큐)든 상관없다 — 순서가 결과에 영향을 주지 않기 때문이다. 여기서는 **BFS**(큐를 이용한 레벨 순회)를 택했다.
- `HashSet<Integer>`에 지금까지 방문한 노드 값을 계속 쌓아간다.
- 새 노드를 큐에서 꺼낼 때, **먼저** `k - node.val`이 집합에 있는지 확인하고, **그다음** 지금 노드의 값을 집합에 추가한다. 이 순서(확인 → 추가)가 중요하다 — 만약 순서를 반대로 하면, `k`가 짝수이고 지금 노드의 값이 `k/2`일 때 "자기 자신"을 자신의 짝으로 잘못 인정해버릴 수 있다. 확인을 먼저 하면 그 시점엔 아직 자기 값이 집합에 없으므로 이 문제가 자연스럽게 방지된다.
- (참고: BST는 중위 순회하면 정렬된 값이 나온다는 성질을 이용해, 정렬된 리스트를 만든 뒤 167번의 투 포인터를 재사용하는 방법도 있다. 다만 그 방법은 **중위 순회(DFS)로만** 가능하고 BFS로는 정렬된 순서를 얻을 수 없어서, 이번엔 순서 무관한 HashSet 방식을 골랐다.)

### 손으로 시뮬레이션 — `k = 9`

BFS 순서(레벨 단위로): `5 → 3, 6 → 2, 4, 7`

| 방문 노드 | `k - node.val` | 집합에 있나? | 동작 |
|---|---|---|---|
| 5 | 4 | 없음 | `set`에 5 추가 → `{5}` |
| 3 | 6 | 없음 | `set`에 3 추가 → `{5, 3}` |
| 6 | 3 | **있음!** (`set`에 3이 있음) | `true` 반환 |

`3 + 6 = 9`가 확인된 순간 바로 종료된다 — 정답과 일치한다.

## 3. 코드 (Java)

```java
import java.util.*;

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
    public boolean findTarget(TreeNode root, int k) {
        Set<Integer> set = new HashSet<>(); // 지금까지 방문한 노드 값들

        Deque<TreeNode> q = new ArrayDeque<>(); // BFS용 큐
        q.add(root);

        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            if (set.contains(k - node.val)) return true; // 짝이 이미 나온 적 있으면 즉시 종료
            set.add(node.val);

            if (node.left != null) q.add(node.left);
            if (node.right != null) q.add(node.right);
        }
        return false;
    }
}
```

### 코드 설명

- `set`과 `q`는 `findTarget` 메서드 **안에서** 선언된 지역 변수다. 클래스 필드로 뒀다면 `findTarget`을 여러 번 호출할 때 이전 호출의 상태(방문 기록)가 남아있게 되어 잘못된 결과가 나올 수 있다 — 실제로 이 버그를 겪었다(아래 "겪었던 실수" 참고).
- `q.poll()`로 큐에서 노드를 하나씩 꺼내며, 그 자식들을 다시 큐에 넣는 게 BFS의 표준 패턴이다.
- `if (set.contains(k - node.val)) return true;`를 `set.add(node.val)`보다 **먼저** 실행하는 순서가 "같은 노드를 두 번 쓰면 안 된다"는 제약을 자동으로 만족시킨다.
- `null`인 자식은 큐에 넣지 않아서, 큐 안에는 항상 실제 노드만 존재한다(`poll` 직후 `null` 체크가 필요 없다).

## 4. 복잡도 분석

- **시간복잡도**: `O(n)` — 모든 노드를 정확히 한 번씩 방문한다 (`n`은 노드 개수, 최대 10,000).
- **공간복잡도**: `O(n)` — 최악의 경우(`true`를 못 찾고 끝까지 순회) `set`과 `q`에 모든 노드 값이 쌓일 수 있다.

## 5. 엣지 케이스

- **노드가 하나뿐인 트리**: 짝지을 다른 노드가 없으므로 항상 `false`. `set.contains(k - node.val)`는 그 노드를 확인하는 시점엔 `set`이 비어있으므로 자연스럽게 `false`가 나온다.
- **`k`가 어떤 노드 값의 2배인 경우** (예: `k=8`, 노드 값 `4`가 트리에 하나뿐): "확인 먼저, 추가 나중" 순서 덕분에 자기 자신과는 짝지어지지 않는다.
- **음수 값 포함**: `HashSet`과 뺄셈 연산 모두 부호와 무관하게 정상 동작한다.
- **같은 `Solution` 인스턴스를 여러 번 호출하는 경우**: 상태가 지역 변수라서 호출마다 완전히 독립적으로 동작한다.

## 6. 겪었던 실수 (디버깅 기록)

1. **`Set`과 `boolean` 결과를 클래스 필드로 관리**: 처음엔 `set`과 `answer`를 `findTarget` 밖의 클래스 필드로 선언했다. 그러다 보니 **같은 `Solution` 객체로 `findTarget`을 두 번째 호출할 때, 첫 번째 호출에서 남은 상태(이미 채워진 `set`, `true`로 바뀐 `answer`)가 그대로 남아있어서** 두 번째 호출의 실제 계산과 무관하게 잘못된 `true`를 반환했다 (`k=9` 호출 후 `k=28` 호출이 `false` 대신 `true`를 반환). → `set`과 결과 상태를 전부 메서드 안의 지역 변수로 옮기고, 재귀 대신 BFS로 재구성하면서 이 문제가 자연스럽게 해결됐다.
   - 이 버그는 LeetCode 채점기에서는 테스트 케이스마다 보통 새 `Solution` 객체를 만들기 때문에 드러나지 않을 수도 있지만, "메서드는 호출마다 독립적으로 동작해야 한다"는 원칙을 어긴 것이라 다른 환경(우리 `main()`처럼 같은 인스턴스를 재사용하는 경우)에서는 바로 문제가 된다.

## 7. 관련 문제

같은 "합이 k인 두 원소 찾기" 계열, 입력 자료구조만 다른 문제들.

- [Two Sum (LeetCode 1)](../1-two-sum/Solution.md) — 정렬 안 된 배열, 해시맵으로 `O(n)` 시간/공간.
- [Two Sum II - Input Array Is Sorted (LeetCode 167)](../167-two-sum-ii-input-array-is-sorted/Solution.md) — 정렬된 배열, 투 포인터로 `O(1)` 공간. 이 문제도 "BST를 중위 순회해서 정렬된 리스트를 얻은 뒤" 이 투 포인터 로직을 재사용하는 방식으로 풀 수도 있다.

> 이 섹션은 새 문제를 풀 때마다 갱신됩니다. 트리 순회, 해시셋 기반 탐색 계열 문제를 풀면 이 목록에 서로 링크를 추가해주세요.

---

*이 문서는 [Problem.md](./Problem.md)의 문제 설명을 기반으로 상세 풀이를 정리한 것입니다.*
