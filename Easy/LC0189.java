package Leetcode.Easy;

/**
 * 189. Rotate Array
 * Given an array, rotate the array to the right by k steps, where k is non-negative.
 *
 * Example 1:
 * Input: [1,2,3,4,5,6,7] and k = 3
 * Output: [5,6,7,1,2,3,4]
 * Explanation:
 * rotate 1 steps to the right: [7,1,2,3,4,5,6]
 * rotate 2 steps to the right: [6,7,1,2,3,4,5]
 * rotate 3 steps to the right: [5,6,7,1,2,3,4]
 *
 * Example 2:
 * Input: [-1,-100,3,99] and k = 2
 * Output: [3,99,-1,-100]
 * Explanation:
 * rotate 1 steps to the right: [99,-1,-100,3]
 * rotate 2 steps to the right: [3,99,-1,-100]
 *
 * Note:
 * Try to come up as many solutions as you can, there are at least 3 different ways to solve this problem.
 * Could you do it in-place with O(1) extra space?
 */
public class LC0189 {
	/**
	 * "I love yahoo" variable
	 * Split the nums into 2 parts
	 * 1 2 3 4     5 6 7
	 * 1) reverse each part
	 * 4 3 2 1    7 6 5
	 * 2) reverse entire array
	 * 5 6 7 1 2 3 4
	 *
	 * Time = O(2n)
	 * Space = O(1)
	 */
	public void rotate(int[] nums, int k) {
		int n = nums.length;
		if (n == 0) return;

		if (k >= n) k = k % n;
		reverse(nums, 0, n - 1);
		reverse(nums, 0, k - 1);
		reverse(nums, k, n - 1);
	}

	private void reverse(int[] nums, int left, int right) {
		while (left < right) {
			int tmp = nums[left];
			nums[left] = nums[right];
			nums[right] = tmp;
			left++;
			right--;
		}
	}

	public static void main(String[] args) {
		LC0189 sol = new LC0189();
		int[] nums = new int[]{-1,-100,3,99};
		sol.rotate(nums, 2);
		for (Integer num:nums) {
			System.out.println(num);
		}
	}
}
