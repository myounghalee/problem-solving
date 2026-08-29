# Palindromic Substrings

- 문제 번호: 647
- 링크: https://leetcode.com/problems/palindromic-substrings/
- 난이도: Medium
- 태그: Two Pointers, String, Dynamic Programming

## 문제 설명 (요약)

문자열 `s`가 주어질 때, `s` 안에 있는 팰린드롬 **부분 문자열(연속된 구간)**의 총 개수를 반환한다. 같은 문자열이라도 위치(시작-끝 인덱스)가 다르면 별개로 센다.

[5번(Longest Palindromic Substring)](../5-longest-palindromic-substring/Problem.md)이 "가장 긴 팰린드롬 부분 문자열 하나"를 찾는 문제였다면, 이번엔 "팰린드롬인 부분 문자열이 총 몇 개인지" 개수를 센다.

## 제약사항

- `1 <= s.length <= 1000`
- `s`는 소문자 영어 알파벳으로만 구성된다.

## 함수 시그니처

```java
class Solution {
    public int countSubstrings(String s) {
        
    }
}
```

## 입출력 예

| s | 결과 |
|---|---|
| "abc" | 3 |
| "aaa" | 6 |

### 입출력 예 설명

**입출력 예 #1**: 팰린드롬인 부분 문자열은 `"a"`, `"b"`, `"c"` 세 개(전부 길이 1).

**입출력 예 #2**: `"a"`, `"a"`, `"a"`(위치가 다른 세 개의 길이 1), `"aa"`, `"aa"`(위치가 다른 두 개의 길이 2), `"aaa"`(길이 3) — 총 6개.

## 참고 (LeetCode 힌트)

- 브루트포스로 모든 시작-끝 쌍(`O(n²)`개)마다 팰린드롬 여부를 확인(`O(n)`)하면 `O(n³)`이라 느리다.
- "이미 계산해둔 더 작은 팰린드롬 결과를 재사용해서 더 큰 팰린드롬을 O(1)에 판정할 수 있는가?"가 핵심. [5번](../5-longest-palindromic-substring/Solution.md)에서 썼던 "중심에서 확장하기(Expand Around Center)"를 그대로 재사용할 수 있다 — 이번엔 "가장 긴 것 하나"를 기록하는 대신, 확장하며 매칭될 때마다 개수를 세면 된다.
