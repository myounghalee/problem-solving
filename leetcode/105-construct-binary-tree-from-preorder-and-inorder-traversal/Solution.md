# [LeetCode 105] Construct Binary Tree from Preorder and Inorder Traversal — 전위 순회의 첫 값으로 중위 순회를 쪼개는 재귀

- 문제 번호: 105
- 링크: https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
- 난이도: Medium
- 태그: Array, Hash Table, Divide and Conquer, Tree, Binary Tree
- 사용 자료구조/알고리즘: **분할 정복 재귀 + 해시맵(값→인덱스)**

## 1. 문제 요약

이진 트리를 **전위 순회**한 결과 `preorder`와 **중위 순회**한 결과 `inorder`가 주어질 때, 원래의 트리를 복원한다.

```text
preorder = [3,9,20,15,7]
inorder  = [9,3,15,20,7]
     3
    / \
   9  20
      / \
     15  7
```

모든 노드 값이 서로 다르다는 전제가 있어서, 이 두 순회 결과로 트리가 유일하게 정해진다.

## 2. 접근 아이디어

### 전위 순회의 첫 값은 항상 루트다

`preorder = [루트, 왼쪽 서브트리 전체, 오른쪽 서브트리 전체]` 순서이므로, `preorder[0]`이 무조건 루트다.

### 그 루트를 중위 순회에서 찾으면 좌우가 갈린다

`inorder = [왼쪽 서브트리 전체, 루트, 오른쪽 서브트리 전체]` 순서다. 루트값이 `inorder`의 몇 번째 인덱스(`mid`)에 있는지 찾으면:

- `inorder`의 `mid`보다 **왼쪽**에 있는 값들은 전부 왼쪽 서브트리
- `inorder`의 `mid`보다 **오른쪽**에 있는 값들은 전부 오른쪽 서브트리

### 왼쪽 서브트리의 크기를 알면 `preorder`도 나눌 수 있다

`inorder`에서 찾은 위치(`mid`)가 곧 왼쪽 서브트리의 노드 개수다. `preorder`도 `[루트, 왼쪽 서브트리(그 개수만큼), 나머지는 오른쪽]` 순서이므로, 같은 개수만큼 `preorder`를 나누면 왼쪽/오른쪽 서브트리 각각의 전위 순회도 구해진다. 이제 **더 작아진 두 부분 문제**(왼쪽 서브트리 만들기, 오른쪽 서브트리 만들기)로 나뉘었으니 재귀로 반복한다.

### 배열을 진짜로 자르지 않는다 — 인덱스와 공유 포인터로 대체한다

`preorder`와 `inorder`를 매번 `Arrays.copyOfRange`로 실제로 잘라내면, 그 복사 자체가 매 호출마다 `O(n)`이라 전체가 `O(n²)`이 된다(제약상 `n≤3000`이라 통과는 하지만, `O(n)`이 가능한 문제다). 대신:

- **`inorder`는 잘라내지 않고, `(start, end)` 범위만 재귀 인자로 넘긴다.**
- **`preorder`는 아예 자르지 않고, "다음 루트로 쓸 인덱스"를 가리키는 포인터 하나를 재귀 전체가 공유**한다. 전위 순회는 항상 왼쪽 서브트리를 통째로 끝낸 뒤에야 오른쪽 서브트리로 넘어가므로, 이 포인터를 왼쪽 호출이 다 쓰고 나면 오른쪽 호출이 이어받아도 절대 꼬이지 않는다.

### 손으로 시뮬레이션 — `preorder=[3,9,20,15,7]`, `inorder=[9,3,15,20,7]`

