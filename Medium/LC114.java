package Leetcode.Medium;

import Leetcode.TreeNode;

/** 114. Flatten Binary Tree to Linked List
 * Given a binary tree, flatten it to a linked list in-place.
 *
 * For example, given the following tree:
 *
 *     1
 *    / \
 *   2   5
 *  / \   \
 * 3   4   6
 * The flattened tree should look like:
 *
 * 1
 *  \
 *   2
 *    \
 *     3
 *      \
 *       4
 *        \
 *         5
 *          \
 *           6
 */
public class LC114 {
    /**
     * Recursive way
     * 1. Flatten the left and right sub-tree
     * 2. For current level,
     *  2.1 Find the right most leaf of left sub-tree
     *  2.2 Link the right sub-tree to that leaf
     *  2.3 Link the left sub-tree to cur.right, set cur.left = null
     *
     * Time = O(n)
     * Space = O(height)
     */
    public void flatten_recursive(TreeNode root) {
        //base case
        if (root == null)   return;

        // 1. Flatten the sub-tree
        flatten_recursive(root.left);
        flatten_recursive(root.right);

        //2. Find the right-most leaf of the left sub-tree
        TreeNode rightMostLeaf = root.left;
        if (rightMostLeaf == null) return;
        while (rightMostLeaf.right != null) {
            rightMostLeaf = rightMostLeaf.right;
        }

        //3. Link the right sub-tree to the leaf of left sub-tree
        rightMostLeaf.right = root.right;
        // Link the left sub-tree to right sub-tree, and set left-subtree equals to null
        root.right = root.left;
        root.left = null;
    }

    // Time = O(n), Space = O(1)
    public void flatten_iterative(TreeNode root) {
        if (root == null)   return;
        TreeNode cur = root;
        // Traverse until find the last node of linked list.
        // We always first link the right sub-tree to the right most leaf of left sub-tree, and then check for the left subtree's root.
        while  (cur != null) {
            TreeNode left = cur.left;
            TreeNode right = cur.right;
            // if left != null, repeat the main logic
            if (left != null) {
                TreeNode right_most = left;
                while (right_most.right != null) {
                    right_most = right_most.right;
                }
                right_most.right = right;
                cur.right = left;
                cur.left = null;
            }
            cur = cur.right;
        }
    }

    public static void main(String[] args) {
        LC114 sol = new LC114();
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right = new TreeNode(5);
        root.right.right = new TreeNode(6);

        sol.flatten_iterative(root);
    }
}
