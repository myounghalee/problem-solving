# [LeetCode 460] LFU Cache — 빈도수별 버킷 + minFreq 추적으로 O(1) 구현하기

- 문제 번호: 460
- 링크: https://leetcode.com/problems/lfu-cache/
- 난이도: Hard
- 태그: Hash Table, Linked List, Design, Doubly-Linked List
- 사용 자료구조/알고리즘: **해시맵 + "빈도수 → 키 집합" 버킷 + `minFreq` 변수**

## 1. 문제 요약

[146번(LRU Cache)](../146-lru-cache/Solution.md)의 상위 버전. 제거 기준이 "가장 오래 안 쓰인 것"이 아니라 **"가장 적게 쓰인 것(Least Frequently Used)"**이고, 사용 횟수가 동점이면 그중 **가장 오래 안 쓰인 것**을 제거한다.

```text
예시) LFUCache(2)
put(1,1), put(2,2), get(1)         // cnt(1)=2, cnt(2)=1
put(3,3)                            // cnt가 가장 작은 2를 제거
get(2) → -1, get(3) → 3
put(4,4)                            // cnt(1)==cnt(3), 둘 다 2로 동점 → 더 오래 안 쓰인 1을 제거
get(1) → -1, get(3) → 3, get(4) → 4
```

`get`, `put` 모두 평균 `O(1)`이어야 한다.

## 2. 접근 아이디어

146번은 "해시맵 + 이중 연결 리스트 하나"로 충분했지만, 이번엔 **"사용 횟수"라는 축이 하나 더 필요**하다.

- `cache`: `키 → [값, 사용횟수]`. 특정 키를 O(1)에 찾기 위한 해시맵.
- `freq`: `사용횟수 → 그 사용횟수를 가진 키들의 집합`. 각 버킷은 **`LinkedHashSet`**을 써서, 같은 사용횟수 안에서도 "먼저 들어온(=오래 안 쓰인) 순서"를 기억하게 한다.
- `minFreq`: 지금 캐시 전체에서 **가장 작은 사용횟수가 몇인지**를 매번 찾지 않고 변수로 직접 들고 있는다.

키 하나의 사용횟수가 바뀔 때마다(=`get`이 호출되거나, 이미 있는 키에 `put`이 호출될 때) 그 키를 **기존 버킷에서 빼서 새 버킷(횟수+1)으로 옮긴다.** 이 "빼고 넣기"를 `addFreq`/`removeFreq` 두 헬퍼로 나눠서 처리한다.

제거할 때는 `freq.get(minFreq)`에서(=가장 적게 쓰인 버킷) `LinkedHashSet`의 **맨 앞 원소**(가장 먼저 그 버킷에 들어온, 즉 가장 오래 안 쓰인 키)를 O(1)에 꺼내 제거한다. 새 키가 들어오면 항상 사용횟수 `1`에서 시작하므로, 그 순간 `minFreq`는 무조건 `1`로 재설정해도 안전하다(1보다 작은 사용횟수는 있을 수 없으니까).

`minFreq`를 정확히 유지하는 게 가장 까다로운 지점이다 — **어떤 버킷이든 비면 무조건 `minFreq`를 올리는 게 아니라, 하필 지금 `minFreq`였던 버킷이 비었을 때만** 올려야 한다. (이 부분에서 실제로 버그를 겪었다 — 아래 "겪었던 실수" 참고.)

### 손으로 시뮬레이션 — 문제의 예시 (`capacity = 2`)

| 호출 | `freq` 상태 (횟수: 키들) | `minFreq` |
|---|---|---|
| `put(1,1)` | `{1: [1]}` | 1 |
| `put(2,2)` | `{1: [1, 2]}` | 1 |
| `get(1)` | `{1: [2], 2: [1]}` | 1 |
| `put(3,3)` | 용량 초과, `minFreq=1`의 `freq[1]=[2]`에서 `2` 제거 → `{2: [1], 1: [3]}` | 1 |
| `get(2)` | 없음 → `-1` | - |
| `get(3)` | `{2: [1], 2': [3]}` → 정리하면 `{2: [1, 3]}` | 2 (`freq[1]`이 비어서 증가) |
| `put(4,4)` | 용량 초과, `minFreq=2`의 `freq[2]=[1, 3]`에서 맨 앞(`1`, 더 오래 안 쓰임) 제거 → `{2: [3], 1: [4]}` | 1 |

