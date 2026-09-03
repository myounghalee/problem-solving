# [LeetCode 146] LRU Cache — LinkedHashMap으로 "해시맵 + 이중 연결 리스트" 구현하기

- 문제 번호: 146
- 링크: https://leetcode.com/problems/lru-cache/
- 난이도: Medium
- 태그: Hash Table, Linked List, Design, Doubly-Linked List
- 사용 자료구조/알고리즘: **`LinkedHashMap`(해시맵 + 이중 연결 리스트)**

## 1. 문제 요약

`capacity`만큼만 담을 수 있는 캐시를 설계한다.

- `get(key)`: 키가 있으면 값을 반환(이 키는 "방금 사용됨"으로 표시), 없으면 `-1`.
- `put(key, value)`: 키가 있으면 값 갱신, 없으면 추가. 용량을 초과하면 **가장 오래 안 쓰인(Least Recently Used) 키**를 제거.
- `get`, `put` 모두 **평균 O(1)**이어야 한다.

```text
예시) LRUCache(2)
put(1,1) → {1=1}
put(2,2) → {1=1, 2=2}
get(1)   → 1                (1을 방금 사용 → 가장 최근 사용으로 이동)
put(3,3) → 용량 초과, LRU였던 2를 제거 → {1=1, 3=3}
get(2)   → -1               (제거됨)
```

## 2. 접근 아이디어

두 가지 요구사항을 동시에 만족해야 한다.

- **특정 키를 O(1)에 찾기** → 해시맵으로 해결.
- **"가장 오래 안 쓰인 것"을 O(1)에 찾아서 제거** → 사용 순서를 유지하는 자료구조가 필요. 배열이나 일반 리스트는 중간 삭제/순서 이동이 O(n)이라 안 된다. **이중 연결 리스트**를 쓰면 특정 노드를 앞뒤 포인터만 바꿔서 O(1)에 삭제/이동할 수 있다.

즉 **해시맵(빠른 조회) + 이중 연결 리스트(빠른 순서 관리)**를 같이 써야 한다. 자바의 `LinkedHashMap`이 정확히 이 두 가지를 이미 합쳐서 구현해둔 자료구조다 — 내부적으로 해시테이블과 이중 연결 리스트를 같이 유지하며, 각 항목이 "삽입 순서"(또는 옵션에 따라 "접근 순서")를 기억한다.

- `LinkedHashMap`은 기본적으로 **삽입 순서**를 유지한다. 그래서 "이 키를 최근에 썼다"는 걸 표시하려면, **그 키를 지웠다가 다시 넣어서** 맨 뒤(최신 위치)로 옮기는 트릭을 쓴다.
- `get(key)`: 값을 꺼낸 뒤, `remove` + `put`으로 다시 넣어서 "방금 사용됨" 상태로 갱신한다.
- `put(key, value)`: 이미 있는 키면 먼저 지우고 다시 넣어서 순서를 갱신하고, 없는 키면 그냥 추가한다. 그 후 용량을 초과했으면, **맵의 첫 번째 항목**(= 가장 오래전에 갱신된, 즉 LRU)을 지운다. `map.keySet().iterator().next()`가 정확히 이 "가장 오래된 키"를 O(1)에 알려준다.

### 손으로 시뮬레이션 — 문제의 예시 (`capacity = 2`)

| 호출 | 내부 순서(오래된 것→최신) | 반환값 |
|---|---|---|
| `put(1,1)` | `[1]` | - |
| `put(2,2)` | `[1, 2]` | - |
| `get(1)` | `[2, 1]` (1을 지웠다 다시 넣어서 맨 뒤로) | `1` |
| `put(3,3)` | `[2, 1]`에 3 추가 → `[2, 1, 3]`, 용량(2) 초과 → 맨 앞(`2`) 제거 → `[1, 3]` | - |
| `get(2)` | 없음 | `-1` |
| `put(4,4)` | `[1, 3, 4]`, 용량 초과 → 맨 앞(`1`) 제거 → `[3, 4]` | - |
| `get(1)` | 없음 | `-1` |
| `get(3)` | `[4, 3]`로 갱신 | `3` |
| `get(4)` | `[3, 4]`로 갱신 | `4` |

모든 반환값이 `[null, null, null, 1, null, -1, null, -1, 3, 4]`(문제 예시)와 일치한다.

## 3. 코드 (Java)

```java
import java.util.LinkedHashMap;
import java.util.Map;

class LRUCache {
    Map<Integer, Integer> map = new LinkedHashMap<>(); // 삽입 순서 유지 (해시맵 + 이중 연결 리스트)
    int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        int val = map.get(key);
        map.remove(key);
        map.put(key, val); // 지웠다 다시 넣어서 "최근 사용"으로 이동
        return val;
    }

    public void put(int key, int value) {
        map.remove(key); // 이미 있으면 순서 갱신을 위해 먼저 제거 (없어도 안전, remove는 no-op)
        map.put(key, value);
        if (capacity < map.size()) map.remove(map.keySet().iterator().next()); // 가장 오래된 키 제거
    }
}
```

### 코드 설명

