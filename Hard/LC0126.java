package Leetcode.Hard;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

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
	 * Solution: BFS + DFS
	 * BFS to construct the graph, and DFS to find all possible paths
	 * Map<String, List> parents: record the previous word for each word in the path.
	 * Map<String, Integer> steps: record the min steps when expanding current node in case one word have multiple parents in the paths
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
		if (!dict.contains(endWord)) {
			return res;
		}
		// beginword will enlarge the steps
		dict.remove(beginWord);

		Map<String, Integer> steps = new HashMap<>();
		steps.put(beginWord, 1);
		Map<String, List<String>> parents = new HashMap<>();
		Queue<String> q = new ArrayDeque<>();
		q.offer(beginWord);

		int step = 1;
		boolean found = false;

		while (!q.isEmpty() && !found) {
			int size = q.size();
			step++;
			for (int i = 0; i < size; i++) {
				String word = q.poll();
				char[] arr = word.toCharArray();
				for (int j = 0; j < arr.length; ++j) {
					char c = arr[j];
					for (char w = 'a'; w <= 'z'; ++w) {
						if (w == c) continue;
						arr[j] = w;
						String cur = new String(arr);
						if (endWord.equals(cur)) {
							parents.putIfAbsent(cur, new ArrayList<>());
							parents.get(cur).add(word);
							found = true;
						}  else {
							// cur has already been expanded
							// cur is not a new word, but another transformation with the same steps,
							// which means cur has other parents.
							// if step > steps.get(cur), means we spend more steps to reach cur, which make no sense
							if (steps.get(cur) != null && steps.get(cur) == step) {
								parents.get(cur).add(word);
							} else {
								// a new word
								if (!dict.contains(cur)) continue;
								dict.remove(cur);
								q.offer(cur);
								steps.put(cur, steps.get(word) + 1);
								parents.putIfAbsent(cur, new ArrayList<>());
								parents.get(cur).add(word);
							}
						}
					}
					arr[j] = c;
				}
			}
		}

		//DFS to find all possible paths
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

	@Test
	public void test1() {
		String beginWord = "red";
		String endWord = "tax";
		List<String> wordList = new ArrayList<>(Arrays.asList("rex", "ted", "tex", "tad", "tax"));
		List<List<String>> act = findLadders(beginWord, endWord, wordList);
		List<List<String>> exp = new ArrayList<>(Arrays.asList(Arrays.asList("red","ted","tad","tax"),
				Arrays.asList("red","ted","tex","tax"),Arrays.asList("red","rex","tex","tax")));
		Assert.assertTrue(act.containsAll(exp) && exp.containsAll(act));
	}

	@Test
	public void test2() {
		String beginWord = "hit";
		String endWord = "cog";
		List<String> wordList = new ArrayList<>(Arrays.asList("hot","dot","dog","lot","log","cog"));
		List<List<String>> act = findLadders(beginWord, endWord, wordList);
		List<List<String>> exp = new ArrayList<>(Arrays.asList(Arrays.asList("hit","hot","dot","dog","cog"),
				Arrays.asList("hit","hot","lot","log","cog")));
		Assert.assertTrue(act.containsAll(exp) && exp.containsAll(act));
	}

	@Test
	public void test3() {
		String beginWord = "hit";
		String endWord = "cog";
		List<String> wordList = new ArrayList<>(Arrays.asList("hot","dot","dog","lot","log"));
		List<List<String>> act = findLadders(beginWord, endWord, wordList);
		List<List<String>> exp = new ArrayList<>();
		Assert.assertTrue(act.containsAll(exp) && exp.containsAll(act));
	}

	public static void main(String[] args) {
		JUnitCore.main(LC0126.class.getName());
	}
}
