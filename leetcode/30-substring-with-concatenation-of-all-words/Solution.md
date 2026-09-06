# [LeetCode 30] Substring with Concatenation of All Words — 잔여류별 슬라이딩 윈도우

- 문제 번호: 30
- 링크: https://leetcode.com/problems/substring-with-concatenation-of-all-words/
- 난이도: Hard
- 태그: Hash Table, String, Sliding Window
- 사용 자료구조/알고리즘: **잔여류(offset)별 슬라이딩 윈도우 + 단어 빈도 비교**

## 1. 문제 요약

문자열 `s`와, **길이가 전부 같은** 단어 배열 `words`가 주어질 때, `words`의 모든 단어를 순서 무관하게 정확히 한 번씩(중복 단어는 그 개수만큼) 이어 붙여 만들 수 있는 부분 문자열의 시작 인덱스를 전부 찾는다.

```text
예시) s="barfoothefoobarman", words=["foo","bar"] → [0, 9]
      s="wordgoodword",       words=["word","good"] → [0, 4]
```

매치 지점은 서로 겹칠 수 있다(`s="aaaaa", words=["aa"]` → `[0,1,2,3]`).

## 2. 접근 아이디어

### 정답 후보의 길이가 고정된다

`words`의 각 단어 길이를 `wordLen`이라 하면, 정답 부분 문자열의 길이는 항상 `totalLen = wordLen × words.length`로 고정이다. 그래서 `s`의 모든 시작 인덱스 `i`에 대해, `s[i, i+totalLen)`를 `wordLen`짜리 조각으로 잘라 그 조각들의 다중집합이 `words`와 같은지만 확인하면 된다.

### 겹치는 계산을 재사용한다 — 그런데 방향이 `wordLen` 단위다

매 시작 인덱스마다 처음부터 다시 세면 느리다. 대신 [76번(Minimum Window Substring)](../76-minimum-window-substring/Solution.md)·[438번(Find All Anagrams)](../438-find-all-anagrams-in-a-string/Solution.md)처럼 슬라이딩 윈도우로 겹치는 부분을 재사용할 수 있다. 다만 이 문제의 "한 칸"은 글자 하나가 아니라 **단어 하나(`wordLen`글자)**다.

- 오른쪽 끝에 새 단어를 추가하고 개수를 센다.
- 그 단어가 필요한 개수(`wordMap`)를 넘어서면, 왼쪽에서 단어를 하나씩 빼며 넘치지 않을 때까지 좁힌다.
- 윈도우 길이가 정확히 `totalLen`이 되는 순간마다 그 왼쪽 끝을 답 후보로 기록한다.

### 핵심 통찰 — 잔여류(offset)마다 독립된 윈도우가 필요하다

윈도우가 `wordLen` 단위로만 움직이므로, **시작점이 `wordLen`으로 나눈 나머지가 다르면 절대 같은 윈도우에서 만날 수 없다.** 예를 들어 `wordLen=2`일 때 인덱스 `0,2,4,...`(잔여류 0)와 `1,3,5,...`(잔여류 1)는 서로 완전히 별개의 세계다. 그래서 `s`를 딱 한 번만 슬라이딩 윈도우로 훑으면 **한 잔여류만** 검사하게 되고, 나머지 잔여류의 정답은 통째로 놓친다.

해결책은 `0`부터 `wordLen-1`까지 **모든 잔여류에 대해 슬라이딩 윈도우를 독립적으로 한 번씩 돌리는 것**이다. `wordLen`가지 잔여류 각각을 훑어도 총 작업량은 여전히 `s.length()`를 넘지 않는다 — 잔여류들이 `s`의 인덱스를 정확히 나눠 갖기 때문이다.

### 손으로 시뮬레이션 — `s="aaaaa", words=["aa"]` (`wordLen=2`)

`wordMap = {aa:1}`, `totalLen=2`.

**잔여류 0** (`i=0`): 시작 인덱스 `0, 2, 4`를 검사

| `right` | 조각 | 동작 | 답 후보 |
|---|---|---|---|
| 0 | `s[0,2)="aa"` | 개수 일치, 길이 2 → 기록 | `left=0` |
| 2 | `s[2,4)="aa"` | 왼쪽 밀고 다시 기록 | `left=2` |

**잔여류 1** (`i=1`): 시작 인덱스 `1, 3`을 검사

| `right` | 조각 | 동작 | 답 후보 |
|---|---|---|---|
| 1 | `s[1,3)="aa"` | 기록 | `left=1` |
| 3 | `s[3,5)="aa"` | 기록 | `left=3` |

두 잔여류를 합치면 `{0,1,2,3}` — 정답과 일치한다. **잔여류 0만 돌렸다면 `{0,2}`만 나와 `1`, `3`을 놓쳤을 것이다.**

