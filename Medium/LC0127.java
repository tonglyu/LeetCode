package Leetcode.Medium;

import java.util.*;

/**
 * 127. Word Ladder
 * Given two words (beginWord and endWord), and a dictionary's word list, find the length of shortest transformation sequence from beginWord to endWord, such that:
 *
 * Only one letter can be changed at a time.
 * Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
 *
 * Note:
 * Return 0 if there is no such transformation sequence.
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
 * Output: 5
 * Explanation: As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
 * return its length 5.
 *
 * Example 2:
 * Input:
 * beginWord = "hit"
 * endWord = "cog"
 * wordList = ["hot","dot","dog","lot","log"]
 * Output: 0
 * Explanation: The endWord "cog" is not in wordList, therefore no possible transformation.
 */
public class LC0127 {
	/**
	 * Data structure:
	 *  BFS: queue
	 *  hashset: a visited dictionary
	 *
	 * Each level represents how many steps you move from begin word
	 * while q is not empty:
	 *      steps++ (for this level we move 1 step forward)
	 *      for word in queue:
	 *          new nodes = expand word
	 *          if (endWord in new nodes) return step
	 *          queue.offer(new nodes)
	 *
	 * Time = O(n * 26^l)
	 * Space = O(n)
	 * l = len(word), n=|wordList|
	 */
	public int ladderLength(String beginWord, String endWord, List<String> wordList) {
		Set<String> dict = new HashSet<>(wordList);
		if (!dict.contains(endWord)) return 0;

		Queue<String> q = new ArrayDeque<>();
		q.offer(beginWord);

		int steps = 1;

		while (!q.isEmpty()) {
			++steps;
			for (int s = q.size(); s > 0; --s) {
				char[] word = q.poll().toCharArray();
				for (int i = 0; i < word.length; ++i) {
					char ch = word[i];
					for (char c = 'a'; c <= 'z'; ++c) {
						if (c == ch) continue;
						word[i] = c;
						String t = new String(word);
						if (t.equals(endWord))
							return steps;
						if (!dict.contains(t)) continue;
						dict.remove(t);
						q.offer(t);
					}
					word[i] = ch;
				}
			}
		}

		return 0;
	}

	/**
	 * Bidirectional BFS
	 *
	 * Time = O(n*26^l/2)
	 * Space = O(n)
	 */
	public int ladderLength2(String beginWord, String endWord, List<String> wordList) {
		Set<String> dict = new HashSet<>(wordList);

		if (!dict.contains(endWord)) return 0;

		Set<String> q1 = new HashSet<>();
		Set<String> q2 = new HashSet<>();
		q1.add(beginWord);
		q2.add(endWord);

		int steps = 1;

		while (!q1.isEmpty() && !q2.isEmpty()) {
			++steps;

			// Always expand smaller queue first, swap the queue
			if (q1.size() > q2.size()) {
				Set<String> tmp = q1;
				q1 = q2;
				q2 = tmp;
			}

			Set<String> q = new HashSet<>();
			for (String word : q1) {
				char[] chs = word.toCharArray();
				for (int i = 0; i < chs.length; ++i) {
					char ch = chs[i];
					for (char c = 'a'; c <= 'z'; ++c) {
						chs[i] = c;
						String t = new String(chs);
						if (q2.contains(t)) return steps;
						if (!dict.contains(t)) continue;
						dict.remove(t);
						q.add(t);
					}
					chs[i] = ch;
				}
			}

			q1 = q;
		}
		return 0;
	}

	public static void main(String[] args) {
		LC0127 sol = new LC0127();
		String beginWord = "hit";
		String endWord = "cog";
		String[] wordList = new String[]{"hot","dot","dog","lot","log","cog"};

		System.out.println(sol.ladderLength2(beginWord,endWord,Arrays.asList(wordList)));
	}
}
