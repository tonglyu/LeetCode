package Leetcode.Easy;

import Leetcode.TreeNode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 617. Merge Two Binary Trees
 * Given two binary trees and imagine that when you put one of them to cover the other, some nodes of the two trees are overlapped while the others are not.
 *
 * You need to merge them into a new binary tree. The merge rule is that if two nodes overlap, then sum node values up as the new value of the merged node. Otherwise, the NOT null node will be used as the node of new tree.
 *
 * Example 1:
 * Input:
 * 	Tree 1                     Tree 2
 *           1                         2
 *          / \                       / \
 *         3   2                     1   3
 *        /                           \   \
 *       5                             4   7
 * Output:
 * Merged tree:
 * 	     3
 * 	    / \
 * 	   4   5
 * 	  / \   \
 * 	 5   4   7
 *
 *
 * Note: The merging process must start from the root nodes of both trees.
 */
public class LC0617 {
	/**
	 * Method1: recursion + in place
	 *
	 * Time = O(m + n)
	 * Space = O(max(h1, h2))
	 */
	public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
		if (t1 == null) return t2;
		if (t2 == null) return t1;

		t1.val += t2.val;
		t1.left = mergeTrees(t1.left, t2.left);
		t1.right = mergeTrees(t1.right, t2.right);

		return t1;
	}

	/**
	 * Method2: Iterative DFS
	 *
	 * Time = O(m + n)
	 * Space = O(max(m,n))
	 */
	public TreeNode mergeTrees2(TreeNode t1, TreeNode t2) {
		if (t1 == null) return t2;
		if (t2 == null) return t1;

		Deque<TreeNode[]> s = new LinkedList<>();
		s.offerFirst(new TreeNode[]{t1,t2});
		while (!s.isEmpty()) {
			TreeNode[] node = s.pollFirst();
			// no need to merge t2 into t1!!!
			if (node[1] == null) continue;
			node[0].val += node[1].val;

			// if node in t1 == null, use node in t2 instead
			// else put both nodes in stack to merge
			if (node[0].right == null) {
				node[0].right = node[1].right;
			} else {
				s.offerFirst(new TreeNode[]{node[0].right, node[1].right});
			}

			if (node[0].left == null) {
				node[0].left = node[1].left;
			}  else {
				s.offerFirst(new TreeNode[]{node[0].left, node[1].left});
			}
		}

		return t1;
	}

	/**
	 * Method3: Iterative BFS
	 *
	 * Time = O(m + n)
	 * Space = O(max(m,n))
	 */
	public TreeNode mergeTrees3(TreeNode t1, TreeNode t2) {
		if (t1 == null) return t2;
		if (t2 == null) return t1;

		Queue<TreeNode[]> q = new LinkedList<>();
		q.offer(new TreeNode[]{t1,t2});
		while (!q.isEmpty()) {
			TreeNode[] node = q.poll();
			// no need to merge t2 into t1!!!
			if (node[1] == null) continue;
			node[0].val += node[1].val;

			// if node in t1 == null, use node in t2 instead
			// else put both nodes in stack to merge
			if (node[0].left == null) {
				node[0].left = node[1].left;
			}  else {
				q.offer(new TreeNode[]{node[0].left, node[1].left});
			}

			if (node[0].right == null) {
				node[0].right = node[1].right;
			} else {
				q.offer(new TreeNode[]{node[0].right, node[1].right});
			}
		}

		return t1;
	}
}
