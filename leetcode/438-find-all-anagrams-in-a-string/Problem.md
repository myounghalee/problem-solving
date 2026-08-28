# Find All Anagrams in a String

- 문제 번호: 438
- 링크: https://leetcode.com/problems/find-all-anagrams-in-a-string/
- 난이도: Medium
- 태그: Hash Table, String, Sliding Window

## 문제 설명 (요약)

문자열 `s`와 `p`가 주어질 때, `s`의 부분 문자열 중 `p`의 애너그램(anagram, 철자 구성이 같은 문자열)이 되는 모든 부분 문자열의 **시작 인덱스**를 배열로 반환한다. 순서는 상관없다.

## 제약사항

- `1 <= s.length, p.length <= 3 * 10^4`
- `s`와 `p`는 모두 소문자 영어 알파벳으로만 구성된다.

## 함수 시그니처

```java
class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        
    }
}
```

## 입출력 예

| s | p | 결과 |
|---|---|---|
| "cbaebabacd" | "abc" | [0,6] |
| "abab" | "ab" | [0,1,2] |

### 입출력 예 설명

**입출력 예 #1**: 인덱스 0의 부분 문자열 `"cba"`는 `"abc"`의 애너그램이고, 인덱스 6의 부분 문자열 `"bac"`도 `"abc"`의 애너그램이다.

**입출력 예 #2**: 인덱스 0(`"ab"`), 1(`"ba"`), 2(`"ab"`) 모두 `"ab"`의 애너그램이다.

## 참고

- 비슷한 문제로 [Two Sum 시리즈](../1-two-sum/Problem.md)와는 다르게, 이번엔 **문자열 슬라이딩 윈도우** 유형이다.
- 매번 부분 문자열을 새로 만들어 정렬해서 비교하면 느리다(`s.length`가 최대 3만이라 시간 초과 위험). "윈도우를 한 칸씩 옮길 때 양 끝 문자 하나씩만 갱신"하는 방식으로 접근하는 게 일반적이다.
