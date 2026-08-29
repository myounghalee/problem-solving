# Merge Two Sorted Lists

- 문제 번호: 21
- 링크: https://leetcode.com/problems/merge-two-sorted-lists/
- 난이도: Easy
- 태그: Linked List, Recursion

## 문제 설명 (요약)

정렬된 두 연결 리스트 `list1`, `list2`의 head가 주어질 때, 두 리스트를 하나의 정렬된 리스트로 합쳐서 반환한다. 새 리스트는 기존 두 리스트의 노드들을 그대로 이어붙여서(splicing) 만든다.

## 제약사항

- 두 리스트의 노드 개수 합: `0 ~ 50`
- `-100 <= Node.val <= 100`
- `list1`, `list2` 모두 오름차순(non-decreasing)으로 정렬되어 있다.

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
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        
    }
}
```

## 입출력 예

**입출력 예 #1**

`list1 = [1,2,4]`, `list2 = [1,3,4]` → `[1,1,2,3,4,4]`

**입출력 예 #2**

`list1 = []`, `list2 = []` → `[]`

**입출력 예 #3**

`list1 = []`, `list2 = [0]` → `[0]`
