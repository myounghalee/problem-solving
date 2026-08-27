# [프로그래머스 84021] 퍼즐 조각 채우기 — BFS(덩어리 추출) + 회전 매칭으로 풀기

- 문제 번호: 84021
- 링크: https://school.programmers.co.kr/learn/courses/30/lessons/84021
- 분류: 깊이/너비 우선 탐색(DFS/BFS)
- 사용 자료구조/알고리즘: **BFS(연결 요소 추출) + 좌표 회전/정규화 매칭**
- 난이도 체감: Lv.3 (알고리즘 자체보다, "모양을 어떻게 비교 가능한 형태로 만들지" 설계가 핵심)

## 1. 문제 요약

`game_board`(빈칸 0 / 채워진 칸 1)와 `table`(조각 1 / 빈칸 0)이 같은 크기의 정사각 격자로 주어진다. `table`의 조각들을 **회전만 허용**(뒤집기 불가)해서 `game_board`의 빈칸에 정확히 맞춰 끼워 넣을 때, 채울 수 있는 최대 칸 수를 구한다.

핵심 규칙 하나: "새로 채워 넣은 조각과 인접한 칸이 비어있으면 안 된다." 이 규칙을 뒤집어 생각하면, **조각은 게임 보드의 빈 구멍(0이 상하좌우로 이어진 덩어리) 하나를 정확히, 딱 맞게 채워야만 한다**는 뜻이 된다. 조각이 구멍보다 작으면 놓자마자 그 옆에 빈 칸이 남아 규칙 위반이기 때문이다.

```text
예시) 3x3 보드에서 4칸짜리 구멍(L자, 다리가 오른쪽 아래)과
      4칸짜리 조각(L자, 다리가 왼쪽 아래)이 있다면
      → 회전만으로는 절대 안 겹침(거울 대칭 관계) → 매칭 불가
```

**제한사항**
- 보드/테이블 크기: 3 ~ 50 (정사각형, 서로 같은 크기)
- 구멍/조각은 각각 1~6칸이 상하좌우로 연결된 형태

## 2. 접근 아이디어

문제를 세 단계로 쪼갠다.

1. **덩어리 추출**: `game_board`에서 0이 연결된 덩어리(구멍)들을, `table`에서 1이 연결된 덩어리(조각)들을 각각 BFS로 찾는다.
2. **모양 정규화**: 덩어리는 `(row, col)` 좌표 목록인데, 절대 위치는 중요하지 않고 **상대적인 모양**만 중요하다. 좌표들의 최소 행/최소 열을 기준으로 빼주면 위치와 무관하게 "모양"만 비교할 수 있다. 단, 좌표 목록의 순서 자체는 BFS 방문 순서라 구멍과 조각이 서로 다를 수 있으므로, 비교 전에 **행→열 순으로 정렬**해서 순서를 통일해야 원소별로 정확히 대응 비교할 수 있다.
3. **회전 매칭**: 조각의 좌표를 0/90/180/270도로 돌려가며, 그중 하나라도 어떤 구멍과 정규화된 모양이 완전히 같으면 그 조각으로 그 구멍을 채울 수 있다. 한 조각은 한 구멍에만 쓸 수 있으므로, 이미 쓰인 조각은 다시 후보에서 제외한다.

### 좌표 회전 공식

좌표 `(r, c)`를 90도 회전하면 `(c, -r)`이 된다. 이 변환을 4번 반복하면 원래 좌표로 돌아오므로, 4번 돌려보는 것만으로 0/90/180/270도를 전부 확인할 수 있다.

### 손으로 시뮬레이션 — `game_board = [[0,1],[1,0]], table = [[1,0],[0,1]]`

- 구멍: `(0,0)`과 `(1,1)` — 서로 붙어있지 않으므로 **각각 독립된 1칸짜리 구멍 2개**.
- 조각: `(0,0)`과 `(1,1)` — 마찬가지로 **각각 독립된 1칸짜리 조각 2개**.
- 1칸짜리 모양은 회전해도 항상 자기 자신과 같으므로, 구멍 2개 모두 조각과 매칭된다.
- 단, 조각이 2개뿐이므로 "이미 쓴 조각은 재사용 금지" 처리가 없으면 조각 1개가 두 구멍에 중복으로 쓰인 것처럼 잘못 계산될 수 있다. `used` 배열로 이를 막는다.
- 결과: `1 + 1 = 2`.

## 3. 코드 (Java)

