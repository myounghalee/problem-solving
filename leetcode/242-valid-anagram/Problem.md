# Valid Anagram

- 문제 번호: 242
- 링크: https://leetcode.com/problems/valid-anagram/
- 난이도: Easy
- 태그: Hash Table, String, Sorting

## 문제 설명 (요약)

두 문자열 `s`, `t`가 주어질 때, `t`가 `s`의 애너그램이면 `true`, 아니면 `false`를 반환한다.

[438번(Find All Anagrams in a String)](../438-find-all-anagrams-in-a-string/Problem.md)에서 쓴 "애너그램 판정" 로직 자체를 독립된 문제로 떼어낸 형태다 — 이번엔 슬라이딩 윈도우 없이, 문자열 두 개가 통째로 애너그램인지만 확인하면 된다.

## 제약사항

- `1 <= s.length, t.length <= 5 * 10^4`
- `s`와 `t`는 모두 소문자 영어 알파벳으로만 구성된다.

## 함수 시그니처

```java
class Solution {
    public boolean isAnagram(String s, String t) {
        
    }
}
```

## 입출력 예

| s | t | 결과 |
|---|---|---|
| "anagram" | "nagaram" | true |
| "rat" | "car" | false |

## Follow-up

입력에 유니코드 문자가 포함된다면, 풀이를 어떻게 바꿔야 할까? (`int[26]` 같은 고정 크기 배열은 소문자 알파벳 전제라 유니코드에는 못 씀 — 해시맵 등으로 바꿔야 함)
