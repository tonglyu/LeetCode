package Leetcode.Medium;

/**
 * 152. Maximum Product Subarray
 * Given an integer array nums, find the contiguous subarray within an array (containing at least one number) which has the largest product.
 *
 * Example 1:
 * Input: [2,3,-2,4]
 * Output: 6
 * Explanation: [2,3] has the largest product 6.
 *
 * Example 2:
 * Input: [-2,0,-1]
 * Output: 0
 * Explanation: The result cannot be 2, because [-2,-1] is not a subarray.
 */
public class LC0152 {
	/**
	 * DP: we need to maintain the max product and min product at the same time.
	 * because there might to negative numbers.
	 *
	 * max[i]: represent the max product up to ith element
	 * min[i]: represent the min product up to ith element
	 * max[i] = max(nums[i], max[i-1] * nums[i], min[i-1]*nums[i])
	 * min[i] = min(nums[i], max[i-1] * nums[i], min[i-1]*nums[i])
	 * res = max(max, res)
	 *
	 * Time = O(n)
	 * Space = O(1) we only need to maintain two previous product
	 */
	public int maxProduct(int[] nums) {
		if (nums.length == 0) {
			return 0;
		}

		int max = nums[0];
		int min = nums[0];
		int res = nums[0];
		for (int i = 1; i < nums.length; i++) {
			// Calculate the product in advance, otherwise max will be different when calculating min.
			int prod1 = nums[i] * max;
			int prod2 = nums[i] * min;
			max = Math.max(nums[i], Math.max(prod1, prod2));
			min = Math.min(nums[i], Math.min(prod1, prod2));
			res = Math.max(max, res);
		}

		return res;
	}

	public static void main(String[] args) {
		LC0152 sol = new LC0152();
		int[] nums = new int[]{-4, -2, 3, -6, 2};
		int max = sol.maxProduct(nums);

		System.out.println(max);

	}
}
