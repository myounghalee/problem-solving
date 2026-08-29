# Longest Palindromic Substring

- 문제 번호: 5
- 링크: https://leetcode.com/problems/longest-palindromic-substring/
- 난이도: Medium
- 태그: Two Pointers, String, Dynamic Programming, Manacher

## 문제 설명 (요약)

문자열 `s`가 주어질 때, `s` 안에서 가장 긴 **팰린드롬(회문, palindrome)** 부분 문자열을 반환한다. (팰린드롬: 앞에서 읽으나 뒤에서 읽으나 같은 문자열, 예: `"aba"`, `"bb"`)

## 제약사항

- `1 <= s.length <= 1000`
- `s`는 숫자와 영어 알파벳으로만 구성된다.

## 함수 시그니처

```java
class Solution {
    public String longestPalindrome(String s) {
        
    }
}
```

## 입출력 예

| s | 결과 |
|---|---|
| "babad" | "bab" (또는 "aba", 둘 다 정답으로 인정) |
| "cbbd" | "bb" |

## 참고 (LeetCode 힌트)

- 브루트포스로 모든 시작-끝 쌍(`O(n²)`개)에 대해 팰린드롬인지 확인(`O(n)`)하면 전체 `O(n³)`이라 느리다.
- "이미 계산해둔 더 작은 팰린드롬 결과를 재사용해서 더 큰 팰린드롬을 O(1)에 판정할 수 있는가?"가 핵심 힌트. 예를 들어 `"aba"`가 팰린드롬이면 `"xabax"`도 팰린드롬인지는 양 끝 문자(`x`, `x`)만 비교하면 되지, `"aba"` 부분을 다시 검사할 필요가 없다.
- 대표적인 접근: 각 위치를 중심으로 좌우로 확장(Expand Around Center, `O(n²)`), 또는 DP(`O(n²)`), 더 빠르게는 Manacher's Algorithm(`O(n)`).
