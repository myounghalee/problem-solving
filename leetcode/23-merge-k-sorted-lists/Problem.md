# Merge k Sorted Lists

- 문제 번호: 23
- 링크: https://leetcode.com/problems/merge-k-sorted-lists/
- 난이도: Hard
- 태그: Linked List, Divide and Conquer, Heap (Priority Queue), Merge Sort

## 문제 설명 (요약)

각각 오름차순으로 정렬된 연결 리스트 `k`개가 배열 `lists`로 주어질 때, 이들을 전부 합쳐서 **하나의 정렬된 연결 리스트**로 만들어 반환한다.

## 제약사항

- `k == lists.length`
- `0 <= k <= 10^4`
- `0 <= lists[i].length <= 500`
- `-10^4 <= lists[i][j] <= 10^4`
- `lists[i]`는 오름차순으로 정렬되어 있다.
- `lists[i].length`의 총합은 `10^4`를 넘지 않는다.

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
    public ListNode mergeKLists(ListNode[] lists) {
        
    }
}
```

## 입출력 예

**입출력 예 #1**

```
lists = [
  1->4->5,
  1->3->4,
  2->6
]
```

`lists = [[1,4,5],[1,3,4],[2,6]]` → `[1,1,2,3,4,4,5,6]`

**입출력 예 #2**

`lists = []` → `[]`

**입출력 예 #3**

`lists = [[]]` → `[]`
