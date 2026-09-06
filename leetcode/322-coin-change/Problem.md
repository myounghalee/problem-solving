# Coin Change

- 문제 번호: 322
- 링크: https://leetcode.com/problems/coin-change/
- 난이도: Medium
- 태그: Array, Dynamic Programming, Breadth-First Search

## 문제 설명 (요약)

서로 다른 액면의 동전 종류 `coins`(각 종류는 무한히 있다고 가정)와 목표 금액 `amount`가 주어질 때, `amount`를 만드는 데 필요한 **동전 개수의 최솟값**을 구한다. 만들 수 없으면 `-1`을 반환한다.

## 제약사항

- `1 <= coins.length <= 12`
- `1 <= coins[i] <= 2^31 - 1`
- `0 <= amount <= 10^4`

## 함수 시그니처

```java
class Solution {
    public int coinChange(int[] coins, int amount) {
        
    }
}
```

## 입출력 예

| coins | amount | 결과 |
|---|---|---|
| [1,2,5] | 11 | 3 |
| [2] | 3 | -1 |
| [1] | 0 | 0 |

### 입출력 예 설명

**입출력 예 #1**: `11 = 5 + 5 + 1`로 동전 3개면 충분하다.

**입출력 예 #2**: 동전이 `2`뿐인데 `3`은 홀수라 어떤 개수로도 만들 수 없다.

**입출력 예 #3**: 목표 금액이 `0`이면 동전을 하나도 안 써도 되므로 `0`.
