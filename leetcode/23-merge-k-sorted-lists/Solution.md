# [LeetCode 23] Merge k Sorted Lists — 값만 전부 모아서 정렬한 뒤 다시 연결 리스트로

- 문제 번호: 23
- 링크: https://leetcode.com/problems/merge-k-sorted-lists/
- 난이도: Hard
- 태그: Linked List, Divide and Conquer, Heap (Priority Queue), Merge Sort
- 사용 자료구조/알고리즘: **DFS로 전체 값 수집 + 정렬 + 새 연결 리스트 구성**

## 1. 문제 요약

각각 오름차순 정렬된 연결 리스트 `k`개가 배열 `lists`로 주어질 때, 이들을 전부 합쳐 **하나의 정렬된 연결 리스트**로 반환한다.

```text
예시)
1->4->5
1->3->4
2->6

합치면: 1->1->2->3->4->4->5->6
```

`lists[i].length`는 `0`일 수 있다 — 즉 배열 안에 **빈 리스트(= `null`)** 가 섞여 있을 수 있다는 점이 중요한 함정이다.

## 2. 접근 아이디어

각 리스트가 개별적으로는 정렬돼 있다는 성질을 활용해 두 개씩 합치는 표준 병합(`Merge Two Sorted Lists`)을 반복할 수도 있지만, 이 풀이는 더 단순하게 접근한다.

1. `lists` 안의 모든 연결 리스트를 순회하면서, **각 노드의 값만 하나의 리스트(`resultList`)에 전부 모은다** (`dfs`로 재귀 순회).
2. `resultList`를 오름차순 정렬한다.
3. 정렬된 값들로 **새 `ListNode`들을 만들어** 순서대로 이어 붙인다.

핵심은 `lists` 배열 안의 원소들 중 **일부가 `null`(빈 리스트)일 수 있다는 것**을 놓치지 않는 것이다. `null`인 리스트는 "합칠 값이 없는 리스트"로 취급해 건너뛰어야지, 그것 때문에 전체를 포기하면 안 된다. 또한 **모든** 리스트가 비어있어서 `resultList`가 끝까지 빈 채로 남는 경우도 별도로 처리해야 한다 (정렬된 값이 하나도 없으면 결과는 `null`).

### 손으로 시뮬레이션 — `lists = [[1,4,5],[1,3,4],[2,6]]`

| 단계 | 내용 |
|---|---|
| `dfs([1,4,5])` | `resultList = [1, 4, 5]` |
| `dfs([1,3,4])` | `resultList = [1, 4, 5, 1, 3, 4]` |
| `dfs([2,6])` | `resultList = [1, 4, 5, 1, 3, 4, 2, 6]` |
| 정렬 | `resultList = [1, 1, 2, 3, 4, 4, 5, 6]` |
| 연결 리스트 재구성 | `1->1->2->3->4->4->5->6` |

