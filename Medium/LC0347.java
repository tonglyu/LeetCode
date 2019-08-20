package Leetcode.Medium;

import java.util.*;

/**
 * 347. Top K Frequent Elements
 * Given a non-empty array of integers, return the k most frequent elements.
 *
 * Example 1:
 * Input: nums = [1,1,1,2,2,3], k = 2
 * Output: [1,2]
 *
 * Example 2:
 * Input: nums = [1], k = 1
 * Output: [1]
 *
 * Note:
 * You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
 * Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
 */
public class LC0347 {
	/**
	 * Solution1: Map + PQ
	 * Map<int, int>: store the number and its frequency
	 * PQ (size k): min-heap, based on the frequency of its key
	 * not sure about the order of key
	 *
	 * Time = (nlogk)
	 * Space = O(n)
	 */
	public List<Integer> topKFrequent(int[] nums, int k) {
		List<Integer> res = new ArrayList<>();
		if (nums.length == 0) {
			return res;
		}

		Map<Integer, Integer> map = new HashMap<>();
		for (int n: nums) {
			map.put(n, map.getOrDefault(n, 0) + 1);
		}

		PriorityQueue<Integer> pq = new PriorityQueue<>(new Comparator<Integer>() {
			public int compare(Integer a, Integer b) {
				return map.get(a) - map.get(b);
			}
		});

		for (int key: map.keySet()) {
			pq.offer(key);
			if (pq.size() > k) {
				pq.poll();
			}
		}

		while (!pq.isEmpty()) {
			res.add(0, pq.poll());
		}
		return res;
	}

	/**
	 * Solution2: bucket sort
	 * List<Integer>[] bucket: maintain an array to store numbers into different bucket whose index is the frequency
	 * Assume length of nums = n
	 * bucket size: n+1, cuz the frequency can only be in the range [0, n]
	 *
	 * Time = O(n)
	 * Space = O(n)
	 */
	public List<Integer> topKFrequent2(int[] nums, int k) {
		List<Integer> res = new ArrayList<>();
		if (nums.length == 0) {
			return res;
		}

		Map<Integer, Integer> map = new HashMap<>();
		for (int n: nums) {
			map.put(n, map.getOrDefault(n, 0) + 1);
		}

		List<Integer>[] bucket = new List[nums.length + 1];
		for (int key: map.keySet()) {
			int value = map.get(key);
			if (bucket[value] == null) {
				bucket[value] = new ArrayList<>();
			}
			bucket[value].add(key);
		}

		for (int i = bucket.length - 1; i >= 0; i--) {
			if (bucket[i] == null) {
				continue;
			}
			for (int num: bucket[i]) {
				if (res.size() < k) {
					res.add(num);
				}
			}
		}

		return res;
	}

	public static void main(String[] args) {
		LC0347 sol = new LC0347();
		int[] nums = new int[]{1,1,2,2,2,3,3,3,4,4,4};
		int k = 2;
		System.out.println(sol.topKFrequent2(nums,k));
	}
}
