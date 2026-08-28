import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

class Solution {
    public int solution(int n, int[][] edge) {
        List<List<Integer>> graph = new ArrayList<>(); // 인접 리스트 (graph.get(i) = i와 연결된 노드 목록)
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] e : edge) {
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]); // 양방향이므로 역방향도 추가
        }

        int[] dist = new int[n + 1]; // 1번 노드로부터의 최단 거리, -1은 미방문
        Arrays.fill(dist, -1);
        dist[1] = 0;

        Deque<Integer> queue = new ArrayDeque<>(); // BFS 큐
        queue.offer(1);
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int next : graph.get(cur)) {
                if (dist[next] == -1) {
                    dist[next] = dist[cur] + 1;
                    queue.offer(next);
                }
            }
        }

        int maxDist = 0; // 전체 노드 중 최단 거리의 최댓값
        for (int i = 1; i <= n; i++) {
            maxDist = Math.max(maxDist, dist[i]);
        }

        int answer = 0; // 최댓값과 거리가 같은 노드 개수
        for (int i = 1; i <= n; i++) {
            if (dist[i] == maxDist) {
                answer++;
            }
        }
        return answer;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.solution(6, new int[][]{{3, 6}, {4, 3}, {3, 2}, {1, 3}, {1, 2}, {2, 4}, {5, 2}})); // 3
    }
}