## 3. 코드 (Java)

```java
import java.util.*;

class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        Set<Integer> set = new HashSet<>();
        if (words == null || words.length == 0 || s == null || s.length() < words[0].length()) return List.of();

        int wordLen = words[0].length();
        Map<String, Integer> wordMap = new HashMap<>();
        for (String word : words) {
            wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);
        }

        for (int i = 0; i < wordLen; i++) {
            Map<String, Integer> map = new HashMap<>();
            int left = i;
            for (int right = i; right < s.length() - wordLen + 1; ) {
                String word = s.substring(right, right + wordLen);

                if (wordMap.containsKey(word)) {
                    map.put(word, map.getOrDefault(word, 0) + 1);
                    right += wordLen;
                    if (map.getOrDefault(word, 0).equals(wordMap.get(word)) && right - left == wordLen * words.length) {
                        set.add(left);
                        String leftWord = s.substring(left, left + wordLen);
                        map.put(leftWord, map.get(leftWord) - 1);
                        left += wordLen;
                    }
                    while (map.get(word) > wordMap.get(word)) {
                        String leftWord = s.substring(left, left + wordLen);
                        map.put(leftWord, map.get(leftWord) - 1);
                        left += wordLen;
                    }
                } else {
                    map.clear();
                    right += wordLen;
                    left = right;
                }
            }
        }

        return set.stream().toList();
    }
}
```

### 코드 설명

- **바깥 `for (i = 0; i < wordLen; i++)`가 잔여류를 순회한다.** 안쪽 전체(윈도우, `map`, `left`)가 `i`마다 완전히 초기화되어, 각 잔여류가 독립된 슬라이딩 윈도우로 처리된다.
- `right`, `left` 모두 **`wordLen`씩만** 움직인다. 매치 실패로 윈도우를 리셋하는 `else` 분기도 `right += wordLen`이다 — `+1`로 두면 잔여류를 벗어나 버려 바깥 `i` 루프의 전제가 깨진다(6절).
- `map.getOrDefault(word, 0).equals(wordMap.get(word))`로 **`Integer` 값을 비교**한다. `==`가 아니라 `.equals()`를 쓴 이유가 6절의 핵심이다.
- `while (map.get(word) > wordMap.get(word))`는 관계 연산자(`>`)라 `Integer`가 자동으로 `int`로 언박싱되어 비교되므로 원래부터 안전하다.
- `else` 분기의 `map.clear()`는 매치에 실패한 단어를 만나면 지금까지 쌓아온 모든 걸 버리고 그 잔여류 안에서 완전히 새로 시작한다는 뜻이다.

## 4. 복잡도 분석

- **시간복잡도**: `O(n × wordLen)` — `n = s.length()`. 잔여류가 `wordLen`가지이고, 각 잔여류의 윈도우는 `O(n / wordLen)`번의 단어 단위 이동을 한다(각 이동에 `substring` 생성과 해시 연산이 `O(wordLen)`). 전체를 곱하면 `wordLen × (n/wordLen) × wordLen = O(n × wordLen)`.
- **공간복잡도**: `O(words.length × wordLen)` — `wordMap`과 각 잔여류의 `map`이 저장하는 문자열들의 총 길이.

실측: `s.length=10000, words.length=2000, wordLen=4`에서 0ms. 실제 매치가 많이 나오는 경우(`s.length=8000`, 매치 755개)도 6ms.

## 5. 엣지 케이스

- **`s`가 `words`의 총 길이보다 짧음**: 첫 줄의 가드에서 걸러지거나, 루프가 한 번도 조건을 만족 못 해 빈 리스트.
- **매치가 겹침** (`"aaaaa"`, `["aa"]`): 정답 `[0,1,2,3]`이 서로 겹치는 구간이다. `Set`으로 모으므로 중복 걱정은 없다.
- **단어 중복** (`words`에 같은 단어가 여러 번): `wordMap`이 이미 개수까지 정확히 세므로 자연스럽게 처리된다.
- **`wordLen=1`**: 잔여류가 하나(`0`)뿐이라 바깥 루프가 한 번만 돈다 — 일반적인(글자 단위) 슬라이딩 윈도우와 동일해진다.

검증: 무작위 20,000건을 브루트포스와 교차 비교해 전부 일치했다.

## 6. 겪었던 함정들

이 문제를 풀며 실제로 두 단계의 버그를 거쳤다.

### (1) 잔여류(offset) 순회를 빠뜨림

처음 버전은 바깥 `i` 루프 없이 `left=right=0`에서 시작하는 슬라이딩 윈도우 하나만 돌렸다.

```java
int left = 0;
for (int right = 0; right < s.length() - wordLen + 1; ) { ... }
```

