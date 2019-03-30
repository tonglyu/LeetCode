package Leetcode;

/** 79. Word Search
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
public class LC079 {
    /**
     * Data structure: boolean[][] visited
     * dfs
     *
     * 1) #Levels = word.length()
     * 2) Every level represent a letter, in each level check if it equals to the target.
     * 3) Termination: when the last index is checked, return true.
     *
     * Time = O(n2 * k) k = word.length
     * Space = O(n2)
     */

    public boolean exist(char[][] board, String word) {
        if (word == null || word.length() == 0) {
            return true;
        }
        char[] target = word.toCharArray();
        boolean[][] visited = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (dfs(board, target, i, j, 0, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfs(char[][] board, char[] target, int row, int col, int idx, boolean[][] visited) {
        if (idx == target.length) {
            return true;
        }

        if (row < 0 || row == board.length || col < 0 || col == board[0].length
                ||board[row][col] != target[idx] || visited[row][col])  {
            return false;
        }

        visited[row][col] = true;
        boolean exist = dfs(board, target, row + 1, col, idx + 1, visited) || dfs(board, target, row - 1, col, idx + 1,visited)
        || dfs(board, target, row, col - 1, idx + 1, visited) || dfs(board, target, row, col + 1,idx + 1, visited);
        visited[row][col] = false;

        return exist;
    }

    public static void main(String[] args) {
        LC079 sol = new LC079();
        char[][] board = new char[][]{
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        };
        String word = "SEE";
        System.out.println(sol.exist(board,word));
    }
}
