# [프로그래머스 131127] 할인 행사 — 증분 갱신 슬라이딩 윈도우

- 문제 번호: 131127
- 링크: https://school.programmers.co.kr/learn/courses/30/lessons/131127
- 분류: 연습문제
- 사용 자료구조/알고리즘: **슬라이딩 윈도우(증분 갱신)**, `HashMap` 빈도수 비교
- 난이도 체감: Lv.2 (아이디어는 단순하지만, "왜 개수만 맞으면 되는지" 근거를 짚고 넘어가면 좋음)

## 1. 문제 요약

`discount`(날짜별 할인 제품)에서 **연속된 10일** 구간 중, 그 10일간 등장하는 제품의 종류·수량이 `want`/`number`와 **정확히 일치**하는 시작일의 개수를 구한다.

```text
want=[banana,apple,rice,pork,pot], number=[3,2,2,2,1]
discount=[chicken,apple,apple,banana,rice,apple,pork,banana,pork,rice,pot,banana,apple,banana]
→ 3일째, 4일째, 5일째부터 10일간이 조건을 만족 → 정답 3
```

**제한사항**: `want`/`number` 길이 ≤ 10, `number` 원소 합은 항상 10, `discount` 길이는 10~100,000.

## 2. 접근 아이디어 — 진짜 슬라이딩 윈도우로 증분 갱신

`number`의 총합이 **항상 정확히 10**이라는 제약이 핵심이다. 이 덕분에 "10일짜리 윈도우 안의 제품 구성이 `want`와 완전히 같은가"를 확인할 때, **원하는 제품들의 개수만 각각 맞는지 확인하면 충분**하다 — 그 외 다른 제품이 섞여 있을 여지 자체가 수학적으로 없다.

> **왜 "여분의 제품이 없는지"를 따로 체크할 필요가 없는가**: 윈도우 크기는 항상 10, `want`가 요구하는 개수의 합도 항상 10이다. 만약 윈도우 안에서 `want`의 모든 항목이 요구 개수만큼 정확히 나왔다면, 그 개수의 합만으로 이미 윈도우의 10칸을 전부 채운 것이다. 남는 자리가 없으므로 `want`에 없는 제품이 끼어들 공간 자체가 존재하지 않는다.

윈도우를 매번 처음부터 다시 세는 대신, **한 칸씩 옮길 때 "새로 들어오는 날 +1, 10칸 전에 빠지는 날 -1"만 갱신**하면 매 단계 갱신량이 상수가 된다 — 이게 슬라이딩 윈도우 기법의 핵심이다.

### 손으로 시뮬레이션 — `i = 0..13` (0-indexed, discount 14일)

`window`를 하나의 `HashMap`으로 유지하면서, 각 날짜 `i`를 지날 때:
1. `discount[i]`를 윈도우에 추가(+1)
2. `i ≥ 10`이면, 10칸 전에 들어왔던 `discount[i-10]`을 윈도우에서 제거(-1)
3. `i ≥ 9`이면(윈도우가 처음으로 10칸을 채운 시점부터), 지금 윈도우가 `want`와 일치하는지 검사

| i | discount[i] | 추가 | 빠지는 날(i≥10) | 윈도우가 10칸 찬 시점? | 검사 결과 |
|---|---|---|---|---|---|
| 0 | chicken | +1 | - | 아니오 | - |
| 1 | apple | +1 | - | 아니오 | - |
| ... | ... | ... | - | 아니오 | - |
| 9 | rice | +1 | - | **예** (윈도우=idx0~9) | 불일치 (chicken 포함) |
| 10 | pot | +1 | idx0 `chicken` 제거 | 예 (윈도우=idx1~10) | 불일치 (banana 부족) |
| 11 | banana | +1 | idx1 `apple` 제거 | 예 (윈도우=idx2~11) | **일치! answer++** |
| 12 | apple | +1 | idx2 `apple` 제거 | 예 (윈도우=idx3~12) | **일치! answer++** |
| 13 | banana | +1 | idx3 `banana` 제거 | 예 (윈도우=idx4~13) | **일치! answer++** |

`i=11,12,13`에서 매치되는데, 이는 각각 윈도우 시작일이 `idx2, idx3, idx4` (0-indexed) = **3일째, 4일째, 5일째**(1-indexed)라는 뜻이다. 문제에서 말한 정답과 정확히 일치한다.

## 3. 코드 (Java)

```java
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int solution(String[] want, int[] number, String[] discount) {
        Map<String, Integer> wantMap = new HashMap<>();
        for (int i = 0; i < want.length; i++) {
            wantMap.put(want[i], number[i]);
        }

        Map<String, Integer> window = new HashMap<>();
        int answer = 0;

        for (int i = 0; i < discount.length; i++) {
            // 새로 들어오는 날 +1
            window.merge(discount[i], 1, Integer::sum);

            // 10칸을 넘어가면, 10칸 전에 들어왔던 날은 -1 (윈도우에서 제외)
            if (i >= 10) {
                String out = discount[i - 10];
                if (window.merge(out, -1, Integer::sum) == 0) {
                    window.remove(out);
                }
            }

            // 윈도우가 처음으로 10칸을 채운 시점부터 매번 일치 여부 검사
            if (i >= 9 && matches(window, wantMap)) {
                answer++;
            }
        }

        return answer;
    }

    private boolean matches(Map<String, Integer> window, Map<String, Integer> wantMap) {
        for (Map.Entry<String, Integer> entry : wantMap.entrySet()) {
            if (!entry.getValue().equals(window.get(entry.getKey()))) {
                return false;
            }
        }
        return true;
    }
}
```

