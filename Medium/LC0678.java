package Leetcode.Medium;

/**
 * 678. Valid Parenthesis String
 * Given a string containing only three types of characters: '(', ')' and '*', write a function to check whether this string is valid. We define the validity of a string by these rules:
 *
 * Any left parenthesis '(' must have a corresponding right parenthesis ')'.
 * Any right parenthesis ')' must have a corresponding left parenthesis '('.
 * Left parenthesis '(' must go before the corresponding right parenthesis ')'.
 * '*' could be treated as a single right parenthesis ')' or a single left parenthesis '(' or an empty string.
 * An empty string is also valid.
 *
 * Example 1:
 * Input: "()"
 * Output: True
 *
 * Example 2:
 * Input: "(*)"
 * Output: True
 *
 * Example 3:
 * Input: "(*))"
 * Output: True
 *
 * Note:
 * The string size will be in the range [1, 100].
 * ref: https://www.youtube.com/watch?v=h9Y3i7hhCpo
 */
public class LC0678 {
	/**
	 * min_op: the minimum right parenthesis required
	 * max_op: the maximum right parenthesis required
	 *
	 * min_op++ if c == '(' else min_op--
	 * max_op++ if c != ')' else max_op--
	 * if max_op < 0, which means we have more right parenthesis than left now, return false
	 * min_op = max(0, min_op)
	 *
	 * Time = O(n)
	 * Space = O(1)
	 */
	public boolean checkValidString(String s) {
		if (s.length() == 0) {
			return  true;
		}

		int min_op = 0;
		int max_op = 0;
		for (char c: s.toCharArray()) {
			if (c == '(') {
				min_op++;
			} else {
				min_op--;
			}
			if (c != ')') {
				max_op++;
			} else {
				max_op--;
			}

			if (max_op < 0) return false;
			if (min_op < 0) min_op = 0;
		}

		return min_op == 0;
	}

	/**
	 * Data structure: dp[i][j] represents whether substring s[i,j] is valid
	 * init: dp[i][i] = T only if s[i] == '*'
	 * Case1: L| LLLRRR| R
	 *      L != ')' && R != '(' && dp[i+1][j-2]
	 * Case2: L....RL...R
	 *      find break point k -> dp[i][k] && dp[k+1][j]
	 *
	 * Time = O(n^3)
	 * Space = O(n^2)
	 */
	public boolean checkValidString2(String s) {
		if (s.length() == 0) {
			return  true;
		}

		int n = s.length();
		int[][] dp = new int[n][n];
		for (int i = 0; i < n; i++) {
			dp[i][i] = s.charAt(i) == '*' ? 1 : 0;
		}

		char[] arr = s.toCharArray();
		for (int len = 2; len <= n; len++) {
			for (int i = 0; i <= n - len; i++) {
				int j = i + len - 1;

				//Case1
				if ((arr[i] == '(' || arr[i] == '*' ) &&
						(arr[j] == ')' || arr[j] == '*')) {
					if (len == 2 || dp[i+1][j-1] == 1) {
						dp[i][j] = 1;
						continue;
					}
				}

				//Case2
				for (int k = i; k < j; k++) {
					if (dp[i][k] == 1 && dp[k + 1][j] == 1) {
						dp[i][j] = 1;
						break;
					}
				}
			}
		}

		return dp[0][n-1] == 1;
	}


	public static void main(String[] args) {
		LC0678 sol = new LC0678();
		String s = "(*))";
		System.out.println(sol.checkValidString2(s));
	}
}

