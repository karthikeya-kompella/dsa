class Solution {
    public int numTrees(int n) {
        if(n<=1){
            return 1;
            

        }
        int total = 0;
        for(int root = 1; root<=n;root++){
            int left =  numTrees(root-1);
            int right = numTrees(n-root);
            total += left*right;

        }
        return total;
    }
}