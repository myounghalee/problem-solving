# [LeetCode 17] Letter Combinations of a Phone Number — 조합을 한 자리씩 늘려가며 만들기 (BFS)

- 문제 번호: 17
- 링크: https://leetcode.com/problems/letter-combinations-of-a-phone-number/
- 난이도: Medium
- 태그: Hash Table, String, Backtracking
- 사용 자료구조/알고리즘: **반복적 조합 생성(BFS 방식) + 숫자→문자 매핑 배열**

## 1. 문제 요약

`2`~`9`로 이루어진 문자열 `digits`가 주어질 때, 전화기 자판에서 그 숫자들이 만들 수 있는 **모든 문자 조합**을 반환한다. 순서는 상관없다.

```text
예시) "23" → ["ad","ae","af","bd","be","bf","cd","ce","cf"]   (3 × 3 = 9가지)
      "2"  → ["a","b","c"]
      ""   → []
```

각 자리에서 문자를 하나씩 고르는 것이므로, 답의 개수는 **각 숫자가 가진 문자 수의 곱**이다. `digits`의 길이가 최대 4이고 `7`·`9`가 문자 4개씩을 가지므로, 최대 크기는 `4^4 = 256`가지다.

## 2. 접근 아이디어

"각 자리에서 하나씩 고른다"는 구조라 재귀 백트래킹이 가장 먼저 떠오르지만, **반복문으로 조합을 한 자리씩 키워나가는 방식**이 더 직관적일 수 있다. 트리를 깊이 우선으로 내려가는 대신, **레벨 단위로 넓혀가는 BFS**에 가깝다.

핵심 아이디어는 이렇다.

- 지금까지 만든 조합 목록 `res`를 들고 있는다. 시작은 `[""]` — **아직 아무 문자도 고르지 않은 조합 하나**다.
- `digits`를 왼쪽부터 한 글자씩 처리한다. 현재 숫자가 가진 문자 하나하나를 `res`의 **모든 기존 조합 뒤에 붙여서** 새 목록 `temp`를 만든다.
- `res`를 `temp`로 교체한다. 이제 `res`의 모든 조합은 길이가 1 늘어난 상태다.
- `digits`를 다 처리하면 `res`가 답이다.

즉 매 단계마다 `res`의 크기가 "현재 숫자의 문자 수"만큼 배로 늘어난다. `"23"`이면 `1개 → 3개 → 9개`가 된다.

### 시작값이 왜 `[""]`인가

`res`를 빈 리스트 `[]`로 시작하면 안 된다. 안쪽 루프가 `res`의 원소를 순회하며 문자를 붙이는데, 원소가 하나도 없으면 붙일 대상이 없어 `temp`가 계속 비고 최종 결과도 빈 리스트가 된다.

`[""]`는 "길이 0짜리 조합이 하나 있다"는 뜻이다. 곱셈의 항등원이 1인 것과 같은 역할로, 여기에 `'a'`를 붙이면 `"a"`가 되면서 첫 단계가 자연스럽게 굴러간다.

### 손으로 시뮬레이션 — `digits = "23"`

`alphabet[2] = "abc"`, `alphabet[3] = "def"`

| 단계 | 처리 중인 숫자 | 붙이는 문자 | `res` (단계 종료 후) |
|---|---|---|---|
| 시작 | — | — | `[""]` |
| 1 | `2` | `a`, `b`, `c`를 `""`에 붙임 | `["a", "b", "c"]` |
| 2 | `3` | `d`를 a/b/c에, `e`를 a/b/c에, `f`를 a/b/c에 | `["ad","bd","cd","ae","be","ce","af","bf","cf"]` |

결과는 9개로 정답과 일치한다. 다만 순서가 예시(`["ad","ae","af",...]`)와 다른데, 이는 **바깥 루프가 "문자", 안쪽 루프가 "기존 조합"**이기 때문이다. 문제에서 순서는 상관없다고 명시했으므로 그대로 정답 처리된다. 예시와 같은 순서를 원하면 두 루프를 맞바꾸면 된다.

## 3. 코드 (Java)

