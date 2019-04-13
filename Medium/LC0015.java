package Leetcode.Medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 15. 3Sum
 * Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.
 *
 * Note:
 * The solution set must not contain duplicate triplets.
 *
 * Example:
 * Given array nums = [-1, 0, 1, 2, -1, -4],
 *
 * A solution set is:
 * [
 *   [-1, 0, 1],
 *   [-1, -1, 2]
 * ]
 */
public class LC0015 {
	/**
	 *  Data structure: three pointers
	 *  Step1: sort the array
	 *  Step2: for each arr[i], find two sum(arr[i+1,len-1], target-arr[i])
	 *      1) arr[i] + arr[left] + arr[right] = target -> add to res
	 *                  then remove duplicate (one side)
	 *      2) sum < target -> left++
	 *      3) sum > target -> right--
	 *
	 *  Time = O(n^2)
	 *  Space = O(1)
	 */
	public List<List<Integer>> threeSum(int[] nums) {
		//Assumptions: array is not null, array.length >= 3.
		List<List<Integer>> res = new ArrayList<>();
		Arrays.sort(nums);

		for (int i = 0; i < nums.length - 2; i++) {
			// Our goal is to find i < j < k, such that
			// arr[i] + arr[j] + arr[k]  == target
			// To make sure there is no duplicate tuple,
			// We ignore all the duplicate possible i.
			// e.g. if we have 2,2,2, only the first 2 will be selected as i.
			if (i > 0 && nums[i] == nums[i-1]) {
				continue;
			}

			int left = i + 1;
			int right = nums.length - 1;
			while (left < right) {
				int sum = nums[i] + nums[left] + nums[right];
				if (sum == 0) {
					res.add(Arrays.asList(nums[i],nums[left],nums[right]));
					// ignore all possible duplicate as i
					while (left < right && nums[left] == nums[left + 1]) {
						left++;
					}

					// We can ignore this part cause we already handle with one side,
					// -1,-1,0,1,1,1,2
					//  i    j     k
					// next time the sum will larger than target, and right-- automatically runs.
					// while (left < right && nums[right] == nums[right - 1]) {
					//	right--;
					//}
					left++;
					right--;
				} else if (sum < 0) {
					left++;
				} else {
					right--;
				}
			}
		}
		return res;
	}

	public static void main(String[] args) {
		LC0015 sol = new LC0015();
		int[] nums = new int[]{-1, 0, 1, 2, -1, -4,2,1,1};
		List<List<Integer>> res = sol.threeSum(nums);
		for (List<Integer> triple: res) {
			for (Integer num: triple) {
				System.out.print(num + " ");
			}
			System.out.println();
		}
	}
}
