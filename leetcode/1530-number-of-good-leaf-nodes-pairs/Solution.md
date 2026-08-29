# [LeetCode 1530] Number of Good Leaf Nodes Pairs — 리프까지의 경로를 문자열로 인코딩해서 LCA 깊이 구하기

- 문제 번호: 1530
- 링크: https://leetcode.com/problems/number-of-good-leaf-nodes-pairs/
- 난이도: Medium
- 태그: Tree, Depth-First Search, Binary Tree, DP on Trees
- 사용 자료구조/알고리즘: **DFS로 경로 문자열 인코딩 + 문자열 비교로 LCA 깊이 계산**

## 1. 문제 요약

이진 트리의 루트 `root`와 정수 `distance`가 주어질 때, 서로 다른 두 리프 노드 사이의 최단 경로 길이(간선 수)가 `distance` 이하이면 "좋은 쌍(good pair)"이라 한다. 트리 안의 좋은 리프 노드 쌍의 개수를 반환한다.

```text
예시)   1
       / \
      2   3
       \
        4

리프: 3, 4
3과 4 사이 최단 경로: 4 -> 2 -> 1 -> 3, 길이 3
distance = 3 이면 좋은 쌍 1개
```

## 2. 접근 아이디어

두 리프 사이의 최단 경로 길이는 트리에서 이렇게 구할 수 있다: **두 리프의 최소 공통 조상(LCA)을 찾아서, "리프1 깊이 - LCA 깊이" + "리프2 깊이 - LCA 깊이"**를 더하면 된다.

이 풀이는 LCA를 별도 함수로 구하지 않고, 각 리프까지의 **경로를 문자열로 인코딩**해서 문자열 비교만으로 LCA 깊이를 얻어내는 방식을 쓴다.

- 루트에서 각 리프까지 내려가면서 왼쪽으로 갈 때마다 `"L"`, 오른쪽으로 갈 때마다 `"R"`을 붙인 문자열을 만든다. 이 문자열의 **길이가 곧 그 리프의 깊이**다.
- 서로 다른 두 리프의 경로 문자열은 **한쪽이 다른 쪽의 접두사가 될 수 없다** — 만약 리프 A의 경로가 리프 B의 경로의 접두사라면, A는 B의 조상이라는 뜻인데, A가 리프(자식이 없음)이면서 동시에 자손 B를 가질 수는 없기 때문이다(모순). 그래서 두 경로 문자열을 앞에서부터 비교하면 **반드시 어딘가에서 문자가 달라지고**, 그 지점(공통 접두사 길이 `k`)이 바로 두 리프의 **LCA의 깊이**다.
- 따라서 두 리프 사이의 거리는 `leaf1.length() + leaf2.length() - 2*k`로 계산된다.

모든 리프 쌍에 대해 이 계산을 반복해서 `distance` 이하인 쌍의 개수를 센다.

### 손으로 시뮬레이션 — `root = [1,2,3,null,4]`, `distance = 3`

DFS로 경로 문자열을 모으면:

| 방문 노드 | 경로 문자열 | 리프? |
|---|---|---|
| `1` | `""` | 아니오 (자식 2개) |
| `2` | `"L"` | 아니오 (오른쪽 자식만 있음) |
| `4` | `"LR"` | **예** → `leafNodes`에 추가 |
| `3` | `"R"` | **예** → `leafNodes`에 추가 |

`leafNodes = ["LR", "R"]`. 두 문자열을 비교하면 인덱스 `0`에서 바로 `'L' != 'R'`이므로 `k = 0`(LCA는 루트).

거리 = `len("LR") + len("R") - 2*0 = 2 + 1 - 0 = 3` → `distance(3)` 이하이므로 좋은 쌍 → 총 `1`.

## 3. 코드 (Java)

