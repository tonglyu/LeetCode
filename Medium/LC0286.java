package Leetcode.Medium;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 286. Walls and Gates
 * You are given a m x n 2D grid initialized with these three possible values.
 * -1 - A wall or an obstacle.
 * 0 - A gate.
 * INF - Infinity means an empty room. We use the value 231 - 1 = 2147483647 to represent INF as you may assume that the distance to a gate is less than 2147483647.
 * Fill each empty room with the distance to its nearest gate. If it is impossible to reach a gate, it should be filled with INF.
 *
 * Example:
 * Given the 2D grid:
 * INF  -1  0  INF
 * INF INF INF  -1
 * INF  -1 INF  -1
 *   0  -1 INF INF
 * After running your function, the 2D grid should be:
 *   3  -1   0   1
 *   2   2   1  -1
 *   1  -1   2  -1
 *   0  -1   3   4
 */
public class LC0286 {
	/**
	 * Solution: BFS
	 * Put all 0's into queue
	 * expand the cell, and add 1 from its parent as the value
	 *
	 * Time = O(mn)
	 * Space = O(mn)
	 */
	public void wallsAndGates(int[][] rooms) {
		if (rooms.length == 0 || rooms[0].length == 0) {
			return;
		}
		int rows = rooms.length;
		int cols = rooms[0].length;

		Queue<int[]> q = new LinkedList<>();
		for (int i = 0; i < rows ;i++) {
			for (int j = 0; j < cols;j++) {
				if (rooms[i][j] == 0) {
					q.offer(new int[]{i,j});
				}
			}
		}

		while (!q.isEmpty()) {
			int[] cell = q.poll();
			int r = cell[0];
			int c = cell[1];
			if (r - 1 >= 0 && rooms[r - 1][c] == Integer.MAX_VALUE) {
				q.offer(new int[]{r - 1,c});
				rooms[r - 1][c] = rooms[r][c] + 1;
			}

			if (r + 1 < rows && rooms[r + 1][c] == Integer.MAX_VALUE) {
				q.offer(new int[]{r + 1,c});
				rooms[r + 1][c] = rooms[r][c] + 1;
			}

			if (c - 1 >= 0 && rooms[r][c - 1] == Integer.MAX_VALUE) {
				q.offer(new int[]{r,c - 1});
				rooms[r][c - 1] = rooms[r][c] + 1;
			}

			if (c + 1 < cols && rooms[r][c + 1] == Integer.MAX_VALUE) {
				q.offer(new int[]{r,c + 1});
				rooms[r][c + 1] = rooms[r][c] + 1;
			}
		}
	}

	@Test
	public void test1() {
		int INF = Integer.MAX_VALUE;
		int[][] rooms = new int[][]{
				{INF,  -1,  0,  INF},
				{INF,  INF,  INF, -1},
				{INF,  -1,  INF, -1},
				{0, -1,  INF,  INF},

		};

		wallsAndGates(rooms);
		int[][] act = new int[][]{
				{3,-1,0,1},
				{2,2,1,-1},
				{1,-1,2,-1},
				{0,-1,3,4}
		};

		for (int i = 0; i < rooms.length; i++) {
			for (int j = 0; j < rooms[0].length; j++) {
				Assert.assertEquals(rooms[i][j], act[i][j]);
			}
		}
	}

	public static void main(String[] args) {
		JUnitCore.main(LC0286.class.getName());
	}
}
