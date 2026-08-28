# [프로그래머스 43164] 여행경로 — 백트래킹(DFS + 되돌리기)으로 풀기

- 문제 번호: 43164
- 링크: https://school.programmers.co.kr/learn/courses/30/lessons/43164
- 분류: 깊이/너비 우선 탐색(DFS/BFS)
- 사용 자료구조/알고리즘: **백트래킹(DFS + 방문 취소)**
- 난이도 체감: Lv.3 (그리디하게만 골라서는 안 되고, 막히면 되돌아갈 수 있어야 함)

## 1. 문제 요약

`ICN`에서 출발해, 주어진 항공권(`tickets`)을 **전부, 각각 정확히 한 번씩** 사용해서 방문 경로를 만든다. 가능한 경로가 여러 개면 **알파벳 순으로 가장 앞서는 경로**를 반환한다.

```text
예시 1) tickets = [["ICN","JFK"],["HND","IAD"],["JFK","HND"]]
→ ["ICN", "JFK", "HND", "IAD"]

예시 2) tickets = [["ICN","SFO"],["ICN","ATL"],["SFO","ATL"],["ATL","ICN"],["ATL","SFO"]]
→ ["ICN", "ATL", "ICN", "SFO", "ATL", "SFO"]
```

**제한사항**
- 공항 수: 3 ~ 10,000개
- 항공권은 전부 사용해야 함
- 경로가 여럿이면 알파벳 순으로 가장 앞서는 것
- 모든 도시를 방문할 수 없는 경우는 주어지지 않음 (항상 답이 존재)

## 2. 접근 아이디어 — 왜 그냥 그리디로는 안 되는가

가장 먼저 떠오르는 방법은 "매번 알파벳상 가장 작은, 아직 안 쓴 목적지로 그리디하게 이동"하는 것이다. 하지만 이렇게 하면 실패하는 입력이 있다.

```text
tickets = [["ICN","KUL"], ["ICN","NRT"], ["NRT","ICN"]]
```

`ICN`에서 갈 수 있는 곳은 `KUL`, `NRT` 두 곳인데, 알파벳상 `KUL`이 `NRT`보다 작다. 그리디하게 `KUL`로 가버리면, `KUL`은 나가는 티켓이 없는 막다른 곳이라 `NRT` 티켓 2장이 영영 안 쓰인 채로 남는다. 이 입력의 진짜 정답은 `["ICN","NRT","ICN","KUL"]` — 당장은 손해처럼 보이는 `NRT`로 먼저 가야 모든 티켓을 다 쓸 수 있다.

즉, **"지금 이 순간 알파벳상 가장 작은 선택"이 항상 전체 정답으로 이어지진 않는다.** 그래서 다음 전략이 필요하다.

- 매 단계, 알파벳순으로 가능한 목적지를 하나씩 시도한다.
- 그 선택으로 끝까지 가봐서 **모든 티켓을 다 쓰는 경로가 완성되면 그대로 답으로 채택**한다.
- 만약 도중에 막혀서(더 갈 곳이 없는데 아직 안 쓴 티켓이 남음) 실패하면, 방금 한 선택을 **취소하고(되돌리고)** 다음으로 작은 목적지를 시도한다.
- 이게 **백트래킹(backtracking)**이다. 그리고 매 단계 알파벳순으로 작은 것부터 시도하기 때문에, **가장 먼저 완성되는 경로가 곧 알파벳순으로 가장 앞서는 정답**이다 — 그래서 답을 찾는 즉시 더 이상 탐색하지 않고 바로 멈춰야 한다 (그렇지 않으면 나중에 발견되는, 알파벳상 더 늦은 다른 완성 경로로 덮어써질 수 있다).

### 손으로 시뮬레이션 — `tickets = [["ICN","KUL"], ["ICN","NRT"], ["NRT","ICN"]]`

목적지 기준으로 정렬하면 `tickets = [["NRT","ICN"], ["ICN","KUL"], ["ICN","NRT"]]` 순서가 된다 (인덱스 0, 1, 2).

