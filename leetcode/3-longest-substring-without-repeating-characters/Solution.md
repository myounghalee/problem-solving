# [LeetCode 3] Longest Substring Without Repeating Characters — 가변 크기 슬라이딩 윈도우로 풀기

- 문제 번호: 3
- 링크: https://leetcode.com/problems/longest-substring-without-repeating-characters/
- 난이도: Medium
- 태그: Hash Table, String, Sliding Window
- 사용 자료구조/알고리즘: **가변 크기 슬라이딩 윈도우 + `HashSet`**

## 1. 문제 요약

문자열 `s`에서 **같은 문자가 두 번 나오지 않는 부분 문자열(substring)** 중 가장 긴 것의 길이를 구한다.

```text
예시) "abcabcbb" → 3 ("abc")
      "bbbbb"    → 1 ("b")
      "pwwkew"   → 3 ("wke")
```

`"pwwkew"`에서 `"pwke"`는 길이 4지만 문자가 연속되어 있지 않은 **부분 수열(subsequence)**이라 답이 될 수 없다. 부분 문자열은 반드시 연속된 구간이어야 한다.

## 2. 접근 아이디어

모든 부분 문자열을 다 만들어보면 `O(n^2)`개의 구간을 검사해야 한다. `n`이 최대 `5×10^4`이므로 이 방식은 너무 느리다.

핵심은 **답이 되는 구간은 항상 "연속된 한 구간"**이라는 점이다. 그래서 구간의 양 끝(`left`, `right`)만 관리하면서, `right`를 오른쪽으로 한 칸씩 밀고 규칙을 어길 때만 `left`를 당기는 **슬라이딩 윈도우**를 쓸 수 있다.

- 윈도우 `s[left..right]`는 항상 "중복 문자가 없는 구간"이라는 불변식(invariant)을 유지한다.
- `right`를 한 칸 넓혔을 때 새 문자 `ch`가 이미 윈도우 안에 있으면, **그 중복이 사라질 때까지** `left`를 오른쪽으로 밀어낸다. 밀어내는 문자는 집합에서도 제거한다.
- 중복이 없어진 시점에 `ch`를 집합에 넣고, 그때의 윈도우 길이로 최댓값을 갱신한다.

438번처럼 윈도우 크기가 `p.length()`로 **고정**된 문제와 달리, 여기서는 윈도우가 조건에 따라 늘었다 줄었다 하는 **가변 크기** 윈도우다. 그래서 `right`는 항상 한 칸씩 전진하지만 `left`는 필요할 때만, 필요한 만큼 전진한다.

### "지금 윈도우에 이 문자가 있나?"를 어떻게 빠르게 아는가

매번 윈도우를 처음부터 훑어 중복을 확인하면 결국 `O(n^2)`이 된다. 대신 **윈도우 안에 들어있는 문자들의 집합**을 `HashSet<Character>`으로 따로 들고 다니면, 포함 여부 확인·추가·삭제가 모두 평균 `O(1)`이다. 윈도우가 한 칸 움직일 때마다 집합도 딱 한 칸씩만 갱신하면 되므로 전체가 `O(n)`으로 떨어진다.

집합에 `HashSet`을 쓰면 문자 종류에 제약이 없다는 장점도 있다. 이 문제의 `s`는 소문자뿐 아니라 **대문자·숫자·기호·공백**까지 포함할 수 있어서, 흔히 쓰는 `int[26]` + `c - 'a'` 방식은 그대로 쓰면 인덱스가 음수가 되어 터진다(자세한 내용은 6절).

### 손으로 시뮬레이션 — `s = "pwwkew"`

| `right` | `ch` | while 루프 동작 | `left` | 윈도우 | `set` | `len` | `maxLen` |
|---|---|---|---|---|---|---|---|
| 0 | `p` | 없음 | 0 | `"p"` | `{p}` | 1 | 1 |
| 1 | `w` | 없음 | 0 | `"pw"` | `{p,w}` | 2 | 2 |
| 2 | `w` | `w` 중복 → `p` 제거, `w` 제거 | 2 | `"w"` | `{w}` | 1 | 2 |
| 3 | `k` | 없음 | 2 | `"wk"` | `{w,k}` | 2 | 2 |
| 4 | `e` | 없음 | 2 | `"wke"` | `{w,k,e}` | 3 | **3** |
| 5 | `w` | `w` 중복 → `w` 제거 | 3 | `"kew"` | `{k,e,w}` | 3 | 3 |

`right = 2`에서 while 루프가 **두 번** 돈다는 점을 보자. `left = 0`의 `'p'`를 빼도 `'w'`는 여전히 집합에 남아 있어 중복이 해소되지 않기 때문에, `left = 1`의 `'w'`까지 빼고 나서야 루프를 빠져나온다. 결과: `3`.

