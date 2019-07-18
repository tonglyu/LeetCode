package Leetcode.Medium;

import Leetcode.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 113. Path Sum II
 * Given a binary tree and a sum, find all root-to-leaf paths where each path's sum equals the given sum.
 * Note: A leaf is a node with no children.
 *
 * Example:
 * Given the below binary tree and sum = 22,
 *
 *       5
 *      / \
 *     4   8
 *    /   / \
 *   11  13  4
 *  /  \    / \
 * 7    2  5   1
 * Return:
 * [
 *    [5,4,11,2],
 *    [5,8,4,5]
 * ]
 */
public class LC0113 {
	/**
	 * Approach: DFS
	 * 1) How many levels: #levels of tree
	 * 2) What does every level represent? Whether to add this level's node to the list or not
	 *
	 * Recursion:
	 * 1) Base: if root == null, return
	 * 2) Rule: add this node to the list
	 *      if node is a leaf node && remain sum = root.val
	 *          add path to result, remove last element in the path
	 *
	 * Time = O(n)  n = # nodes of tree
	 * Space = O(height)
	 */
	public List<List<Integer>> pathSum(TreeNode root, int sum) {
		List<List<Integer>> res = new ArrayList<>();
		if (root == null)
			return res;

		List<Integer> path = new ArrayList<>();
		helper(root, sum, path, res);
		return res;
	}

	private void helper(TreeNode root, int sum, List<Integer> path, List<List<Integer>> res) {
		if (root == null)
			return;

		path.add(root.val);
		if (root.left == null && root.right == null && sum == root.val) {
			res.add(new ArrayList<>(path));
			path.remove(path.size() - 1);
			return;
		}

		helper(root.left, sum - root.val, path, res);
		helper(root.right, sum - root.val, path, res);
		path.remove(path.size() - 1);
	}

	public static void main(String[] args) {
		LC0113 sol = new LC0113();
		TreeNode root = new TreeNode(5);
		root.left = new TreeNode(4);
		root.left.left = new TreeNode(11);
		root.left.left.left = new TreeNode(7);
		root.left.left.right = new TreeNode(2);
		root.right = new TreeNode(8);
		root.right.left = new TreeNode(13);
		root.right.right = new TreeNode(4);
		root.right.right.left = new TreeNode(5);
		root.right.right.right = new TreeNode(1);

		int sum = 22;
		List<List<Integer>> res = sol.pathSum(root, sum);
		System.out.println(res);
	}
}
