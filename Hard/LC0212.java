package Leetcode.Hard;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.util.*;

/**
 * 212. Word Search II
 * Given a 2D board and a list of words from the dictionary, find all words in the board.
 * Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring.
 * The same letter cell may not be used more than once in a word.
 *
 * Example:
 * Input:
 * board = [
 *   ['o','a','a','n'],
 *   ['e','t','a','e'],
 *   ['i','h','k','r'],
 *   ['i','f','l','v']
 * ]
 * words = ["oath","pea","eat","rain"]
 * Output: ["eat","oath"]
 *
 * Note:
 * All inputs are consist of lowercase letters a-z.
 * The values of words are distinct.
 */
public class LC0212 {
	/**
	 * Solution: Trie + DFS
	 * We build a Trie out of the words in the dictionary, which would be used for the matching process later.
	 * Starting from each cell, we start the backtracking exploration (i.e. backtracking(cell)), if there exists any word in the dictionary that starts with the letter in the cell.
	 * During the recursive function call backtracking(cell), we explore the neighbor cells (i.e. neighborCell) around the current cell for the next recursive call backtracking(neighborCell).
	 * At each call, we check if the sequence of letters that we traverse so far matches any word in the dictionary, with the help of the Trie data structure that we built at the beginning.
	 *
	 * #Levels: max word length L
	 * #of branches of each level: 4 for first level, 3 for the remaining L-1 level
	 * termination: when current char is visited or cur char does not exist for trienode, return
	 *
	 * Improvement for algo: pruning Trie when we found a word
	 * 
	 * N = the total number of letters in the dictionary.
	 * M = the number of cells in the board
	 * L = the maximum length of words.
	 *
	 * Time = O(N + M * 4 * 3^(L-1))
	 * Space = O(N)
	 */
	public List<String> findWords(char[][] board, String[] words) {
		List<String> res = new ArrayList<>();
		if (board.length == 0 || board[0].length == 0) {
			return res;
		}

		TrieNode root = new TrieNode();
		for (String w: words) {
			addWord(w, root);
		}
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				dfs(board, i, j, root, res);
			}
		}
		return res;
	}

	private void dfs(char[][] board, int row, int col, TrieNode cur, List<String> res) {
		char c = board[row][col];
		//if current is visited or cur letter does not exist, we move back
		if (c == '#' || cur.links[c - 'a'] == null) {
			return;
		}

		cur = cur.links[c - 'a'];
		if (cur.word != null) {
			//if cur is a word, we add it into res;
			//NOTICE: we cannot return here, cuz we might find another word along with current prefix
			res.add(cur.word);
			cur.word = null; //deduplicate
		}

		int[][] dir = new int[][]{{-1,0},{1,0},{0,-1},{0,1}};
		board[row][col] = '#';
		for (int i = 0; i < 4; i++) {
			int x = row + dir[i][0];
			int y = col + dir[i][1];
			if (x < 0 || x >= board.length || y < 0 || y >= board[0].length) {
				continue;
			}
			dfs(board, x, y, cur, res);
		}
		board[row][col] = c;
	}

	private void addWord(String word, TrieNode root) {
		TrieNode cur = root;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (cur.links[c - 'a'] == null) {
				cur.links[c - 'a'] = new TrieNode();
			}
			cur = cur.links[c - 'a'];
		}
		cur.word = word;
	}

	class TrieNode {
		TrieNode[] links;
		String word;
		public TrieNode() {
			links = new TrieNode[26];
		}
	}

	@Test
	public void test1() {
		char[][] board = new char[][]{
				{'o','a','a','n'},
				{'e','t','a','e'},
				{'i','h','k','r'},
				{'i','f','l','v'}
		};
		String[] words = new String[]{"oath","pea","eat","rain"};
		List<String> exp = Arrays.asList("eat","oath");
		List<String>  act = findWords(board, words);
		Assert.assertTrue(exp.containsAll(act) && act.containsAll(exp));
	}

	@Test
	public void test2() {
		char[][] board = new char[][]{
				{'a','b'},
				{'c','d'}
		};
		String[] words = new String[]{"ab","cb","ad","bd","ac","ca","da","bc","db","adcb","dabc","abb","acb"};
		List<String> exp = Arrays.asList("ab","ac","bd","ca","db");
		List<String>  act = findWords(board, words);
		Assert.assertTrue(exp.containsAll(act) && act.containsAll(exp));
	}

	public static void main(String[] args) {
		JUnitCore.main(LC0212.class.getName());
	}
}
