package Leetcode.Medium;

/**
 * 208. Implement Trie (Prefix Tree)
 * Implement a trie with insert, search, and startsWith methods.
 *
 * Example:
 * Trie trie = new Trie();
 * trie.insert("apple");
 * trie.search("apple");   // returns true
 * trie.search("app");     // returns false
 * trie.startsWith("app"); // returns true
 * trie.insert("app");
 * trie.search("app");     // returns true
 *
 * Note:
 * You may assume that all inputs are consist of lowercase letters a-z.
 * All inputs are guaranteed to be non-empty strings.
 */
class Trie {
	/**
	 * Time = O(m) for all api
	 */
	TrieNode root;

	/** Initialize your data structure here. */
	public Trie() {
		root = new TrieNode();
	}

	/** Inserts a word into the trie. */
	public void insert(String word) {
		TrieNode cur = root;
		for (char c: word.toCharArray()) {
			if (cur.links[c - 'a'] == null) {
				cur.links[c - 'a'] = new TrieNode();
			}
			cur = cur.links[c - 'a'];
		}
		cur.isEnd = true;
	}

	/** Returns if the word is in the trie. */
	public boolean search(String word) {
		TrieNode cur = root;
		for (char c: word.toCharArray()) {
			if (cur.links[c - 'a'] == null) {
				return false;
			}
			cur = cur.links[c - 'a'];
		}

		return cur.isEnd;
	}

	/** Returns if there is any word in the trie that starts with the given prefix. */
	public boolean startsWith(String prefix) {
		TrieNode cur = root;
		for (char c: prefix.toCharArray()) {
			if (cur.links[c - 'a'] == null) {
				return false;
			}
			cur = cur.links[c - 'a'];
		}
		return true;
	}
}

class TrieNode {
	TrieNode[] links;
	boolean isEnd;
	public TrieNode() {
		links = new TrieNode[26];
	}
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */

public class LC0208 {
	public static void main(String[] args) {
		Trie trie = new Trie();

		trie.insert("apple");
		System.out.println(trie.search("apple"));   // returns true
		System.out.println(trie.search("app"));     // returns false
		System.out.println(trie.startsWith("app")); // returns true
		trie.insert("app");
		System.out.println(trie.search("app"));     // returns true
	}
}
