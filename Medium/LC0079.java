package Leetcode.Medium;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * 79. Word Search
 * Given a 2D board and a word, find if the word exists in the grid.
 *
 * The word can be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring.
 * The same letter cell may not be used more than once.
 *
 * Example:
 *
 * board =
 * [
 *   ['A','B','C','E'],
 *   ['S','F','C','S'],
 *   ['A','D','E','E']
 * ]
 *
 * Given word = "ABCCED", return true.
 * Given word = "SEE", return true.
 * Given word = "ABCB", return false.
 */
public class LC0079 {
    /**
     * Solution1: DFS
     * 1) #Levels = word.length()
     * 2) Each level has 4 directions, represent a letter, in each level check if it equals to the target.
     * 3) Termination: when the last index is checked, return true.
     *
     * Time = O(n^2 * 4^l) l = word.length
     * Space = O(l) l for stack
     */

    public boolean exist(char[][] board, String word) {
        if (word == null || word.length() == 0) {
            return true;
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (dfs(board, word, i, j, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfs(char[][] board, String word, int row, int col, int idx) {
        if (idx == word.length()) {
            return true;
        }

        if (row < 0 || row == board.length || col < 0 || col == board[0].length
                ||board[row][col] != word.charAt(idx))  {
            return false;
        }

        char c = board[row][col];
        // Use '#' to represent current char has been visited
        board[row][col] = '#';
        boolean res = dfs(board, word, row + 1, col, idx + 1) || dfs(board, word, row - 1, col, idx + 1)
        || dfs(board, word, row, col - 1, idx + 1) || dfs(board, word, row, col + 1,idx + 1);
        board[row][col] = c;

        return res;
    }

    @Test
    public void test1() {
        char[][] board = new char[][]{
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        };
        String w = "ABCCED";
        Assert.assertTrue(exist(board,w));
    }

    @Test
    public void test2() {
        char[][] board = new char[][]{
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        };
        String w = "SEE";
        Assert.assertTrue(exist(board,w));
    }

    @Test
    public void test3() {
        char[][] board = new char[][]{
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        };
        String w = "ABCB";
        Assert.assertTrue(!exist(board,w));
    }

    public static void main(String[] args) {
        JUnitCore.main(LC0079.class.getName());
    }

}
