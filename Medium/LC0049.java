package Leetcode.Medium;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

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
	 * key = letters + #(e.g. b1u1y1), value = list of anagrams
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
			map.putIfAbsent(key, new ArrayList<>());
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

	@Test
	public void test1() {
		String[] strs = new String[]{"eat", "tea", "tan", "ate", "nat", "bat"};

		List<Set<String>> exp = Arrays.asList(new HashSet<>(Arrays.asList("ate","eat","tea")),
				new HashSet<>(Arrays.asList("nat","tan")),new HashSet<>(Arrays.asList("bat")));
		List<List<String>> res = groupAnagrams(strs);
		List<Set<String>> act = new ArrayList<>();
		for (List<String> list: res) {
			act.add(new HashSet<>(list));
		}
		Assert.assertTrue(exp.containsAll(act) && act.containsAll(exp));
	}

	@Test
	public void test2() {
		String[] strs = new String[]{"cab","tin","pew","duh","may","ill","buy","bar","max","doc"};

		List<List<String>> exp = new ArrayList<>(Arrays.asList(Arrays.asList("cab"),Arrays.asList("tin"),
				Arrays.asList("pew"),Arrays.asList("duh"),Arrays.asList("may"),Arrays.asList("ill"),
				Arrays.asList("buy"),Arrays.asList("bar"),Arrays.asList("max"),Arrays.asList("doc")));
		List<List<String>> act = groupAnagrams(strs);
		Assert.assertTrue(exp.containsAll(act) && act.containsAll(exp));
	}

	public static void main(String[] args) {
		JUnitCore.main(LC0049.class.getName());
	}
}
