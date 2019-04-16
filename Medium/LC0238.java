package Leetcode.Medium;

/**
 * 238. Product of Array Except Self
 * Given an array nums of n integers where n > 1,  return an array output such that output[i] is equal to the product of all the elements of nums except nums[i].
 *
 * Example:
 * Input:  [1,2,3,4]
 * Output: [24,12,8,6]
 * Note: Please solve it without division and in O(n).
 *
 * Follow up:
 * Could you solve it with constant space complexity? (The output array does not count as extra space for the purpose of space complexity analysis.)
 */
public class LC0238 {
	/**
	 * DP: Similar to 42. Trapping Rain Water
	 *
	 * For each element, we need to record the product of its left elements,
	 * as well as the product of its right elements.
	 * prod_left[0] = 1, prod_left[i] = prod[i-1] * nums[i-1]
	 * prod_right[n-1] = 1, prod_right[i] = prod[i + 1] * nums[i + 1];
	 * res[i] = prod_left[i] * prod_right[i]
	 *
	 * we can use result array to record prod_left, and maintain a variable to record last prod_right
	 * Time = O(2n)
	 * Space = O(1)
	 */
	public int[] productExceptSelf(int[] nums) {
		if (nums.length <= 1) {
			return nums;
		}

		int[] res = new int[nums.length];
		res[0] = 1;
		for (int i = 1; i < nums.length; i++) {
			res[i] = res[i - 1] * nums[i - 1];
		}

		int prod = 1;
		for (int i = nums.length - 2; i >= 0; i--) {
			prod = prod * nums[i + 1];
			res[i] = res[i] * prod;
		}

		return res;
	}

	public static void main(String[] args) {
		LC0238 sol = new LC0238();
		int[] nums = new int[]{1, 2, 3, 6, 4};
		int[] res = sol.productExceptSelf(nums);
		for (Integer prod : res) {
			System.out.println(prod);
		}
	}
}
