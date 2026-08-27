# [프로그래머스 42628] 이중우선순위큐 — 최소 힙 + 최대 힙을 동시에, 지연 삭제로 동기화

- 문제 번호: 42628
- 링크: https://school.programmers.co.kr/learn/courses/30/lessons/42628
- 분류: 힙(Heap)
- 사용 자료구조/알고리즘: **최소 힙 + 최대 힙 (지연 삭제)**, 또는 `TreeMap`
- 난이도 체감: Lv.3 (힙 두 개를 "동기화"하는 부분이 이 문제의 진짜 난관)

## 1. 문제 요약

`"I 숫자"`(삽입), `"D 1"`(최댓값 삭제), `"D -1"`(최솟값 삭제) 연산을 순서대로 처리한 뒤, 남은 원소가 있으면 `[최댓값, 최솟값]`, 없으면 `[0, 0]`을 반환한다. 최댓값/최솟값이 여러 개면 하나만 지우고, 빈 큐에 삭제 연산이 오면 무시한다.

## 2. 접근 아이디어 — 힙 두 개를 쓸 때 진짜 어려운 지점

최댓값도 빠르게, 최솟값도 빠르게 다뤄야 하므로 **최소 힙 하나와 최대 힙 하나를 동시에** 유지하고 싶어진다. 문제는 이거다:

> `"D 1"`로 최대 힙에서 값 하나를 실제로 꺼내 지워도, **최소 힙에는 그 값이 여전히 남아있다.** 두 힙의 내용물이 서로 어긋나기 시작한다.

이걸 해결하는 표준 기법이 **지연 삭제(lazy deletion)**다. 값을 진짜로 양쪽 힙에서 동시에 지우는 대신, "이 힙의 이 값은 사실 이미 지워진 것으로 쳐줘"라는 **표식(카운트)만 남겨두고, 나중에 그 힙의 맨 위(peek)를 확인할 때 표식이 있으면 그제서야 실제로 꺼내서 버린다.**

### 처음 시도에서 만난 함정 — 표식을 헷갈리게 공유하면 틀린다

처음에 "지워진 값의 개수"를 값 하나당 카운트 하나로만 (양쪽 힙 공통으로) 관리했더니, 아래와 같은 경우에서 틀렸다:

```text
5를 두 번 삽입 → minHeap=[5,5], maxHeap=[5,5]
"D 1" (최댓값 삭제): maxHeap에서 5 하나를 꺼내 지움. "5가 다른 힙에 하나 남아있으니 나중에 지워라"라는 표식을 남김.
"D 1" (또 최댓값 삭제): maxHeap을 보니 top이 5인데, 표식이 남아있네? → 이걸 "이미 지워진 것"으로 착각하고 그냥 버려버림!
```

문제는 이 표식이 **"어느 힙에서 걸러내야 하는 표식인지" 구분이 없었다**는 것이다. `maxHeap`에서 만든 표식이 (원래는 `minHeap`의 잔여물을 걸러내라는 뜻인데) 정작 `maxHeap` 자신의 **아직 안 지워진 정상적인 중복값**을 걸러내는 데 잘못 쓰여버린 것. 그래서 **표식을 힙별로 분리**해야 한다:

- `staleInMin`: "이 값 하나는 `minHeap`에서 걸러내야 한다" (→ `maxHeap`에서 삭제가 일어났을 때 남긴다)
- `staleInMax`: "이 값 하나는 `maxHeap`에서 걸러내야 한다" (→ `minHeap`에서 삭제가 일어났을 때 남긴다)

이렇게 **삭제가 일어난 힙 자신이 아니라 반대편 힙에 대한 표식만 남기도록** 분리하면, 자기 자신의 정상적인 중복값을 잘못 걸러내는 일이 없어진다.

## 3. 코드 (Java)

```java
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class Solution {
    public int[] solution(String[] operations) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        Map<Integer, Integer> staleInMin = new HashMap<>(); // maxHeap 삭제로 생긴, minHeap이 걸러내야 할 표식
        Map<Integer, Integer> staleInMax = new HashMap<>(); // minHeap 삭제로 생긴, maxHeap이 걸러내야 할 표식
        int size = 0;

        for (String operation : operations) {
            String[] token = operation.split(" ");
            int value = Integer.parseInt(token[1]);

            if (token[0].equals("I")) {
                minHeap.add(value);
                maxHeap.add(value);
                size++;
            } else if (value == 1) {          // D 1: 최댓값 삭제
                clean(maxHeap, staleInMax);
                if (!maxHeap.isEmpty()) {
                    int v = maxHeap.poll();
                    staleInMin.merge(v, 1, Integer::sum);
                    size--;
                }
            } else {                           // D -1: 최솟값 삭제
                clean(minHeap, staleInMin);
                if (!minHeap.isEmpty()) {
                    int v = minHeap.poll();
                    staleInMax.merge(v, 1, Integer::sum);
                    size--;
                }
            }
        }

        if (size == 0) return new int[]{0, 0};
        clean(maxHeap, staleInMax);
        clean(minHeap, staleInMin);
        return new int[]{maxHeap.peek(), minHeap.peek()};
    }

    private void clean(PriorityQueue<Integer> heap, Map<Integer, Integer> stale) {
        while (!heap.isEmpty() && stale.getOrDefault(heap.peek(), 0) > 0) {
            int v = heap.poll();
            stale.merge(v, -1, Integer::sum);
        }
    }
}
```

### 코드 설명

