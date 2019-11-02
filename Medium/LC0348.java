package Leetcode.Medium;

/**
 * 348. Design Tic-Tac-Toe
 * Design a Tic-tac-toe game that is played between two players on a n x n grid.
 *
 * You may assume the following rules:
 * A move is guaranteed to be valid and is placed on an empty block.
 * Once a winning condition is reached, no more moves is allowed.
 * A player who succeeds in placing n of their marks in a horizontal, vertical, or diagonal row wins the game.
 *
 * Example:
 * Given n = 3, assume that player 1 is "X" and player 2 is "O" in the board.
 * TicTacToe toe = new TicTacToe(3);
 * toe.move(0, 0, 1); -> Returns 0 (no one wins)
 * |X| | |
 * | | | |    // Player 1 makes a move at (0, 0).
 * | | | |
 *
 * toe.move(0, 2, 2); -> Returns 0 (no one wins)
 * |X| |O|
 * | | | |    // Player 2 makes a move at (0, 2).
 * | | | |
 *
 * toe.move(2, 2, 1); -> Returns 0 (no one wins)
 * |X| |O|
 * | | | |    // Player 1 makes a move at (2, 2).
 * | | |X|
 *
 * toe.move(1, 1, 2); -> Returns 0 (no one wins)
 * |X| |O|
 * | |O| |    // Player 2 makes a move at (1, 1).
 * | | |X|
 *
 * toe.move(2, 0, 1); -> Returns 0 (no one wins)
 * |X| |O|
 * | |O| |    // Player 1 makes a move at (2, 0).
 * |X| |X|
 *
 * toe.move(1, 0, 2); -> Returns 0 (no one wins)
 * |X| |O|
 * |O|O| |    // Player 2 makes a move at (1, 0).
 * |X| |X|
 *
 * toe.move(2, 1, 1); -> Returns 1 (player 1 wins)
 * |X| |O|
 * |O|O| |    // Player 1 makes a move at (2, 1).
 * |X|X|X|
 */
class TicTacToe {
	/**
	 * Solution:
	 * We need two arrays: int rows[n], int cols[n], plus two variables: diagonal, anti_diagonal.
	 * add one for Player1 and -1 for Player2
	 *
	 * Since each time only rows[row], cols[col], dig1, dig2,  4 values might be changes, we only need to check these 4
	 *
	 * Time = O(1)
	 * Space = O(n)
	 */
	int[] rows;
	int[] cols;
	int dig1;
	int dig2;
	int n;

	/**
	 * Initialize your data structure here.
	 */
	public TicTacToe(int n) {
		this.n = n;
		rows = new int[n];
		cols = new int[n];
		dig1 = 0;
		dig2 = 0;
	}

	/**
	 * Player {player} makes a move at ({row}, {col}).
	 *
	 * @param row    The row of the board.
	 * @param col    The column of the board.
	 * @param player The player, can be either 1 or 2.
	 * @return The current winning condition, can be either:
	 * 0: No one wins.
	 * 1: Player 1 wins.
	 * 2: Player 2 wins.
	 */
	public int move(int row, int col, int player) {
		int val = player == 1 ? 1 : -1;
		rows[row] += val;
		cols[col] += val;
		if (row == col) {
			dig1 += val;
		}
		if (row == n - 1 - col) {
			dig2 += val;
		}

		if (Math.abs(rows[row]) == n || Math.abs(cols[col]) == n || Math.abs(dig1) == n || Math.abs(dig2) == n) {
			return player;
		}

		return 0;
	}
}

/**
 * Your TicTacToe object will be instantiated and called as such:
 * TicTacToe obj = new TicTacToe(n);
 * int param_1 = obj.move(row,col,player);
 */

public class LC0348 {
	public static void main(String[] args) {
		TicTacToe toe = new TicTacToe(3);
		System.out.println(toe.move(0, 0, 1));
		System.out.println(toe.move(0, 2, 2));
		System.out.println(toe.move(2, 2, 1));
		System.out.println(toe.move(1, 1, 2));
		System.out.println(toe.move(2, 0, 1));
		System.out.println(toe.move(1, 0, 2));
		System.out.println(toe.move(2, 1, 1));
	}
}
