package Leetcode.Easy;

/**
 * 167. Two Sum II - Input array is sorted
 * Given an array of integers that is already sorted in ascending order, find two numbers such that they add up to a specific target number.
 * The function twoSum should return indices of the two numbers such that they add up to the target, where index1 must be less than index2.
 *
 * Note:
 * Your returned answers (both index1 and index2) are not zero-based.
 * You may assume that each input would have exactly one solution and you may not use the same element twice.
 *
 * Example:
 * Input: numbers = [2,7,11,15], target = 9
 * Output: [1,2]
 * Explanation: The sum of 2 and 7 is 9. Therefore index1 = 1, index2 = 2.
 */
public class LC0167 {
	/**
	 * Two pointers
	 *
	 * Time = O(n)
	 * Space = O(1)
	 */
	public int[] twoSum(int[] numbers, int target) {
		if (numbers.length == 0) {
			return new int[2];
		}

		int[] res = new int[2];
		int left  = 0;
		int right = numbers.length - 1;
		while (left < right) {
			int sum = numbers[left] + numbers[right];
			if ( sum ==  target) {
				res[0] = left + 1;
				res[1] = right + 1;
				return res;
			} else if (sum < target) {
				left++;
			} else {
				right--;
			}
		}

		return res;
	}

	public static void main(String[] args) {
		LC0167 sol = new LC0167();
		int[] nums = new int[]{1,3,4,4,5,7,9,10};
		int target = 9;
		int[] res = sol.twoSum(nums,target);
		for (Integer idx: res) {
			System.out.println(idx);
		}
	}
}
