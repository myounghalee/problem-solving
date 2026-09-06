# [LeetCode 21] Merge Two Sorted Lists — 값만 모아서 정렬한 뒤 다시 연결 리스트로

- 문제 번호: 21
- 링크: https://leetcode.com/problems/merge-two-sorted-lists/
- 난이도: Easy
- 태그: Linked List, Recursion
- 사용 자료구조/알고리즘: **두 리스트의 값을 모아 정렬 + 새 연결 리스트 구성**

## 1. 문제 요약

정렬된 두 연결 리스트 `list1`, `list2`의 head가 주어질 때, 두 리스트를 하나의 정렬된 리스트로 합쳐서 반환한다.

```text
예시)
list1 = 1->2->4
list2 = 1->3->4

합치면: 1->1->2->3->4->4
```

## 2. 접근 아이디어

[Merge k Sorted Lists (LeetCode 23)](../23-merge-k-sorted-lists/Solution.md)에서 썼던 것과 **같은 패턴**을, 리스트가 2개뿐인 경우로 단순화한 것이다.

1. `list1`, `list2`의 모든 노드 값을 하나의 `List<Integer>`로 모은다 (`toList`로 각각 변환 후 합치기).
2. 정렬한다.
3. 정렬된 값들로 **새 `ListNode`들을 만들어서** 순서대로 이어 붙인다.

두 리스트가 이미 각각 정렬돼 있다는 성질은 활용하지 않고, 그냥 값을 다 모아서 다시 정렬하는 방식이다. 리스트 크기 제한이 작아서(`0 ~ 50`) 이 방식으로도 충분히 빠르게 통과한다. (더 빠르고 문제 취지에 맞는 방법은 5절 참고)

### 손으로 시뮬레이션 — `list1 = [1,2,4]`, `list2 = [1,3,4]`

