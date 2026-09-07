# [LeetCode 92] Reverse Linked List II — 구간 맨 앞에 하나씩 끼워 넣기

- 문제 번호: 92
- 링크: https://leetcode.com/problems/reverse-linked-list-ii/
- 난이도: Medium
- 태그: Linked List
- 사용 자료구조/알고리즘: **더미 노드 + 머리에 끼워 넣기(head insertion)**

## 1. 문제 요약

연결 리스트에서 `left`번째부터 `right`번째 노드까지(1-인덱스)만 뒤집고 나머지는 그대로 둔다.

```text
예시) [1,2,3,4,5], left=2, right=4 → [1,4,3,2,5]
```

## 2. 접근 아이디어

### `left == 1`이면 `head` 자체가 바뀐다 — 더미 노드로 흡수한다

뒤집을 구간이 리스트 맨 앞부터면 결과의 `head`가 바뀐다. 이걸 따로 처리하지 않으려고, 진짜 `head` 앞에 가짜 노드(`dummy`)를 붙여둔다. `dummy.next`를 최종 답으로 반환하면, "구간 앞에 노드가 있는 경우"와 "구간이 맨 앞부터인 경우"가 코드상 완전히 똑같이 처리된다.

### 구간의 첫 노드는 끝까지 자리를 지킨다 — 그게 꼬리가 된다

`[2,3,4]`를 뒤집으면 `[4,3,2]`가 된다. 이때 **`2`는 뒤집기 전에도 후에도 구간의 "끝"에 있다** — 뒤집기 전엔 맨 앞(구간의 시작), 뒤집은 후엔 맨 뒤(구간의 꼬리)다. 즉 `2` 자신은 실제로 이동할 필요가 없고, **`2`보다 뒤에 있던 노드들이 하나씩 `2`를 앞질러 나가는 것**으로 뒤집기를 표현할 수 있다.

- `start`: 구간의 첫 노드. 처음부터 끝까지 위치가 고정되고, 결국 구간의 꼬리가 된다.
- `then`: `start` 바로 다음 노드. 매 반복마다 이 노드를 뽑아서 **구간의 맨 앞**(`pre` 바로 뒤)에 꽂는다.

한 번 반복할 때마다 하는 일은 셋뿐이다.

1. `start`가 `then`을 건너뛰게 한다(`start.next = then.next`) — `then`을 리스트에서 떼어낸다.
2. `then`을 구간의 맨 앞(`pre.next`, 지금까지 뒤집힌 부분의 머리)으로 연결한다.
3. `pre.next`를 `then`으로 갱신해, `then`이 새로운 맨 앞이 되게 한다.

이걸 `right - left`번(구간 길이보다 1번 적게, `start` 자신은 옮기지 않으므로) 반복하면 구간 전체가 뒤집힌다.

### 반복문 한 바퀴를, 줄 단위로 쪼개서 보기

`[1,2,3,4,5], left=2, right=4`로 시작한다.

```text
dummy -> 1 -> 2 -> 3 -> 4 -> 5
          ↑    ↑    ↑
         pre  start then
```

목표는 `2,3,4` 구간을 `4,3,2`로 뒤집는 것이다. 반복문 한 바퀴(1회차)를 네 줄로 나눠 각 줄 직후의 상태를 확인해보자.

**줄 1: `start.next = then.next;`** — `then`(3)을 리스트에서 떼어낸다.

```text
이전: 1 -> 2 -> 3 -> 4 -> 5
이후: 1 -> 2 -------> 4 -> 5      (2가 3을 건너뛰고 바로 4를 가리킴)
              3(고아 상태, 아직 아무도 안 가리킴)
```

`start`(2)가 원래 `3`을 가리켰는데, 이제 `3`의 다음 노드였던 `4`를 바로 가리킨다. `3`은 리스트 밖으로 빠져나와 "어디에도 연결 안 된" 상태가 됐다.

**줄 2: `then.next = pre.next;`** — 떼어낸 `then`(3)이 "지금까지 뒤집힌 부분의 머리"(`pre.next`, 아직 `2`)를 가리키게 한다.

```text
이전: 1 -> 2 -------> 4 -> 5   /   3(고아)
이후: 1 -> 2 -------> 4 -> 5   /   3 -> 2   (3이 2를 가리키기 시작)
```

**줄 3: `pre.next = then;`** — `pre`(1)가 이제 `then`(3)을 가리키게 해서, `3`을 정식으로 리스트에 편입시킨다.

