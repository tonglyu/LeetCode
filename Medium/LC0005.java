package Leetcode.Medium;

/**
 * 5. Longest Palindromic Substring
 * Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.
 *
 * Example 1:
 * Input: "babad"
 * Output: "bab"
 * Note: "aba" is also a valid answer.
 *
 * Example 2:
 * Input: "cbbd"
 * Output: "bb"
 */
public class LC0005 {
	/**
	 * Solution1: dp
	 * dp[i][j] represents if substring[i,j] is a valid palindrome
	 * base case:
	 *      i == j, dp[i][i] = true
	 *      i + 1 = j, dp[i][j] = s.charAt(i) == s.charAt(j)
	 * dp rule:
	 *      dp[i][j] = s.charAt(i) == s.charAt(j) && dp[i+1][j-1]
	 *
	 * We only need half matrix to store
	 * outer loop: j means the length of string - 1
	 * inner loop: i from 0 to j
	 *
	 * Time = O(n^2)
	 * Space = O(n^2)
	 */
	public String longestPalindrome(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}

		int len = s.length();
		int max = -1;
		String res = "";
		boolean[][] dp = new boolean[s.length()][s.length()];
		for (int j = 0; j < len; j++) {
			for (int i = 0; i <= j; i++) {
				// Case1: i == j, s[i] == s[j]
				// Case2: i+1 == j, s[i] == s[j]
				// Case3: dp[i+1][j-1], s[i] == s[j]
				if ((j - i <= 2|| dp[i+1][j-1]) && s.charAt(i) == s.charAt(j)) {
					dp[i][j] = true;
					if (j - i + 1 > max) {
						max = j - i + 1;
						res = s.substring(i, j + 1);
					}
				}
			}
		}
		return res;
	}

	/**
	 * Solution2: expand from center
	 * Case1: expand from s(i)
	 * Case2: expand from s(i,i+1)
	 * When two sides' character equals to each other, we move the boundary one step until they are different
	 * So, palindrome from center i is substring[i+1, j-1]
	 *
	 * Time = O(n^2)
	 * Space = O(1)
	 */
	int max;
	int start;
	public String longestPalindrome2(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}

		for (int i = 0; i < s.length(); i++) {
			helper(s,i,i);
			helper(s,i,i+1);
		}

		return s.substring(start, start + max);
	}

	private void helper(String s, int left, int right) {
		while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
			left--;
			right++;
		}

		if (right - left - 1 > max) {
			max = right - left - 1;
			start = left + 1;
		}
	}

	public static void main(String[] args) {
		LC0005 sol = new LC0005();
		String s = "cbbd";
		System.out.println(sol.longestPalindrome2(s));
	}
}
