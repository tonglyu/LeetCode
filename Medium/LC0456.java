package Leetcode.Medium;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 456. 132 Pattern
 * Given a sequence of n integers a1, a2, ..., an, a 132 pattern is a subsequence ai, aj, ak such that i < j < k and ai < ak < aj.
 * Design an algorithm that takes a list of n numbers as input and checks whether there is a 132 pattern in the list.
 * Note: n will be less than 15,000.
 *
 * Example 1:
 * Input: [1, 2, 3, 4]
 * Output: False
 * Explanation: There is no 132 pattern in the sequence.
 *
 * Example 2:
 * Input: [3, 1, 4, 2]
 * Output: True
 * Explanation: There is a 132 pattern in the sequence: [1, 4, 2].
 *
 * Example 3:
 * Input: [-1, 3, 2, 0]
 * Output: True
 * Explanation: There are three 132 patterns in the sequence: [-1, 3, 2], [-1, 3, 0] and [-1, 2, 0].
 */
public class LC0456 {
	/**
	 * Solution1: brute force
	 * Use 3 for loop to traverse the index, consider every triplet (i,j,k)
	 * and check if the corresponding numbers satisfy the 132 criteria.
	 * <p>
	 * Time = O(n^3)
	 * Space = O(1)
	 */
	public boolean find132pattern(int[] nums) {
		int n = nums.length;
		if (n < 3) return false;

		for (int i = 0; i <= n - 3; i++) {
			for (int j = i + 1; j < n; j++) {
				for (int k = j + 1; k < n; k++) {
					if (nums[i] < nums[k] && nums[k] < nums[j]) {
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * Solution2: improve brute force
	 * Assume we have jth element fixed, if we can also fixed i, we are up to find the third number nums[k] which will be within the range (nums[i], nums[j])
	 * The larger the range is,  the more likely there will be a number "falling into" it. => which means we can minimizing the lower bound nums[i].
	 *
	 * Thus, when we traversing the array, we can maintain a variable to record the current minimum value as nums[i]
	 * And do another for loop to find nums[k]
	 *
	 * Time = O(n^2)
	 * Space = O(1)
	 */
	public boolean find132pattern2(int[] nums) {
		int n = nums.length;
		if (n < 3) return false;

		//min represents the i-th element
		int min = Integer.MAX_VALUE;
		for (int j = 0; j < n - 1; j++) {
			min = Math.min(min, nums[j]);
			//if min != nums[j], means nums[i] < nums[j]
			if (min == nums[j]) continue;
			for (int k = n - 1; k > j; k--) {
				if (nums[j] > nums[k] && min < nums[k]) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Solution3: stack
	 * Scan from right to left, use a monotonically decreasing stack, the elements in stack are all elements >= nums[j]
	 * Each time, we pop out from the stack to find the biggest element that is smaller than nums[j], which is most possible > min[j]
	 * Case1: second > min[j], return true
	 * Case2: scan continously, assume cur = [0,j-1], min[t] >= min[j]
	 *      Case2.1: nums[t] > nums[j], the stack still keeps all elements which is >= nums[j], it is still possible to have second > min[t]
	 *      Case2.2: nums[t] < nums[j], if j is not valid, which means there is no val in range (min[j], nums[j]) in the right side
	 *              as for t, min[t] >= min[j], the range (min[t], nums[t]) is smaller than previous range, it is not possible to have a value in this range then.
	 *
	 * Time = O(n)
	 * Space = O(n)
	 */
	public boolean find132pattern3(int[] nums) {
		int n = nums.length;
		if (n < 3) return false;

		int[] min = new int[nums.length];
		min[0] = Integer.MAX_VALUE;
		// current min from [0,i)
		for (int i = 1; i < n; i++) {
			min[i] = Math.min(min[i - 1], nums[i - 1]);
		}

		Deque<Integer> stack = new LinkedList<>();
		//min represents the i-th element
		for (int j = n - 1; j >= 0; j--) {
			int second = Integer.MIN_VALUE;
			while (!stack.isEmpty() && stack.peek() < nums[j]) {
				second = stack.pop();
			}
			if (min[j] < nums[j] && min[j] < second) {
				return true;
			}
			stack.push(nums[j]);
		}

		return false;
	}

	@Test
	public void test1() {
		int[] nums = new int[]{1,2,3,4};
		Assert.assertEquals(false, find132pattern(nums));
	}

	@Test
	public void test2() {
		int[] nums = new int[]{3,1,4,2};
		Assert.assertEquals(true, find132pattern2(nums));
	}


	@Test
	public void test3() {
		int[] nums = new int[]{-1, 3, 2, 0};
		Assert.assertEquals(true, find132pattern3(nums));
	}

	@Test
	public void test4() {
		int[] nums = new int[]{3,5,0,3,4};
		Assert.assertEquals(true, find132pattern3(nums));
	}

	public static void main(String[] args) {
		JUnitCore.main(LC0456.class.getName());
	}
}