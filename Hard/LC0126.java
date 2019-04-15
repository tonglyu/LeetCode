package Leetcode.Hard;

import java.util.*;

/**
 * 126. Word Ladder II
 * Given two words (beginWord and endWord), and a dictionary's word list, find all shortest transformation sequence(s) from beginWord to endWord, such that:
 *
 * Only one letter can be changed at a time
 * Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
 * Note:
 *
 * Return an empty list if there is no such transformation sequence.
 * All words have the same length.
 * All words contain only lowercase alphabetic characters.
 * You may assume no duplicates in the word list.
 * You may assume beginWord and endWord are non-empty and are not the same.
 *
 * Example 1:
 * Input:
 * beginWord = "hit",
 * endWord = "cog",
 * wordList = ["hot","dot","dog","lot","log","cog"]
 *
 * Output:
 * [
 *   ["hit","hot","dot","dog","cog"],
 *   ["hit","hot","lot","log","cog"]
 * ]
 *
 * Example 2:
 * Input:
 * beginWord = "hit"
 * endWord = "cog"
 * wordList = ["hot","dot","dog","lot","log"]
 *
 * Output: []
 *
 * Explanation: The endWord "cog" is not in wordList, therefore no possible transformation.
 */
public class LC0126 {
	/**
	 * BFS + DFS
	 * Map<String, List> parents: record the previous word for each word in the path.
	 * Map<String, Integer> height: record the level of current node in case one word have multiple parents in the paths
	 * flag: indicate whether the end word has been found. If found, we just need to complete current level
	 *
	 * When expand each word:
	 *      1) Check if it is end word:
	 *              add to parents map
	 *              found = T
	 *      2) else:
	 *          if word is not new:
	 *              if current step = height.get(word)
	 *                  add to word's parents
	 *          else: // a new word
	 *              check dict
	 *              q.offer(word)
	 *              update height
	 *              update parents
	 *
	 * Time = O(n * 26^l)
	 * Space = O(n)
	 */
	public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
		Set<String> dict = new HashSet<>(wordList);
		List<List<String>> res = new ArrayList<>();
		if (!dict.contains(endWord)) return res;
		// beginword will enlarge the steps
		dict.remove(beginWord);
		//dict.remove(endWord);

		Map<String, Integer> height = new HashMap<>();
		height.put(beginWord, 1);
		Map<String, List<String>> parents = new HashMap<>();
		Queue<String> q = new ArrayDeque<>();
		q.offer(beginWord);

		int step = 1;
		boolean found = false;

		while (!q.isEmpty() && !found) {
			step++;
			for (int s = q.size(); s > 0; --s) {
				String parent = q.poll();
				char[] word = parent.toCharArray();
				for (int i = 0; i < word.length; ++i) {
					char ch = word[i];
					for (char c = 'a'; c <= 'z'; ++c) {
						if (c == ch) continue;
						word[i] = c;
						String w = new String(word);
						if (endWord.equals(w)) {
							if (parents.get(w) == null) {
								parents.put(w, new ArrayList<>());
							}
							parents.get(w).add(parent);
							found = true;
						}  else {
							// w has already been expanded
							// w is not a new word, but another transformation with the same steps
							// which means w has other parents.
							if (height.get(w) != null && height.get(w) == step) {
								parents.get(w).add(parent);
							} else {
								// a new word
								if (!dict.contains(w)) continue;
								dict.remove(w);
								q.offer(w);
								height.put(w, height.get(parent) + 1);
								if (parents.get(w) == null) {
									parents.put(w, new ArrayList<>());
								}
								parents.get(w).add(parent);
							}
						}
					}
					word[i] = ch;
				}
			}
		}

		if (found) {
			List<String> path = new ArrayList<>();
			path.add(endWord);
			getPaths(beginWord, endWord, parents, path, res);
		}

		return res;
	}

	private void getPaths(String beginWord, String word, Map<String, List<String>> parents, List<String> path, List<List<String>> res) {
		if (word.equals(beginWord)) {
			res.add(new ArrayList<>(path));
			return;
		}

		for (String node : parents.get(word)) {
			path.add(0, node);
			getPaths(beginWord, node, parents, path, res);
			path.remove(node);
		}
	}

	public static void main(String[] args) {
		LC0126 sol = new LC0126();
		String beginWord = "red";
		String endWord = "tax";
		String[] wordList = new String[]{"rex", "ted", "tex", "tad", "tax"};

		List<List<String>> res = sol.findLadders(beginWord, endWord, Arrays.asList(wordList));
		System.out.println(res);
	}
}
