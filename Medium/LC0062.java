package Leetcode.Medium;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.util.Arrays;

/**
 * 62. Unique Paths
 * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
 * The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).
 * How many possible unique paths are there?
 *
 * Above is a 7 x 3 grid. How many possible unique paths are there?
 * Note: m and n will be at most 100.
 * Example 1:
 * Input: m = 3, n = 2
 * Output: 3
 * Explanation:
 * From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
 * 1. Right -> Right -> Down
 * 2. Right -> Down -> Right
 * 3. Down -> Right -> Right
 *
 * Example 2:
 * Input: m = 7, n = 3
 * Output: 28
 */
public class LC0062 {
	/**
	 * Solution: DP
	 * dp[i][j] represents the unique path from [0,0] to [i,j]
	 * init: for the first row and first column, there would only one path to reach the cell
	 * ans: dp[m-1][n-1]
	 * transition rule: dp[i][j] = dp[i-1][j] + dp[i][j-1]
	 *
	 * Time = O(m*n)
	 * Space = O(m*n)
	 */
	public int uniquePaths(int m, int n) {
		int[][] dp = new int[m][n];

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (i == 0 || j == 0) {
					dp[i][j] = 1;
				} else {
					dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
				}
			}
		}

		return dp[m - 1][n - 1];
	}


	/** Solution: DP
	 * Same as before, use 1-D array to save space
	 *
	 * Time = O(m*n)
	 * Space = O(min(m,n))
	 */
	public int uniquePaths2(int m, int n) {
		int min = Math.min(m,n);
		int max = Math.max(m,n);
		int[] dp = new int[min];
		Arrays.fill(dp, 1);


		for (int i = 1; i < max; i++) {
			for (int j = 1; j < min; j++) {
				int pre = dp[j];
				dp[j] = dp[j - 1] + pre;
			}
		}

		return dp[min - 1];
	}

	@Test
	public void test1() {
		int m = 3, n = 2;
		Assert.assertEquals(3, uniquePaths(m,n));
	}

	@Test
	public void test2() {
		int m = 7, n = 3;
		Assert.assertEquals(28, uniquePaths2(m,n));
	}

	public static void main(String[] args) {
		JUnitCore.main(LC0062.class.getName());
	}
}
