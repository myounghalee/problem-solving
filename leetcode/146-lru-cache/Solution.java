import java.util.LinkedHashMap;
import java.util.Map;

class LRUCache {

    Map<Integer, Integer> map = new LinkedHashMap<>();
    int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        int val = map.get(key);
        map.remove(key);
        map.put(key, val);
        return val;
    }

    public void put(int key, int value) {
        map.remove(key);
        map.put(key, value);
        if (capacity < map.size()) map.remove(map.keySet().iterator().next());
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1)); // 1
        cache.put(3, 3); // evicts key 2
        System.out.println(cache.get(2)); // -1
        cache.put(4, 4); // evicts key 1
        System.out.println(cache.get(1)); // -1
        System.out.println(cache.get(3)); // 3
        System.out.println(cache.get(4)); // 4
    }
}
