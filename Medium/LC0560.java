package Leetcode.Medium;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.util.HashMap;
import java.util.Map;

/**
 * 560. Subarray Sum Equals K
 * Given an array of integers and an integer k, you need to find the total number of continuous subarrays whose sum equals to k.
 *
 * Example 1:
 * Input:nums = [1,1,1], k = 2
 * Output: 2
 *
 * Note:
 * The length of the array is in range [1, 20,000].
 * The range of numbers in the array is [-1000, 1000] and the range of the integer k is [-1e7, 1e7].
 */
public class LC0560 {
	/**
	 * Solution1: Cumulative sum
	 * Subtract the cumulative sum corresponding to the two indices to obtain the sum directly.
	 * sum[i] represents the cumulative sum of nums up to the element corresponding to the i-1th index.
	 * sum(nums[i:j]) = sum[j + 1] - sum[i])
	 *
	 * Time = O(n + n^2)
	 * Space = O(n) can be improved by O(1) by resetteing sum = 0 inside first loop
	 */
	public int subarraySum(int[] nums, int k) {
		int[] sum = new int[nums.length + 1];
		for (int i = 0; i < nums.length; i++) {
			sum[i + 1] = nums[i] + sum[i];
		}

		int cnt = 0;
		for (int i = 0; i < nums.length; i++) {
			for (int j = i + 1; j <= nums.length; j++) {
				if (sum[j] - sum[i] == k) {
					cnt++;
				}
			}
		}

		return cnt;
	}

	/**
	 * Solution2: Map
	 * map(cumulative sum,  #occurrence)
	 * if sum[i]−sum[j]=k, the sum of elements lying between indices i and j is k.
	 *
	 * For every sum encountered, we also determine the number of times the sum[j] = sum - k has already occurred
	 * increment the count by the same amount
	 *
	 * Time = O(n)
	 * Space = O(n)
	 */
	public int subarraySum2(int[] nums, int k) {
		Map<Integer, Integer> map = new HashMap<>();
		//init we have original sum = 0
		map.put(0, 1);
		int cnt = 0, sum = 0;
		for (int i = 0; i < nums.length; i++) {
			sum += nums[i];
			int remain = sum - k;
			if (map.get(remain) != null) {
				cnt += map.get(remain);
			}
			map.put(sum, map.getOrDefault(sum, 0) + 1);
		}

		return cnt;
	}

	@Test
	public void test1() {
		int[] nums = new int[]{1,1,1};
		int k = 2;
		Assert.assertEquals(2, subarraySum(nums, k));
	}

	@Test
	public void test2() {
		int[] nums = new int[]{1,2,1,2,1};
		int k = 3;
		Assert.assertEquals(4, subarraySum2(nums, k));
	}

	@Test
	public void test3() {
		int[] nums = new int[]{3,4,7,2,-3,1,4,2,7};
		int k = 7;
		Assert.assertEquals(5, subarraySum2(nums, k));
	}

	public static void main(String[] args) {
		JUnitCore.main(LC0560.class.getName());
	}
}
