# Word Pattern

- 문제 번호: 290
- 링크: https://leetcode.com/problems/word-pattern/
- 난이도: Easy
- 태그: Hash Table, String

## 문제 설명 (요약)

패턴 문자열 `pattern`과, 공백으로 구분된 단어들의 문자열 `s`가 주어질 때, `s`가 `pattern`을 따르는지 판별한다.

`s`가 `pattern`을 따른다는 것은 `pattern`의 각 문자와 `s`의 각 단어 사이에 **일대일 대응**이 성립한다는 뜻이다 — [205번(Isomorphic Strings)](../205-isomorphic-strings/Problem.md)과 같은 개념을 문자 대 문자가 아니라 **문자 대 단어**로 확장한 문제다.

- `pattern`의 같은 문자는 항상 `s`의 같은 단어에 대응해야 한다.
- `pattern`의 서로 다른 문자가 `s`의 같은 단어에 대응되면 안 된다.
- `pattern`의 길이와 `s`의 단어 개수가 다르면 애초에 대응이 불가능하다.

## 제약사항

- `1 <= pattern.length <= 300`
- `pattern`은 소문자 영어 알파벳으로만 이루어져 있다.
- `1 <= s.length <= 3000`
- `s`는 소문자 영어 알파벳과 공백(`' '`)으로만 이루어져 있다.
- `s`의 앞뒤에 공백이 없다.
- `s`의 단어들은 공백 하나로 구분되어 있다.

## 함수 시그니처

```java
class Solution {
    public boolean wordPattern(String pattern, String s) {

    }
}
```

## 입출력 예

| pattern (입력) | s (입력) | 반환값 |
|---|---|---|
| "abba" | "dog cat cat dog" | true |
| "abba" | "dog cat cat fish" | false |
| "aaaa" | "dog cat cat dog" | false |

### 입출력 예 설명

**입출력 예 #1**: `a→dog`, `b→cat`로 대응시키면 패턴 `"abba"`가 정확히 `"dog cat cat dog"`가 된다. `true`.

**입출력 예 #2**: `a→dog`, `b→cat`까지는 맞지만, 마지막 `a`는 다시 `dog`이어야 하는데 `fish`가 나와 대응이 깨진다. `false`.

**입출력 예 #3**: 패턴의 `a`가 `dog`과 `cat` 두 단어 모두에 대응돼야 해서 일대일 대응이 아니다. `false`.
