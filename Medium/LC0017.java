package Leetcode.Medium;

import java.util.ArrayList;
import java.util.List;

/**
 * 17. Letter Combinations of a Phone Number
 * Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent.
 * A mapping of digit to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.
 * "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"
 *
 * Example:
 * Input: "23"
 * Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
 *
 * Note:
 * Although the above answer is in lexicographical order, your answer could be in any order you want.
 */
public class LC0017 {
	/**
	 * Data structure: recursion (DFS)
	 * 1) how many levels: digits.length() - 1
	 * 2) What does it store on each level: each possible letters in this position
	 * 3) How many different states should we try to put on this level: 3 or 4
	 *
	 * Time = O(3^n * 4^m)
	 * Space = O(m + n)
	 */
	public List<String> letterCombinations(String digits) {
		List<String> res = new ArrayList<>();
		if (digits == null || digits.length() == 0) {
			return res;
		}
		char[] arr = digits.toCharArray();
		StringBuilder sb = new StringBuilder();

		combinations(arr, 0, sb, res);
		return res;
	}

	private void combinations(char[] arr, int index, StringBuilder sb, List<String> res) {
		if (index == arr.length) {
			res.add(sb.toString());
			return;
		}

		int start = (arr[index] - '0' - 2) * 3 + 97;
		if (arr[index] - '0' >= 8) {
			start++;
		}
		int end = (arr[index] == '9' || arr[index] == '7') ? start + 3 : start + 2;
		for (int i = start; i <= end; i++) {
			char cur = (char)i;
			sb.append(cur);
			combinations(arr, index + 1, sb, res);
			sb.deleteCharAt(sb.length() - 1);
		}
	}

	public static void main(String[] args) {
		LC0017 sol = new LC0017();
		String[] str = new String[]{"","23","7"};
		for (String s: str) {
			System.out.println(sol.letterCombinations(s));
		}
	}
}
