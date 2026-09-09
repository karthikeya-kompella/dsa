class Solution {
    public long countCommas(long n) {
        long ans = 0;

        for (long power = 1000; power <= n; ) {
            ans += n - power + 1;

            // Move to next comma position
            if (power > n / 1000) {
                break;
            }

            power *= 1000;
        }

        return ans;
    }
}