package Leetcode.Hard;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 239. Sliding Window Maximum
 * Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position. Return the max sliding window.
 *
 * Example:
 *
 * Input: nums = [1,3,-1,-3,5,3,6,7], and k = 3
 * Output: [3,3,5,5,6,7]
 * Explanation:
 *
 * Window position                Max
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       3
 *  1 [3  -1  -3] 5  3  6  7       3
 *  1  3 [-1  -3  5] 3  6  7       5
 *  1  3  -1 [-3  5  3] 6  7       5
 *  1  3  -1  -3 [5  3  6] 7       6
 *  1  3  -1  -3  5 [3  6  7]      7
 *
 * Note:
 * You may assume k is always valid, 1 ≤ k ≤ input array's size for non-empty array.
 * Follow up:
 * Could you solve it in linear time?
 */
public class LC0239 {
	/**
	 * Method1: brute force
	 * Time = O(n * k)
	 * Space = O(1)
	 */
	public int[] maxSlidingWindow1(int[] nums, int k) {
		if (nums.length == 0) return new int[0];
		int[] res = new int[nums.length - k + 1];

		for (int i = 0; i <= nums.length - k; i++) {
			int max = Integer.MIN_VALUE;
			for (int j = i; j < i + k; j++) {
				max = Math.max(max, nums[j]);
			}
			res[i] = max;
		}
		return res;
	}

	/**
	 * Method2: monotonic queue
	 * Deque: record the indices of elements
	 * 1) remove the indices smaller than left side from head of the queue
	 * 2) ***remove the indices whose elements are smaller than current element x:
	 *      because when x comes in, the smaller element cannot be the max any more
	 * 3) add x to the tail of the queue
	 * 4) if i is larger or equal to than window size - 1, update res[i-(k-1)] with head to the queue
	 *
	 * Time = O(n)
	 * Space = O(n)
	 */
	public int[] maxSlidingWindow2(int[] nums, int k) {
		if (k == 0) return nums;

		//# of sliding window = n - ( k -1 )
		int[] res = new int[nums.length - k + 1];
		Deque<Integer> q = new LinkedList<>();

		for (int i = 0; i < nums.length; ++i) {
			// remove left boundary
			while (!q.isEmpty() && i - k + 1 > q.peekFirst()) {
				q.pollFirst();
			}

			// remove element which is smaller
			while (!q.isEmpty() && nums[q.peekLast()] < nums[i]) {
				q.pollLast();
			}

			q.offerLast(i);

			// i - (k - 1) is head index
			if (i - k + 1 >= 0) {
				res[i-k + 1] = nums[q.peekFirst()];
			}
		}

		return res;
	}

	/**
	 * Method3: dp
	 * 最后一步没看懂
	 * Time = O(n)
	 * Space = O(n)
	 */
	public int[] maxSlidingWindow3(int[] nums, int k) {
		int[] max_left = new int[nums.length];
		int[] max_right = new int[nums.length];

		max_left[0] = nums[0];
		max_right[nums.length - 1] = nums[nums.length - 1];

		for (int i = 1; i < nums.length; i++) {
			max_left[i] = (i % k == 0) ? nums[i] : Math.max(max_left[i - 1], nums[i]);

			int j = nums.length - i - 1;
			max_right[j] = (j % k == 0) ? nums[j] : Math.max(max_right[j + 1], nums[j]);
		}

		int[] sliding_max = new int[nums.length - k + 1];
		for (int i = 0, j = 0; i + k <= nums.length; i++) {
			sliding_max[j++] = Math.max(max_right[i], max_left[i + k - 1]);
		}

		return sliding_max;
	}

	public static void main(String[] args) {
		LC0239 sol = new LC0239();
		int[] nums = new int[]{1,3,-1,-3,5,3,6,7};
		int k = 3;
		int[] res = sol.maxSlidingWindow2(nums, k);

		for (Integer max: res) {
			System.out.println(max);
		}
	}

}
