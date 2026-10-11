
import java.util.PriorityQueue;

class Solution {
    public int kthSmallest(TreeNode root, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        addAll(root, pq);

        for (int i = 1; i < k; i++) {
            pq.poll();
        }

        return pq.poll();
    }

    private void addAll(TreeNode root, PriorityQueue<Integer> pq) {
        if (root == null) {
            return;
        }

        pq.offer(root.val);
        addAll(root.left, pq);
        addAll(root.right, pq);
    }
}
