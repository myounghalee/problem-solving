# Valid Palindrome

- 문제 번호: 125
- 링크: https://leetcode.com/problems/valid-palindrome/
- 난이도: Easy
- 태그: Two Pointers, String

## 문제 설명 (요약)

문자열 `s`가 주어질 때, **알파벳과 숫자만 남기고 대소문자 구분 없이** 봤을 때 팰린드롬(앞뒤로 읽어도 같은 문자열)인지 판정한다.

공백, 문장부호 같은 알파벳/숫자가 아닌 문자는 전부 무시하고, 대문자와 소문자는 같은 문자로 취급한다.

## 제약사항

- `1 <= s.length <= 2 * 10^5`
- `s`는 출력 가능한 ASCII 문자로만 구성된다.

## 함수 시그니처

```java
class Solution {
    public boolean isPalindrome(String s) {
        
    }
}
```

## 입출력 예

| s | 결과 |
|---|---|
| "A man, a plan, a canal: Panama" | true |
| "race a car" | false |
| " " | true |

### 입출력 예 설명

**입출력 예 #1**: 알파벳/숫자만 남기고 소문자로 바꾸면 `"amanaplanacanalpanama"`이고, 이는 팰린드롬이다.

**입출력 예 #2**: `"raceacar"`는 팰린드롬이 아니다.

**입출력 예 #3**: 알파벳/숫자를 하나도 포함하지 않는 문자열(공백뿐)은, 걸러내고 나면 빈 문자열이 되고 빈 문자열은 팰린드롬으로 취급한다.
