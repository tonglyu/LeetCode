package Leetcode.Medium;

/**
 * 59. Spiral Matrix II
 * Given a positive integer n, generate a square matrix filled with elements from 1 to n2 in spiral order.
 *
 * Example:
 *
 * Input: 3
 * Output:
 * [
 *  [ 1, 2, 3 ],
 *  [ 8, 9, 4 ],
 *  [ 7, 6, 5 ]
 * ]
 */
public class LC0059 {
	/**
	 * Solution:
	 * Same as 54. Spiral Matrix, here we use iterative way
	 *
	 * Time = O(n)
	 * Space = O(1)
	 */
	public int[][] generateMatrix(int n) {
		if (n <= 0) {
			return new int[0][0];
		}

		int[][] res = new int[n][n];
		int num = 1;
		int r1 = 0, c1 = 0, r2 = n-1, c2 = n-1;
		while (r1 <= r2 && c1 <= c2) {
			for (int c = c1; c <= c2; c++) {
				res[r1][c] = num++;
			}

			for (int r = r1 + 1; r <= r2; r++) {
				res[r][c2] = num++;
			}

			if (r1 < r2) {
				for (int c = c2 - 1; c > c1; c--) {
					res[r2][c] = num++;
				}
			}

			if (c1 < c2) {
				for (int r = r2; r > r1; r--) {
					res[r][c1] = num++;
				}
			}

			r1++;
			c1++;
			r2--;
			c2--;
		}

		return res;
	}

	public static void main(String[] args) {
		LC0059 sol = new LC0059();
		int n = 5;

		int[][] res = sol.generateMatrix(n);
		for (int[] row : res) {
			for (int num : row) {
				System.out.print(num + "\t");
			}
			System.out.println();
		}
	}
}
