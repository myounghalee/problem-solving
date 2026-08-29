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
| [167](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/) | Two Sum II - Input Array Is Sorted | Medium | Array, Two Pointers, Binary Search | [Problem.md](./167-two-sum-ii-input-array-is-sorted/Problem.md) | [Solution.md](./167-two-sum-ii-input-array-is-sorted/Solution.md) |
| [653](https://leetcode.com/problems/two-sum-iv-input-is-a-bst/) | Two Sum IV - Input is a BST | Easy | Hash Table, Two Pointers, Tree, DFS, BFS, BST | [Problem.md](./653-two-sum-iv-input-is-a-bst/Problem.md) | [Solution.md](./653-two-sum-iv-input-is-a-bst/Solution.md) |
| [438](https://leetcode.com/problems/find-all-anagrams-in-a-string/) | Find All Anagrams in a String | Medium | Hash Table, String, Sliding Window | [Problem.md](./438-find-all-anagrams-in-a-string/Problem.md) | [Solution.md](./438-find-all-anagrams-in-a-string/Solution.md) |
| [242](https://leetcode.com/problems/valid-anagram/) | Valid Anagram | Easy | Hash Table, String, Sorting | [Problem.md](./242-valid-anagram/Problem.md) | [Solution.md](./242-valid-anagram/Solution.md) |
| [49](https://leetcode.com/problems/group-anagrams/) | Group Anagrams | Medium | Array, Hash Table, String, Sorting | [Problem.md](./49-group-anagrams/Problem.md) | [Solution.md](./49-group-anagrams/Solution.md) |
| [5](https://leetcode.com/problems/longest-palindromic-substring/) | Longest Palindromic Substring | Medium | Two Pointers, String, DP, Manacher | [Problem.md](./5-longest-palindromic-substring/Problem.md) | [Solution.md](./5-longest-palindromic-substring/Solution.md) |
| [516](https://leetcode.com/problems/longest-palindromic-subsequence/) | Longest Palindromic Subsequence | Medium | String, Dynamic Programming | [Problem.md](./516-longest-palindromic-subsequence/Problem.md) | [Solution.md](./516-longest-palindromic-subsequence/Solution.md) |
| [647](https://leetcode.com/problems/palindromic-substrings/) | Palindromic Substrings | Medium | Two Pointers, String, Dynamic Programming | [Problem.md](./647-palindromic-substrings/Problem.md) | 풀이 전 |

## 개발 환경

- Java, IntelliJ IDEA — `programmers/`와 동일하게 문제 폴더마다 독립된 모듈(`.iml`)로 구성됩니다.
