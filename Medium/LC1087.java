package Leetcode.Medium;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * 1087. Brace Expansion
 * A string S represents a list of words.
 * Each letter in the word has 1 or more options.  If there is one option, the letter is represented as is.  If there is more than one option, then curly braces delimit the options.  For example, "{a,b,c}" represents options ["a", "b", "c"].
 * For example, "{a,b,c}d{e,f}" represents the list ["ade", "adf", "bde", "bdf", "cde", "cdf"].
 *
 * Return all words that can be formed in this manner, in lexicographical order.
 *
 * Example 1:
 * Input: "{a,b}c{d,e}f"
 * Output: ["acdf","acef","bcdf","bcef"]
 *
 * Example 2:
 * Input: "abcd"
 * Output: ["abcd"]
 *
 * Note:
 * 1 <= S.length <= 50
 * There are no nested curly brackets.
 * All characters inside a pair of consecutive opening and ending curly brackets are different.
 */
public class LC1087 {
	/**
	 * Solution: DFS
	 * Each segment by braces are levels, at each level, we have characters in options to fill in
	 *
	 * Time = O(n + n^number of braces)?
	 * Space = O(number of braces + n) = O(n)
	 */
	public String[] expand(String S) {
		if (S == null || S.length() == 0) {
			return new String[0];
		}

		List<String> parsed = parse(S);

		TreeSet<String> res = new TreeSet<>();
		StringBuilder sb = new StringBuilder();
		dfs(parsed, 0, sb, res);

		return res.toArray(new String[res.size()]);
	}

	private List<String> parse(String S) {
		List<String> parsed = new ArrayList<>();
		StringBuilder sb;
		for (int i = 0; i < S.length(); i++) {
			char c = S.charAt(i);
			if (c == '{' ) {
				sb = new StringBuilder();
				while (i < S.length() && S.charAt(i) != '}') {
					if (Character.isLetter(S.charAt(i))) {
						sb.append(S.charAt(i));
					}
					i++;
				}
				parsed.add(sb.toString());
			} else {
				parsed.add(Character.toString(c));
			}
		}

		return parsed;
	}

	private void dfs(List<String> map, int idx, StringBuilder sb, TreeSet<String> res) {
		if (idx == map.size()) {
			res.add(sb.toString());
			return;
		}

		for (char c: map.get(idx).toCharArray()) {
			sb.append(c);
			dfs(map, idx + 1, sb, res);
			sb.deleteCharAt(sb.length() - 1);
		}
	}

	/**
	 * A better version without parsing in advance.
	 */
	public String[] expand2(String S) {
		if (S == null || S.length() == 0) {
			return new String[0];
		}

		TreeSet<String> res = new TreeSet<>();
		StringBuilder sb = new StringBuilder();
		dfs2(S, 0, sb, res);

		return res.toArray(new String[res.size()]);
	}

	private void dfs2(String S, int idx, StringBuilder sb, TreeSet<String> res) {
		if (idx == S.length()) {
			res.add(sb.toString());
			return;
		}

		if (S.charAt(idx) == '{') {
			int endIdx = idx;
			List<Character> level = new ArrayList<>();
			while (endIdx < S.length() && S.charAt(endIdx) != '}') {
				if (Character.isLetter(S.charAt(endIdx))) {
					level.add(S.charAt(endIdx));
				}
				endIdx++;
			}

			for (char c: level) {
				sb.append(c);
				dfs2(S, endIdx + 1, sb, res);
				sb.deleteCharAt(sb.length() - 1);
			}
		} else {
			sb.append(Character.toString(S.charAt(idx)));
			dfs2(S, idx + 1, sb, res);
			sb.deleteCharAt(sb.length() - 1);
		}
	}

	@Test
	public void test1() {
		String S = "{a,b}c{d,e}f";
		Assert.assertArrayEquals(expand2(S), new String[]{"acdf","acef","bcdf","bcef"});
	}

	@Test
	public void test2() {
		String S = "abcd";
		Assert.assertArrayEquals(expand2(S), new String[]{"abcd"});
	}

	@Test
	public void test3() {
		String S = "{a,b}{z,x,y}";
		Assert.assertArrayEquals(expand2(S), new String[]{"ax","ay","az","bx","by","bz"});
	}

	public static void main(String[] args) {
		JUnitCore.main(LC1087.class.getName());
	}
}