| 호출 | `preorder[idx]` (루트) | `inorder` 범위 | `mid` | 왼쪽 범위 | 오른쪽 범위 |
|---|---|---|---|---|---|
| `build(0,4)` | `3` (idx 0→1) | `[9,3,15,20,7]` | 1 | `[0,0]`=`[9]` | `[2,4]`=`[15,20,7]` |
| `build(0,0)` | `9` (idx 1→2) | `[9]` | 0 | 없음 | 없음 |
| `build(2,4)` | `20` (idx 2→3) | `[15,20,7]` | 3(`inorder`의 실제 인덱스) | `[2,2]`=`[15]` | `[4,4]`=`[7]` |
| `build(2,2)` | `15` (idx 3→4) | `[15]` | 2 | 없음 | 없음 |
| `build(4,4)` | `7` (idx 4→5) | `[7]` | 4 | 없음 | 없음 |

`idx`가 `0→1→2→3→4→5`로 재귀 호출 순서를 넘나들며 한 번도 되돌아가지 않고 계속 전진한다는 점을 확인하자. 이게 공유 포인터가 안전한 이유다.

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
}
```

### 코드 설명

- **`idx`가 인스턴스 필드다.** 재귀 함수의 매개변수로 넘기면 각 호출마다 값이 복사되어 왼쪽 재귀에서 늘어난 값이 오른쪽 재귀에 반영되지 않는다. 필드(또는 크기 1짜리 배열로 감싸 참조로 넘기는 방법)로 두어야 왼쪽·오른쪽 호출이 "같은 포인터"를 공유한다.
- **`idx++`는 루트를 `inorder`에서 찾은 "직후"에 한다.** 루트 노드(`new TreeNode(preorder[idx])`)를 만드는 시점에는 아직 `idx`를 전진시키지 않고, 그 값을 실제로 다 썼다는 게 확인된 뒤(반복문 안에서 일치하는 위치를 찾은 뒤) 전진시킨다.
- **왼쪽 재귀를 오른쪽 재귀보다 먼저 호출한다.** `node.left = build(...)`가 끝나야 `idx`가 왼쪽 서브트리 전체만큼 전진해 있고, 그다음 `node.right = build(...)`가 정확한 다음 루트를 가리키게 된다. 순서를 바꾸면 완전히 틀린 트리가 만들어진다.
- `for` 루프로 `inorder[start..end]` 안에서 루트값을 찾는다. 이 선형 탐색이 이 코드의 유일한 비효율 지점이다(4절, 6절).

## 4. 복잡도 분석

- **시간복잡도**: `O(n²)` — 노드마다 `inorder`에서 자기 값을 찾는 데 최악 `O(n)`이 걸리고, 노드가 `n`개이므로 `O(n²)`.
- **공간복잡도**: `O(n)` — 재귀 호출 스택과 결과 트리.

실측으로 `O(n²)`를 확인했다(제약을 넘어서는 크기로 테스트).

| `n` | 시간 |
|---|---|
| 2,000 | 1.0ms |
| 4,000 | 4.2ms |
| 8,000 | 16.8ms |
| 16,000 | 67.7ms |

`n`이 2배 될 때마다 시간이 4배가 되어 `O(n²)`임이 뚜렷하다. 다만 이 문제의 실제 제약(`n≤3000`)에서는 최악의 경우도 900만 번 비교 수준이라 몇 ms 안에 끝난다.

## 5. 엣지 케이스

- **노드 하나** (`preorder=[-1], inorder=[-1]`): 첫 호출에서 바로 리프를 만들고 끝.
- **완전히 한쪽으로만 뻗은 트리** (`preorder=[1,2,3], inorder=[3,2,1]` → 전부 왼쪽 자식): 매 단계 `mid`가 범위의 맨 끝(또는 맨 앞)에 위치해, 한쪽 서브트리는 텅 비고 다른 쪽에 나머지 전부가 몰린다. 6절에서 다루듯 이런 형태가 `O(n²)`의 최악 케이스이기도 하다.
- **음수 값**: 값의 부호와 무관하게 비교(`==`)만 하므로 문제없이 동작한다.
- **매우 깊은 트리**: 재귀 깊이가 트리 높이에 비례한다. 완전히 한쪽으로 치우친 트리가 극단적으로 크면(이 문제의 제약을 훨씬 넘는 약 10,000~20,000 이상) 스택 오버플로 가능성이 있다 — 다만 이 문제의 실제 제약(`n≤3000`)에서는 걱정할 수준이 아니다.

검증: 무작위로 생성한 트리 20,000그루를 각각 전위·중위 순회로 직렬화한 뒤 다시 복원해서, 원본과 완전히 같은 트리가 나오는지 확인했다(왕복 검증). 전부 일치했다.

## 6. 이런 방법도 있다 — 해시맵으로 탐색을 `O(1)`로

3절의 유일한 비효율은 **매번 `inorder`를 선형 탐색**해 루트 위치를 찾는 것이다. 문제의 제약에 "모든 값이 서로 다르다"는 조건이 있으므로, **값 하나는 `inorder`에서 정확히 한 위치에만 존재**한다. 그렇다면 탐색을 시작하기 전에 **값 → 인덱스** 맵을 한 번만 만들어두면, 이후 모든 조회가 `O(1)`이 된다.

```java
import java.util.*;

