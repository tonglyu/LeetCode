package Leetcode.Easy;

import Leetcode.Node;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 559. Maximum Depth of N-ary Tree
 * Given a n-ary tree, find its maximum depth.
 *
 * The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
 *
 * For example, given a 3-ary tree:
 *              1
 *       /      |      \
 *     3        2       4
 *  /   \
 *5     6
 *
 * We should return its max depth, which is 3.
 *
 * Note:
 * The depth of the tree is at most 1000.
 * The total number of nodes is at most 5000.
 */
public class LC0559 {
	/**
	 * Solution1: recursion
	 * Totally the height of tree
	 * We can from height of each child, find the max
	 * return max + 1 to my parent
	 *
	 * Time = O(n)
	 * Space = O(height)
	 */
	public int maxDepth(Node root) {
		if (root == null) {
			return 0;
		}

		return helper(root);
	}

	private int helper(Node root) {
		if (root == null) {
			return 0;
		}

		int maxDepthChild = 0;
		for (Node n: root.children) {
			int depth = helper(n);
			maxDepthChild = Math.max(maxDepthChild, depth);
		}
		return maxDepthChild + 1;
	}

	/**
	 * Solution2: BFS
	 * Level by level traversal, for each level, increase depth by 1
	 *
	 * Time = O(n)
	 * Space = O(n)
	 */
	public int maxDepth2(Node root) {
		if (root == null) {
			return 0;
		}

		int depth = 0;
		Queue<Node> q = new LinkedList<>();
		q.offer(root);

		while (!q.isEmpty()) {
			int size = q.size();
			depth++;
			for (int i = 0; i < size; i++) {
				Node cur = q.poll();
				if (cur.children != null) {
					for (Node n : cur.children) {
						q.offer(n);
					}
				}
			}

		}

		return depth;
	}
}
