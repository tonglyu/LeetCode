package Leetcode.Hard;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

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
	 * dp[i] represents the list of valid combination of substring to form word[0,i-1]
	 * dp[i] can add current word[j,i) if word in dict and dp[j]
	 *
	 * Time = O(n^3)
	 * Space = O(n^3)
	 */
	public List<String> wordBreak(String s, List<String> wordDict) {
		List<String> res = new ArrayList<>();
		if (s.length() == 0) return res;

		Set<String> dict = new HashSet<>(wordDict);
		List<String>[] dp = new List[s.length() + 1];

		dp[0] = new ArrayList<>(Arrays.asList(""));
		for (int i = 1; i <= s.length(); i++) {
			List<String> cur = new ArrayList<>();
			for (int j = 0; j < i; j++) {
				String sub = s.substring(j,i);
				if (dp[j].size() > 0 && dict.contains(sub)) {
					for (String ele: dp[j]) {
						cur.add(ele.equals("") ? sub : ele + " " + sub);
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
	 * Time = O(n^3) Size of recursion tree can go up to n^2. The creation of list takes n time.
	 * Space = O(n^3) The depth of the recursion tree can go up to n and each activation record can contains a string list of size n.
	 */
	public List<String> wordBreak2(String s, List<String> wordDict) {
		if (s.length() == 0) {
			return new ArrayList<>();
		}
		Set<String> dict = new HashSet<>(wordDict);
		Map<Integer, List<String>> map = new HashMap<>();

		List<String> res = dfs(s, dict,0, map);
		return res;
	}

	private List<String> dfs(String s, Set<String> dict, int start, Map<Integer, List<String>> map) {
		if (map.containsKey(start)) {
			return map.get(start);
		}

		if (start == s.length()) {
			return new ArrayList<>(Arrays.asList(""));
		}

		List<String> res = new ArrayList<>();
		for (int end = start + 1; end <= s.length(); end++) {
			String sub = s.substring(start,end);
			if (dict.contains(sub)) {
				List<String> list = dfs(s, dict, end, map);
				for (String temp: list) {
					res.add(temp.equals("") ? sub : sub +  " " + temp);
				}
			}
		}

		map.put(start, res);
		return res;
	}

	@Test
	public void test1() {
		String s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		List<String> wordDict = new ArrayList<>(Arrays.asList("a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa"));

		List<String> exp = new ArrayList<>();
		List<String> act = wordBreak2(s,wordDict);
		Assert.assertTrue(exp.containsAll(act) && act.containsAll(exp));
	}

	@Test
	public void test2() {
		String s = "catsanddog";
		List<String> wordDict = new ArrayList<>(Arrays.asList("cat", "cats", "and", "sand", "dog"));

		List<String> exp = new ArrayList<>(Arrays.asList("cats and dog", "cat sand dog"));
		List<String> act = wordBreak(s,wordDict);
		Assert.assertTrue(exp.containsAll(act) && act.containsAll(exp));
	}

	@Test
	public void test3() {
		String s = "pineapplepenapple";
		List<String> wordDict = new ArrayList<>(Arrays.asList("apple", "pen", "applepen", "pine", "pineapple"));

		List<String> exp = new ArrayList<>(Arrays.asList("pine apple pen apple", "pineapple pen apple", "pine applepen apple"));
		List<String> act = wordBreak2(s,wordDict);
		Assert.assertTrue(exp.containsAll(act) && act.containsAll(exp));
	}

	public static void main(String[] args) {
		JUnitCore.main(LC0140.class.getName());
	}
}
