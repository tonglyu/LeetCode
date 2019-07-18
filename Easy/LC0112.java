package Leetcode.Easy;

import Leetcode.TreeNode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 112. Path Sum
 * Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all the values along the path equals the given sum.
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
 *  /  \      \
 * 7    2      1
 * return true, as there exist a root-to-leaf path 5->4->11->2 which sum is 22.
 */
public class LC0112 {
	/**
	 * Solution1: DFS recursive
	 * Base case:
	 *      1) if root == null, return false
	 *      2) if root is leaf node, check if leaf equals to the remain sum
	 * check for left child or right child
	 *
	 * Time = O(n)
	 * Space = O(height) -> O(logn) for best
	 */
	public boolean hasPathSum(TreeNode root, int sum) {
		if (root == null)
			return false;
		return helper(root, sum);
	}

	private boolean helper(TreeNode root, int sum) {
		if (root == null)
			return false;
		if (root.left == null && root.right == null) {
			return sum == root.val;
		}

		return helper(root.left, sum - root.val) || helper(root.right, sum - root.val);
	}

	/**
	 * Solution2: DFS iterative
	 * Use two stacks for dfs
	 * S: record all nodes
	 * Remain: record current remain sum
	 * if remain sum = 0, return true
	 *
	 * Time = O(n)
	 * Space = O(n)
	 */
	public boolean hasPathSum2(TreeNode root, int sum) {
		if (root == null)
			return false;

		Deque<TreeNode> s = new LinkedList<>();
		Deque<Integer> remain = new LinkedList<>();
		s.push(root);
		remain.push(sum - root.val);

		while (!s.isEmpty()) {
			TreeNode cur = s.pop();
			int curSum = remain.pop();
			if (cur.left == null && cur.right == null && curSum == 0) {
				return true;
			}
			if (cur.left != null) {
				s.push(cur.left);
				remain.push(curSum - cur.left.val);
			}
			if (cur.right != null) {
				s.push(cur.right);
				remain.push(curSum - cur.right.val);
			}
		}

		return false;
	}

	public static void main(String[] args) {
		LC0112 sol = new LC0112();
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
		System.out.println(sol.hasPathSum(root, sum));
	}
}
