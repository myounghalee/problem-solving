class Solution {
    public long solution(int[] sequence) {
        long maxEndingHere = 0, maxSoFar = Long.MIN_VALUE;
        long minEndingHere = 0, minSoFar = Long.MAX_VALUE;

        for (int i = 0; i < sequence.length; i++) {
            long a = (i % 2 == 0) ? sequence[i] : -sequence[i];

            maxEndingHere = Math.max(a, maxEndingHere + a);
            maxSoFar = Math.max(maxSoFar, maxEndingHere);

            minEndingHere = Math.min(a, minEndingHere + a);
            minSoFar = Math.min(minSoFar, minEndingHere);
        }

        return Math.max(maxSoFar, -minSoFar);
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.solution(new int[]{2, 3, -6, 1, 3, -1, 2, 4})); // 10
    }
}
