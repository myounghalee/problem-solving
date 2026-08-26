# 프로그래머스 문제 풀이

[프로그래머스](https://school.programmers.co.kr) 코딩테스트 문제를 풀고, 블로그에 게시할 수 있는 형태로 정리한 저장소입니다.

## 폴더 구조

문제 하나당 폴더 하나, 폴더명은 `문제번호-제목` 형식입니다. 각 폴더는 이렇게 구성됩니다.

```
<문제번호>-<제목>/
├── Problem.md      # 문제 설명, 제한사항, 입출력 예 (프로그래머스 원문 기반)
├── Solution.java    # 직접 작성하는 풀이 코드
└── Solution.md      # 블로그 게시용 상세 해설 (접근 아이디어, 시뮬레이션, 코드, 복잡도, 관련 문제)
```

- `Solution.java`는 각자 IntelliJ에서 직접 작성하는 작업 파일입니다.
- `Solution.md`는 `Solution.java`와 독립적인 완결형 문서로, 코드 블록을 자체적으로 포함합니다 (풀이가 바뀌어도 블로그 글은 검증된 코드 기준으로 유지).
- 새 문제를 풀 때, 기존 문제와 패턴이 겹치면(예: 스택 기반 짝 제거) 서로의 `Solution.md` "관련 문제" 섹션에 상대 링크를 추가합니다.

## 문제 목록

| 번호 | 제목 | 분류 | 자료구조/알고리즘 | 문제 | 풀이 |
|---|---|---|---|---|---|
| [12973](https://school.programmers.co.kr/learn/courses/30/lessons/12973) | 짝지어 제거하기 | 2017 팁스타운 | 스택 (O(n)) | [Problem.md](./12973-짝지어제거하기/Problem.md) | [Solution.md](./12973-짝지어제거하기/Solution.md) |
| [60058](https://school.programmers.co.kr/learn/courses/30/lessons/60058) | 괄호 변환 | 2020 KAKAO BLIND RECRUITMENT | 재귀 (O(n²)) | [Problem.md](./60058-괄호변환/Problem.md) | [Solution.md](./60058-괄호변환/Solution.md) |
| [12911](https://school.programmers.co.kr/learn/courses/30/lessons/12911) | 다음 큰 숫자 | 연습문제 | 브루트포스 (+비트 트릭 O(1)) | [Problem.md](./12911-다음큰숫자/Problem.md) | [Solution.md](./12911-다음큰숫자/Solution.md) |
| [12945](https://school.programmers.co.kr/learn/courses/30/lessons/12945) | 피보나치 수 | 연습문제 | DP (O(n)) | [Problem.md](./12945-피보나치수/Problem.md) | [Solution.md](./12945-피보나치수/Solution.md) |
| [42885](https://school.programmers.co.kr/learn/courses/30/lessons/42885) | 구명보트 | 탐욕법(Greedy) | 정렬 + 투 포인터 (O(n log n)) | [Problem.md](./42885-구명보트/Problem.md) | [Solution.md](./42885-구명보트/Solution.md) |
| [12914](https://school.programmers.co.kr/learn/courses/30/lessons/12914) | 멀리 뛰기 | 연습문제 | DP (O(n)) | [Problem.md](./12914-멀리뛰기/Problem.md) | [Solution.md](./12914-멀리뛰기/Solution.md) |
| [131127](https://school.programmers.co.kr/learn/courses/30/lessons/131127) | 할인 행사 | 연습문제 | 슬라이딩 윈도우 (O(n)) | [Problem.md](./131127-할인행사/Problem.md) | [Solution.md](./131127-할인행사/Solution.md) |

## 개발 환경

- Java, IntelliJ IDEA
- 문제 폴더마다 독립된 IntelliJ 모듈(`.iml`)로 구성되어 있습니다. 모든 문제의 클래스명이 `Solution`으로 동일해서, 패키지 없이 하나의 공유 소스 루트를 쓰면 컴파일 충돌이 나기 때문입니다.
- `.idea/`, `*.iml`, `out/`은 로컬 IDE 설정/빌드 산출물이라 `.gitignore`에서 제외했습니다. 새로 클론한 사람은 이 파일들 없이도 각 문제 폴더의 `.java`를 `javac`/`java`로 바로 컴파일·실행할 수 있습니다.