모든 반환값이 `[null, null, null, 1, null, -1, 3, null, -1, 3, 4]`(문제 예시)와 일치한다.

## 3. 코드 (Java)

```java
import java.util.*;

class LFUCache {

    int capacity;
    Map<Integer, int[]> cache = new LinkedHashMap<>(); // 키 -> [값, 사용횟수]
    Map<Integer, Set<Integer>> freq = new HashMap<>();  // 사용횟수 -> 그 횟수를 가진 키들 (삽입 순서 유지)
    int minFreq; // 현재 가장 작은 사용횟수

    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!cache.containsKey(key)) return -1;

        int[] value = cache.get(key);
        addFreq(key, value[1]); // 사용횟수 버킷을 한 칸 위로 이동
        value[1]++;
        cache.remove(key);
        cache.put(key, value);
        return cache.get(key)[0];
    }

    public void put(int key, int value) {
        if (!cache.containsKey(key) && cache.size() >= capacity) {
            int val = freq.get(minFreq).iterator().next(); // 가장 적게, 그중 가장 오래 안 쓰인 키
            removeFreq(val, minFreq);
            cache.remove(val);
        }

        if (cache.containsKey(key)) {
            addFreq(key, cache.get(key)[1]);
            cache.get(key)[0] = value;
            cache.get(key)[1]++;
        } else {
            addFreq(key, 0);
            minFreq = 1; // 새 키는 항상 사용횟수 1에서 시작 -> 전체 최솟값도 1
            cache.put(key, new int[]{value, 1});
        }
    }

    // 키 k를 사용횟수 f 버킷에서 f+1 버킷으로 옮김
    private void addFreq(int k, int f) {
        removeFreq(k, f);
        freq.computeIfAbsent(f + 1, i -> new LinkedHashSet<>()).add(k);
    }

    // 키 k를 사용횟수 f 버킷에서 제거
    private void removeFreq(int k, int f) {
        if (freq.containsKey(f)) {
            freq.get(f).remove(k);
            if (freq.get(f).isEmpty()) {
                freq.remove(f);
                if (f == minFreq) minFreq++; // 비워진 버킷이 minFreq였을 때만 갱신
            }
        }
    }
}
```

### 코드 설명

- `freq`의 각 버킷은 `LinkedHashSet<Integer>`다. `Set`이면서도 **삽입 순서를 기억**하기 때문에, 같은 사용횟수를 가진 키들 중에서도 `iterator().next()`로 "그 버킷에 가장 먼저 들어온(=가장 오래 안 쓰인) 키"를 O(1)에 얻을 수 있다.
- `addFreq(k, f)`는 "키 `k`를 `f` 버킷에서 빼서 `f+1` 버킷에 넣기"다. `get`이든 `put`(기존 키 갱신)이든, 사용횟수가 1 올라가는 모든 경우에 이 함수 하나로 처리한다.
- `removeFreq(k, f)`의 마지막 줄(`if (f == minFreq) minFreq++;`)이 핵심이다. **버킷이 비었다고 무조건 `minFreq`를 올리면 안 되고, 하필 그 버킷이 지금의 최솟값이었을 때만** 올려야 한다. 다른 (더 큰) 사용횟수 버킷이 비는 것은 전체 최솟값과 무관하다.
- `put`에서 새 키를 넣을 때 `minFreq = 1`로 무조건 재설정하는 이유: 새로 삽입되는 키는 항상 사용횟수 `1`에서 시작하고, 사용횟수는 `1` 밑으로 내려갈 수 없으므로, 새 키가 들어온 순간의 전체 최솟값은 항상 `1`이다.
- 제거(eviction)는 `freq.get(minFreq).iterator().next()` 한 번으로 끝난다 — 루프로 전체를 훑지 않는다.

## 4. 복잡도 분석

