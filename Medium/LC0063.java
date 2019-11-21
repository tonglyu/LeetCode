package Leetcode.Medium;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * 63. Unique Paths II
 * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
 * The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).
 * Now consider if some obstacles are added to the grids. How many unique paths would there be?
 *
 * An obstacle and empty space is marked as 1 and 0 respectively in the grid.
 * Note: m and n will be at most 100.
 *
 * Example 1:
 * Input:
 * [
 *   [0,0,0],
 *   [0,1,0],
 *   [0,0,0]
 * ]
 * Output: 2
 * Explanation:
 * There is one obstacle in the middle of the 3x3 grid above.
 * There are two ways to reach the bottom-right corner:
 * 1. Right -> Right -> Down -> Down
 * 2. Down -> Down -> Right -> Right
 */
public class LC0063 {
	/**
	 * Solution: DP
	 * First check if original or destination is an obstacle, return 0
	 * dp[i][j] represent the unique path from [0,0] to [i,j]
	 * init: dp[0][0] = 1
	 * ans: dp[m-1][n-1]
	 * transition rule:
	 *      dp[i][j] = 0, if [i][j] is an obstacle
	 *      dp[i][j] = dp[i - 1][j] + dp[i][j - 1]
	 *
	 * Time = O(m * n)
	 * Space = O(m * n)
	 */
	public int uniquePathsWithObstacles(int[][] obstacleGrid) {
		int m = obstacleGrid.length;
		int n = obstacleGrid[0].length;
		if (obstacleGrid[0][0] == 1 || obstacleGrid[m-1][n-1] == 1) {
			return 0;
		}
		int[][] dp = new int[m][n];
		dp[0][0] = 1;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if ((i == 0 && j == 0) || obstacleGrid[i][j] == 1) continue;
				int up = i > 0 ? dp[i - 1][j] : 0;
				int left = j > 0 ? dp[i][j - 1] : 0;
				dp[i][j] = up + left;
			}
		}

		return dp[m - 1][n - 1];
	}

	/**
	 * Solution: DP
	 * Same as above, except we use obstacle as dp grid
	 *
	 * Time = O(m * n)
	 * Space = O(1)
	 */
	public int uniquePathsWithObstacles2(int[][] obstacleGrid) {
		int m = obstacleGrid.length;
		int n = obstacleGrid[0].length;
		if (obstacleGrid[0][0] == 1 || obstacleGrid[m-1][n-1] == 1) {
			return 0;
		}

		obstacleGrid[0][0] = 1;
		for (int i = 1; i < m; i++) {
			if (obstacleGrid[i][0] == 1) {
				obstacleGrid[i][0] = 0;
			} else {
				obstacleGrid[i][0] = obstacleGrid[i - 1][0];
			}
		}

		for (int i = 1; i < n; i++) {
			if (obstacleGrid[0][i] == 1) {
				obstacleGrid[0][i] = 0;
			} else {
				obstacleGrid[0][i] = obstacleGrid[0][i - 1];
			}
		}

		for (int i = 1; i < m; i++) {
			for (int j = 1; j < n; j++) {
				if (obstacleGrid[i][j] == 1) {
					obstacleGrid[i][j] = 0;
				} else {
					obstacleGrid[i][j] = obstacleGrid[i - 1][j] + obstacleGrid[i][j - 1];
				}
			}
		}

		return obstacleGrid[m - 1][n - 1];
	}

	@Test
	public void test1() {
		int[][] obstacleGrid = new int[][]{
				{0,0},
				{1,1},
				{0,0}
		};
		Assert.assertEquals(0, uniquePathsWithObstacles(obstacleGrid));
	}

	@Test
	public void test2() {
		int[][] obstacleGrid = new int[][]{
				{1,0}
		};
		Assert.assertEquals(0, uniquePathsWithObstacles(obstacleGrid));
	}

	@Test
	public void test3() {
		int[][] obstacleGrid = new int[][]{
				{0,0,0},
				{0,1,0},
				{0,0,0}
		};
		Assert.assertEquals(2, uniquePathsWithObstacles2(obstacleGrid));
	}

	public static void main(String[] args) {
		JUnitCore.main(LC0063.class.getName());
	}
}
