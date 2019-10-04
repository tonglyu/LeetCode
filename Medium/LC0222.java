package Leetcode.Medium;

import Leetcode.TreeNode;

/**
 * 222. Count Complete Tree Nodes
 * Given a complete binary tree, count the number of nodes.
 *
 * Note:
 * Definition of a complete binary tree from Wikipedia:
 * In a complete binary tree every level, except possibly the last, is completely filled, and all nodes in the last level are as far left as possible. It can have between 1 and 2h nodes inclusive at the last level h.
 *
 * Example:
 * Input:
 *     1
 *    / \
 *   2   3
 *  / \  /
 * 4  5 6
 * Output: 6
 */
public class LC0222 {
	/**
	 * Solution: divide and conquer
	 *
	 * For a complete binary tree, it is either also a perfect binary tree or not,
	 * Case1: it is perfect, return 2^h - 1
	 * Case2: it is just complete, then its left subtree and right subtree must be one perfect another one complete
	 *      So we recursively do the same thing to its subtree.
	 *
	 * How to judge if it is perfect?
	 * count the depth of leftmost leaf and rightmost leaf, if they are the same, then it is perfect.
	 *
	 * Time = O(2h + 2(h-1) + 2(h-2) + 2(h-3) + .... + 2) = O(h^2) = O(logn^2)
	 * Space = O(logn)
	 *
	 * For more solutions:
	 * https://blog.csdn.net/jmspan/article/details/51056085
	 */
	public int countNodes(TreeNode root) {
		if (root == null) {
			return 0;
		}

		int leftmost = 0;
		TreeNode left = root;
		while (left != null) {
			leftmost++;
			left = left.left;
		}

		int rightmost = 0;
		TreeNode right = root;
		while (right != null) {
			rightmost++;
			right = right.right;
		}

		if (leftmost == rightmost) {
			return (1 << leftmost) - 1;
		}

		return 1 + countNodes(root.left) + countNodes(root.right);
	}

	public static void main(String[] args) {
		LC0222 sol = new LC0222();
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		root.right.left = new TreeNode(6);
		System.out.println(sol.countNodes(root));
	}

}
