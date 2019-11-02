package Leetcode.Medium;

/**
 * 794. Valid Tic-Tac-Toe State
 * A Tic-Tac-Toe board is given as a string array board. Return True if and only if it is possible to reach this board position during the course of a valid tic-tac-toe game.
 * The board is a 3 x 3 array, and consists of characters " ", "X", and "O".  The " " character represents an empty square.
 *
 * Here are the rules of Tic-Tac-Toe:
 * Players take turns placing characters into empty squares (" ").
 * The first player always places "X" characters, while the second player always places "O" characters.
 * "X" and "O" characters are always placed into empty squares, never filled ones.
 * The game ends when there are 3 of the same (non-empty) character filling any row, column, or diagonal.
 * The game also ends if all squares are non-empty.
 * No more moves can be played if the game is over.
 *
 *  Example 1:
 * Input: board = ["O  ", "   ", "   "]
 * Output: false
 * Explanation: The first player always plays "X".
 *
 * Example 2:
 * Input: board = ["XOX", " X ", "   "]
 * Output: false
 * Explanation: Players take turns making moves.
 *
 * Example 3:
 * Input: board = ["XXX", "   ", "OOO"]
 * Output: false
 *
 * Example 4:
 * Input: board = ["XOX", "O O", "XOX"]
 * Output: true
 *
 * Note:
 * board is a length-3 array of strings, where each string board[i] has length 3.
 * Each board[i][j] is a character in the set {" ", "X", "O"}.
 */
public class LC0794 {
	/**
	 * Restrictions:
	 *      1) X is one greater than O or X = O
	 *      2) X wins: means X = O + 1 && !win(O)
	 *      3) O wins: means X == O && !win(X)
	 * We need to check the enemy's state because we need to exclusive both players win.
	 *
	 * Time = O(1)
	 * Space = O(1)
	 */
	public boolean validTicTacToe(String[] board) {
		int cX = 0, cO = 0;
		char[][] matrix = new char[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				matrix[i] = board[i].toCharArray();
				if (matrix[i][j] == 'X')
					cX++;
				if (matrix[i][j] == 'O')
					cO++;
			}
		}

		// 1. count does not macth
		if (!(cX == cO || cX - cO == 1))
			return false;

		// 2. if X wins, O should not take more steps
		if (cX == cO && win(matrix, 'X'))
			return false;

		//3. if O wins, X should not take more steps
		if (cX - cO == 1 && win(matrix, 'O'))
			return false;

		return true;
	}

	private boolean win(char[][] board, char ch) {
		for (int i = 0; i < 3; i++) {
			if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][2] == ch)
				return true;

			if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[2][i] == ch)
				return true;
		}

		if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[2][2] == ch)
			return true;

		if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[2][0] == ch)
			return true;

		return false;
	}

	public static void main(String[] args) {
		LC0794 sol = new LC0794();
		String[][] data = new String[][]{
				{"O  ", "   ", "   "},
				{"XOX", " X ", "   "},
				{"XXX", "   ", "OOO"},
				{"XOX", "O O", "XOX"}
		};

		for (String[] board: data) {
			System.out.println(sol.validTicTacToe(board));
		}
	}
}
