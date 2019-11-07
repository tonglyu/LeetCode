package Leetcode.Medium;

/**
 * 211. Add and Search Word - Data structure design
 * Design a data structure that supports the following two operations:
 *
 * void addWord(word)
 * bool search(word)
 * search(word) can search a literal word or a regular expression string containing only letters a-z or .. A . means it can represent any one letter.
 *
 * Example:
 * addWord("bad")
 * addWord("dad")
 * addWord("mad")
 * search("pad") -> false
 * search("bad") -> true
 * search(".ad") -> true
 * search("b..") -> true
 *
 * Note:
 * You may assume that all words are consist of lowercase letters a-z.
 */
class WordDictionary {
	/**
	 * Same as LC0208 for add a word
	 * However, for search, we need to use dfs for Fuzzy Search
	 *
	 * Time = O(26^l) l=word.length
	 */
	TrieNode root;
	/** Initialize your data structure here. */
	public WordDictionary() {
		root = new TrieNode();
	}

	/** Adds a word into the data structure. */
	public void addWord(String word) {
		TrieNode cur = root;
		for (char c: word.toCharArray()) {
			if (cur.links[c - 'a'] == null) {
				cur.links[c - 'a'] = new TrieNode();
			}
			cur = cur.links[c - 'a'];
		}
		cur.isEnd = true;
	}

	/** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
	public boolean search(String word) {
		return dfs(word.toCharArray(), 0, root);
	}

	private boolean dfs(char[] word, int index, TrieNode cur) {
		if (index == word.length) {
			return cur.isEnd;
		}

		char c = word[index];
		if (c != '.') {
			if (cur.links[c - 'a'] == null) return false;
			return dfs(word, index + 1, cur.links[c - 'a']);
		} else {
			for (int i = 0; i < 26; i++) {
				if (cur.links[i] != null && dfs(word, index + 1, cur.links[i])) {
					return true;
				}
			}
			return false;
		}
	}
}

public class LC0211 {
	public static void main(String[] args) {
		WordDictionary obj = new WordDictionary();
		obj.addWord("bad");
		obj.addWord("dad");
		obj.addWord("mad");
		System.out.println(obj.search("pad"));//false
		System.out.println(obj.search("bad"));//true
		System.out.println(obj.search(".ad"));//true
		System.out.println(obj.search("b.."));//true
	}
}
