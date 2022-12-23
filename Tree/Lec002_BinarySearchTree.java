package Tree;

import java.util.ArrayList;

import Tree.Lec001_FindSet.TreeNode;

public class Lec002_BinarySearchTree {

    public int size(TreeNode root) {
        if (root == null)
            return 0;
        return size(root.left) + size(root.left) + 1;
    }

    public int height(TreeNode root) {
        return root == null ? -1 : Math.max(height(root.left), height(root.right)) + 1;
    }

    // Iterative solution is much better than recursive solution because we can save
    // stack space.
    public int maximum(TreeNode root) {

        TreeNode itr = root;
        while (itr.right != null) {
            itr = itr.right;
        }
        return itr.val;
    }

    public int minimum(TreeNode root) {

        TreeNode itr = root;
        while (itr.left != null) {
            itr = itr.left;
        }
        return itr.val;
    }

    public boolean find(TreeNode root, int target) {
        TreeNode itr = root;
        while (itr != null) {
            if (itr.val > target) {
                itr = itr.left;
            } else if (itr.val < target) {
                itr = itr.right;
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean nodeToRootPath(TreeNode root, int target, ArrayList<Integer> ans) {
        TreeNode itr = root;
        while (itr != null) {
            if (itr.val > target) {
                ans.add(itr.val);
                itr = itr.left;
            } else if (itr.val < target) {
                ans.add(itr.val);
                itr = itr.right;
            } else {
                ans.add(itr.val);
                return true;
            }
        }

        return false;
    }

    public TreeNode lowestCommonAnsector(TreeNode root, int p, int q) {
        TreeNode itr = root;
        while(itr != null){
            if(itr.val >= p && itr.val <= q){
                return (find(itr, p) && find(itr, q)) ? itr : null;
            } else if(itr.val > p && itr.val > q){
                itr = itr.left;
            } else {
                itr = itr.right;
            }
        }

        return null;
    }
}
