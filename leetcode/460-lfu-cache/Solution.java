import java.util.*;

class LFUCache {

    int capacity;
    Map<Integer, int[]> cache = new LinkedHashMap<>();
    Map<Integer, Set<Integer>> freq = new HashMap<>();
    int minFreq;

    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!cache.containsKey(key)) return -1;

        int[] value =  cache.get(key);
        addFreq(key, value[1]);
        value[1]++;
        cache.remove(key);
        cache.put(key, value);
        return cache.get(key)[0];
    }

    public void put(int key, int value) {
        if (!cache.containsKey(key) && cache.size() >= capacity) {
            int val = freq.get(minFreq).iterator().next();
            removeFreq(val, minFreq);
            cache.remove(val);
        }

        if (cache.containsKey(key)) {
            addFreq(key, cache.get(key)[1]);
            cache.get(key)[0] = value;
            cache.get(key)[1]++;
        } else {
            addFreq(key, 0);
            minFreq = 1;
            cache.put(key, new int[]{value, 1});
        }
    }

    private void addFreq(int k, int f) {
        removeFreq(k, f);
        freq.computeIfAbsent(f + 1, i -> new LinkedHashSet<>()).add(k);
    }

    private void removeFreq(int k, int f) {
        if (freq.containsKey(f)) {
            freq.get(f).remove(k);
            if (freq.get(f).isEmpty()) {
                freq.remove(f);
                if (f == minFreq) minFreq++;
            }
        }
    }

    public static void main(String[] args) {
        LFUCache lfu = new LFUCache(2);
        lfu.put(1, 1);
        lfu.put(2, 2);
        System.out.println(lfu.get(1)); // 1
        lfu.put(3, 3); // evicts key 2
        System.out.println(lfu.get(2)); // -1
        System.out.println(lfu.get(3)); // 3
        lfu.put(4, 4); // evicts key 1 (tie on count, 1 is LRU)
        System.out.println(lfu.get(1)); // -1
        System.out.println(lfu.get(3)); // 3
        System.out.println(lfu.get(4)); // 4

        // 두 번째 시나리오: put(2,1),put(3,2),get(3),get(2),put(4,3),get(2),get(3),get(4)
        // 기대값: [null, null, null, 2, 1, null, 1, -1, 3]
        LFUCache lfu2 = new LFUCache(2);
        lfu2.put(2, 1);
        lfu2.put(3, 2);
        check(lfu2.get(3), 2);
        check(lfu2.get(2), 1);
        lfu2.put(4, 3); // cnt(2)==cnt(3)==2로 동점, 3이 더 오래 안 쓰였으므로 3을 제거
        check(lfu2.get(2), 1);
        check(lfu2.get(3), -1);
        check(lfu2.get(4), 3);
    }

    private static void check(int actual, int expected) {
        System.out.println((actual == expected ? "OK" : "FAIL") + " actual=" + actual + " expected=" + expected);
    }
}
