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
| [42747](https://school.programmers.co.kr/learn/courses/30/lessons/42747) | H-Index | 정렬 | 정렬 (O(n log n)) | [Problem.md](./42747-H-Index/Problem.md) | [Solution.md](./42747-H-Index/Solution.md) |
| [12913](https://school.programmers.co.kr/learn/courses/30/lessons/12913) | 땅따먹기 | 연습문제 | DP (O(n)) | [Problem.md](./12913-땅따먹기/Problem.md) | [Solution.md](./12913-땅따먹기/Solution.md) |
| [12927](https://school.programmers.co.kr/learn/courses/30/lessons/12927) | 야근 지수 | 연습문제 | 탐욕법 + 최대 힙 (O(n log k)) | [Problem.md](./12927-야근지수/Problem.md) | [Solution.md](./12927-야근지수/Solution.md) |
| [17687](https://school.programmers.co.kr/learn/courses/30/lessons/17687) | [3차] n진수 게임 | 2018 KAKAO BLIND RECRUITMENT | 문자열/진법 변환 | [Problem.md](./17687-n진수게임/Problem.md) | [Solution.md](./17687-n진수게임/Solution.md) |
| [42584](https://school.programmers.co.kr/learn/courses/30/lessons/42584) | 주식가격 | 스택/큐 | 스택 (O(n)) | [Problem.md](./42584-주식가격/Problem.md) | [Solution.md](./42584-주식가격/Solution.md) |
| [12987](https://school.programmers.co.kr/learn/courses/30/lessons/12987) | 숫자 게임 | Summer/Winter Coding(~2018) | 정렬 + 탐욕법 (O(n log n)) | [Problem.md](./12987-숫자게임/Problem.md) | [Solution.md](./12987-숫자게임/Solution.md) |
| [42626](https://school.programmers.co.kr/learn/courses/30/lessons/42626) | 더 맵게 | 힙(Heap) | 최소 힙 (O(n log n)) | [Problem.md](./42626-더맵게/Problem.md) | [Solution.md](./42626-더맵게/Solution.md) |
| [42628](https://school.programmers.co.kr/learn/courses/30/lessons/42628) | 이중우선순위큐 | 힙(Heap) | 최소 힙 + 최대 힙 (지연 삭제), O(n log n) | [Problem.md](./42628-이중우선순위큐/Problem.md) | [Solution.md](./42628-이중우선순위큐/Solution.md) |
| [42898](https://school.programmers.co.kr/learn/courses/30/lessons/42898) | 등굣길 | 동적계획법(Dynamic Programming) | DP (O(mn)) | [Problem.md](./42898-등굣길/Problem.md) | [Solution.md](./42898-등굣길/Solution.md) |
| [42884](https://school.programmers.co.kr/learn/courses/30/lessons/42884) | 단속카메라 | 탐욕법(Greedy) | 정렬 + 탐욕법 (O(n log n)) | [Problem.md](./42884-단속카메라/Problem.md) | [Solution.md](./42884-단속카메라/Solution.md) |
| [43105](https://school.programmers.co.kr/learn/courses/30/lessons/43105) | 정수 삼각형 | 동적계획법(Dynamic Programming) | DP (O(n²)) | [Problem.md](./43105-정수삼각형/Problem.md) | [Solution.md](./43105-정수삼각형/Solution.md) |
| [161988](https://school.programmers.co.kr/learn/courses/30/lessons/161988) | 연속 펄스 부분 수열의 합 | 연습문제 | Kadane's Algorithm (O(n)) | [Problem.md](./161988-연속펄스부분수열의합/Problem.md) | [Solution.md](./161988-연속펄스부분수열의합/Solution.md) |

## 개발 환경

- Java, IntelliJ IDEA
- 문제 폴더마다 독립된 IntelliJ 모듈(`.iml`)로 구성되어 있습니다. 모든 문제의 클래스명이 `Solution`으로 동일해서, 패키지 없이 하나의 공유 소스 루트를 쓰면 컴파일 충돌이 나기 때문입니다.
- `.idea/`, `*.iml`, `out/`은 로컬 IDE 설정/빌드 산출물이라 `.gitignore`에서 제외했습니다. 새로 클론한 사람은 이 파일들 없이도 각 문제 폴더의 `.java`를 `javac`/`java`로 바로 컴파일·실행할 수 있습니다.
