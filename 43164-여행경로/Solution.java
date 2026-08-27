import java.util.*;

class Solution {
    String[] answer;

    public String[] solution(String[][] tickets) {
        answer = new String[tickets.length + 1];
        List<String> path = new ArrayList<>();
        boolean[] visited = new boolean[tickets.length];

        Arrays.sort(tickets, Comparator.comparing(s -> s[1]));

        dfs("ICN", tickets, visited, path);

        return answer;
    }

    private boolean dfs(String from, String[][] tickets, boolean[] visited, List<String> path) {
        if (path.size() == tickets.length) {
            for (int i = 0; i < path.size(); i++) {
                answer[i] = path.get(i);
            }
            answer[path.size()] = from;
            return true;
        }

        for (int i = 0, ticketsLength = tickets.length; i < ticketsLength; i++) {
            if (tickets[i][0].equals(from) && !visited[i]) {
                path.add(from);
                visited[i] = true;
                if (dfs(tickets[i][1], tickets, visited, path)) return true;

                path.removeLast();
                visited[i] = false;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
//        System.out.println(Arrays.toString(sol.solution(new String[][]{{"ICN", "JFK"}, {"HND", "IAD"}, {"JFK", "HND"}}))); // [ICN, JFK, HND, IAD]
        System.out.println(Arrays.toString(sol.solution(new String[][]{{"ICN", "SFO"}, {"ICN", "ATL"}, {"SFO", "ATL"}, {"ATL", "ICN"}, {"ATL", "SFO"}}))); // [ICN, ATL, ICN, SFO, ATL, SFO]
//        System.out.println(Arrays.toString(sol.solution(new String[][]{{"ICN","KUL"}, {"ICN","NRT"}, {"NRT","ICN"}})));
    }
}
