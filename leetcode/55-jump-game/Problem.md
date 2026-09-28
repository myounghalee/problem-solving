# Jump Game

- 문제 번호: 55
- 링크: https://leetcode.com/problems/jump-game/
- 난이도: Medium
- 태그: Array, Dynamic Programming, Greedy

## 문제 설명 (요약)

정수 배열 `nums`가 주어진다. 처음엔 배열의 첫 번째 인덱스(`0`)에 있다. `nums[i]`는 인덱스 `i`에서 앞으로 최대 몇 칸까지 점프할 수 있는지를 나타낸다(`0`부터 `nums[i]`칸까지 중 원하는 만큼 이동 가능).

마지막 인덱스(`nums.length - 1`)에 도달할 수 있으면 `true`, 도달할 수 없으면 `false`를 반환한다.

## 제약사항

- `1 <= nums.length <= 10^4`
- `0 <= nums[i] <= 10^5`

## 함수 시그니처

```java
class Solution {
    public boolean canJump(int[] nums) {

    }
}
```

## 입출력 예

| nums (입력) | 반환값 |
|---|---|
| [2,3,1,1,4] | true |
| [3,2,1,0,4] | false |

### 입출력 예 설명

**입출력 예 #1**: 인덱스 `0`(값 2)에서 인덱스 `1`(값 3)로 이동, 거기서 인덱스 `4`(마지막 인덱스)까지 한 번에 점프할 수 있다. `true`.

**입출력 예 #2**: 어떻게 이동해도 인덱스 `3`(값 0)에 도달하면 더 이상 앞으로 나아갈 수 없어서 마지막 인덱스(`4`)에 도달하지 못한다. `false`.
