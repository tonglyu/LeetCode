package Leetcode.Medium;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * 31. Next Permutation
 * Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.
 * If such arrangement is not possible, it must rearrange it as the lowest possible order (ie, sorted in ascending order).
 * The replacement must be in-place and use only constant extra memory.
 *
 * Here are some examples. Inputs are in the left-hand column and its corresponding outputs are in the right-hand column.
 * 1,2,3 → 1,3,2
 * 3,2,1 → 1,2,3
 * 1,1,5 → 1,5,1
 */
public class LC0031 {
	/**
	 * Solution: find the longest descending suffix
	 * First, we observe that for any given sequence that is in descending order [3,2,1], no next larger permutation is possible.
	 * 1. Find the first pair (k, k+ 1), smallest index k such that nums[k] < nums[k + 1]. (No rearrangement for [k+1,n] can be larger, so we need to rearrange [k-1,n])
	 *      If no such index exists, just reverse nums and done
	 * 2. Replace the number a[k] with the number a[j] which is just larger (cannot equal) than a[k] among the numbers lying to its right section.
	 * 3. Swap a[k] and a[j], now we have the right number for kth position
	 * 4. Reverse a[k+1,n] to place those numbers in ascending order to get their smallest permutation.
	 *
	 * Time = O(3n) one pass for k, one pass for j, one pass for reverse
	 * Space = O(1)
	 */
	public void nextPermutation(int[] nums) {
		if (nums == null && nums.length <= 1) {
			return;
		}

		// pivot is the element just before the non-increasing (weakly decreasing) suffix
		int pivot = nums.length - 2;
		while (pivot >= 0 && nums[pivot] >= nums[pivot+1]) {
			pivot--;
		}


		if (pivot >= 0) {
			// Find rightmost first larger id greater
			int greater = pivot + 1;
			while (greater < nums.length && nums[greater] > nums[pivot]) {
				greater++;
			}

			swap(nums, pivot, greater - 1);
		}

		reverse(nums, pivot + 1, nums.length - 1);

	}

	private void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}

	private void reverse(int[] nums, int left, int right) {
		while (left < right) {
			swap(nums, left, right);
			left++;
			right--;
		}
	}

	@Test
	public void test1() {
		int[] nums = new int[]{1,5,1};
		nextPermutation(nums);
		Assert.assertArrayEquals(new int[]{5,1,1}, nums);
	}

	@Test
	public void test2() {
		int[] nums = new int[]{9, 5, 4, 3, 1};
		nextPermutation(nums);
		Assert.assertArrayEquals(new int[]{1,3,4,5,9}, nums);
	}

	@Test
	public void test3() {
		int[] nums = new int[]{1,3,5,4,7,5,4};
		nextPermutation(nums);
		Assert.assertArrayEquals(new int[]{1,3,5,5,4,4,7}, nums);
	}

	public static void main(String[] args) {
		JUnitCore.main(LC0031.class.getName());
	}
}