| 단계 | 내용 |
|---|---|
| `toList(list1)` | `[1, 2, 4]` |
| `toList(list2)` | `[1, 3, 4]` |
| 합치기 | `[1, 2, 4, 1, 3, 4]` |
| 정렬 | `[1, 1, 2, 3, 4, 4]` |
| 연결 리스트 재구성 | `1->1->2->3->4->4` |

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

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        List<Integer> list = new ArrayList<>();
        list.addAll(toList(list1));
        list.addAll(toList(list2));
        Collections.sort(list);

        ListNode[] temp = new ListNode[list.size()];
        if (temp.length == 0) return null;
        for (int i = 0; i < list.size(); i++) {
            temp[i] = new ListNode(list.get(i));
            if (i != 0) temp[i - 1].next = temp[i];
        }

        return temp[0];
    }

    // 연결 리스트를 List<Integer>로 변환
    private static List<Integer> toList(ListNode node) {
        List<Integer> result = new ArrayList<>();
        while (node != null) {
            result.add(node.val);
            node = node.next;
        }
        return result;
    }
}
```

### 코드 설명

- `toList`는 원래 테스트(`main`)에서 결과를 비교하려고 만든 헬퍼였는데, `mergeTwoLists` 로직 안에서도 "연결 리스트 → 배열" 변환에 그대로 재사용했다. 같은 동작이라 자연스러운 재사용이지만, 실제 채점 코드로 옮길 때 `toList`를 빠뜨리면 컴파일이 안 되니 주의해야 한다(단순 테스트 헬퍼가 아니라 핵심 로직의 일부가 됐다는 뜻).
- `temp.length == 0`(두 리스트가 모두 비어있는 경우) 체크가 없으면 `temp[0]`에서 `ArrayIndexOutOfBoundsException`이 난다.
- 기존 노드의 `next`를 재배치하는 게 아니라 **매번 새 `ListNode`를 생성**해서 잇는다 — 문제 설명의 "splicing(기존 노드를 이어붙이기)"과는 다른 방식이지만, 채점 기준(출력 값의 순서)에는 영향이 없다.

## 4. 복잡도 분석

- **시간복잡도**: `O(n log n)` — `n`은 두 리스트의 노드 개수 합(최대 `100`). 값 수집 `O(n)`, 정렬 `O(n log n)`, 재구성 `O(n)`.
- **공간복잡도**: `O(n)` — 값 리스트와 새로 만드는 `ListNode`들.

## 5. 더 빠른/정석 방법: 두 포인터로 직접 이어붙이기 — O(n)

두 리스트가 **이미 각각 정렬돼 있다**는 성질을 활용하면, 정렬 없이 **한 번의 순회**만으로 병합할 수 있다. 이 방식이 문제가 요구하는 "기존 노드를 그대로 이어붙이기(splicing)"의 의도에도 맞다.

```java
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(); // 결과 리스트의 시작을 가리키는 더미 노드
        ListNode cur = dummy;

        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                cur.next = list1; // 기존 노드를 그대로 재사용
                list1 = list1.next;
            } else {
                cur.next = list2;
                list2 = list2.next;
            }
            cur = cur.next;
        }
        cur.next = (list1 != null) ? list1 : list2; // 둘 중 남은 나머지를 통째로 이어붙임

        return dummy.next;
    }
}
```

- `dummy` 노드는 "결과 리스트의 시작 지점을 가리키는 손잡이" 역할만 하고, 실제 값으로 쓰이지 않는다.
- 두 리스트 중 하나가 먼저 끝나면, 남은 리스트는 **이미 정렬돼 있으므로** 나머지를 통째로 이어붙이기만 하면 된다.
- 정렬을 아예 하지 않으므로 `O(n log n)`이 아니라 `O(n)`이고, 새 노드를 만들지 않고 기존 노드의 `next`만 재배치하므로 값 저장용 리스트도 필요 없어 `O(1)` 추가 공간(반환할 리스트 자체는 제외)만 쓴다.

| | 값 수집 + 정렬 (위 3절) | 두 포인터 splice |
|---|---|---|
| 시간복잡도 | `O(n log n)` | `O(n)` |
| 공간복잡도 | `O(n)` | `O(1)` (새 노드 생성 없음) |
| 문제의 "splicing" 의도 | 새 노드를 만들어서 값만 맞춤 | 기존 노드를 그대로 재배치 |

## 6. 엣지 케이스

- **두 리스트가 모두 빈 경우** (`list1 = [], list2 = []`): `temp.length == 0`에서 `null` 반환.
- **한쪽만 빈 경우** (`list1 = [], list2 = [0]`): 빈 쪽은 `toList`가 빈 리스트를 반환하므로 자연스럽게 처리된다.
- **두 리스트에 중복된 값이 있는 경우** (예제 1의 `1`, `4`): 정렬 방식은 값이 같으면 상대 순서가 결과에 영향을 주지 않는다.
- **한 리스트가 다른 리스트보다 훨씬 긴 경우**: 두 포인터 방식(5절)에서는 짧은 쪽이 먼저 소진되고 남은 긴 쪽을 통째로 이어붙이는 것으로 자연스럽게 처리된다.

## 7. 관련 문제

- [Merge k Sorted Lists (LeetCode 23)](../23-merge-k-sorted-lists/Solution.md) — 이 문제를 `k`개 리스트로 일반화한 버전. "값을 모아서 정렬 후 재구성"하는 접근 방식이 완전히 동일하다(이 문제를 먼저 `k`개 버전으로 푼 뒤, 그 로직을 2개짜리로 줄인 것).
- [Add Two Numbers (LeetCode 2)](../2-add-two-numbers/Solution.md) — 같은 "더미 헤드 + `cur` 포인터로 새 리스트를 만든다"는 패턴을 쓴다. 값을 비교해 순서대로 잇는 이 문제와 달리, 값을 더해 자리올림을 계산한다는 목적이 다르다.

> 이 섹션은 새 문제를 풀 때마다 갱신됩니다. 연결 리스트 병합 계열 문제를 풀면 이 목록에 서로 링크를 추가해주세요.

---

*이 문서는 [Problem.md](./Problem.md)의 문제 설명을 기반으로 상세 풀이를 정리한 것입니다.*
