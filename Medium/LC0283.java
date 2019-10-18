package Leetcode.Medium;

/**
 * 283. Move Zeroes
 * Given an array nums, write a function to move all 0's to the end of it while maintaining the relative order of the non-zero elements.
 *
 * Example:
 * Input: [0,1,0,3,12]
 * Output: [1,3,12,0,0]
 *
 * Note:
 * You must do this in-place without making a copy of the array.
 * Minimize the total number of operations.
 */
public class LC0283 {
	/**
	 * Solution: two pointers
	 * i: all elements on the left of i are all non-zeros (not including i)
	 * j: current idx
	 *
	 * For each arr[j]:
	 * Case1: arr[j] is not 0, swap elements at i and j, i++, j++
	 * Case2: arr[j] is 0, keep moving forward -> j++
	 *
	 * Time = O(n)
	 * Space = O(1)
	 */
	public void moveZeroes(int[] nums) {
		if (nums.length ==  0) {
			return;
		}

		int i = 0, j = 0;
		while (j < nums.length) {
			if (nums[j] != 0) {
				swap(nums,i,j);
				i++;
			}
			j++;
		}
	}

	private void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
}
