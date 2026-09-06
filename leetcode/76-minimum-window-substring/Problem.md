# Minimum Window Substring

- 문제 번호: 76
- 링크: https://leetcode.com/problems/minimum-window-substring/
- 난이도: Hard
- 태그: Hash Table, String, Sliding Window

## 문제 설명 (요약)

문자열 `s`와 `t`가 주어질 때, **`t`의 모든 문자를 (개수까지 포함해) 포함하는 `s`의 부분 문자열 중 가장 짧은 것**을 반환한다. 그런 부분 문자열이 없으면 빈 문자열 `""`을 반환한다.

`t`에 같은 문자가 여러 번 나오면, 그 부분 문자열도 그 문자를 **그만큼의 개수 이상** 포함해야 한다. 정답이 유일하다는 보장은 없지만, 테스트 케이스는 정답이 유일하도록 주어진다.

## 제약사항

- `1 <= s.length, t.length <= 10^5`
- `s`와 `t`는 대소문자 영어 알파벳으로 구성된다.

## 함수 시그니처

```java
class Solution {
    public String minWindow(String s, String t) {
        
    }
}
```

## 입출력 예

| s | t | 결과 |
|---|---|---|
| "ADOBECODEBANC" | "ABC" | "BANC" |
| "a" | "a" | "a" |
| "a" | "aa" | "" |

### 입출력 예 설명

**입출력 예 #1**: `"BANC"`가 `A`, `B`, `C`를 모두 포함하는 가장 짧은 부분 문자열이다.

**입출력 예 #2**: `s`와 `t`가 완전히 같다.

**입출력 예 #3**: `t`는 `a`가 2개 필요한데 `s`에는 `a`가 1개뿐이라 답이 없다. 이때는 빈 문자열을 반환한다.
