package Leetcode.Medium;

import java.util.Arrays;

public class LC {
	private int min = Integer.MAX_VALUE;
	public int snakesAndLadders(int[][] board) {
		if (board.length == 0) {
			return 0;
		}

		int n = board.length;

		dfs(board, 1, n, 0, false);
		return min;
	}

	private void dfs(int[][] board, int number, int n, int steps, boolean quick) {
		if (number == n*n ) {
			min = Math.min(min, steps);
			return;
		}

		if (steps > min) {
			return;
		}

		int row = n - 1 - (number - 1) / n;
		int col;
		if (n % 2 == 0 && row % 2 != 0) {
			col = (number - 1) % n;
		} else {
			col = n - 1 - (number - 1) % n;
		}

		if (board[row][col] != -1 && !quick) {
			dfs(board, board[row][col], n, steps, true);
		} else {
			int[] dirs = new int[]{1,2,3,4,5,6};
			for (Integer dir: dirs) {
				if (number + dir <= n * n) {
					dfs(board, number + dir, n, steps + 1, false);
				}
			}
		}
	}
	public static void main(String[] args) {
		LC sol = new LC();
		int[][] board = new int[6][6];
		for (int[] row : board) {
			Arrays.fill(row, -1);
		}
		board[5][1] = 15;
		board[3][1] = 35;
		board[3][4] = 13;

		System.out.println(sol.snakesAndLadders(board));
	}

}
