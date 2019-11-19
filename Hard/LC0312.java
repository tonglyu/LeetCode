package Leetcode.Hard;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * 312. Burst Balloons
 * Given n balloons, indexed from 0 to n-1. Each balloon is painted with a number on it represented by array nums. You are asked to burst all the balloons. If the you burst balloon i you will get nums[left] * nums[i] * nums[right] coins.
 * Here left and right are adjacent indices of i. After the burst, the left and right then becomes adjacent.
 * Find the maximum coins you can collect by bursting the balloons wisely.
 *
 * Note:
 * You may imagine nums[-1] = nums[n] = 1. They are not real therefore you can not burst them.
 * 0 ≤ n ≤ 500, 0 ≤ nums[i] ≤ 100
 *
 * Example:
 * Input: [3,1,5,8]
 * Output: 167
 * Explanation: nums = [3,1,5,8] --> [3,5,8] -->   [3,8]   -->  [8]  --> []
 *              coins =  3*1*5      +  3*5*8    +  1*3*8      + 1*8*1   = 167
 */
public class LC0312 {
	/**
	 * Solution1: DP
	 * Balloons 0, 1, ..., n - 1, What is the max value if we burst all of them [0, n - 1]?
	 * Let's first consider bursting [start, end]
	 * Imagine we burst index k at the end.[i, ... k - 1, (k), k + 1 ... j]
	 *      Before the end, we already burst [i, k - 1] and [k + 1, j]
	 * Before the end, boundaries i - 1, k, j + 1 are always there (现在这个分治递归区间里,只存在三个元素即start-1,i,end+1,因为在[start-1,i-1],[i+1,end+1]这两个区间已经提前被扎爆了)
	 * This helps us calculate coins without worrying about details inside [i, k - 1] and [k + 1, j]
	 * So the range can be divided into: i - 1, maxCoin[i, k - 1], i, maxCoins[k + 1, j], j+1
	 *
	 * dp[i][j] represents the max coin start from ith balloon to jth balloon
	 * dp[i][j] = max(dp[i][k-1] + dp[k+1][j] + nums[i-1]*nums[k]*nums[j+1])
	 *
	 * Time = O(n^3)
	 * Space = O(n^2)
	 */
	public int maxCoins(int[] nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		int len = nums.length;
		int[][] dp = new int[len][len];
		for (int l = 1; l <= len; l++) {
			for (int i = 0; i <= len - l; i++) {
				int  j = i + l - 1;
				for (int k = i; k <= j; k++) {
					int coin = nums[k] * getVal(nums, i-1) * getVal(nums,j+1);
					coin += (k == i) ? 0 : dp[i][k-1];// If not first one, we can add subrange on its left.
					coin += (k == j) ? 0 : dp[k+1][j];// If not last one, we can add subrange on its right
					dp[i][j] = Math.max(dp[i][j],  coin);
				}
			}
		}

		return dp[0][len - 1];
	}

	private int getVal(int[] nums, int i) {
		if (i < 0 || i >= nums.length) {
			return 1;
		}
		return nums[i];
	}

	@Test
	public void test1() {
		int[] nums = new int[]{3,1,5,8};
		Assert.assertEquals(167, maxCoins(nums));
	}

	public static void main(String[] args) {
		JUnitCore.main(LC0312.class.getName());
	}
}

