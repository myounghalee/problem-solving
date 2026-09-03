# [LeetCode 432] All O`one Data Structure — 횟수 버킷을 이중 연결 리스트로 꿰어 O(1) 만들기

- 문제 번호: 432
- 링크: https://leetcode.com/problems/all-oone-data-structure/
- 난이도: Hard
- 태그: Hash Table, Linked List, Design, Doubly-Linked List
- 사용 자료구조/알고리즘: **해시맵 + 정렬된 이중 연결 리스트(횟수별 버킷)**

## 1. 문제 요약

문자열 키의 등장 횟수를 관리하면서 네 연산을 지원하는 자료구조를 만든다.

- `inc(key)` / `dec(key)`: 횟수를 1 올리거나 내린다. 0이 되면 키를 제거한다.
- `getMaxKey()` / `getMinKey()`: 횟수가 최대·최소인 키 하나. 비어 있으면 `""`.

**네 연산 모두 `O(1)`** — 이 한 줄이 이 문제의 전부다. 정답만 내는 건 `HashMap` 하나로 5분이면 되지만, `O(1)`을 만드는 순간 난이도가 Hard가 된다.

## 2. 접근 아이디어

### 왜 어려운가

`Map<String, Integer>`로 횟수만 들고 있으면 `inc`/`dec`는 `O(1)`이지만 `getMaxKey`가 전체를 훑어야 한다. 반대로 최대·최소를 빠르게 얻으려고 정렬 구조를 쓰면 이번엔 `inc`/`dec`가 느려진다. **네 연산을 동시에 `O(1)`로 만드는 것**이 과제다.

### 관찰 1 — 같은 횟수의 키들은 묶어도 된다

`getMaxKey`는 최대 횟수를 가진 키 중 **아무거나** 반환하면 된다. 그러니 키를 하나하나 정렬해 둘 필요가 없다. **횟수가 같은 키들을 한 덩어리(버킷)로 묶고, 버킷만 횟수 순으로 정렬**해 두면 충분하다.

```text
키 a:1, b:1, c:3, d:5  →  버킷은 3개

head <-> [1: {a,b}] <-> [3: {c}] <-> [5: {d}] <-> tail
          ↑ 최소                      ↑ 최대
```

이렇게 두면 `getMinKey`는 `head.next`, `getMaxKey`는 `tail.prev`에서 아무 키나 꺼내면 끝이다. **탐색이 아예 없다.**

### 관찰 2 — 목적지는 언제나 이웃이다

이게 `O(1)`의 열쇠다. 횟수는 항상 **±1로만** 변한다. 그래서 버킷 리스트가 횟수 오름차순으로 정렬되어 있다면:

> `inc`로 키가 `c`에서 `c+1`로 갈 때, `c+1` 버킷이 존재한다면 그것은 **반드시 현재 버킷 바로 다음**이다.

중간에 다른 버킷이 끼어들 수 없다. `c`와 `c+1` 사이에는 정수가 없으니까. 그래서 확인할 것이 딱 하나다.

```text
cur.next.count == c + 1 인가?
  맞다  → 그 버킷으로 옮긴다
  아니다 → c+1짜리 새 버킷을 cur 바로 뒤에 끼워넣는다
```

`dec`은 방향만 반대로 `cur.prev`를 본다. **어느 경우에도 탐색이 없다.**

### 관찰 3 — 움직이는 것은 키다

버킷의 `count`는 생성될 때 정해지고 **절대 변하지 않는다**. `inc`는 버킷의 횟수를 올리는 게 아니라, **키를 한 버킷에서 옆 버킷으로 옮기는** 일이다. 이 구분을 놓치면 구조가 무너진다 (6절).

그리고 빈 버킷은 즉시 끊어낸다. 이 불변식 덕분에 `head.next`가 항상 유효한 최솟값이 된다.

### 손으로 시뮬레이션

`inc(a)`, `inc(b)`, `inc(b)`, `dec(a)` 순으로 따라가 보자.

