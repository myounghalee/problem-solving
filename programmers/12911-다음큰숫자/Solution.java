class Solution {
    public int solution(int n) {
        int lowestBit = n & -n;                    // 최하위 1비트만 남김
        int rippled = n + lowestBit;                // 그 1비트를 한 자리 위로 올림(캐리 전파)
        int ones = ((rippled ^ n) >> 2) / lowestBit; // 캐리 과정에서 사라진 1들을 오른쪽 끝에 채워 넣을 값
        return ones | rippled;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.solution(78)); // 83
        System.out.println(sol.solution(15)); // 23
    }
}