```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<String> letterCombinations(String digits) {

        // 인덱스 = 숫자. 0, 1은 대응 문자가 없어 null
        String[] alphabet = new String[]{null, null, "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

        List<String> res = new ArrayList<>();
        if (digits.isEmpty()) return res; // 빈 입력의 답은 [""]가 아니라 []
        res.add(""); // 길이 0짜리 조합 하나에서 시작

        for (char digit : digits.toCharArray()) {
            List<String> temp = new ArrayList<>(); // 이번 자리까지 확장한 조합들
            for (int i = 0; i < alphabet[digit - '0'].length(); i++) {
                char ch = alphabet[digit - '0'].charAt(i);
                for (int j = 0; j < res.size(); j++) {
                    temp.add(res.get(j) + ch); // 기존 조합 뒤에 문자 하나 붙이기
                }
            }
            res.clear();
            res.addAll(temp);
        }

        return res;
    }
}
```

### 코드 설명

- `alphabet`은 **인덱스를 숫자 그대로 쓰는 배열**이다. `digit - '0'`으로 문자 `'2'`를 정수 `2`로 바꿔 바로 인덱싱한다. `HashMap<Character, String>`을 써도 되지만, 키가 `2`~`9`로 연속된 작은 정수라면 배열이 더 단순하고 빠르다. 쓰이지 않는 0번·1번 칸을 `null`로 채워 인덱스를 숫자와 일치시킨 덕분에 오프셋 계산이 필요 없다.
- 바깥 `for`는 `digits`의 각 자리를, 가운데 `for`는 그 숫자의 문자들을, 안쪽 `for`는 지금까지 만들어둔 조합들을 순회한다. 삼중 루프지만 **전체 작업량은 결국 최종 결과의 개수에 비례**한다.
- `temp`를 따로 만드는 이유는, `res`를 순회하면서 동시에 `res`에 추가하면 방금 추가한 원소를 다시 순회하게 되어 무한히 늘어나기 때문이다(혹은 `ConcurrentModificationException`). 이번 단계 결과를 별도 리스트에 모은 뒤 통째로 교체해야 한다.

## 4. 복잡도 분석

`digits`의 길이를 `n`, 각 숫자가 가진 문자 수의 최댓값을 `m`(여기서는 4)이라 하자.

- **시간복잡도**: `O(n · m^n)` — 최종 조합의 개수가 최대 `m^n`개이고, 각 조합을 만들 때 문자열 이어붙이기(`res.get(j) + ch`)에 길이에 비례하는 `O(n)` 비용이 든다. 제약상 `n ≤ 4`, `m ≤ 4`라 최대 256개 조합에 불과하다.
- **공간복잡도**: `O(n · m^n)` — 결과 리스트가 차지하는 공간. 중간의 `temp`도 같은 규모지만 상수 배다.

문자열 조합을 전부 나열하는 문제라 **출력 크기 자체가 `m^n`**이므로, 어떤 알고리즘을 쓰든 이보다 빠를 수 없다. 즉 이 복잡도는 최적이다.

## 5. 엣지 케이스

- **빈 문자열** (`""`): `[]`를 반환해야 한다. `[""]`(빈 문자열 하나가 든 리스트)와는 다르다.
- **숫자 하나** (`"2"`): 첫 단계만 돌고 `["a","b","c"]`가 된다.
- **문자가 4개인 숫자** (`7`, `9`): 자판에서 `7 = pqrs`, `9 = wxyz`로 문자가 3개가 아니라 4개다. `"79"`는 16가지, `"7979"`는 256가지가 나온다.
- **최대 길이** (`"7979"`): 답이 256개로 이 문제의 최대 출력이다.

검증: 제약 범위 안에서 가능한 모든 입력(길이 0~4, `2`~`9` 조합 총 **4,681가지**)을 재귀 백트래킹으로 만든 기준 답과 전수 비교해 전부 일치함을 확인했다.

## 6. 흔한 함정 두 가지

이 문제에서 실수하기 쉬운 두 지점은 **둘 다 테스트 출력만 봐서는 잡히지 않는다.**

### (1) 빈 입력이 `[""]`를 반환하는 문제

`res`를 `[""]`로 초기화한 뒤 `if (digits.isEmpty()) return res;` 가드를 빼먹으면, `digits`가 비었을 때 `for` 루프가 한 번도 돌지 않아 초기값 `""`가 그대로 남는다. 문제는 이걸 출력해도 **눈으로 구분이 안 된다**는 것이다.

```text
System.out.println(res);   // 출력: []
res.size();                // 실제로는 1  ← 원소가 있다!
```

`println`이 리스트 괄호 `[`, `]` 사이에 빈 문자열을 찍으니 `[]`로 보인다. LeetCode 채점기는 `[""]`와 `[]`를 정확히 구분하므로 여기서 틀린다. **리스트에 빈 문자열이 들어갈 수 있는 문제에서는 `size()`를 함께 확인**하는 습관이 필요하다.