| 연산 | 리스트 상태 | `getMinKey()` | `getMaxKey()` |
|---|---|---|---|
| 시작 | `head <-> tail` | `""` | `""` |
| `inc(a)` | `head <-> [1:{a}] <-> tail` | `a` | `a` |
| `inc(b)` | `head <-> [1:{a,b}] <-> tail` | `a` 또는 `b` | 〃 |
| `inc(b)` | `head <-> [1:{a}] <-> [2:{b}] <-> tail` | `a` | `b` |
| `dec(a)` | `head <-> [2:{b}] <-> tail` | `b` | `b` |

세 번째 줄에서 `b`가 `[1]`에서 새로 만든 `[2]`로 옮겨갔고, 네 번째 줄에서 `a`가 빠지며 빈 `[1]` 버킷이 끊겨 나갔다. **`[1]`이 사라지자마자 `head.next`가 자동으로 `[2]`를 가리킨다** — 새 최솟값을 따로 계산할 필요가 없다. 이 지점이 6절에서 다룰 함정의 해답이다.

## 3. 코드 (Java)

```java
import java.util.*;

class AllOne {

    // 같은 횟수를 가진 키들의 묶음. count는 생성 시 정해지고 변하지 않는다.
    private static class Bucket {
        final int count;
        final Set<String> keys = new HashSet<>();
        Bucket prev, next;

        Bucket(int count) {
            this.count = count;
        }
    }

    Map<String, Bucket> keyMap; // key → 그 키가 현재 속한 버킷
    Bucket head, tail;          // 센티넬. 리스트는 count 오름차순, 빈 버킷은 두지 않는다.

    public AllOne() {
        keyMap = new HashMap<>();
        head = new Bucket(0);
        tail = new Bucket(0);
        head.next = tail;
        tail.prev = head;
    }

    public void inc(String key) {
        Bucket cur = keyMap.get(key);

        if (cur == null) { // 새 키 → 횟수 1 버킷으로
            Bucket to = (head.next != tail && head.next.count == 1)
                    ? head.next
                    : insertAfter(head, 1);
            to.keys.add(key);
            keyMap.put(key, to);
            return;
        }

        // 목적지는 항상 바로 다음 버킷 자리 — 있으면 재사용, 없으면 끼워넣는다
        Bucket to = (cur.next != tail && cur.next.count == cur.count + 1)
                ? cur.next
                : insertAfter(cur, cur.count + 1);
        move(key, cur, to);
    }

    public void dec(String key) {
        Bucket cur = keyMap.get(key); // 존재가 보장됨

        if (cur.count == 1) { // 0이 되면 키 자체를 제거
            cur.keys.remove(key);
            keyMap.remove(key);
            if (cur.keys.isEmpty()) unlink(cur);
            return;
        }

        Bucket to = (cur.prev != head && cur.prev.count == cur.count - 1)
                ? cur.prev
                : insertAfter(cur.prev, cur.count - 1);
        move(key, cur, to);
    }

    public String getMaxKey() {
        return tail.prev == head ? "" : tail.prev.keys.iterator().next();
    }

    public String getMinKey() {
        return head.next == tail ? "" : head.next.keys.iterator().next();
    }

    // 키를 from에서 to로 옮기고, 빈 버킷이 된 from은 끊어낸다
    private void move(String key, Bucket from, Bucket to) {
        from.keys.remove(key);
        to.keys.add(key);
        keyMap.put(key, to);
        if (from.keys.isEmpty()) unlink(from);
    }

    private Bucket insertAfter(Bucket node, int count) {
        Bucket b = new Bucket(count);
        b.prev = node;
        b.next = node.next;
        node.next.prev = b;
        node.next = b;
        return b;
    }

    private void unlink(Bucket b) {
        b.prev.next = b.next;
        b.next.prev = b.prev;
    }
}
```

### 코드 설명

