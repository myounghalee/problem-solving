# LFU Cache

- 문제 번호: 460
- 링크: https://leetcode.com/problems/lfu-cache/
- 난이도: Hard
- 태그: Hash Table, Linked List, Design, Doubly-Linked List

## 문제 설명 (요약)

**LFU(Least Frequently Used) 캐시**를 설계한다. [146번(LRU Cache)](../146-lru-cache/Problem.md)와 비슷하지만, 제거 기준이 "가장 오래 안 쓰인 것"이 아니라 **"가장 적게 쓰인 것"**이라는 점이 다르다.

`LFUCache` 클래스를 구현한다:

- `LFUCache(int capacity)`: 용량 `capacity`로 캐시를 초기화한다.
- `int get(int key)`: `key`가 있으면 값을 반환, 없으면 `-1`.
- `void put(int key, int value)`: `key`가 있으면 값 갱신, 없으면 추가. 용량을 초과하면 **사용 횟수(use counter)가 가장 작은 키**를 제거한다. 사용 횟수가 같은 키가 여러 개면(동점), 그중 **가장 오래 안 쓰인(LRU) 키**를 제거한다.

각 키마다 "사용 횟수"를 추적한다. 키가 처음 `put`으로 삽입되면 사용 횟수는 `1`이 된다. 이후 그 키에 대해 `get` 또는 `put`이 호출될 때마다 사용 횟수가 1씩 증가한다.

`get`과 `put` 모두 **평균 `O(1)` 시간복잡도**로 동작해야 한다.

## 제약사항

- `1 <= capacity <= 10^4`
- `0 <= key <= 10^5`
- `0 <= value <= 10^9`
- `get`과 `put`을 합쳐 최대 `2 * 10^5`번 호출된다.

## 함수 시그니처

```java
class LFUCache {

    public LFUCache(int capacity) {

    }

    public int get(int key) {

    }

    public void put(int key, int value) {

    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
```

## 입출력 예

**입력**
```
["LFUCache", "put", "put", "get", "put", "get", "get", "put", "get", "get", "get"]
[[2], [1, 1], [2, 2], [1], [3, 3], [2], [3], [4, 4], [1], [3], [4]]
```

**출력**
```
[null, null, null, 1, null, -1, 3, null, -1, 3, 4]
```

**설명** (`cnt(x)` = 키 `x`의 사용 횟수, `cache=[...]`의 왼쪽이 더 최근에 쓰인 것)
```
LFUCache lfu = new LFUCache(2);
lfu.put(1, 1); // cache=[1,_], cnt(1)=1
lfu.put(2, 2); // cache=[2,1], cnt(2)=1, cnt(1)=1
lfu.get(1);    // return 1
               // cache=[1,2], cnt(2)=1, cnt(1)=2
lfu.put(3, 3); // cnt(2)=1이 가장 작아서 2를 제거. cache=[3,1], cnt(3)=1, cnt(1)=2
lfu.get(2);    // return -1 (찾을 수 없음)
lfu.get(3);    // return 3
               // cache=[3,1], cnt(3)=2, cnt(1)=2
lfu.put(4, 4); // 1과 3의 cnt가 같은데(동점), 1이 더 오래 안 쓰였으므로 1을 제거. cache=[4,3], cnt(4)=1, cnt(3)=2
lfu.get(1);    // return -1 (찾을 수 없음)
lfu.get(3);    // return 3
               // cache=[3,4], cnt(4)=1, cnt(3)=3
lfu.get(4);    // return 4
               // cache=[4,3], cnt(4)=2, cnt(3)=3
```
