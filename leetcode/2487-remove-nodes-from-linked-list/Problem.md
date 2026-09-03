# Remove Nodes From Linked List

- 문제 번호: 2487
- 링크: https://leetcode.com/problems/remove-nodes-from-linked-list/
- 난이도: Medium
- 태그: Linked List, Stack, Recursion, Monotonic Stack

## 문제 설명 (요약)

연결 리스트의 `head`가 주어질 때, **자기 오른쪽 어딘가에 자기보다 큰 값을 가진 노드가 하나라도 있는 노드**를 전부 제거하고 남은 리스트의 `head`를 반환한다.

"오른쪽 어딘가"는 바로 다음 노드가 아니라 **뒤쪽 전체**를 뜻한다. 그리고 비교는 **엄격히 큰(strictly greater)** 경우만 해당하므로, 같은 값은 제거 사유가 되지 않는다.

## 제약사항

- 노드 개수는 `1`부터 `10^5` 사이다.
- `1 <= Node.val <= 10^5`

## 함수 시그니처

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode removeNodes(ListNode head) {
        
    }
}
```

## 입출력 예

| head | 결과 |
|---|---|
| [5,2,13,3,8] | [13,8] |
| [1,1,1,1] | [1,1,1,1] |

### 입출력 예 설명

**입출력 예 #1**: `[5,2,13,3,8]`

| 노드 | 오른쪽에 더 큰 값이 있는가 | 처리 |
|---|---|---|
| `5` | `13`이 있음 | 제거 |
| `2` | `13`이 있음 | 제거 |
| `13` | 오른쪽은 `3`, `8`뿐 | 유지 |
| `3` | `8`이 있음 | 제거 |
| `8` | 오른쪽에 아무것도 없음 | 유지 |

남는 것은 `[13, 8]`이다.

**입출력 예 #2**: `[1,1,1,1]` — 모든 값이 같다. "엄격히 큰" 값이 오른쪽에 없으므로 아무것도 제거되지 않고 그대로 `[1,1,1,1]`이다.
