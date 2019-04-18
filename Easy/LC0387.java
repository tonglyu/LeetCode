package Leetcode.Easy;

import java.util.*;

/**
 * 387. First Unique Character in a String
 * Given a string, find the first non-repeating character in it and return it's index. If it doesn't exist, return -1.
 *
 * Examples:
 * s = "leetcode"
 * return 0.
 *
 * s = "loveleetcode",
 * return 2.
 * Note: You may assume the string contain only lowercase letters.
 */
public class LC0387 {
	/**
	 * Method1: hashmap
	 * We can also go through the array twice to find the first index
	 * or use the linkedhashmap
	 *
	 * Time = O(n)
	 * Space = O(1)
	 */
	public int firstUniqChar(String s) {
		if (s == null || s.length() == 0) {
			return -1;
		}

		Set<Character> set = new HashSet<>();
		Map<Character, Integer> index = new LinkedHashMap<>();
		char[] arr = s.toCharArray();
		for (int i = 0; i < arr.length; i++) {
			if (set.contains(arr[i])) {
				index.remove(arr[i]);
			} else {
				set.add(arr[i]);
				index.put(arr[i],i);
			}
		}

		if (index.size() == 0) {
			return -1;
		}

		Iterator entries = index.values().iterator();
		return (int)entries.next();
	}

	/**
	 * Method2: two pointers
	 * slow: the first non-duplicate letter's index
	 * fast: current index
	 * int[256] letter: record the utf-8's character
	 *
	 * When to move slow:
	 * at each time, if we found letter[s.charAt(slow)] > 1, slow++
	 *
	 * Time = O(n)
	 * Space = O(1)
	 */
	public int firstUniqChar2(String s) {
		if (s == null || s.length() == 0) {
			return -1;
		}

		int slow = 0;
		int fast = 0;
		int[] letter = new int[256];
		while (fast < s.length()) {
			letter[s.charAt(fast)]++;
			while (slow < s.length() && letter[s.charAt(slow)] > 1) {
				slow++;
			}
			fast++;
		}

		if (slow == s.length()) {
			return -1;
		}
		return slow;
	}

	public static void main(String[] args) {
		LC0387 sol = new LC0387();
		String s = "loveleetcode";
		System.out.println(sol.firstUniqChar(s));
	}
}
