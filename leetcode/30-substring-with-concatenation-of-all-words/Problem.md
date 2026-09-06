# Substring with Concatenation of All Words

- 문제 번호: 30
- 링크: https://leetcode.com/problems/substring-with-concatenation-of-all-words/
- 난이도: Hard
- 태그: Hash Table, String, Sliding Window

## 문제 설명 (요약)

문자열 `s`와, **모두 길이가 같은** 단어 배열 `words`가 주어진다. `words`에 있는 단어들을 **순서는 상관없이, 각 단어를 정확히 한 번씩(중복된 단어가 있으면 그 개수만큼)** 이어 붙여 만들 수 있는 부분 문자열의 시작 인덱스를 전부 찾는다. 단어 사이에 다른 문자가 끼어들면 안 되고, 이어 붙인 결과가 `s`의 **연속된** 부분 문자열이어야 한다.

## 제약사항

- `1 <= s.length <= 10^4`
- `1 <= words.length <= 5000`
- `1 <= words[i].length <= 30`
- `s`와 `words[i]`는 소문자 영어 알파벳으로만 구성된다.
- `words`의 모든 단어 길이는 서로 같다.

## 함수 시그니처

```java
class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        
    }
}
```

## 입출력 예

| s | words | 결과 |
|---|---|---|
| "barfoothefoobarman" | ["foo","bar"] | [0,9] |
| "wordgoodgoodgoodbestword" | ["word","good","best","word"] | [] |
| "barfoofoobarthefoobarman" | ["bar","foo","the"] | [6,9,12] |
| "wordgoodword" | ["word","good"] | [0,4] |

### 입출력 예 설명

**입출력 예 #1**: 인덱스 `0`에서 `"barfoo"`(`bar`+`foo`), 인덱스 `9`에서 `"foobar"`(`foo`+`bar`)가 각각 `words`의 두 단어를 순서만 바꿔 이어 붙인 결과다.

**입출력 예 #2**: `words`에 `"word"`가 두 번 있으므로, 정답이 되려면 부분 문자열도 `"word"`를 정확히 두 번 포함해야 한다. `s`에서 그런 부분 문자열을 찾을 수 없다.

**입출력 예 #3**: `words`에 중복 단어가 없고 길이가 모두 3이라, 총 길이 9인 부분 문자열 중 `bar`/`foo`/`the`를 순서 무관하게 정확히 하나씩 포함하는 것을 찾으면 `6, 9, 12` 세 곳이 나온다.