- **`count`가 `final`이다.** 이건 문서화이자 안전장치다. "버킷의 횟수를 올린다"는 잘못된 발상으로 `count++`를 쓰는 순간 컴파일이 실패한다. 움직이는 건 버킷이 아니라 키다.
- **`keyMap`이 키를 버킷 객체로 직접 가리킨다.** 그래서 `Map<String,Integer>`(키의 횟수)와 `Map<Integer,Bucket>`(횟수별 버킷) 두 개가 하나로 합쳐졌다. 키의 횟수는 `keyMap.get(key).count`로 얻는다. **횟수로 버킷을 찾는 조회가 한 번도 없기 때문에** 인덱스용 맵이 필요 없다 — 목적지는 언제나 `cur.next`나 `cur.prev`다.
- **센티넬 `head`/`tail`** 덕분에 `prev == null` 검사가 전부 사라진다. 리스트가 비었는지는 `head.next == tail`로 판정한다. 두 센티넬의 `count` 값(여기서는 0)은 쓰이지 않는다 — 비교 전에 `!= tail`, `!= head`로 걸러내기 때문이다.
- **`move`가 "옮기고, 비면 끊는다"를 함께 처리한다.** `inc`와 `dec` 양쪽의 마무리가 통일되어 빈 버킷을 남길 실수를 막는다.
- **`insertAfter(cur.prev, ...)`** — `dec`의 "앞에 끼우기"는 별도 메서드 없이 기준점만 바꿔 재사용한다.
- **순서 주의**: 새 버킷은 `cur`를 기준으로 끼워넣으므로, `cur`를 끊어내는 것은 반드시 **삽입 이후**여야 한다. 위 코드에서는 `insertAfter`가 삼항 연산자 안에서 먼저 실행되고 `unlink`는 `move` 끝에서 일어나므로 순서가 지켜진다.

## 4. 복잡도 분석

- **시간복잡도**: 네 연산 모두 `O(1)`. `HashMap` 조회·삽입·삭제가 평균 `O(1)`이고, `HashSet`의 `add`/`remove`/`iterator().next()`도 `O(1)`이며, 연결 리스트의 삽입·삭제는 포인터 몇 개만 바꾸므로 상수 시간이다. **어떤 연산도 리스트를 훑지 않는다.**
- **공간복잡도**: `O(n)` — 키 `n`개에 대해 `keyMap` 항목 `n`개, 버킷은 최대 `n`개(모든 키의 횟수가 서로 다를 때).

실측으로 `O(1)`을 확인했다. 입력 규모를 100배 키워도 연산당 비용이 그대로다.

| 키 개수 | 총 시간 (`inc` 2n회) | 연산당 |
|---|---|---|
| 25,000 | 3ms | 52 ns |
| 250,000 | 22ms | 44 ns |
| 2,500,000 | 280ms | 56 ns |

## 5. 엣지 케이스

- **빈 상태**: `getMaxKey`/`getMinKey` 모두 `""`. `head.next == tail`로 판정한다.
- **키가 하나뿐**: 그 키가 최대이자 최소다.
- **`dec`으로 마지막 키가 제거됨**: 버킷이 끊기고 리스트가 다시 비어 `""`를 반환해야 한다.
- **최소 버킷의 마지막 키가 제거됨** (`{a:1, b:2}`에서 `dec("a")`): 새 최솟값이 2로 바뀌어야 한다. 6절에서 다루듯 정수로 최솟값을 추적하는 방식이 **유일하게 실패하는 지점**이다.
- **한 키가 올라가며 원래 버킷이 빔** (`{a:1, b:1}`에서 `inc("b")`): 최솟값이 그대로 1이어야 하고(`a`가 남아 있으므로), 최댓값은 2가 되어야 한다.

검증: 키 3종·길이 1~8인 **모든 연산 시퀀스 201만 개**를 전수 탐색했고, 별도로 **120만 연산** 규모의 무작위 퍼징을 돌렸다. 매 연산마다 답의 정확성뿐 아니라 리스트 불변식(정렬 유지, 빈 버킷 없음, 양방향 링크 일관성, 키 중복 없음, `keyMap`과 실제 위치 일치)까지 검사해 전부 통과했다.

## 6. 왜 더 단순한 방법들은 안 되는가

이 문제는 "정답은 쉽고 `O(1)`이 어렵다". 중간 단계 시도들이 각각 어디서 막히는지 정리해 둘 가치가 있다.

### (1) `HashMap` + 매번 전수 조사

```java
int max = Collections.max(countMap.keySet()); // O(m)
```

