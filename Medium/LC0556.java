package Leetcode.Medium;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * 556. Next Greater Element III
 * Given a positive 32-bit integer n, you need to find the smallest 32-bit integer which has exactly the same digits existing in the integer n and is greater in value than n. If no such positive 32-bit integer exists, you need to return -1.
 *
 * Example 1:
 * Input: 12
 * Output: 21
 *
 * Example 2:
 * Input: 21
 * Output: -1
 */
public class LC0556 {
	/**
	 * Solution: Same as 31. Next Permutation
	 * At first, lets look at the edge cases -
	 * If all digits sorted in descending order, then output is always “Not Possible”. For example, 4321.
	 * If all digits are sorted in ascending order, then we need to swap last two digits. For example, 1234.
	 * For other cases, we need to process the number from rightmost side (why? because we need to find the smallest of all greater numbers)
	 *
	 * Now the main algorithm works in following steps -
	 * I) Traverse the given number from rightmost digit, keep traversing till you find a digit which is smaller than the previously traversed digit.
	 * For example, if the input number is “534976”, we stop at 4 because 4 is smaller than next digit 9. If we do not find such a digit, then output is “Not Possible”.
	 *
	 * II) Now search the right side of above found digit ‘d’ for the smallest digit greater than ‘d’. For “534976″, the right side of 4 contains “976”. The smallest digit greater than 4 is 6.
	 *
	 * III) Swap the above found two digits, we get 536974 in above example.
	 *
	 * IV) Now sort all digits from position next to ‘d’ to the end of number. The number that we get after sorting is the output.
	 * For above example, we sort digits in bold 536974. We get “536479” which is the next greater number for input 534976.
	 *
	 * Time = O(n)
	 * Space = O(n)
	 */
	public int nextGreaterElement(int n) {
		char[] nums = String.valueOf(n).toCharArray();
		if (nums.length <= 1) {
			return -1;
		}

		int pivot = nums.length - 2;
		while (pivot >= 0 && nums[pivot] >= nums[pivot + 1]) {
			pivot--;
		}

		if (pivot < 0) {
			return -1;
		}

		int greater = pivot + 1;
		while (greater < nums.length && nums[greater] > nums[pivot]) {
			greater++;
		}

		swap(nums, pivot, greater - 1);
		reverse(nums, pivot + 1, nums.length - 1);

		long res = Long.valueOf(new String(nums));

		return res <= Integer.MAX_VALUE ? (int)res : -1;
	}

	private void swap(char[] nums, int i, int j) {
		char tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}

	private void reverse(char[] nums, int left, int right) {
		while (left < right) {
			swap(nums, left, right);
			left++;
			right--;
		}
	}

	@Test
	public void test1() {
		int n = 534976;
		Assert.assertEquals(536479, nextGreaterElement(n));
	}

	@Test
	public void test2() {
		int n = 1999999999;
		Assert.assertEquals(-1, nextGreaterElement(n));
	}

	public static void main(String[] args) {
		JUnitCore.main(LC0556.class.getName());

	}
}