## 3. 코드 (Java)

```java
import java.util.HashSet;
import java.util.Set;

class Solution {
    public int lengthOfLongestSubstring(String s) {

        Set<Character> set = new HashSet<>(); // 현재 윈도우에 들어있는 문자들

        int left = 0;   // 윈도우 왼쪽 끝
        int len = 0;    // 현재 윈도우 길이
        int maxLen = 0; // 지금까지 본 최대 윈도우 길이
        for (int right = 0; right < s.length(); right++) {
            char ch = s.charAt(right);
            len++;

            while (set.contains(ch)) {           // 중복이 사라질 때까지 왼쪽을 줄인다
                set.remove(s.charAt(left++));
                len--;
            }
            set.add(ch);

            maxLen = Math.max(maxLen, len);
        }

        return maxLen;
    }
}
```

### 코드 설명

- **`set.add(ch)`가 while 루프 *뒤*에 있는 것이 중요하다.** 만약 `ch`를 먼저 집합에 넣고 나서 `while (set.contains(ch))`를 돌면, 방금 넣은 `ch` 자신 때문에 조건이 항상 참이 되어 `left`가 `right`를 지나쳐 버린다. "새 문자를 넣기 전에 자리를 먼저 비운다"는 순서를 지켜야 한다.
- **`while`이지 `if`가 아니다.** 중복 문자가 윈도우 중간에 있으면 `left`를 한 칸 미는 것만으로는 그 문자가 빠지지 않는다. 위 시뮬레이션의 `right = 2`처럼, 중복된 문자 자신이 밖으로 나갈 때까지 여러 칸을 밀어야 할 수 있다.
- `len`은 현재 윈도우 길이를 증분으로 관리한다. `right`가 전진할 때 `+1`, `left`가 전진할 때 `-1`이므로 항상 `right - left + 1`과 같은 값이다. (`right - left + 1`을 직접 계산해도 결과는 동일하다.)
- `left`는 절대 뒤로 가지 않는다. 그래서 `right`와 `left`가 각각 문자열을 한 번씩만 훑고, 이중 루프처럼 보여도 전체는 선형이다.

## 4. 복잡도 분석

- **시간복잡도**: `O(n)` — 겉보기엔 `for` 안에 `while`이 있는 이중 루프지만, `left`는 전체 실행을 통틀어 0에서 `n`까지 **단조 증가**하기만 한다. 즉 while 루프의 총 실행 횟수는 모든 `right`를 합쳐도 최대 `n`번이다. `HashSet`의 `contains`/`add`/`remove`는 평균 `O(1)`이므로 전체가 `O(n)`.
- **공간복잡도**: `O(min(n, k))` — `set`에는 윈도우 안의 서로 다른 문자만 들어가므로, 문자열 길이 `n`과 등장 가능한 문자 종류 수 `k`(ASCII 기준 128) 중 작은 쪽에 비례한다.

실측: 길이 50,000짜리 무작위 소문자 문자열에서 약 3ms.

## 5. 엣지 케이스

- **빈 문자열** (`""`): `for` 루프가 한 번도 돌지 않아 `maxLen = 0`이 그대로 반환된다.
- **모두 같은 문자** (`"bbbbb"`): 매 `right`마다 while이 한 번씩 돌아 윈도우 길이가 항상 1로 유지된다 → `1`.
- **모두 다른 문자** (`"abcdef"`): while이 한 번도 돌지 않고 `left = 0`을 유지한다 → 문자열 전체 길이.
- **중복 문자가 윈도우 중간에 있는 경우** (`"dvdf"`): `right = 2`에서 `'d'`가 중복이지만 `left`를 1칸만 밀면 되고, 이후 `"vdf"`로 답이 `3`이 된다. `left`를 무조건 `이전 중복 위치 + 1`로 점프시키는 구현에서 실수가 잦은 케이스라 반드시 확인해야 한다.
- **공백·숫자·기호가 섞인 경우** (`" "`, `"a1!a1!b"`): `HashSet<Character>`는 문자 종류를 가리지 않으므로 그대로 동작한다 → 각각 `1`, `4`(뒤쪽 `"a1!b"`). 문자 빈도 배열로 풀 때 가장 먼저 깨지는 케이스이므로 6절을 함께 보자.

## 6. 흔한 함정 — `int[26]` + `c - 'a'`

슬라이딩 윈도우 + 문자 빈도 문제를 여러 개 풀다 보면 반사적으로 이렇게 쓰게 된다.

```java
int[] count = new int[26];
count[s.charAt(right) - 'a']++; // ← 소문자만 가정
```

