# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this repository is

A personal archive of coding test solutions, written in Java, organized so each solved problem also becomes a blog-ready writeup. The repo is split by platform at the top level:

- `programmers/` — [프로그래머스](https://school.programmers.co.kr) problems (see `programmers/README.md` for its problem index)
- `leetcode/` — [LeetCode](https://leetcode.com) problems (see `leetcode/README.md` for its problem index)

Each platform folder has its own README with the index table (number, title, category, data structure/algorithm, links) — keep the relevant one in sync when adding a problem. The root `README.md` is just a short pointer to both.

## Folder structure

One folder per problem, named `<문제번호>-<제목>` (프로그래머스, no spaces) or `<문제번호>-<slug>` (LeetCode), inside the platform folder (`programmers/` or `leetcode/`):

```
<플랫폼>/<문제번호>-<제목>/
├── Problem.md              # 문제 설명, 제한사항, 입출력 예 (원문 기반 — LeetCode는 저작권상 요약)
├── Solution.java            # 실제 풀이 코드 (제출용)
├── Solution.md               # 블로그 게시용 상세 해설
└── <문제번호>-<제목>.iml    # IntelliJ module file (gitignored)
```

- `Solution.java` must **never** contain a `package` declaration — the class name is always `Solution`, matching the platform's skeleton exactly so it can be pasted straight into the submission box.
- `Solution.md` is a self-contained document (includes its own code block) independent of `Solution.java`, so it stays pinned to a verified solution even if the working code later changes. It typically covers: approach/idea, step-by-step simulation on the sample input, code, complexity analysis, edge cases, and a "관련 문제" (related problems) section that cross-links other solutions sharing the same pattern (e.g. stack-based pair removal) — add links both ways when patterns overlap. Code blocks get short one-line inline comments only (variable meaning, not derivation); the "코드 설명" prose section handles the "why". The difficulty line in the header is `- 난이도: Lv.X` (no "체감" wording, no parenthetical explanation).
- A problem folder isn't required to have `Solution.md` yet if unsolved (the platform README marks it "풀이 전" in that case). By default, `Claude` only writes the bare skeleton + a `main` method with sample-input test calls into `Solution.java` — the solving logic is left for the user to write themselves. `Problem.md`'s own approach/hint section and `Solution.md` are written only after the user has solved it (or explicitly asks for the solution).

## Why one IntelliJ module per problem

Every `Solution.java` uses the same class name (`Solution`) in the default (no-)package, matching what the platform requires for submission. If all problem folders shared one source root, the classes would collide. Instead, each problem folder (across both `programmers/` and `leetcode/`) is registered as its own IntelliJ module:

- `.idea/misc.xml` sets the project JDK (`project-jdk-name` — currently `homebrew-21`, `languageLevel="JDK_21"`).
- `.idea/modules.xml` lists every problem folder's `.iml` under `<modules>`.
- Each problem folder's `<폴더명>.iml` is a `JAVA_MODULE` with content root = itself, source folder = itself, `orderEntry type="inheritedJdk"`.
- There is no single project-wide module — module count should equal problem-folder count.

`.idea/`, `*.iml`, and `out/` are gitignored (local IDE state), so a fresh clone can still `javac`/`java` any `Solution.java` directly without them.

**When adding a new problem folder**, also: (1) create `<플랫폼>/<폴더명>/<폴더명>.iml` from the template above, (2) add a matching `<module fileurl="file://$PROJECT_DIR$/<플랫폼>/<폴더명>/<폴더명>.iml" .../>` line to `.idea/modules.xml` (note the `programmers/` or `leetcode/` path segment). IntelliJ needs a reload (project view root → "Reload from Disk", or restart) to pick up these file-level changes — editing the files alone doesn't make the module runnable immediately.

## Compiling/running Java from the Bash tool

The Bash tool's non-interactive shell does not load `~/.zshrc`, so bare `java`/`javac` resolve to the macOS stub and fail with "Unable to locate a Java Runtime". Always use the full Homebrew OpenJDK 21 path:

```bash
/opt/homebrew/opt/openjdk@21/bin/javac Solution.java && /opt/homebrew/opt/openjdk@21/bin/java Solution
```

(or prefix the command with `export PATH="/opt/homebrew/opt/openjdk@21/bin:$PATH"`).

Solutions commonly include a `main` method with a couple of inline sanity-check calls (see any existing `Solution.java`) — run it this way to verify before considering a problem done.
