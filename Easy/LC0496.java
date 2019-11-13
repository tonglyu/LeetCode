package Leetcode.Easy;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.util.*;

/**
 * 496. Next Greater Element I
 * You are given two arrays (without duplicates) nums1 and nums2 where nums1’s elements are subset of nums2.
 * Find all the next greater numbers for nums1's elements in the corresponding places of nums2.
 * The Next Greater Number of a number x in nums1 is the first greater number to its right in nums2.
 * If it does not exist, output -1 for this number.
 *
 * Example 1:
 * Input: nums1 = [4,1,2], nums2 = [1,3,4,2].
 * Output: [-1,3,-1]
 * Explanation:
 *     For number 4 in the first array, you cannot find the next greater number for it in the second array, so output -1.
 *     For number 1 in the first array, the next greater number for it in the second array is 3.
 *     For number 2 in the first array, there is no next greater number for it in the second array, so output -1.
 *
 * Example 2:
 * Input: nums1 = [2,4], nums2 = [1,2,3,4].
 * Output: [3,-1]
 * Explanation:
 *     For number 2 in the first array, the next greater number for it in the second array is 3.
 *     For number 4 in the first array, there is no next greater number for it in the second array, so output -1.
 *
 * Note:
 * All elements in nums1 and nums2 are unique.
 * The length of both nums1 and nums2 would not exceed 1000.
 */
public class LC0496 {
	/**
	 * Solution1: stack
	 * Stack is a monotonically decreasing stack, after each process, stores all elements >= cur element
	 * Scan from right to left, for each element:
	 *  Pop out all elements smaller than current. Then, the top element that is larger than cur is the ans for cur.
	 *  Push cur into stack
	 *
	 * Time = O(m + n)
	 * Space = O(m + n)
	 */
	public int[] nextGreaterElement(int[] nums1, int[] nums2) {
		Map<Integer, Integer> map = new HashMap<>();
		Deque<Integer> s = new LinkedList<>();
		for (int i = nums2.length - 1; i >= 0; i--) {
			while (!s.isEmpty() && s.peek() <= nums2[i]) {
				s.pop();
			}
			map.put(nums2[i], s.isEmpty() ? -1 : s.peek());
			s.push(nums2[i]);
		}

		int[] res = new int[nums1.length];
		for (int i = 0; i < nums1.length; i++) {
			res[i] = map.get(nums1[i]);
		}
		return res;
	}

	/**
	 * Solution2: stack
	 * Stack is a monotonically decreasing stack, whenever we see a number x greater than stack.peek() we pop all elements less than x and for all the popped ones, their next greater element is x
	 * Scan from left to right, for each element:
	 *      Pop out all elements smaller than current. These elements next greater element is cur
	 *      Push cur into stack
	 *
	 * Time = O(m + n)
	 * Space = O(m + n)
	 */
	public int[] nextGreaterElement2(int[] nums1, int[] nums2) {
		Map<Integer, Integer> map = new HashMap<>();
		Deque<Integer> s = new LinkedList<>();

		for (int i = 0; i < nums2.length; i++) {
			while (!s.isEmpty() && s.peek() < nums2[i]) {
				map.put(s.pop(), nums2[i]);
			}
			s.push(nums2[i]);
		}

		int[] res = new int[nums1.length];
		for (int i = 0; i < nums1.length; i++) {
			res[i] = map.getOrDefault(nums1[i], -1);
		}
		return res;
	}

	@Test
	public void test1() {
		int[] nums1 = new int[]{4,1,2}, nums2 = new int[]{1,3,4,2};
		Assert.assertArrayEquals(new int[]{-1, 3, -1}, nextGreaterElement2(nums1,nums2));
	}

	@Test
	public void test2() {
		int[] nums1 = new int[]{2,4}, nums2 = new int[]{1,2,3,4};
		Assert.assertArrayEquals(new int[]{3, -1}, nextGreaterElement2(nums1,nums2));
	}

	public static void main(String[] args) {
		JUnitCore.main(LC0496.class.getName());
	}
}
