# Logger Rate Limiter

- 문제 번호: 359
- 링크: https://leetcode.com/problems/logger-rate-limiter/
- 난이도: Easy
- 태그: Hash Table, Design, Data Stream

## 문제 설명 (요약)

타임스탬프와 함께 메시지 스트림을 받는 로거 시스템을 설계한다. 어떤 메시지는, **같은 메시지가 최근 10초 이내에 이미 출력된 적이 없을 때만** 출력되어야 한다. (즉 타임스탬프 `t`에 어떤 메시지가 출력되면, 같은 메시지는 `t + 10` 이전까지 다시 출력되지 않는다.)

`Logger` 클래스를 구현한다:

- `Logger()`: 로거 객체를 초기화한다.
- `boolean shouldPrintMessage(int timestamp, String message)`: 주어진 `timestamp`에 `message`가 출력되어야 하면 `true`, 아니면 `false`를 반환한다.

## 제약사항

- `0 <= timestamp <= 10^9`
- 모든 `timestamp`는 **비내림차순(non-decreasing)** 순서로 들어온다.
- `1 <= message.length <= 30`
- `shouldPrintMessage`는 최대 `10^4`번 호출된다.

## 함수 시그니처

```java
class Logger {

    public Logger() {

    }

    public boolean shouldPrintMessage(int timestamp, String message) {

    }
}

/**
 * Your Logger object will be instantiated and called as such:
 * Logger obj = new Logger();
 * boolean param_1 = obj.shouldPrintMessage(timestamp,message);
 */
```

## 입출력 예

**입력**
```
["Logger", "shouldPrintMessage", "shouldPrintMessage", "shouldPrintMessage", "shouldPrintMessage", "shouldPrintMessage", "shouldPrintMessage"]
[[], [1, "foo"], [2, "bar"], [3, "foo"], [8, "bar"], [10, "foo"], [11, "foo"]]
```

**출력**
```
[null, true, true, false, false, false, true]
```

**설명**
```
Logger logger = new Logger();
logger.shouldPrintMessage(1, "foo");  // "foo"의 다음 허용 시각은 1 + 10 = 11 → true
logger.shouldPrintMessage(2, "bar");  // "bar"의 다음 허용 시각은 2 + 10 = 12 → true
logger.shouldPrintMessage(3, "foo");  // 3 < 11 → false
logger.shouldPrintMessage(8, "bar");  // 8 < 12 → false
logger.shouldPrintMessage(10, "foo"); // 10 < 11 → false
logger.shouldPrintMessage(11, "foo"); // 11 >= 11 → true, 다음 허용 시각은 11 + 10 = 21
```
