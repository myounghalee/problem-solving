# Design Hit Counter

- 문제 번호: 362
- 링크: https://leetcode.com/problems/design-hit-counter/
- 난이도: Medium
- 태그: Design, Queue, Binary Search, Data Stream

## 문제 설명 (요약)

지난 5분(300초) 동안 발생한 "히트(hit)" 수를 집계하는 히트 카운터를 설계한다.

`HitCounter` 클래스를 구현한다:

- `HitCounter()`: 히트 카운터 객체를 초기화한다.
- `void hit(int timestamp)`: `timestamp`(초 단위)에 히트가 발생했음을 기록한다. 같은 `timestamp`에 여러 번 히트가 발생할 수 있다.
- `int getHits(int timestamp)`: `timestamp` 기준으로 **지난 300초 이내**(`[timestamp - 299, timestamp]` 구간)에 발생한 히트 수를 반환한다.

## 제약사항

- `1 <= timestamp <= 2 * 10^9`
- `hit`과 `getHits`에 대한 모든 호출은 **타임스탬프가 단조 증가하는(시간순) 순서**로 이루어진다.
- `hit`과 `getHits`는 합쳐서 최대 `300`번 호출된다.

## 함수 시그니처

```java
class HitCounter {

    public HitCounter() {

    }

    public void hit(int timestamp) {

    }

    public int getHits(int timestamp) {

    }
}

/**
 * Your HitCounter object will be instantiated and called as such:
 * HitCounter obj = new HitCounter();
 * obj.hit(timestamp);
 * int param_2 = obj.getHits(timestamp);
 */
```

## 입출력 예

**입력**
```
["HitCounter", "hit", "hit", "hit", "getHits", "hit", "getHits", "getHits"]
[[], [1], [2], [3], [4], [300], [300], [301]]
```

**출력**
```
[null, null, null, null, 3, null, 4, 3]
```

**설명**
```
HitCounter hitCounter = new HitCounter();
hitCounter.hit(1);       // 타임스탬프 1에 히트
hitCounter.hit(2);       // 타임스탬프 2에 히트
hitCounter.hit(3);       // 타임스탬프 3에 히트
hitCounter.getHits(4);   // 타임스탬프 4 기준 히트 수 조회 → 3
hitCounter.hit(300);     // 타임스탬프 300에 히트
hitCounter.getHits(300); // 타임스탬프 300 기준 → 4 (구간 [1, 300] 전부 포함)
hitCounter.getHits(301); // 타임스탬프 301 기준 → 3 (구간 [2, 301], 타임스탬프 1은 제외)
```
