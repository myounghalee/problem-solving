import java.util.ArrayDeque;
import java.util.Deque;

class HitCounter {

    Deque<Integer> q;

    public HitCounter() {
        q = new ArrayDeque<>();
    }

    public void hit(int timestamp) {
        q.add(timestamp);
    }

    public int getHits(int timestamp) {
        while (!q.isEmpty() && q.peek() <= timestamp - 300) {
            q.poll();
        }
        return q.size();
    }

    public static void main(String[] args) {
        HitCounter hitCounter = new HitCounter();

        hitCounter.hit(1);
        hitCounter.hit(2);
        hitCounter.hit(3);
        System.out.println(hitCounter.getHits(4));   // 3
        hitCounter.hit(300);
        System.out.println(hitCounter.getHits(300)); // 4
        System.out.println(hitCounter.getHits(301)); // 3
    }
}
