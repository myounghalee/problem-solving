# Minimum Size Subarray Sum

- 문제 번호: 209
- 링크: https://leetcode.com/problems/minimum-size-subarray-sum/
- 난이도: Medium
- 태그: Array, Binary Search, Sliding Window, Prefix Sum

## 문제 설명 (요약)

양의 정수로만 이루어진 배열 `nums`와 양의 정수 `target`이 주어질 때, **합이 `target` 이상이 되는 부분 배열(연속된 구간) 중 가장 짧은 것의 길이**를 구한다. 그런 부분 배열이 없으면 `0`을 반환한다.

## 제약사항

- `1 <= target <= 10^9`
- `1 <= nums.length <= 10^5`
- `1 <= nums[i] <= 10^4`

**추가 조건(Follow-up)**: `O(n)` 풀이를 찾았다면, `O(n log n)` 풀이도 시도해볼 것.

## 함수 시그니처

```java
class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        
    }
}
```

## 입출력 예

| target | nums | 결과 |
|---|---|---|
| 7 | [2,3,1,2,4,3] | 2 |
| 4 | [1,4,4] | 1 |
| 11 | [1,1,1,1,1,1,1,1] | 0 |

### 입출력 예 설명

**입출력 예 #1**: `[4,3]`(합 `7`)의 길이 `2`가 최소다.

**입출력 예 #2**: `[4]` 하나만으로 이미 `4`를 넘는다.

**입출력 예 #3**: 배열 전체의 합이 `8`로 `11`에 못 미쳐, 어떤 부분 배열도 조건을 만족하지 못한다.