```java
import java.util.*;

class Solution {
    public int solution(int[][] game_board, int[][] table) {
        List<int[][]> holes = new ArrayList<>();   // game_board의 빈칸(0) 덩어리들
        List<int[][]> pieces = new ArrayList<>();  // table의 조각(1) 덩어리들

        findBlocks(game_board, holes, 1);  // 1이 아닌(=0인) 칸들의 덩어리를 모음
        findBlocks(table, pieces, 0);      // 0이 아닌(=1인) 칸들의 덩어리를 모음

        boolean[] used = new boolean[pieces.size()]; // 이미 어떤 구멍에 쓰인 조각인지 표시
        int answer = 0;

        for (int[][] hole : holes) {
            for (int i = 0; i < pieces.size(); i++) {
                int[][] piece = pieces.get(i);
                if (!used[i] && hole.length == piece.length && matches(hole, piece)) {
                    answer += piece.length;
                    used[i] = true;
                    break; // 이 구멍은 채워졌으니 다음 구멍으로
                }
            }
        }

        return answer;
    }

    // piece를 0/90/180/270도로 돌려보며 hole과 같은 모양이 되는 각도가 있는지 확인
    private boolean matches(int[][] hole, int[][] piece) {
        int[][] rotated = piece.clone(); // 원본 piece는 그대로 두고 복사본만 회전
        for (int i = 0; i < 4; i++) {
            rotate(rotated);
            if (sameShape(hole, rotated)) return true;
        }
        return false;
    }

    // 좌표들을 90도 회전: (r, c) -> (c, -r)
    private void rotate(int[][] points) {
        for (int i = 0; i < points.length; i++) {
            points[i] = new int[]{points[i][1], -points[i][0]};
        }
    }

    // 두 좌표 집합이 이동(평행이동)만으로 완전히 겹쳐지는 같은 모양인지 비교
    private boolean sameShape(int[][] a, int[][] b) {
        Comparator<int[]> byRowThenCol = (p, q) -> p[0] != q[0] ? p[0] - q[0] : p[1] - q[1];
        Arrays.sort(a, byRowThenCol);
        Arrays.sort(b, byRowThenCol);

        int aMinRow = Integer.MAX_VALUE, aMinCol = Integer.MAX_VALUE;
        for (int[] p : a) {
            aMinRow = Math.min(aMinRow, p[0]);
            aMinCol = Math.min(aMinCol, p[1]);
        }
        int bMinRow = Integer.MAX_VALUE, bMinCol = Integer.MAX_VALUE;
        for (int[] p : b) {
            bMinRow = Math.min(bMinRow, p[0]);
            bMinCol = Math.min(bMinCol, p[1]);
        }

        for (int i = 0; i < a.length; i++) {
            int ar = a[i][0] - aMinRow, ac = a[i][1] - aMinCol;
            int br = b[i][0] - bMinRow, bc = b[i][1] - bMinCol;
            if (ar != br || ac != bc) return false;
        }
        return true;
    }

    // target 배열에서 no가 아닌 값을 가진 칸들을 상하좌우로 이어서 덩어리 단위로 모두 찾는다 (BFS)
    private void findBlocks(int[][] target, List<int[][]> blocks, int no) {
        int n = target.length;
        boolean[][] visited = new boolean[n][n];
        int[] dr = {1, -1, 0, 0}, dc = {0, 0, 1, -1};

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (target[r][c] == no || visited[r][c]) continue;

                List<int[]> block = new ArrayList<>();
                Deque<int[]> queue = new ArrayDeque<>();
                queue.add(new int[]{r, c});
                visited[r][c] = true;

                while (!queue.isEmpty()) {
                    int[] cur = queue.poll();
                    block.add(cur);
                    for (int d = 0; d < 4; d++) {
                        int nr = cur[0] + dr[d], nc = cur[1] + dc[d];
                        if (nr < 0 || nr >= n || nc < 0 || nc >= n) continue;
                        if (target[nr][nc] == no || visited[nr][nc]) continue;
                        visited[nr][nc] = true;
                        queue.add(new int[]{nr, nc});
                    }
                }
                blocks.add(block.toArray(new int[0][]));
            }
        }
    }
}
```

### 코드 설명