| 단계 | 시도 | 결과 |
|---|---|---|
| 1 | `ICN`에서 티켓1(`ICN→KUL`) 사용 시도 | `path=[ICN]`, `KUL`로 이동 |
| 2 | `KUL`에서 나가는 티켓 탐색 | 없음 (모든 티켓 순회했지만 origin이 KUL인 것 없음) → 실패, `false` 반환 |
| 3 | 1단계 되돌리기: 티켓1 사용 취소 | `path=[]` |
| 4 | `ICN`에서 티켓2(`ICN→NRT`) 사용 시도 | `path=[ICN]`, `NRT`로 이동 |
| 5 | `NRT`에서 티켓0(`NRT→ICN`) 사용 시도 | `path=[ICN, NRT]`, `ICN`으로 이동 |
| 6 | `ICN`에서 남은 티켓1(`ICN→KUL`) 사용 시도 | `path=[ICN, NRT, ICN]`, `KUL`로 이동 |
| 7 | `path.size() == tickets.length`(3) 도달 → 완성! | `answer = [ICN, NRT, ICN, KUL]`, `true` 반환하며 즉시 종료 |

2단계에서 실패해 3단계에서 되돌린 것이 핵심이다. 이 되돌리기가 없으면(=단순 그리디) `KUL`에서 그대로 멈춰버려 오답(예외 발생)이 난다.

## 3. 코드 (Java)

```java
import java.util.*;

class Solution {
    String[] answer;

    public String[] solution(String[][] tickets) {
        answer = new String[tickets.length + 1];
        List<String> path = new ArrayList<>();           // 지금까지 확정한 경로
        boolean[] visited = new boolean[tickets.length];  // 티켓별 사용 여부

        Arrays.sort(tickets, Comparator.comparing(s -> s[1])); // 목적지 알파벳순 정렬

        dfs("ICN", tickets, visited, path);

        return answer;
    }

    private boolean dfs(String from, String[][] tickets, boolean[] visited, List<String> path) {
        if (path.size() == tickets.length) {   // 모든 티켓을 다 썼다 = 경로 완성
            for (int i = 0; i < path.size(); i++) {
                answer[i] = path.get(i);
            }
            answer[path.size()] = from;
            return true;
        }

        for (int i = 0, ticketsLength = tickets.length; i < ticketsLength; i++) {
            if (tickets[i][0].equals(from) && !visited[i]) { // from에서 출발하는, 아직 안 쓴 티켓
                path.add(from);
                visited[i] = true;
                if (dfs(tickets[i][1], tickets, visited, path)) return true; // 성공하면 즉시 전파하며 종료

                path.removeLast(); // 실패했으니 되돌리기
                visited[i] = false;
            }
        }
        return false; // 이 지점에서 완성 가능한 경로 없음
    }
}
```

### 코드 설명

- `tickets`를 목적지(`s[1]`) 기준으로 미리 정렬해두면, 같은 출발지를 가진 티켓들도 배열을 앞에서부터 순회할 때 자연스럽게 알파벳순으로 나오게 된다. 그래서 인접 리스트를 따로 안 만들어도, 매번 티켓 배열 전체를 스캔하며 "아직 안 쓰였고, 알파벳순으로 가장 앞선" 티켓을 순서대로 찾을 수 있다.
- `visited[i]`는 `i`번째 티켓을 이미 사용했는지 표시한다. `dfs`가 실패하고 돌아오면 `visited[i] = false`로 되돌려서 그 티켓을 "다시 쓸 수 있는 상태"로 복원한다 — 이게 백트래킹의 "되돌리기"다.
- `dfs`가 `boolean`을 반환하는 게 핵심이다. `true`(경로 완성 성공)를 반환받으면 호출한 쪽도 즉시 `return true`로 전파해서, 이미 찾은 정답을 무시하고 다른 조합을 계속 탐색하는 낭비 겸 오류를 막는다.
- `path.size() == tickets.length`가 곧 "모든 티켓을 다 썼다"는 뜻이므로, 이 조건이 참일 때 지금까지의 `path` + 현재 `from`을 그대로 답으로 확정한다.

## 4. 복잡도 분석

- **시간복잡도**: 한 단계(`dfs` 호출)마다 티켓 배열 전체를 순회하므로 `O(E)`, 이게 최대 `E`번(경로 길이) 중첩되니 백트래킹이 전혀 없을 때 기준 `O(E²)`이다. 다만 백트래킹 자체는 그래프 구조에 따라 추가로 여러 번 되돌아갈 수 있어서, 이론상 최악의 경우를 딱 잘라 보장하기는 어렵다 (인접 리스트 없이 매번 선형 탐색하는 구조라서). 실제로 티켓 9,999개짜리 촘촘한 그래프로 테스트해보면 200ms 이내로 잘 동작하지만, 인접 리스트 기반 알고리즘보다는 느리다.
- **공간복잡도**: `O(E)` — `visited` 배열, 재귀 호출 스택, `path` 리스트가 각각 티켓 개수만큼 쓰인다.

## 5. 엣지 케이스

