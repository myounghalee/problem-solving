# LRU Cache

- 문제 번호: 146
- 링크: https://leetcode.com/problems/lru-cache/
- 난이도: Medium
- 태그: Hash Table, Linked List, Design, Doubly-Linked List

## 문제 설명 (요약)

**LRU(Least Recently Used) 캐시**의 동작 규칙을 따르는 자료구조를 설계한다. `LRUCache` 클래스를 구현한다:

- `LRUCache(int capacity)`: 양수 크기 `capacity`로 캐시를 초기화한다.
- `int get(int key)`: `key`가 존재하면 그 값을 반환하고, 없으면 `-1`을 반환한다.
- `void put(int key, int value)`: `key`가 이미 있으면 값을 갱신하고, 없으면 새로 추가한다. 이 동작으로 인해 저장된 키 개수가 `capacity`를 초과하게 되면, **가장 최근에 사용되지 않은(least recently used)** 키를 제거한다.

`get`과 `put` 모두 **평균 `O(1)` 시간복잡도**로 동작해야 한다. (`get`이나 `put`으로 어떤 키에 접근하면, 그 키는 "가장 최근에 사용된" 것으로 취급된다.)

## 제약사항

- `1 <= capacity <= 3000`
- `0 <= key <= 10^4`
- `0 <= value <= 10^5`
- `get`과 `put`을 합쳐 최대 `2 * 10^5`번 호출된다.

## 함수 시그니처

```java
class LRUCache {

    public LRUCache(int capacity) {

    }

    public int get(int key) {

    }

    public void put(int key, int value) {

    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
```

## 입출력 예

**입력**
```
["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
```

**출력**
```
[null, null, null, 1, null, -1, null, -1, 3, 4]
```

**설명**
```
LRUCache lRUCache = new LRUCache(2);
lRUCache.put(1, 1); // cache is {1=1}
lRUCache.put(2, 2); // cache is {1=1, 2=2}
lRUCache.get(1);    // return 1
lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
lRUCache.get(2);    // returns -1 (not found)
lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
lRUCache.get(1);    // return -1 (not found)
lRUCache.get(3);    // return 3
lRUCache.get(4);    // return 4
```
