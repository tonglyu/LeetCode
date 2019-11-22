package Leetcode.Hard;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * 45. Jump Game II
 * Given an array of non-negative integers, you are initially positioned at the first index of the array.
 * Each element in the array represents your maximum jump length at that position.
 * Your goal is to reach the last index in the minimum number of jumps.
 *
 * Example:
 * Input: [2,3,1,1,4]
 * Output: 2
 * Explanation: The minimum number of jumps to reach the last index is 2.
 *     Jump 1 step from index 0 to 1, then 3 steps to the last index.
 *
 * Note:
 * You can assume that you can always reach the last index.
 */
public class LC0045 {
	/**
	 * Solution1: DP
	 * dp[i] represents whether the minimum steps jump from i to end point
	 * init: dp[end] = 0
	 * ans: dp[0]
	 * transition rule: dp[i] = min(dp[j] + 1) i < j <= i + A[i] && dp[j] is not INF
	 *
	 * Time = O(n^2)
	 * Space = O(n)
	 */
	public int jump(int[] nums) {
		int[] dp = new int[nums.length];
		for (int i = nums.length - 2; i >= 0; i--) {
			dp[i] = Integer.MAX_VALUE;
			for (int j = i + 1; j <= i + nums[i] && j < nums.length; j++) {
				//if j cannot reach to the end point, then it is INF
				if (dp[j] == Integer.MAX_VALUE) continue;
				dp[i] = Math.min(dp[i], dp[j] + 1);
			}
		}
		return dp[0];
	}

	/**
	 * Solution: Greedy/ BFS
	 * The problem can be treat as a BFS
	 * level0: 0(2)
	 * level1: 1(3), 2(1)
	 * level2: 3(1), 4(4)
	 *
	 * The range of the current jump is [curBegin, curMaxIdx], nextMaxIdx is the farthest point that all points in [curBegin, curMaxIdx] can reach.
	 * Once the current point reaches curMaxIdx, then trigger another jump, and set the new curMaxIdx with nextMaxIdx, then keep the above steps.
	 *
	 * Time = O(n)
	 * Space = O(1)
	 */
	public int jump2(int[] nums) {
		int levels = 0, curMaxIdx = 0, nextMaxIdx = 0;
		for (int i = 0; i < nums.length - 1; i++) {
			//traverse current level , and update the max reach of next level
			nextMaxIdx = Math.max(nextMaxIdx, i + nums[i]);
			//if we meet the last index of current level, we should increase levels and update next level's max idx
			if (i == curMaxIdx) {
				levels++;
				curMaxIdx = nextMaxIdx;
			}
		}
		return levels;
	}

	@Test
	public void test1() {
		int[] nums = new int[]{2,3,1,1,4};
		Assert.assertEquals(2, jump(nums));
	}

	@Test
	public void test2() {
		int[] nums = new int[]{2,3,0,1,4};
		Assert.assertEquals(2, jump(nums));
	}

	@Test
	public void test3() {
		int[] nums = new int[]{0};
		Assert.assertEquals(0, jump2(nums));
	}

	@Test
	public void test4() {
		int[] nums = new int[]{1};
		Assert.assertEquals(0, jump2(nums));
	}

	public static void main(String[] args) {
		JUnitCore.main(LC0045.class.getName());
	}
}
