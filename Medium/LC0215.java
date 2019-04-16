package Leetcode.Medium;

import java.util.PriorityQueue;

/**
 * 215. Kth Largest Element in an Array
 * Find the kth largest element in an unsorted array. Note that it is the kth largest element in the sorted order, not the kth distinct element.
 *
 * Example 1:
 * Input: [3,2,1,5,6,4] and k = 2
 * Output: 5
 *
 * Example 2:
 * Input: [3,2,3,1,2,4,5,5,6] and k = 4
 * Output: 4
 *
 * Note:
 * You may assume k is always valid, 1 ≤ k ≤ array's length.
 */
public class LC0215 {
	/**
	 * Method1: min-heap of k size
	 * Maintain a  min-heap to store the k largest element in array
	 * for each ele:
	 *      if pq.size < k: pq.offer(ele)
	 *      else:
	 *          if pq.peek < ele:
	 *              pq.poll; pq.offer(ele)
	 * ans = pq.poll
	 *
	 * Time = O(n * logk)
	 * Space = O(k) for heap
	 */
	public int findKthLargest(int[] nums, int k) {
		if (nums.length == 0) return 0;

		PriorityQueue<Integer> pq = new PriorityQueue<>(k);

		for (int i = 0; i < nums.length; i++) {
			if (pq.size() < k) {
				pq.offer(nums[i]);
			} else {
				if (pq.peek() < nums[i]) {
					pq.poll();
					pq.offer(nums[i]);
				}
			}
		}

		return pq.peek();
	}

	/**
	 * Method2: quick select
	 *
	 * Similar to quick sort: each time find the pivot idx for pivot
	 * if (idx < target) search right part
	 * else search left part
	 * Difference: do not need to check left >= right as base case
	 *
	 * Time = O(n + n/2 + n/4 + ... + 1) = O(n) in average
	 * Space = O(logn)
	 */
	public int findKthLargest2(int[] nums, int k) {
		if (nums.length == 0) return 0;

		int n = nums.length;
		quickSelect(nums, n-k, 0, nums.length - 1);
		return nums[n-k];
	}

	private void quickSelect(int[] nums, int target, int left, int right) {
		int pivot = partition(nums, left, right);
		if (pivot == target) {
			return;
		} else if (target > pivot) {
			quickSelect(nums, target, pivot + 1, right);
		}  else {
			quickSelect(nums, target, left, pivot - 1);
		}
	}

	private int partition(int[] nums, int left, int right) {
		int pivot = nums[right];

		int l = left;
		int r = right - 1;
		while (l <= r) {
			if (nums[l] < pivot) {
				l++;
			} else if (nums[r] >= pivot) {
				r--;
			} else {
				swap(nums,l,r);
				l++;
				r--;
			}
		}

		swap(nums,l,right);
		return l;
	}

	private void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}

	public static void main(String[] args) {
		LC0215 sol = new LC0215();
		int[] nums = new int[]{2,1};
		int k = 1;

		System.out.println(sol.findKthLargest2(nums,k));
	}
}
