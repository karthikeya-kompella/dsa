import java.util.*;

class Solution {

    public int[] maximumWeight(List<List<Integer>> intervals) {

        int n = intervals.size();

        // arr[i] = {start, end, value, originalIndex}
        int[][] arr = new int[n][4];

        for (int i = 0; i < n; i++) {
            arr[i][0] = intervals.get(i).get(0); // start
            arr[i][1] = intervals.get(i).get(1); // end
            arr[i][2] = intervals.get(i).get(2); // value
            arr[i][3] = i;                       // original index
        }

        // Sort according to ending time
        Arrays.sort(arr, (a, b) -> {
            if (a[1] != b[1]) {
                return Integer.compare(a[1], b[1]);
            }
            return Integer.compare(a[0], b[0]);
        });

        /*
         * dp[k][i] =
         * maximum score using at most k intervals
         * among the first i intervals
         */
        long[][] dp = new long[5][n + 1];

        /*
         * take[k][i] =
         * list of original indices producing dp[k][i]
         */
        @SuppressWarnings("unchecked")
        List<Integer>[][] take = new ArrayList[5][n + 1];

        // Initialize lists
        for (int k = 0; k <= 4; k++) {
            for (int i = 0; i <= n; i++) {
                take[k][i] = new ArrayList<>();
            }
        }

        // We can select at most 4 intervals
        for (int k = 1; k <= 4; k++) {

            for (int i = 1; i <= n; i++) {

                /*
                 * OPTION 1:
                 * Don't take current interval
                 */
                dp[k][i] = dp[k][i - 1];

                take[k][i] =
                        new ArrayList<>(take[k][i - 1]);

                /*
                 * OPTION 2:
                 * Take current interval
                 */

                int current = i - 1;

                /*
                 * Find the last interval whose
                 * ending time < current starting time.
                 */
                int previous = findPrevious(
                        arr,
                        current,
                        arr[current][0]
                );

                /*
                 * previous is an index in arr.
                 *
                 * dp uses number of intervals, so
                 * previous + 1 gives the correct dp column.
                 */
                long candidate =
                        dp[k - 1][previous + 1]
                        + arr[current][2];

                /*
                 * Construct candidate index list
                 */
                List<Integer> candidateList =
                        new ArrayList<>(
                                take[k - 1][previous + 1]
                        );

                candidateList.add(arr[current][3]);

                /*
                 * IMPORTANT:
                 *
                 * LeetCode requires the returned indices
                 * to be lexicographically smallest when
                 * multiple answers have the same score.
                 *
                 * Therefore sort the original indices.
                 */
                Collections.sort(candidateList);

                /*
                 * Replace current answer if:
                 *
                 * 1. Candidate has greater score
                 *
                 * OR
                 *
                 * 2. Same score but candidate indices
                 *    are lexicographically smaller.
                 */
                if (candidate > dp[k][i] ||
                        (candidate == dp[k][i]
                                && isLexicographicallySmaller(
                                        candidateList,
                                        take[k][i]))) {

                    dp[k][i] = candidate;
                    take[k][i] = candidateList;
                }
            }
        }

        /*
         * Get final selected indices
         */
        List<Integer> answer = take[4][n];

        Collections.sort(answer);

        int[] result = new int[answer.size()];

        for (int i = 0; i < answer.size(); i++) {
            result[i] = answer.get(i);
        }

        return result;
    }


    /*
     * Binary search:
     *
     * Find the last interval in arr[0...index]
     * whose ending time is strictly less than start.
     */
    private int findPrevious(
            int[][] arr,
            int index,
            int start) {

        int left = 0;
        int right = index;
        int answer = -1;

        while (left <= right) {

            int mid =
                    left + (right - left) / 2;

            if (arr[mid][1] < start) {

                answer = mid;
                left = mid + 1;

            } else {

                right = mid - 1;
            }
        }

        return answer;
    }


    /*
     * Compare two lists lexicographically.
     *
     * Example:
     *
     * [3, 4] < [4, 7]
     *
     * because 3 < 4.
     */
    private boolean isLexicographicallySmaller(
            List<Integer> a,
            List<Integer> b) {

        int n = Math.min(a.size(), b.size());

        for (int i = 0; i < n; i++) {

            if (!a.get(i).equals(b.get(i))) {

                return a.get(i) < b.get(i);
            }
        }

        /*
         * If one is a prefix of the other,
         * shorter list is lexicographically smaller.
         */
        return a.size() < b.size();
    }
}