- `findBlocks`는 게임 보드/테이블 양쪽에 공통으로 쓰는 BFS 덩어리 추출 함수다. `no` 파라미터로 "무시할 값"을 넘겨서, 구멍(0 덩어리) 찾을 땐 `no=1`(1은 건너뜀), 조각(1 덩어리) 찾을 땐 `no=0`(0은 건너뜀)으로 재사용한다.
- `matches`에서 `piece.clone()`으로 복사본을 만들어 회전시키는 이유는, 원본 `piece` 좌표 배열은 다른 구멍과 비교할 때도 재사용되기 때문이다. 원본을 직접 돌려버리면 그다음 비교부터는 이미 회전된(원래 모양이 아닌) 상태로 비교하게 되어버린다.
- `sameShape`는 두 좌표 집합을 (1) 행→열 순으로 정렬해서 비교 순서를 통일하고, (2) 각자 최소 행/최소 열을 빼서 원점 기준으로 정규화한 뒤, (3) 순서대로 좌표가 완전히 일치하는지 확인한다. 정렬을 행만으로 하면 같은 행에 여러 좌표가 있을 때 순서가 안 정해져서 엉뚱한 좌표끼리 비교될 수 있으므로 열까지 2차 정렬 기준으로 써야 한다.
- `used` 배열은 한 조각이 여러 구멍에 중복으로 매칭되는 것을 막는다. 조각 크기(`piece.length`)가 같고 모양도 같은 조각이 여러 개 있을 수 있으므로, 매칭에 성공한 조각은 즉시 "사용됨"으로 표시하고 다음 구멍 탐색으로 넘어간다(`break`).

## 4. 복잡도 분석

- **덩어리 추출**: `O(n²)` — `n × n` 격자를 한 번씩 훑으며 BFS (각 칸은 최대 한 번만 방문).
- **매칭**: 구멍 개수를 `H`, 조각 개수를 `P`, 한 덩어리의 최대 칸 수를 `K`(≤6)라 하면, 구멍-조각 쌍마다 회전 4번 × 정렬(`O(K log K)`) × 비교(`O(K)`)이므로 매칭 전체는 대략 `O(H × P × K log K)`. `H`, `P`는 최대 `n²/1`(1칸짜리 조각이 아주 많은 극단적 경우)까지 갈 수 있지만, 실질적으로는 `n ≤ 50`이라 `n² = 2500` 수준이라 충분히 빠르다.

## 5. 엣지 케이스

- **뒤집어야만 같아지는 모양(거울 대칭)**: 회전 4번으로는 절대 일치하지 않으므로 자연스럽게 매칭 실패로 처리된다 (예시 2).
- **크기가 다른 구멍/조각**: `hole.length == piece.length` 체크로 애초에 비교조차 하지 않아 불필요한 연산을 건너뛴다.
- **같은 모양의 구멍/조각이 여러 개**: `used` 배열이 없으면 조각 하나가 여러 구멍에 중복 매칭되어 답이 부풀려질 수 있다 (실제로 이 버그를 겪고 고쳤다 — 아래 "겪었던 실수" 참고).
- **1칸짜리 구멍/조각**: 회전해도 항상 같은 모양이라 항상 매칭된다.

## 6. 겪었던 실수 (디버깅 기록)

풀이 과정에서 실제로 마주쳤던 버그 3가지를 기록해둔다 — 비슷한 "모양 비교 + 회전" 문제를 풀 때 참고할 만하다.

1. **회전 함수가 원본 배열을 직접 훼손**: 처음엔 `rotate()`가 넘겨받은 배열을 그 자리에서 영구적으로 바꿔버렸는데, 그 배열이 여러 번 재사용되는 객체라 다음 비교부터 이미 회전된 상태로 시작하는 문제가 있었다. → 복사본(`clone()`)을 만들어 그것만 회전시키도록 수정.
2. **회전은 시켜놓고 비교는 원본과 함**: 복사본을 만들어 회전은 시켰는데, 정작 `sameShape()` 호출 시 회전된 복사본이 아니라 원본을 넘기고 있어서 회전이 아무 의미가 없었다. → 회전된 복사본을 비교 대상으로 넘기도록 수정.
3. **정렬 기준이 부족해서 순서가 안 정해짐**: 좌표를 행(`row`)만 기준으로 정렬했더니, 같은 행에 좌표가 여러 개 있을 때 순서가 구멍/조각 사이에 다를 수 있어서 실제로는 같은 모양인데도 다르다고 판정되는 경우가 있었다. → 행이 같으면 열(`col`)로도 정렬하는 2차 기준을 추가.
4. **중복 매칭 방지 누락**: 조각별 "이미 사용됨" 표시가 없어서, 같은 모양의 조각이 여러 개 있을 때 한 조각이 여러 구멍에 중복으로 카운트되는 문제가 있었다. → `used` 배열 추가.

## 7. 관련 문제

같은 "격자에서 연결된 덩어리를 찾고, 모양을 정규화해서 비교"하는 계열 문제들. 이 저장소에 해당 문제 풀이가 추가되면 링크를 연결할 예정이다.

> 이 섹션은 새 문제를 풀 때마다 갱신됩니다. 격자 BFS/DFS, 도형 회전·모양 매칭 계열 문제를 풀면 이 목록에 서로 링크를 추가해주세요.

---

*이 문서는 [Problem.md](./Problem.md)의 문제 설명을 기반으로 상세 풀이를 정리한 것입니다.*
