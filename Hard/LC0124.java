package Leetcode.Hard;

import Leetcode.TreeNode;

/**
 * 124. Binary Tree Maximum Path Sum
 * Given a non-empty binary tree, find the maximum path sum.
 *
 * For this problem, a path is defined as any sequence of nodes from some starting node to any node in the tree along the parent-child connections.
 * The path must contain at least one node and does not need to go through the root.
 *
 * Example 1:
 * Input: [1,2,3]
 *
 *        1
 *       / \
 *      2   3
 *
 * Output: 6
 *
 * Example 2:
 * Input: [-10,9,20,null,null,15,7]
 *
 *    -10
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 * Output: 42
 */
public class LC0124 {
	/**
	 * Solution: recursion
	 * Base case: root == null -> return 0
	 * What do we want from our children:
	 *      Get the sum from left & right child, and compare with 0:  max(0, helper(root.left))
	 * What to do on current level:
	 *      compare maxValue with the new path contains root (connect to left and right):  max(left + right + root.val, maxValue)
	 * What to return to parent:
	 *      the max old path contains root: max(left, right) + root.val
	 *
	 * Time = O(n)
	 * Space = O(height)
	 */
	int max =  Integer.MIN_VALUE;
	public int maxPathSum(TreeNode root) {
		if (root == null) {
			return 0;
		}

		helper(root);
		return max;
	}

	private int helper(TreeNode root) {
		if (root == null) {
			return 0;
		}

		int left = Math.max(0, helper(root.left));
		int right = Math.max(0, helper(root.right));
		int res = left + right + root.val;
		max = Math.max(max, res);
		return Math.max(left, right) + root.val;
	}

	public static void main(String[] args) {
		LC0124 sol = new LC0124();
		TreeNode root = new TreeNode(10);
		root.left = new TreeNode(5);
		root.left.left = new TreeNode(3);
		root.left.left.left = new TreeNode(3);
		root.left.left.right = new TreeNode(-2);
		root.left.right = new TreeNode(2);
		root.left.right.right = new TreeNode(1);
		root.right = new TreeNode(-3);
		root.right.right = new TreeNode(11);

		System.out.println(sol.maxPathSum(root));
	}
}