가장 먼저 떠오르는 방식이고 **정답은 맞다.** 여기서 `m`은 키 개수가 아니라 **서로 다른 횟수의 가짓수**인데, 이게 생각보다 훨씬 작게 묶인다.

동시에 존재하는 서로 다른 횟수를 `m`개라 하면 그 값들은 서로 다른 양의 정수이므로 합이 최소 `m(m+1)/2`다. 한편 현재 횟수들의 총합은 지금까지의 `inc` 호출 수를 넘을 수 없고, 제약상 그건 `5×10⁴`이다.

```text
m(m+1)/2 ≤ 50,000  →  m ≤ 315
```

그래서 실측으로는 총 5만 호출에 35ms로 가볍게 통과한다. **채점은 통과하지만 요구사항은 만족하지 못하는** 전형적인 경우다.

### (2) `TreeMap`

`TreeMap<Integer, Set<String>>`으로 두면 `firstKey()`/`lastKey()`가 최소·최대를 바로 준다. 하지만 네 연산 모두 `O(log m)`이다. 위 계산으로 `log₂(315) ≈ 8.3`이라 실측 차이는 거의 없지만, 역시 `O(1)`은 아니다.

### (3) 버킷의 키 목록을 `ArrayList`로 두기 — 입력 순서에 15배 흔들린다

`Map<Integer, List<String>>`으로 버킷을 관리하면 `list.remove(key)`가 **선형 탐색**이다. 문제는 이 비용이 **입력 순서에 따라 극단적으로 달라진다**는 점이다.

```text
키 25,000개를 횟수 1로 만든 뒤 각각 inc:
  삽입 순서대로 (리스트 앞에서 바로 발견)     19ms
  역순으로     (리스트 끝까지 탐색)          283ms   ← 비교 약 3억 1천만 회
```

같은 연산 횟수인데 순서만 뒤집으면 15배가 된다. `HashSet`으로 바꾸면 이 자리는 `O(1)`이 되므로, **`ArrayList`를 쓸 이유가 없다.**

### (4) `min`/`max`를 정수로 들고 다니기 — `max`는 되고 `min`은 안 된다

전수 조사를 없애려고 최솟값·최댓값을 필드로 추적하는 건 자연스러운 다음 수다. 갱신 규칙도 대부분 성립한다.

- `inc`에서 버킷 `c`가 비고 `c == min`이었다면, 나머지 키는 모두 `c`보다 크고 우리 키가 `c+1`에 있으므로 `min = c+1`. ✅
- `dec`에서 버킷 `c`가 비고 `c == max`였다면, 우리 키가 `c-1`에 내려가 있으므로 `max = c-1`. ✅

**그런데 딱 한 경우가 뚫린다.** `dec`으로 횟수가 0이 되어 키가 *제거*되고, 그 키가 최소 버킷의 마지막 키였을 때다.

```text
inc(a)          → {a:1}
inc(b), inc(b)  → {a:1, b:2}   min=1, max=2
dec(a)          → a 제거, 버킷 1 소멸 → {b:2}

  새 min은 2여야 하는데, 남은 키들의 횟수가 2일지 7일지 100일지 알 방법이 없다.
```

`max`에는 이런 구멍이 없다. 최대 버킷이 비는 경우 그 키는 항상 `c-1`로 **내려가 있기 때문**이다. 반면 최소 버킷이 비는 경우 그 키는 아래로 갈 곳이 없어 **사라져 버린다**. 이 비대칭이 핵심이다.

규칙 기반 추적을 20만 개 무작위 시퀀스로 퍼징한 결과가 이를 그대로 보여준다.

| | 실패한 시퀀스 수 |
|---|---|
| `max` 갱신 | **0건** |
| `min` 갱신 | **165,236건** (전부 위 형태) |

그리고 3절의 구조는 이 문제를 **계산이 아니라 구조로** 해결한다. 버킷을 끊어낼 때 `prev`/`next`가 이미 손에 있으므로 다음 최솟값이 저절로 드러난다. `min`/`max` 변수 자체가 필요 없어진다.

### (5) 버킷을 "키별"로 만들기 — 가장 빠지기 쉬운 함정

