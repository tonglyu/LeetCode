package Leetcode.Easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 118. Pascal's Triangle
 * Given a non-negative integer numRows, generate the first numRows of Pascal's triangle.
 *
 * In Pascal's triangle, each number is the sum of the two numbers directly above it.
 *
 * Example:
 * Input: 5
 * Output:
 * [
 *      [1],
 *     [1,1],
 *    [1,2,1],
 *   [1,3,3,1],
 *  [1,4,6,4,1]
 * ]
 */
public class LC0118 {
	/**
	 * Each row generate a new list
	 *     1) add 1 for first element
	 *     2) list(r,c) = list(r-1,c) + list(r-1,c-1)
	 *     3) add 1 at the end
	 *
	 * Time = O(1 + 2 + 3 + ... + n) = O(n^2)
	 * Space = O(1)
	 */
	public List<List<Integer>> generate(int numRows) {
		List<List<Integer>> res = new ArrayList<>();
		if (numRows == 0) return res;

		res.add(new ArrayList<>(Arrays.asList(1)));
		for (int r = 1; r < numRows; r++) {
			res.add(new ArrayList<>(Arrays.asList(1)));
			for (int c = 1; c < r; c++) {
				res.get(r).add(res.get(r - 1).get(c) + res.get(r - 1).get(c - 1));
			}
			res.get(r).add(1);
		}

		return res;
	}

	public static void main(String[] args) {
		LC0118 sol = new LC0118();
		System.out.println(sol.generate(5));
	}
}
