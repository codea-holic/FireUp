package Tree;

import java.util.ArrayList;
import java.util.List;

public class Lec001 {

    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }
    }

    public static int size(TreeNode root) {
        if (root == null)
            return 0;
        return size(root.left) + size(root.right) + 1;
    }

    /*
     * Confusion between Height & Depth, this is all about perspective,
     * if you look mountain from base then you will call it as height of mountain
     * but if you look from mountain top, then same thing will depth for you.
     * We calculate height in the respect of edge
     */

    public static int height(TreeNode root) {
        return root == null ? -1 : Math.max(height(root.left), height(root.right)) + 1;
    }

    public static int maximum(TreeNode root) {
        return root == null ? -(int) 1e9 : Math.max(Math.max(maximum(root.left), maximum(root.right)), root.val);
    }

    /* Important function */
    public static boolean find(TreeNode root, int data) {
        if (root == null)
            return false;

        if (root.val == data)
            return true;

        return find(root.left, data) || find(root.right, data);
    }

    private static boolean nodeToRootPath_(TreeNode root, int data, ArrayList<TreeNode> list) {
        if (root == null)
            return false;
        if (root.val == data) {
            list.add(root);
            return true;
        }

        boolean res = nodeToRootPath_(root.left, data, list) || nodeToRootPath_(root.right, data, list);
        if (res)
            list.add(root);
        return res;
    }

    public static ArrayList<TreeNode> nodeToRootPath(TreeNode root, int data) {

        ArrayList<TreeNode> list = new ArrayList<>();
        nodeToRootPath_(root, data, list);
        return list;
    }

    public static ArrayList<TreeNode> nodeToRootPath_(TreeNode root, int data) {
        if (root == null)
            return new ArrayList<>();

        if (root.val == data) {
            ArrayList<TreeNode> baseList = new ArrayList<>();
            baseList.add(root);
            return baseList;
        }

        ArrayList<TreeNode> leftPath = nodeToRootPath_(root.left, data);
        if (leftPath.size() > 0) {
            leftPath.add(root);
            return leftPath;
        }

        ArrayList<TreeNode> rightPath = nodeToRootPath_(root.right, data);
        if (rightPath.size() > 0) {
            rightPath.add(root);
            return rightPath;
        }

        return null;
    }

    private static void NodeToAllLeafPaths_(TreeNode root, ArrayList<ArrayList<Integer>> totalPaths,
            ArrayList<Integer> path) {
        if (root == null)
            return;
        if (root.left == null && root.right == null) {
            ArrayList<Integer> base = new ArrayList<>(path);
            base.add(root.val);
            totalPaths.add(base);
            return;
        }

        path.add(root.val);
        NodeToAllLeafPaths_(root.left, totalPaths, path);
        NodeToAllLeafPaths_(root.right, totalPaths, path);
        path.remove(path.size() - 1);
    }

    public static ArrayList<ArrayList<Integer>> NodeToAllLeafPaths(TreeNode root) {

        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        ArrayList<Integer> ans = new ArrayList<>();
        NodeToAllLeafPaths_(root, result, ans);
        return result;
    }

    private static void allSingleChildParentInBinaryTree_(TreeNode root, ArrayList<Integer> ans) {
        if (root.left == null && root.right == null)    // Leaf Node
            return;
        
        if(root.left == null || root.right == null) {
            ans.add(root.val);
        }

        allSingleChildParentInBinaryTree_(root.left, ans);
        allSingleChildParentInBinaryTree_(root.right, ans);
    }

    public static ArrayList<Integer> allSingleChildParentInBinaryTree(TreeNode root) {

        ArrayList<Integer> ans = new ArrayList<>();
        allSingleChildParentInBinaryTree_(root, ans);
        return ans;
    }

    
    public static int count = 0;
    public static int countAllSingleChildParent(TreeNode root){
        if(root == null || (root.left == null && root.right == null)) return 0;

        if(root.left == null || root.right == null)
            count++;

        countAllSingleChildParent(root.left);
        countAllSingleChildParent(root.right);
        return count;
    }
    
    private void printKDist(TreeNode node, int k, List<Integer> ans, TreeNode blocker) {
        if(node == null || node == blocker) return;
        if(k == 0) {
            ans.add(node.val);
            return;
        }
        
        printKDist(node.left, k - 1, ans, blocker);
        printKDist(node.right, k - 1, ans, blocker);
    }

    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        ArrayList<TreeNode> nodes = nodeToRootPath(root, target.val);
        List<Integer> ans = new ArrayList<>();
        
        for(int i = 0; i < nodes.size(); i++){
            printKDist(nodes.get(i), k - i, ans, i == 0 ? null : nodes.get(i - 1));
        }
        return null;
    }


    public static void main(String[] args) {

    }
}