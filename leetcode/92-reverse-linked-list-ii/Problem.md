# Reverse Linked List II

- 문제 번호: 92
- 링크: https://leetcode.com/problems/reverse-linked-list-ii/
- 난이도: Medium
- 태그: Linked List

## 문제 설명 (요약)

연결 리스트 `head`와 두 정수 `left`, `right`(`1 <= left <= right <= n`, `1`부터 시작하는 인덱스)가 주어질 때, **`left`번째부터 `right`번째 노드까지의 구간만 뒤집고** 나머지는 그대로 둔 리스트를 반환한다.

**추가 조건(Follow-up)**: 리스트를 한 번만 순회(`O(n)`)해서 풀 수 있는가?

## 제약사항

- 리스트의 노드 개수는 `1`부터 `500` 사이.
- `-500 <= Node.val <= 500`
- `1 <= left <= right <= n` (`n`은 리스트 길이)

## 함수 시그니처

```java
class Solution {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        
    }
}
```

## 입출력 예

| head | left | right | 결과 |
|---|---|---|---|
| [1,2,3,4,5] | 2 | 4 | [1,4,3,2,5] |
| [5] | 1 | 1 | [5] |

### 입출력 예 설명

**입출력 예 #1**: `2`번째(`2`)부터 `4`번째(`4`)까지, 즉 `[2,3,4]` 구간만 뒤집어 `[4,3,2]`가 되고, 앞뒤(`1`, `5`)는 그대로다.

**입출력 예 #2**: 노드가 하나뿐이고 `left == right == 1`이면 뒤집을 게 없어 그대로 반환한다.
