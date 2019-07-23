package Leetcode.Medium;

import java.util.HashMap;
import java.util.Map;

/**
 * 666. Path Sum IV
 * If the depth of a tree is smaller than 5, then this tree can be represented by a list of three-digits integers.
 *
 * For each integer in this list:
 * The hundreds digit represents the depth D of this node, 1 <= D <= 4.
 * The tens digit represents the position P of this node in the level it belongs to, 1 <= P <= 8. The position is the same as that in a full binary tree.
 * The units digit represents the value V of this node, 0 <= V <= 9.
 *
 * Given a list of ascending three-digits integers representing a binary tree with the depth smaller than 5, you need to return the sum of all paths from the root towards the leaves.
 *
 * Example 1:
 * Input: [113, 215, 221]
 * Output: 12
 * Explanation:
 * The tree that the list represents is:
 *     3
 *    / \
 *   5   1
 *
 * The path sum is (3 + 5) + (3 + 1) = 12.
 *
 *
 * Example 2:
 * Input: [113, 221]
 * Output: 4
 * Explanation:
 * The tree that the list represents is:
 *     3
 *      \
 *       1
 *
 * The path sum is (3 + 1) = 4.
 */
public class LC0666 {
	public int total = 0;
	Map<Integer, Integer> tree = new HashMap<>();

	/**
	 * Solution1: implemented by my first draft
	 * Use the array to store tree node
	 * row = nums[i] / 100, col = nums[i] / 10 % 10
	 * idx = 2 ^ (row - 1) - 1 + col - 1
	 */
	public int pathSum(int[] nums) {
		Integer[] trees = new Integer[15];
		for (int i = 0; i < nums.length; i++) {
			int row = nums[i] / 100;
			int col = nums[i] / 10 % 10;
			int val = nums[i] % 10;
			int idx = (int)Math.pow(2, row - 1) + col - 2;
			trees[idx] = val;
		}

		getSum(trees, 0, 0);
		return total;
	}

	private void getSum(Integer[] tree, int pos, int sum) {
		if (pos >= tree.length || tree[pos] == null) {
			return;
		}
		int left = pos * 2 + 1;
		int right = pos * 2 + 2;
		if ((left >= tree.length || tree[left] == null) && (right >= tree.length || tree[right] == null)) {
			sum += tree[pos];
			total += sum;
			return;
		}

		sum += tree[pos];
		getSum(tree, left, sum);
		getSum(tree, right, sum);
	}

	/**
	 * Solution2: Use HashMap to represent tree
	 * Store the nums[i] / 10 as the key, nums[i] / 10 = 10 * depth + pos
	 * left child = 10 * (depth + 1) + 2 * pos - 1
	 * right child = 10 * (depth + 1) + 2 * pos
	 *
	 * DFS: pass the prefix sum to the leaf
	 * Base case: if map doesn't contain the node, then return
	 * Current level: sum += node.val
	 * Recursive rule: getSum(left, prefixSum)
	 *
	 * Time = O(n)
	 * Space = O(n) -- for map
	 */
	public int pathSum2(int[] nums) {
		for (int i = 0; i < nums.length; i++) {
			int node = nums[i] / 10;
			int val = nums[i] % 10;
			tree.put(node, val);
		}

		getSum(nums[0] / 10, 0);
		return total;
	}

	private void getSum(int node, int sum) {
		if (!tree.containsKey(node)) {
			return;
		}
		int depth = node / 10;
		int pos = node % 10;
		int left = 10 * (depth + 1) + 2 * pos - 1;
		int right = 10 * (depth + 1) + 2 * pos;
		if (!tree.containsKey(left) && !tree.containsKey(right)) {
			sum += tree.get(node);
			total += sum;
			return;
		}

		sum += tree.get(node);
		getSum(left, sum);
		getSum(right, sum);
	}

	public static void main(String[] args) {
		LC0666 sol = new LC0666();
		int[] nums = new int[]{110,218,315,326,423,433};
		System.out.println(sol.pathSum2(nums));
	}
}
