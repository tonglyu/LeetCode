package Leetcode.Medium;

import java.util.*;

/**
 * 692. Top K Frequent Words
 * Given a non-empty list of words, return the k most frequent elements.
 * Your answer should be sorted by frequency from highest to lowest. If two words have the same frequency, then the word with the lower alphabetical order comes first.
 *
 * Example 1:
 * Input: ["i", "love", "leetcode", "i", "love", "coding"], k = 2
 * Output: ["i", "love"]
 * Explanation: "i" and "love" are the two most frequent words.
 *     Note that "i" comes before "love" due to a lower alphabetical order.
 *
 * Example 2:
 * Input: ["the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"], k = 4
 * Output: ["the", "is", "sunny", "day"]
 * Explanation: "the", "is", "sunny" and "day" are the four most frequent words,
 *     with the number of occurrence being 4, 3, 2 and 1 respectively.
 *
 * Note:
 * You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
 * Input words contain only lowercase letters.
 *
 * Follow up:
 * Try to solve it in O(n log k) time and O(n) extra space.
 */
public class LC0692 {
	/**
	 * Solution: Map + Heap
	 * Map<string, int>: store the word and its count
	 * Heap: min-heap, of count and reverse alphabet order of words
	 *      (day,1), (sum,2), (the,3), (is,3)
	 * Add all entries in the heap, and add to result in a reverse order
	 *
	 * Time = O(n + nlogk + klogk) = O(nlogk)
	 * Space = O(n)
	 */
	public List<String> topKFrequent(String[] words, int k) {
		List<String> res = new ArrayList<>();
		if (words.length == 0 || k <= 0) {
			return res;
		}

		Map<String, Integer> map = new HashMap<>();
		for (String str: words) {
			map.put(str, map.getOrDefault(str, 0) + 1);
		}

		PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
				if (e1.getValue() != e2.getValue()) {
					return e1.getValue() - e2.getValue();
				} else {
					return e2.getKey().compareTo(e1.getKey());
				}
			}
		});

		for (Map.Entry<String, Integer> entry: map.entrySet()) {
			pq.offer(entry);
			if (pq.size() > k) {
				pq.poll();
			}
		}

		while (!pq.isEmpty()) {
			res.add(0, pq.poll().getKey());
		}
		return res;
	}

	/**
	 * Solution: same map + heap
	 * Heap: max-heap, natural order of string
	 *
	 * Time = O(n + nlogn + klogn) = O(nlogn)
	 * Space = O(n) but actually a little faster
	 */
	public List<String> topKFrequent2(String[] words, int k) {
		List<String> res = new ArrayList<>();
		if (words.length == 0 || k <= 0) {
			return res;
		}

		Map<String, Integer> map = new HashMap<>();
		for (String str: words) {
			map.put(str, map.getOrDefault(str, 0) + 1);
		}

		PriorityQueue<String> pq = new PriorityQueue<>(new Comparator<String>() {
			public int compare(String s1, String s2) {
				if (map.get(s1) != map.get(s2)) {
					return map.get(s2) - map.get(s1);
				} else {
					return s1.compareTo(s2);
				}
			}
		});

		pq.addAll(map.keySet());
		for (int i = 0; i < k; i++) {
			if (!pq.isEmpty()) {
				res.add(pq.poll());
			}
		}
		return res;
	}

	public static void main(String[] args) {
		LC0692 sol = new LC0692();
		String[] words = new String[]{"the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is","is"};
		int k = 4;
		System.out.println(sol.topKFrequent(words,k));
	}
}
