import java.util.HashMap;
import java.util.Map;

class Solution {
    public int solution(String[] want, int[] number, String[] discount) {
        Map<String, Integer> wantMap = new HashMap<>();
        for (int i = 0; i < want.length; i++) {
            wantMap.put(want[i], number[i]);
        }

        Map<String, Integer> window = new HashMap<>();
        int answer = 0;

        for (int i = 0; i < discount.length; i++) {
            // 새로 들어오는 날 +1
            window.merge(discount[i], 1, Integer::sum);

            // 10칸을 넘어가면, 10칸 전에 들어왔던 날은 -1 (윈도우에서 제외)
            if (i >= 10) {
                String out = discount[i - 10];
                if (window.merge(out, -1, Integer::sum) == 0) {
                    window.remove(out);
                }
            }

            // 윈도우가 처음으로 10칸을 채운 시점부터 매번 일치 여부 검사
            if (i >= 9 && matches(window, wantMap)) {
                answer++;
            }
        }

        return answer;
    }

    private boolean matches(Map<String, Integer> window, Map<String, Integer> wantMap) {
        for (Map.Entry<String, Integer> entry : wantMap.entrySet()) {
            if (!entry.getValue().equals(window.get(entry.getKey()))) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.solution(
                new String[]{"banana", "apple", "rice", "pork", "pot"},
                new int[]{3, 2, 2, 2, 1},
                new String[]{"chicken", "apple", "apple", "banana", "rice", "apple", "pork", "banana", "pork", "rice", "pot", "banana", "apple", "banana"}
        )); // 3

        System.out.println(sol.solution(
                new String[]{"apple"},
                new int[]{10},
                new String[]{"banana", "banana", "banana", "banana", "banana", "banana", "banana", "banana", "banana", "banana"}
        )); // 0
    }
}
