# Isomorphic Strings

- 문제 번호: 205
- 링크: https://leetcode.com/problems/isomorphic-strings/
- 난이도: Easy
- 태그: Hash Table, String

## 문제 설명 (요약)

두 문자열 `s`와 `t`가 주어질 때, `s`가 `t`와 **동형(isomorphic)**인지 판별한다.

`s`의 문자를 규칙에 따라 치환했을 때 `t`가 되면 동형이라고 한다. 치환 규칙은 다음을 모두 만족해야 한다.

- `s`의 같은 문자는 항상 `t`의 같은 문자로 치환되어야 한다.
- `s`의 서로 다른 문자가 `t`의 같은 문자로 치환되면 안 된다(즉, 치환은 일대일 대응이어야 한다).
- 문자의 순서는 그대로 유지된다(문자 위치만 대응시키는 것이지 재배열은 아니다).

## 제약사항

- `1 <= s.length <= 5 * 10^4`
- `t.length == s.length`
- `s`와 `t`는 유효한 ASCII 문자로 이루어져 있다.

## 함수 시그니처

```java
class Solution {
    public boolean isIsomorphic(String s, String t) {

    }
}
```

## 입출력 예

| s (입력) | t (입력) | 반환값 |
|---|---|---|
| "egg" | "add" | true |
| "foo" | "bar" | false |
| "paper" | "title" | true |

### 입출력 예 설명

**입출력 예 #1**: `e→a`, `g→d`로 대응시키면 `"egg"`가 `"add"`가 된다. 일대일 대응이 유지되므로 `true`.

**입출력 예 #2**: `f→b`, `o→a`로 대응시키려 하면 `"foo"`의 두 번째, 세 번째 문자(`o`, `o`)가 `"bar"`에서는 서로 다른 문자(`a`, `r`)에 대응돼야 해서 규칙이 깨진다. `false`.

**입출력 예 #3**: `p→t`, `a→i`, `e→l`, `r→e`로 대응시키면 `"paper"`가 `"title"`이 된다. `true`.
