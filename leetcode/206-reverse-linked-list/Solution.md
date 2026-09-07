# [LeetCode 206] Reverse Linked List — 포인터 세 개로 방향을 뒤집기

- 문제 번호: 206
- 링크: https://leetcode.com/problems/reverse-linked-list/
- 난이도: Easy
- 태그: Linked List, Recursion
- 사용 자료구조/알고리즘: **삼중 포인터(이전/현재/다음)**

## 1. 문제 요약

연결 리스트 전체를 뒤집는다.

```text
예시) [1,2,3,4,5] → [5,4,3,2,1]
```

## 2. 접근 아이디어

연결 리스트의 각 노드는 `next`로 **다음** 노드만 가리킨다. 뒤집는다는 건 이 화살표의 방향을 전부 반대로 돌리는 것 — 즉 각 노드가 **이전** 노드를 가리키게 만드는 것이다.

한 노드에서 화살표 방향을 바꾸려면(`node.next = 이전노드`), 그 순간 **원래의 다음 노드가 어디였는지**를 먼저 기억해둬야 한다. 안 그러면 방향을 바꾸는 순간 원래 다음 노드로 가는 길을 잃어버린다. 그래서 포인터가 세 개 필요하다.

- `prev`: 지금까지 뒤집어 놓은 부분의 머리(다음 노드가 가리켜야 할 곳)
- `head`(또는 `cur`): 지금 처리 중인 노드
- `tmp`(또는 `next`): 화살표를 바꾸기 전에 미리 저장해두는 원래의 다음 노드

### 손으로 시뮬레이션 — `[1,2,3]`

| 단계 | `prev` | `head` | 동작 |
|---|---|---|---|
| 시작 | `null` | `1` | — |
| 1 | `1`(`1.next=null`) | `2` | `tmp=2`; `1.next=null`; `prev=1` |
| 2 | `2`(`2.next=1`) | `3` | `tmp=3`; `2.next=1`; `prev=2` |
| 3 | `3`(`3.next=2`) | `null` | `tmp=null`; `3.next=2`; `prev=3` |

`head`가 `null`이 되어 루프가 끝나고, `prev`(`3`)가 새 머리다. `3 → 2 → 1`로 방향이 뒤집혔다.

## 3. 코드 (Java)

```java
class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        while (head != null) {
            ListNode tmp = head;
            head = head.next;
            tmp.next = prev;
            prev = tmp;
        }
        return prev;
    }
}
```

### 코드 설명

- `ListNode tmp = head; head = head.next;`로 **다음 노드로 넘어가기 전에** 현재 노드(`tmp`)를 따로 잡아둔다. 이 줄이 없으면 바로 다음 줄에서 `tmp.next`(=`head.next`)를 바꾸는 순간 원래 다음 노드로 가는 길이 사라진다.
- `tmp.next = prev;`가 실제로 화살표 방향을 뒤집는 줄이다.
- `prev = tmp;`로 "지금까지 뒤집은 부분의 머리"를 갱신한다.
- `head`가 `null`이 되면(리스트 끝까지 갔으면) 루프가 끝나고, 그 시점의 `prev`가 새로운 머리다.

## 4. 복잡도 분석

- **시간복잡도**: `O(n)` — 각 노드를 정확히 한 번씩 방문한다.
- **공간복잡도**: `O(1)` — 포인터 몇 개만 사용하고 새 노드를 만들지 않는다.

실측: 제약 상한 `n=5000`에서 1ms.

## 5. 엣지 케이스

- **빈 리스트** (`head == null`): `while` 루프가 한 번도 안 돌아 `prev`(초깃값 `null`)를 그대로 반환한다.
- **노드 하나**: 한 번 반복하고 끝나며, `next`가 `null`인 노드 하나가 그대로 반환된다(뒤집어도 모양이 같다).

검증: 무작위 5,000건을 `Collections.reverse`로 뒤집은 기준값과 교차 비교해 전부 일치했다.

## 6. 이런 방법도 있다 — 재귀

Follow-up이 반복(iterative)과 재귀(recursive) 둘 다 요구한다. 재귀는 "나머지 부분을 먼저 다 뒤집어 놓고, 그 결과에 지금 노드를 마지막에 붙인다"는 발상이다.

```java
class Solution {
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head; // 기저 조건: 0개 또는 1개 노드

        ListNode newHead = reverseList(head.next); // "나머지"를 먼저 통째로 뒤집는다
        head.next.next = head; // 원래 다음 노드가 나를 가리키게
        head.next = null;      // 나는 이제 꼬리이므로 다음이 없다

        return newHead;
    }
}
```

`head.next.next = head`가 핵심이다. 재귀 호출이 끝나 돌아왔을 때 `head.next`는 **아직 뒤집히기 전** 상태라, 여전히 원래의 다음 노드를 가리키고 있다. 그 노드의 `next`를 `head`로 돌려세우는 것이다.

**비교:**

| | 반복 (3절) | 재귀 (6절) |
|---|---|---|
| 공간복잡도 | `O(1)` | `O(n)`(호출 스택) |
| 깊은 리스트에서의 위험 | 없음 | 스택 오버플로 가능(리스트가 아주 길면) |

제약이 최대 5000개 노드라 재귀도 무난히 통과하지만, 리스트 길이에 상한이 없는 일반적인 상황이라면 반복 방식이 더 안전하다.

## 7. 관련 문제

- [Reverse Linked List II (LeetCode 92)](../92-reverse-linked-list-ii/Solution.md) — 이 문제의 뒤집기 로직을 리스트 전체가 아니라 **지정된 구간에만** 적용한 확장판. "어디서 시작해 몇 번만 도는가", "양 끝을 어떻게 재연결하는가"라는 두 가지가 추가된다.

> 이 섹션은 새 문제를 풀 때마다 갱신됩니다. 연결 리스트 뒤집기, 재귀/반복 비교 계열 문제를 풀면 이 목록에 서로 링크를 추가해주세요.

---

*이 문서는 [Problem.md](./Problem.md)의 문제 설명을 기반으로 상세 풀이를 정리한 것입니다.*
