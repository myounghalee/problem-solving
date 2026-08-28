import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

class Solution {
    public long solution(int n, int[] works) {
        long total = 0;
        for (int w : works) total += w;

        // 총 작업량이 n 이하면 전부 처리 가능 → 곧바로 0
        int loops = (int) Math.min(n, total);

        PriorityQueue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());
        for (int w : works) heap.add(w);

        for (int i = 0; i < loops; i++) {
            int max = heap.poll();
            heap.add(max - 1);
        }

        long answer = 0;
        for (int w : heap) {
            answer += (long) w * w;
        }
        return answer;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.solution(4, new int[]{4, 3, 3})); // 12
        System.out.println(sol.solution(1, new int[]{2, 1, 2})); // 6
        System.out.println(sol.solution(3, new int[]{1, 1}));    // 0
    }
}
