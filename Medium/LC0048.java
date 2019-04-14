package Leetcode.Medium;

/**
 * 48. Rotate Image
 * You are given an n x n 2D matrix representing an image.
 *
 * Rotate the image by 90 degrees (clockwise).
 *
 * Note:
 * You have to rotate the image in-place, which means you have to modify the input 2D matrix directly. DO NOT allocate another 2D matrix and do the rotation.
 *
 * Example 1:
 * Given input matrix =
 * [
 *   [1,2,3],
 *   [4,5,6],
 *   [7,8,9]
 * ],
 *
 * rotate the input matrix in-place such that it becomes:
 * [
 *   [7,4,1],
 *   [8,5,2],
 *   [9,6,3]
 * ]
 *
 * Example 2:
 * Given input matrix =
 * [
 *   [ 5, 1, 9,11],
 *   [ 2, 4, 8,10],
 *   [13, 3, 6, 7],
 *   [15,14,12,16]
 * ],
 *
 * rotate the input matrix in-place such that it becomes:
 * [
 *   [15,13, 2, 5],
 *   [14, 3, 4, 1],
 *   [12, 6, 8, 9],
 *   [16, 7,10,11]
 * ]
 */
public class LC0048 {
	/**
	 * Split matrix into 4 parts, rotate for each loop
	 * 4 coordinates: (i,j),(n-1-j,i),(n-1-i,n-1-j),(j,n-1-i)
	 * Only needs to loop half of the matrix:
	 *  row = (0, n - 2)
	 *  col = (row, n - 2 - row) from leftmost corner to second to last index
	 *
	 * Time = O(n * n)
	 * Space = O(1)
	 */
	public void rotate(int[][] matrix) {
		int n = matrix.length;
		if (n <= 1) {
			return;
		}

		for (int row = 0; row < n / 2; row++) {
			for (int col = row; col <= n - 2 - row; col++) {
				int tmp = matrix[row][col];
				matrix[row][col] = matrix[n - 1 - col][row];
				matrix[n - 1 - col][row] = matrix[n - 1 - row][n - 1 - col];
				matrix[n - 1 - row][n - 1 - col] = matrix[col][n - 1 - row];
				matrix[col][n - 1 - row] = tmp;
			}
		}
	}

	/**
	 * Method2:
	 * 1. Mirror the point according to y axis, then
	 * 2. Mirror the point according to line of y = x
	 */

	public static void main(String[] args) {
		LC0048 sol = new LC0048();
		int[][] matrix = new int[][]{
		{1,2,3},
		{4,5,6},
		{7,8,9}
		};

		sol.rotate(matrix);
		for(int[] row: matrix) {
			for (Integer num : row) {
				System.out.print(num + " ");
			}
			System.out.println();
		}
	}
}
