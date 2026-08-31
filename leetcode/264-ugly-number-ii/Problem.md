# Ugly Number II

- 문제 번호: 264
- 링크: https://leetcode.com/problems/ugly-number-ii/
- 난이도: Medium
- 태그: Hash Table, Math, Dynamic Programming, Heap (Priority Queue)

## 문제 설명 (요약)

"어글리 넘버(ugly number)"란 소인수가 `2`, `3`, `5`로만 이루어진 양의 정수를 말한다. 정수 `n`이 주어질 때, `n`번째 어글리 넘버를 반환한다.

## 제약사항

- `1 <= n <= 1690`

## 함수 시그니처

```java
class Solution {
    public int nthUglyNumber(int n) {
        
    }
}
```

## 입출력 예

**입출력 예 #1**

`n = 10` → `12`

처음 10개의 어글리 넘버는 `[1, 2, 3, 4, 5, 6, 8, 9, 10, 12]`.

**입출력 예 #2**

`n = 1` → `1`

`1`은 소인수가 없으므로, 소인수가 전부 `2, 3, 5`로만 이루어져 있다는 조건을 (공허하게) 만족한다.
