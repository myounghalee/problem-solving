class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        while (head != null) {
            ListNode tmp = head;
            head = head.next;
            tmp.next = prev;
            prev = tmp;
        }
        return prev;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 예 1: [1,2,3,4,5] -> [5,4,3,2,1]
        check(sol.reverseList(build(1, 2, 3, 4, 5)), java.util.List.of(5, 4, 3, 2, 1));

        // 예 2: [1,2] -> [2,1]
        check(sol.reverseList(build(1, 2)), java.util.List.of(2, 1));

        // 예 3: 빈 리스트
        check(sol.reverseList(null), java.util.List.of());

        // 노드 하나뿐
        check(sol.reverseList(build(1)), java.util.List.of(1));
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
    private static java.util.List<Integer> toList(ListNode node) {
        java.util.List<Integer> result = new java.util.ArrayList<>();
        while (node != null) {
            result.add(node.val);
            node = node.next;
        }
        return result;
    }

    private static void check(ListNode actual, java.util.List<Integer> expected) {
        java.util.List<Integer> actualList = toList(actual);
        System.out.println((actualList.equals(expected) ? "OK  " : "FAIL") + " actual=" + actualList + " expected=" + expected);
    }
}
