package Leetcode.Medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 763. Partition Labels
 * A string S of lowercase letters is given. We want to partition this string into as many parts as possible so that each letter appears in at most one part, and return a list of integers representing the size of these parts.
 *
 * Example 1:
 * Input: S = "ababcbacadefegdehijhklij"
 * Output: [9,7,8]
 * Explanation:
 * The partition is "ababcbaca", "defegde", "hijhklij".
 * This is a partition so that each letter appears in at most one part.
 * A partition like "ababcbacadefegde", "hijhklij" is incorrect, because it splits S into less parts.
 *
 * Note:
 * S will have length in range [1, 500].
 * S will consist of lowercase letters ('a' to 'z') only.
 */
public class LC0763 {
	/**
	 * For each letter encountered, process the last occurrence of that letter, extending the current partition [anchor, j] appropriately.
	 * 1) Find the last occurrence of each letter
	 * 2) maintain two pointers: expand the range when last occurrence of current letter is larger than right
	 *      left = left boundary
	 *      right = Math.max(right, s.charAt(i))
	 *      if i == right:
	 *          add range to result
	 *          update left
	 *
	 * Time = O(n)
	 * Space = O(n)
	 */
	public List<Integer> partitionLabels(String S) {
		List<Integer> res = new ArrayList<>();
		if (S.length() == 0) return res;

		Map<Character, Integer> map = new HashMap<>();
		for (int i = 0; i < S.length(); i++) {
			map.put(S.charAt(i), i);
		}

		int idx = 0;
		int left = 0;
		int right = 0;
		while (idx < S.length()) {
			right = Math.max(right, map.get(S.charAt(idx)));
			if (idx == right) {
				res.add(right - left + 1);
				left = idx + 1;
			}
			idx++;
		}

		return res;
	}

	public static void main(String[] args) {
		LC0763 sol = new LC0763();
		String s = "ababcbacadefegdehijhklij";
		System.out.println(sol.partitionLabels(s));
	}
}
