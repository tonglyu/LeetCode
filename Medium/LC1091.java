package Leetcode.Medium;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 1091. Shortest Path in Binary Matrix
 * In an N by N square grid, each cell is either empty (0) or blocked (1).
 *
 * A clear path from top-left to bottom-right has length k if and only if it is composed of cells C_1, C_2, ..., C_k such that:
 * Adjacent cells C_i and C_{i+1} are connected 8-directionally (ie., they are different and share an edge or corner)
 * C_1 is at location (0, 0) (ie. has value grid[0][0])
 * C_k is at location (N-1, N-1) (ie. has value grid[N-1][N-1])
 * If C_i is located at (r, c), then grid[r][c] is empty (ie. grid[r][c] == 0).
 * Return the length of the shortest such clear path from top-left to bottom-right.  If such a path does not exist, return -1.
 *
 * Example 1:
 * Input: [[0,1],[1,0]]
 * Output: 2
 *
 * Example 2:
 * Input: [[0,0,0],[1,1,0],[1,1,0]]
 * Output: 4
 *
 * Note:
 * 1 <= grid.length == grid[0].length <= 100
 * grid[r][c] is 0 or 1
 */
public class LC1091 {
	/**
	 * Solution: BFS
	 * Init: offer (0,0) into the queue
	 * Termination: reach (n-1,n-1)
	 * Expand rule:
	 *      If we reach the target point, return the current path length.
	 *      For each level, expand the node for 8 directions, and increase the path
	 *
	 * Time = O(n^2)
	 * Space = O(n^2)
	 */
	public int shortestPathBinaryMatrix(int[][] grid) {
		if (grid.length == 0 || grid[0].length == 0) {
			return  -1;
		}

		int rows = grid.length;
		int cols = grid[0].length;
		if (grid[0][0] != 0 || grid[rows-1][cols-1] != 0) {
			return -1;
		}

		int[] dr = new int[]{-1,-1,-1,0,0,1,1,1};
		int[] dc = new int[]{-1,0,1,-1,1,-1,0,1};

		Queue<int[]> q = new LinkedList<>();
		q.offer(new int[]{0,0});
		grid[0][0] = -1;

		int res = 0;
		while (!q.isEmpty()) {
			res++;
			int size = q.size();
			for (int i = 0; i < size; i++) {
				int[] cell = q.poll();
				// check if it is the target point
				if (cell[0] == rows - 1 && cell[1] == cols - 1) {
					return res;
				}
				for (int j = 0; j < 8; j++) {
					int r = cell[0] + dr[j];
					int c = cell[1] + dc[j];
					if (r >= 0 && r < rows && c >= 0 && c < cols && grid[r][c] == 0) {
						grid[r][c] = -1;
						q.offer(new int[]{r,c});
					}
				}
			}
		}

		return -1;
	}

	@Test
	public void test1() {
		int[][] grid = new int[][]{
				{0,1},
				{1,0}
		};
		Assert.assertEquals(2,shortestPathBinaryMatrix(grid));
	}

	@Test
	public void test2() {
		int[][] grid = new int[][]{
				{0,0,0},
				{1,1,0},
				{1,1,0}
		};
		Assert.assertEquals(4,shortestPathBinaryMatrix(grid));
	}

	public static void main(String[] args) {
		JUnitCore.main(LC1091.class.getName());
	}
}
