package Leetcode.Easy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 819. Most Common Word
 * Given a paragraph and a list of banned words, return the most frequent word that is not in the list of banned words.  It is guaranteed there is at least one word that isn't banned, and that the answer is unique.
 *
 * Words in the list of banned words are given in lowercase, and free of punctuation.  Words in the paragraph are not case sensitive.  The answer is in lowercase.
 *
 * Example:
 * Input:
 * paragraph = "Bob hit a ball, the hit BALL flew far after it was hit."
 * banned = ["hit"]
 * Output: "ball"
 * Explanation:
 * "hit" occurs 3 times, but it is a banned word.
 * "ball" occurs twice (and no other word does), so it is the most frequent non-banned word in the paragraph.
 * Note that words in the paragraph are not case sensitive,
 * that punctuation is ignored (even if adjacent to words, such as "ball,"),
 * and that "hit" isn't the answer even though it occurs more because it is banned.
 *
 * Note:
 * 1 <= paragraph.length <= 1000.
 * 0 <= banned.length <= 100.
 * 1 <= banned[i].length <= 10.
 * The answer is unique, and written in lowercase (even if its occurrences in paragraph may have uppercase symbols, and even if it is a proper noun.)
 * paragraph only consists of letters, spaces, or the punctuation symbols !?',;.
 * There are no hyphens or hyphenated words.
 * Words only consist of letters, never apostrophes or other punctuation symbols.
 */
public class LC0819 {
	/**
	 * Data structure: map to record the occurrence of the word
	 * StringBuilder to build word letter by letter
	 * For each (int idx, char c)
	 * 1) if (c is letter && idx is not the last index)
	 *      sb.append(c)
	 * 2) else: c is not letter or idx == s.length() - 1
	 *    if idx == s.length() -  1 && c is letter: (we can ignore first half)
	 *          s.append(c)
	 *    if sb is not empty
	 *      if letter is not in banned
	 *          update map
	 *          update result
	 *      set sb empty
	 *
	 * Time = O(n)
	 * Space = O(n)
	 */
	public String mostCommonWord(String paragraph, String[] banned) {
		if (paragraph.length() == 0) {
			return "";
		}

		Set<String> set = new HashSet<>();
		for (String s: banned) {
			set.add(s);
		}

		Map<String, Integer> map = new HashMap<>();
		String res = "";
		int resCount = 0;
		char[] arr = paragraph.toCharArray();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < arr.length; i++) {
			if (Character.isLetter(arr[i]) && i != arr.length - 1) {
				sb.append(Character.toLowerCase(arr[i]));
			} else {
				if (Character.isLetter(arr[i])) {
					sb.append(Character.toLowerCase(arr[i]));
				}
				if (sb.length() > 0) {
					String tmp = sb.toString();
					if (!set.contains(tmp)) {
						map.put(tmp, map.getOrDefault(tmp, 0) + 1);
						if (map.get(tmp) > resCount) {
							res = tmp;
							resCount = map.get(tmp);
						}
					}
				}
				sb = new StringBuilder();
			}
		}

		return  res;
	}

	public static void main(String[] args) {
		LC0819 sol = new LC0819();
		String paragraph = "Bob hit a ball, the hit BALL flew far after it was hit";
		String[] banned = new String[]{"hit"};

		System.out.println(sol.mostCommonWord(paragraph,banned));
	}
}
