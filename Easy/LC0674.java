package Leetcode.Easy;

/**
 * 674. Longest Continuous Increasing Subsequence
 * Given an unsorted array of integers, find the length of longest continuous increasing subsequence (subarray).
 *
 * Example 1:
 * Input: [1,3,5,4,7]
 * Output: 3
 * Explanation: The longest continuous increasing subsequence is [1,3,5], its length is 3.
 * Even though [1,3,5,7] is also an increasing subsequence, it's not a continuous one where 5 and 7 are separated by 4.
 * Example 2:
 * Input: [2,2,2,2,2]
 * Output: 1
 * Explanation: The longest continuous increasing subsequence is [2], its length is 1.
 * Note: Length of the array will not exceed 10,000.
 */
public class LC0674 {
	/**
	 * Data structure: dp array
	 * Init: dp[0] = 1;
	 * dp[i] = dp[i - 1] + 1    if nums[i] > nums[i-1]
	 *          1               if nums[i] <= nums[i-1]
	 *
	 *  Time = O(n)
	 *  Space = O(n)
	 */
	public int findLengthOfLCIS(int[] nums) {
		if (nums == null || nums.length == 0)   return 0;
		int[] dp = new int[nums.length];
		int max = 1;
		dp[0] = 1;
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] > nums[i - 1]) {
				dp[i] = dp[i - 1] + 1;
				max = Math.max(max, dp[i]);
			} else {
				dp[i] = 1;
			}
		}
		return max;
	}

	// Time = O(n), Space = O(1)
	public int findLengthOfLCISSpace(int[] nums) {
		if (nums == null || nums.length == 0)   return 0;
		int count = 1;
		int max = 1;
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] > nums[i - 1]) {
				count++;
				max = Math.max(max, count);
			} else  {
				count = 1;
			}
		}
		return max;
	}

	public static void main(String[] args) {
		LC0674 sol = new LC0674();
		int[] nums = new int[]{2,2,2,2,2};
		System.out.println(sol.findLengthOfLCISSpace(nums));
	}
}
