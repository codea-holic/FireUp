package Tree;

import java.util.LinkedList;
import java.util.List;
import Tree.Lec001_FindSet.TreeNode;

public class Lec003_MorrisTraversal {

    // Average Time complexity is O(5n) & Space Complexity is O(1);
    public static void inorderMorrisTraversal(TreeNode root, List<Integer> ans) {
            TreeNode curr = root;
            while (curr != null) {

                // thread creation
                if (curr.left == null) {
                    ans.add(curr.val);
                    curr = curr.right;
                    continue;
                }
                TreeNode left = curr.left;
                TreeNode rightMost = getRightMostNode(left, curr);

                // it means thread is already present, need to cut thread
                if (rightMost.right == null) {
                    rightMost.right = curr;
                    curr = curr.left;
                } else {
                    rightMost.right = null;
                    ans.add(curr.val);
                    curr = curr.right;
                }
            }
    }

    public static void preOrderMorrisTraversal(TreeNode root, List<Integer> ans) {
        TreeNode curr = root;
        while (curr != null) {
            if (curr.left == null) {
                ans.add(curr.val);
                curr = curr.right;
                continue;
            }
            TreeNode left = curr.left;
            TreeNode rightMost = getRightMostNode(left, curr);
            if (rightMost.right == null) { // thread creation
                ans.add(curr.val);
                rightMost.right = curr;
                curr = curr.left;
            } else {
                rightMost.right = null;
                curr = curr.right;
            }
        }
    }

    private static TreeNode getRightMostNode(TreeNode itr, TreeNode curr) {
        while (itr.right != null && itr.right != curr) {
            itr = itr.right;
        }
        return itr;
    }

    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MAX_VALUE, Long.MIN_VALUE);
    }

    public boolean isValidBST(TreeNode root, long max, long min) {
        if (root == null)
            return true;
        if (root.val >= max || root.val <= min)
            return false;

        return isValidBST(root.left, root.val, min) && isValidBST(root.right, max, root.val);
    }

    public boolean isValidInOrderBST(TreeNode root) {
        TreeNode curr = root;
        long min = Long.MIN_VALUE;
        while (curr != null) {
            if (curr.left == null) {
                if (curr.val <= min)
                    return false;
                else
                    min = curr.val;
                curr = curr.right;
                continue;
            }
            TreeNode leftNode = curr.left;
            TreeNode rightMost = getRightMostNode(leftNode, curr);
            if (rightMost.right == null) {
                // create thread
                rightMost.right = curr;
                curr = curr.left;
            } else {
                rightMost.right = null;
                if (curr.val <= min)
                    return false;
                else
                    min = curr.val;
                curr = curr.right;
            }
        }
        return true;
    }

    class BSTIterator {
        TreeNode root = null;
        TreeNode curr = null;
        public BSTIterator(TreeNode root) {
            this.root = root;
            this.curr = root;
        }
    
        private TreeNode getRightMostNode(TreeNode root, TreeNode curr){
            while(root.right != null && root.right != curr) root = root.right;
            return root;
        }
        
        public int next() {
            int temp = -1;
            while(curr != null){
                if(curr.left == null){
                    temp = curr.val;
                    curr = curr.right;
                    break;
                }
                TreeNode left = curr.left;
                TreeNode rightMost = getRightMostNode(left, curr);
                if(rightMost.right == null){
                    rightMost.right = curr;
                    curr = curr.left;
                } else{
                    rightMost.right = null;
                    temp = curr.val;
                    curr = curr.right;
                    break;
                }
            }
            return temp;
        }

        public boolean hasNext() {
            return curr != null;
        }
    }
    
    class BSTIteratorUsingStack {
        TreeNode root = null;
        TreeNode curr = null;
        LinkedList<TreeNode> stk = new LinkedList<>();
        public BSTIteratorUsingStack(TreeNode root) {
            this.root = root;
            this.curr = root;
            insertAllLeft(curr, stk);
        }
    
        public int next() {
            int ans = -1;
            if(hasNext()){
                TreeNode rNode = stk.removeFirst();
                ans = rNode.val;
                insertAllLeft(rNode.right, stk);
            }
            return ans;
        }

        private void insertAllLeft(TreeNode node, LinkedList<TreeNode> stk){
            while(node != null){
                stk.addFirst(node);
                node = node.left;
            }
        }
        
        public boolean hasNext() {
            return stk.size() > 0;
        }
    }

    public boolean isValidBSTUsingStack(TreeNode root){
        LinkedList<TreeNode> stk = new LinkedList<TreeNode>();
        long ans = -(long)1e13;
        while(stk.size() != 0){
            TreeNode rNode = stk.removeFirst();
            if(ans >= rNode.val)
                return false;
            ans = rNode.val;
            insertAllLeft(rNode.right, stk);
        }
        return true;
    }

    private void insertAllLeft(TreeNode node, LinkedList<TreeNode> stk){
        while(node != null){
            stk.addFirst(node.left);
            node = node.left;
        }
    }
}