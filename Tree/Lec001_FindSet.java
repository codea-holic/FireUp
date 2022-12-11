package Tree;

import java.util.ArrayList;
import java.util.List;

public class Lec001_FindSet {

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
    
    private void printKDown(TreeNode node, int k, List<Integer> ans, TreeNode blocker) {
        if(node == null || node == blocker) return;
        if(k == 0) {
            ans.add(node.val);
            return;
        }
        
        printKDown(node.left, k - 1, ans, blocker);
        printKDown(node.right, k - 1, ans, blocker);
    }

    // https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/description/
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        ArrayList<TreeNode> nodes = nodeToRootPath(root, target.val);
        List<Integer> ans = new ArrayList<>();
        
        for(int i = 0; i < nodes.size(); i++){
            printKDown(nodes.get(i), k - i, ans, i == 0 ? null : nodes.get(i - 1));
        }
        return ans;
    }

    private int distanceKII_(TreeNode root, TreeNode target, int k, List<Integer> list){
        if(root == null) return -1;
        if(root.val == target.val){
            printKDown(root, k, list, null);
            return 1;
        }
        int resLeft = distanceKII_(root.left, target, k, list);
        if(resLeft > 0){
            printKDown(root, k - resLeft, list, root.left);
            return resLeft + 1;
        }
        int resRight = distanceKII_(root.right, target, k, list);
        if(resRight > 0){
            printKDown(root, k - resRight, list, root.right);
            return resRight + 1;
        }
        return -1;
    }

    /* Better Method (We doesn't need to store node to Root Path) Solely based on find function. */
    public List<Integer> distanceKII(TreeNode root, TreeNode target, int k){
        List<Integer> list = new ArrayList<>();
        distanceKII_(root, target, k, list);
        return list;
    }

}


// Find Set
// View Set
// Diameter & Sum Set
// Construction Set
// Morris and Traversal Set