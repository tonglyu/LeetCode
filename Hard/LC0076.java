package Leetcode.Hard;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.util.HashMap;
import java.util.Map;

/**
 * 76. Minimum Window Substring
 * Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).
 *
 * Example:
 * Input: S = "ADOBECODEBANC", T = "ABC"
 * Output: "BANC"
 *
 * Note:
 * If there is no such window in S that covers all characters in T, return the empty string "".
 * If there is such window, you are guaranteed that there will always be only one unique minimum window in S.
 */
public class LC0076 {
	/**
	 * Solution: sliding window
	 * Two pointers slow and fast
	 * Map(char, int) maintains the occurrence of chars in t
	 * matchCount: the number of chars have been matched in t, when we have all chars matched, we can update our window size
	 *
	 * For each char c at fast:
	 *  Case1: c is not in map, fast++
	 *  Case2: c is in map,
	 *      1. update remain required occurrence of c, map(c, cnt - 1)
	 *      2. if current left occurrence of c is 0, means we have all c matched, matchCount++
	 *      3. if all chars are matched, we should move slow as much as possible until matchCount is less than required:
	 *          3.1 update window size
	 *          3.2 get leftmost char ch in window
	 *              if ch is not in map, move forward slow
	 *              otherwise, increase required occurrence of ch by 1
	 *              check if required occurrence of ch is > 0, matchCount--
	 *
	 * Time = O(S + T) -> worst case 2S + T, for each char is accessed by both slow and fast
	 * Space = O(T)
	 */
	public String minWindow(String s, String t) {
		if (s == null || t == null || t.length() > s.length()) {
			return "";
		}

		Map<Character, Integer> map = new HashMap<>();
		for (char c: t.toCharArray()) {
			map.put(c, map.getOrDefault(c, 0) + 1);
		}

		int slow = 0, matchCount = 0;
		int start = 0, end = 0, min = Integer.MAX_VALUE;

		for (int fast = 0; fast < s.length(); fast++) {
			char ch = s.charAt(fast);
			Integer cnt = map.get(ch);
			if (cnt == null) {
				continue;
			}
			map.put(ch, cnt - 1);
			// ch is all matched, update matchCount
			if (map.get(ch) == 0) {
				matchCount++;
			}

			// when we have all chars matched
			while (matchCount == map.size()) {
				if (fast - slow + 1 < min) {
					min = fast - slow + 1;
					start = slow;
					end = fast;
				}

				char leftMost = s.charAt(slow);
				slow++;
				Integer cntLeft = map.get(leftMost);
				if (cntLeft == null) {
					continue;
				}
				map.put(leftMost, cntLeft + 1);
				// if we remove one required char in our res, we should jump out of the loop
				if (map.get(leftMost) > 0) {
					matchCount--;
				}
			}
		}

		if (min == Integer.MAX_VALUE) {
			return "";
		}

		return s.substring(start, end + 1);
	}

	@Test
	public void test1() {
		String S = "ADOBECODEBANC", T = "ABC";
		Assert.assertEquals("BANC", minWindow(S,T));
	}

	@Test
	public void test2() {
		String S = "ab", T = "b";
		Assert.assertEquals("b", minWindow(S,T));
	}

	@Test
	public void test3() {
		String S = "aa", T = "aa";
		Assert.assertEquals("aa", minWindow(S,T));
	}

	public static void main(String[] args) {
		JUnitCore.main(LC0076.class.getName());
	}
}
