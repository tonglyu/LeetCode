package Leetcode.Hard;

import java.util.*;

/**
 * 140. Word Break II
 * Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, add spaces in s to construct a sentence where each word is a valid dictionary word. Return all such possible sentences.
 *
 * Note:
 * The same word in the dictionary may be reused multiple times in the segmentation.
 * You may assume the dictionary does not contain duplicate words.
 * Example 1:
 * Input:
 * s = "catsanddog"
 * wordDict = ["cat", "cats", "and", "sand", "dog"]
 * Output:
 * [
 *   "cats and dog",
 *   "cat sand dog"
 * ]
 *
 * Example 2:
 * Input:
 * s = "pineapplepenapple"
 * wordDict = ["apple", "pen", "applepen", "pine", "pineapple"]
 * Output:
 * [
 *   "pine apple pen apple",
 *   "pineapple pen apple",
 *   "pine applepen apple"
 * ]
 * Explanation: Note that you are allowed to reuse a dictionary word.
 *
 * Example 3:
 * Input:
 * s = "catsandog"
 * wordDict = ["cats", "dog", "sand", "and", "cat"]
 * Output:
 * []
 */
public class LC0140 {
	/**
	 * Method1: DP, but TLE?
	 * Time = O(n^3)
	 * Space = O(n^3)
	 */
	public List<String> wordBreak(String s, List<String> wordDict) {
		List<String> res = new ArrayList<>();
		if (s.length() == 0) return res;

		Set<String> dict = new HashSet<>(wordDict);
		List<String>[] dp = new ArrayList[s.length() + 1];
		dp[0] = new ArrayList<>();
		dp[0].add("");
		for (int i = 1; i <= s.length(); i++) {
			List<String> cur = new ArrayList<>();
			for (int j = 0; j < i; j++) {
				String sub = s.substring(j,i);
				if (dp[j].size() > 0 && dict.contains(sub)) {
					for (String ele: dp[j]) {
						StringBuilder tmp = new StringBuilder();
						tmp.append(ele);
						if (!ele.equals("")) {
							tmp.append(" ");
						}
						tmp.append(sub);
						cur.add(tmp.toString());
					}
				}
			}
			dp[i] = cur;
		}

		return dp[s.length()];
	}


	/**
	 * Method2: DFS + memorization
	 * map<index, list of string> : record substring(index, end) can be broken into what combinations
	 *
	 * For each level, if we find s.substring(start, i) is in the dict, we would like to get the combinations
	 * of string from index  i + 1
	 *
	 * n levels, for each level, we need to traverse n - 1/ n- 2/ ... 1 letters, and build a list need O(n) time
	 * Time = O(n*n*n)
	 * Space = O(n*n*n)
	 */
	public List<String> wordBreak2(String s, List<String> wordDict) {
		if (s.length() == 0) return new ArrayList<>();
		Set<String> dict = new HashSet<>(wordDict);
		Map<Integer, List<String>> map = new HashMap<>();

		List<String> res = dfs(s, dict,0, map );
		return res;
	}

	private List<String> dfs(String s, Set<String> dict, int start, Map<Integer, List<String>> map) {
		if (map.containsKey(start)) {
			return map.get(start);
		}

		List<String> res = new ArrayList<>();
		if (s.length() == start) {
			res.add("");
		}

		for (int end = start + 1; end <= s.length(); end++) {
			if (dict.contains(s.substring(start,end))) {
				List<String> list = dfs(s, dict, end, map);
				for (String temp: list) {
					res.add(s.substring(start,end) + (temp.equals("") ? "" : " ") + temp);
				}
			}
		}

		map.put(start, res);
		return res;
	}

	public static void main(String[] args) {
		String s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		String[] wordDict = new String[]{"a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa"};

		String s1 = "pineapplepenapple";
		String[] wordDict1 = new String[]{"apple", "pen", "applepen", "pine", "pineapple"};
		LC0140 sol = new LC0140();
		List<String> res = sol.wordBreak2(s,Arrays.asList(wordDict));
		System.out.println(res);
	}
}