- `map`은 `LinkedHashMap`이라 내부적으로 각 항목이 삽입된 순서를 이중 연결 리스트로 기억한다. `keySet().iterator().next()`는 이 리스트의 맨 앞(가장 먼저 들어간, 즉 가장 오래 안 쓰인) 키를 O(1)에 돌려준다.
- `get`에서 `remove` 후 `put`을 다시 하는 이유: 단순히 값을 읽기만 하면 그 항목의 "삽입 순서상 위치"는 그대로 남는다. 그런데 LRU 규칙상 "방금 읽은 키"는 가장 최근에 쓰인 걸로 취급해야 하므로, 지웠다가 다시 넣어서 리스트의 맨 뒤(최신 위치)로 강제로 옮긴다.
- `put`에서도 같은 이유로, 키가 이미 있으면 먼저 지우고 다시 넣어서 순서를 최신으로 갱신한다. 키가 없었다면 `map.remove(key)`는 아무 일도 하지 않는(no-op) 안전한 호출이다.
- 용량을 초과했는지(`capacity < map.size()`)는 새 항목을 넣은 **직후에** 확인하고, 초과했다면 그 시점의 맨 앞(가장 오래된) 키 하나만 제거한다.

## 4. 복잡도 분석

- **시간복잡도**: `get`, `put` 모두 평균 `O(1)`. `LinkedHashMap`의 `get`/`put`/`remove`가 일반 `HashMap`과 동일하게 평균 `O(1)`이고, 순서 정보를 갱신하는 이중 연결 리스트 연산도 포인터만 바꾸는 거라 `O(1)`이다.
- **공간복잡도**: `O(capacity)` — 최대 `capacity`개의 항목을 저장한다.

## 5. 엣지 케이스

- **`capacity = 1`**: 새 키가 들어올 때마다 기존 유일한 키가 즉시 제거된다.
- **이미 있는 키를 `put`으로 값만 갱신하는 경우**: 값이 바뀌는 것뿐 아니라, 그 키가 "가장 최근에 쓰인" 것으로도 갱신되어야 한다 — `remove` 후 재삽입이 이걸 보장한다. (실제로 "값만 갱신하고 순서는 그대로 두면 어떻게 되는지"까지 별도로 테스트해서, 갱신된 키가 LRU로 잘못 제거되지 않는지 확인했다.)
- **존재하지 않는 키로 `get`**: 그냥 `-1` 반환, 순서에 영향 없음.

## 6. 이런 방법도 있다 — 직접 이중 연결 리스트 + HashMap 구현

`LinkedHashMap`은 사실 "해시맵 + 이중 연결 리스트"를 자바 표준 라이브러리가 이미 구현해둔 것이다. 면접 등에서는 이 내부 구조를 **직접 구현**하도록 요구하는 경우도 많다. 직접 만들면 이런 모습이 된다.

```java
import java.util.HashMap;
import java.util.Map;

class LRUCache {
    class Node {
        int key, value;
        Node prev, next;
        Node(int key, int value) { this.key = key; this.value = value; }
    }

    private final int capacity;
    private final Map<Integer, Node> map = new HashMap<>();
    private final Node head = new Node(0, 0); // 더미 head: head.next가 항상 "가장 최근 사용"
    private final Node tail = new Node(0, 0); // 더미 tail: tail.prev가 항상 "가장 오래 안 씀(LRU)"

    public LRUCache(int capacity) {
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }

    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void insertToFront(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        Node node = map.get(key);
        remove(node);
        insertToFront(node); // 최근 사용으로 이동
        return node.value;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) remove(map.get(key));
        Node node = new Node(key, value);
        map.put(key, node);
        insertToFront(node);
        if (map.size() > capacity) {
            Node lru = tail.prev; // 가장 오래된 노드
            remove(lru);
            map.remove(lru.key);
        }
    }
}
```

**비교하면:**

| | `LinkedHashMap` (위 3.의 코드) | 직접 구현한 이중 연결 리스트 + `HashMap` |
|---|---|---|
| 코드 길이 | 짧음 (표준 라이브러리 재사용) | 김 (노드, 포인터 연산 직접 관리) |
| 내부 동작 이해 | 라이브러리에 감춰져 있음 | "왜 O(1)인지"를 직접 보여줌 |
| 실무/면접 적합성 | 실무에서는 이쪽이 자연스러움 | 자료구조 이해도를 확인하려는 면접에서 요구되기도 함 |

두 방식 모두 `O(1)` 평균 시간, `O(capacity)` 공간으로 동일하다. 정답은 같지만, 어떤 상황에서 어느 쪽을 쓸지는 "표준 라이브러리를 써도 되는가"에 달려 있다.

## 7. 관련 문제

같은 캐시 설계 계열, 난이도가 한 단계 올라간 짝 문제.

- [LFU Cache (LeetCode 460)](../460-lfu-cache/Solution.md) — "가장 오래 안 쓰인 것"뿐 아니라 "가장 적게 쓰인 것"까지 고려해야 하는 상위 버전. 해시맵+순서 유지 자료구조 하나로는 부족해서, 사용 횟수별 버킷 구조가 추가로 필요하다.
- [Logger Rate Limiter (LeetCode 359)](../359-logger-rate-limiter/Solution.md) — 해시맵으로 키(메시지)별 상태를 남겨 다음 호출에서 조회하는 설계 아이디어는 같지만, "순서/용량"이 아니라 "시간(10초 창) 통과 여부"만 판정하면 되는 더 단순한 형태다.
- [All O`one Data Structure (LeetCode 432)](../432-all-oone-data-structure/Solution.md) — 해시맵 + 이중 연결 리스트로 `O(1)`을 만드는 같은 뼈대. 다만 리스트의 정렬 기준이 "최근 사용 순"이 아니라 "등장 횟수 순"이고, 노드 하나가 키 하나가 아니라 같은 횟수를 가진 키들의 묶음이다.

> 이 섹션은 새 문제를 풀 때마다 갱신됩니다. 캐시 설계, 이중 연결 리스트 계열 문제를 풀면 이 목록에 서로 링크를 추가해주세요.

---

*이 문서는 [Problem.md](./Problem.md)의 문제 설명을 기반으로 상세 풀이를 정리한 것입니다.*
