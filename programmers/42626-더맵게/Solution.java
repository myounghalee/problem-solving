import java.util.*;

class Solution {
    public int solution(int[] scoville, int K) {
        int answer = 0;
        Queue<Integer> q = new PriorityQueue<>();
        for (int s : scoville) q.offer(s);

        while (!q.isEmpty()) {
            if (q.peek() >= K) return answer;
            if (q.size() < 2) break;

            int s1 = q.poll();
            int s2 = q.poll();
            q.add(s1 + s2 * 2);
            answer++;
        }

        return -1;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.solution(new int[]{1, 2, 3, 9, 10, 12}, 7)); // 2
    }
}
