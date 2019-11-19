package Leetcode.Medium;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 909. Snakes and Ladders
 * On an N x N board, the numbers from 1 to N*N are written boustrophedonically starting from the bottom left of the board, and alternating direction each row.  For example, for a 6 x 6 board, the numbers are written as follows:
 *
 * You start on square 1 of the board (which is always in the last row and first column).  Each move, starting from square x, consists of the following:
 *
 * You choose a destination square S with number x+1, x+2, x+3, x+4, x+5, or x+6, provided this number is <= N*N.
 * (This choice simulates the result of a standard 6-sided die roll: ie., there are always at most 6 destinations, regardless of the size of the board.)
 * If S has a snake or ladder, you move to the destination of that snake or ladder.  Otherwise, you move to S.
 * A board square on row r and column c has a "snake or ladder" if board[r][c] != -1.  The destination of that snake or ladder is board[r][c].
 *
 * Note that you only take a snake or ladder at most once per move: if the destination to a snake or ladder is the start of another snake or ladder, you do not continue moving.  (For example, if the board is `[[4,-1],[-1,3]]`, and on the first move your destination square is `2`, then you finish your first move at `3`, because you do not continue moving to `4`.)
 * Return the least number of moves required to reach square N*N.  If it is not possible, return -1.
 *
 * Example 1:
 *
 * Input: [
 * [-1,-1,-1,-1,-1,-1],
 * [-1,-1,-1,-1,-1,-1],
 * [-1,-1,-1,-1,-1,-1],
 * [-1,35,-1,-1,13,-1],
 * [-1,-1,-1,-1,-1,-1],
 * [-1,15,-1,-1,-1,-1]]
 * Output: 4
 *
 * Explanation:
 * At the beginning, you start at square 1 [at row 5, column 0].
 * You decide to move to square 2, and must take the ladder to square 15.
 * You then decide to move to square 17 (row 3, column 5), and must take the snake to square 13.
 * You then decide to move to square 14, and must take the ladder to square 35.
 * You then decide to move to square 36, ending the game.
 * It can be shown that you need at least 4 moves to reach the N*N-th square, so the answer is 4.
 * Note:
 *
 * 2 <= board.length = board[0].length <= 20
 * board[i][j] is between 1 and N*N or is equal to -1.
 * The board square with number 1 has no snake or ladder.
 * The board square with number N*N has no snake or ladder.
 */
public class LC0909 {
	/**
	 * Solution: BFS
	 * Suppose we are on a square with number s. We would like to know all final destinations with number s2 after making one move.
	 * This requires knowing the coordinates get(s2) of square s2.
	 * This is a small puzzle in itself: we know that the row changes every N squares, and so is only based on quot = (s2-1) / N;
	 * also the column is only based on rem = (s2-1) % N and what row we are on (forwards or backwards.)
	 *
	 * From there, we perform a breadth first search, where the nodes are the square numbers s.
	 *
	 * Time = O(n^2)
	 * Space = O(n^2)
	 */
	public int snakesAndLadders(int[][] board) {
		if (board == null || board.length == 0 || board[0].length == 0) {
			return -1;
		}

		int n = board.length;
		Queue<Integer> q = new LinkedList<>();
		q.offer(1);
		boolean[] visited = new boolean[n * n + 1];
		visited[1] = true;

		int step = 0;
		while (!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				int cur = q.poll();
				if (cur == n * n) {
					return step;
				}
				for (int dir = 1; dir <= 6 && cur + dir <= n * n; dir++) {
					int[] pos = getCoordinate(cur + dir, n);
					int next;
					if (board[pos[0]][pos[1]] == -1) {
						next = cur + dir;
					} else {
						next = board[pos[0]][pos[1]];
					}
					if (!visited[next]) {
						visited[next] = true;
						q.offer(next);
					}
				}
			}
			step++;
		}

		return -1;
	}

	// Given a square num s, return board coordinates (r, c) as r*N + c
	private int[] getCoordinate(int num, int n) {
		int r = (n - 1) - (num - 1) / n;
		int c = (num - 1) % n;
		if (r % 2 == n % 2) {
			return new int[]{r, n - 1 - c};
		} else {
			return new int[]{r,c};
		}
	}

	@Test
	public void test1() {
		int[][] board = new int[][]{
				{-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1},
				{-1,35,-1,-1,13,-1},
				{-1,-1,-1,-1,-1,-1},
				{-1,15,-1,-1,-1,-1}};
		Assert.assertEquals(4, snakesAndLadders(board));
	}

	@Test
	public void test2() {
		int[][] board = new int[][]{
				{-1,-1,-1,46,47,-1,-1,-1},
				{51,-1,-1,63,-1,31,21,-1},
				{-1,-1,26,-1,-1,38,-1,-1},
				{-1,-1,11,-1,14,23,56,57},
				{11,-1,-1,-1,49,36,-1,48},
				{-1,-1,-1,33,56,-1,57,21},
				{-1,-1,-1,-1,-1,-1,2,-1},
				{-1,-1,-1,8,3,-1,6,56}};
		Assert.assertEquals(4, snakesAndLadders(board));
	}

	public static void main(String[] args) {
		JUnitCore.main(LC0909.class.getName());
	}
}