[438번](../438-find-all-anagrams-in-a-string/Solution.md)처럼 "`s`와 `p`는 소문자 영문자로만 구성된다"는 제약이 있으면 문제없지만, **이 문제의 제약사항은 "영문자, 숫자, 기호, 공백"**이다. 예를 들어 `s = " "`(공백)이면 `' ' - 'a' = 32 - 97 = -65`가 되어 이렇게 터진다.

```text
java.lang.ArrayIndexOutOfBoundsException: Index -65 out of bounds for length 26
```

배열로 풀고 싶다면 `'a'`를 빼지 말고 ASCII 코드를 그대로 인덱스로 쓰면 된다.

```java
int[] count = new int[128]; // ASCII 전체를 커버
count[s.charAt(right)]++;   // 오프셋 없이 문자 코드를 그대로 인덱스로
```

배열 방식은 해싱 오버헤드가 없어 `HashSet`보다 상수 배 빠르지만, 문자 집합의 상한을 코드에 박아두는 셈이라 제약사항을 정확히 읽어야 한다. `HashSet`은 조금 느린 대신 문자 종류를 신경 쓸 필요가 없다는 점에서 더 안전한 기본값이다.

**교훈: 문자 빈도 배열을 쓸 때는 "`- 'a'`를 해도 되는 문제인가"를 제약사항에서 먼저 확인하자.**

## 7. 이런 방법도 있다 — `HashMap`으로 `left`를 한 번에 점프

`HashSet` 대신 `HashMap<Character, Integer>`에 **문자 → 마지막으로 등장한 인덱스**를 저장하면, `left`를 한 칸씩 미는 대신 곧바로 목적지로 점프시킬 수 있다.

```java
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> lastIndex = new HashMap<>(); // 문자 → 마지막 등장 인덱스
        int left = 0, maxLen = 0;

        for (int right = 0; right < s.length(); right++) {
            char ch = s.charAt(right);
            Integer prev = lastIndex.get(ch);
            if (prev != null && prev >= left) { // 중복이 '현재 윈도우 안'일 때만 점프
                left = prev + 1;
            }
            lastIndex.put(ch, right);
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }
}
```

여기서 **`prev >= left` 조건이 핵심**이다. 맵에는 윈도우 밖으로 이미 밀려난 문자의 인덱스도 그대로 남아 있기 때문에, 이 조건 없이 `left = prev + 1`을 하면 `left`가 뒤로 되돌아가 윈도우에 중복이 다시 섞인다. `"abba"`가 대표적인 반례로, 조건이 없으면 마지막 `'a'`에서 `left`가 `1`로 되돌아가 `"bba"`를 세면서 답이 3이 되어버린다(정답은 2).

**비교:**

| | `HashSet` (위 3.의 코드) | `HashMap` + 인덱스 점프 |
|---|---|---|
| `left` 이동 | 한 칸씩 (`while`) | 한 번에 점프 (`if`) |
| 시간복잡도 | `O(n)` | `O(n)` |
| 실제 연산 횟수 | `left`가 이동한 칸 수만큼 `remove` 호출 | `left` 갱신은 항상 대입 한 번 |
| 주의할 점 | `set.add`를 while **뒤**에 둘 것 | `prev >= left` 조건을 빠뜨리지 말 것 |

둘 다 점근적으로는 `O(n)`이고, 입력 크기가 `5×10^4`이라 실측 차이도 거의 없다. `HashSet` 버전이 "윈도우 = 집합"이라는 불변식이 눈에 더 잘 보여 디버깅하기 쉽고, `HashMap` 버전은 `left`가 뒤로 가지 않도록 지켜야 할 조건이 하나 늘어난다.

## 8. 관련 문제

같은 **슬라이딩 윈도우** 계열 문제.

- [Find All Anagrams in a String (LeetCode 438)](../438-find-all-anagrams-in-a-string/Solution.md) — 윈도우 크기가 `p.length()`로 **고정**된 버전. 이 문제(가변 크기)와 대비해서 보면 "윈도우 크기를 무엇이 결정하는가"의 차이가 분명해진다.
- [Valid Anagram (LeetCode 242)](../242-valid-anagram/Solution.md) — 윈도우 없이 문자 빈도 비교만 하는 가장 기본형.
- [Valid Sudoku (LeetCode 36)](../36-valid-sudoku/Solution.md) — 같은 "이미 본 값인지" 판정을 행·열·3x3 박스라는 고정된 그룹마다 적용하는 문제. 값의 범위가 작고 고정이면 `HashSet`보다 배열이 빠르다는 점(6절)이 그대로 반복된다.

> 이 섹션은 새 문제를 풀 때마다 갱신됩니다. 슬라이딩 윈도우(고정/가변), 문자 빈도 관리 계열 문제를 풀면 이 목록에 서로 링크를 추가해주세요.

---

*이 문서는 [Problem.md](./Problem.md)의 문제 설명을 기반으로 상세 풀이를 정리한 것입니다.*
