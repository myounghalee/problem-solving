# Group Anagrams

- 문제 번호: 49
- 링크: https://leetcode.com/problems/group-anagrams/
- 난이도: Medium
- 태그: Array, Hash Table, String, Sorting

## 문제 설명 (요약)

문자열 배열 `strs`가 주어질 때, 서로 애너그램인 문자열끼리 그룹으로 묶어서 반환한다. 그룹의 순서, 그룹 안 문자열의 순서는 상관없다.

[242번(Valid Anagram)](../242-valid-anagram/Problem.md)이 "두 문자열이 애너그램인가"를 판정하는 문제였다면, 이번엔 **여러 문자열을 애너그램 관계끼리 묶는** 문제다.

## 제약사항

- `1 <= strs.length <= 10^4`
- `0 <= strs[i].length <= 100`
- `strs[i]`는 소문자 영어 알파벳으로만 구성된다 (길이 0인 빈 문자열도 가능).

## 함수 시그니처

```java
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        
    }
}
```

## 입출력 예

| strs | 결과 |
|---|---|
| ["eat","tea","tan","ate","nat","bat"] | [["bat"],["nat","tan"],["ate","eat","tea"]] |
| [""] | [[""]] |
| ["a"] | [["a"]] |

### 입출력 예 설명

**입출력 예 #1**: `"bat"`은 다른 어떤 문자열과도 애너그램이 아니라 혼자 그룹. `"nat"`과 `"tan"`은 서로 애너그램. `"ate"`, `"eat"`, `"tea"`는 셋 다 서로 애너그램.
