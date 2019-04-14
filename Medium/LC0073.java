package Leetcode.Medium;

/**
 * 73. Set Matrix Zeroes
 * Given a m x n matrix, if an element is 0, set its entire row and column to 0. Do it in-place.
 *
 * Example 1:
 * Input:
 * [
 *   [1,1,1],
 *   [1,0,1],
 *   [1,1,1]
 * ]
 * Output:
 * [
 *   [1,0,1],
 *   [0,0,0],
 *   [1,0,1]
 * ]
 *
 * Example 2:
 * Input:
 * [
 *   [0,1,2,0],
 *   [3,4,5,2],
 *   [1,3,1,5]
 * ]
 * Output:
 * [
 *   [0,0,0,0],
 *   [0,4,5,0],
 *   [0,3,1,0]
 * ]
 *
 * Follow up:
 * A straight forward solution using O(mn) space is probably a bad idea.
 * A simple improvement uses O(m + n) space, but still not the best solution.
 * Could you devise a constant space solution?
 */
public class LC0073 {
	/**
	 * We record whether a row or a column needs to be set as 0 by first element at each row and column.
	 * Step1: For each cell(i,j),
	 *      if cell(i,j) == 0: cell[i,0] =  0, cell[0,j] = 0
	 *      Meanwhile, if cell(i,0) == 0, which means the first col needs to be set as 0
	 * Step2: for each cell (i, j start from 1)
	 *      if cell[i,0] = 0 || cell[0,j] = 0 -> cell[i][j] = 0
	 * Step3: check if first row is 0
	 * Step4: check if first col is 0 (maintain a variable)
	 *
	 * Time = O(m * n)
	 * Space = O(1)
	 */
	public void setZeroes(int[][] matrix) {
		if (matrix.length == 0) {
			return;
		}

		boolean firstCol = false;
		int rows = matrix.length;
		int cols = matrix[0].length;
		for (int i = 0; i < rows; i++) {
			if (matrix[i][0] == 0) {
				firstCol = true;
			}
			for (int j = 1; j < cols; j++) {
				if (matrix[i][j] == 0) {
					matrix[i][0] = 0;
					matrix[0][j] = 0;
				}
			}
		}

		for (int i = 1; i < rows; i++) {
			for (int j = 1; j < cols; j++) {
				if (matrix[i][0] == 0 || matrix[0][j] == 0) {
					matrix[i][j] = 0;
				}
			}
		}

		if (matrix[0][0] == 0) {
			for (int i = 1; i < cols; i++) {
				matrix[0][i] = 0;
			}
		}

		if (firstCol) {
			for (int i = 0; i < rows; i++) {
				matrix[i][0] = 0;
			}
		}
	}

	public static void main(String[] args) {
		LC0073 sol = new LC0073();
		int[][] matrix = new int[][]{
				{0,1,2,0},
				{3,4,5,2},
				{1,3,1,5}
		};

		sol.setZeroes(matrix);
		for(int[] row: matrix) {
			for (Integer num : row) {
				System.out.print(num + " ");
			}
			System.out.println();
		}
	}
}
