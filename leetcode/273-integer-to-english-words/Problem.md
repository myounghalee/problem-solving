# Integer to English Words

- 문제 번호: 273
- 링크: https://leetcode.com/problems/integer-to-english-words/
- 난이도: Hard
- 태그: Math, String, Recursion

## 문제 설명 (요약)

0 이상의 정수 `num`이 주어졌을 때, 이 숫자를 영어 단어로 풀어쓴 문자열로 변환한다. (예: `123` → `"One Hundred Twenty Three"`)

## 제약사항

- `0 <= num <= 2^31 - 1`

## 함수 시그니처

```java
class Solution {
    public String numberToWords(int num) {
        
    }
}
```

## 입출력 예

| num | 결과 |
|---|---|
| 123 | "One Hundred Twenty Three" |
| 12345 | "Twelve Thousand Three Hundred Forty Five" |
| 1234567 | "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven" |

## 참고 (LeetCode 힌트)

- 숫자를 1000 단위(천 단위)로 끊어서 묶어 생각하면 패턴이 보인다. 1000 미만의 숫자 하나를 영어로 바꾸는 헬퍼 함수를 만들고, 그 위에 Thousand/Million/Billion 단위를 붙이는 방식으로 접근할 수 있다.
- 엣지 케이스에 주의: `num = 0`인 경우, 그리고 `1000010`처럼 중간 자릿수(천 단위 묶음)가 0이라 아예 출력하면 안 되는 경우.
