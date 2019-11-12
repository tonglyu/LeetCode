package Leetcode.Easy;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 994. Rotting Oranges
 * In a given grid, each cell can have one of three values:
 * the value 0 representing an empty cell;
 * the value 1 representing a fresh orange;
 * the value 2 representing a rotten orange.
 * Every minute, any fresh orange that is adjacent (4-directionally) to a rotten orange becomes rotten.
 *
 * Return the minimum number of minutes that must elapse until no cell has a fresh orange.  If this is impossible, return -1 instead.
 *
 * Example 1:
 * Input: [[2,1,1],[1,1,0],[0,1,1]]
 * Output: 4
 *
 * Example 2:
 * Input: [[2,1,1],[0,1,1],[1,0,1]]
 * Output: -1
 * Explanation:  The orange in the bottom left corner (row 2, column 0) is never rotten, because rotting only happens 4-directionally.
 *
 * Example 3:
 * Input: [[0,2]]
 * Output: 0
 * Explanation:  Since there are already no fresh oranges at minute 0, the answer is just 0.
 *
 * Note:
 * 1 <= grid.length <= 10
 * 1 <= grid[0].length <= 10
 * grid[i][j] is only 0, 1, or 2.
 */
public class LC0994 {
	/**
	 * Solution: BFS
	 * Put all cells that equals to 2 into queue,
	 *      see how many levels are there for BFS
	 * Check again for each cell, if there is still 1, means we cannot get to that cell and return -1
	 * if res == 0, means there is no rotten orange, we just return 0
	 *
	 * Time = O(n)  n = #cells
	 * Space = O(n)
	 */
	public int orangesRotting(int[][] grid) {
		int rows = grid.length;
		int cols = grid[0].length;

		Queue<int[]> q = new LinkedList<>();
		for (int i = 0; i < rows ;i++) {
			for (int j = 0; j < cols;j++) {
				if (grid[i][j] == 2) {
					q.offer(new int[]{i,j});
				}
			}
		}

		int[] dr = new int[]{-1,0,0,1};
		int[] dc = new int[]{0,-1,1,0};
		int res = 0;
		while (!q.isEmpty()) {
			int size = q.size();
			res++;
			for (int i = 0; i < size; i++) {
				int[] cell = q.poll();
				for (int j = 0; j < 4; j++) {
					int r = cell[0] + dr[j];
					int c = cell[1] + dc[j];
					if (r >= 0 && r < rows && c >= 0 && c < cols && grid[r][c] == 1) {
						q.offer(new int[]{r,c});
						grid[r][c] = 2;
					}
				}
			}
		}

		for (int i = 0; i < rows ;i++) {
			for (int j = 0; j < cols;j++) {
				if (grid[i][j] == 1) {
					return -1;
				}
			}
		}

		return res == 0 ? 0 : res - 1;
	}

	@Test
	public void test1() {
		int[][] grid = new int[][]{
				{2,1,1},
				{1,1,0},
				{0,1,1}
		};
		Assert.assertEquals(4, orangesRotting(grid));
	}

	@Test
	public void test2() {
		int[][] grid = new int[][]{
				{2,1,1},
				{0,1,1},
				{1,0,1}
		};
		Assert.assertEquals(-1, orangesRotting(grid));
	}

	@Test
	public void test3() {
		int[][] grid = new int[][]{
				{2,0,0}
		};
		Assert.assertEquals(0, orangesRotting(grid));
	}

	@Test
	public void test4() {
		int[][] grid = new int[][]{
				{0}
		};
		Assert.assertEquals(0, orangesRotting(grid));
	}

	public static void main(String[] args) {
		JUnitCore.main(LC0994.class.getName());
	}
}
