# Container With Most Water

- 문제 번호: 11
- 링크: https://leetcode.com/problems/container-with-most-water/
- 난이도: Medium
- 태그: Array, Two Pointers, Greedy

## 문제 설명 (요약)

`n`개의 세로선이 있고, `i`번째 선의 높이는 `height[i]`다. 이 중 **두 선을 골라 그 사이에 물을 담을 때, 담을 수 있는 물의 최대량**을 구한다.

두 선 `i`, `j`(`i < j`)를 고르면, 담기는 물의 양은 `min(height[i], height[j]) * (j - i)`다(짧은 쪽 높이까지만 채울 수 있고, 폭은 두 선 사이 거리).

## 제약사항

- `n == height.length`
- `2 <= n <= 10^5`
- `0 <= height[i] <= 10^4`

## 함수 시그니처

```java
class Solution {
    public int maxArea(int[] height) {
        
    }
}
```

## 입출력 예

| height | 결과 |
|---|---|
| [1,8,6,2,5,4,8,3,7] | 49 |
| [1,1] | 1 |

### 입출력 예 설명

**입출력 예 #1**: 인덱스 `1`(높이 `8`)과 `8`(높이 `7`)을 고르면 `min(8,7) * (8-1) = 7*7 = 49`로 최대다.

**입출력 예 #2**: 선이 둘뿐이면 그 둘을 고를 수밖에 없다. `min(1,1) * (1-0) = 1`.
