package Leetcode.Medium;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * 81. Search in Rotated Sorted Array II
 * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
 * (i.e., [0,0,1,2,2,5,6] might become [2,5,6,0,0,1,2]).
 * You are given a target value to search. If found in the array return true, otherwise return false.
 *
 * Example 1:
 * Input: nums = [2,5,6,0,0,1,2], target = 0
 * Output: true
 *
 * Example 2:
 * Input: nums = [2,5,6,0,0,1,2], target = 3
 * Output: false
 *
 * Follow up:
 * This is a follow up problem to Search in Rotated Sorted Array, where nums may contain duplicates.
 * Would this affect the run-time complexity? How and why?
 */
public class LC0081 {
	/**
	 * Solution:
	 * Same as LC33, algorithm is same, difference is when we encounter same mid and left number
	 * we can only increase left boundary to skip one cell
	 *
	 * Time = O(logn) -> O(n) in worst case
	 * Space = O(1)
	 */
	public boolean search(int[] nums, int target) {
		int left = 0, right = nums.length - 1;
		while (left <= right) {
			int mid = left + (right - left) / 2;
			if  (nums[mid] == target) {
				return true;
			} else if (nums[left] < nums[mid]) {
				if (target >= nums[left] && target < nums[mid]) {
					right = mid - 1;
				} else {
					left = mid + 1;
				}
			} else if (nums[left] > nums[mid]) {
				if (target > nums[mid] && target <= nums[right]) {
					left = mid + 1;
				}  else {
					right = mid - 1;
				}
			} else {
				//duplicates, we know nums[mid] != target, so nums[start] != target
				//based on current information, we can only move left pointer to skip one cell
				//thus in the worst case, we would have target: 2, and array like 11111111, then
				//the running time would be O(n)
				left++;
			}
		}

		return false;
	}

	@Test
	public void test1() {
		int[] nums = new int[]{2,5,6,0,0,1,2};
		int target = 0;
		Assert.assertTrue(search(nums, target));
	}

	@Test
	public void test2() {
		int[] nums = new int[]{2,5,6,0,0,1,2};
		int target = 3;
		Assert.assertTrue(!search(nums, target));
	}

	@Test
	public void test3() {
		int[] nums = new int[]{1,3,1,1,1};
		int target = 3;
		Assert.assertTrue(search(nums, target));
	}

	public static void main(String[] args) {
		JUnitCore.main(LC0081.class.getName());
	}
}
