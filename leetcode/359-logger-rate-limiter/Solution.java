import java.util.HashMap;
import java.util.Map;

class Logger {

    Map<String, Integer> map = new HashMap<>();

    public Logger() {}

    public boolean shouldPrintMessage(int timestamp, String message) {

        if (!map.containsKey(message)) {
            map.put(message, timestamp);
            return true;
        }

        if (map.get(message) + 10 > timestamp) {
            return false;
        }

        map.put(message, timestamp);
        return true;
    }

    public static void main(String[] args) {
        Logger logger = new Logger();

        System.out.println(logger.shouldPrintMessage(1, "foo"));  // true
        System.out.println(logger.shouldPrintMessage(2, "bar"));  // true
        System.out.println(logger.shouldPrintMessage(3, "foo"));  // false
        System.out.println(logger.shouldPrintMessage(8, "bar"));  // false
        System.out.println(logger.shouldPrintMessage(10, "foo")); // false
        System.out.println(logger.shouldPrintMessage(11, "foo")); // true
    }
}
