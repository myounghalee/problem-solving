import java.util.*;

class Solution {
    public int[] solution(String[] operations) {
        Queue<Integer> minQ = new PriorityQueue<>();
        Queue<Integer> maxQ = new PriorityQueue<>(Collections.reverseOrder());

        for (String operation : operations) {
            if (operation.startsWith("I ")) {
                int n = Integer.parseInt(operation.substring(2));
                minQ.offer(n);
                maxQ.offer(n);
            } else if (!minQ.isEmpty() && operation.equals("D -1")) {
                maxQ.remove(minQ.poll());
            } else if (!maxQ.isEmpty() && operation.equals("D 1")) {
                minQ.remove(maxQ.poll());
            }
        }

        if (minQ.isEmpty() && maxQ.isEmpty()) {
            return new int[]{0, 0};
        }

        return new int[]{maxQ.poll(), minQ.poll()};
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(Arrays.toString(sol.solution(new String[]{"I 16", "I -5643", "D -1", "D 1", "D 1", "I 123", "D -1"}))); // [0, 0]
        System.out.println(Arrays.toString(sol.solution(new String[]{"I -45", "I 653", "D 1", "I -642", "I 45", "I 97", "D 1", "D -1", "I 333"}))); // [333, -45]
    }
}