- **시간복잡도**: `get`, `put` 모두 평균 `O(1)`. 해시맵 조회/삽입/삭제, `LinkedHashSet`의 삽입/삭제/첫 원소 접근이 전부 평균 `O(1)`이고, `minFreq` 갱신도 조건 체크 한 번뿐이라 루프가 없다.
- **공간복잡도**: `O(capacity)` — `cache`와 `freq`에 최대 `capacity`개의 키가 저장된다.

**실측**: `capacity = 10,000`으로 초기화한 뒤 `get`/`put`을 20만 번 호출해도(문제의 최댓값 근처) **26ms**밖에 걸리지 않는다.

## 5. 엣지 케이스

- **사용횟수가 동점인 경우**: `LinkedHashSet`의 삽입 순서 덕분에, 같은 버킷 안에서 항상 가장 먼저 들어온(=가장 오래 안 쓰인) 키가 제거 대상으로 뽑힌다.
- **한 키가 여러 번 연속으로 쓰이는 경우**: 매번 사용횟수 버킷이 한 칸씩 올라가고, 그 버킷이 비면서 `minFreq`도 필요할 때만 정확히 갱신된다.
- **새 키가 계속 들어와 다른 키들을 밀어내는 경우**: 새 키는 항상 `minFreq=1`로 리셋시키므로, 사용횟수 `1`인 버킷이 항상 가장 먼저 제거 대상이 된다.

## 6. 겪었던 실수 (디버깅 기록)

1. **제거할 키를 찾을 때 전체를 훑는 `O(n)` 스캔**: 처음엔 `put`마다 `cache`(또는 `freq`) 전체를 `for`로 순회하며 최솟값을 찾았다. 로직 자체는 맞았지만, 문제가 요구하는 평균 `O(1)`을 못 지켜서 시간초과 위험이 컸다 — `capacity=2000`으로 인위적인 최악의 경우를 만들어 실측했더니 3000번 `put`에 56ms가 걸렸고, 실제 제약(`capacity=10000`, 호출 20만 번)으로 환산하면 약 18초까지 걸릴 수 있었다. → `minFreq`를 변수로 직접 추적하도록 바꿔서 스캔 자체를 없앴다.
2. **`minFreq++`를 "아무 버킷이나 비면" 실행**: `minFreq`를 도입한 직후엔, `removeFreq`에서 어떤 버킷이 비든 무조건 `minFreq++`를 했다. 그런데 비워진 버킷이 실제 최솟값이 아닐 수도 있는데(예: 사용횟수 2인 버킷이 비었지만, 사용횟수 1인 버킷엔 여전히 다른 키가 남아있는 경우) `minFreq`가 잘못 올라가버려서, 나중에 `freq.get(minFreq)`가 `null`을 반환해 `NullPointerException`이 발생했다. → 비워진 버킷의 사용횟수(`f`)가 **정확히 지금의 `minFreq`와 같을 때만** 증가시키도록 조건을 추가해서 해결.

## 7. 관련 문제

같은 캐시 설계 계열, 난이도가 한 단계 올라간 짝 문제.

- [LRU Cache (LeetCode 146)](../146-lru-cache/Solution.md) — "가장 오래 안 쓰인 것"만 추적하면 되는 더 단순한 버전. `LinkedHashMap` 하나로 충분했지만, 이 문제는 "사용 횟수"라는 축이 추가되어 버킷 구조(`freq`)와 `minFreq` 추적이 더 필요하다.
- [All O`one Data Structure (LeetCode 432)](../432-all-oone-data-structure/Solution.md) — 같은 "횟수별 버킷" 발상을 쓰지만, 횟수가 내려가는 `dec` 연산이 있어 `minFreq`를 정수 하나로 추적하는 방법이 무너진다. 그래서 버킷을 이중 연결 리스트로 꿰어야 `O(1)`이 유지된다.

> 이 섹션은 새 문제를 풀 때마다 갱신됩니다. 캐시 설계, 해시맵+순서 유지 자료구조 계열 문제를 풀면 이 목록에 서로 링크를 추가해주세요.

---

*이 문서는 [Problem.md](./Problem.md)의 문제 설명을 기반으로 상세 풀이를 정리한 것입니다.*
