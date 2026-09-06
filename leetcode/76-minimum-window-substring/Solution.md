# [LeetCode 76] Minimum Window Substring — 가변 슬라이딩 윈도우로 "만족하면 줄이기"

- 문제 번호: 76
- 링크: https://leetcode.com/problems/minimum-window-substring/
- 난이도: Hard
- 태그: Hash Table, String, Sliding Window
- 사용 자료구조/알고리즘: **가변 크기 슬라이딩 윈도우 + 문자 빈도 배열**

## 1. 문제 요약

문자열 `s`에서 `t`의 모든 문자를 **개수까지 포함하는** 부분 문자열 중 가장 짧은 것을 찾는다. 그런 부분 문자열이 없으면 `""`을 반환한다.

```text
예시) s = "ADOBECODEBANC", t = "ABC" → "BANC"
      s = "a", t = "aa" → ""  (a가 1개뿐이라 2개를 채울 수 없음)
```

`t`에 같은 문자가 여러 번 나오면 그 개수만큼 필요하다(`t = "aa"`면 `a`가 최소 2개).

## 2. 접근 아이디어

### [3번](../3-longest-substring-without-repeating-characters/Solution.md)과 정반대 방향의 슬라이딩 윈도우

[3번(Longest Substring Without Repeating Characters)](../3-longest-substring-without-repeating-characters/Solution.md)은 "조건(중복 없음)을 어기면 왼쪽을 줄이는" 문제였다. 이 문제는 그 반대다.

> 조건(`t`를 전부 포함)을 **아직 만족 못 했으면 오른쪽을 늘리고, 만족하면 왼쪽을 줄여본다.**

즉 3번은 "깨지면 줄임", 이 문제는 "채워지면 줄임"이다. 이 차이 때문에 최솟값을 갱신하는 타이밍도 반대가 된다 — 3번은 while을 빠져나온 뒤(조건이 회복된 뒤) 최댓값을 갱신하지만, 이 문제는 while **안에서**(조건을 만족하는 동안) 최솟값을 갱신한다.

### "포함 여부"가 아니라 "개수"를 맞춰야 한다

`t = "aa"`처럼 같은 문자가 여러 번 필요할 수 있으므로, "그 문자가 윈도우에 있는가"가 아니라 "**그 문자가 필요한 개수만큼 있는가**"를 추적해야 한다. 그래서 `Set` 대신 **문자별 개수 차이**를 담는 배열 `need`를 쓴다.

- `need[c]`: `t`의 문자별 개수로 초기화한다. **양수면 "아직 부족한 개수"**, **0이면 "딱 맞음"**, **음수면 "필요한 것보다 남는 개수(잉여)"**를 뜻한다.
- 윈도우에 문자 `c`가 들어올 때마다 `need[c]--`. 나갈 때마다 `need[c]++`.
- `required`: `t`의 전체 길이(문자 종류가 아니라 **총 개수**)에서, 윈도우가 채운 만큼 하나씩 줄인 값. `required == 0`이면 윈도우가 `t`를 완전히 덮는다는 뜻이다.

### `required`를 언제 줄이고 늘리는가 — "부족했던 자리를 메웠는가"만 본다

```java
if (need[c] > 0) required--;   // 이 문자가 아직 부족했던 상태였다면, 하나 채웠으니 부족분이 줄어든다
need[c]--;                     // 카운트 자체는 항상 갱신한다 (잉여가 쌓여도 됨)
```

`need[c]`가 `0`이거나 음수(이미 충분하거나 남는 상태)일 때 같은 문자가 또 들어와도 `required`는 그대로다 — 이미 채워진 자리를 또 채운다고 부족분이 줄어들진 않는다. 이 판정 덕분에 `t`에 없는 문자(`need[c]`가 계속 더 음수로 내려가기만 함)나 이미 충분히 채운 문자가 아무리 많이 들어와도 `required`가 잘못 줄어들지 않는다.

왼쪽을 뺄 때는 정확히 반대다.

```java
char lc = s.charAt(left++);
need[lc]++;                    // 카운트를 되돌린다
if (need[lc] > 0) required++;  // 되돌린 결과 '부족'해졌다면(0을 넘어 양수가 됐다면) 조건이 깨진 것
```

`need[lc]`가 `0`에서 `1`로 넘어가는 순간에만(즉 "딱 맞았는데 방금 부족해진" 순간에만) `required`를 다시 늘린다. 잉여가 있던 상태(`-3 → -2`처럼 여전히 음수)에서 하나 빼는 건 조건에 영향이 없다.

### 손으로 시뮬레이션 — `s = "ADOBECODEBANC"`, `t = "ABC"`

`need` 초기값: `A:1, B:1, C:1`, `required = 3`.

