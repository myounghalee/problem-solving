import org.w3c.dom.NodeList;

import java.util.*;

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class Solution {

    public ListNode removeNodes(ListNode head) {


        Deque<ListNode> d = new ArrayDeque<>();
        d.add(head);

        ListNode to = head.next;
        while (to != null) {
            while (!d.isEmpty()) {
                if (d.peekLast().val >= to.val) {
                    d.add(to);
                    break;
                }
                d.removeLast();
            }
            if (d.isEmpty()) d.add(to);
            to = to.next;
        }

        if (d.isEmpty()) return null;
        head = d.poll();
        ListNode cur = head;
        while (!d.isEmpty()) {
            ListNode next = d.poll();
            cur.next = next;
            cur = next;
        }

        return head;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        // 예 1: [5,2,13,3,8] -> [13,8]
        check(toList(solution.removeNodes(build(5, 2, 13, 3, 8))), List.of(13, 8));

        // 예 2: [1,1,1,1] -> [1,1,1,1]  (같은 값은 제거 사유가 아니다)
        check(toList(solution.removeNodes(build(1, 1, 1, 1))), List.of(1, 1, 1, 1));

        // 노드 하나
        check(toList(solution.removeNodes(build(7))), List.of(7));

        // 내림차순이면 전부 살아남는다
        check(toList(solution.removeNodes(build(9, 7, 5, 3, 1))), List.of(9, 7, 5, 3, 1));

        // 오름차순이면 마지막 하나만 남는다
        check(toList(solution.removeNodes(build(1, 3, 5, 7, 9))), List.of(9));

        // 최댓값이 맨 뒤에 있는 경우
        check(toList(solution.removeNodes(build(2, 4, 3, 10))), List.of(10));
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
        System.out.println((actual.equals(expected) ? "OK  " : "FAIL") + " actual=" + actual + " expected=" + expected);
    }
}