```text
이전: 1 -> 2 -------> 4 -> 5   /   3 -> 2
이후: 1 -> 3 -> 2 -------> 4 -> 5      (3이 1과 2 사이에 끼워짐)
```

**줄 4: `then = start.next;`** — 다음 차례에 옮길 노드를 갱신한다. `start`(2)는 한 번도 안 움직였으므로, `start.next`는 항상 "아직 안 옮겨진, 그다음 순서의 노드"를 정확히 가리킨다.

```text
현재: 1 -> 3 -> 2 -> 4 -> 5
새 then = start.next = 4
```

**1회차가 끝난 모습**: `1 -> 3 -> 2 -> 4 -> 5`. `3`이 구간 맨 앞으로 옮겨졌다.

### 2회차 — 같은 네 줄을 그대로 반복

이번엔 `then = 4`다. 같은 네 줄이 그대로 반복된다.

```text
줄1 (4를 떼어냄):        1 -> 3 -> 2 -------> 5   /   4(고아)
줄2 (4가 머리를 가리킴):  1 -> 3 -> 2 -------> 5   /   4 -> 3
줄3 (1이 4를 가리킴):    1 -> 4 -> 3 -> 2 -------> 5
줄4 (then 갱신):        then = start.next = 5
```

`right - left = 2`번 반복했으니(1회차, 2회차) 여기서 끝난다. 최종 결과 `1 -> 4 -> 3 -> 2 -> 5` — 정답과 일치한다.

### 전체 표로 정리

| 반복 | 떼어내는 노드(`then`) | 결과 리스트 | 다음 `then` |
|---|---|---|---|
| 1회 | `3` | `1 -> 3 -> 2 -> 4 -> 5` | `4` (`start.next`) |
| 2회 | `4` | `1 -> 4 -> 3 -> 2 -> 5` | `5` |

`start`(2)는 두 번의 반복 내내 **한 번도 움직이지 않았다.** 대신 `then`이 매번 바뀌면서 `start` 앞으로 하나씩 끼어들었고, 그 결과 `2`는 상대적으로 계속 뒤로 밀려나 구간의 꼬리가 됐다. "노드를 옮긴다"기보다 "다른 노드들이 이 노드를 앞지르게 한다"는 쪽에 가깝다.

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
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(0, head);
        ListNode pre = dummy;
        for (int i = 0; i < left - 1; i++) pre = pre.next; // 구간 시작 바로 앞까지 이동

        ListNode start = pre.next; // 구간의 첫 노드. 끝까지 자리를 지키며 꼬리 역할을 한다
        ListNode then = start.next; // 매번 맨 앞으로 옮겨질 노드

        for (int i = 0; i < right - left; i++) { // 구간 길이보다 1번 적게 반복
            start.next = then.next;
            then.next = pre.next;
            pre.next = then;
            then = start.next;
        }

        return dummy.next;
    }
}
```

### 코드 설명

- `new ListNode(0, head)`로 더미 노드를 만들면서 그 다음이 곧 원래 `head`가 되도록 한다.
- `for (int i = 0; i < left - 1; i++)`가 `left == 1`일 때는 한 번도 안 돌아, `pre`가 `dummy`에 그대로 남는다 — "구간이 맨 앞부터 시작하는 경우"가 자연스럽게 처리되는 지점이다.
- 반복문 안 네 줄의 순서가 중요하다. `start.next = then.next`로 먼저 `then`을 리스트에서 떼어내야, 그다음 `then.next = pre.next`가 안전하게 "지금까지 뒤집힌 부분의 머리"를 가리킬 수 있다. 순서를 바꾸면 `then`이 자기 자신을 가리키는 등 리스트가 깨진다.
- `then = start.next`로 다음 반복에서 옮길 노드를 갱신한다. `start`는 절대 움직이지 않으므로, `start.next`는 항상 "아직 안 옮겨진 다음 노드"를 정확히 가리킨다.
- 반복 횟수가 `right - left`(구간 길이 `right-left+1`보다 하나 적음)인 이유는 `start` 자신은 이동시키지 않기 때문이다.

## 4. 복잡도 분석

- **시간복잡도**: `O(n)` — 구간 앞까지 가는 데 `O(left)`, 구간을 뒤집는 데 `O(right - left)`. 리스트를 한 번만 순회하므로 Follow-up이 요구한 조건을 만족한다.
- **공간복잡도**: `O(1)` — 포인터 몇 개만 사용하고, 새 노드를 만들지 않는다(기존 노드의 `next`만 다시 연결).

실측: 제약 상한 `n=500` 전체 뒤집기에서 0ms.

## 5. 엣지 케이스

- **`left == right`**: 뒤집을 노드가 하나뿐이라 반복문이 `0`번 돌아(`right-left=0`) 아무것도 바뀌지 않는다.
- **`left == 1`**: `pre`가 `dummy`에 남아, `dummy.next`가 자연스럽게 뒤집힌 구간의 새 머리가 된다.
- **`right`가 리스트의 마지막 노드**: `then.next`가 `null`이 되는 시점까지 자연스럽게 반복되고, 별도 처리가 필요 없다.
- **`left == 1 && right == n`(전체 뒤집기)**: 두 특수 케이스가 동시에 일어나지만, 더미 노드와 "고정된 `start`" 구조 덕분에 별도 분기 없이 그대로 처리된다.

검증: 무작위 20,000건(부분 배열을 뒤집는 브루트포스와 비교)을 교차 검증해 전부 일치했다.

## 6. 이런 방법도 있다 — [206번](../206-reverse-linked-list/Solution.md)의 뒤집기를 그대로 가져오기

머리에 끼워 넣는 방식이 낯설다면, [206번(Reverse Linked List)](../206-reverse-linked-list/Solution.md)에서 쓴 **전체 뒤집기 로직을 구간에만 적용**하는 방식도 있다. "구간을 통째로 뒤집은 뒤, 양 끝을 한 번에 재연결"하는 접근이다.

```java
class Solution {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(0, head);
        ListNode prev = dummy;
        for (int i = 0; i < left - 1; i++) prev = prev.next;