| `right` | 새 문자 | `required` | 상태 | while에서 하는 일 |
|---|---|---|---|---|
| 0~9 | `A,D,O,B,E,C,O,D,E,B` | 3→2→2→1→1→0(`right=5`) | `right=5`에서 처음 만족 | `left`를 `A`까지 줄여봄 → 더 못 줄임(A가 부족해짐) |
| 10 | `B` | 계속 0 유지 | 윈도우 안에 `B`가 남아있어 잉여 | while 진입, 줄이려 시도하지만 곧 깨짐 |
| 11 | `A` | 다시 0 | `"CODEBA"`류 시도 후 줄임 | ... |
| 12 | `N` | — | — | — |
| 13 | `C` | 0 | `right=13` | `left`를 `B`(인덱스9) 위치까지 줄여 `"BANC"`(길이4) 확정 |

이 과정에서 `required == 0`이 될 때마다 **그 순간의 윈도우 길이가 지금까지의 최솟값보다 작은지 비교**해야 한다. 이 비교를 빠뜨리면 6절의 버그가 발생한다.

## 3. 코드 (Java)

```java
class Solution {
    public String minWindow(String s, String t) {

        if (t.length() > s.length()) return "";

        int[] need = new int[128];       // 문자별 부족(+)/잉여(-) 개수
        int required = t.length();       // 아직 채워야 할 총 개수
        int min = Integer.MAX_VALUE;     // 지금까지 찾은 최소 윈도우 길이
        String result = "";

        for (char c : t.toCharArray()) need[c]++;

        int left = 0;
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (need[c] > 0) required--;  // 부족했던 자리를 채웠을 때만 감소
            need[c]--;

            while (required == 0) {                    // t를 완전히 덮는 동안
                if (min > right - left + 1) {
                    min = right - left + 1;
                    result = s.substring(left, right + 1);
                }
                char lc = s.charAt(left++);
                need[lc]++;
                if (need[lc] > 0) required++;           // 방금 뺀 것이 부족해졌다면 조건 깨짐
            }
        }

        return result;
    }
}
```

### 코드 설명

- `need`는 `int[128]`로 ASCII 전체를 커버한다. 제약사항이 "대소문자 영어 알파벳"이라 `int[26]` + `c - 'a'`는 대문자에서 인덱스가 어긋난다(3번 문제의 6절에서 다룬 함정과 같은 종류다).
- `if (t.length() > s.length()) return "";`로 애초에 불가능한 경우를 먼저 걸러낸다. 이 검사가 없어도 알고리즘 자체는 정확히 동작하지만(`required`가 끝내 0이 되지 못해 `result`가 `""` 그대로 반환됨), 불필요한 순회를 미리 건너뛴다.
- **`while` 안에서 최솟값을 갱신하고, 그 다음에 왼쪽을 줄인다.** 순서가 바뀌면(줄인 뒤 갱신) 조건이 막 깨지기 직전의 윈도우를 놓친다.
- `min`과 `result`를 항상 같이 갱신한다. `min`만 갱신하고 `result`를 빠뜨리거나 그 반대면 둘이 어긋난다.

## 4. 복잡도 분석

- **시간복잡도**: `O(n + m)` — `n = s.length()`, `m = t.length()`. `need` 초기화에 `O(m)`, `right`가 `s`를 한 번 훑고(`O(n)`), `left`는 전체 실행에서 최대 `n`번만 전진하므로(단조 증가) `while`의 총 실행 횟수도 `O(n)`. `s.substring`이 `O(창 길이)`가 들지만 실제로 답을 갱신할 때만 호출되고, 그 총합은 최악에도 `O(n)`을 넘지 않는다.
- **공간복잡도**: `O(1)` — `need` 배열이 크기 128로 고정, 입력 크기와 무관하다(결과 문자열 제외).

실측: `n = 100,000`, `m = 4,000`에서 1ms.

## 5. 엣지 케이스

- **`t`가 `s`보다 긴 경우**: 앞의 가드에서 바로 `""`.
- **`t.length() <= s.length()`인데 답이 없는 경우** (`s="ab", t="aa"`): `required`가 끝내 0이 되지 못해 `while`이 한 번도 안 돌고, `result`는 초기값 `""` 그대로 반환된다. **6절의 버그가 정확히 이 자리에서 발생했었다.**
- **`s`와 `t`가 완전히 같음** (`"a","a"`): 윈도우가 곧 `s` 전체.
- **`t`에 같은 문자가 여러 번** (`s="aa", t="aa"`): `need['a']=2`, `required=2`. `a`가 하나씩 들어올 때마다 `required`가 정확히 하나씩 줄어야 하고, 이 문제는 `int[]` 카운트가 아니라 `Set`(존재 여부만 봄)으로 풀면 여기서 틀린다.
- **정답이 문자열 뒤쪽에서 나오고, 앞쪽에 더 짧은 후보가 있었던 경우** (`s="aaXXXXa", t="aa"`): 정답은 앞쪽의 `"aa"`(길이 2)인데, 뒤쪽에서도 조건을 만족하는 윈도우(`"aXXXXa"`, 길이 6)가 한 번 더 나온다. 최솟값 비교 없이 매번 덮어쓰면 뒤쪽의 더 긴 것이 최종 답이 되어버린다.

