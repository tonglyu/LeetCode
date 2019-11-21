package Leetcode.Medium;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * 33. Search in Rotated Sorted Array
 * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
 * (i.e., [0,1,2,4,5,6,7] might become [4,5,6,7,0,1,2]).
 *
 * You are given a target value to search. If found in the array return its index, otherwise return -1.
 * You may assume no duplicate exists in the array.
 * Your algorithm's runtime complexity must be in the order of O(log n).
 *
 * Example 1:
 * Input: nums = [4,5,6,7,0,1,2], target = 0
 * Output: 4
 *
 * Example 2:
 * Input: nums = [4,5,6,7,0,1,2], target = 3
 * Output: -1
 */
public class LC0033 {
	/**
	 * Solution: one-pass binary search
	 * For each round:
	 *  1)take the middle and compare with target, if matches return.
	 *  2)if middle is bigger than left side, it means left is sorted:
	 *      2.1)  if [left] < target < [middle], we only need to find in the left side
	 *      2.2) otherwise, on the right side
	 *  3)if middle is smaller than left side, which means right side is sorted:
	 *      3.1)  if [mid] < target < [right], we only need to find in the right side
	 *      2.2) otherwise, on the left side
	 *
	 * Time = O(logn)
	 * Space = O(1)
	 */
	public int search(int[] nums, int target) {
		int left = 0, right = nums.length - 1;
		while (left <= right) {
			int mid = left + (right - left) / 2;
			if  (nums[mid] == target) {
				return mid;
			} else if (nums[left] <= nums[mid]) {
				if (target >= nums[left] && target < nums[mid]) {
					right = mid - 1;
				} else {
					left = mid + 1;
				}
			} else {
				if (target > nums[mid] && target <= nums[right]) {
					left = mid + 1;
				}  else {
					right = mid - 1;
				}
			}
		}

		return -1;
	}

	@Test
	public void test1() {
		int[] nums = new int[]{4,5,6,7,0,1,2};
		int target = 0;
		Assert.assertEquals(4, search(nums, target));
	}

	@Test
	public void test2() {
		int[] nums = new int[]{4,5,6,7,0,1,2};
		int target = 3;
		Assert.assertEquals(-1, search(nums, target));
	}

	public static void main(String[] args) {
		JUnitCore.main(LC0033.class.getName());
	}
}