`s="aaaaa", words=["aa"]`(정답 `[0,1,2,3]`)에서 `[0,2]`만 나왔다. 모든 조각이 계속 매치에 성공하니 `right`가 `0→2→4`로만 움직이고, **잔여류가 `1`인 위치(`1,3`)는 단 한 번도 검사되지 않았다.**

```text
right가 wordLen씩만 뛰면서, 시작한 잔여류를 절대 벗어나지 못한다.
"실패했을 때만" +1로 움직이는 로직이 있었지만, 이 예시처럼 실패가 한 번도 없으면
그 기회조차 생기지 않는다.
```

`0`부터 `wordLen-1`까지 전체를 감싸는 바깥 루프를 추가해, 각 잔여류를 독립적으로 전부 검사하도록 고쳤다(3절의 최종 코드).

### (2) `Integer`를 `==`로 비교함

잔여류 버그를 고친 뒤에도, **특정 단어가 128번 이상 반복되는 규모**에서만 드러나는 두 번째 버그가 있었다.

```java
if (map.getOrDefault(word, 0) == wordMap.get(word) && ...) {
```

`map.getOrDefault(word, 0)`과 `wordMap.get(word)`는 둘 다 박싱된 `Integer` 객체다. `==`는 값이 아니라 **객체 참조**를 비교한다. Java는 `-128~127` 범위의 `Integer`를 캐시해 재사용하지만, 그 범위를 벗어나면 `Integer.valueOf(128)`을 호출할 때마다 매번 새 객체를 만든다.

```java
Integer a = 127, b = 127;
a == b;   // true  (캐시된 같은 객체)

Integer c = 128, d = 128;
c == d;   // false (값은 같지만 서로 다른 객체!)
```

그 결과, 같은 단어가 정확히 `128`번 이상 필요한 경우(`s`와 `words`를 전부 `"a"` `5000`개로 채운 극단 케이스에서 발견됨) `map`과 `wordMap`의 값이 실제로는 같은 `128` 이상인 정수인데도 `==`가 항상 `false`를 반환해, **"단어를 다 모았다"는 조건을 영원히 인식하지 못했다.** 이진 탐색으로 정확한 경계를 좁혀보니 `n=127`까지는 정상, `n=128`부터 깨졌다 — `Integer` 캐시 범위의 경계와 정확히 일치했다.

```text
n=100  → [0]   (정상, 캐시 범위 안)
n=127  → [0]   (정상, 캐시 범위 안)
n=128  → []    (버그, 캐시 범위 밖 — 여기서부터 처음 깨짐)
n=5000 → []    (여전히 버그)
```

`==`를 `.equals()`로 바꾸자 모든 크기에서 정확해졌다.

**교훈: `Integer`(또는 다른 박싱 타입)끼리 `==`로 비교하면 안 된다.** 작은 값에서는 캐시 덕분에 우연히 맞아떨어져서, 준비된 예제나 값이 작은 무작위 테스트로는 **절대** 잡히지 않는다. 이번에도 20,000건 무작위 퍼징이 전부 통과한 뒤에야, "특정 단어의 반복 횟수를 점점 늘려보는" 규모 테스트에서 처음 드러났다. **값의 크기가 결과에 영향을 줄 수 있는 로직이라면, 작은 값뿐 아니라 몇백 단위로도 반드시 확인해야 한다.**

## 7. 관련 문제

같은 **슬라이딩 윈도우 + 빈도 비교** 계열이지만, 비교 단위가 다르다.

- [Find All Anagrams in a String (LeetCode 438)](../438-find-all-anagrams-in-a-string/Solution.md) — 고정 크기 윈도우로 **문자** 빈도를 비교한다. 이 문제는 같은 아이디어를 **단어** 단위로 확장한 버전이라 볼 수 있고, 그래서 "한 칸"이 글자 하나가 아니라 단어 하나가 되면서 잔여류라는 새로운 개념이 필요해졌다.
- [Minimum Window Substring (LeetCode 76)](../76-minimum-window-substring/Solution.md) — 가변 크기 윈도우로 문자 빈도를 맞춘다. 이 문제는 윈도우 크기가 `totalLen`으로 고정이라는 점이 다르다.
- [Minimum Size Subarray Sum (LeetCode 209)](../209-minimum-size-subarray-sum/Solution.md) — 같은 "늘리고 줄이는" 슬라이딩 윈도우 뼈대지만, 조건이 합계라는 점에서 결이 다르다.

> 이 섹션은 새 문제를 풀 때마다 갱신됩니다. 슬라이딩 윈도우, 단어/문자 빈도 비교 계열 문제를 풀면 이 목록에 서로 링크를 추가해주세요.

---

*이 문서는 [Problem.md](./Problem.md)의 문제 설명을 기반으로 상세 풀이를 정리한 것입니다.*