```java
import java.util.ArrayList;
import java.util.List;

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

    List<String> leafNodes;

    public int countPairs(TreeNode root, int distance) {
        leafNodes = new ArrayList<>();
        dfs(root, "");
        int result = 0;

        for (int i = 0; i < leafNodes.size(); i++) {
            for (int j = i + 1; j < leafNodes.size(); j++) {
                String leaf1 = leafNodes.get(i);
                String leaf2 = leafNodes.get(j);

                int min = Math.min(leaf1.length(), leaf2.length());
                for (int k = 0; k < min; k++) {
                    if (leaf1.charAt(k) != leaf2.charAt(k)) { // 처음 갈라지는 지점 = LCA 깊이
                        if (leaf1.length() + leaf2.length() - 2 * k <= distance) result++;
                        break;
                    }
                }
            }
        }

        return result;
    }

    private void dfs(TreeNode node, String s) {
        if (node.left == null && node.right == null) {
            leafNodes.add(s); // 리프에 도달하면 지금까지의 경로를 기록
            return;
        }

        if (node.left != null) dfs(node.left, s + "L");
        if (node.right != null) dfs(node.right, s + "R");
    }
}
```

### 코드 설명

- `dfs`는 현재까지의 경로 문자열 `s`를 인자로 들고 내려가다가, 리프에 도달하면 `leafNodes`에 그 경로를 그대로 기록한다.
- 두 리프의 경로가 서로 접두사 관계일 수 없다는 성질 덕분에, `for (int k = 0; k < min; k++)` 루프는 **항상 `min`에 도달하기 전에 `break`로 빠져나온다** — 즉 두 경로가 끝까지 같은 채로 루프가 끝나는 경우는 생기지 않는다.
- `leaf1.length() + leaf2.length() - 2 * k`가 곧 "리프1까지 거리 + 리프2까지 거리 − LCA 깊이 두 번"이라는, 트리에서의 표준적인 두 노드 간 거리 공식이다.

## 4. 복잡도 분석

- **시간복잡도**: `O(L² × D)` — `L`은 리프 개수, `D`는 경로 문자열의 최대 길이(트리 높이). 모든 리프 쌍(`O(L²)`)마다 문자열을 최대 `D`칸까지 비교한다.
- **공간복잡도**: `O(L × D)` — 리프마다 최대 길이 `D`인 경로 문자열을 저장한다.

`n <= 1024`, `distance <= 10` 제약에서는 리프가 많아도(균형 트리 기준 최대 `~512`개) `512² × 10 ≈ 260만`으로 충분히 빠르게 통과한다.

## 5. 더 빠른 방법: DP on Trees — O(n × distance²)

리프 쌍을 전부 순회하는 대신, **각 노드에서 "거리별 리프 개수"를 세어 부모로 전달**하면 트리를 한 번만 순회하고도 답을 구할 수 있다 (문제의 힌트가 제시하는 방향).

- `dfs(node)`는 길이 `distance+1`짜리 배열을 반환한다. `count[d]`는 "`node`로부터 거리 `d`만큼 떨어진, `node`의 서브트리 안 리프의 개수".
- 리프면 `count[0] = 1`, 나머지는 `0`.
- 내부 노드는 왼쪽/오른쪽 자식의 배열을 받아서:
  - **답 누적**: 왼쪽에서 거리 `i`인 리프와 오른쪽에서 거리 `j`인 리프를 짝지으면, 현재 노드를 거쳐가는 실제 거리는 `i + j + 2`다. `i + j + 2 <= distance`인 모든 `(i, j)` 조합 수(`left[i] * right[j]`)를 답에 더한다.
  - **부모에게 전달할 배열**: 왼쪽/오른쪽 배열을 한 칸씩 밀어서(현재 노드를 지나는 만큼 거리 `+1`) 합친다. 밀었을 때 `distance`를 넘어가는 거리는 **더 이상 어떤 조상과 짝지어도 좋은 쌍이 될 수 없으므로 그냥 버린다** — 이 가지치기 덕분에 배열 크기가 항상 `distance+1` 이하로 유지된다.

