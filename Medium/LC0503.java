package Leetcode.Medium;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 503. Next Greater Element II
 * Given a circular array (the next element of the last element is the first element of the array), print the Next Greater Number for every element.
 * The Next Greater Number of a number x is the first greater number to its traversing-order next in the array, which means you could search circularly to find its next greater number. If it doesn't exist, output -1 for this number.
 *
 * Example 1:
 * Input: [1,2,1]
 * Output: [2,-1,2]
 * Explanation: The first 1's next greater number is 2;
 * The number 2 can't find next greater number;
 * The second 1's next greater number needs to search circularly, which is also 2.
 *
 * Note: The length of given array won't exceed 10000.
 */
public class LC0503 {
	/**
	 * Solution: stack
	 * This stack stores the indices of the appropriate elements from nums array. The top of the stack refers to the index of the Next Greater Element found so far.
	 * We store the indices instead of the elements since there could be duplicates in array.
	 *
	 * We go through two such passes over the complete nums array. This is done so as to complete a circular traversal over the array.
	 * The first pass could make some wrong entries in the res array since it considers only the elements lying to the right of nums[i], without a circular traversal.
	 * But, these entries are corrected in the second pass.
	 *
	 * Time = O(2n)
	 * Space = O(n)
	 */
	public int[] nextGreaterElements(int[] nums) {
		int[] res = new int[nums.length];

		//store the indices instead of num itself
		Deque<Integer> s = new LinkedList<>();
		for (int i = nums.length * 2 - 1; i >= 0; i--) {
			int num = nums[i % nums.length];
			while (!s.isEmpty() && nums[s.peek()] <= num) {
				s.pop();
			}
			res[i % nums.length] = s.isEmpty() ? -1 : nums[s.peek()] ;
			s.push(i % nums.length);
		}

		return res;
	}

	@Test
	public void test1() {
		int[] nums = new int[]{1,2,2,3,1};
		Assert.assertArrayEquals(new int[]{2,3,3,-1,2}, nextGreaterElements(nums));
	}

	@Test
	public void test2() {
		int[] nums = new int[]{1,8,-1,-100,-1,222,1111111,-111111};
		Assert.assertArrayEquals(new int[]{8,222,222,-1,222,1111111,-1,1}, nextGreaterElements(nums));
	}

	public static void main(String[] args) {
		JUnitCore.main(LC0503.class.getName());
	}
}
