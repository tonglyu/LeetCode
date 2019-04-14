package Leetcode.Easy;

import java.util.ArrayList;
import java.util.List;

/**
 * 119. Pascal's Triangle II
 * Given a non-negative index k where k ≤ 33, return the kth index row of the Pascal's triangle.
 *
 * Note that the row index starts from 0.
 * In Pascal's triangle, each number is the sum of the two numbers directly above it.
 *
 * Example:
 * Input: 3
 * Output: [1,3,3,1]
 * Follow up:
 *
 * Could you optimize your algorithm to use only O(k) extra space?
 */
public class LC0119 {
	/**
	 * For each row:
	 *  1) Add 1 into the list, to expand the size = row + 1
	 *  2) traverse col from row - 1 to 1:
	 *         list.set(col, list(col) + list(col - 1))
	 *
	 * Time = O(n)
	 * Space = O(1)
	 */
	public List<Integer> getRow(int rowIndex) {
		List<Integer> res = new ArrayList<>();
		res.add(1);
		for (int r = 1; r <= rowIndex; r++) {
			res.add(1);
			// We need to update list from right to left, otherwise the previous number will be updated earlier.
			for (int c = r - 1; c > 0; c--) {
				res.set(c, res.get(c - 1) + res.get(c));
			}
		}

		return res;
	}

	public static void main(String[] args) {
		LC0119 sol = new LC0119();
		int[] rows = new int[]{0,1,2,3,4,5};
		for (Integer row :rows) {
			List<Integer> res = sol.getRow(row);
			System.out.println(res);
		}
	}
}
