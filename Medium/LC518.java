package Leetcode.Medium;

import java.util.Arrays;

/**
 * 518. Coin Change 2
 * You are given coins of different denominations and a total amount of money. Write a function to compute the number of combinations that make up that amount.
 * You may assume that you have infinite number of each kind of coin.
 *
 * Example 1:
 * Input: amount = 5, coins = [1, 2, 5]
 * Output: 4
 * Explanation: there are four ways to make up the amount:
 * 5=5
 * 5=2+2+1
 * 5=2+1+1+1
 * 5=1+1+1+1+1
 *
 * Example 2:
 * Input: amount = 3, coins = [2]
 * Output: 0
 * Explanation: the amount of 3 cannot be made up just with coins of 2.
 *
 * Example 3:
 * Input: amount = 10, coins = [10]
 * Output: 1
 *
 * Note:
 * You can assume that
 * 0 <= amount <= 5000
 * 1 <= coin <= 5000
 * the number of coins is less than 500
 * the answer is guaranteed to fit into signed 32-bit integer
 */
public class LC518 {
	/**
	 * DFS:
	 * 1. how many levels = number of coins
	 * 2. how many branches: it depends on the max number of coins consist by remain
	 * 3. base case: use all kinds of coins, check if remain can be divided by the last coin
	 *
	 * Time = O(n * number of coins)
	 * Space = O(n * number of coins)
	 */
	public int change(int amount, int[] coins) {
		if (amount == 0) return 1;
		if (coins.length == 0) return 0;

		int[][] matrix = new int[amount + 1][coins.length];
		for (int i = 0; i < matrix.length; i++) {
			Arrays.fill(matrix[i],-1);
		}
		int count = dfs(amount, coins, 0, matrix);
		return count;
	}

	private int dfs(int remain, int[] coins, int index, int[][] matrix) {
		if (index == coins.length - 1) {
			if (remain % coins[index] == 0) {
				return 1;
			}
			return 0;
		}

		if (matrix[remain][index] != -1) {
			return matrix[remain][index];
		}

		int count = 0;
		int max = remain / coins[index];
		for (int i = 0; i <= max; i++) {
			count += dfs(remain - coins[index] * i, coins, index + 1, matrix);
		}
		return count;
	}

	/**
	 * dp[i][j] : the number of combinations to make up amount j by using the first i types of coins
	 * State transition:
	 *
	 * not using the ith coin, only using the first i-1 coins to make up amount j, then we have dp[i-1][j] ways.
	 * using the ith coin, since we can use unlimited same coin, we need to know how many ways to make up amount j - coins[i-1] by using first i coins(including ith),
	 * which is dp[i][j-coins[i-1]]
	 * Initialization: dp[i][0] = 1
	 */
	public int change2(int amount, int[] coins) {
		int[][] dp = new int[coins.length + 1][amount+1];
		dp[0][0] = 1;

		for (int i = 1; i <= coins.length; i++) {
			dp[i][0] = 1;
			for (int j = 1; j <= amount; j++) {
				dp[i][j] = dp[i-1][j] + (j >= coins[i-1] ? dp[i][j-coins[i-1]] : 0);
			}
		}
		return dp[coins.length][amount];
	}


	public static void main(String[] args) {
		LC518 sol = new LC518();
		int amount = 500;
		int[] coins = new int[]{3,5,7,8,9,10,11};
		System.out.println(sol.change2(amount,coins));
	}
}
