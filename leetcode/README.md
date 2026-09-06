# LeetCode 문제 풀이

[LeetCode](https://leetcode.com) 문제를 풀고, 블로그에 게시할 수 있는 형태로 정리합니다. (저장소 최상위 [README.md](../README.md) 참고)

## 폴더 구조

문제 하나당 폴더 하나, 폴더명은 `문제번호-slug` 형식입니다 (slug는 LeetCode URL의 마지막 경로 조각, 예: `integer-to-english-words`). 각 폴더는 이렇게 구성됩니다.

```
<문제번호>-<slug>/
├── Problem.md      # 문제 설명, 제한사항, 입출력 예 (LeetCode 원문 기반 요약 — 저작권상 전문 그대로 복사하지 않고 우리말로 요약)
├── Solution.java    # 직접 작성하는 풀이 코드
└── Solution.md      # 블로그 게시용 상세 해설 (접근 아이디어, 시뮬레이션, 코드, 복잡도, 관련 문제)
```

- `Solution.java`는 각자 IntelliJ에서 직접 작성하는 작업 파일입니다. `package` 선언은 넣지 않습니다 (LeetCode 제출 코드도 스켈레톤 그대로 유지, 클래스 충돌은 문제 폴더 = 별도 모듈 구조로 방지 — `programmers/`와 동일한 컨벤션).
- LeetCode는 문제마다 메서드 시그니처(메서드명·반환 타입)가 다르므로, 그대로 LeetCode가 제시하는 스켈레톤을 따릅니다.
- `Solution.md`는 `Solution.java`와 독립적인 완결형 문서로, 코드 블록을 자체적으로 포함합니다.

## 문제 목록

| 번호 | 제목 | 난이도 | 태그 | 문제 | 풀이 |
|---|---|---|---|---|---|
| [273](https://leetcode.com/problems/integer-to-english-words/) | Integer to English Words | Hard | Math, String, Recursion | [Problem.md](./273-integer-to-english-words/Problem.md) | [Solution.md](./273-integer-to-english-words/Solution.md) |
| [12](https://leetcode.com/problems/integer-to-roman/) | Integer to Roman | Medium | Hash Table, Math, String | [Problem.md](./12-integer-to-roman/Problem.md) | [Solution.md](./12-integer-to-roman/Solution.md) |
| [13](https://leetcode.com/problems/roman-to-integer/) | Roman to Integer | Easy | Hash Table, Math, String | [Problem.md](./13-roman-to-integer/Problem.md) | [Solution.md](./13-roman-to-integer/Solution.md) |
| [1](https://leetcode.com/problems/two-sum/) | Two Sum | Easy | Array, Hash Table | [Problem.md](./1-two-sum/Problem.md) | [Solution.md](./1-two-sum/Solution.md) |
| [3](https://leetcode.com/problems/longest-substring-without-repeating-characters/) | Longest Substring Without Repeating Characters | Medium | Hash Table, String, Sliding Window | [Problem.md](./3-longest-substring-without-repeating-characters/Problem.md) | [Solution.md](./3-longest-substring-without-repeating-characters/Solution.md) |
| [17](https://leetcode.com/problems/letter-combinations-of-a-phone-number/) | Letter Combinations of a Phone Number | Medium | Hash Table, String, Backtracking | [Problem.md](./17-letter-combinations-of-a-phone-number/Problem.md) | [Solution.md](./17-letter-combinations-of-a-phone-number/Solution.md) |
| [36](https://leetcode.com/problems/valid-sudoku/) | Valid Sudoku | Medium | Array, Hash Table, Matrix | [Problem.md](./36-valid-sudoku/Problem.md) | [Solution.md](./36-valid-sudoku/Solution.md) |
| [432](https://leetcode.com/problems/all-oone-data-structure/) | All O`one Data Structure | Hard | Hash Table, Linked List, Design, Doubly-Linked List | [Problem.md](./432-all-oone-data-structure/Problem.md) | [Solution.md](./432-all-oone-data-structure/Solution.md) |
| [2487](https://leetcode.com/problems/remove-nodes-from-linked-list/) | Remove Nodes From Linked List | Medium | Linked List, Stack, Recursion, Monotonic Stack | [Problem.md](./2487-remove-nodes-from-linked-list/Problem.md) | [Solution.md](./2487-remove-nodes-from-linked-list/Solution.md) |
| [76](https://leetcode.com/problems/minimum-window-substring/) | Minimum Window Substring | Hard | Hash Table, String, Sliding Window | [Problem.md](./76-minimum-window-substring/Problem.md) | [Solution.md](./76-minimum-window-substring/Solution.md) |
| [15](https://leetcode.com/problems/3sum/) | 3Sum | Medium | Array, Two Pointers, Sorting | [Problem.md](./15-3sum/Problem.md) | [Solution.md](./15-3sum/Solution.md) |
| [198](https://leetcode.com/problems/house-robber/) | House Robber | Medium | Array, Dynamic Programming | [Problem.md](./198-house-robber/Problem.md) | [Solution.md](./198-house-robber/Solution.md) |
| [322](https://leetcode.com/problems/coin-change/) | Coin Change | Medium | Array, Dynamic Programming, Breadth-First Search | [Problem.md](./322-coin-change/Problem.md) | [Solution.md](./322-coin-change/Solution.md) |
| [120](https://leetcode.com/problems/triangle/) | Triangle | Medium | Array, Dynamic Programming | [Problem.md](./120-triangle/Problem.md) | [Solution.md](./120-triangle/Solution.md) |
| [100](https://leetcode.com/problems/same-tree/) | Same Tree | Easy | Tree, DFS, BFS, Binary Tree | [Problem.md](./100-same-tree/Problem.md) | [Solution.md](./100-same-tree/Solution.md) |
| [105](https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/) | Construct Binary Tree from Preorder and Inorder Traversal | Medium | Array, Hash Table, Divide and Conquer, Tree, Binary Tree | [Problem.md](./105-construct-binary-tree-from-preorder-and-inorder-traversal/Problem.md) | [Solution.md](./105-construct-binary-tree-from-preorder-and-inorder-traversal/Solution.md) |
| [167](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/) | Two Sum II - Input Array Is Sorted | Medium | Array, Two Pointers, Binary Search | [Problem.md](./167-two-sum-ii-input-array-is-sorted/Problem.md) | [Solution.md](./167-two-sum-ii-input-array-is-sorted/Solution.md) |
| [653](https://leetcode.com/problems/two-sum-iv-input-is-a-bst/) | Two Sum IV - Input is a BST | Easy | Hash Table, Two Pointers, Tree, DFS, BFS, BST | [Problem.md](./653-two-sum-iv-input-is-a-bst/Problem.md) | [Solution.md](./653-two-sum-iv-input-is-a-bst/Solution.md) |
| [438](https://leetcode.com/problems/find-all-anagrams-in-a-string/) | Find All Anagrams in a String | Medium | Hash Table, String, Sliding Window | [Problem.md](./438-find-all-anagrams-in-a-string/Problem.md) | [Solution.md](./438-find-all-anagrams-in-a-string/Solution.md) |
| [242](https://leetcode.com/problems/valid-anagram/) | Valid Anagram | Easy | Hash Table, String, Sorting | [Problem.md](./242-valid-anagram/Problem.md) | [Solution.md](./242-valid-anagram/Solution.md) |
| [49](https://leetcode.com/problems/group-anagrams/) | Group Anagrams | Medium | Array, Hash Table, String, Sorting | [Problem.md](./49-group-anagrams/Problem.md) | [Solution.md](./49-group-anagrams/Solution.md) |
| [5](https://leetcode.com/problems/longest-palindromic-substring/) | Longest Palindromic Substring | Medium | Two Pointers, String, DP, Manacher | [Problem.md](./5-longest-palindromic-substring/Problem.md) | [Solution.md](./5-longest-palindromic-substring/Solution.md) |
| [516](https://leetcode.com/problems/longest-palindromic-subsequence/) | Longest Palindromic Subsequence | Medium | String, Dynamic Programming | [Problem.md](./516-longest-palindromic-subsequence/Problem.md) | [Solution.md](./516-longest-palindromic-subsequence/Solution.md) |
| [647](https://leetcode.com/problems/palindromic-substrings/) | Palindromic Substrings | Medium | Two Pointers, String, Dynamic Programming | [Problem.md](./647-palindromic-substrings/Problem.md) | [Solution.md](./647-palindromic-substrings/Solution.md) |
| [146](https://leetcode.com/problems/lru-cache/) | LRU Cache | Medium | Hash Table, Linked List, Design, Doubly-Linked List | [Problem.md](./146-lru-cache/Problem.md) | [Solution.md](./146-lru-cache/Solution.md) |
| [460](https://leetcode.com/problems/lfu-cache/) | LFU Cache | Hard | Hash Table, Linked List, Design, Doubly-Linked List | [Problem.md](./460-lfu-cache/Problem.md) | [Solution.md](./460-lfu-cache/Solution.md) |
| [110](https://leetcode.com/problems/balanced-binary-tree/) | Balanced Binary Tree | Easy | Tree, DFS, Binary Tree | [Problem.md](./110-balanced-binary-tree/Problem.md) | [Solution.md](./110-balanced-binary-tree/Solution.md) |
| [3319](https://leetcode.com/problems/k-th-largest-perfect-subtree-size-in-binary-tree/) | K-th Largest Perfect Subtree Size in Binary Tree | Medium | Tree, DFS, Sorting, Binary Tree | [Problem.md](./3319-k-th-largest-perfect-subtree-size-in-binary-tree/Problem.md) | [Solution.md](./3319-k-th-largest-perfect-subtree-size-in-binary-tree/Solution.md) |
| [1530](https://leetcode.com/problems/number-of-good-leaf-nodes-pairs/) | Number of Good Leaf Nodes Pairs | Medium | Tree, DFS, Binary Tree, DP on Trees | [Problem.md](./1530-number-of-good-leaf-nodes-pairs/Problem.md) | [Solution.md](./1530-number-of-good-leaf-nodes-pairs/Solution.md) |
| [23](https://leetcode.com/problems/merge-k-sorted-lists/) | Merge k Sorted Lists | Hard | Linked List, Divide and Conquer, Heap, Merge Sort | [Problem.md](./23-merge-k-sorted-lists/Problem.md) | [Solution.md](./23-merge-k-sorted-lists/Solution.md) |
| [21](https://leetcode.com/problems/merge-two-sorted-lists/) | Merge Two Sorted Lists | Easy | Linked List, Recursion | [Problem.md](./21-merge-two-sorted-lists/Problem.md) | [Solution.md](./21-merge-two-sorted-lists/Solution.md) |
| [264](https://leetcode.com/problems/ugly-number-ii/) | Ugly Number II | Medium | Hash Table, Math, Dynamic Programming, Heap (Priority Queue) | [Problem.md](./264-ugly-number-ii/Problem.md) | [Solution.md](./264-ugly-number-ii/Solution.md) |
| [359](https://leetcode.com/problems/logger-rate-limiter/) | Logger Rate Limiter | Easy | Hash Table, Design, Data Stream | [Problem.md](./359-logger-rate-limiter/Problem.md) | [Solution.md](./359-logger-rate-limiter/Solution.md) |
| [362](https://leetcode.com/problems/design-hit-counter/) | Design Hit Counter | Medium | Design, Queue, Binary Search, Data Stream | [Problem.md](./362-design-hit-counter/Problem.md) | [Solution.md](./362-design-hit-counter/Solution.md) |

## 개발 환경

- Java, IntelliJ IDEA — `programmers/`와 동일하게 문제 폴더마다 독립된 모듈(`.iml`)로 구성됩니다.
