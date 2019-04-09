package Leetcode.Easy;

import java.util.HashMap;
import java.util.Map;

/**
 * 290. Word Pattern
 * Given a pattern and a string str, find if str follows the same pattern.
 *Here follow means a full match, such that there is a bijection between a letter in pattern and a non-empty word in str.
 *
 * Example 1:
 * Input: pattern = "abba", str = "dog cat cat dog"
 * Output: true
 *
 * Example 2:
 * Input:pattern = "abba", str = "dog cat cat fish"
 * Output: false
 *
 * Example 3:
 * Input: pattern = "aaaa", str = "dog cat cat dog"
 * Output: false
 *
 * Example 4:
 * Input: pattern = "abba", str = "dog dog dog dog"
 * Output: false
 *
 * Notes:
 * You may assume pattern contains only lowercase letters, and str contains lowercase letters that may be separated by a single space.
 */
public class LC290 {
	/**
	 * Data structure: Hashmap<Character, String>
	 * Case1: if map contains pattern
	 *      1) str of the pattern != current str -> false
	 *      2) ignore
	 * Case2: map does not contain pattern
	 *      1) if map contains value str (which means the key is not current pattern) -> return fasle;
	 *      2) map.put(pattern, str)
	 *
	 * Note: we cannot merge case1.1 into one if statement, otherwise, if the pattern maps to the current str,
	 * it will goes to case2 rather than case1.2
	 *
	 * Time = O(n^2)? Maybe because of the containsValue()
	 * Space = O(n)
	 */
	public boolean wordPattern(String pattern, String str) {
		String[] words = str.split(" ");
		Map<Character, String> map = new HashMap<>();
		if (pattern.length() != words.length) return false;
		for (int i = 0; i < pattern.length(); i++) {
			char cur = pattern.charAt(i);
			if (map.containsKey(cur)) {
				if (!map.get(cur).equals(words[i])) {
					return false;
				}
			} else if (map.containsValue(words[i])) {
				return false;
			}
			map.put(cur, words[i]);
		}
		return true;
	}

	public static void main(String[] args) {
		LC290 sol = new LC290();
		String pattern = "abba";
		String str = "dog cat cat dog";
		System.out.println(sol.wordPattern(pattern, str));
	}
}
