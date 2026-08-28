# [LeetCode 242] Valid Anagram — 세 가지 방법으로 비교하기

- 문제 번호: 242
- 링크: https://leetcode.com/problems/valid-anagram/
- 난이도: Easy
- 태그: Hash Table, String, Sorting
- 사용 자료구조/알고리즘: **정렬 비교 / 고정 크기 배열 / 해시맵 — 세 가지 방식 비교**

## 1. 문제 요약

두 문자열 `s`, `t`가 주어질 때 `t`가 `s`의 애너그램(문자 구성은 같고 순서만 다른 문자열)이면 `true`, 아니면 `false`를 반환한다.

```text
예시) s = "anagram", t = "nagaram" → true
      s = "rat",      t = "car"     → false
```

Follow-up: 입력이 유니코드 문자를 포함한다면 풀이를 어떻게 바꿔야 할까?

## 2. 접근 아이디어 — 핵심은 하나, 구현은 세 가지

애너그램 판정의 본질은 딱 하나다: **"두 문자열이 같은 문자를 같은 개수만큼 갖고 있는가?"** 이 하나의 아이디어를 구현하는 방법은 여러 가지가 있고, 이번엔 세 가지를 다 짜보고 비교했다.

1. **정렬 비교**: 두 문자열을 각각 정렬하면, 애너그램인 두 문자열은 정렬 후 완전히 같은 문자열이 된다.
2. **고정 크기 배열(`int[26]`)**: 소문자 알파벳만 나온다는 제약을 이용해, 문자별 등장 횟수를 배열 인덱스(`c - 'a'`)에 직접 세어 비교한다.
3. **해시맵**: 문자별 등장 횟수를 `Map<Character, Integer>`에 세어 비교한다. 배열과 원리는 같지만 문자 종류가 미리 정해져 있지 않아도 동작한다.

## 3. 코드 (Java) — 세 가지 방법

### 방법 1: 정렬 비교

```java
public boolean isAnagram(String s, String t) {
    char[] str = s.toCharArray();
    Arrays.sort(str);

    char[] target = t.toCharArray();
    Arrays.sort(target);
    return Arrays.equals(str, target); // 길이가 다르면 여기서 바로 false
}
```

두 문자열을 각각 문자 배열로 바꿔 정렬한 뒤, 배열 전체가 같은지 비교한다. `Arrays.equals`는 길이가 다르면 즉시 `false`를 반환하므로, 길이 체크를 따로 할 필요가 없다.

### 방법 2: 고정 크기 배열(`int[26]`)

```java
public boolean isAnagram(String s, String t) {
    int[] str = new int[26]; // 알파벳 26개 각각의 등장 횟수
    for (char c : s.toCharArray()) str[c - 'a']++;

    int[] target = new int[26];
    for (char c : t.toCharArray()) target[c - 'a']++;
    return Arrays.equals(str, target);
}
```

`c - 'a'`로 문자를 `0~25` 인덱스로 변환해 등장 횟수를 센다. 길이가 다르면 두 배열의 총합(= 각 문자열 길이)이 달라지므로, 배열 내용이 완전히 같을 수 없어 자동으로 `false`가 나온다.

### 방법 3: 해시맵

```java
public boolean isAnagram(String s, String t) {
    Map<Character, Integer> str = new HashMap<>();
    for (char c : s.toCharArray()) str.put(c, str.getOrDefault(c, 0) + 1);

    Map<Character, Integer> target = new HashMap<>();
    for (char c : t.toCharArray()) target.put(c, target.getOrDefault(c, 0) + 1);
    return str.equals(target);
}
```

배열 대신 해시맵에 문자별 개수를 센다. `Map.equals()`는 두 맵이 **같은 키를 가지고, 각 키의 값도 같을 때만** `true`를 반환하므로, 방법 2와 똑같은 판정을 해준다.

## 4. 손으로 시뮬레이션 — `s = "rat", t = "car"` (방법 2 기준)

| 문자 | `str`(s의 카운트) | `target`(t의 카운트) |
|---|---|---|
| r | 1 | 1 |
| a | 1 | 1 |
| t | 1 | 0 |
| c | 0 | 1 |

