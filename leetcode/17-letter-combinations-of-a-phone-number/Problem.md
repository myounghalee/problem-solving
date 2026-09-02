# Letter Combinations of a Phone Number

- 문제 번호: 17
- 링크: https://leetcode.com/problems/letter-combinations-of-a-phone-number/
- 난이도: Medium
- 태그: Hash Table, String, Backtracking

## 문제 설명 (요약)

`2`~`9` 사이의 숫자로만 이루어진 문자열 `digits`가 주어질 때, 그 숫자들이 나타낼 수 있는 **모든 문자 조합**을 반환한다. 답의 순서는 상관없다.

숫자와 문자의 대응은 아래 전화기 자판을 따른다. `1`은 어떤 문자에도 대응되지 않는다.

| 숫자 | 문자 |
|---|---|
| 2 | a b c |
| 3 | d e f |
| 4 | g h i |
| 5 | j k l |
| 6 | m n o |
| 7 | p q r s |
| 8 | t u v |
| 9 | w x y z |

## 제약사항

- `0 <= digits.length <= 4`
- `digits[i]`는 `'2'`부터 `'9'` 사이의 숫자다.

## 함수 시그니처

```java
class Solution {
    public List<String> letterCombinations(String digits) {
        
    }
}
```

## 입출력 예

| digits | 결과 |
|---|---|
| "23" | ["ad","ae","af","bd","be","bf","cd","ce","cf"] |
| "" | [] |
| "2" | ["a","b","c"] |

### 입출력 예 설명

**입출력 예 #1**: `2`는 `a/b/c`, `3`은 `d/e/f`에 대응하므로 앞자리 3가지 × 뒷자리 3가지 = 9가지 조합이 나온다.

**입출력 예 #2**: `digits`가 빈 문자열이면 조합할 것이 없으므로 **빈 리스트**를 반환한다. `[""]`(빈 문자열 하나가 든 리스트)가 아니라는 점에 주의.

**입출력 예 #3**: 숫자가 하나뿐이면 그 숫자에 대응하는 문자 각각이 곧 답이 된다.
