# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this repository is

A personal archive of [프로그래머스](https://school.programmers.co.kr) coding test solutions, written in Java, organized so each solved problem also becomes a blog-ready writeup. `README.md` at the root is the index of all solved problems (number, title, category, data structure/algorithm, links) — keep it in sync when adding a problem.

## Folder structure

One folder per problem, named `<문제번호>-<제목>` (no spaces), at the repo root:

```
<문제번호>-<제목>/
├── Problem.md              # 문제 설명, 제한사항, 입출력 예 (프로그래머스 원문 기반)
├── Solution.java            # 실제 풀이 코드 (프로그래머스 제출용)
├── Solution.md               # 블로그 게시용 상세 해설
└── <문제번호>-<제목>.iml    # IntelliJ module file (gitignored)
```

- `Solution.java` must **never** contain a `package` declaration — the class name is always `Solution`, matching the 프로그래머스 skeleton exactly so it can be pasted straight into the submission box.
- `Solution.md` is a self-contained document (includes its own code block) independent of `Solution.java`, so it stays pinned to a verified solution even if the working code later changes. It typically covers: approach/idea, step-by-step simulation on the sample input, code, complexity analysis, edge cases, and a "관련 문제" (related problems) section that cross-links other solutions sharing the same pattern (e.g. stack-based pair removal) — add links both ways when patterns overlap.
- A problem folder isn't required to have `Solution.md` yet if unsolved (root `README.md` marks it "풀이 전" in that case).

## Why one IntelliJ module per problem

Every `Solution.java` uses the same class name (`Solution`) in the default (no-)package, matching what 프로그래머스 requires for submission. If all problem folders shared one source root, the classes would collide. Instead, each problem folder is registered as its own IntelliJ module:

- `.idea/misc.xml` sets the project JDK (`project-jdk-name` — currently `homebrew-21`, `languageLevel="JDK_21"`).
- `.idea/modules.xml` lists every problem folder's `.iml` under `<modules>`.
- Each problem folder's `<폴더명>.iml` is a `JAVA_MODULE` with content root = itself, source folder = itself, `orderEntry type="inheritedJdk"`.
- There is no single project-wide module — module count should equal problem-folder count.

`.idea/`, `*.iml`, and `out/` are gitignored (local IDE state), so a fresh clone can still `javac`/`java` any `Solution.java` directly without them.

**When adding a new problem folder**, also: (1) create `<폴더명>/<폴더명>.iml` from the template above, (2) add a matching `<module .../>` line to `.idea/modules.xml`. IntelliJ needs a reload (project view root → "Reload from Disk", or restart) to pick up these file-level changes — editing the files alone doesn't make the module runnable immediately.

## Compiling/running Java from the Bash tool

The Bash tool's non-interactive shell does not load `~/.zshrc`, so bare `java`/`javac` resolve to the macOS stub and fail with "Unable to locate a Java Runtime". Always use the full Homebrew OpenJDK 21 path:

```bash
/opt/homebrew/opt/openjdk@21/bin/javac Solution.java && /opt/homebrew/opt/openjdk@21/bin/java Solution
```

(or prefix the command with `export PATH="/opt/homebrew/opt/openjdk@21/bin:$PATH"`).

Solutions commonly include a `main` method with a couple of inline sanity-check calls (see any existing `Solution.java`) — run it this way to verify before considering a problem done.