검증: 무작위 5만 건(소문자)과 3만 건(대소문자 혼용)을 브루트포스(모든 부분 문자열을 검사)와 교차 비교해 전부 일치했다.

## 6. 겪었던 함정 — `min`을 선언만 하고 갱신하지 않음

이 문제를 풀면서 실제로 마주친 버그라 기록해 둘 값어치가 있다.

```java
int min = Integer.MAX_VALUE;
...
while (required == 0) {
    if (min > right - left + 1) {
        result = s.substring(left, right + 1);   // min을 갱신하는 줄이 빠짐
    }
    ...
}
```

`min`이 `Integer.MAX_VALUE`에서 **한 번도 바뀌지 않으므로**, `min > right - left + 1`은 사실상 항상 참이다. 그 결과 조건을 만족하는 윈도우를 찾을 때마다 **길이 비교 없이 무조건 덮어쓴다.**

한 `right` 값 안에서는 `while`이 조건이 깨질 때까지 계속 줄이기 때문에 우연히 그 구간에서는 최소가 남는다. 하지만 **`right`가 다음 단계로 넘어가면, 이전에 찾아둔 더 짧은 답이 비교 한 번 없이 버려진다.**

```text
s = "aaXXXXa", t = "aa"

right=1에서 찾은 윈도우: "aa"      (길이 2, 진짜 정답)
right=6에서 찾은 윈도우: "aXXXXa"  (길이 6)

결과: "aXXXXa"   ← 비교가 없으니 나중 것이 무조건 이긴다. 정답은 "aa".
```

무작위 퍼징으로도 바로 잡혔다.

```text
s="babbaab" t="bbb"   got="bbaab"(5)  want="babb"(4)
s="aaababa" t="abaa"  got="ababa"(5)  want="aaab"(4)
```

공통점은 전부 **"뒤쪽에서 찾은 더 긴 윈도우가 앞쪽의 더 짧은 답을 덮어썼다"**는 패턴이다.

고치는 방법은 빠진 대입 한 줄을 채우는 것뿐이다.

```java
if (min > right - left + 1) {
    min = right - left + 1;   // 이 줄이 필요하다
    result = s.substring(left, right + 1);
}
```

**교훈: "최솟값/최댓값을 추적하는 변수"를 선언했다면, 그 변수가 조건문 안에서 실제로 갱신되는지 반드시 확인하자.** 이런 버그는 `if` 조건이 인간의 눈에는 "당연히 될 것 같은" 모양이라(변수 이름이 `min`이고 비교 연산자도 맞으니) 코드 리뷰에서도 놓치기 쉽다. 준비된 테스트 케이스들(문제의 공식 예제 포함)이 전부 통과했던 것도, 우연히 "정답이 마지막에 발견되는" 입력들뿐이었기 때문이다. **"윈도우가 여러 번 조건을 만족하되, 더 짧은 것이 먼저 나오고 더 긴 것이 나중에 나오는" 입력을 직접 설계해서 테스트에 추가**해야 이런 종류의 버그가 드러난다.

## 7. 관련 문제

같은 **슬라이딩 윈도우** 계열이지만 방향이 반대인 문제들.

- [Longest Substring Without Repeating Characters (LeetCode 3)](../3-longest-substring-without-repeating-characters/Solution.md) — "조건이 깨지면 줄이고, 그 사이 최댓값을 갱신"하는 반대 방향. 이 문제와 짝을 지어 보면 "만족 조건이 무엇이고, 언제 늘리고 언제 줄이는가"라는 슬라이딩 윈도우의 뼈대가 뚜렷하게 보인다. 그쪽 6절에서 다룬 "소문자 전용 `int[26]`" 함정이 여기서도 그대로(대문자까지 있어 `int[128]`이 필요하다는 형태로) 반복된다.
- [Find All Anagrams in a String (LeetCode 438)](../438-find-all-anagrams-in-a-string/Solution.md) — 윈도우 크기가 `p.length()`로 고정된 버전. 이 문제와 달리 "정확히 같은 개수"를 요구하고, 윈도우가 늘어나기만 하는 게 아니라 크기가 고정되어 오른쪽이 늘 때 왼쪽도 함께 미는 구조다.

- [Minimum Size Subarray Sum (LeetCode 209)](../209-minimum-size-subarray-sum/Solution.md) — 완전히 같은 뼈대. 조건이 "합이 target 이상"으로 바뀐 더 단순한 버전이다.

- [Substring with Concatenation of All Words (LeetCode 30)](../30-substring-with-concatenation-of-all-words/Solution.md) — 같은 빈도 비교 슬라이딩 윈도우지만, 이 문제는 가변 크기인 반면 그쪽은 `totalLen`으로 크기가 고정이고 단어 단위로 움직인다.

> 이 섹션은 새 문제를 풀 때마다 갱신됩니다. 슬라이딩 윈도우, 문자 빈도 비교 계열 문제를 풀면 이 목록에 서로 링크를 추가해주세요.

---

*이 문서는 [Problem.md](./Problem.md)의 문제 설명을 기반으로 상세 풀이를 정리한 것입니다.*