## 3. 코드 (Java)

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class Solution {

    List<Integer> resultList;

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;

        resultList = new ArrayList<>();

        for (ListNode list : lists) {
            if (list == null) continue; // 빈 리스트는 건너뛴다 (포기하고 null 반환하면 안 됨)
            dfs(list);
        }

        if (resultList.isEmpty()) return null; // 모든 리스트가 비어있던 경우

        Collections.sort(resultList);
        ListNode[] temp = new ListNode[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            temp[i] = new ListNode(resultList.get(i));
        }

        for (int i = 0; i < resultList.size() - 1; i++) {
            temp[i].next = temp[i + 1];
        }

        return temp[0];
    }

    private void dfs(ListNode list) {
        if (list == null) return;
        resultList.add(list.val);
        dfs(list.next);
    }
}
```

### 코드 설명

- `resultList`는 클래스 필드이지만, `mergeKLists`가 호출될 때마다 `new ArrayList<>()`로 새로 초기화하므로 같은 `Solution` 인스턴스를 여러 번 호출해도 이전 호출의 상태가 남지 않는다.
- `for (ListNode list : lists) { if (list == null) continue; ... }` — 배열 안의 개별 리스트가 비어있는 것(`null`)과, 순회 자체를 계속할지는 별개다. 하나가 비어있다고 전체를 포기하면 안 된다.
- `resultList.isEmpty()` 체크는 "빈 리스트들만 모여있어서 합칠 값이 하나도 없는 경우"를 위한 것이다. 이 체크가 없으면 크기 `0`짜리 `temp` 배열에서 `temp[0]`을 참조하다가 `ArrayIndexOutOfBoundsException`이 난다.
- 원본 노드를 재활용하지 않고 정렬된 값으로 **새 `ListNode`를 만들어** 이어 붙인다 — 원본 리스트들의 노드/링크를 건드리지 않으므로 부작용이 없다.

## 4. 복잡도 분석

- **시간복잡도**: `O(N log N)` — `N`은 전체 노드 개수의 합(최대 `10^4`). 값을 모으는 데 `O(N)`, 정렬에 `O(N log N)`, 새 리스트를 잇는 데 `O(N)`.
- **공간복잡도**: `O(N)` — `resultList`와 새로 만드는 `ListNode`들이 전체 노드 수만큼 필요하다. (재귀 `dfs`의 호출 스택도 리스트 하나의 길이만큼 사용하지만, 개별 리스트 길이는 최대 500이라 문제되지 않는다.)

참고로 이 문제의 "정석" 풀이는 **최소 힙(우선순위 큐)** 을 이용해 `k`개의 리스트 머리 노드 중 가장 작은 값을 매번 꺼내는 방식으로 `O(N log k)`에 풀거나, 리스트들을 **분할 정복(Divide and Conquer)** 으로 두 개씩 짝지어 합쳐서 `O(N log k)`에 푸는 방식이다 (`k`는 리스트 개수). 지금 방식은 각 리스트가 이미 정렬돼 있다는 성질을 쓰지 않고 값만 모아 다시 정렬하기 때문에, `k`가 작고 `N`이 크면 정석 풀이보다 로그 항이 `log N` vs `log k`로 더 크지만, 이 문제의 제약(`N <= 10^4`)에서는 성능 차이가 체감되지 않는다.

## 5. 엣지 케이스

- **`lists`가 빈 배열인 경우** (`lists = []`): `lists.length == 0`에서 바로 `null` 반환.
- **`lists` 안의 모든 리스트가 비어있는 경우** (`lists = [[]]` 등): 순회는 다 하지만 `resultList`가 끝까지 비어있으므로 `resultList.isEmpty()`에서 `null` 반환.
- **일부 리스트만 비어있는 경우** (`lists = [[2,6], []]`): 비어있는 리스트는 건너뛰고, 값이 있는 리스트들만 모아 정상적으로 병합한다.
- **모든 리스트에 값이 하나씩만 있는 경우**: `dfs`가 각 리스트에서 값 하나만 추가하고 바로 종료되므로 정상 동작.
- **중복된 값이 여러 리스트에 걸쳐 있는 경우** (예제 1의 `1`이 두 리스트에 등장): `Collections.sort`는 안정 정렬은 아니지만 값 자체가 같으므로 순서가 결과에 영향을 주지 않는다.

## 6. 겪었던 실수 (디버깅 기록)

1. **리스트 하나가 비어있으면 전체를 포기**: 처음 버전은 `for` 루프 안에서 `if (list == null) return null;`로, 순회 중인 리스트 하나라도 `null`이면 **그 즉시 전체 결과를 `null`로 반환**해버렸다. 문제 제약상 `lists[i].length`가 `0`일 수 있어서 `lists = [[2,6], []]`처럼 일부만 빈 리스트인 입력이 흔한데, 이 경우 실제로는 `[2,6]`이 나와야 하는데도 두 번째 리스트가 비어있다는 이유만으로 빈 결과를 반환했다. → `return null;`을 `continue;`로 바꿔서, 비어있는 리스트는 그냥 건너뛰고 나머지를 계속 처리하도록 고쳤다.
2. **모든 리스트가 비어있는 경우의 빈 배열 접근**: 위 수정과 함께, `resultList`가 끝까지 비어있을 수 있다는 점도 처리해야 했다. 그 상태로 `temp[0]`에 접근하면 `ArrayIndexOutOfBoundsException`이 나므로, 정렬 전에 `resultList.isEmpty()`를 확인해 `null`을 반환하도록 추가했다.

두 실수 모두 "리스트 배열 안에 빈 리스트가 섞여 있을 수 있다"는 제약을 처음엔 고려하지 못한 데서 비롯됐다 — 예제 3(`lists = [[]]`)처럼 **모든** 리스트가 비어있는 경우는 챙겼지만, **일부만** 비어있는 중간 케이스는 놓치기 쉬웠다.

## 7. 관련 문제

같은 연결 리스트(`ListNode`)를 다루는 문제. (이 저장소에서 `ListNode`를 다루는 첫 문제)

- [Merge Two Sorted Lists (LeetCode 21)](../21-merge-two-sorted-lists/Solution.md) — 이 문제를 리스트 2개짜리로 축소한 기본형. "값을 모아서 정렬 후 재구성"하는 접근이 동일하다.
- [Ugly Number II (LeetCode 264)](../264-ugly-number-ii/Solution.md) — 정렬된 여러 흐름 중 매번 최솟값을 꺼내 병합한다는 다중 포인터 아이디어는 같지만, 병합 대상 수열이 미리 주어지지 않고 그때그때 만들어진다는 점이 다르다.

> 이 섹션은 새 문제를 풀 때마다 갱신됩니다. 연결 리스트 병합/분할 정복/힙 계열 문제를 풀면 이 목록에 서로 링크를 추가해주세요.

---

*이 문서는 [Problem.md](./Problem.md)의 문제 설명을 기반으로 상세 풀이를 정리한 것입니다.*
