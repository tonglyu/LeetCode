package Leetcode.Medium;

/**
 * 240. Search a 2D Matrix II
 * Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:
 *
 * Integers in each row are sorted in ascending from left to right.
 * Integers in each column are sorted in ascending from top to bottom.
 * Example:
 *
 * Consider the following matrix:
 *
 * [
 *   [1,   4,  7, 11, 15],
 *   [2,   5,  8, 12, 19],
 *   [3,   6,  9, 16, 22],
 *   [10, 13, 14, 17, 24],
 *   [18, 21, 23, 26, 30]
 * ]
 * Given target = 5, return true.
 * Given target = 20, return false.
 */
public class LC0240 {
	/**
	 * Method1: Recursion
	 *
	 * Split the matrix into four parts, find center
	 * if target > center, target cannot be in the top left partition
	 * if target < center, target cannot be in the botton right partition
	 *
	 * Time = O(log_(4/3)(mn))
	 * Space = O(max(m,n))
	 */
	public boolean searchMatrix(int[][] matrix, int target) {
		if (matrix.length == 0 || matrix[0].length == 0) {
			return false;
		}

		int m = matrix.length;
		int n = matrix[0].length;

		return binarySearch(matrix, target, new int[]{0, 0}, new int[]{m - 1, n - 1});
	}

	private boolean binarySearch(int[][] matrix, int target, int[] upperLeft, int[] lowerRight) {
		int m = matrix.length;
		int n = matrix[0].length;

		if (upperLeft[0] > lowerRight[0] || upperLeft[1] > lowerRight[1]
				|| lowerRight[0] >= m || lowerRight[1] >= n) {
			return false;
		}

		if (upperLeft[0] == lowerRight[0] && upperLeft[1] == lowerRight[1])
			return matrix[upperLeft[0]][upperLeft[1]] == target;

		int r = (upperLeft[0] + lowerRight[0]) / 2;
		int c = (upperLeft[1] + lowerRight[1]) / 2;
		if (matrix[r][c] == target) {
			return true;
		} else if (matrix[r][c] > target) {
			return binarySearch(matrix, target, upperLeft, new int[]{r, c})
					|| binarySearch(matrix, target, new int[]{upperLeft[0], c + 1}, new int[]{r, lowerRight[1]})
					|| binarySearch(matrix, target, new int[]{r + 1, upperLeft[1]}, new int[]{lowerRight[0], c});
		} else {
			return binarySearch(matrix, target, new int[]{upperLeft[0], c + 1}, new int[]{r, lowerRight[1]})
					|| binarySearch(matrix, target, new int[]{r + 1, upperLeft[1]}, new int[]{lowerRight[0], c})
					|| binarySearch(matrix, target, new int[]{r + 1, c + 1}, lowerRight);
		}
	}

	/**
	 * Method2: Search Space Reduction
	 * The matrix is like a BST from top right
	 * Init: r = 0, c = n - 1
	 * Each time, we check the top right element in the matrix:
	 *  1) if ele = target, return true
	 *  2) if target < ele, c--, ele must be in the left part (because the elements in that col are larger than ele)
	 *  3) if target > ele, r++, ele must be in the upper part (because the elements in that row are smaller than ele)
	 *
	 *  Time = O(m + n)
	 *  Space = O(1)
	 */
	public boolean searchMatrix2(int[][] matrix, int target) {
		if (matrix.length == 0 || matrix[0].length == 0) {
			return false;
		}

		int m = matrix.length;
		int n = matrix[0].length;

		int r = 0, c = n - 1;
		while (r < m && c >= 0) {
			if (matrix[r][c] == target) {
				return true;
			} else if (target < matrix[r][c]) {
				c--;
			} else {
				r++;
			}
		}

		return false;
	}

	public static void main(String[] args) {
		LC0240 sol = new LC0240();
		int[][] board = new int[][]{
				{1, 2, 3, 4, 5},
				{6, 7, 8, 9, 10},
				{11, 12, 13, 14, 15},
				{16, 17, 18, 19, 20},
				{21, 22, 23, 24, 25}
		};

		int target = 5;
		System.out.println(sol.searchMatrix2(board, target));
	}
}
