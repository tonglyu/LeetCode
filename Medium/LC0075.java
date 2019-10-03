package Leetcode.Medium;

/**
 * 75. Sort Colors
 * Given an array with n objects colored red, white or blue, sort them in-place so that objects of the same color are adjacent, with the colors in the order red, white and blue.
 *
 * Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.
 * Note: You are not suppose to use the library's sort function for this problem.
 *
 * Example:
 * Input: [2,0,2,1,1,0]
 * Output: [0,0,1,1,2,2]
 *
 * Follow up:
 * A rather straight forward solution is a two-pass algorithm using counting sort.
 * First, iterate the array counting number of 0's, 1's, and 2's, then overwrite array with total number of 0's, then 1's and followed by 2's.
 * Could you come up with a one-pass algorithm using only constant space?
 */
public class LC0075 {
	/**
	 * Solution: Rainbow sort
	 * We need to maintain three pointers to track the rightmost boundary of numbers:
	 * i: all numbers left to i (not including i) are all 0's
	 * j: all numbers btw [i,j) are 1's, j is the current idx
	 * [j,k): are unexplored area
	 * k: all numbers right to k (not including k) are all 2's
	 *
	 * Case1: a[j] == 0, swap(a,i,j),i++,j++ => only when a[i]=a[j]=0 or a[i]=1,a[j]=0 would enter this choice
	 * Case2: a[j] == 1, j++
	 * Case3: a[j] == 2, swap(a,i,j),k-- => j cannot increase cuz we did not check the number after swap
	 *
	 * Time = O(n)
	 * Space = O(1)
	 */
	public void sortColors(int[] nums) {
		if (nums.length == 0) {
			return;
		}

		int i = 0, j = 0, k = nums.length - 1;
		// cannot be j < k, otherwise we don't check a[k] before we jump out of the loop
		while (j <= k) {
			if (nums[j] == 0) {
				swap(nums, i, j);
				i++;
				j++;
			} else if (nums[j] == 1) {
				j++;
			} else {
				swap(nums,j, k);
				k--;
			}
		}

	}

	private void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}

	public static void main(String[] args) {
		LC0075 sol = new LC0075();
		int[] nums = new int[]{2,0,2,0,1,2,0};
		sol.sortColors(nums);

		for (int n:nums) {
			System.out.println(n);
		}
	}
}
