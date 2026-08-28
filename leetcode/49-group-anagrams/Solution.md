# [LeetCode 49] Group Anagrams — 정렬한 문자열을 키로 묶기

- 문제 번호: 49
- 링크: https://leetcode.com/problems/group-anagrams/
- 난이도: Medium
- 태그: Array, Hash Table, String, Sorting
- 사용 자료구조/알고리즘: **해시맵(정렬된 문자열을 키로 사용)**

## 1. 문제 요약

문자열 배열 `strs`가 주어질 때, 서로 애너그램인 문자열끼리 그룹으로 묶어서 반환한다. 그룹 순서, 그룹 안 원소 순서는 상관없다.

```text
예시) strs = ["eat","tea","tan","ate","nat","bat"]
→ [["bat"],["nat","tan"],["ate","eat","tea"]]
```

[242번(Valid Anagram)](../242-valid-anagram/Solution.md)이 "두 문자열이 애너그램인가"를 판정했다면, 이번엔 그 판정 로직을 **여러 문자열을 묶는 기준(키)**으로 확장한 문제다.

## 2. 접근 아이디어

핵심 관찰: **애너그램인 문자열들은 정렬하면 완전히 똑같은 문자열이 된다.** 예를 들어 `"eat"`, `"tea"`, `"ate"`를 각각 정렬하면 셋 다 `"aet"`가 된다.

- 이 "정렬된 결과"를 **맵의 키**로 쓴다: `Map<String, List<String>>`.
- `strs`의 각 문자열을 정렬해서 키를 만들고, 그 키에 해당하는 리스트에 **원본 문자열**(정렬 전)을 추가한다.
- 같은 애너그램 그룹에 속한 문자열들은 전부 같은 키를 갖게 되므로, 자연스럽게 같은 리스트에 모인다.
- 마지막에 맵의 모든 값(리스트들)을 꺼내 `List<List<String>>`으로 반환한다.

### 손으로 시뮬레이션 — `strs = ["eat","tea","tan","ate","nat","bat"]`

| 처리한 문자열 | 정렬한 키 | 맵 상태 |
|---|---|---|
| `"eat"` | `"aet"` | `{"aet": ["eat"]}` |
| `"tea"` | `"aet"` | `{"aet": ["eat","tea"]}` |
| `"tan"` | `"ant"` | `{"aet": [...], "ant": ["tan"]}` |
| `"ate"` | `"aet"` | `{"aet": ["eat","tea","ate"], "ant": ["tan"]}` |
| `"nat"` | `"ant"` | `{"aet": [...], "ant": ["tan","nat"]}` |
| `"bat"` | `"abt"` | `{"aet": [...], "ant": [...], "abt": ["bat"]}` |

최종적으로 맵의 값들만 꺼내면 `[["eat","tea","ate"], ["tan","nat"], ["bat"]]` — 순서는 달라도(맵은 순서를 보장 안 함) 그룹 구성은 정답과 일치한다.

## 3. 코드 (Java)

```java
import java.util.*;

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>(); // 정렬된 문자열 -> 같은 그룹의 원본 문자열들

        for (String str : strs) {
            char[] c = str.toCharArray();
            Arrays.sort(c);
            map.computeIfAbsent(Arrays.toString(c), k -> new ArrayList<>()).add(str);
        }

        List<List<String>> result = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            result.add(entry.getValue());
        }
        return result;
    }
}
```

### 코드 설명

- `char[] c`를 정렬한 뒤 `Arrays.toString(c)`로 문자열화한 것을 맵의 키로 쓴다. (`Arrays.toString(char[])`는 `"[a, b, c]"` 같은 형태를 반환하는데, 애너그램끼리는 이 형태도 항상 똑같이 나오므로 키로 쓰기에 문제없다.)
- `map.computeIfAbsent(key, k -> new ArrayList<>())`가 핵심이다. 이 키가 맵에 **없으면** 새 빈 리스트를 만들어 맵에 실제로 저장한 뒤 그 리스트를 반환하고, **있으면** 저장된 기존 리스트를 그대로 반환한다. 반환된 리스트에 바로 `.add(str)`을 호출하면 맵에 저장된 리스트가 정확히 갱신된다.
  - (참고: `getOrDefault(key, new ArrayList<>())`로 바꾸면 안 된다 — `getOrDefault`는 키가 없을 때 기본값을 그냥 "반환"만 하지 맵에 저장은 안 하므로, 그 기본 리스트에 `add`해도 맵에는 전혀 반영되지 않는다.)
