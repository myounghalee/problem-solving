import java.util.*;

class AllOne {

    // 같은 횟수를 가진 키들의 묶음. count는 생성 시 정해지고 변하지 않는다.
    private static class Bucket {
        final int count;
        final Set<String> keys = new HashSet<>();
        Bucket prev, next;

        Bucket(int count) {
            this.count = count;
        }
    }

    Map<String, Bucket> keyMap; // key → 그 키가 현재 속한 버킷
    Bucket head, tail;          // 센티넬. 리스트는 count 오름차순, 빈 버킷은 두지 않는다.

    public AllOne() {
        keyMap = new HashMap<>();
        head = new Bucket(0);
        tail = new Bucket(0);
        head.next = tail;
        tail.prev = head;
    }

    public void inc(String key) {
        Bucket cur = keyMap.get(key);

        if (cur == null) { // 새 키 → 횟수 1 버킷으로
            Bucket to = (head.next != tail && head.next.count == 1)
                    ? head.next
                    : insertAfter(head, 1);
            to.keys.add(key);
            keyMap.put(key, to);
            return;
        }

        // 목적지는 항상 바로 다음 버킷 자리 — 있으면 재사용, 없으면 끼워넣는다
        Bucket to = (cur.next != tail && cur.next.count == cur.count + 1)
                ? cur.next
                : insertAfter(cur, cur.count + 1);
        move(key, cur, to);
    }

    public void dec(String key) {
        Bucket cur = keyMap.get(key); // 존재가 보장됨

        if (cur.count == 1) { // 0이 되면 키 자체를 제거
            cur.keys.remove(key);
            keyMap.remove(key);
            if (cur.keys.isEmpty()) unlink(cur);
            return;
        }

        Bucket to = (cur.prev != head && cur.prev.count == cur.count - 1)
                ? cur.prev
                : insertAfter(cur.prev, cur.count - 1);
        move(key, cur, to);
    }

    public String getMaxKey() {
        return tail.prev == head ? "" : tail.prev.keys.iterator().next();
    }

    public String getMinKey() {
        return head.next == tail ? "" : head.next.keys.iterator().next();
    }

    // 키를 from에서 to로 옮기고, 빈 버킷이 된 from은 끊어낸다
    private void move(String key, Bucket from, Bucket to) {
        from.keys.remove(key);
        to.keys.add(key);
        keyMap.put(key, to);
        if (from.keys.isEmpty()) unlink(from);
    }

    private Bucket insertAfter(Bucket node, int count) {
        Bucket b = new Bucket(count);
        b.prev = node;
        b.next = node.next;
        node.next.prev = b;
        node.next = b;
        return b;
    }

    private void unlink(Bucket b) {
        b.prev.next = b.next;
        b.next.prev = b.prev;
    }

    public static void main(String[] args) {
        // 문제의 예시
        AllOne a = new AllOne();
        a.inc("hello");
        a.inc("hello");
        check(a.getMaxKey(), "hello");
        check(a.getMinKey(), "hello");
        a.inc("leet");
        check(a.getMaxKey(), "hello");
        check(a.getMinKey(), "leet");

        // 빈 상태
        AllOne b = new AllOne();
        check(b.getMaxKey(), "");
        check(b.getMinKey(), "");

        // dec로 0이 되면 제거되는지 — 제거 후 min/max가 갱신되어야 함
        AllOne c = new AllOne();
        c.inc("a");
        c.inc("a"); // a:2
        c.inc("b"); // b:1
        check(c.getMinKey(), "b");
        c.dec("b"); // b 제거 → {a:2}만 남음
        check(c.getMinKey(), "a");
        check(c.getMaxKey(), "a");
        c.dec("a");
        c.dec("a"); // a도 제거 → 빈 상태
        check(c.getMaxKey(), "");
        check(c.getMinKey(), "");

        // 최소 버킷의 마지막 키가 제거되는 경우 — min을 정수로 추적하면 깨지는 지점
        AllOne d = new AllOne();
        d.inc("a"); // a:1
        d.inc("b");
        d.inc("b"); // b:2
        d.dec("a"); // a 제거 → 횟수 1 버킷 소멸, 남은 건 b:2
        check(d.getMinKey(), "b");
        check(d.getMaxKey(), "b");

        // 한 키가 올라가며 원래 버킷이 비는 경우
        AllOne e = new AllOne();
        e.inc("a");
        e.inc("b");
        e.inc("b"); // {a:1, b:2}
        check(e.getMinKey(), "a");
        check(e.getMaxKey(), "b");
    }

    private static void check(String actual, String expected) {
        System.out.println((actual.equals(expected) ? "OK  " : "FAIL") + " actual=\"" + actual + "\" expected=\"" + expected + "\"");
    }
}