`t`(인덱스 19)는 `str`에서 1, `target`에서 0으로 다르다 → 배열이 서로 다르므로 `false`. 정답과 일치한다.

## 5. 복잡도 비교

| | 방법 1: 정렬 | 방법 2: `int[26]` | 방법 3: 해시맵 |
|---|---|---|---|
| 시간복잡도 | `O(n log n)` | `O(n)` | `O(n)` (평균, 해시 연산 상수 가정) |
| 공간복잡도 | `O(n)`(정렬용 배열 복사본) | `O(1)`(항상 26칸 고정) | `O(k)`(`k` = 서로 다른 문자 종류 수) |
| 유니코드 대응 | 그대로 동작 | **안 됨** (26개 알파벳 전제) | 그대로 동작 |
| 구현 난이도 | 가장 단순 | 단순 | 단순 (Map API 익숙하면) |

`n`은 문자열 길이(최대 `5×10^4`)다. 셋 다 이 정도 크기에서는 체감 속도 차이가 거의 없지만, **이론적으로는 방법 2·3이 방법 1보다 낫다** — 정렬은 `O(n log n)`인데 개수만 세는 건 `O(n)`으로 충분하기 때문이다.

## 6. Follow-up — 유니코드 문자가 포함된다면?

이 문제는 "소문자 영어 알파벳만 나온다"는 제약 덕분에 방법 2(`int[26]`)처럼 **문자 종류를 미리 알고 고정 크기 배열을 쓰는 최적화**가 가능했다. 하지만 유니코드처럼 문자 종류가 미리 정해져 있지 않거나 범위가 매우 넓다면:

- **방법 2(`int[26]`)는 쓸 수 없다** — 배열 크기를 유니코드 전체 범위로 잡는 건 비현실적이다(수십만 개).
- **방법 1(정렬)이나 방법 3(해시맵)은 그대로 쓸 수 있다** — 둘 다 특정 문자 집합을 가정하지 않기 때문이다. 특히 해시맵은 "실제로 등장한 문자만" 키로 가지므로, 문자 종류가 아무리 넓은 범위여도 메모리를 낭비하지 않는다.

즉, 이 문제에서 세 방법을 비교해본 것 자체가 Follow-up에 대한 답이 된다 — **"입력 문자 집합이 제한적이고 작을 때만 고정 배열 최적화가 유효하고, 그렇지 않으면 해시맵/정렬로 돌아가야 한다.**"

## 7. 엣지 케이스

- **길이가 다른 경우** (`s="a", t="ab"`): 세 방법 모두 길이가 다르면 자동으로 `false`가 나온다(각각 다른 이유로: 정렬은 배열 길이 비교, 배열/맵은 총합 불일치).
- **문자 종류는 같지만 개수가 다른 경우** (`s="aab", t="abb"`): 세 방법 모두 정확히 `false`를 반환한다.
- **완전히 같은 문자열** (`s="a", t="a"`): 자기 자신과 비교해도 `true`가 정확히 나온다.

## 8. 관련 문제

같은 "애너그램" 계열 문제들.

- [Find All Anagrams in a String (LeetCode 438)](../438-find-all-anagrams-in-a-string/Solution.md) — `s`의 부분 문자열 중 `p`의 애너그램인 것을 전부 찾는 문제. 여기서 쓴 "문자별 개수 비교"(방법 2, `int[26]`) 아이디어를 슬라이딩 윈도우와 결합해서 `O(n)`에 풀었다.
- [Group Anagrams (LeetCode 49)](../49-group-anagrams/Solution.md) — 두 문자열의 애너그램 판정을 넘어, 여러 문자열을 애너그램 그룹으로 묶는 문제. 여기서 쓴 "정렬해서 비교"(방법 1) 아이디어를 "정렬해서 그룹 키로 쓴다"로 확장했다.

> 이 섹션은 새 문제를 풀 때마다 갱신됩니다. 애너그램/문자 빈도 비교 계열 문제를 풀면 이 목록에 서로 링크를 추가해주세요.

---

*이 문서는 [Problem.md](./Problem.md)의 문제 설명을 기반으로 상세 풀이를 정리한 것입니다.*