- `size`는 "논리적으로 살아있는" 원소 개수를 별도로 추적한다 — 힙 내부에는 지연 삭제 때문에 표식만 붙은 "죽은" 원소들이 섞여 있을 수 있어서, `힙.size()`만으로는 진짜 원소 수를 알 수 없다.
- `clean()`은 힙의 맨 위(top)가 표식 붙은 죽은 값일 동안 계속 실제로 꺼내서 버린다 — 살아있는 진짜 top이 나올 때까지.
- `"D 1"`에서 `maxHeap`을 정리(clean)해서 진짜 최댓값을 찾아 꺼내고, 그 값이 `minHeap`에도 남아있을 테니 `staleInMin`에 표식을 남긴다. `"D -1"`은 정확히 대칭.
- 마지막 조회 시에도 양쪽 힙을 한 번씩 정리해야, `peek()`이 죽은 값이 아니라 진짜 살아있는 최댓값/최솟값을 가리킨다.

## 4. 복잡도 분석

- **시간복잡도**: `O(n log n)` — 모든 힙 연산(`add`/`poll`/`peek`)이 `O(log n)`이고, `clean()`에서 버려지는 원소들도 각자 평생 최대 한 번만 poll되므로(전체 poll 횟수가 삽입 횟수를 넘지 않음) amortized로 선형 배수만 붙는다.
- **공간복잡도**: `O(n)` — 두 힙 + 표식 맵.

실측(`n = 1,000,000`, 무작위 연산): **약 143ms**.

### 참고: `TreeMap`으로 훨씬 간단하게

값 → 개수를 저장하는 `TreeMap<Integer,Integer>`(정렬된 멀티셋) 하나만으로도 같은 문제를 풀 수 있다. `firstKey()`/`lastKey()`로 최소/최대를 즉시 얻고, 삭제 시 카운트를 줄이거나 키를 지우면 된다 — 힙 두 개를 동기화하는 복잡함이 아예 없다.

| 방식 | 실측 (n=1,000,000) |
|---|---|
| 최소 힙 + 최대 힙 (지연 삭제) | 약 143ms |
| `TreeMap` 멀티셋 | **약 86ms** (더 빠르고 코드도 단순) |

이 저장소는 "힙(Heap)" 카테고리 문제라 힙 기반 풀이를 메인으로 다뤘지만, 실전에서는 `TreeMap` 버전이 더 낫다.

## 5. 더 간단한 대안 — `PriorityQueue.remove(Object)`, 그런데 왜 위험한가

지연 삭제 없이, 힙 두 개를 그냥 독립적으로 유지하면서 삭제 시 **반대편 힙에서 그 값을 직접 찾아 지우는** 방식도 가능하다.

```java
Queue<Integer> minpq = new PriorityQueue<>();
Queue<Integer> maxpq = new PriorityQueue<>(Collections.reverseOrder());
...
// D -1 처리
maxpq.remove(minpq.poll());
// D 1 처리
minpq.remove(maxpq.poll());
```

코드가 훨씬 짧고 읽기 쉽다. 3,000회 무작위 대조 결과 **정답도 완전히 일치**한다 — 로직 자체는 정확하다.

문제는 `Queue.remove(Object)`가 힙 내부를 **처음부터 끝까지 선형 탐색**해서 값을 찾는다는 점이다 (힙은 "맨 위" 조회/삭제만 `O(log n)`이지, "임의의 값 하나 찾아 지우기"는 최적화돼 있지 않다 — `O(n)`이 걸린다). 삽입과 삭제가 많이 섞이면 이게 누적돼 `O(n²)`에 가까워진다.

`n=1,000,000`, "앞쪽 절반은 전부 삽입, 뒤쪽 절반은 전부 삭제"처럼 삭제가 몰리는 패턴으로 직접 실측했다:

| 방식 | 실행 시간 |
|---|---|
| `remove(Object)` 버전 | **약 74,000ms (74초)** |
| 지연 삭제 버전 | 약 141ms |

**약 525배** 차이가 난다. 정답은 맞지만 이 문제의 `n ≤ 1,000,000` 규모에서는 시간 초과가 사실상 확정적이다. "코드가 짧고 명확한 것"과 "채점을 통과하는 것"이 항상 같이 가지는 않는다는 걸 잘 보여주는 사례다 — 작은 입력에서만 검증하고 넘어가면 이 함정을 놓치기 쉽다.

## 6. 검증

**중복값이 몰린 경우를 포함해** 5,000회 무작위 데이터로, `TreeMap` 기반 참조 구현과 대조해서 전부 일치함을 확인했다. 공식 예시 2개도 통과한다.

## 7. 엣지 케이스

- 빈 큐에 `D 1` 또는 `D -1`: 두 경우 모두 아무 일도 일어나지 않아야 한다 (`clean()` 이후 힙이 비어있으면 그냥 통과).
- 같은 값이 여러 번 삽입된 경우: 지연 삭제 표식이 힙별로 분리돼 있어야 정확히 처리된다 (위 3번 섹션 참고).
- 모든 연산이 삽입뿐이고 삭제가 하나도 없는 경우: 그대로 전체의 최댓값/최솟값 반환.

## 8. 관련 문제

- [더 맵게 (42626)](../42626-더맵게/Solution.md) — 최소 힙 하나만 필요했던 문제. 이 문제는 최소/최대 힙을 **동시에** 다뤄야 해서 한 단계 더 나아간 형태다.

---

*이 문서는 [Problem.md](./Problem.md)의 문제 설명을 기반으로 상세 풀이를 정리한 것입니다.*
