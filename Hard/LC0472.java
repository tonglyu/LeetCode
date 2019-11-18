package Leetcode.Hard;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.util.*;

/**
 * 472. Concatenated Words
 * Given a list of words (without duplicates), please write a program that returns all concatenated words in the given list of words.
 * A concatenated word is defined as a string that is comprised entirely of at least two shorter words in the given array.
 *
 * Example:
 * Input: ["cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"]
 * Output: ["catsdogcats","dogcatsdog","ratcatdogcat"]
 *
 * Explanation: "catsdogcats" can be concatenated by "cats", "dog" and "cats";
 *  "dogcatsdog" can be concatenated by "dog", "cats" and "dog";
 * "ratcatdogcat" can be concatenated by "rat", "cat", "dog" and "cat".
 *
 * Note:
 * The number of elements of the given array will not exceed 10,000
 * The length sum of elements in the given array will not exceed 600,000.
 * All the input string will only include lower case letters.
 * The returned elements order does not matter.
 */
public class LC0472 {
	/**
	 * Solution1: DP
	 * First sort the array by length, the longer string can only be concatenated by shorter string
	 * For each word:
	 *      if word is valid, add it to res;
	 *      add word to the dictionary we have
	 * To check if a word is valid, same algorithm as word break
	 * dp[i] represent the word[0,i - 1] is valid in dict
	 * dp[i] = true if dp[j] && word[j, i - 1] is in dict
	 *
	 * n = #words, len = avg length of word
	 * Time = (nlogn + n * len^2)
	 * Space = O(n*len)
	 */
	public List<String> findAllConcatenatedWordsInADict(String[] words) {
		List<String> res = new ArrayList<>();
		if (words == null || words.length == 0) {
			return res;
		}

		Arrays.sort(words, (s1, s2) -> (s1.length() - s2.length()));
		Set<String> dict = new HashSet<>();

		for (String word: words) {
			if (isValid(word, dict)) {
				res.add(word);
			}
			dict.add(word);
		}

		return res;
	}

	private boolean isValid(String word, Set<String> dict) {
		if (dict.size() == 0) {
			return false;
		}

		// Same as word break
		boolean[] dp = new boolean[word.length() + 1];
		dp[0] = true;
		for (int i = 1; i <= word.length(); i++) {
			for (int j = 0; j < i; j++) {
				if (dp[j] && dict.contains(word.substring(j, i))) {
					dp[i] = true;
					break;
				}
			}
		}

		return dp[word.length()];
	}

	/**
	 * Solution2: Trie + DFS
	 * A concatenated word is a word that satisfies:
	 *   - exist in words;
	 *   - is combination of at least 2 words in words;
	 *
	 * In other words, a concatenated word is a word add other word(words) as prefix.
	 * That's natural to prefix tree(trie).
	 * We can build a trie using words and search for concatenated words in the trie.
	 *
	 * We have to make a decision when we meet a node that meets the end of a word (with en in the example below). We can
	 *   - either take the current node's associated word as prefix (and restart at root for another word)
	 *   - or not take the  current node's associated word as prefix (i.e. move further within the same branch).
	 * For example,
	 *     root
	 *      /\
	 *     c  d
	 *     /   \
	 *     a    o
	 *    /      \
	 *   t(en)    g(en)
	 *   /
	 *  s(en)
	 *
	 * To concatenate catsdogcats using {cat, cats, dog}
	 * search tree: (-'s in the same vertical line are sibling nodes)
	 *     root - c - a - t(en)  - X [to take cat as prefix doesn't work]
	 *                           - s(en) - d - o - g(en) - c - a - t  - s(en) DONE!
	 *                                                   - X [not to take dog as prefix doesn't work]
	 *                                   -  [not to take cats as prefix doesn't work]
	 * Time = O(n * len + n * len ^ 2)
	 * Space = O(n * len)
	 */
	public List<String> findAllConcatenatedWordsInADict2(String[] words) {
		List<String> res = new ArrayList<>();
		if (words == null || words.length == 0) {
			return res;
		}

		TrieNode root = new TrieNode("");
		// construct Trie tree
		for (String word : words) {
			if (word.length() == 0) {
				continue;
			}
			addWord(word, root);
		}

		// test word is a concatenated word or not
		for (String word : words) {
			if (word.length() == 0) {
				continue;
			}
			if (dfs(word.toCharArray(), 0, root, 0)) {
				res.add(word);
			}
		}
		return res;
	}

	private boolean dfs(char[] word, int idx, TrieNode root, int cnt) {
		//base case, when we reach the end, see if we use more than 1 word to construct the word
		if (idx == word.length) {
			return cnt > 1;
		}

		//Each time starts from root to check the word
		TrieNode cur = root;
		for (int i = idx; i < word.length; i++) {
			char c = word[i];
			//if cur char does not exit, return false
			if (cur.links[c - 'a'] == null) {
				return false;
			}

			//continue read word until the end
			cur = cur.links[c - 'a'];

			//when we find a end of a word, we start from the root to find from next index
			if (cur.isEnd) {
				if (dfs(word, i + 1, root, cnt + 1))
					return true;
			}


		}
		return false;
	}

	private void addWord(String word, TrieNode root) {
		char[] chars = word.toCharArray();
		TrieNode cur = root;
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (cur.links[c - 'a'] == null) {
				cur.links[c - 'a'] = new TrieNode(new String(chars, 0, i + 1));
			}
			cur = cur.links[c - 'a'];
		}
		cur.isEnd = true;
	}

	class TrieNode {
		String word;
		TrieNode[] links;
		boolean isEnd;

		public TrieNode(String word) {
			this.word = word;
			links = new TrieNode[26];
		}
	}


	@Test
	public void test1() {
		String[] words = new String[]{"cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"};
		List<String> exp = Arrays.asList("catsdogcats","dogcatsdog","ratcatdogcat");
		List<String> act = findAllConcatenatedWordsInADict2(words);
		Assert.assertTrue(exp.containsAll(act) && act.containsAll(exp));
	}


	public static void main(String[] args) {
		JUnitCore.main(LC0472.class.getName());
	}
}
