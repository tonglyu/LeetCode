package Leetcode.Medium;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 394. Decode String
 * Given an encoded string, return its decoded string.
 * The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated exactly k times. Note that k is guaranteed to be a positive integer.
 * You may assume that the input string is always valid; No extra white spaces, square brackets are well-formed, etc.
 * Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, k. For example, there won't be input like 3a or 2[4].
 *
 * Examples:
 * s = "3[a]2[bc]", return "aaabcbc".
 * s = "3[a2[c]]", return "accaccacc".
 * s = "2[abc]3[cd]ef", return "abcabccdcdcdef".
 */
public class LC0394 {
	/**
	 * Solution: use stack
	 * nums: use to store the repeat numbers
	 * strs: used to store the different level of string
	 * res: use to store current level of string
	 *
	 * For each char c in s:
	 * Case1: digit, read all digits and put into nums
	 * Case2: letter, append to res
	 * Case3: [,  push res, which means a lower level string into strs, start a new res
	 * Case4: ], pop out from nums to get repeat times,
	 *          start a new sb, init it as strs.pop (lower level)
	 *          append repeated numbers of res into sb
	 *          update res as sb
	 *
	 * Time = O(n)
	 * Space = O(n)
	 */
	public String decodeString(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}

		Deque<Integer> nums = new LinkedList<>();
		Deque<String> strs = new LinkedList<>();
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (Character.isDigit(c)) {
				int num = 0;
				while (i < s.length() && Character.isDigit(s.charAt(i))) {
					num = num * 10 + s.charAt(i) - '0';
					i++;
				}
				nums.push(num);
				i--;
			} else if (c == '[') {
				strs.push(res.toString());
				res = new StringBuilder();
			} else if (c == ']') {
				int recur = nums.pop();
				StringBuilder sb = new StringBuilder(strs.pop());
				for (int j = 0; j < recur; j++) {
					sb.append(res);
				}
				res = new StringBuilder(sb);
			} else {
				res.append(c);
			}
		}

		return res.toString();
	}
	@Test
	public void test1() {
		String S = "3[a]2[bc]";
		Assert.assertEquals(decodeString(S), "aaabcbc");
	}

	@Test
	public void test2() {
		String S = "3[a2[c]]";
		Assert.assertEquals(decodeString(S), "accaccacc");
	}

	@Test
	public void test3() {
		String S = "2[abc]3[cd]ef";
		Assert.assertEquals(decodeString(S), "abcabccdcdcdef");
	}

	public static void main(String[] args) {
		JUnitCore.main(LC0394.class.getName());
	}
}
