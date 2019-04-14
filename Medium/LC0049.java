package Leetcode.Medium;

import java.util.*;

/**
 * 49. Group Anagrams
 * Given an array of strings, group anagrams together.
 *
 * Example:
 *
 * Input: ["eat", "tea", "tan", "ate", "nat", "bat"],
 * Output:
 * [
 *   ["ate","eat","tea"],
 *   ["nat","tan"],
 *   ["bat"]
 * ]
 *
 * Note:
 * All inputs will be in lowercase.
 * The order of your output does not matter.
 */
public class LC0049 {
	/**
	 * Data structure: map
	 * key = letters + #, value = list of anagrams
	 * e.g. a1e1t1, ate, eat, tea
	 *
	 * For each string:
	 *      1) count the occurrence of each letter
	 *      2) build the key
	 *      3) add new entry into map
	 *
	 * Time = O(nk) k equals to max length of str
	 * Space = O(n)
	 */
	public List<List<String>> groupAnagrams(String[] strs) {
		if (strs.length == 0) {
			return new ArrayList<>();
		}

		Map<String, List<String>> map = new HashMap<>();
		for (String s: strs) {
			int[] count = new int[26];
			for (char c: s.toCharArray()) {
				count[c - 'a']++;
			}

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 26; i++) {
				if (count[i] != 0) {
					sb.append(String.valueOf((char)('a' + i)));
					sb.append(count[i]);
				}
			}

			String key = sb.toString();
			if (map.get(key) == null) {
				map.put(key, new ArrayList<>());
			}
			map.get(key).add(s);
		}

		/* In case of sorting
		List<List<String>> res = new ArrayList<>();
		for (List<String> list: map.values()) {
			Collections.sort(list);
			res.add(list);
		}
		*/

		return new ArrayList<>(map.values());
	}

	public static void main(String[] args) {
		LC0049 sol = new LC0049();
		String[] strs = new String[]{"eat", "tea", "tan", "ate", "nat", "bat"};
		List<List<String>> res = sol.groupAnagrams(strs);
		System.out.println(res);
	}
}
