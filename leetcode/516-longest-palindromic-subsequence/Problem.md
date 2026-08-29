# Longest Palindromic Subsequence

- 문제 번호: 516
- 링크: https://leetcode.com/problems/longest-palindromic-subsequence/
- 난이도: Medium
- 태그: String, Dynamic Programming

## 문제 설명 (요약)

문자열 `s`가 주어질 때, `s` 안에서 가장 긴 팰린드롬(회문) **부분 수열(subsequence)**의 길이를 반환한다.

**부분 수열**은 원래 문자열에서 몇몇 문자를 지워서(순서는 그대로 유지) 만들 수 있는 수열을 말한다 — [5번(Longest Palindromic Substring)](../5-longest-palindromic-substring/Problem.md)의 "부분 **문자열**(연속된 구간)"과 다르게, 부분 수열은 **연속하지 않아도** 된다.

## 제약사항

- `1 <= s.length <= 1000`
- `s`는 소문자 영어 알파벳으로만 구성된다.

## 함수 시그니처

```java
class Solution {
    public int longestPalindromeSubseq(String s) {
        
    }
}
```

## 입출력 예

| s | 결과 |
|---|---|
| "bbbab" | 4 |
| "cbbd" | 2 |

### 입출력 예 설명

**입출력 예 #1**: 가능한 정답 중 하나는 `"bbbb"`(인덱스 0,1,2,4의 문자를 골라 만든 부분 수열) — 길이 4.

**입출력 예 #2**: 가능한 정답 중 하나는 `"bb"` — 길이 2.
