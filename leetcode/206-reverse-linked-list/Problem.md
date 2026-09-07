# Reverse Linked List

- 문제 번호: 206
- 링크: https://leetcode.com/problems/reverse-linked-list/
- 난이도: Easy
- 태그: Linked List, Recursion

## 문제 설명 (요약)

단일 연결 리스트의 `head`가 주어질 때, 리스트 전체를 뒤집어서 반환한다.

## 제약사항

- 리스트의 노드 개수는 `0`부터 `5000` 사이.
- `-5000 <= Node.val <= 5000`

**추가 조건(Follow-up)**: 반복(iterative)과 재귀(recursive) 두 가지 방식으로 모두 풀 수 있는가?

## 함수 시그니처

```java
class Solution {
    public ListNode reverseList(ListNode head) {
        
    }
}
```

## 입출력 예

| head | 결과 |
|---|---|
| [1,2,3,4,5] | [5,4,3,2,1] |
| [1,2] | [2,1] |
| [] | [] |

### 입출력 예 설명

**입출력 예 #1, #2**: 노드 순서를 통째로 뒤집는다.

**입출력 예 #3**: 빈 리스트를 뒤집어도 빈 리스트다.
