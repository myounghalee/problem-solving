# [LeetCode 290] Word Pattern — 양방향 매핑으로 문자↔단어 일대일 대응 확인하기

- 문제 번호: 290
- 링크: https://leetcode.com/problems/word-pattern/
- 난이도: Easy
- 태그: Hash Table, String
- 사용 자료구조/알고리즘: **해시맵 두 개(양방향 매핑)**

## 1. 문제 요약

패턴 `pattern`의 각 문자와, 공백으로 구분된 `s`의 각 단어 사이에 **일대일 대응**이 성립하는지 판별한다. [205번(Isomorphic Strings)](../205-isomorphic-strings/Solution.md)과 완전히 같은 개념을 "문자 대 문자"가 아니라 "문자 대 단어"로 확장한 문제다.

```text
예시) pattern = "abba", s = "dog cat cat dog"  → true   (a→dog, b→cat)
      pattern = "abba", s = "dog cat cat fish" → false  (마지막 a가 dog가 아닌 fish에 대응)
      pattern = "aaaa", s = "dog cat cat dog"  → false  (a 하나가 dog, cat 두 단어에 대응)
```

## 2. 접근 아이디어

먼저 `s.split(" ")`로 단어 배열을 만들고, `pattern.length()`와 단어 개수가 다르면 애초에 일대일 대응이 불가능하므로 바로 `false`를 반환한다.

그다음은 205번과 똑같이 **양방향 매핑**을 사용한다.

- `map_ps`: `pattern`의 문자 → `s`의 단어 (정방향 매핑)
- `map_sp`: `s`의 단어 → `pattern`의 문자 (역방향 매핑)

패턴을 한 글자씩 순회하면서, 각 위치 `i`에서 `pattern.charAt(i)`와 `words[i]`에 대해

- `map_ps`에 이 문자가 이미 있는데 대응된 단어가 `words[i]`와 다르면 → 정방향 일관성 위반, `false`.
- `map_sp`에 이 단어가 이미 있는데 대응된 문자가 `pattern.charAt(i)`와 다르면 → 역방향 일관성 위반, `false`.
- 두 검사를 통과하면 두 맵에 각각 `(문자, 단어)` / `(단어, 문자)`를 기록한다(이미 같은 값이면 덮어써도 무해하다).

두 방향을 모두 검사해야 하는 이유는, 정방향만 확인하면 "서로 다른 문자가 같은 단어로 합쳐지는" 경우(`"aaaa"` / `"dog cat cat dog"`처럼 `a`가 `dog`과 `cat` 모두에 대응되려는 상황과는 다른 방향의 충돌, 예: `pattern="ab"`, `s="dog dog"`)를 걸러내지 못하기 때문이다.

### 손으로 시뮬레이션 — `pattern = "abba"`, `s = "dog cat cat dog"`

`words = ["dog", "cat", "cat", "dog"]`

| i | pattern[i] | words[i] | map_ps 확인 | map_sp 확인 | 통과 후 매핑 |
|---|---|---|---|---|---|
| 0 | a | dog | 없음 | 없음 | map_ps{a→dog}, map_sp{dog→a} |
| 1 | b | cat | 없음 | 없음 | map_ps{a→dog,b→cat}, map_sp{dog→a,cat→b} |
| 2 | b | cat | 있음, `cat`과 일치 | 있음, `b`와 일치 | 변화 없음(덮어쓰기만) |
| 3 | a | dog | 있음, `dog`과 일치 | 있음, `a`와 일치 | 변화 없음 |

끝까지 위반이 없으므로 `true` — 정답과 일치한다.

### 위반이 걸리는 경우 — `pattern = "aaaa"`, `s = "dog cat cat dog"`

| i | pattern[i] | words[i] | map_ps 확인 | 결과 |
|---|---|---|---|---|
| 0 | a | dog | 없음 | 통과, map_ps{a→dog} |
| 1 | a | cat | 있음, 값 `dog` ≠ `cat` | **여기서 `false`로 중단** |

