# All O`one Data Structure

- 문제 번호: 432
- 링크: https://leetcode.com/problems/all-oone-data-structure/
- 난이도: Hard
- 태그: Hash Table, Linked List, Design, Doubly-Linked List

## 문제 설명 (요약)

문자열 키의 **등장 횟수(count)** 를 관리하면서, 그중 **가장 많은 키**와 **가장 적은 키**를 즉시 알려주는 자료구조를 설계한다.

`AllOne` 클래스를 구현한다:

- `AllOne()`: 자료구조를 초기화한다.
- `void inc(String key)`: `key`의 횟수를 1 증가시킨다. `key`가 없으면 횟수 `1`로 새로 넣는다.
- `void dec(String key)`: `key`의 횟수를 1 감소시킨다. 감소 후 횟수가 `0`이 되면 그 키를 **제거**한다. `dec` 호출 시 `key`는 반드시 존재함이 보장된다.
- `String getMaxKey()`: 횟수가 가장 큰 키 **하나**를 반환한다. 비어 있으면 `""`.
- `String getMinKey()`: 횟수가 가장 작은 키 **하나**를 반환한다. 비어 있으면 `""`.

**네 연산 모두 평균 `O(1)` 시간복잡도**로 동작해야 한다. 이 요구가 이 문제의 전부라고 해도 좋다.

횟수가 같은 키가 여러 개일 때 그중 어느 것을 반환해도 정답으로 인정된다.

## 제약사항

- `1 <= key.length <= 10`
- `key`는 소문자 영어 알파벳으로만 구성된다.
- `dec` 호출 시 `key`는 자료구조 안에 존재함이 보장된다.
- `inc`, `dec`, `getMaxKey`, `getMinKey`를 합쳐 최대 `5 * 10^4`번 호출된다.

## 함수 시그니처

```java
class AllOne {

    public AllOne() {
        
    }
    
    public void inc(String key) {
        
    }
    
    public void dec(String key) {
        
    }
    
    public String getMaxKey() {
        
    }
    
    public String getMinKey() {
        
    }
}
```

## 입출력 예

```text
입력:
["AllOne", "inc", "inc", "getMaxKey", "getMinKey", "inc", "getMaxKey", "getMinKey"]
[[],       ["hello"], ["hello"], [],     [],          ["leet"], [],       []]

출력:
[null, null, null, "hello", "hello", null, "hello", "leet"]
```

### 입출력 예 설명

| 호출 | 상태 (키: 횟수) | 반환 |
|---|---|---|
| `AllOne()` | `{}` | — |
| `inc("hello")` | `{hello: 1}` | — |
| `inc("hello")` | `{hello: 2}` | — |
| `getMaxKey()` | `{hello: 2}` | `"hello"` |
| `getMinKey()` | `{hello: 2}` | `"hello"` (키가 하나뿐이라 최대이자 최소) |
| `inc("leet")` | `{hello: 2, leet: 1}` | — |
| `getMaxKey()` | `{hello: 2, leet: 1}` | `"hello"` |
| `getMinKey()` | `{hello: 2, leet: 1}` | `"leet"` |
