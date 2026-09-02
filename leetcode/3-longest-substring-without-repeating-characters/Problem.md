# Longest Substring Without Repeating Characters

- 문제 번호: 3
- 링크: https://leetcode.com/problems/longest-substring-without-repeating-characters/
- 난이도: Medium
- 태그: Hash Table, String, Sliding Window

## 문제 설명 (요약)

문자열 `s`가 주어질 때, **같은 문자가 반복되지 않는 부분 문자열(substring) 중 가장 긴 것의 길이**를 반환한다.

## 제약사항

- `0 <= s.length <= 5 * 10^4`
- `s`는 영문자, 숫자, 기호(symbol), 공백으로 구성된다.

## 함수 시그니처

```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        
    }
}
```

## 입출력 예

| s | 결과 |
|---|---|
| "abcabcbb" | 3 |
| "bbbbb" | 1 |
| "pwwkew" | 3 |

### 입출력 예 설명

**입출력 예 #1**: 반복 없는 가장 긴 부분 문자열은 `"abc"`이며 길이는 3이다.

**입출력 예 #2**: 반복 없는 가장 긴 부분 문자열은 `"b"`이며 길이는 1이다.

**입출력 예 #3**: 반복 없는 가장 긴 부분 문자열은 `"wke"`이며 길이는 3이다. `"pwke"`는 부분 수열(subsequence)일 뿐 부분 문자열(substring)이 아니므로 정답이 될 수 없다.

## 참고

- [438. Find All Anagrams in a String](../438-find-all-anagrams-in-a-string/Problem.md)와 마찬가지로 **문자열 슬라이딩 윈도우** 유형이다. 다만 윈도우 크기가 고정이 아니라 조건(중복 없음)에 따라 늘었다 줄었다 하는 가변 윈도우라는 차이가 있다.
