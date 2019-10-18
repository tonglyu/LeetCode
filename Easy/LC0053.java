package Leetcode.Easy;

/**
 * 53. Maximum Subarray
 * Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.
 *
 * Example:
 * Input: [-2,1,-3,4,-1,2,1,-5,4],
 * Output: 6
 * Explanation: [4,-1,2,1] has the largest sum = 6.
 *
 * Follow up:
 * If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach, which is more subtle.
 */
public class LC0053 {
	/**
	 * Solution: dp
	 * dp[i] represents the max sum of subarray including i
	 * if we can start from a new element here, we ignore former sum
	 * dp[i] = Math.max(dp[i - 1] + nums[i], nums[i])
	 *
	 * Time = O(n)
	 * Space = O(n) -> can be O(1)
	 */
	public int maxSubArray(int[] nums) {
		if (nums.length == 0) {
			return 0;
		}

		int[] dp = new int[nums.length + 1];
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < nums.length; i++) {
			dp[i + 1] = Math.max(nums[i], dp[i] + nums[i]);
			max = Math.max(max, dp[i + 1]);
		}

		return max;
	}
}