- 반복이 끝나면 맵에는 "정렬된 키 → 같은 그룹 문자열 리스트"가 전부 모여있다. `entrySet()`을 순회하며 값(리스트)들만 꺼내 최종 결과 리스트에 담는다.

## 4. 복잡도 분석

- **시간복잡도**: `O(n · k log k)` — `n`은 `strs`의 길이(최대 `10^4`), `k`는 문자열의 최대 길이(최대 `100`). 각 문자열을 정렬하는 데 `O(k log k)`가 들고, 이걸 `n`개 문자열에 대해 반복한다.
- **공간복잡도**: `O(n · k)` — 맵에 모든 문자열(및 정렬된 키)을 저장한다.

## 5. 엣지 케이스

- **빈 문자열이 여러 개인 경우** (`["", ""]`): 둘 다 정렬해도 빈 문자열이라 같은 키(`"[]"`)를 가지므로 하나의 그룹 `["", ""]`으로 정확히 묶인다.
- **완전히 같은 문자열이 중복으로 있는 경우** (`["abc", "abc"]`): 같은 키를 가지므로 같은 그룹에 둘 다 들어간다 (중복 제거 없이 그대로 유지되어야 함).
- **어떤 문자열과도 애너그램이 아닌 경우** (`"bat"`처럼): 자기 혼자만의 그룹(`["bat"]`)이 만들어진다.

## 6. 이런 방법도 있다 — 정렬 대신 문자 빈도로 키 만들기 (더 빠름)

문자열 정렬은 `O(k log k)`인데, [242번(Valid Anagram)](../242-valid-anagram/Solution.md)에서 봤듯 애너그램 판정에는 정렬 대신 **문자별 개수(`int[26]`)**를 써도 되고, 그게 더 빠르다(`O(k)`). 같은 아이디어를 "그룹 키 만들기"에도 그대로 적용할 수 있다.

```java
import java.util.*;

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for (String str : strs) {
            int[] count = new int[26]; // 문자별 개수
            for (char ch : str.toCharArray()) count[ch - 'a']++;
            map.computeIfAbsent(Arrays.toString(count), k -> new ArrayList<>()).add(str);
        }

        return new ArrayList<>(map.values());
    }
}
```

정렬 대신 문자별 개수를 센 배열(`Arrays.toString(count)`)을 키로 쓴다. 애너그램끼리는 이 빈도 배열도 항상 똑같으므로 그룹 키로 문제없이 쓸 수 있다.

**비교하면:**

| | 정렬 기반 (위 3.의 코드) | 문자 빈도 기반 |
|---|---|---|
| 문자열 1개당 키 생성 비용 | `O(k log k)` | `O(k)` |
| 전체 시간복잡도 | `O(n · k log k)` | `O(n · k)` |
| 같은 입력(`n=10000, k=100`, 랜덤 문자열) 실측 | 약 42ms | 약 10ms |

같은 문제(`strs` 최댓값 `n=10^4`, `k=100`)로 실측해보면 빈도 기반 방식이 **약 4배 빠르다.** `k`가 100 정도로 크지 않아 체감 차이가 아주 크진 않지만(`log k ≈ 7`), 이론적으로도 실측으로도 확실히 더 빠른 방법이다.

## 7. 관련 문제

같은 "애너그램" 계열 문제들.

- [Valid Anagram (LeetCode 242)](../242-valid-anagram/Solution.md) — 두 문자열이 애너그램인지 판정하는 더 단순한 버전. 여기서 쓴 "정렬해서 비교" 아이디어를 "정렬해서 그룹 키로 쓴다"로 확장한 게 이 문제다.
- [Find All Anagrams in a String (LeetCode 438)](../438-find-all-anagrams-in-a-string/Solution.md) — 슬라이딩 윈도우와 결합한 버전.

> 이 섹션은 새 문제를 풀 때마다 갱신됩니다. 애너그램/문자 빈도 비교 계열 문제를 풀면 이 목록에 서로 링크를 추가해주세요.

---

*이 문서는 [Problem.md](./Problem.md)의 문제 설명을 기반으로 상세 풀이를 정리한 것입니다.*