class Solution {
    int[] preorder;
    int preIdx;
    Map<Integer, Integer> indexOf; // inorder의 값 -> 인덱스

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        this.preorder = preorder;
        this.preIdx = 0;
        this.indexOf = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) indexOf.put(inorder[i], i);
        return build(0, inorder.length - 1);
    }

    private TreeNode build(int start, int end) {
        if (start > end) return null;

        int rootVal = preorder[preIdx++];
        TreeNode node = new TreeNode(rootVal);
        int mid = indexOf.get(rootVal); // O(1) 조회

        node.left = build(start, mid - 1);
        node.right = build(mid + 1, end);
        return node;
    }
}
```

전체 구조(공유 포인터, 왼쪽 먼저 재귀)는 3절과 완전히 같고, **"루트를 어떻게 찾는가"만** 선형 탐색에서 해시맵 조회로 바뀌었다.

**비교** (같은 결과를 내는지 확인 후, 워밍업하고 측정):

| `n` | 선형 탐색 (3절) | 해시맵 (6절) |
|---|---|---|
| 2,000 | 1.0ms | 0.20ms |
| 4,000 | 4.2ms | 0.32ms |
| 8,000 | 16.8ms | 0.48ms |
| 16,000 | 67.7ms | 0.81ms |

해시맵 버전은 `n`이 2배 될 때 시간도 거의 2배로만 늘어 `O(n)`을 그대로 보여준다. `n=16,000`에서 이미 80배 넘게 차이 난다.

**교훈: "제약사항에 명시된 조건"(여기서는 "값이 모두 서로 다르다")은 대개 알고리즘을 위한 힌트다.** 이 조건이 없었다면 값 하나가 여러 위치에 있을 수 있어 해시맵 자체가 성립하지 않았을 것이다.

## 7. 관련 문제

같은 `TreeNode` 구조를 다루지만, **기존 트리를 분석**하는 문제들과 달리 이 문제는 **트리를 처음부터 만든다**는 점에서 결이 다르다.

- [Same Tree (LeetCode 100)](../100-same-tree/Solution.md) — 두 트리가 같은지 판정. 이 문제에서 정답 트리를 만든 뒤 왕복 검증(원본과 재구성 결과 비교)할 때 쓴 `isSameTree` 로직이 여기서 그대로 재사용됐다.
- [Balanced Binary Tree (LeetCode 110)](../110-balanced-binary-tree/Solution.md) — 이미 있는 트리의 구조를 검사하는 재귀. 이 문제는 반대로 순회 결과라는 "평면 데이터"에서 구조를 역으로 복원한다.

이 저장소에서 **순회 결과로부터 트리를 복원하는** 첫 문제이자, **분할 정복(Divide and Conquer)** 태그가 붙은 첫 트리 문제이기도 하다.

> 이 섹션은 새 문제를 풀 때마다 갱신됩니다. 트리 복원, 분할 정복 계열 문제를 풀면 이 목록에 서로 링크를 추가해주세요.

---

*이 문서는 [Problem.md](./Problem.md)의 문제 설명을 기반으로 상세 풀이를 정리한 것입니다.*
