package Leetcode.Easy;

import Leetcode.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * 437. Path Sum III
 * You are given a binary tree in which each node contains an integer value.
 * Find the number of paths that sum to a given value.
 * The path does not need to start or end at the root or a leaf, but it must go downwards (traveling only from parent nodes to child nodes).
 * The tree has no more than 1,000 nodes and the values are in the range -1,000,000 to 1,000,000.
 *
 * Example:
 * root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8
 *
 *       10
 *      /  \
 *     5   -3
 *    / \    \
 *   3   2   11
 *  / \   \
 * 3  -2   1
 *
 * Return 3. The paths that sum to 8 are:
 *
 * 1.  5 -> 3
 * 2.  5 -> 2 -> 1
 * 3. -3 -> 11
 */
public class LC0437 {
	/**
	 * Solution1: DFS
	 * for each node:
	 *  1) Find the path which starts from itself who meets the requirement -> refer to Path Sum II
	 *  2) Find the # path in the left & right subtree
	 *
	 * Time = O(n + 2 * n / 2 + 4 * n / 4 + ...) < O(n^2)
	 * Avg Time = O(nlogn)
	 * Space = O(height)
	 */
	public int pathSum(TreeNode root, int sum) {
		if (root == null)
			return 0;
		int curSum = helper(root, sum);
		int leftSum = pathSum(root.left, sum);
		int rightSum = pathSum(root.right, sum);
		return curSum + leftSum + rightSum;
	}

	private int helper(TreeNode root, int sum) {
		if (root == null) {
			return 0;
		}

		int curCount = 0;
		if (sum == root.val) {
			curCount++;
		}

		int leftCount = helper(root.left, sum - root.val);
		int rightCount = helper(root.right, sum - root.val);
		return curCount + leftCount + rightCount;
	}

	/**
	 * Solution2: DFS+HashMap
	 * Use a hashmap to store the (prefix_sum, #amount)
	 * Only need to go through each node once,
	 *  1) get the number of paths start from that node and end with previous nodes -> target sum = curPrefixSum - prevPrefixSum
	 *  2) update curPrefixSum in map -> (curPrefixSum, ++count)
	 *  3) Get the #paths from children
	 */
	public int pathSum2(TreeNode root, int sum) {
		if (root == null) {
			return 0;
		}
		Map<Integer, Integer> map = new HashMap<>();
		map.put(0, 1);
		return helper(root, 0, sum, map);
	}

	private int helper(TreeNode root, int prefixSum, int sum, Map<Integer, Integer> map) {
		if (root == null) {
			return 0;
		}
		// update the prefix sum by adding the current val
		prefixSum += root.val;
		// get the number of valid path, ended by the current node
		int numPathToCurr = map.getOrDefault(prefixSum - sum, 0);

		// update the map with the current sum, so the map is good to be passed to the next recursion
		map.put(prefixSum, map.getOrDefault(prefixSum, 0) + 1);

		// add the 3 parts discussed in 8. together
		int leftPath = helper(root.left, prefixSum, sum, map);
		int rightPath = helper(root.right, prefixSum, sum, map);

		// restore the map, as the recursion goes from the bottom to the top
		map.put(prefixSum, map.get(prefixSum) - 1);

		return numPathToCurr + leftPath + rightPath;
	}

	public static void main(String[] args) {
		LC0437 sol = new LC0437();
		TreeNode root = new TreeNode(10);
		root.left = new TreeNode(5);
		root.left.left = new TreeNode(3);
		root.left.left.left = new TreeNode(3);
		root.left.left.right = new TreeNode(-2);
		root.left.right = new TreeNode(2);
		root.left.right.right = new TreeNode(1);
		root.right = new TreeNode(-3);
		root.right.right = new TreeNode(11);

		int sum = 8;
		System.out.println(sol.pathSum2(root, sum));
	}
}
