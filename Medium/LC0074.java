package Leetcode.Medium;

/**
 * 74. Search a 2D Matrix
 * Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:
 * Integers in each row are sorted from left to right.
 * The first integer of each row is greater than the last integer of the previous row.
 *
 * Example 1:
 * Input:
 * matrix = [
 *   [1,   3,  5,  7],
 *   [10, 11, 16, 20],
 *   [23, 30, 34, 50]
 * ]
 * target = 3
 * Output: true
 *
 * Example 2:
 * Input:
 * matrix = [
 *   [1,   3,  5,  7],
 *   [10, 11, 16, 20],
 *   [23, 30, 34, 50]
 * ]
 * target = 13
 * Output: false
 */
public class LC0074 {
	/**
	 * Solution: binary search
	 * The matrix can be deserialized as 1-d array since the number is ordered
	 *
	 * Time = O(log(mn))
	 * Space = O(1)
	 */
	public boolean searchMatrix(int[][] matrix, int target) {
		if (matrix.length == 0 || matrix[0].length == 0) {
			return false;
		}

		int m = matrix.length;
		int n = matrix[0].length;

		int left = 0;
		int right = m * n - 1;
		while (left <= right) {
			int mid = left + (right - left) / 2;
			int row = mid / n;
			int col = mid % n;
			if (matrix[row][col] == target) {
				return true;
			} else if (matrix[row][col] < target) {
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}

		return false;
	}
}
