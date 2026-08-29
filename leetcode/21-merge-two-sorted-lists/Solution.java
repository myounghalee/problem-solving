import java.util.*;

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class Solution {

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        List<Integer> list = new ArrayList<>();
        list.addAll(toList(list1));
        list.addAll(toList(list2));
        Collections.sort(list);

        ListNode[] temp = new ListNode[list.size()];
        if (temp.length == 0) return null;
        for (int i = 0; i < list.size(); i++) {
            temp[i] = new ListNode(list.get(i));
            if (i != 0) temp[i - 1].next = temp[i];
        }

        return temp[0];
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        // 예 1: list1 = [1,2,4], list2 = [1,3,4] -> [1,1,2,3,4,4]
        check(toList(solution.mergeTwoLists(build(1, 2, 4), build(1, 3, 4))), List.of(1, 1, 2, 3, 4, 4));

        // 예 2: list1 = [], list2 = [] -> []
        check(toList(solution.mergeTwoLists(null, null)), List.of());

        // 예 3: list1 = [], list2 = [0] -> [0]
        check(toList(solution.mergeTwoLists(null, build(0))), List.of(0));
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
