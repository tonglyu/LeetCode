package Leetcode.Easy;

import Leetcode.TreeNode;

/**
 * 572. Subtree of Another Tree
 * Given two non-empty binary trees s and t, check whether tree t has exactly the same structure and node values with a subtree of s. A subtree of s is a tree consists of a node in s and all of this node's descendants.
 * The tree s could also be considered as a subtree of itself.
 *
 * Example 1:
 * Given tree s:
 *
 *      3
 *     / \
 *    4   5
 *   / \
 *  1   2
 * Given tree t:
 *    4
 *   / \
 *  1   2
 * Return true, because t has the same structure and node values with a subtree of s.
 *
 * Example 2:
 * Given tree s:
 *
 *      3
 *     / \
 *    4   5
 *   / \
 *  1   2
 *     /
 *    0
 * Given tree t:
 *    4
 *   / \
 *  1   2
 * Return false.
 *
 */
public class LC0572 {
	/**
	 * Solution: recursion
	 * For each node during pre-order traversal of s,
	 * 1) check if sub-tree start from s is same as t
	 * 2) if not, check if t is same as subtree of s
	 *
	 * Time = O(m * n)
	 * Space = O(height of s)
	 */
	public boolean isSubtree(TreeNode s, TreeNode t) {
		if (s == null) {
			return false;
		}

		return isSame(s,t) || isSubtree(s.left, t) || isSubtree(s.right, t);
	}

	private boolean isSame(TreeNode node1, TreeNode node2) {
		if (node1 == null && node2 == null) {
			return true;
		}

		if (node1 == null || node2 == null || node1.val != node2.val) {
			return false;
		}

		return isSame(node1.left, node2.left) && isSame(node1.right, node2.right);
	}
}
