package Leetcode.Medium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 78. Subsets
 * Given a set of distinct integers, nums, return all possible subsets (the power set).
 *
 * Note: The solution set must not contain duplicate subsets.
 * Example:
 *
 * Input: nums = [1,2,3]
 * Output:
 * [
 *   [3],
 *   [1],
 *   [2],
 *   [1,2,3],
 *   [1,3],
 *   [2,3],
 *   [1,2],
 *   []
 * ]
 */
public class LC0078 {
	/**
	 * DFS
	 * 1) What does each level represent? : nums.length, each level decides whether contains current element in subset
	 * 2) How many different states should we try to put on this level? 2, add this element or not
	 *
	 * Time = O(2^n)
	 * Space = O(n)
	 */
	public List<List<Integer>> subsets(int[] nums) {
		List<List<Integer>> res = new ArrayList<>();
		if (nums.length == 0) {
			return res;
		}

		List<Integer> sub = new ArrayList<>();
		dfs(nums, 0, res, sub);
		Collections.sort(res, new Comparator<List<Integer>>() {
			@Override
			public int compare(List<Integer> o1, List<Integer> o2) {
				if (o1.size()!= o2.size()) {
					return o1.size() - o2.size();
				}
				for (int i = 0; i < o1.size(); i++) {
					if (o1.get(i) != o2.get(i)) {
						return o1.get(i) - o2.get(i);
					}
				}
				return 0;
			}
		});
		return res;
	}

	private void dfs(int[] nums, int index, List<List<Integer>> res, List<Integer> sub) {
		if (index == nums.length) {
			// we cannot add sub, only works when creating a new list
			res.add(new ArrayList<>(sub));
			return;
		}

		dfs(nums, index + 1, res, sub);
		sub.add(nums[index]);
		dfs(nums, index + 1, res, sub);
		sub.remove(sub.size() - 1);
	}

	public static void main(String[] args) {
		LC0078 sol = new LC0078();
		int[] nums = new int[]{6,5,9};
		List<List<Integer>> res = sol.subsets(nums);
		System.out.println(res);
	}
}