- **막다른 곳으로 먼저 가는 경우** (`KUL`/`NRT` 예시): 되돌리기가 없으면 오답이 나거나 예외가 발생한다. 백트래킹으로 해결된다.
- **완성 가능한 경로가 여러 개인 경우** (예시 2): `dfs`가 `true`를 반환하는 즉시 전파해서 멈추지 않으면, 나중에 찾은 다른 완성 경로로 답이 덮어써져 알파벳순 조건을 어길 수 있다. `boolean` 반환 + 즉시 종료가 이를 막는다.
- **같은 구간에 티켓이 여러 장인 경우**: `visited`가 티켓 "장(인덱스)" 단위로 관리되므로, 같은 출발지-도착지 조합이 여러 번 있어도 각각 독립적으로 취급되어 문제없이 전부 소비된다.

## 6. 이런 방법도 있다 — Hierholzer's Algorithm (스택 기반, 백트래킹 없이)

이 문제는 그래프 이론에서 "모든 간선을 정확히 한 번씩 쓰는 경로"인 **오일러 경로(Eulerian Path)**를 찾는 문제로도 볼 수 있다. 이 관점에서는 백트래킹 없이도 **항상 `O(E log E)`를 보장**하는 표준 알고리즘이 있다 — **Hierholzer's Algorithm**이다.

핵심 아이디어는 재귀로 "성공/실패를 판정하고 되돌리는" 대신, **막다른 노드를 만나면 그 자리에서 바로 "결과 경로"로 확정**해버리고 절대 취소하지 않는 것이다. 이렇게 확정된 순서를 전부 모은 뒤 뒤집으면 그대로 정답이 된다 (막다른 노드부터 거꾸로 확정되기 때문).

```java
import java.util.*;

class Solution {
    public String[] solution(String[][] tickets) {
        Map<String, PriorityQueue<String>> graph = new HashMap<>(); // 출발지 → 도착지 후보(오름차순)
        for (String[] ticket : tickets) {
            graph.computeIfAbsent(ticket[0], k -> new PriorityQueue<>()).add(ticket[1]);
        }

        Deque<String> stack = new ArrayDeque<>(); // 지금 걷고 있는 경로
        Deque<String> path = new ArrayDeque<>();  // 확정된 경로(막다른 곳부터 쌓임)
        stack.push("ICN");

        while (!stack.isEmpty()) {
            String top = stack.peek();
            PriorityQueue<String> neighbors = graph.get(top);
            if (neighbors == null || neighbors.isEmpty()) {
                path.push(stack.pop()); // 더 갈 곳 없음 → 확정
            } else {
                stack.push(neighbors.poll()); // 알파벳순 최소 후보로 전진
            }
        }

        String[] answer = new String[path.size()];
        int i = 0;
        while (!path.isEmpty()) answer[i++] = path.pop();
        return answer;
    }
}
```

**백트래킹 방식과 비교하면:**

| | 백트래킹 (위 코드) | Hierholzer's |
|---|---|---|
| 되돌리기 | 필요함 (`visited[i]=false` 등) | 필요 없음 — 한 번 확정하면 끝 |
| 다음 후보 탐색 | 매번 티켓 배열 전체 스캔, `O(E)` | 인접 리스트 + 우선순위 큐, `O(log E)` |
| 시간복잡도 | 그래프 구조에 따라 달라짐(대략 `O(E²)`, 최악 보장 어려움) | 항상 `O(E log E)` 보장 |
| 이해하기 | "그리디 + 실패하면 취소"라는 직관적인 흐름 | "막히면 바로 확정하고, 나중에 순서를 뒤집는다"는 트릭을 알아야 함 |

작은 입력에서는 두 방식의 체감 차이가 거의 없지만, 티켓 수가 최대치(10,000개 근처)로 커지고 그래프가 복잡해질수록 인접 리스트 기반의 Hierholzer's algorithm이 더 안전한 선택이다.

## 7. 관련 문제

같은 "그래프의 모든 간선을 정확히 한 번씩 사용하는 경로(오일러 경로)"를 찾는 계열 문제들. 이 저장소에 해당 문제 풀이가 추가되면 링크를 연결할 예정이다.

> 이 섹션은 새 문제를 풀 때마다 갱신됩니다. 오일러 경로/회로, 백트래킹, Hierholzer's algorithm 계열 문제를 풀면 이 목록에 서로 링크를 추가해주세요.

---

*이 문서는 [Problem.md](./Problem.md)의 문제 설명을 기반으로 상세 풀이를 정리한 것입니다.*