### (2) 자판 매핑 오타

`alphabet` 배열은 손으로 적는 하드코딩 데이터라 오타가 나기 쉽다. 특히 문자가 4개인 `7 = pqrs`와 `9 = wxyz`, 그리고 `8 = tuv`가 자주 틀린다(`"tus"`처럼).

이런 오타는 **그 숫자를 쓰는 테스트 케이스가 없으면 절대 드러나지 않는다.** 문제의 예시(`"23"`, `"2"`, `""`)만으로는 `4`~`9`를 한 번도 검증하지 않는다. 그래서 아래처럼 **모든 숫자를 한 번씩 훑는 테스트**를 직접 넣어야 한다.

```java
for (char d = '2'; d <= '9'; d++) {
    System.out.println(d + " → " + sol.letterCombinations(String.valueOf(d)));
}
// 2 → [a, b, c]      3 → [d, e, f]      4 → [g, h, i]      5 → [j, k, l]
// 6 → [m, n, o]      7 → [p, q, r, s]   8 → [t, u, v]      9 → [w, x, y, z]
```

**교훈: 하드코딩 테이블이 있는 문제는 테이블의 모든 항목을 한 번씩 지나가는 테스트를 먼저 짜자.**

## 7. 이런 방법도 있다 — 재귀 백트래킹

같은 문제를 재귀로 풀면, 조합을 통째로 들고 다니는 대신 `StringBuilder` 하나를 공유하며 **문자를 붙였다 뗐다** 한다. 이 문제의 태그가 `Backtracking`인 이유다.

```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    private static final String[] MAP = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        if (digits.isEmpty()) return res;
        backtrack(digits, 0, new StringBuilder(), res);
        return res;
    }

    private void backtrack(String digits, int idx, StringBuilder sb, List<String> res) {
        if (idx == digits.length()) { // 모든 자리를 다 골랐으면 완성된 조합
            res.add(sb.toString());
            return;
        }
        for (char ch : MAP[digits.charAt(idx) - '0'].toCharArray()) {
            sb.append(ch);                            // 고르고
            backtrack(digits, idx + 1, sb, res);      // 다음 자리로 내려가고
            sb.deleteCharAt(sb.length() - 1);         // 되돌린다 (백트래킹)
        }
    }
}
```

`sb.deleteCharAt(...)`로 **되돌리는 것이 백트래킹의 핵심**이다. 이게 없으면 `sb`에 문자가 계속 쌓여 다음 분기가 오염된다.

**비교:**

| | 반복 (위 3.의 코드, BFS) | 재귀 백트래킹 (DFS) |
|---|---|---|
| 탐색 순서 | 레벨 단위로 넓게 | 한 갈래를 끝까지 깊게 |
| 중간 메모리 | 현재 레벨의 조합 전부(`res` + `temp`) | 경로 하나(`StringBuilder`)만 |
| 결과 순서 | 루프 순서에 따라 달라짐 | 예시와 같은 사전순 |
| 문자열 생성 | 매 단계 새 문자열 이어붙이기 | 마지막에 `toString()` 한 번씩 |
| 부분 가지치기 | 어려움 | 쉬움 (조건 걸고 `return`) |

`n ≤ 4`라 실측 차이는 무의미하다. 다만 **중간 단계에서 조건을 걸어 가지를 쳐내야 하는 문제**(예: 사전에 없는 접두사면 중단)로 확장될 때는 재귀 쪽이 훨씬 자연스럽다. 반복 방식은 이미 만들어진 조합 전체를 들고 있어야 해서, 유망하지 않은 조합도 일단 다 만들어놓고 걸러야 한다.

## 8. 관련 문제

"모든 경우를 빠짐없이 나열한다"는 조합 탐색 계열의 첫 문제라, 아직 상호 링크할 풀이가 없다. 이 저장소에서 지금까지 푼 [3번](../3-longest-substring-without-repeating-characters/Solution.md)·[438번](../438-find-all-anagrams-in-a-string/Solution.md) 같은 문자열 문제는 "하나의 최적값"을 구했지만, 이 문제는 **답을 전부 나열해야 해서 출력 크기 자체가 복잡도의 하한**이 된다는 점이 다르다.

> 이 섹션은 새 문제를 풀 때마다 갱신됩니다. 백트래킹, 조합/순열 생성 계열 문제를 풀면 이 목록에 서로 링크를 추가해주세요.

---

*이 문서는 [Problem.md](./Problem.md)의 문제 설명을 기반으로 상세 풀이를 정리한 것입니다.*
