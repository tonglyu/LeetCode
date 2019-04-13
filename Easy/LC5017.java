package Leetcode.Easy;

import Leetcode.TreeNode;

public class LC5017 {
	private double total = 0;

	public int sumRootToLeaf(TreeNode root) {
		if (root == null) {
			return 0;
		}

		helper(root, 0);
		int m = 1000000007;
		return (int)total % m;
	}

	void helper(TreeNode root, double sum) {
		if (root == null) return;

		sum = sum * 2 + root.val;

		if (root.left == null && root.right == null) {
			total += sum;
			return;
		}

		helper(root.left, sum);
		helper(root.right, sum);

	}

	public static void main(String[] args) {
		LC5017 sol = new LC5017();
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(0);
		root.left.left = new TreeNode(0);
		root.left.right = new TreeNode(1);
		root.right = new TreeNode(1);
		root.right.left = new TreeNode(0);
		root.right.right = new TreeNode(1);

		System.out.println(sol.sumRootToLeaf(root));
	}
}