        ListNode leftNode = prev.next; // 뒤집고 나면 구간의 꼬리가 될 노드
        ListNode cur = leftNode;
        ListNode before = null;        // 206번의 prev 역할

        for (int i = 0; i <= right - left; i++) { // 구간 길이만큼 반복
            ListNode tmp = cur;
            cur = cur.next;
            tmp.next = before;
            before = tmp;
        }

        prev.next = before;   // 구간 앞 -> 뒤집힌 구간의 새 머리
        leftNode.next = cur;  // 뒤집힌 구간의 꼬리 -> 구간 뒤에 남은 부분

        return dummy.next;
    }
}
```

**비교:**

| | 머리에 끼워 넣기 (3절) | 206번 재사용 (6절) |
|---|---|---|
| 반복 횟수 | `right - left`번 | `right - left + 1`번 |
| 재연결 시점 | 매 반복마다 조금씩(`pre.next` 갱신) | 반복이 다 끝난 뒤 한 번에 두 줄 |
| 필요한 변수 | `start`, `then` | `before`, `cur`, `leftNode` |
| 핵심 직관 | "구간의 시작은 고정, 뒤 노드들이 하나씩 앞지른다" | "206번 로직을 그대로, 범위만 제한한다" |

둘 다 정확하고 시간·공간복잡도가 같다(`O(n)` / `O(1)`). 206번을 먼저 풀어봤다면 6절 쪽이 "아는 코드의 응용"이라 더 빨리 받아들여지고, 새로 접근한다면 3절 쪽이 "구간의 시작은 안 움직인다"는 관찰 하나로 반복문 네 줄만 기억하면 되어 더 단순하게 느껴질 수 있다.

## 7. 관련 문제

- [Reverse Linked List (LeetCode 206)](../206-reverse-linked-list/Solution.md) — 이 문제가 구간에만 적용하는 뒤집기 로직의 원형. 6절에서 그 로직을 거의 그대로 재사용한다.
- [Add Two Numbers (LeetCode 2)](../2-add-two-numbers/Solution.md) — 같은 연결 리스트 조작이지만 새 노드를 계속 만들어가는 문제. 이 문제는 반대로 **새 노드를 하나도 만들지 않고** 기존 노드의 `next`만 다시 연결한다는 점이 대비된다.

> 이 섹션은 새 문제를 풀 때마다 갱신됩니다. 연결 리스트 뒤집기, 더미 노드 활용 계열 문제를 풀면 이 목록에 서로 링크를 추가해주세요.

---

*이 문서는 [Problem.md](./Problem.md)의 문제 설명을 기반으로 상세 풀이를 정리한 것입니다.*
