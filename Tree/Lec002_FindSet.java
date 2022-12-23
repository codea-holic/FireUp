package Tree;

import java.util.ArrayList;
import java.util.HashSet;

import Tree.Lec001_FindSet.TreeNode;

/* Find Set */
public class Lec002_FindSet {

    // https://practice.geeksforgeeks.org/problems/burning-tree/1
    private static void BurningTreeNode(TreeNode root, int time, TreeNode blocker, ArrayList<ArrayList<Integer>> ans) {
        if (root == null || root == blocker)
            return;
        if (time == ans.size())
            ans.add(new ArrayList<>());
        ans.get(time).add(root.val);

        BurningTreeNode(root.left, time + 1, blocker, ans);
        BurningTreeNode(root.right, time + 1, blocker, ans);
    }

    public static int BurningTree(TreeNode root, TreeNode FireNode, ArrayList<ArrayList<Integer>> ans) {
        if (root == null)
            return -1;
        if (root.val == FireNode.val) {
            BurningTreeNode(root, 0, null, ans);
            return 1;
        }
        int leftTime = BurningTree(root.left, FireNode, ans);
        if (leftTime > 0) {
            BurningTreeNode(root, leftTime, root.left, ans);
            return leftTime + 1;
        }
        int rightTime = BurningTree(root.right, FireNode, ans);
        if (rightTime > 0) {
            BurningTreeNode(root, rightTime, root.right, ans);
            return rightTime + 1;
        }
        return -1;
    }
    /* Question: Find out Worst Time Complexity for above function? */
    // Solution: Complete Binary Tee with RightMost set as FireNode... //

    private static void BurningTreeNodeWithWater(TreeNode root, int time, TreeNode blocker, HashSet<Integer> waterSet,
            ArrayList<ArrayList<Integer>> ans) {
        if (root == null || waterSet.contains(root.val) || root == blocker)
            return;
        if (time == ans.size())
            ans.add(new ArrayList<>());
        ans.get(time).add(root.val);

        BurningTreeNodeWithWater(root.left, time + 1, blocker, waterSet, ans);
        BurningTreeNodeWithWater(root.right, time + 1, blocker, waterSet, ans);
    }

    public static int BurningTreeWithWater(TreeNode root, TreeNode FireNode, ArrayList<ArrayList<Integer>> ans,
        HashSet<Integer> waterSet) {
        if (root == null)
            return -1;
        if (root.val == FireNode.val) {
            if(!waterSet.contains(FireNode.val)){
                BurningTreeNodeWithWater(root, 0, null, waterSet, ans);
                return 1;
            }
            return -2;  // fire node is present, but it have water.
        }
        int leftTime = BurningTreeWithWater(root.left, FireNode, ans, waterSet);
        if (leftTime > 0) {
            if(!waterSet.contains(FireNode.val)){
                BurningTreeNodeWithWater(root, leftTime, root.left, waterSet, ans);
                return leftTime + 1;
            }
            return -2;
        }
        if(leftTime == -2) return -2;
        
        int rightTime = BurningTreeWithWater(root.right, FireNode, ans, waterSet);
        if (rightTime > 0) {
            if(!waterSet.contains(FireNode.val)){
                BurningTreeNodeWithWater(root, rightTime, root.right, waterSet, ans);
                return rightTime + 1;
            }
            return -2;
        }

        if(rightTime == -2) return -2;
        return -1;
    }

    // https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
    TreeNode LCANode = null;
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        lowestCommonAncestor_(root, p, q);
        return LCANode;
    }

    public boolean lowestCommonAncestor_(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null) return false;
        boolean selfPresent = (root == p || root == q);

        boolean leftPresent = lowestCommonAncestor_(root.left, p, q);
        boolean rightPresent = lowestCommonAncestor_(root.right, p, q);

        if(leftPresent && rightPresent || leftPresent && selfPresent || rightPresent && selfPresent){
            LCANode = root;
        }

        return leftPresent || rightPresent || selfPresent;
    }

}
