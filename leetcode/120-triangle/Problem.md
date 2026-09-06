# Triangle

- 문제 번호: 120
- 링크: https://leetcode.com/problems/triangle/
- 난이도: Medium
- 태그: Array, Dynamic Programming

## 문제 설명 (요약)

삼각형 모양으로 배열된 숫자들이 `triangle`로 주어진다. 맨 위(0번째 줄)에서 시작해, **각 줄에서 바로 아래 줄의 인접한 두 숫자 중 하나로만** 내려가며 맨 아래 줄까지 이동한다. 이렇게 이동하며 지나간 숫자들의 **합의 최솟값**을 구한다.

`i`번째 줄의 `j`번째 숫자에서는, `i+1`번째 줄의 `j`번째 또는 `j+1`번째 숫자로만 내려갈 수 있다.

## 제약사항

- `1 <= triangle.length <= 200`
- `triangle[0].length == 1`
- `triangle[i].length == triangle[i - 1].length + 1`
- `-10^4 <= triangle[i][j] <= 10^4`

**추가 조건(Follow-up)**: 삼각형 전체를 담는 공간 외에, `O(n)`(전체 줄 수) 추가 공간만 써서 풀 수 있는가?

## 함수 시그니처

```java
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        
    }
}
```

## 입출력 예

| triangle | 결과 |
|---|---|
| [[2],[3,4],[6,5,7],[4,1,8,3]] | 11 |
| [[-10]] | -10 |

### 입출력 예 설명

**입출력 예 #1**:

```text
     2
    3 4
   6 5 7
  4 1 8 3
```

`2 → 3 → 5 → 1` 경로의 합이 `2+3+5+1=11`로 최소다.

**입출력 예 #2**: 줄이 하나뿐이면 그 값 자체가 답이다.
