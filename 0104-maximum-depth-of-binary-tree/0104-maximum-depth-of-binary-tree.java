/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public int maxDepth(TreeNode root) {
        return level( root , 0);
    }
    public int level(TreeNode node , int level){
        if(node == null){
            return level;
        }
        int right = level(node.right,level+1);
        int left = level(node.left,level+1);
        return Math.max(right,left);
    }
}