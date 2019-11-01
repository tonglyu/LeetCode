package Leetcode.Medium;

import Leetcode.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 545. Boundary of Binary Tree
 * Given a binary tree, return the values of its boundary in anti-clockwise direction starting from root. Boundary includes left boundary, leaves, and right boundary in order without duplicate nodes.  (The values of the nodes may still be duplicates.)
 *
 * Left boundary is defined as the path from root to the left-most node. Right boundary is defined as the path from root to the right-most node. If the root doesn't have left subtree or right subtree, then the root itself is left boundary or right boundary.
 * Note this definition only applies to the input binary tree, and not applies to any subtrees.
 *
 * The left-most node is defined as a leaf node you could reach when you always firstly travel to the left subtree if exists. If not, travel to the right subtree. Repeat until you reach a leaf node.
 * The right-most node is also defined by the same way with left and right exchanged.
 *
 * Example 1
 * Input:
 *   1
 *    \
 *     2
 *    / \
 *   3   4
 *
 * Ouput:
 * [1, 3, 4, 2]
 *
 * Explanation:
 * The root doesn't have left subtree, so the root itself is left boundary.
 * The leaves are node 3 and 4.
 * The right boundary are node 1,2,4. Note the anti-clockwise direction means you should output reversed right boundary.
 * So order them in anti-clockwise without duplicates and we have [1,3,4,2].
 *
 * Example 2
 * Input:
 *     ____1_____
 *    /          \
 *   2            3
 *  / \          /
 * 4   5        6
 *    / \      / \
 *   7   8    9  10
 *
 * Ouput:
 * [1,2,4,7,8,9,10,6,3]
 *
 * Explanation:
 * The left boundary are node 1,2,4. (4 is the left-most node according to definition)
 * The leaves are node 4,7,8,9,10.
 * The right boundary are node 1,3,6,10. (10 is the right-most node).
 * So order them in anti-clockwise without duplicate nodes we have [1,2,4,7,8,9,10,6,3].
 */
public class LC0545 {
	/**
	 * Solution: recursion
	 * Divide this problem into three subproblems - left boundary, leaves and right boundary.
	 *  1)Left Boundary: We keep on traversing the tree towards the left and keep on adding the nodes in the res, provided the current node isn't a leaf node.
	 *          If at any point, we can't find the left child of a node, but its right child exists, we put the right child in the res and continue the process.
	 *  2)Leaf Nodes: If the current root node happens to be a leaf node, it is added to the res. Otherwise, we make the recursive call using the left child and right child.
	 *  3)Right Boundary: We perform the same process as the left boundary. But, this time, we traverse towards the right.
	 *          If the right child doesn't exist, we move towards the left child. After we finish the traversal, we add the root values into res.
	 *
	 * Time = O(n)
	 * Space = O(height)
	 */
	public List<Integer> boundaryOfBinaryTree(TreeNode root) {
		List<Integer> res = new ArrayList<>();
		if (root == null) {
			return res;
		}

		res.add(root.val);
		addLeft(root.left, res);
		addLeaves(root.left, res);
		addLeaves(root.right, res);
		addRight(root.right, res);

		return res;
	}

	private void addLeft(TreeNode root, List<Integer> res) {
		// if the root is null, means we don't have this subtree or it is a leaf node, just return
		if (root == null || root.left == null && root.right == null) {
			return;
		}

		res.add(root.val);
		//prefer left subtree first
		if (root.left != null) {
			addLeft(root.left, res);
		} else {
			addLeft(root.right, res);
		}
	}

	private void addLeaves(TreeNode root, List<Integer> res) {
		if (root == null) {
			return;
		}

		if (root.left == null && root.right == null) {
			res.add(root.val);
			return;
		}

		addLeaves(root.left, res);
		addLeaves(root.right, res);
	}

	private void addRight(TreeNode root,  List<Integer> res) {
		if (root == null || root.left == null && root.right == null) {
			return;
		}

		if (root.right != null) {
			addRight(root.right, res);
		} else {
			addRight(root.left, res);
		}
		res.add(root.val);
	}

	public static void main(String[] args) {
		LC0545 sol = new LC0545();
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		root.left.right.left = new TreeNode(7);
		root.left.right.right = new TreeNode(8);
		root.right.left = new TreeNode(6);
		root.right.left.left = new TreeNode(9);
		root.right.left.right = new TreeNode(10);

		System.out.println(sol.boundaryOfBinaryTree(root));
	}
}
