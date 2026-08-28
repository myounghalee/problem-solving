import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution {
    public String[] solution(String[] record) {
        Map<String, String> nicknameOf = new HashMap<>(); // 유저ID -> 최종 닉네임

        // 1차 순회: Enter/Change만 반영해서 각 유저의 마지막 닉네임을 계속 덮어씀
        for (String line : record) {
            String[] tokens = line.split(" ");
            if (!tokens[0].equals("Leave")) {
                nicknameOf.put(tokens[1], tokens[2]);
            }
        }

        // 2차 순회: Enter/Leave만 골라, 최종 닉네임으로 메시지 생성
        StringBuilder sb = new StringBuilder();
        for (String line : record) {
            String[] tokens = line.split(" ");
            String action = tokens[0];
            String nickname = nicknameOf.get(tokens[1]);

            if (action.equals("Enter")) {
                sb.append(nickname).append("님이 들어왔습니다.!");
            } else if (action.equals("Leave")) {
                sb.append(nickname).append("님이 나갔습니다.!");
            }
        }
        return sb.toString().split("!"); // 구분자로 이어붙였다가 한 번에 배열로 쪼갬
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(Arrays.toString(sol.solution(new String[]{
                "Enter uid1234 Muzi", "Enter uid4567 Prodo", "Leave uid1234", "Enter uid1234 Prodo", "Change uid4567 Ryan"
        })));
        // [Prodo님이 들어왔습니다., Ryan님이 들어왔습니다., Prodo님이 나갔습니다., Prodo님이 들어왔습니다.]
    }
}
