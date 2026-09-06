class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode();
        ListNode cur = dummy;
        int carry = 0;

        while (l1 != null || l2 != null || carry != 0) {
            int sum = carry + (l1 != null ? l1.val : 0) + (l2 != null ? l2.val : 0);
            carry = sum / 10;
            cur.next = new ListNode(sum % 10);
            cur = cur.next;
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }

        return dummy.next;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 예 1: [2,4,3] + [5,6,4] -> [7,0,8]  (342+465=807)
        check(sol.addTwoNumbers(build(2, 4, 3), build(5, 6, 4)), java.util.List.of(7, 0, 8));

        // 예 2: [0] + [0] -> [0]
        check(sol.addTwoNumbers(build(0), build(0)), java.util.List.of(0));

        // 예 3: 길이가 다르고 자리올림이 끝까지 이어지는 경우
        check(sol.addTwoNumbers(build(9, 9, 9, 9, 9, 9, 9), build(9, 9, 9, 9)), java.util.List.of(8, 9, 9, 9, 0, 0, 0, 1));

        // 한쪽이 다른 쪽보다 짧고, 자리올림이 짧은 쪽이 끝난 후에도 이어지는 경우
        check(sol.addTwoNumbers(build(1), build(9, 9)), java.util.List.of(0, 0, 1));

        // 자리올림이 전혀 없는 경우
        check(sol.addTwoNumbers(build(1, 2, 3), build(4, 5, 6)), java.util.List.of(5, 7, 9));
    }

    // 편의용 헬퍼: 정수들을 연결 리스트로 변환 (각 자리가 그대로 노드 하나)
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