`a`가 이미 `dog`으로 매핑돼 있는데 이번엔 `cat`으로 매핑하려 하니 정방향 일관성이 깨진다.

## 3. 코드 (Java)

```java
class Solution {
    public boolean wordPattern(String pattern, String s) {
        String[] words = s.split(" ");
        if (pattern.length() != words.length) return false; // 길이가 다르면 대응 자체가 불가능

        Map<Character, String> map_ps = new HashMap<>(); // pattern의 문자 -> s의 단어
        Map<String, Character> map_sp = new HashMap<>(); // s의 단어 -> pattern의 문자
        for (int i = 0; i < pattern.length(); i++) {
            if (map_ps.containsKey(pattern.charAt(i)) && !map_ps.get(pattern.charAt(i)).equals(words[i])) {
                return false; // 정방향 불일치
            }
            if (map_sp.containsKey(words[i]) && !map_sp.get(words[i]).equals(pattern.charAt(i))) {
                return false; // 역방향 불일치
            }
            map_ps.put(pattern.charAt(i), words[i]);
            map_sp.put(words[i], pattern.charAt(i));
        }

        return true;
    }
}
```

### 코드 설명

- `words[i]`가 `String`이라 값 비교에 `==`가 아닌 `.equals()`를 써야 한다 — 205번의 `char` 비교(`!=`)와 다른 부분이다.
- `map_ps`, `map_sp`는 항상 서로의 역함수 관계를 유지하도록 함께 갱신된다. 이미 올바르게 매핑된 문자/단어를 다시 만나도 `put`이 같은 값을 덮어쓸 뿐이라 안전하다.
- 길이 검사(`pattern.length() != words.length`)를 맨 앞에서 먼저 해두면, 이후 루프에서는 `words[i]`가 항상 유효한 인덱스라고 가정하고 안심하고 접근할 수 있다.

## 4. 복잡도 분석

- **시간복잡도**: `O(n + m)` — `n`은 `pattern.length()`(= 단어 개수), `m`은 `s.length()`(단어를 나누는 `split`에 드는 비용). 나머지는 각 위치에서 `O(1)` 해시맵 연산이다.
- **공간복잡도**: `O(n + m)` — `words` 배열과 두 해시맵이 최대 `pattern`의 길이(또는 단어 개수)만큼, 그리고 단어들의 총 길이만큼 공간을 쓴다.

## 5. 엣지 케이스

- **패턴 길이와 단어 개수가 다른 경우**(`pattern="abc"`, `s="dog cat"`): 맨 앞의 길이 검사에서 바로 `false`.
- **서로 다른 문자가 같은 단어로 합쳐지는 경우**(`pattern="ab"`, `s="dog dog"`): `map_sp`에 이미 `dog→a`가 있는데 `b`가 다시 `dog`에 대응하려 해서 역방향 검사에서 `false`.
- **패턴 길이 1**: 루프가 한 번만 돌고, 매핑이 없던 상태에서 바로 등록되므로 항상 `true`.
- **모든 문자가 같고 단어도 모두 같은 경우**(`pattern="aaa"`, `s="dog dog dog"`): 매번 같은 매핑을 확인/덮어쓰기만 하므로 `true`.

## 6. 관련 문제

문자(또는 단어) 간 일대일 대응을 검사하는 계열 문제.

- [Isomorphic Strings (LeetCode 205)](../205-isomorphic-strings/Solution.md) — 이 문제와 동일한 "양방향 매핑" 아이디어를 쓰지만, 대응 단위가 "문자 대 문자"라는 점이 다르다. 이 문제는 그 개념을 "문자 대 단어"로 확장한 버전이다.

---

*이 문서는 [Problem.md](./Problem.md)의 문제 설명을 기반으로 상세 풀이를 정리한 것입니다.*
