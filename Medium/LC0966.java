package Leetcode.Medium;

import java.util.*;

/**
 * 966. Vowel Spellchecker
 * Given a wordlist, we want to implement a spellchecker that converts a query word into a correct word.
 * For a given query word, the spell checker handles two categories of spelling mistakes:
 * Capitalization: If the query matches a word in the wordlist (case-insensitive), then the query word is returned with the same case as the case in the wordlist.
 * Example: wordlist = ["yellow"], query = "YellOw": correct = "yellow"
 * Example: wordlist = ["Yellow"], query = "yellow": correct = "Yellow"
 * Example: wordlist = ["yellow"], query = "yellow": correct = "yellow"
 * Vowel Errors: If after replacing the vowels ('a', 'e', 'i', 'o', 'u') of the query word with any vowel individually, it matches a word in the wordlist (case-insensitive), then the query word is returned with the same case as the match in the wordlist.
 * Example: wordlist = ["YellOw"], query = "yollow": correct = "YellOw"
 * Example: wordlist = ["YellOw"], query = "yeellow": correct = "" (no match)
 * Example: wordlist = ["YellOw"], query = "yllw": correct = "" (no match)
 *
 * In addition, the spell checker operates under the following precedence rules:
 * When the query exactly matches a word in the wordlist (case-sensitive), you should return the same word back.
 * When the query matches a word up to capitlization, you should return the first such match in the wordlist.
 * When the query matches a word up to vowel errors, you should return the first such match in the wordlist.
 * If the query has no matches in the wordlist, you should return the empty string.
 * Given some queries, return a list of words answer, where answer[i] is the correct word for query = queries[i].
 *
 * Example 1:
 * Input: wordlist = ["KiTe","kite","hare","Hare"], queries = ["kite","Kite","KiTe","Hare","HARE","Hear","hear","keti","keet","keto"]
 * Output: ["kite","KiTe","KiTe","Hare","hare","","","KiTe","","KiTe"]
 *
 * Note:
 * 1 <= wordlist.length <= 5000
 * 1 <= queries.length <= 5000
 * 1 <= wordlist[i].length <= 7
 * 1 <= queries[i].length <= 7
 * All strings in wordlist and queries consist only of english letters.
 */
public class LC0966 {
	Map<String, String> standadize = new HashMap<>();
	Map<String, String> capitalize = new HashMap<>();
	Set<String> original = new HashSet<>();

	/**
	 * Solution: use 3 maps to store the different formats of words
	 * 1 for the original format
	 * 1 for the all lowercase format, only store the first match
	 * 1 for the ignore vowel char format -> all in lowercase, only store the first match
	 *
	 * For each query in queries:
	 *      we first check if we have the original format
	 *      then check if we have the lowercase
	 *      then for the standardize format
	 *
	 * Time = O(W + Q)
	 * Space = O(3W)
	 */
	public String[] spellchecker(String[] wordlist, String[] queries) {
		for (String w: wordlist) {
			original.add(w);

			String wordlow = w.toLowerCase();
			if (capitalize.get(wordlow) == null) {
				capitalize.put(wordlow, w);
			}

			String ans = devowel(w);
			if (standadize.get(ans) == null) {
				standadize.put(ans, w);
			}
		}

		String[] ans = new String[queries.length];
		int t = 0;
		for (String q: queries) {
			ans[t++] = check(q);
		}

		return ans;
	}

	private String check(String q) {
		if (original.contains(q)) {
			return q;
		}

		String queryL = q.toLowerCase();
		if (capitalize.containsKey(queryL))
			return capitalize.get(queryL);

		String stdQ = devowel(q);
		if (standadize.containsKey(stdQ)) {
			return standadize.get(stdQ);
		}

		return "";
	}

	public String devowel(String word) {
		StringBuilder ans = new StringBuilder();
		for (char c: word.toCharArray())
			ans.append(isVowel(Character.toLowerCase(c)) ? '*' : Character.toLowerCase(c));
		return ans.toString();
	}

	public boolean isVowel(char c) {
		return (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u');
	}

	public static void main(String[] args) {
//		String[] wordlist = new String[]{"KiTe","kite","hare","Hare"};
//		String[] queries = new String[]{"kite","Kite","KiTe","Hare","HARE","Hear","hear","keti","keet","keto"};

		String[] wordlist = new String[]{"YellOw"};
		String[] queries = new String[]{"yollow"};

		LC0966 sol = new LC0966();
		for (String r: sol.spellchecker(wordlist, queries)) {
			System.out.println(r);
		}

	}
}
