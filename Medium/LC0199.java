package Leetcode.Medium;

import Leetcode.TreeNode;

import java.util.*;

/**
 * 199. Binary Tree Right Side View
 * Given a binary tree, imagine yourself standing on the right side of it, return the values of the nodes you can see ordered from top to bottom.
 *
 * Example:
 *
 * Input: [1,2,3,null,5,null,4]
 * Output: [1, 3, 4]
 * Explanation:
 *
 *    1            <---
 *  /   \
 * 2     3         <---
 *  \     \
 *   5     4       <---
 */
public class LC0199 {
	/**
	 * Method1: BFS
	 * For each level, the first pop out node is the rightmost node in this level
	 * For each node, first expand its right child into the queue, then its left child
	 *
	 * Time = O(n)
	 * Space = O(n)
	 */
	public List<Integer> rightSideView(TreeNode root) {
		List<Integer> res = new ArrayList<>();
		if (root == null) return res;

		Queue<TreeNode> q = new LinkedList<>();
		q.offer(root);
		while (!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				TreeNode cur = q.poll();
				if (i == 0) {
					res.add(cur.val);
				}
				if (cur.right != null) {
					q.offer(cur.right);
				}
				if (cur.left != null) {
					q.offer(cur.left);
				}
			}
		}

		return res;
	}

	class Tuple {
		public TreeNode node;
		public int level;
		public Tuple(TreeNode node, int level) {
			this.node = node;
			this.level = level;
		}
	}

	/**
	 * Method2: DFS
	 *
	 * For each node, we need to record its value and level
	 * If this level is first seen in the result, which means level ==  res.size()
	 * We add this node into the result.
	 *
	 * Same as above, we first expand right child, then left child.
	 *
	 * Time = O(n)
	 * Space = O(n)
	 */
	public List<Integer> rightSideView2(TreeNode root) {
		List<Integer> res = new ArrayList<>();
		if (root == null) return res;

		Deque<Tuple> s = new LinkedList<>();
		s.offerFirst(new Tuple(root,0));
		while (!s.isEmpty()) {
			Tuple t = s.pollFirst();
			if (t.level == res.size()) {
				res.add(t.node.val);
			}

			if (t.node.left != null) {
				s.offerFirst(new Tuple(t.node.left, t.level + 1));
			}

			if (t.node.right != null) {
				s.offerFirst(new Tuple(t.node.right, t.level + 1));
			}
		}

		return res;
	}


	public static void main(String[] args) {
		LC0199 sol = new LC0199();
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.right = new TreeNode(5);
		root.right.right = new TreeNode(4);
		//root.right.left.left = new TreeNode(6);
		//root.right.left.right = new TreeNode(7);

		System.out.println(sol.rightSideView(root));
	}

}
