# 코딩테스트 문제 풀이

코딩테스트 문제를 풀고, 블로그에 게시할 수 있는 형태로 정리하는 저장소입니다. 플랫폼별로 폴더가 나뉘어 있습니다.

- [`programmers/`](./programmers/README.md) — [프로그래머스](https://school.programmers.co.kr) 문제 풀이
- [`leetcode/`](./leetcode/README.md) — [LeetCode](https://leetcode.com) 문제 풀이

각 폴더의 컨벤션(폴더 구조, `Problem.md`/`Solution.java`/`Solution.md` 역할)은 해당 폴더의 README를 참고하세요.

## 개발 환경

- Java, IntelliJ IDEA
- 문제 폴더마다(플랫폼 무관) 독립된 IntelliJ 모듈(`.iml`)로 구성되어 있습니다. 모든 문제의 클래스명이 `Solution`으로 동일해서, 패키지 없이 하나의 공유 소스 루트를 쓰면 컴파일 충돌이 나기 때문입니다.
- `.idea/`, `*.iml`, `out/`은 로컬 IDE 설정/빌드 산출물이라 `.gitignore`에서 제외했습니다. 새로 클론한 사람은 이 파일들 없이도 각 문제 폴더의 `.java`를 `javac`/`java`로 바로 컴파일·실행할 수 있습니다.