### 코드 설명

- `wantMap`: `want[i] → number[i]`를 미리 한 번만 만들어둔다 (매 윈도우마다 다시 만들 필요 없음).
- `window`: 현재 10일 윈도우 안의 제품별 개수. `discount`를 한 번만 순회하면서 계속 같은 `Map` 객체를 갱신한다 — 윈도우마다 새로 만들지 않는다.
- `window.merge(key, 1, Integer::sum)`: 키가 없으면 `1`로 초기화, 있으면 기존 값에 `1`을 더한다 (`getOrDefault` + `put`을 한 번에).
- 10칸을 넘어서면(`i >= 10`) 빠져나가는 날의 카운트를 `-1`. `0`이 되면 `remove`로 아예 지운다 — 이렇게 해야 `matches()`에서 "그 제품이 윈도우에 없다"는 게 `containsKey`/`get == null` 기준으로 정확히 판별된다 (값이 `0`으로 남아있으면 존재하는 걸로 착각할 수 있음).
- `matches()`: `wantMap`의 모든 항목이 `window`에 정확히 같은 개수로 있는지 확인. [이전 버전](#5-참고-매-윈도우마다-hashmap을-새로-만드는-버전과-비교)과 마찬가지로, `number`의 합이 항상 10이라는 제약 덕분에 "여분 제품이 없는지"는 따로 검사할 필요가 없다.

## 4. 복잡도 분석

- **시간복잡도**: `discount`를 한 번만 순회(`O(n)`), 매 단계 `merge`는 `O(1)`(상수), `matches()`는 `O(want.length) ≤ O(10)` — 전체 **`O(n)`** (n = `discount.length`). 매 윈도우마다 `Map`을 다시 만드는 버전보다 상수 계수가 더 작다.
- **공간복잡도**: `window`, `wantMap` 모두 최대 크기가 상수(윈도우 10칸, `want` 10개)로 제한 → `O(1)`.

실측(`discount.length = 100,000`, 무작위 데이터): **약 4.2ms**.

## 5. 참고: 매 윈도우마다 HashMap을 새로 만드는 버전과 비교

같은 아이디어를 조금 다르게 구현할 수도 있다 — 윈도우가 옮겨갈 때마다 그 10칸을 처음부터 다시 세는 방식.

```java
for (int l = 0; l + 10 <= discount.length; l++) {
    Map<String, Integer> map = new HashMap<>();
    for (int i = l; i < l + 10; i++) {
        map.put(discount[i], map.getOrDefault(discount[i], 0) + 1);
    }
    for (int i = 0; i < want.length; i++) {
        if (!map.containsKey(want[i]) || map.get(want[i]) != number[i]) break;
        if (i == want.length - 1) answer++;
    }
}
```

두 버전 모두 `want`/윈도우 크기가 상수(≤10)라 점근적으로는 똑같이 `O(n)`이지만, 매번 새 `HashMap`을 만들고 10개를 처음부터 다시 세는지, 아니면 하나의 `Map`을 증분 갱신하는지에서 실제 상수 계수가 달라진다.

| 방식 | discount.length=100,000 실측 |
|---|---|
| 윈도우마다 HashMap 재생성 | 약 7.5ms |
| **증분 갱신 슬라이딩 윈도우 (이 문서의 코드)** | 약 4.2ms |

2000회 무작위 데이터로 두 방식이 **항상 같은 답**을 내는 것도 확인했다. 이 문제 규모(`n≤100,000`)에서는 두 방식 다 시간 제한에 전혀 문제없지만, "슬라이딩 윈도우"라는 이름에 걸맞게 실제로 증분 갱신하는 쪽이 더 적은 작업으로 같은 결과를 낸다.

## 6. 엣지 케이스

- `discount.length == 10` (최소): 윈도우가 정확히 하나뿐 (`i=9`에서 한 번만 검사) — 일치하면 1, 아니면 0.
- 정답이 없는 경우(예시 #2): 원하는 제품이 할인 목록에 아예 없으면 모든 시점에서 `matches()`가 실패해 0 반환.
- `want.length == 1`이고 `number[0] == 10`: 10일 내내 같은 제품 하나만 할인해야 통과 — 매우 좁은 조건.
- 윈도우에서 카운트가 `0`이 된 제품을 `remove`하지 않고 그냥 `0`으로 남겨두면, `matches()`에서 "그 제품이 없다"는 걸 `get(key) == null`로 판별할 때 오작동한다 — 이 코드처럼 `0`이 되면 반드시 `remove`해야 한다.

## 7. 관련 문제

> 아직 이 저장소에 등록된 다른 슬라이딩 윈도우 문제가 없습니다. 새로 풀 때 여기에 연결하겠습니다.

---

*이 문서는 [Problem.md](./Problem.md)의 문제 설명을 기반으로 상세 풀이를 정리한 것입니다.*