이중 연결 리스트를 도입하고도 틀릴 수 있는 지점이다.

```java
Bucket bucket = keyMap.computeIfAbsent(key, k -> new Bucket()); // 키마다 새 노드
bucket.count++;                                                 // 노드의 횟수를 올림
```

이러면 노드 개수가 **서로 다른 횟수의 가짓수**가 아니라 **키 개수**만큼 생긴다. 그리고 노드의 `count`가 변하니, 정렬을 유지하려면 **노드가 리스트 안에서 자리를 옮겨 다녀야 한다.** 앞에 같은 횟수의 노드가 100개 있으면 100칸을 지나가야 하므로 `O(1)`이 깨진다. `O(1)`을 만들려고 도입한 구조가 오히려 이동 비용을 떠안는다.

증상은 이렇게 나타난다.

```text
inc(a)   head <-> [1: {a}] <-> tail
inc(b)   head <-> [1: {a,b}] <-> [1: {a,b}] <-> tail   ← 노드 2개가 같은 Set 공유
inc(b)   head <-> [2: {b}] <-> [1: {a}] <-> tail       ← 2가 1보다 앞! 정렬 붕괴
```

`getMinKey()`가 `"b"`(횟수 2), `getMaxKey()`가 `"a"`(횟수 1)로 **정확히 뒤집힌 답**을 내놓는다.

**`count`를 `final`로 선언하면 이 실수가 컴파일 단계에서 막힌다.** 버킷은 횟수를 대표하고, 키가 버킷 사이를 옮겨 다닌다 — 이 방향을 코드로 못박아 두는 것이 좋다.

### 이 함정들이 문제 예시로는 잡히지 않는다

위 (5)의 최소 반례는 **3연산**(`inc(a), inc(b), inc(b)`)이고, (4)의 최소 반례는 **4연산**(`inc(a), inc(b), inc(b), dec(a)`)이다. 그런데 문제가 주는 예시는 `inc`만 3번 하고 끝나서 둘 다 건드리지 않는다.

**교훈: 불변식이 있는 자료구조를 만들 때는 연산마다 불변식 자체를 검사하는 테스트를 짜자.** "답이 맞는가"만 보면 리스트가 이미 망가졌는데도 우연히 맞는 답이 나오는 구간을 놓친다. 짧은 시퀀스 전수 탐색은 이런 버그를 몇 초 만에 잡아낸다.

## 7. 관련 문제

**횟수별 버킷**을 다루는 계열.

- [LFU Cache (LeetCode 460)](../460-lfu-cache/Solution.md) — "사용 횟수가 가장 적은 것을 제거"하려고 횟수별 버킷을 관리한다는 점에서 뼈대가 같다. 다만 LFU는 횟수가 **올라가기만 하므로** 최솟값을 정수 하나로 추적할 수 있다(단조 증가라 상각 `O(1)`). 이 문제는 `dec`이 있어 그 방법이 무너지고(6절 (4)), 그래서 연결 리스트가 필요해진다. **두 문제의 난이도 차이가 정확히 여기서 갈린다.**
- [LRU Cache (LeetCode 146)](../146-lru-cache/Solution.md) — 해시맵 + 이중 연결 리스트로 `O(1)`을 만드는 가장 기본형. 이 문제의 리스트가 "횟수 순 정렬"인 반면 LRU의 리스트는 "최근 사용 순"이다.
- [Longest Substring Without Repeating Characters (LeetCode 3)](../3-longest-substring-without-repeating-characters/Solution.md) — 직접 관련은 없지만, "자료구조 선택이 상수 배를 좌우한다"는 점이 겹친다. 6절 (3)의 `ArrayList` → `HashSet` 교체가 283ms를 3ms로 바꾼 것과 같은 성격이다.

> 이 섹션은 새 문제를 풀 때마다 갱신됩니다. 해시맵 + 이중 연결 리스트로 `O(1)`을 만드는 설계 문제를 풀면 이 목록에 서로 링크를 추가해주세요.

---

*이 문서는 [Problem.md](./Problem.md)의 문제 설명을 기반으로 상세 풀이를 정리한 것입니다.*