```java
class Solution {
    int result = 0;
    int distance;

    public int countPairs(TreeNode root, int distance) {
        this.distance = distance;
        dfs(root);
        return result;
    }

    // count[d] = 현재 노드로부터 거리 d인 (서브트리 안) 리프 개수
    private int[] dfs(TreeNode node) {
        if (node.left == null && node.right == null) {
            int[] count = new int[distance + 1];
            count[0] = 1;
            return count;
        }

        int[] left = node.left != null ? dfs(node.left) : new int[distance + 1];
        int[] right = node.right != null ? dfs(node.right) : new int[distance + 1];

        // 왼쪽 거리 i + 오른쪽 거리 j + 2(현재 노드를 거치는 두 간선) <= distance 인 쌍을 모두 센다
        for (int i = 0; i <= distance - 2; i++) {
            if (left[i] == 0) continue;
            for (int j = 0; j <= distance - 2 - i; j++) {
                result += left[i] * right[j];
            }
        }

        // 현재 노드를 지나는 만큼 거리 +1, distance를 넘는 값은 버림(가지치기)
        int[] merged = new int[distance + 1];
        for (int i = 0; i < distance; i++) {
            merged[i + 1] += left[i];
            merged[i + 1] += right[i];
        }
        return merged;
    }
}
```

`distance`가 최대 10으로 고정돼 있어서 각 노드에서 하는 일이 `O(distance²)`(최대 100) 상수에 가까워, 전체가 `O(n × distance²)` — 사실상 `O(n)`에 수렴한다.

| | 경로 문자열 방식 (위 3절) | DP on Trees |
|---|---|---|
| 시간복잡도 | `O(L² × D)` | `O(n × distance²)` |
| 균형 트리, `n=1024` 기준 | 리프 `~512`개 → 약 `260만` 연산 | 약 `10만` 연산 |
| 핵심 아이디어 | 리프까지 경로를 문자열로 인코딩 → 두 리프씩 비교해 LCA 깊이 계산 | 서브트리별 "거리별 리프 개수"를 post-order로 병합하며 그 자리에서 카운트 |

두 방식 모두 이 문제의 제약(`n <= 1024`, `distance <= 10`) 안에서는 충분히 빠르게 통과하지만, DP on Trees 쪽이 점근적으로 더 우수하다.

## 6. 엣지 케이스

- **트리에 리프가 하나뿐인 경우** (`root`가 단일 노드): 짝지을 다른 리프가 없으므로 항상 `0`.
- **모든 리프가 같은 깊이인 완전 이진 트리** (예 2): 형제 리프끼리는 거리 `2`로 항상 가깝고, 사촌 리프끼리는 거리가 더 멀어질 수 있다.
- **한쪽으로 치우친 경로 때문에 리프 깊이가 서로 많이 다른 경우** (예 3): 경로 문자열 길이가 서로 다르므로 `Math.min`으로 짧은 쪽 길이까지만 비교해도 안전하다(더 짧은 쪽이 먼저 끝나는 지점 이후는 비교할 필요가 없다 — 접두사 관계가 불가능하므로 반드시 그 전에 갈라진다).
- **`distance`가 매우 작은 경우** (`distance=1`): 어떤 두 서로 다른 리프도 거리가 최소 `2`(공통 부모를 가진 형제 리프) 이상이므로 항상 `0`.

## 7. 관련 문제

같은 이진 트리(`TreeNode`)를 재귀로 순회하며 "리프" 또는 "모든 노드"의 성질을 검사하는 계열.

- [Balanced Binary Tree (LeetCode 110)](../110-balanced-binary-tree/Solution.md), [K-th Largest Perfect Subtree Size in Binary Tree (LeetCode 3319)](../3319-k-th-largest-perfect-subtree-size-in-binary-tree/Solution.md) — 둘 다 "서브트리 전체를 순회하며 노드별 조건을 판정"하는 post-order DFS 계열이지만, 이 문제는 리프들 사이의 거리를 구하는 게 목적이라 접근 방식(경로 인코딩, DP on Trees)이 다르다.

> 이 섹션은 새 문제를 풀 때마다 갱신됩니다. 리프 노드 간 거리, DP on Trees 계열 문제를 풀면 이 목록에 서로 링크를 추가해주세요.

---

*이 문서는 [Problem.md](./Problem.md)의 문제 설명을 기반으로 상세 풀이를 정리한 것입니다.*
