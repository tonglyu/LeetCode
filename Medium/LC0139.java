package Leetcode.Medium;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 139. Word Break
 * Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, determine if s can be segmented into a space-separated sequence of one or more dictionary words.
 *
 * Note:
 * The same word in the dictionary may be reused multiple times in the segmentation.
 * You may assume the dictionary does not contain duplicate words.
 *
 * Example 1:
 * Input: s = "leetcode", wordDict = ["leet", "code"]
 * Output: true
 * Explanation: Return true because "leetcode" can be segmented as "leet code".
 *
 * Example 2:
 * Input: s = "applepenapple", wordDict = ["apple", "pen"]
 * Output: true
 * Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
 *              Note that you are allowed to reuse a dictionary word.
 *
 * Example 3:
 * Input: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
 * Output: false
 */
public class LC0139 {
	/**
	 * DP
	 * dp[i] represents input[i-1], also can represent s.substring(0,i)
	 *
	 * dp[0] = true
	 * dp[i] = OR ( dp[j] && dict.contains(s.substring(j,i))
	 *          j = 0 to i-1
	 *
	 * Time = O(n^2)
	 * Space = O(n)
	 */
	public boolean wordBreak(String s, List<String> wordDict) {
		if (s.length() == 0) {
			return true;
		}

		Set<String> dict = new HashSet<>(wordDict);
		boolean[] dp = new boolean[s.length() + 1];
		dp[0] = true;
		for (int i = 1; i <= s.length(); i++) {
			for (int j = 0; j < i; j++) {
				if (dp[j] && dict.contains(s.substring(j,i))) {
					dp[i] = true;
					break;
				}
			}
		}
		return dp[s.length()];
 	}

	public static void main(String[] args) {
		LC0139 sol = new LC0139();
		String s = "catsandog";
		String[] wordDict = new String[]{"cats", "dog", "sand", "and", "cat"};

		System.out.println(sol.wordBreak(s,Arrays.asList(wordDict)));
	}
}
