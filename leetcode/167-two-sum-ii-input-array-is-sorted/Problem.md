# Two Sum II - Input Array Is Sorted

- 문제 번호: 167
- 링크: https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/
- 난이도: Medium
- 태그: Array, Two Pointers, Binary Search

## 문제 설명 (요약)

**1-인덱스** 기반이고 **오름차순(정확히는 non-decreasing) 정렬되어 있는** 정수 배열 `numbers`와 정수 `target`이 주어진다. 합이 `target`이 되는 두 수 `numbers[index1]`, `numbers[index2]`(`1 <= index1 < index2 <= numbers.length`)를 찾아, 그 인덱스를 `[index1, index2]` 형태(1-인덱스 그대로)로 반환한다.

[1번(Two Sum)](../1-two-sum/Problem.md)과 사실상 같은 문제이지만, 이번엔 **배열이 이미 정렬되어 있다는 조건**과 **상수 추가 공간만 써야 한다는 제약**이 추가됐다 — 그래서 해시맵을 쓰는 대신 정렬된 배열의 특성을 활용해야 한다.

- 같은 원소를 두 번 쓸 수 없다.
- 정답은 항상 정확히 하나 존재한다고 가정해도 된다.
- **반환하는 인덱스는 0이 아니라 1부터 시작**한다(`index1 + 1`, `index2 + 1`이 아니라, 애초에 1-인덱스 배열 기준의 인덱스를 그대로 반환).

## 제약사항

- `2 <= numbers.length <= 3 * 10^4`
- `-1000 <= numbers[i] <= 1000`
- `numbers`는 오름차순(non-decreasing) 정렬되어 있다.
- `-1000 <= target <= 1000`
- 정답은 항상 유일하게 존재한다.
- **추가 공간은 상수(O(1))만 사용해야 한다.**

## 함수 시그니처

```java
class Solution {
    public int[] twoSum(int[] numbers, int target) {
        
    }
}
```

## 입출력 예

| numbers | target | 결과 |
|---|---|---|
| [2,7,11,15] | 9 | [1,2] |
| [2,3,4] | 6 | [1,3] |
| [-1,0] | -1 | [1,2] |

### 입출력 예 설명

**입출력 예 #1**: `2 + 7 = 9` → 1-인덱스 기준 `index1=1, index2=2` → `[1, 2]`

**입출력 예 #2**: `2 + 4 = 6` → `index1=1, index2=3` → `[1, 3]`

**입출력 예 #3**: `-1 + 0 = -1` → `index1=1, index2=2` → `[1, 2]`
