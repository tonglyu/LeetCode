package Leetcode.Medium;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * 55. Jump Game
 * Given an array of non-negative integers, you are initially positioned at the first index of the array.
 * Each element in the array represents your maximum jump length at that position.
 * Determine if you are able to reach the last index.
 *
 * Example 1:
 * Input: [2,3,1,1,4]
 * Output: true
 * Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
 *
 * Example 2:
 * Input: [3,2,1,0,4]
 * Output: false
 * Explanation: You will always arrive at index 3 no matter what. Its maximum
 *              jump length is 0, which makes it impossible to reach the last index.
 */
public class LC0055 {
	/**
	 * Solution1: DP
	 * dp[i] represents whether I can jump from i to end point
	 * init: dp[end] = true
	 * ans: dp[0]
	 * transition rule: dp[i] = true if exist i < j <= i + A[i] && dp[j] = true
	 *
	 * Time = O(n^2)
	 * Space = O(n)
	 */
	public boolean canJump(int[] nums) {
		boolean[] dp = new boolean[nums.length];
		dp[nums.length - 1] = true;
		for (int i = nums.length - 2; i >= 0; i--) {
			for (int j = i + 1; j <= i + nums[i] && j < nums.length; j++) {
				if (dp[j]) {
					dp[i] = true;
					break;
				}
			}
		}
		return dp[0];
	}

	/**
	 * Solution2: Greedy
	 * We need to maintain a variable to record the current longest index we can reach
	 * for each current idx, if (idx > max), which means we cannot reach idx, return false
	 *
	 * Finally, if max >= last index, we can reach the end point
	 *
	 * Time = O(n)
	 * Space = O(1)
	 */
	public boolean canJump2(int[] nums) {
		int curLongestIndex = 0;
		for (int i = 0; i < nums.length; i++) {
			if (i > curLongestIndex) return false;
			curLongestIndex = Math.max(curLongestIndex, i + nums[i]);
		}

		return curLongestIndex >= nums.length - 1;
	}

	@Test
	public void test1() {
		int[] nums = new int[]{2,3,1,1,4};
		Assert.assertTrue(canJump(nums));
	}

	@Test
	public void test2() {
		int[] nums = new int[]{3,2,1,0,4};
		Assert.assertTrue(!canJump2(nums));
	}

	public static void main(String[] args) {
		JUnitCore.main(LC0055.class.getName());
	}
}
