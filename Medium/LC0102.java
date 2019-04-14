package Leetcode.Medium;

import Leetcode.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 102. Binary Tree Level Order Traversal
 * Given a binary tree, return the level order traversal of its nodes' values. (ie, from left to right, level by level).
 *
 * For example:
 * Given binary tree [3,9,20,null,null,15,7],
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * return its level order traversal as:
 * [
 *   [3],
 *   [9,20],
 *   [15,7]
 * ]
 */
public class LC0102 {
	/**
	 * Data structure: Queue
	 * Initiate queue with a root and start from the level number 0 : level = 0.
	 *
	 * While queue is not empty :
	 *      Start the current level by adding an empty list into output structure levels.
	 *      Compute how many elements should be on the current level : it's a queue length.
	 *      Pop out all these elements from the queue and add them into the current level.
	 *      Push their child nodes into the queue for the next level.
	 * Go to the next level level++.
	 *
	 * Time = O(n)
	 * Space = O(n)
	 */
	public List<List<Integer>> levelOrder(TreeNode root) {
		List<List<Integer>> res = new ArrayList<>();
		if (root == null)   return res;

		Queue<TreeNode> q = new LinkedList<>();
		q.offer(root);
		while (!q.isEmpty()) {
			List<Integer> tmp = new ArrayList<>();
			int size = q.size();
			for (int i = 0; i < size; i++) {
				TreeNode cur = q.poll();
				tmp.add(cur.val);
				if (cur.left != null) {
					q.offer(cur.left);
				}
				if (cur.right != null) {
					q.offer(cur.right);
				}
			}
			res.add(tmp);
		}

		return res;
	}

	/**
	 * Recursion
	 * base case: when we get a new level, we create a list
	 * recursive rule: helper(child, level + 1)
	 *
	 * Time = O(n)
	 * Space = O(height)
	 */
	public List<List<Integer>> levelOrder2(TreeNode root) {
		List<List<Integer>> res = new ArrayList<>();
		if (root == null)   return res;
		helper(root, 0, res);
		return res;
	}

	private void helper(TreeNode root, int level, List<List<Integer>> res) {
		if (res.size()  == level) {
			res.add(new ArrayList<>());
		}

		res.get(level).add(root.val);
		if (root.left != null)  {
			helper(root.left, level + 1, res);
		}
		if (root.right != null) {
			helper(root.right, level + 1, res);
		}
	}

	public static void main(String[] args) {
		LC0102 sol = new LC0102();
		TreeNode root = new TreeNode(3);
		root.left = new TreeNode(9);
		root.left.left = new TreeNode(3);
		root.left.right = new TreeNode(4);
		root.right = new TreeNode(20);
		root.right.right = new TreeNode(6);

		List<List<Integer>> res = sol.levelOrder2(root);
		System.out.println(res);
	}
}
