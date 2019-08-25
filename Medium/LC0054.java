package Leetcode.Medium;

import java.util.ArrayList;
import java.util.List;

/**
 * 54. Spiral Matrix
 * Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral order.
 *
 * Example 1:
 * Input:
 * [
 *  [ 1, 2, 3 ],
 *  [ 4, 5, 6 ],
 *  [ 7, 8, 9 ]
 * ]
 * Output: [1,2,3,6,9,8,7,4,5]
 *
 * Example 2:
 * Input:
 * [
 *   [1, 2, 3, 4],
 *   [5, 6, 7, 8],
 *   [9,10,11,12]
 * ]
 * Output: [1,2,3,4,8,12,11,10,9,5,6,7]
 */
public class LC0054 {
	/**
	 * Solution: recursion
	 * sub-problem: print the most outer round
	 * recursive rule: go through top left and  bottom right
	 * base case: the top left > bottom right, no element can be accessed
	 *
	 * Time = O(rows * cols)
	 * Space = O(min(log(rows), log(cols))  # of call stacks = how many rounds
	 */
	public List<Integer> spiralOrder(int[][] matrix) {
		List<Integer> res = new ArrayList<>();
		if (matrix.length == 0 || matrix[0].length == 0) {
			return res;
		}

		int rows = matrix.length;
		int cols = matrix[0].length;

		helper(matrix,0,0,rows - 1, cols - 1, res);
		return res;
	}


	private void helper(int[][] matrix, int r1, int c1, int r2, int c2, List<Integer> res) {
		// base case
		if (r1 > r2 || c1 > c2) {
			return;
		}

		// top: c from c1 to c2
		for (int j = c1; j <= c2; j++) {
			res.add(matrix[r1][j]);
		}

		// right: r from r1 + 1 to r2
		for (int i= r1 + 1; i <= r2; i++) {
			res.add(matrix[i][c2]);
		}

		// when r1 != r2, bottom: c from c2 -1 to c1 - 1
		if (r1 < r2) {
			for (int j = c2 - 1; j > c1; j--) {
				res.add(matrix[r2][j]);
			}
		}

		// when c1 != c2, left: r from r2 to r1 + 1
		if (c1 < c2) {
			for (int i = r2; i > r1; i--) {
				res.add(matrix[i][c1]);
			}
		}

		helper(matrix, r1 + 1, c1 + 1, r2 -1, c2 - 1, res);
	}

	public static void main(String[] args) {
		LC0054 sol = new LC0054();
		int[][] matrix = new int[][]{
				{1,2,3,4},
				{5,6,7,8},
				{9,10,11,12}
		};

		System.out.println(sol.spiralOrder(matrix));
	}
}
