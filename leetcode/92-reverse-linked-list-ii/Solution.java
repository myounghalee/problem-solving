class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class Solution {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(0, head);
        ListNode pre = dummy;
        for (int i = 0; i < left - 1; i++) pre = pre.next; // 구간 시작 바로 앞까지 이동

        ListNode start = pre.next; // 구간의 첫 노드. 끝까지 자리를 지키며 꼬리 역할을 한다
        ListNode then = start.next; // 매번 맨 앞으로 옮겨질 노드

        for (int i = 0; i < right - left; i++) { // 구간 길이보다 1번 적게 반복
            start.next = then.next;
            then.next = pre.next;
            pre.next = then;
            then = start.next;
        }

        return dummy.next;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 예 1: [1,2,3,4,5], left=2, right=4 -> [1,4,3,2,5]
        check(sol.reverseBetween(build(1, 2, 3, 4, 5), 2, 4), java.util.List.of(1, 4, 3, 2, 5));

        // 예 2: [5], left=1, right=1 -> [5]
        check(sol.reverseBetween(build(5), 1, 1), java.util.List.of(5));

        // 맨 앞부터 뒤집는 경우 (left=1) — head 자체가 바뀜
        check(sol.reverseBetween(build(1, 2, 3, 4, 5), 1, 3), java.util.List.of(3, 2, 1, 4, 5));

        // 맨 끝까지 뒤집는 경우 (right가 마지막 노드)
        check(sol.reverseBetween(build(1, 2, 3, 4, 5), 3, 5), java.util.List.of(1, 2, 5, 4, 3));

        // 전체를 뒤집는 경우 (left=1, right=n)
        check(sol.reverseBetween(build(1, 2, 3, 4), 1, 4), java.util.List.of(4, 3, 2, 1));
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
