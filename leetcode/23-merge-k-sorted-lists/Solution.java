import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class Solution {

    List<Integer> resultList;

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;

        resultList = new ArrayList<>();

        for (ListNode list : lists) {
            if (list == null) continue;
            dfs(list);
        }

        if (resultList.isEmpty()) return null;

        Collections.sort(resultList);
        ListNode[] temp = new ListNode[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            temp[i] = new ListNode(resultList.get(i));
        }

        for (int i = 0; i < resultList.size() - 1; i++) {
            temp[i].next = temp[i + 1];
        }

        return temp[0];
    }

    private void dfs(ListNode list) {
        if (list == null) return;
        resultList.add(list.val);
        dfs(list.next);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        // 예 1: lists = [[1,4,5],[1,3,4],[2,6]] -> [1,1,2,3,4,4,5,6]
        ListNode[] lists1 = {
                build(1, 4, 5),
                build(1, 3, 4),
                build(2, 6)
        };
        check(toList(solution.mergeKLists(lists1)), List.of(1, 1, 2, 3, 4, 4, 5, 6));

        // 예 2: lists = [] -> []
        check(toList(solution.mergeKLists(new ListNode[]{})), List.of());

        // 예 3: lists = [[]] -> []
        check(toList(solution.mergeKLists(new ListNode[]{null})), List.of());
    }

    // 편의용 헬퍼: 정수들을 연결 리스트로 변환
    private static ListNode build(int... vals) {
        ListNode dummy = new ListNode();
        ListNode cur = dummy;
        for (int v : vals) {
            cur.next = new ListNode(v);
            cur = cur.next;
        }
        return dummy.next;
    }

    // 편의용 헬퍼: 연결 리스트를 List<Integer>로 변환
    private static List<Integer> toList(ListNode node) {
        List<Integer> result = new ArrayList<>();
        while (node != null) {
            result.add(node.val);
            node = node.next;
        }
        return result;
    }

    private static void check(List<Integer> actual, List<Integer> expected) {
        System.out.println((actual.equals(expected) ? "OK" : "FAIL") + " actual=" + actual + " expected=" + expected);
    }
}
